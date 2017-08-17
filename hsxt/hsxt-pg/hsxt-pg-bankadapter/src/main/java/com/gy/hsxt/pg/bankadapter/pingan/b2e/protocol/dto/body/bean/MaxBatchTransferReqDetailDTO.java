package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.AddrFlag;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.UnionFlag;

/**
 * @author jbli
 *
 */
public class MaxBatchTransferReqDetailDTO {

	 //单笔转账凭证号(批次中的流水号)/序号	C(20)	必输	同一个批次内不能重复，建议按顺序递增生成，若上送返回则按此字段递增排序。；建议为递增序号，如从1开始
	private String sThirdVoucher;	    
	//客户自定义凭证号	C(20)	非必输	用于客户转账登记和内部识别，通过转账结果查询可以返回。银行不检查唯一性
	private String cstInnerFlowNo;	     
	//收款人开户行行号	C(12)	非必输	跨行转账不落地，则必输。为人行登记在册的商业银行号
	private String inAcctBankNode;	 
	//接收行行号	C(12)	非必输	跨行转账不落地，则必输。为人行登记在册的商业银行号
	private String inAcctRecCode;
	//收款人账户	C(32)	必输
	private String inAcctNo;	     	
	//收款人账户户名	C(60)	必输	
	private String inAcctName;	     
	 //收款人开户行名称	C(60)	必输	建议格式：xxx银行xx分行xx支行
	private String inAcctBankName;	   
	//收款账户开户省代码	C(2)	非必输	建议上送，减少跨行转账落单率。对照码参考“附录-省对照表”
	private String inAcctProvinceCode;   
	//收款账户开户市	C(12)	非必输	建议上送，减少跨行转账落单率。
	private String inAcctCityName	;     
	//转出金额	C(13,2)	必输	如为XML报文，则直接输入输出以元为单位的浮点数值，如2.50 (两元五角)
	private String tranAmount;	     
	 //资金用途	C(30)	必输	
	private String useEx;	            
	//行内跨行标志	C(1)	必输	1：行内转账，0：跨行转账
	private String unionFlag;	     
	//同城/异地标志	C(1) 
	private String addrFlag;	     
	//主账户	C(32)	非必输	券商资金结算时，填写“法人清算号”
	private String mainAcctNo;

	public String getsThirdVoucher() {
		return sThirdVoucher;
	}

	public String getCstInnerFlowNo() {
		return cstInnerFlowNo;
	}

	public String getInAcctBankNode() {
		return inAcctBankNode;
	}

	public String getInAcctRecCode() {
		return inAcctRecCode;
	}

	public String getInAcctNo() {
		return inAcctNo;
	}

	public String getInAcctName() {
		return inAcctName;
	}

	public String getInAcctBankName() {
		return inAcctBankName;
	}

	public String getInAcctProvinceCode() {
		return inAcctProvinceCode;
	}

	public String getInAcctCityName() {
		return inAcctCityName;
	}

	public String getTranAmount() {
		return tranAmount;
	}

	public String getUseEx() {
		return useEx;
	}

	public String getUnionFlag() {
		return unionFlag;
	}

	public String getAddrFlag() {
		return addrFlag;
	}

	public String getMainAcctNo() {
		return mainAcctNo;
	}

	private MaxBatchTransferReqDetailDTO(Builder builder){
		this.sThirdVoucher= builder.sThirdVoucher;	    
		this.cstInnerFlowNo= builder.cstInnerFlowNo;	    
		this.inAcctBankNode= builder.inAcctBankNode;	    
		this.inAcctRecCode= builder.inAcctRecCode;	    
		this.inAcctNo= builder.inAcctNo;	     
		this.inAcctName= builder.inAcctName;	     
		this.inAcctBankName= builder.inAcctBankName;	   
		this.inAcctProvinceCode= builder.inAcctProvinceCode;  
		this.inAcctCityName	= builder.inAcctCityName;    
		this.tranAmount= builder.tranAmount;	    
		this.useEx= builder.useEx;	            
		this.unionFlag= builder.unionFlag;	     
		this.addrFlag= builder.addrFlag;	    
		this.mainAcctNo= builder.mainAcctNo;	
	}


	public static class Builder{
		 //单笔转账凭证号(批次中的流水号)/序号	C(20)	必输	同一个批次内不能重复，建议按顺序递增生成，若上送返回则按此字段递增排序。；建议为递增序号，如从1开始
		private String sThirdVoucher = "";	    
		//客户自定义凭证号	C(20)	非必输	用于客户转账登记和内部识别，通过转账结果查询可以返回。银行不检查唯一性
		private String cstInnerFlowNo = "";	     
		//收款人开户行行号	C(12)	非必输	跨行转账不落地，则必输。为人行登记在册的商业银行号
		private String inAcctBankNode = "";	 
		//接收行行号	C(12)	非必输	跨行转账不落地，则必输。为人行登记在册的商业银行号
		private String inAcctRecCode = "";
		//收款人账户	C(32)	必输
		private String inAcctNo = "";	     	
		//收款人账户户名	C(60)	必输	
		private String inAcctName = "";	     
		 //收款人开户行名称	C(60)	必输	建议格式：xxx银行xx分行xx支行
		private String inAcctBankName = "";	   
		//收款账户开户省代码	C(2)	非必输	建议上送，减少跨行转账落单率。对照码参考“附录-省对照表”
		private String inAcctProvinceCode = "";   
		//收款账户开户市	C(12)	非必输	建议上送，减少跨行转账落单率。
		private String inAcctCityName	 = "";     
		//转出金额	C(13,2)	必输	如为XML报文，则直接输入输出以元为单位的浮点数值，如2.50 (两元五角)
		private String tranAmount = "";	     
		 //资金用途	C(30)	必输	
		private String useEx = "";	            
		//行内跨行标志	C(1)	必输	1：行内转账，0：跨行转账
		private String unionFlag = UnionFlag.SAME_BANK.getValue();	     
		//同城/异地标志	C(1) 
		private String addrFlag = AddrFlag.SAME_CITY.getValue();	     
		//主账户	C(32)	非必输	券商资金结算时，填写“法人清算号”
		private String mainAcctNo = "";
		
		
		/**
		 * @param sThirdVoucher C(32) 收款人账户  
		 * @param inAcctNo C(32) 收款人账户  
		 * @param inAcctName C(60) 收款人账户户名 C(60)
		 * @param inAcctBankName C(60)  收款人开户行名称  建议格式：xxx银行xx分行xx支行 
		 * @param tranAmount C(13,2)  转出金额 如为XML报文，则直接输入输出以元为单位的浮点数值，如2.50 (两元五角)
		 * @param unionFlag  C(1) 行内跨行标志
		 * @param useEx  C(30)  资金用途
		 */
		public Builder(String sThirdVoucher, String inAcctNo, String inAcctName,
				String inAcctBankName, String tranAmount, UnionFlag unionFlag,
				AddrFlag addrFlag, String useEx) {
			this.sThirdVoucher = sThirdVoucher;
			this.inAcctNo = inAcctNo;
			this.inAcctName = inAcctName;
			this.inAcctBankName = inAcctBankName;
			this.tranAmount = tranAmount;
			this.unionFlag = unionFlag.getValue();
			this.addrFlag = addrFlag.getValue();
			this.useEx = useEx;
		}

		/**
		 * 单笔转账凭证号(批次中的流水号)/序号
		 * 同一个批次内不能重复，建议按顺序递增生成，若上送返回则按此字段递增排序。；建议为递增序号，如从1开始
		 * @param sThirdVoucher
		 * @return
		 */
		public Builder setsThirdVoucher(String sThirdVoucher) {
			this.sThirdVoucher = sThirdVoucher;
			return this;
		}

		/**
		 * 客户自定义凭证号
		 * 用于客户转账登记和内部识别，通过转账结果查询可以返回。银行不检查唯一性
		 * @param cstInnerFlowNo
		 * @return
		 */
		public Builder setCstInnerFlowNo(String cstInnerFlowNo) {
			this.cstInnerFlowNo = cstInnerFlowNo;
			return this;
		}

		/**
		 * 收款人开户行行号      跨行转账不落地，则必输。为人行登记在册的商业银行号
		 * @param inAcctBankNode
		 * @return
		 */
		public Builder setInAcctBankNode(String inAcctBankNode) {
			this.inAcctBankNode = inAcctBankNode;
			return this;
		}

		
		/**
		 * 接收行行号     跨行转账不落地，则必输。为人行登记在册的商业银行号
		 * @param inAcctRecCode
		 * @return
		 */
		public Builder setInAcctRecCode(String inAcctRecCode) {
			this.inAcctRecCode = inAcctRecCode;
			return this;
		}

		/**
		 * 收款账户开户省代码 C(2) 建议上送，减少跨行转账落单率。对照码参考“附录-省对照表”
		 * @param inAcctProvinceCode
		 * @return
		 */
		public Builder setInAcctProvinceCode(String inAcctProvinceCode) {
			this.inAcctProvinceCode = inAcctProvinceCode;
			return this;
		}

		/**
		 * 收款账户开户市 C(12) 建议上送，减少跨行转账落单率。
		 * @param inAcctCityName
		 * @return
		 */
		public Builder setInAcctCityName(String inAcctCityName) {
			this.inAcctCityName = inAcctCityName;
			return this;
		}

		/**
		 * 资金用途 必输
		 * @param useEx
		 * @return
		 */
		public Builder setUseEx(String useEx) {
			this.useEx = useEx;
			return this;
		}


		/**
		 * 主账户 非必输 券商资金结算时，填写
		 * @param mainAcctNo
		 * @return
		 */
		public Builder setMainAcctNo(String mainAcctNo) {
			this.mainAcctNo = mainAcctNo;
			return this;
		}
		
		public MaxBatchTransferReqDetailDTO build(){
			return new MaxBatchTransferReqDetailDTO(this);
		}

	}

}
