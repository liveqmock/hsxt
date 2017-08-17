/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service.impl;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.gp.common.bean.Counter;
import com.gy.hsxt.gp.common.constant.ConfigConst;
import com.gy.hsxt.gp.common.constant.Constant.SeqType;
import com.gy.hsxt.gp.common.utils.DateUtils;
import com.gy.hsxt.gp.common.utils.StringUtils;
import com.gy.hsxt.gp.mapper.TGpSequenceMapper;
import com.gy.hsxt.gp.mapper.vo.TGpSequence;
import com.gy.hsxt.gp.service.ISequenceService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service.impl
 * 
 *  File Name       : SequenceService.java
 * 
 *  Creation Date   : 2016年5月27日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 序列号接口实现
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("sequenceService")
public class SequenceService implements ISequenceService {

	private static final String INSTANCE_NO = HsPropertiesConfigurer
			.getProperty(ConfigConst.SYSTEM_INSTANCE_NO);

	private static final String FMT_YYMMDD = "yyMMdd";

	private static final long DEFAULT_VALUE = 1;

	private static final Lock UPOP_LOCK = new ReentrantLock();

	@Autowired
	private TGpSequenceMapper sequenceMapper;

	@Override
	public String getNextSequence(int seqType) {
		return null;
	}

	@Override
	public String getNextSeq4UpopBinding() {

		String nextSeqNumber;

		try {
			UPOP_LOCK.lock();

			// 查询上一个编号
			String oldSeqNumber = sequenceMapper.selectCurrSeqNumber(
					SeqType.UPOP_BINDING, INSTANCE_NO);

			// 计数下一个编号
			nextSeqNumber = increaseUpopBindingSeq(oldSeqNumber, INSTANCE_NO);

			TGpSequence record = new TGpSequence();
			record.setSeqType(SeqType.UPOP_BINDING);
			record.setSysInstanceNo(INSTANCE_NO);
			record.setSeqNumber(nextSeqNumber);
			record.setCreatedDate(new Date());

			if (0 >= sequenceMapper.updateSequence(record)) {
				sequenceMapper.insertSequence(record);
			}
		} catch (Exception ex) {
			nextSeqNumber = StringUtils.joinString(INSTANCE_NO,
					System.currentTimeMillis() + "");

			nextSeqNumber = StringUtils.cut2SpecialLength(nextSeqNumber, 16);
		} finally {
			UPOP_LOCK.unlock();
		}

		return nextSeqNumber;
	}

	/**
	 * 从缓存中获取Counter对象并自增(最大16位)
	 * 
	 * @param currValue
	 * @param instanceNo
	 * @return
	 */
	private String increaseUpopBindingSeq(String currValue, String instanceNo) {

		long startValue = 0L;

		String currDate = DateUtils.dateToString(DateUtils.getCurrentDate(),
				FMT_YYMMDD);

		if (!StringUtils.isEmpty(currValue)) {
			int dateLen = FMT_YYMMDD.length();

			// 判断上一次编号日期跟当前日期是否一致,如果不一致则重置为今天日期
			if (currValue.substring(0, dateLen).equals(currDate)) {
				int prefixLen = dateLen + instanceNo.length();

				startValue = StringUtils.str2Long(
						currValue.substring(prefixLen), DEFAULT_VALUE);
			}
		}

		// 递增加1
		long nextValue = new Counter(DEFAULT_VALUE, 9999_9999L)
				.doIncreasingFrom(startValue);

		return String.format("%s%s%08d", currDate, instanceNo, nextValue);
	}

	public static void main(String[] args) {

		String currValue = "1605300100000005";

		for (int i = 0; i < 200; i++) {
			currValue = new SequenceService().increaseUpopBindingSeq(currValue,
					"01");
			System.out.println(currValue);
		}

		System.out.println(System.currentTimeMillis());
	}
}
