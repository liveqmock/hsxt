package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageDTO;
/**
 * BIO方式与网关通信
 * @author jbli
 */
//@Component("bioCaller")  
public class BioCaller  implements RemoteCaller{
	private static final Logger logger = Logger.getLogger(BioCaller.class);
   
	//@Value("${bank.erp.gw.port}")  
	private String port = "7070";
	
	//@Value("${bank.erp.gw.ip}")  
	private String servAddress = "192.168.229.250";
	
	public void setPort(String port) {
		this.port = port;
	}

	public void setServAddress(String servAddress) {
		this.servAddress = servAddress;
	}

	@Override
	public PackageDTO performCall(PackageDTO packageDTO) {
		PackageDTO returnedPackageDTO = null;
		OutputStream out = null;
		InputStream in = null;
		Socket socket = null;

		try {
			SocketAddress endpoint = new InetSocketAddress(servAddress,
					Integer.parseInt(port));
			SocketChannel sc = SocketChannel.open();
			sc.configureBlocking(true); // bio
			socket = sc.socket();
			socket.setSoTimeout(60 * 1000); // 读超时为60秒
			sc.connect(endpoint);
			out = socket.getOutputStream();
			packageDTO.write(out);
			out.flush();
		} catch (Exception e) {
			logger.error(e);

			throw new BankAdapterException(ErrorCode.SOCKET_ERROR,
					"支付网关与平安银行SCP通信异常!");
		}

		try {
			in = socket.getInputStream();
			returnedPackageDTO = PackageDTO.getInstance(in, null);
		} catch (Exception e) {
			logger.error(e);

			throw new BankAdapterException(ErrorCode.SOCKET_ERROR,
					"支付网关读取平安银行响应报文发生异常!");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}

		if (returnedPackageDTO == null
				|| returnedPackageDTO.getHeader() == null
				|| returnedPackageDTO.getHeader().getReqSequence() == null) {
			logger.error("returnedPackageDTO is null! returnedPackageDTO = "
					+ returnedPackageDTO);
			
			return null;
		}
		
		String reqSequence = packageDTO.getHeader().getReqSequence().trim();
		String respReqSequence = returnedPackageDTO.getHeader()
				.getReqSequence().trim();

		if (!reqSequence.equals(respReqSequence)) {
			logger.error("reponse reqSequence is not equals request's. "
					+ " request='" + reqSequence + "' reponse='"
					+ respReqSequence + "'");
			
			return null;
		}

		return returnedPackageDTO;
	}
}
