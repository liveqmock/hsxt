package com.gy.hsxt.ws.service;

import static com.gy.hsxt.common.utils.DateUtil.DateTimeToString;
import static com.gy.hsxt.common.utils.StringUtils.getNowTimestamp;
import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.api.IWsClaimsAccountingService;
import com.gy.hsxt.ws.bean.ClaimsAccountingDetail;
import com.gy.hsxt.ws.bean.ClaimsAccountingInfo;
import com.gy.hsxt.ws.bean.ClaimsAccountingQueryCondition;
import com.gy.hsxt.ws.bean.ClaimsAccountingRecord;
import com.gy.hsxt.ws.bean.MedicalDetail;
import com.gy.hsxt.ws.bean.MedicalDetailInfo;
import com.gy.hsxt.ws.common.ClaimsAccountStatus;
import com.gy.hsxt.ws.common.WsErrorCode;
import com.gy.hsxt.ws.mapper.ClaimsAccountingInfoMapper;
import com.gy.hsxt.ws.mapper.MedicalDetailInfoMapper;

/**
 * 理赔核算 服务接口实现类
 * 
 * @Package: com.gy.hsxt.ws.service
 * @ClassName: ClaisAccountingService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-11 下午5:05:16
 * @version V1.0
 */
@Service
public class ClaisAccountingService extends BasicService implements IWsClaimsAccountingService {

	@Autowired
	private ClaimsAccountingInfoMapper claimsAccountingMapper;

	@Autowired
	private MedicalDetailInfoMapper medicalDetailMapper;

	/**
	 * 医疗明细核算
	 * 
	 * @param list
	 *            医疗明细列表
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsClaimsAccountingService#countMedicalDetail(java.util.List)
	 */
	@Transactional
	public void countMedicalDetail(List<MedicalDetail> list) throws HsException {
		counting(list, ClaimsAccountStatus.PROCESSING.getStatus());
	}

	private void counting(List<MedicalDetail> list, Integer status) {
		// 数据验证
		if (isBlank(list) || list.isEmpty()) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), "核算明细列表不能为null");
		}
		// 初始化理赔核算单 统计金额为 0
		ClaimsAccountingInfo claimsAccountingInfo = new ClaimsAccountingInfo();
		claimsAccountingInfo.setHealthPayAmount(new BigDecimal(0));
		claimsAccountingInfo.setPersonalPayAmount(new BigDecimal(0));
		claimsAccountingInfo.setHsPayAmount(new BigDecimal(0));
		// 遍历医疗明细列表
		List<MedicalDetailInfo> infoList = new ArrayList<MedicalDetailInfo>();
		for (MedicalDetail medicalDetail : list) {
			// 医疗明细数据验证
			medicalDetail.validParams();
			MedicalDetailInfo detailInfo = MedicalDetailInfo.buildMedicalDetailInfo(medicalDetail);
			infoList.add(detailInfo);
			claimsAccountingInfo.setAccountingId(detailInfo.getAccountingId());
			// 统计总金额、个人支付总金额、互生支付总金额
			claimsAccountingInfo.setHealthPayAmount(claimsAccountingInfo.getHealthPayAmount().add(
					detailInfo.getHealthPayAmount()));
			claimsAccountingInfo.setPersonalPayAmount(claimsAccountingInfo.getPersonalPayAmount()
					.add(detailInfo.getPersonalPayAmount()));
			claimsAccountingInfo.setHsPayAmount(claimsAccountingInfo.getHsPayAmount().add(
					detailInfo.getHsPayAmount()));
			
			claimsAccountingInfo.setBillsStartDate(medicalDetail.getBillsStartDate());
			claimsAccountingInfo.setBillsEndDate(medicalDetail.getBillsEndDate());
			claimsAccountingInfo.setProvinceNo(medicalDetail.getProvinceNo());
			claimsAccountingInfo.setCityNo(medicalDetail.getCityNo());
		}
		// 先删除后插入
		medicalDetailMapper.deleteByPrimaryKey(list.get(0).getAccountingId());
		// 医疗明细入库
		medicalDetailMapper.batchInsertMedicalDetailInfo(infoList);
		// 更新核算单
		claimsAccountingInfo.setStatus(status);
		claimsAccountingInfo.setAccountDate(getNowTimestamp());
		claimsAccountingMapper.updateByPrimaryKeySelective(claimsAccountingInfo);
	}

	/**
	 * 确认核算
	 * 
	 * @param list
	 *            医疗明细列表 必传
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsClaimsAccountingService#confrimClaimsAccounting(java.util.List)
	 */
	@Transactional
	public void confrimClaimsAccounting(List<MedicalDetail> list) throws HsException {
		counting(list, ClaimsAccountStatus.OVER.getStatus());
	}

	/**
	 * 查询已核算的 理赔核算单
	 * 
	 * @param queryCondition
	 *            查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsApprovalService#listHisClaimsAccounting(com.gy.hsxt.ws.bean.ClaimsAccountingQueryCondition,
	 *      com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<ClaimsAccountingRecord> listHisClaimsAccounting(
			ClaimsAccountingQueryCondition queryCondition, Page page) throws HsException {
		return pageClaimsAccounting(queryCondition, page, ClaimsAccountStatus.OVER.getStatus());
	}

	private PageData<ClaimsAccountingRecord> pageClaimsAccounting(
			ClaimsAccountingQueryCondition queryCondition, Page page, Integer... status) {
		// 数据验证
		validParamIsEmptyOrNull(page, "分页参数为空");

		// 查询总条数
		int count = claimsAccountingMapper
				.countClaimsAccounting(queryCondition, status);
		if (count == 0) {
			return null;
		}

		// 分页查询数据
		List<ClaimsAccountingInfo> list = claimsAccountingMapper.pageClaimsAccounting(
				queryCondition, page, status);

		// 构造返回数据类型
		List<ClaimsAccountingRecord> result = new ArrayList<ClaimsAccountingRecord>();
		for (ClaimsAccountingInfo claimsAccountingInfo : list) {
			ClaimsAccountingRecord record = new ClaimsAccountingRecord();
			BeanUtils.copyProperties(claimsAccountingInfo, record);
			if (claimsAccountingInfo.getHealthPayAmount() != null) {
				record.setHealthPayAmount(claimsAccountingInfo.getHealthPayAmount().toString());
			}
			if (claimsAccountingInfo.getPersonalPayAmount() != null) {
				record.setPersonalPayAmount(claimsAccountingInfo.getPersonalPayAmount().toString());
			}
			if (claimsAccountingInfo.getHsPayAmount() != null) {
				record.setHsPayAmount(claimsAccountingInfo.getHsPayAmount().toString());
			}
			if (claimsAccountingInfo.getAccountDate() != null) {
				record.setAccountDate(DateTimeToString(claimsAccountingInfo.getAccountDate()));
			}
			result.add(record);
		}

		return new PageData<>(count, result);
	}

	/**
	 * 查询待核算的 理赔核算单
	 * 
	 * @param queryCondition
	 *            查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsApprovalService#listPendingClaimsAccounting(com.gy.hsxt.ws.bean.ClaimsAccountingQueryCondition,
	 *      com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<ClaimsAccountingRecord> listPendingClaimsAccounting(
			ClaimsAccountingQueryCondition queryCondition, Page page) throws HsException {
		return pageClaimsAccounting(queryCondition, page, ClaimsAccountStatus.PENDING.getStatus(),
				ClaimsAccountStatus.PROCESSING.getStatus(), ClaimsAccountStatus.OVER.getStatus());
	}

	/**
	 * 查询理赔核算单 详情
	 * 
	 * @param accountingId
	 *            理赔核算单编号（主键） 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsClaimsAccountingService#queryClaimsAccountingDetail(java.lang.String)
	 */
	@Override
	public ClaimsAccountingDetail queryClaimsAccountingDetail(String accountingId)
			throws HsException {
		// 数据验证
		validParamIsEmptyOrNull(accountingId, " 理赔核算单编号不能为空");

		// 查询核算单信息
		ClaimsAccountingInfo claimsAccountingInfo = claimsAccountingMapper
				.selectByPrimaryKey(accountingId);
		ClaimsAccountingRecord claimsAccountingRecord = claimsAccountingInfo
				.generateClaimsAccountingRecord();

		// 查询医疗明细数据
		List<MedicalDetailInfo> detailInfoList = medicalDetailMapper
				.listMedicalDetailByAccountingId(accountingId);

		// 构造返回数据
		ClaimsAccountingDetail claimsAccountingDetail = new ClaimsAccountingDetail();
		BeanUtils.copyProperties(claimsAccountingRecord, claimsAccountingDetail);
		claimsAccountingDetail.setDetailList(covertToMedicalDetail(detailInfoList));

		return claimsAccountingDetail;
	}

	private List<MedicalDetail> covertToMedicalDetail(List<MedicalDetailInfo> list) {
		List<MedicalDetail> result = new ArrayList<MedicalDetail>();
		for (MedicalDetailInfo medicalDetailInfo : list) {
			MedicalDetail medicalDetail = medicalDetailInfo.generateMedicalDetail();
			result.add(medicalDetail);
		}
		return result;
	}

}
