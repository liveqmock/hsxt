package com.gy.hsxt.ws.mapper;

import java.util.List;

import com.gy.hsxt.ws.bean.MedicalDetailInfo;

/**
 * 医疗明细信息 Mapper
 * 
 * @Package: com.gy.hsxt.ws.mapper
 * @ClassName: MedicalDetailInfoMapper
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 下午3:29:08
 * @version V1.0
 */
public interface MedicalDetailInfoMapper {
	/**
	 * 删除医疗明细记录
	 * 
	 * @param accountingId
	 *            理赔核算单编号
	 * @return
	 */
	int deleteByPrimaryKey(String accountingId);

	/**
	 * 修改医疗明细记录
	 * 
	 * @param record
	 *            医疗明细记录
	 * @return
	 */
	int updateByPrimaryKeySelective(MedicalDetailInfo record);

	/**
	 * 批量插入医疗明细记录
	 * 
	 * @param list
	 *            医疗明细记录列表
	 * @return
	 */
	int batchInsertMedicalDetailInfo(List<MedicalDetailInfo> list);

	/**
	 * 查询医疗明细 通过核算单编号
	 * 
	 * @param accountingId
	 *            核算单编号
	 * @return
	 */
	List<MedicalDetailInfo> listMedicalDetailByAccountingId(String accountingId);
}