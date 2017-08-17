/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.service.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.api.IOtherNotifyService;
import com.gy.hsxt.gpf.fss.bean.*;
import com.gy.hsxt.gpf.fss.constant.FssNotifyType;
import com.gy.hsxt.gpf.fss.constant.FssPurpose;
import com.gy.hsxt.gpf.fss.mapper.FileDetailMapper;
import com.gy.hsxt.gpf.fss.mapper.LocalNotifyMapper;
import com.gy.hsxt.gpf.fss.mapper.RemoteNotifyMapper;
import com.gy.hsxt.gpf.fss.service.CallbackService;
import com.gy.hsxt.gpf.fss.uf.RegionPacketSender;
import com.gy.hsxt.gpf.fss.utils.DirPathBuilder;
import com.gy.hsxt.gpf.fss.utils.FssDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知回调业务处理
 *
 * @Package :com.gy.hsxt.gpf.fss.service.impl
 * @ClassName : CallbackServiceImpl
 * @Description : 通知回调业务处理
 * @Author : chenm
 * @Date : 2015/10/30 14:35
 * @Version V3.0.0.0
 */
@Service("callbackService")
public class CallbackServiceImpl implements CallbackService {

    private Logger logger = LoggerFactory.getLogger(CallbackServiceImpl.class);

    @Resource
    private LocalNotifyMapper localNotifyMapper;

    @Resource
    private RemoteNotifyMapper remoteNotifyMapper;

    @Resource
    private FileDetailMapper fileDetailMapper;

    @Resource
    private PlatData platData;

    @Resource
    private IOtherNotifyService bmFssNotifyService;

    @Resource
    private IOtherNotifyService bsFssNotifyService;

    /**
     * 注入综合前置发送消息接口
     */
    @Resource
    private RegionPacketSender fileNotifySender;

    /**
     * 增值系统的系统代码
     */
    @Value("${bm.sys.code}")
    private String bmSysCode;

    /**
     * 服务映射地址
     */
    @Value("${nginx.server}")
    private String server;

    /**
     * 本地回调bean名称
     * key:子系统代码 value:bean名称
     */
    private Map<String, String> map = new HashMap<>();

    /**
     * 远程通知本地回调
     *
     * @param remoteNotify 远程通知
     * @return 处理结果
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean callbackLocalForRemote(RemoteNotify remoteNotify) throws HsException {
        //可以进一步思考，进行配置调用（不同的子系统通知，调用的不同的接口）
        LocalNotify localNotify = localNotifyMapper.selectOneById(remoteNotify.getNotifyNo());
        if (localNotify == null) {
            String now = FssDateUtil.obtainToday(FssDateUtil.DATE_TIME_FORMAT);
            localNotify = new LocalNotify();
            BeanUtils.copyProperties(remoteNotify, localNotify);
            localNotify.setNotifyType(FssNotifyType.REMOTE_BACK.getTypeNo());
            localNotify.setCreateTime(now);
            localNotify.setReceived(0);
            localNotifyMapper.insert(localNotify);
        }

        //查询所有文件详情
        List<FileDetail> details = fileDetailMapper.selectByNotifyNo(remoteNotify.getNotifyNo());
        localNotify.setDetails(details);

        if (specialCheck(localNotify)) {
            try {
                //调用本平台内子系统
                IOtherNotifyService otherNotifyService = getCallbackInterfaces(localNotify.getToSys());
                if (otherNotifyService != null) {
                    boolean success = otherNotifyService.fssSyncNotify(localNotify);
                    logger.info("=====远程通知本地回调:{} ======", success);
                    if (success) {
                        if (bmSysCode.equals(localNotify.getToSys()) && FssPurpose.BM_BMLM.getCode().equals(localNotify.getPurpose())) {
                            //更新上个月的所有再增值通知
                            QueryNotify queryNotify = new QueryNotify();
                            queryNotify.setNotifyStartDate(FssDateUtil.obtainMonthFirstDay(FssDateUtil.PREVIOUS_MONTH, FssDateUtil.DEFAULT_DATE_FORMAT));
                            queryNotify.setNotifyEndDate(FssDateUtil.obtainMonthFirstDay(FssDateUtil.THIS_MONTH, FssDateUtil.DEFAULT_DATE_FORMAT));
                            queryNotify.setToPlat(localNotify.getToPlat());
                            queryNotify.setToSys(localNotify.getToSys());
                            queryNotify.setPurpose(localNotify.getPurpose());
                            int count = localNotifyMapper.updateForBmlm(queryNotify);
                            logger.info("==========本次再增值通知影响记录数:{} ===========", count);
                        } else {
                            localNotify.setReceived(1);
                            localNotifyMapper.update(localNotify);
                        }
                    }
                } else {
                    logger.info("==========远程通知本地[{}]回调服务[otherNotifyService]不存在 ===========", localNotify.getToSys());
                    return false;
                }
            } catch (Exception e) {
                logger.error("========远程通知本地回调异常=====", e);
                return false;
            }
        }
        return true;
    }

    /**
     * 如果是增值系统的文件，需要全平台通知的查收
     *
     * @param localNotify 本地通知
     * @return 处理结果
     */
    private boolean specialCheck(LocalNotify localNotify) {
        //再增值积分处理，特殊对待
        if (bmSysCode.equals(localNotify.getToSys()) && FssPurpose.BM_BMLM.getCode().equals(localNotify.getPurpose())) {
            QueryNotify queryNotify = new QueryNotify();
            queryNotify.setNotifyStartDate(FssDateUtil.obtainMonthFirstDay(FssDateUtil.PREVIOUS_MONTH, FssDateUtil.DEFAULT_DATE_FORMAT));
            queryNotify.setNotifyEndDate(FssDateUtil.obtainMonthFirstDay(FssDateUtil.THIS_MONTH, FssDateUtil.DEFAULT_DATE_FORMAT));
            queryNotify.setToPlat(localNotify.getToPlat());
            queryNotify.setToSys(localNotify.getToSys());
            queryNotify.setPurpose(localNotify.getPurpose());
            List<RemoteNotify> notifies = remoteNotifyMapper.selectByOther(queryNotify);
            Collection<String> platCodes = platData.obtainAllCode();
            //账务系统和业务系统都要发送通知
            int platCount = (platCodes.size()) * 2;
            logger.info("=====平台数[{}],再增值文件通知数[{}]=====", platCodes.size(), notifies.size());
            if (platCount != notifies.size()) {
                return false;
            } else {
                //真实匹配的平台通知数
                int realCount = 0;
                for (String platCode : platCodes) {
                    for (RemoteNotify notify : notifies) {
                        if (notify.getFromPlat().equals(platCode)) {
                            realCount++;
                            //把剩余的文件详情全部装载
                            if (!notify.getNotifyNo().equals(localNotify.getNotifyNo())) {
                                List<FileDetail> details = fileDetailMapper.selectByNotifyNo(notify.getNotifyNo());
                                //添加文件详情
                                localNotify.getDetails().addAll(details);
                                //增加文件数量
                                localNotify.setFileCount(details.size() + localNotify.getFileCount());
                            }
                        }
                    }
                }
                logger.info("=====真实匹配的平台通知数[{}]=====", realCount);
                return realCount == platCount;
            }
        }
        return true;
    }

    /**
     * 本地通知本地回调
     *
     * @param localNotify 本地通知
     * @return 处理结果
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean callbackLocalForLocal(LocalNotify localNotify) throws HsException {
        //根据子系统代码和用途查询对应回调接口信息
        List<FileDetail> details = fileDetailMapper.selectByNotifyNo(localNotify.getNotifyNo());
        localNotify.setDetails(details);

        //设置为本地回调
        localNotify.setNotifyType(FssNotifyType.LOCAL_BACK.getTypeNo());

        //调用本平台内子系统
        IOtherNotifyService otherNotifyService = getCallbackInterfaces(localNotify.getToSys());
        if (otherNotifyService != null) {
            boolean success = otherNotifyService.fssSyncNotify(localNotify);
            logger.info("=====通知编号[{}]---本地通知本地回调:{} ======", localNotify.getNotifyNo(), success);
            return success;
        } else {
            logger.info("==========本地通知本地[{}]回调服务[otherNotifyService]不存在 ===========", localNotify.getToSys());
            return false;
        }
    }

    /**
     * 本地通知远程回调
     *
     * @param localNotify 本地通知
     * @return 处理结果
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean callbackRemoteForLocal(LocalNotify localNotify) throws HsException {

        //本地文件上传并校验之后，通知地区平台下载(综合前置转发)
        RemoteNotify remoteNotify = remoteNotifyMapper.selectOneById(localNotify.getNotifyNo());
        if (remoteNotify == null) {
            String now = FssDateUtil.obtainToday(FssDateUtil.DATE_TIME_FORMAT);
            remoteNotify = new RemoteNotify();
            BeanUtils.copyProperties(localNotify, remoteNotify);
            remoteNotify.setNotifyType(FssNotifyType.DOWNLOAD_MD5.getTypeNo());
            remoteNotify.setCreateTime(now);
            remoteNotify.setReceived(0);
            remoteNotifyMapper.insert(remoteNotify);
        }

        //查询所有文件详情
        List<FileDetail> details = fileDetailMapper.selectByNotifyNo(localNotify.getNotifyNo());
        //处理一下文件地址
        for (FileDetail detail : details) {
            detail.setSource(server + File.separator + DirPathBuilder.UPLOAD_DIR + StringUtils.substringAfter(detail.getSource(), DirPathBuilder.UPLOAD_DIR));
            detail.setTarget("");
        }
        remoteNotify.setDetails(details);

        //调用综合前置向目标平台发送通知
        try {
            boolean success = fileNotifySender.sendRegionPacketData(remoteNotify);
            if (success) {
                remoteNotify.setReceived(1);
                remoteNotifyMapper.update(remoteNotify);
            }
        } catch (HsException e) {
            logger.error("====本地通知远程回调异常====", e);
            return false;
        }
        return true;
    }

    /**
     * 远程通知远程回调
     * 一般为处理完远程通知之后，给通知一个异步回复
     *
     * @param remoteNotify 远程通知
     * @return 处理结果
     * @throws HsException
     */
    @Override
    public boolean callbackRemoteForRemote(RemoteNotify remoteNotify) throws HsException {

        ResultNotify resultNotify = new ResultNotify();
        //回复远程通知，通知编号与远程通知编号一致
        resultNotify.setNotifyNo(remoteNotify.getNotifyNo());
        resultNotify.setNotifyType(FssNotifyType.REMOTE_RESULT.getTypeNo());

        //源平台和目的平台要互换
        resultNotify.setFromPlat(remoteNotify.getToPlat());
        resultNotify.setToPlat(remoteNotify.getFromPlat());

        //处理完成时间
        resultNotify.setAllCompleted(remoteNotify.getAllCompleted());
        resultNotify.setCompletedTime(remoteNotify.getCompletedTime());

        //处理完成并不代表文件完整，需要看所有文件是否通过校验
        resultNotify.setAllPass(remoteNotify.getAllPass());
        resultNotify.setRemark(remoteNotify.getRemark());

        logger.debug("=====远程通知远程回调:{}====", resultNotify);

        //调用综合前置向目标平台发送通知
        return fileNotifySender.sendRegionPacketData(resultNotify);
    }

    /**
     * 获取回调接口
     *
     * @throws HsException
     */
    private IOtherNotifyService getCallbackInterfaces(String toSysCode) throws HsException {
        IOtherNotifyService otherNotifyService = null;
        if (StringUtils.isNotEmpty(toSysCode)) {
            if ("BM".equals(toSysCode.toUpperCase())) {
                otherNotifyService = bmFssNotifyService;
            }
            if ("BS".equals(toSysCode.toUpperCase())) {
                otherNotifyService = bsFssNotifyService;
            }
        }
        return otherNotifyService;
    }
}
