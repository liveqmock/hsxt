package com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.sdk.util;

import java.util.Map;

/**
 * 类名：接口处理核心类
 * 功能：组转报文请求，发送报文，解析应答报文
 * 版本：1.0
 * 日期：2012-10-11
 * 作者：中国银联UPMP团队
 * 版权：中国银联
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */
public class UpmpService{
		
	/**
	 * 交易查询处理
	 * @param req 请求要素
	 * @param resp 应答要素
	 * @return 是否成功
	 */
	public static boolean query(String queryUrl, Map<String, String> req, Map<String, String> resp,String md5Key){
		String nvp = buildReq(req, md5Key);
		String respString = HttpUtil.post(queryUrl, nvp);
		return verifyResponse(respString, resp, md5Key);
	}
	
    /**
     * 拼接保留域
     * @param req 请求要素
     * @return 保留域
     */
    public static String buildReserved(Map<String, String> req) {
        StringBuilder merReserved = new StringBuilder();
        merReserved.append("{");
        merReserved.append(UpmpCore.createLinkString(req, false, true));
        merReserved.append("}");
        return merReserved.toString();
    }
	
	/**
	 * 拼接请求字符串
	 * @param req 请求要素
	 * @param resp 应答要素
	 * @return 请求字符串
	 */
	public static String buildReq(Map<String, String> req,String md5Key) {
	    // 除去数组中的空值和签名参数
        Map<String, String> filteredReq = UpmpCore.paraFilter(req);
		// 生成签名结果
		String signature = UpmpCore.buildSignature(filteredReq, md5Key);

		// 签名结果与签名方式加入请求提交参数组中
		filteredReq.put("signature", signature);
		filteredReq.put("signMethod", "MD5");

		return UpmpCore.createLinkString(filteredReq, false, true);
	}
	
    /**
     * 异步通知消息验证
     * @param para 异步通知消息
     * @return 验证结果
     */
    public static boolean verifySignature(Map<String, String> para,String md5Key) {
        String respSignature = para.get("signature");
        // 除去数组中的空值和签名参数
        Map<String, String> filteredReq = UpmpCore.paraFilter(para);
        String signature = UpmpCore.buildSignature(filteredReq,md5Key);
        if (null != respSignature && respSignature.equals(signature)) {
			return true;
		}else {
            return false;
        }
    }
	
	/**
	 * 应答解析
	 * 
	 * @param respString 应答报文
	 * @param resp 应答要素
	 * @return 应答是否成功
	 */
	public static boolean verifyResponse(String respString, Map<String, String> resp,
			String md5Key) {
		if ((respString != null) && (!"".equals(respString))) {
			// 请求要素
			Map<String, String> para;
			
			try {
				para = UpmpCore.parseQString(respString);
			} catch (Exception e) {
				return false;
			}
			
			boolean signIsValid = verifySignature(para, md5Key);

			if (null != resp) {
				resp.putAll(para);
			}

			return signIsValid;
		}
		return false;
	}
}
	