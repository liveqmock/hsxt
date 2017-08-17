package com.gy.hsxt.access.pos.validation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.CommonService;
import com.gy.hsxt.access.pos.service.LcsClientService;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.util.PosUtil;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.device.AsDevCheckParam;
import com.gy.hsxt.uc.as.bean.device.AsDevCheckResult;
import com.gy.hsxt.uc.as.bean.device.AsPos;
import com.gy.hsxt.uc.as.bean.device.AsDevice.AsDeviceStatusEnum;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;


/**
 * 签到状态、mac、企业信息、卡信息、密码信息的校验
 * @author kend
 *
 */
@Service("commonValidation")
public class CommonValidation {
	
	@Autowired
	@Qualifier("ucApiService")
	private UcApiService ucApiService;
	
	@Autowired
    @Qualifier("commonService")
    private CommonService commonService;
	
	@Autowired
    @Qualifier("lcsClientService")
    private LcsClientService lcsClientService;
	
	/**
	 * 遵从《用户中心接口文档说明书_AS_V1.0.0.doc》，
	 * 就mac、企业信息、消费者卡片信息、消费者密码等进行校验，并按需要返回pos机信息。签到状态校验隐含，自动执行。
	 * @param cmd  校验参数
	 * @param item  交易项，分别是：<br>
	 * 		ent：企业信息;card:卡信息;pwd：支付密码;<br>
	 * 		说明，mac是一切请求的必须校验项
	 * @return 
	 */
	public void reqParamValid(Cmd cmd) throws Exception{
		SystemLog.info("CommonValidation", "reqParamValid()", "entering method.");
		PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        
        AsDevCheckParam checkParam = new AsDevCheckParam();
        
		//不同业务需做相应的校验项
        Set<String> item = new HashSet<String>();
        switch (reqId){
        case PosConstant.REQ_HSB_PAY_CCY_ID: 
        case PosConstant.REQ_HSORDER_HSB_PAY_ID: 
        case PosConstant.REQ_EARNEST_PREPAY_ID: 
        	item.add("mac");item.add("ent");item.add("card");item.add("TP");//需交易密码
        	break;
        	
        case PosConstant.REQ_HSB_ENT_RECHARGE_ID:
        	item.add("mac");item.add("ent");item.add("card");item.add("EP");//校验企业8位支付密码
        	break;
        	
        case PosConstant.REQ_HSB_PAY_CANCEL_ID: 
        case PosConstant.REQ_HSB_PAY_RETURN_ID: 
        case PosConstant.REQ_POINT_CANCEL_ID: 
        	item.add("mac");item.add("ent");item.add("card");item.add("LP");//需登录密码
        	break;
        
        case PosConstant.REQ_POINT_ID: 
        	//start--commented by liuzh on 2016-06-27
        	//item.add("mac");item.add("ent");item.add("card");item.add("LPM");//手输卡号需校验登录密码
        	//end--commented by liuzh on 2016-06-27
        	
        	//start--added by liuzh on 2016-06-27 去掉校验登录密码
        	item.add("mac");
        	item.add("ent");item.add("card");
        	//item.add("LPM");//手输卡号需校验登录密码
        	//end--added by liuzh on 2016-06-27
        	break;
 
        case PosConstant.REQ_CARDNAME_SEARCH_ID://代兑第一步需支持跨地区,故要验卡。
        case PosConstant.REQ_HSB_PROXY_RECHARGE_ID: //代兑第二步仍带有卡信息	
        case PosConstant.REQ_EARNEST_SEARCH_ID: //定金单检索
        case PosConstant.REQ_EARNEST_CANCEL_ID: //定金撤销 无需密码
        case PosConstant.REQ_EARNEST_SETTLE_ACC_ID: //定金结算 无需交易密码   
        //start--added by liuzh on 2016-06-23
        case PosConstant.REQ_POINT_ORDERS_SEARCH_ID:
        case PosConstant.REQ_POINT_ORDER_CANCEL_SEARCH_ID:
        case PosConstant.REQ_POINT_ORDER_BACK_SEARCH_ID:        	
        //end--added by liuzh on 2016-06-23        	
        	item.add("mac");item.add("ent");item.add("card");//只校验卡信息
        	break;
        	
        case PosConstant.REQ_HSB_PAY_REVERSE_ID: 
        case PosConstant.REQ_HSB_PAY_CANCEL_REVERSE_ID: 
        case PosConstant.REQ_HSB_PAY_RETURN_REVERSE_ID: 
        case PosConstant.REQ_POINT_REVERSE_ID:
        case PosConstant.REQ_POINT_CANCEL_REVERSE_ID:
        case PosConstant.REQ_HSB_PROXY_RECHARGE_REVERSE_ID:
        case PosConstant.REQ_HSB_ENT_RECHARGE_REVERSE_ID:
        case PosConstant.REQ_POINT_ORDER_SEARCH_ID:
        case PosConstant.REQ_HSORDER_CASH_PAY_REVERSE_ID:
        case PosConstant.REQ_HSORDER_HSB_PAY_REVERSE_ID:
        case PosConstant.REQ_EARNEST_REVERSE_ID:
        case PosConstant.REQ_EARNEST_SETTLE_REVERSE_ID:
        case PosConstant.REQ_EARNEST_CANCEL_REVERSE_ID:
        case PosConstant.REQ_HSORDERS_SEARCH_ID:
        case PosConstant.REQ_BALANCE_SEARCH_ID:
        case PosConstant.REQ_HSORDER_CASH_PAY_ID://网上订单现金支付，报文无卡信息，不校验卡
        //start--added by liuzh on 2016-05-23	
        case PosConstant.REQ_HSORDER_SEARCH_ID:
        //end--added by liuzh on 2016-05-23	
        	item.add("mac");item.add("ent");//只校验企业
        	break;
        
        case PosConstant.REQ_SIGNIN_ID:
        	item.add("ent");item.add("signin");
        	break;	
        	

        case PosConstant.REQ_BATCH_SETTLE_ID:
        case PosConstant.REQ_POS_UPGRADE_CHECK_ID:        	
        	item.add("ent");
        	break;	
        	
        default :
        	CommonUtil.checkState(true,"不符合综合校验范围的业务类型。reqId：" + reqId,
        			PosRespCode.POS_CENTER_FAIL);
        }
        
        
        
        
		//0 任何类型的综合校验必须有这些项
		String entResNo = reqParam.getEntNo();
		String posNo = reqParam.getPosNo();
		String opr = reqParam.getOperNo();
		checkParam.setEntResNo(entResNo);
		checkParam.setDeviceNO(posNo);
		checkParam.setDeviceType(1);//指代pos机
		checkParam.setOperator(opr);
		
		//1 mac校验（签到状态校验已在uc的checkEntDevice（）中封装，使用方无需明示。）
		if(item.contains("mac")) {
			checkParam.setCheckMac(true);
			//提取pos计算得到的mac值
			byte[] macData = reqParam.getMacDat();
			CommonUtil.checkState(null == macData,"MAC校验失败，MAC串为空", PosRespCode.MAC_CHECK);
			//拼接计算mac的数据：
			//1  去掉64域（64域存放报文鉴别码，即pos机计算的mac，占最后8个字节）；
			//2 头中typeId+体中业务数据连接后成为计算依据；
			//注：这段拼接手法拙劣，要优化。
			byte[] typeId = cmd.getTypeId();
			byte[] requestBody = cmd.getBody();
			byte[] newBody = new byte[requestBody.length - 8];//去掉64域
			System.arraycopy(requestBody, 0, newBody, 0, requestBody.length - 8);
			byte[] dataByte = new byte[typeId.length + newBody.length];
			int count = 0;
			System.arraycopy(typeId, 0, dataByte, count, typeId.length);
			count += typeId.length;
			System.arraycopy(newBody, 0, dataByte, count, newBody.length);
			checkParam.setData(dataByte);
			checkParam.setMac(macData);
			SystemLog.debug("CommonValidation","reqParamValid()","用于mac校验的请求参数：" + 
					Hex.encodeHexString(dataByte));
		}

		
		//2 企业信息校验
		if(item.contains("ent")) checkParam.setGetPosInfo(true);
		
		//签到所需的企业互生号、pos编号、操作员在开始已置入。
		if(item.contains("signin")) checkParam.setPosSignIn(true);
		
		
		//3 卡校验
		//输入方式及是否有pin，22域的1+2位和3位。22域，服务点输入方式，必须有值。
		String inputWay = reqParam.getInputWay();
		String isPin = reqParam.getIsPin();
		String custCardNo = null;
		String custCardCode = null;
		boolean localCardFlag = true;
		if(item.contains("card")){//需要进行卡信息校验的业务类型，在这里添加
			
			if(PosConstant.INPUT_WAY_STRIPE.equals(inputWay))
				checkParam.setCheckCardCode(true);
			else checkParam.setCheckCardCode(false);
			
			/*第一阶段：初始化，参数获取及识别-------------*/
	        //3.1 提取消费者卡号
	        //获取消费者的互生卡号、暗码
			if(PosConstant.REQ_CARDNAME_SEARCH_ID.equals(reqId)){//代兑互生币前一步的"查询互生积分卡姓名"，有卡号但22域为空，只能个别处理。
				custCardNo = reqParam.getCardNo();
			}else if (item.contains("EP")){
				//没有卡信息
			}else{

				Map<String, String> cardNoAndCode = commonService.getCustomerHscardNo(cmd);

				custCardNo = cardNoAndCode.get("cardNo");//消费者互生号，获取时已做了长度校验
				custCardCode = cardNoAndCode.get("cardCode");//消费者互生卡暗码，获取时已做了长度校验
				SystemLog.debug("CommonValidation","reqParamValid()","custCardNo：" + custCardNo + 
						" ; custCardCode:" + custCardCode);
		        CommonUtil.checkState(StringUtils.isEmpty(custCardNo), "8583报文异常, 消费者互生卡号为空", 
		        			PosRespCode.INVALID_CARD);
			}
			
	        
	        //消费者卡号回写cmd
	        cmd.getTmpMap().put("custCardNo", custCardNo);
	        cmd.getTmpMap().put("custCardCode", custCardCode);
	        
	        //3.2识别本地、异地交易并设置类型标记
	        /*
	         * 注意：这里为提高系统可用性，主观认为多数交易都是本地卡交易。
	         * 则在lcs异常时，就认为是本地卡，继续交易。若为异地卡，则交易时同样会报错。
	         */
	        if (!item.contains("EP")){//无消费者卡信息
	        	try{
		        	localCardFlag = lcsClientService.isLocalPlat(custCardNo);
		        }catch(HsException he){
		        	if(he.getErrorCode() == RespCode.GL_DATA_NOT_FOUND.getCode()){
		        		cmd.setRespCode(PosRespCode.INVALID_CARD);
		    	        SystemLog.error("CommonValidation", "reqParamValid()","本地异地卡校验失败。LCS报卡号："+ 
		    	        		custCardNo +" 未找到；请求参数：" + JSON.toJSONString(reqParam), he);
		    	        throw he;//只有lcs明确告知卡片无效，才终止流程。
		        	}else{
		        		cmd.setRespCode(PosRespCode.POS_FUNC_FAIL);
		    	        SystemLog.error("CommonValidation", "reqParamValid()","本地异地卡校验失败,LCS服务异常。卡号："+ 
		    	        		custCardNo +"；请求参数：" + JSON.toJSONString(reqParam), he);   
		        	}
		        }catch(Exception e){
		        	//考虑到绝大部分交易是本地卡交易，若lcs异常，则假定位本地卡继续发起支付。ps处理失败会抛出异常。
		        	SystemLog.error("CommonValidation", "reqParamValid()","本地异地卡校验失败,LCS未知异常。卡号："+ 
			        		custCardNo +" ；请求参数：" + JSON.toJSONString(reqParam), e);
		        }	
	        }
	        
	        //本地异地卡标识信息回写cmd
    		cmd.getTmpMap().put("localCardFlag", localCardFlag);
	        
	        if(!localCardFlag)//异地卡无法校验
	        	item.remove("card");
	        else{
	        	/*第二阶段: 开始卡片校验-------------*/
				checkParam.setResNo(custCardNo);
				checkParam.setCode(custCardCode);
				//密码校验 遵从《归一科技（POS）终端应用规范 消息域-消息交换说明v3.0》中《2.2　交易类》，52域要求有值的都需要校验密码
				//直接按照报文协议加校验，不根据其它域做判断
				if(PosConstant.PIN_1.equals(isPin)){//有pin，需密码
					//52域 持卡人的个人密码的密文
					byte[] pinData = reqParam.getPinDat();
					//B64，8个字节的定长二进制数域
					CommonUtil.checkState(null == pinData || pinData.length != 8,
							"8583报文异常, 交易中包含PIN格式异常！pinDat: " + pinData, PosRespCode.PIN_FORMAT);
					if (item.contains("EP")){
						checkParam.setCheckCardPwd(false);
						checkParam.setEntTradePwd(pinData);
					}else {
						checkParam.setCheckCardPwd(true);
						checkParam.setPosPwd(pinData);
					}
					
				} else if (PosConstant.PIN_2.equals(isPin)) {//无pin。除此其它则为异常数据。
					checkParam.setCheckCardPwd(false);
				}else if(PosConstant.REQ_CARDNAME_SEARCH_ID.equals(reqId)){//要刷卡但22无值，个别处理
					
				}else{
					SystemLog.fatal("CommonValidation", "reqParamValid()", 
							"服务点输入方式码异常！22.3域数值无效。isPin：" + isPin);
					throw new PosException(PosRespCode.REQUEST_PACK_FORMAT, "服务点输入方式码异常！");
				}
	        }
		}

		
		SystemLog.debug("CommonValidation", "reqParamValid()", "发起uc综合校验， checkParam : " +
										JSON.toJSONString(checkParam));//kend test
		
		//4 发起综合校验
		AsDevCheckResult checkResu = ucApiService.checkEntDevice(checkParam);
		
		//5  企业、pos信息结果校验及回写cmd
		AsEntStatusInfo entInfo = checkResu.getAsEntStatusInfo();
		CommonUtil.checkState(null == entInfo, "无此企业信息：" + entResNo, PosRespCode.NO_FOUND_ENT);
        CommonUtil.checkState(entInfo.getIsClosedEnt().equals(PosConstant.POS_TRUE), "企业已被冻结：" + 
        		entResNo, PosRespCode.CHECK_ACCOUNT_FREEZE);
        /**
         * 基本状态   1：正常(NORMAL)  成员企业、托管企业用
                    2：预警(WARNING) 成员企业、托管企业用
                    3：休眠(DORMANT) 成员企业用
                    4：长眠（申请注销）成员企业
                    5：已注销 成员企业
                    6：申请停止积分活动中  托管企业用
                    7：停止积分活动    托管企业用
                    8：注销申请中   成员企业
         */
        CommonUtil.checkState(entInfo.getBaseStatus() == 7, "企业基础状态异常：" + 
        		entResNo, PosRespCode.NO_ENT_CARDPOINT);
        CommonUtil.checkState(entInfo.getBaseStatus() == 5, "企业已被注销：" + 
        		entResNo, PosRespCode.NO_FOUND_ENT);
        //start--add by liuzh on 2016-05-17
        CommonUtil.checkState(entInfo.getBaseStatus() == 8, "企业注销申请中：" + 
        		entResNo, PosRespCode.NO_FOUND_ENT);        
        //end--add by liuzh on 2016-05-17
        
        //pos设备信息结果校验
        AsPos posInfo = checkResu.getAsPos();
        CommonUtil.checkState(null == posInfo, "没找到POS设备, 企业互生号：" + entResNo + 
        		posNo, PosRespCode.NO_FOUND_DEVICE);
        /**
         * 可用      ENABLED(1),
         * 禁用       DISABLED(2),
         * 返修       REPAIRED(3),
         * 冻结          LOCKED(4);
         */
        CommonUtil.checkState(posInfo.getStatus() != AsDeviceStatusEnum.ENABLED.getValue(), 
        		"pos机状态异常, 企业互生号：" + entResNo + "； pos机编号：" + posNo, PosRespCode.POS_STATUS);
        SystemLog.debug("CommonValidation", "reqParamValid()","企业信息:" + JSON.toJSONString(entInfo) +
        		" ; pos机信息：" + JSON.toJSONString(posInfo));
        
        //将获取的企业状态信息回写cmd，供业务方法后续使用。
		cmd.getTmpMap().put("entStatusInfo", entInfo);
		cmd.getTmpMap().put("posInfo", posInfo);
		
		
		//企业资源类型（申报资源类型） 1：首段资源  2：创业资源  3：全部资源 4：正常成员企业 5：免费成员企业
        //用以识别是否为免费成员企业 ，最小积分比例与非免费企业不同。
        CommonUtil.checkState(null == checkResu.getStartResType(), "从uc获取企业资源类型为null, 企业互生号：" + 
        		entResNo, PosRespCode.POS_CENTER_FAIL);
        cmd.getTmpMap().put("startResType", checkResu.getStartResType());

		//获取消费者客户号
        cmd.getTmpMap().put("custId", checkResu.getCustId());
        
        //对于签到作业，获取pikmak
        cmd.getTmpMap().put("pikmak", checkResu.getPikmak());

		//6  密码校验
		String pwd = null;
		if(item.contains("card")){
			//6.1 有密码（兑换互生币，传来的也是1。代兑互生币是2）
			if(PosConstant.PIN_1.equals(isPin)){
				//6.1.1 则提取密码。接续"3 卡校验"的执行结果继续处理（后续将转至checkEntDevice（）中实现。）
				byte[] arrGyPin = item.contains("EP") ? checkResu.getEntTradePwd():checkResu.getPwdDecrypt();
				CommonUtil.checkState(null == arrGyPin || arrGyPin.length != 8, 
						"AC综合校验返回的密钥格式异常！arrGyPin:" + Arrays.toString(arrGyPin), PosRespCode.POS_FUNC_FAIL);
				//密码最大明文长度
				String servicePinLen = reqParam.getServicePinLen();
				//pos机2.0 消费积分手输卡号，需6位登录码，但报文无此值，故只提示，不严格校验。
				if (StringUtils.isEmpty(servicePinLen)) 
				    SystemLog.warn("CommonValidation", "reqParamValid()","servicePinLen isEmpty..");
				
				if(item.contains("EP"))
					pwd = PosUtil.decryptWithANSIFormat(arrGyPin, entResNo, servicePinLen);
				else
					pwd = PosUtil.decryptWithANSIFormat(arrGyPin, custCardNo, servicePinLen);
				
			}
			
			//回写密码明文
			cmd.getTmpMap().put("pwd", pwd);
			
			//6.2 卡密信息校验
			if(item.contains("TP")){
				CommonUtil.checkState(!(null != pwd && pwd.length() == 8), 
                		"消费者支付密码错误", PosRespCode.CHECK_CARDORPASS_FAIL);
				ucApiService.checkResNoAndTradePwd(custCardNo, CommonUtil.string2MD5(pwd));	
			}else if(item.contains("EP")){
				CommonUtil.checkState(!(null != pwd && pwd.length() == 8), 
                		"企业支付密码错误", PosRespCode.CHECK_CARDORPASS_FAIL);
				ucApiService.checkEntTradePwd(entInfo.getEntCustId(), CommonUtil.string2MD5(pwd));
			}else if(item.contains("LP")){
				CommonUtil.checkState(!(null != pwd && pwd.length() == 6), 
                		"消费者登录密码错误", PosRespCode.CHECK_CARDORPASS_FAIL);
				// 校验登录密码
	            // 登录密码不要md5，交易密码仍要，但CommonUtil.string2MD5（）中算法做了调整，有注释。
				ucApiService.checkResNoAndLoginPwd(custCardNo, pwd);
//				ucApiService.checkResNoPwdAndCode(custCardNo, CommonUtil.string2MD5(pwd), custCardCode);//未用到	
	        }else if(item.contains("LPM")){
	        	//积分，刷卡、扫码无需密码，要有暗码；输入，无暗码，要有6位登录密码。
	        	if(PosConstant.INPUT_WAY_STRIPE.equals(inputWay)){
	        		ucApiService.checkResNoAndCode(custCardNo, custCardCode);
				}else if(PosConstant.INPUT_WAY_MANUAL.equals(inputWay)){
					ucApiService.checkResNoAndLoginPwd(custCardNo, pwd);
				}else if(PosConstant.INPUT_WAY_QR.equals(inputWay)){
					//目前的规则，积分扫码，只校验卡号有效性。
				}
	        }else if(PosConstant.INPUT_WAY_STRIPE.equals(inputWay) && PosConstant.PIN_2.equals(isPin)){
				ucApiService.checkResNoAndCode(custCardNo, custCardCode);
			}
				
		}

	}
	
	/**
	 * 组装异地（发卡平台）业务系统卡片校验所需的参数
	 * @param cmd
	 * @param item
	 * @return
	 * @throws PosException 
	 */
	public JSONObject assembleRemoteValidParm(Cmd cmd, Set<String> item) throws PosException{
		SystemLog.debug(this.getClass().getName(), "assembleRemoteValidParm()", "entering method");
		
		JSONObject rValidParamJson = new JSONObject();	
		PosReqParam reqParam = cmd.getPosReqParam();
		String isPin = reqParam.getIsPin();
		
        String custCardNo = (String)cmd.getTmpMap().get("custCardNo");
        String custCardCode = (String)cmd.getTmpMap().get("custCardCode");
		rValidParamJson.put("custCardNo", custCardNo);
		rValidParamJson.put("custCardCode", custCardCode);
		
		rValidParamJson.put("inputWay", reqParam.getInputWay());
		rValidParamJson.put("isPin", isPin);
		//=1则校验密码
		if(PosConstant.PIN_1.equals(isPin)){
			//获取密码第一步所需参数
			byte[] pinData = reqParam.getPinDat();
			CommonUtil.checkState(null == pinData || pinData.length != 8,"8583报文异常, 交易中包含PIN格式异常！pinDat: " + 
					pinData, PosRespCode.PIN_FORMAT);
			rValidParamJson.put("pinData", Base64.encodeBase64String(pinData));
			
			rValidParamJson.put("posNo", reqParam.getPosNo());
			rValidParamJson.put("entNo", reqParam.getEntNo());
			//第二步所需参数
			String servicePinLen = reqParam.getServicePinLen();
			CommonUtil.checkState(servicePinLen == null, "报文格式异常！有pin，但26域无值:", PosRespCode.REQUEST_PACK_FORMAT);
			rValidParamJson.put("servicePinLen", servicePinLen);
			if(item.contains("TP"))  rValidParamJson.put("payPwd", "1");
		} else if (!PosConstant.PIN_2.equals(isPin)) {//无pin及扫码，无密码。除此其它则为异常数据。
			throw new PosException(PosRespCode.REQUEST_PACK_FORMAT, "服务点输入方式码异常！22.3域数值无效。isPin：" + isPin);
		}
	
		return rValidParamJson;
		
	}
	
	/**
	 * 剥离出卡信息交易，用于移植到ps等核心业务。这里仅作验证，不调用。
	 * @param cardParams
	 * @throws PosException
	 */
	public void checkCard(JSONObject cardParams) throws Exception{
		SystemLog.debug(this.getClass().getName(), "checkCard()", "entering method");
		
		String inputWay = cardParams.getString("inputWay");
		String isPin = cardParams.getString("isPin");
		String posNo = cardParams.getString("posNo");
		String entNo = cardParams.getString("entNo");
		String custCardNo = cardParams.getString("custCardNo");
		String custCardCode = cardParams.getString("custCardCode");
		byte[] pinData = Base64.decodeBase64(cardParams.getString("pinData"));
		String servicePinLen = cardParams.getString("servicePinLen");
		String transType = cardParams.getString("transType");
		
		String pwd = null;
		if("1".equals(isPin)){
			byte[] arrGyPin = ucApiService.getDecrypt(posNo, entNo, pinData);
			if(arrGyPin == null || arrGyPin.length != 8)
				throw new PosException(PosRespCode.POS_CENTER_FAIL, "调用key server解密接口返回密钥格式异常！arrGyPin:" + arrGyPin);
			pwd = PosUtil.decryptWithANSIFormat(arrGyPin, custCardNo, servicePinLen);
			if (TransType.LOCAL_CARD_REMOTE_HSB.equals(transType)
                    //|| TransType.HS_ORDER_PAY.equals(transType) //后续增加电商订单的跨地区交易
                    ) {
                CommonUtil.checkState(pwd == null || pwd.length() != 8, "密码长度有误", PosRespCode.CHECK_CARDORPASS_FAIL);
            } else {//其余的都是登录密码
                CommonUtil.checkState(pwd == null || pwd.length() != 6, "密码长度有误", PosRespCode.CHECK_CARDORPASS_FAIL);
            }
		}

		//开始校验
		if ("01".equals(inputWay)){//手动输入
			ucApiService.checkResNoAndLoginPwd(custCardNo, CommonUtil.string2MD5(pwd));
		}else if("02".equals(inputWay)){//刷卡
			//不是撤单退货的，输入的是交易密码，所以只校验	卡号 + 暗码（支付时的校验另有5.3完成）
			if(!TransType.LOCAL_CARD_REMOTE_POINT_CANCEL.equals(transType)
					&& !TransType.LOCAL_CARD_REMOTE_HSB_CANCEL.equals(transType) 
					&& !TransType.LOCAL_CARD_REMOTE_HSB_BACK.equals(transType)){
				ucApiService.checkResNoAndCode(custCardNo, custCardCode);
			}else{//撤单或退货类业务 输入的是登录密码
				ucApiService.checkResNoPwdAndCode(custCardNo, CommonUtil.string2MD5(pwd), custCardCode);
			}
		}

		//5.3 有要求则进行支付密码校验
		if("1".equals(cardParams.getString("payPwd")))
			ucApiService.checkResNoAndTradePwd(custCardNo, CommonUtil.string2MD5(pwd));
	}
	
	
}
