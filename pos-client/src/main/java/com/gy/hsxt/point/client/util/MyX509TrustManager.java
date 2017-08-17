package com.gy.hsxt.point.client.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * SSL证书
 * 
 * @Package: com.gy.point.client.util
 * @ClassName: MyX509TrustManager
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-12-18 下午2:58:41
 * @version V1.0
 */
public class MyX509TrustManager implements X509TrustManager {

	String cnName;

	MyX509TrustManager(String cnName) {
		this.cnName = cnName;
	}

	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}

	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		if (chain == null || chain.length == 0)
			throw new IllegalArgumentException("非法证书链");
		if (authType == null || authType.length() == 0)
			throw new IllegalArgumentException("非法认证类型");

		boolean rc = false;
		java.security.Principal principal = null;
		for (X509Certificate aChain : chain) {
			principal = aChain.getSubjectDN();
			if (principal != null
					&& GetPrincipalCN(principal.getName()).equals(cnName)) {
				rc = true;
				break;
			}
		}
		if (!rc)
			throw new IllegalArgumentException("非法名称");
	}

	private String GetPrincipalCN(String Name) {
		int begin = Name.indexOf("CN=");
		int end = Name.indexOf(',', begin);
		String rc = Name.substring(begin + 3, end);
		return rc;
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		if (chain == null || chain.length == 0)
			throw new IllegalArgumentException("非法证书链");
		if (authType == null || authType.length() == 0)
			throw new IllegalArgumentException("非法认证类型");

		boolean rc = false;
		java.security.Principal principal = null;
		for (X509Certificate aChain : chain) {
			principal = aChain.getSubjectDN();
			if (principal != null
					&& GetPrincipalCN(principal.getName()).equals(cnName)) {
				rc = true;
				break;
			}
		}
		if (!rc)
			throw new IllegalArgumentException("非法名称");
	}
}
