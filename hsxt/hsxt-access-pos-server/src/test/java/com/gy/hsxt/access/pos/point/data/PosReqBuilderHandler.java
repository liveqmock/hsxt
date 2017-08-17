package com.gy.hsxt.access.pos.point.data;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.util.PosUtil;

@Component("reqBuilder")
public class PosReqBuilderHandler {

	Logger log = LoggerFactory.getLogger(PosReqBuilderHandler.class);
	
	@Value("${netty.port}")
	public int nettyPort;
	
	@Value("${key.host}")
	public String keyHost;
	
//	@Value("{key.port}")
//	public int keyPort;
	
	@Autowired
	@Qualifier("ucApiService")
	private UcApiService ucApiService;
	
	
	/**
	 * 只用来和KeyServer通讯的。 GengLian added at 2014/11/18
	 */
//	private SocketClient keyServerClient;
	private SocketClient posServerClient;
	
	@PostConstruct
	public void init(){
		posServerClient = new SocketClient("127.0.0.1", nettyPort);
//		keyServerClient = new SocketClient(keyHost, 18087);
	}
	
	
	public byte[] sendPubParam(byte[] messageTypeId, String[] messageBodyMap, String entNo, //
			String posNo, String operNo, byte[] pversion) throws Exception {
		
		Assert.notNull(messageBodyMap, "case8583.xml is NOT xml.");
		
		Assert.isTrue(messageBodyMap[11].length() == 6, "终端流水号长度输入错误,停止发送!");
		
		int count;
		StringBuilder bitStr = new StringBuilder(160); 

		StringBuilder messageBody = new StringBuilder(160);
		for (int k = 1; k < messageBodyMap.length; k++) { // 根据实情，就是从1开始的。这里大大修改了，原来的Map-->String[]
			if (null == messageBodyMap[k]) {
				bitStr.append(PosConstant.ZERO_CHAR);
			} else {
				bitStr.append(PosConstant.ONE_CHAR);
				messageBody.append(messageBodyMap[k]);
			}
		}
		
		bitStr.append(Arrays.equals(PosConstant.POINTTYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.POINTREVERSETYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.CARDCHECKTYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.HSPAYTYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.HSCANCLETYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.HSRETURNTYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.HSORDERTYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.HSBCRECTYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.HSBBRECTYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.POINTSEARCHTYPEREQ, messageTypeId) ? PosConstant.ONE_CHAR : PosConstant.ZERO_CHAR); // 积分/撤单/卡校验 补一位
		
		final byte[] newBitByte = new BinaryCodec().toByteArray(bitStr.toString());
		ArrayUtils.reverse(newBitByte);
	
		final byte[] messageBodyByte = Hex.decodeHex(messageBody.toString().toCharArray());
		final byte[] requestMessageBody = new byte[newBitByte.length + messageBodyByte.length];
		
		System.arraycopy(newBitByte, 0, requestMessageBody, 0, newBitByte.length);
		System.arraycopy(messageBodyByte, 0, requestMessageBody, newBitByte.length, messageBodyByte.length);

		final byte[] b = new byte[PreData.fixHeadByteValue().length + messageTypeId.length
				+ requestMessageBody.length];
		
		count = 0;
		if (!Arrays.equals(pversion, PosConstant.POSOLDVERSION)) {//新版本
			System.arraycopy(PreData.fixHeadByteValue(), 0, b, count, PreData.fixHeadByteValue().length);
		} else {
			System.arraycopy(PreData.fixHeadByteValue(), 0, b, count, PreData.fixHeadByteValue().length);
		}
		
		count += PreData.fixHeadByteValue().length;
		System.arraycopy(messageTypeId, 0, b, count, messageTypeId.length);
		
		count += messageTypeId.length;
		System.arraycopy(requestMessageBody, 0, b, count, requestMessageBody.length);
		byte[] dataByte, newMAC = null;
		
		if (Arrays.equals(PosConstant.POINTTYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.POINTREVERSETYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.CARDCHECKTYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.HSPAYTYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.HSCANCLETYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.HSRETURNTYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.HSORDERTYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.HSBCRECTYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.HSBBRECTYPEREQ, messageTypeId)
				|| Arrays.equals(PosConstant.POINTSEARCHTYPEREQ, messageTypeId)) {//积分/撤单/卡校验
			
			byte[] macDataByte = new byte[messageTypeId.length + requestMessageBody.length];
			
			int countk = 0;
			System.arraycopy(messageTypeId, 0, macDataByte, countk, messageTypeId.length);
			
			countk += messageTypeId.length;
			System.arraycopy(requestMessageBody, 0, macDataByte, countk, requestMessageBody.length);

			try
            {
			    log.debug("加密报文数据============" + Hex.encodeHexString(macDataByte));
                //模拟生成mac
                newMAC = ucApiService.genMac(entNo, posNo, macDataByte);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            //			newMAC = getMAC(entNo, posNo, operNo, messageTypeId, requestMessageBody, pversion);
			Assert.state(null != newMAC, " 2 svc.getMAC() has error. ");
			log.debug("pos mac-------" + Hex.encodeHexString(newMAC));

			assertEquals(8, newMAC.length);
			
			dataByte = new byte[b.length + newMAC.length];
			count = 0;
			System.arraycopy(b, 0, dataByte, count, b.length);
			count = b.length;
			System.arraycopy(newMAC, 0, dataByte, count, newMAC.length);
			
		} else {
			dataByte = b;
		}
		
		System.out.println("test sendpack dataByte:" + new String(Hex.encodeHex(dataByte)));
		final byte[] returnByte = posServerClient.send(dataByte);
		
		Assert.state(null != returnByte, "Reason: 1 Netty not started"
				+ " 2 ServerHandler finally{} has error.");
		
		byte[] receiveByte = Arrays.copyOfRange(returnByte, PreData.fixHeadByteValue().length, returnByte.length);
		return receiveByte;
	}
	
	
	final static String xmlFilePath = "src/test/resources/case8583.xml";
	final static String DEFAULT_RUNCODE = "000001";
	final static String DEFAULT_BATCH = "000001";
	final static String DEFAULT_RUNCODE1 = "100001";
	final static String DEFAULT_BATCH1 = "100001";
	final static String PROMPT_ERROR = "确保这个文件存在：" + xmlFilePath + "。而且，它应该是svn ignore的，要保障它的序号不重复。";

	public String getPosRunCode(String posNo) {
		int posRunCode;
		String posRunCodeStr = null;
		try {
			final File file = new File(xmlFilePath);
			final GuiDefinition gui = Util.unmarshall(file, GuiDefinition.class);
			final List<Pos> posList = gui.getPosList();
			boolean isExists = false;
			for (final Pos pos : posList) {
				if (posNo.equals(pos.getPosNo())) {
					
					isExists = true;
					posRunCode = Integer.parseInt(pos.getPosRunCode());
					if (Util.POS_RUN_CODE_AND_BATCH_NO_MAX == posRunCode) {
						posRunCode = 0;
					}
					posRunCodeStr = StringUtils.leftPad(String.valueOf(++posRunCode), 6, PosConstant.ONE_CHAR);
					pos.setPosRunCode(posRunCodeStr);
					break;
				}
			}
			if(!isExists){
				Pos p = new Pos(posNo, "000001", "000001");
				posList.add(p);
			}
			Util.marshall(gui, file, GuiDefinition.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(PROMPT_ERROR);
		}
		return posRunCodeStr;
		
	}
	
	/**
	 * 
	 * @author   wucl	
	 * 2015-11-9 下午2:54:09
	 * @param entNo
	 * @param posNo
	 * @param operNo
	 * @param pwd
	 * @param cardNo
	 * @return
	 */
	public String getPackPin(String entNo, String posNo, String operNo, String cardNo, String pwd) {
		
		final byte[] pwdByte = PosUtil.encryptWithANSIFormat(pwd, cardNo);
		log.debug("===========调用用户中心加密密码");
		
		byte[] encryData = null;
        try
        {
            encryData = ucApiService.getEncrypt(posNo, entNo, pwdByte);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // TODO: handle exception
        }
		
		Assert.notNull(encryData,"key server exception!");
		@SuppressWarnings("deprecation")
		String packPin = CommonUtil.byte2HexStr(encryData);
		return packPin;
	}
	
	
	
	/**
	 * 2014/11/24 目前都是4位向2位转，以后新KeyServer上来了，就要修改这个函数了。
	 */
	public String checkPosNo(String posNo) {
		
		return (2 < posNo.length() ? StringUtils.right(posNo, 2) : posNo);
	}
}
