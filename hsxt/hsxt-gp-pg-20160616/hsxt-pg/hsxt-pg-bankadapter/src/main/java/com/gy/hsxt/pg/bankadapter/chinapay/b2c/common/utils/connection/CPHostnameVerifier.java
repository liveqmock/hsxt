package com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.utils.connection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

class CPHostnameVerifier implements HostnameVerifier {
	public CPHostnameVerifier() {
	}

	public boolean verify(String hostname, SSLSession session) {
		return true;
	}
}
