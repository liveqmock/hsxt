package com.gy.hsxt.access.pos.service;

import java.util.Map;

import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.Cmd;


/**
 * 公共service
 * @author kend
 *
 */
public interface CommonService {
	
	/**
	 * 从请求报文中识别交易卡号输入方式，用相应方法提取消费者互生卡号
	 * @param cmd
	 * @return Map<String, String> cardNoAndCode
	 * 	其中有至多两个值.
	 * 		key = "cardNo":消费者互生卡号,11位；
	 * 		key = "cardCode":互生卡暗码,11位。只有刷卡交易有。
	 * @throws Exception 卡号和暗码长度校验不通过则抛出异常
	 */
	public Map<String, String> getCustomerHscardNo(Cmd cmd) throws Exception;
	
	
	
	/**
	 * pos机扫码得到的交易凭据（交易流水号）写入62域
	 * “交易凭据二维码”字符串原始定义：(2位字母数字)二维码类型&(2位字母数字)交易类别&12位数字的交易流水号明文。
	 * 为便于以后扩展，这里不做严格校验
	 * @param _62area 
	 * @return Map中有两个值：
	 * 						BizType：业务类型
	 * 						BizSeq：业务序列号(均已做非空校验)
	 * @throws PosException
	 */
	public Map<String, String> getSeqNTradeTypeFromQr(String _62area) throws PosException;
	
	/**
	 * pos机扫码得到的二维码(交易凭据二维码,交易单据二维码)
	 * @param _62area 
	 * @throws PosException
	 */
	public Object getQrCode(String _62area) throws PosException;
	
	/**
	 * added by liuzh on 2016-06-22 
	 * pos机扫码得到的二维码(交易凭据二维码,交易单据二维码)
	 * @param _62area 
	 * @param pversion 软件分版本
	 * @throws PosException
	 */
	public Object getQrCode(String _62area,byte[] pversion) throws PosException;
	
	/**
	 * 调用综合前置服务，发起跨平台交易请求
	 * @param param 交易参数类，如Point、Cancel等
	 * @param custHscardNo 消费者互生号，用于地方平台路由识别
	 * @param instructCode 交易指令，需与异地平台的对应响应系统设置保持一致
	 * @return 接受请求的系统职能模块定义的响应类
	 * 可能抛出的互生异常：
	 	* 错误码	说明<br>
		* 17000	请求参数无效<br>
		* 17001	非跨地区平台报文,不得通过UF转发<br>
		* 17100	综合前置内部处理异常<br>
		* 17101	综合前置查询路由目标地址失败<br>
		* 17102	综合前置没有找到路由目标地址<br>
		* 17103	综合前置没有找到目标业务子系统<br>
		* 17201	综合前置处理失败<br>
		* 17202	内部系统与综合前置通信失败<br>
		* 17203	综合前置与目标平台综合前置通信失败<br>
		* 17204	该报文无法被目标平台综合前置识别<br>
		* 17205	该报文不属于目标平台<br>
		* 17206	目标平台处理该报文时发生错误<br>
	 * @throws PosException
	 */
	public Object sendCrossPlatformIndicate(Object param, String custHscardNo, 
				String instructCode) throws Exception;
	
	
	/**
	 * 根据互生异常编码识别隶属的分系统
	 * @param code
	 * @return
	 */
	public String checkSubSysByHsErrCode(Integer code);

}
