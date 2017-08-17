/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.ws.service;

import static com.gy.hsxt.common.utils.DateUtil.DateToString;
import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.ds.common.contants.DSContants.DSTaskStatus;
import com.gy.hsi.nt.api.beans.DynamicBizMsgBean;
import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsi.nt.api.service.INtService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolder;
import com.gy.hsxt.ws.bean.ExecuteLog;
import com.gy.hsxt.ws.bean.NoticeMsg;
import com.gy.hsxt.ws.bean.WelfareQualification;
import com.gy.hsxt.ws.mapper.ExecuteLogMapper;
import com.gy.hsxt.ws.mapper.NoticeMsgMapper;
import com.gy.hsxt.ws.mapper.WelfareQualificationMapper;

/**
 * 积分福利通知服务
 * 
 * 每天定时对已生效、失效的福利资格发送短信、推送消息给消费者
 * 
 * @Package: com.gy.hsxt.ws.service
 * @ClassName: WelfareNoticeService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2016-04-17 上午16:49:01
 * @version V1.0
 */
@Service("welfareNoticeService")
public class WelfareNoticeService extends BasicService implements IDSBatchService {

	@Autowired
	private IDSBatchServiceListener listener;

	@Autowired
	private NoticeMsgMapper msgMppper;

	@Autowired
	private WelfareQualificationMapper qualificationMapper;

	@Autowired
	private IUCAsCardHolderService cardHolderService;

	@Autowired
	private INtService ntService;

	@Autowired
	private ExecuteLogMapper logMapper;
	
	@Resource
	private ILCSBaseDataService lcsBaseDataService;
	
	@Autowired
	private IUCAsEntService entService;

	/**
	 * 执行批处理任务
	 * 
	 * @param arg0
	 * @return
	 * @see com.gy.hsi.ds.api.IDSBatchService#excuteBatch(java.util.Map)
	 */
	@Override
	public boolean excuteBatch(String executeId, Map<String, String> arg1) {
		String batchDate = DateToString(new Date(), "yyyyMMdd");
		try {
			// 上报任务进度给调度中心
			reportStatus(executeId, DSTaskStatus.HANDLING, "积分福利通知消息服务：执行开始");
			// 执行处理、提取需要发送消息的福利资格信息放入消息推送表中
			try {
				msgMppper.handSendMsg();
			} catch (Exception e) {
				logError(e.getMessage(), e);
				saveLog(batchDate, -1, e.getMessage());
			}
			// 获取待发送的消息
			List<NoticeMsg> list = msgMppper.queryNotice(null, 0);
			if (list == null || list.isEmpty()) {
				reportStatus(executeId, DSTaskStatus.SUCCESS, "积分福利通知消息服务：执行成功,无通知需要发送");
				saveLog(batchDate, 1, null);
				return true;
			}
			logInfo("msgSize:-----" + list.size());
			String paltFormCustId = entService.findEntCustIdByEntResNo(lcsBaseDataService
					.getLocalInfo().getPlatResNo());
			logInfo("paltFormCustId:-----" + paltFormCustId);
			// 遍历消息逐个发送
			for (NoticeMsg msg : list) {
				try {
					// 如果是短信消息
					if (msg.getMsgType() == 0) {
						NoteBean noteBean = msg.bulidNoteBean();
						// 设置发送者
						AsCardHolder cardHolderInfo = cardHolderService
								.searchCardHolderInfoByCustId(msg.getCustId());
						if (cardHolderInfo == null || isBlank(cardHolderInfo.getMobile())) {
							updateFailNotieMsg(msg, "消费者没有绑定手机号码");
							continue;
						}
						// 设置短信消息接收人
						noteBean.setMsgReceiver(new String[] { cardHolderInfo.getMobile() });
						// 设置消息内容
						noteBean.setContent(buildMsgContent(msg));
						noteBean.setSender(paltFormCustId);
						logInfo("msgcontent:-----" + JSON.toJSONString(noteBean));
						ntService.sendNote(noteBean);
						// 更新消息发送状态
						updateSucessNotieMsg(msg);
						continue;
					}
					// 如果是推送互动消息
					DynamicBizMsgBean dynamicBizMsgBean = msg.bulidDynamicBizMsgBean();
					dynamicBizMsgBean.setContent(buildMsgContent(msg));
					dynamicBizMsgBean.setSender(paltFormCustId);
					logInfo("msgcontent:-----" + JSON.toJSONString(dynamicBizMsgBean));
					ntService.sendDynamicBiz(dynamicBizMsgBean);
					// 更新消息发送状态
					updateSucessNotieMsg(msg);
				} catch (Exception e) {
					logError(e.getMessage(), e);
					// 更新消息发送状态
					updateFailNotieMsg(msg, e.getMessage());
					continue;
				}
			}

			// 任务处理完成，上报进度给调度中心
			reportStatus(executeId, DSTaskStatus.SUCCESS, "积分福利通知消息服务：执行成功");
			saveLog(batchDate, 1, null);
			return true;
		} catch (HsException e) {
			logError(e.getMessage(), e);
			reportStatus(executeId, DSTaskStatus.FAILED, "积分福利通知消息服务：执行失败>" + e.getMessage());
			saveLog(batchDate, 0, e.getMessage());
			return false;
		} catch (Exception e) {
			logError(e.getMessage(), e);
			reportStatus(executeId, DSTaskStatus.FAILED, "积分福利通知消息服务：执行失败>" + e.toString());
			saveLog(batchDate, 0, e.getMessage());
			return false;
		}
	}

	private Map<String, String> buildMsgContent(NoticeMsg msg) {
		Map<String, String> map = new HashMap<String, String>();
		// {0}--消费者姓名
		map.put("{0}", msg.getCustName());
		// 如果是意外保障生效
		if (1 == msg.getBizType()) {
			WelfareQualification welfare = qualificationMapper.selectByPrimaryKey(msg
					.getWelfareId());
			// {1}--生效日期
			map.put("{1}", DateUtil.timestampToString(welfare.getEffectDate(), "yyyy-MM-dd"));
			// {2}--失效日期
			map.put("{2}", DateUtil.timestampToString(welfare.getFailureDate(), "yyyy-MM-dd"));
		}
		//如果是意外正常到期失效
		if (3 == msg.getBizType()) {
			WelfareQualification welfare = qualificationMapper.selectByPrimaryKey(msg
					.getWelfareId());
			// {1}--失效日期
			map.put("{1}", DateUtil.timestampToString(welfare.getFailureDate(), "yyyy-MM-dd"));
		}
		return map;
	}

	private void updateFailNotieMsg(NoticeMsg msg, String errorMsg) {
		msg.setSendStatus(3);
		msg.setFailMsg(errorMsg);
		msgMppper.updateByPrimaryKeySelective(msg);
	}

	private void updateSucessNotieMsg(NoticeMsg msg) {
		msg.setSendStatus(1);
		msg.setFailMsg("");
		msgMppper.updateByPrimaryKeySelective(msg);
	}

	private void saveLog(String executeDate, int status, String msg) {
		ExecuteLog log = ExecuteLog.bulidExecuteLog();
		log.setExecuteService("WelfareNoticeService");
		log.setStatus(status);
		log.setExecuteDate(executeDate);
		log.setCreateDate(new Timestamp(new Date().getTime()));
		log.setErrorDesc(msg);
		logMapper.insertSelective(log);
	}

	private void reportStatus(String executeId, int status, String desc) {
		logInfo("report status to scheduler center ,status: ----" + status + " desc:--" + desc);
		listener.reportStatus(executeId, status, desc);
	}

}
