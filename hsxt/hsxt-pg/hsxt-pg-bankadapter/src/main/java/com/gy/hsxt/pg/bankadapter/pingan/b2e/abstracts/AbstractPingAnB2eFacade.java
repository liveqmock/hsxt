/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.pingan.b2e.abstracts;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.RemoteCaller;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.HeaderDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.TradeCode;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageDTO;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.pingan.b2e.abstracts
 * 
 *  File Name       : AbstractPingAnB2eFacade.java
 * 
 *  Creation Date   : 2014-11-4
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : AbstractPingAnB2eFacade
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class AbstractPingAnB2eFacade {
	protected final Logger logger = Logger.getLogger(this.getClass());

	private Random random = new Random();

	// 复合一个调用者实例，方便根据不同的策略进行切换
	private RemoteCaller remoteCaller;

	// 默认的企业代码，在配置文件中设置
	private String companyCode = "00203030000000037000";

	public AbstractPingAnB2eFacade() {
	}

	public void setRemoteCaller(RemoteCaller caller) {
		this.remoteCaller = caller;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 根据银行的接口规范发送请求报文给银行
	 * 
	 * @param tradeCode
	 * @param body
	 * @param sequence
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	protected PackageDTO performCall(TradeCode tradeCode, BodyDTO body, String sequence)  {
		// 设置报文头
		HeaderDTO header = null;

		try {
			byte[] xmlBytes = body.obj2xml().getBytes("UTF-8");

			header = new HeaderDTO.Builder(tradeCode.getValue(), xmlBytes.length, 0)
					.setCompanyCode(companyCode).setReqSequence(sequence).build();

			if (logger.isDebugEnabled()) {
				logger.debug(tradeCode.getValue() + "body length=" + xmlBytes.length);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("performCall method UnsupportedEncodingException error:", e);
		}

		// 封装包
		PackageDTO reqDTO = new PackageDTO.Builder(header, body, null).Build();

		if (logger.isDebugEnabled()) {
			logger.debug(tradeCode.getValue() + ": performDTO reqDTO=" + reqDTO);
		}

		// 调用底层的网络接口，向银行发送消息，并等待银行的返回
		PackageDTO repDTO = remoteCaller.performCall(reqDTO);

		if (logger.isDebugEnabled()) {
			logger.debug(tradeCode.getValue() + ": performDTO repDTO=" + repDTO);
		}

		return repDTO;
	}

	/**
	 * 生成银行接口的请求流水号
	 * 
	 * @param sources
	 * @return
	 */
	protected String generateReqSequence(String... sources) {
		String sequence = "";

		for (String source : sources) {
			sequence = StringHelper.leftPad(source, 20, '0');

			if (!StringUtils.isEmpty(sequence)) {
				return sequence;
			}
		}
		
		String randomStr = (System.currentTimeMillis() + String
				.valueOf(random.nextLong())).replaceAll("[^\\d]+", "");

		return StringHelper.leftPad(randomStr + "", 20, '0');
	}
}
