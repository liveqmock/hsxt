package com.gy.hsxt.pg.bankadapter.common.utils;

import org.apache.log4j.Logger;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.AddrFlag;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.BSysFlag;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.UnionFlag;

/**
 * @author jbli
 */
public class B2ePABankTransFeeUtil {

	private static final Logger logger = Logger.getLogger(B2ePABankTransFeeUtil.class);	
	private static final double PINGAN_BANK_MAX_RATE = 0.000016;
	
	/**
	 * 获取单笔转账扣费的手续费金额(平安银行)
	 * @param tranAmount 转账金额 单位（元）
	 * @param unionFlag 同行/跨行
	 * @param addrFlag 同城/异地
	 * @param bsysFlag 普通/加急
	 * @return 元
	 */
	public static double getPABankFee(double tranAmount,UnionFlag unionFlag,AddrFlag addrFlag,BSysFlag bsysFlag) throws Exception{
		if(tranAmount<= 0 || unionFlag == null || addrFlag == null || bsysFlag == null){
			throw new Exception("parameter is wrong in method getFee!");
		}

		if(AddrFlag.OTHER_CITY == addrFlag && UnionFlag.SAME_BANK == unionFlag){   //平安银行企业网银异地行内汇款
			if(tranAmount<=10000){
				return 4;
			}else if(tranAmount>10000 && tranAmount<=100000){
				return 8;
			}else if(tranAmount>100000 && tranAmount<=500000){
				return 12;
			}else if(tranAmount>500000 && tranAmount<=1000000){
				return 16;
			}else if(tranAmount>1000000 && tranAmount<=10000000){
				return Math.ceil(ArithHelper.multiply(tranAmount, PINGAN_BANK_MAX_RATE, 2));
			}else{
				return 160;
			}
		}else if(AddrFlag.SAME_CITY == addrFlag && UnionFlag.OTHER_BANK == unionFlag){     //平安银行企业网银同城跨行转账
			if(BSysFlag.NO == bsysFlag){
				return 1;
			}else{
				if(tranAmount<=10000){
					return 4;
				}else if(tranAmount>10000 && tranAmount<=100000){
					return 8;
				}else if(tranAmount>100000 && tranAmount<=500000){
					return 12;
				}else if(tranAmount>500000 && tranAmount<=1000000){
					return 16;
				}else if(tranAmount>1000000 && tranAmount<=10000000){
					return Math.ceil(ArithHelper.multiply(tranAmount, PINGAN_BANK_MAX_RATE, 2));
				}else {
					return 160;
				}
			}
		}else if(AddrFlag.OTHER_CITY == addrFlag && UnionFlag.OTHER_BANK == unionFlag){    //平安银行企业网银异地跨行汇款
			if(BSysFlag.NO == bsysFlag){
				if(tranAmount<=10000){
					return 4;
				}else if(tranAmount>10000 && tranAmount<=100000){
					return 8;
				}else if(tranAmount>100000 && tranAmount<=500000){
					return 12;
				}else if(tranAmount>500000 && tranAmount<=1000000){
					return 16;
				}else if(tranAmount>1000000 && tranAmount<=10000000){
					return Math.ceil(ArithHelper.multiply(tranAmount, PINGAN_BANK_MAX_RATE, 2));
				}else {
					return 160;
				}
			}else{
				if(tranAmount<=10000){
					return 5;
				}else if(tranAmount>10000 && tranAmount<=100000){
					return 10;
				}else if(tranAmount>100000 && tranAmount<=500000){
					return 15;
				}else if(tranAmount>500000 && tranAmount<=1000000){
					return 20;
				}else if(tranAmount>1000000 && tranAmount<=10000000){
					return Math.ceil(ArithHelper.multiply(tranAmount, PINGAN_BANK_MAX_RATE, 2));
				}else {
					return 200;
				}				
			}
		}else {          //平安银行企业网银同城行内转账
			return 0;
		} 
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			logger.debug("---------------------------平安银行企业网银同城行内转账---------------------------");
			logger.debug("普通;免费;==="+ B2ePABankTransFeeUtil.getPABankFee(200000, UnionFlag.SAME_BANK,AddrFlag.SAME_CITY, BSysFlag.YES));
			logger.debug("加急;免费;==="+ B2ePABankTransFeeUtil.getPABankFee(200000, UnionFlag.SAME_BANK,AddrFlag.SAME_CITY, BSysFlag.NO));
			
			logger.debug("---------------------------平安银行企业网银异地行内汇款---------------------------");
			logger.debug("普通;1万元（含）以下:4元;==="+ B2ePABankTransFeeUtil.getPABankFee(2000, UnionFlag.SAME_BANK,AddrFlag.OTHER_CITY, BSysFlag.NO));
			logger.debug("普通;1万元至10万元（含）:8元;==="+ B2ePABankTransFeeUtil.getPABankFee(20000, UnionFlag.SAME_BANK,AddrFlag.OTHER_CITY, BSysFlag.NO));
			logger.debug("普通;10万元至50万元（含）:12元;==="+ B2ePABankTransFeeUtil.getPABankFee(200000, UnionFlag.SAME_BANK,AddrFlag.OTHER_CITY, BSysFlag.NO));
			logger.debug("普通;50万元至100万元（含）:16元;==="+ B2ePABankTransFeeUtil.getPABankFee(700000, UnionFlag.SAME_BANK,AddrFlag.OTHER_CITY, BSysFlag.NO));
			logger.debug("普通;100万元至1000万元（含）:0.016‰;==="+ B2ePABankTransFeeUtil.getPABankFee(9000000, UnionFlag.SAME_BANK,AddrFlag.OTHER_CITY, BSysFlag.NO));
			logger.debug("普通;1000万元以上:160元封顶==="+ B2ePABankTransFeeUtil.getPABankFee(11000000, UnionFlag.SAME_BANK,AddrFlag.OTHER_CITY, BSysFlag.NO));
			logger.debug("---------------------------------------------------------------------");
			logger.debug("加急;1万元（含）以下:4元;==="+ B2ePABankTransFeeUtil.getPABankFee(2000, UnionFlag.SAME_BANK,AddrFlag.OTHER_CITY, BSysFlag.YES));
			logger.debug("加急;1万元至10万元（含）:8元;==="+ B2ePABankTransFeeUtil.getPABankFee(20000, UnionFlag.SAME_BANK,AddrFlag.OTHER_CITY, BSysFlag.YES));
			logger.debug("加急;10万元至50万元（含）:12元;==="+ B2ePABankTransFeeUtil.getPABankFee(200000, UnionFlag.SAME_BANK,AddrFlag.OTHER_CITY, BSysFlag.YES));
			logger.debug("加急;50万元至100万元（含）:16元;==="+ B2ePABankTransFeeUtil.getPABankFee(700000, UnionFlag.SAME_BANK,AddrFlag.OTHER_CITY, BSysFlag.YES));
			logger.debug("加急;100万元至1000万元（含）:0.016‰;==="+ B2ePABankTransFeeUtil.getPABankFee(9000000, UnionFlag.SAME_BANK,AddrFlag.OTHER_CITY, BSysFlag.YES));
			logger.debug("加急;1000万元以上:160元封顶==="+ B2ePABankTransFeeUtil.getPABankFee(11000000, UnionFlag.SAME_BANK,AddrFlag.OTHER_CITY, BSysFlag.YES));			
			
			logger.debug("---------------------------平安银行企业网银同城跨行转账---------------------------");
			logger.debug("普通：1元/笔;==="+ B2ePABankTransFeeUtil.getPABankFee(2000, UnionFlag.OTHER_BANK,AddrFlag.SAME_CITY, BSysFlag.NO));
			logger.debug("普通：1元/笔;==="+ B2ePABankTransFeeUtil.getPABankFee(20000, UnionFlag.OTHER_BANK,AddrFlag.SAME_CITY, BSysFlag.NO));
			logger.debug("普通：1元/笔;==="+ B2ePABankTransFeeUtil.getPABankFee(200000, UnionFlag.OTHER_BANK,AddrFlag.SAME_CITY, BSysFlag.NO));
			logger.debug("普通：1元/笔;==="+ B2ePABankTransFeeUtil.getPABankFee(700000, UnionFlag.OTHER_BANK,AddrFlag.SAME_CITY, BSysFlag.NO));
			logger.debug("普通：1元/笔;==="+ B2ePABankTransFeeUtil.getPABankFee(9990000, UnionFlag.OTHER_BANK,AddrFlag.SAME_CITY, BSysFlag.NO));
			logger.debug("普通：1元/笔;==="+ B2ePABankTransFeeUtil.getPABankFee(11000000, UnionFlag.OTHER_BANK,AddrFlag.SAME_CITY, BSysFlag.NO));				
			logger.debug("---------------------------------------------------------------------");
			logger.debug("加急：1万元（含）以下:4元;==="+ B2ePABankTransFeeUtil.getPABankFee(2000, UnionFlag.OTHER_BANK,AddrFlag.SAME_CITY, BSysFlag.YES));
			logger.debug("加急：1万元至10万元（含）:8元;==="+ B2ePABankTransFeeUtil.getPABankFee(20000, UnionFlag.OTHER_BANK,AddrFlag.SAME_CITY, BSysFlag.YES));
			logger.debug("加急：10万元至50万元（含）:12元;==="+ B2ePABankTransFeeUtil.getPABankFee(200000, UnionFlag.OTHER_BANK,AddrFlag.SAME_CITY, BSysFlag.YES));
			logger.debug("加急：50万元至100万元（含）:16元;==="+ B2ePABankTransFeeUtil.getPABankFee(700000, UnionFlag.OTHER_BANK,AddrFlag.SAME_CITY, BSysFlag.YES));
			logger.debug("加急：100万元至1000万元（含）:0.016‰;==="+ B2ePABankTransFeeUtil.getPABankFee(9561234.39, UnionFlag.OTHER_BANK,AddrFlag.SAME_CITY, BSysFlag.YES));
			logger.debug("加急：1000万元以上:160元封顶==="+ B2ePABankTransFeeUtil.getPABankFee(11000000, UnionFlag.OTHER_BANK,AddrFlag.SAME_CITY, BSysFlag.YES));			
			logger.debug("---------------------------平安银行企业网银异地跨行汇款---------------------------");
			logger.debug("普通：1万元（含）以下:4元;==="+ B2ePABankTransFeeUtil.getPABankFee(2000, UnionFlag.OTHER_BANK,AddrFlag.SAME_CITY, BSysFlag.YES));
			logger.debug("普通：1万元至10万元（含）:8元;==="+ B2ePABankTransFeeUtil.getPABankFee(20000, UnionFlag.OTHER_BANK,AddrFlag.OTHER_CITY, BSysFlag.NO));
			logger.debug("普通：10万元至50万元（含）:12元;==="+ B2ePABankTransFeeUtil.getPABankFee(200000, UnionFlag.OTHER_BANK,AddrFlag.OTHER_CITY, BSysFlag.NO));
			logger.debug("普通：50万元至100万元（含）:16元;==="+ B2ePABankTransFeeUtil.getPABankFee(700000, UnionFlag.OTHER_BANK,AddrFlag.OTHER_CITY, BSysFlag.NO));
			logger.debug("普通：100万元至1000万元（含）:0.016‰;==="+ B2ePABankTransFeeUtil.getPABankFee(9561234.39, UnionFlag.OTHER_BANK,AddrFlag.OTHER_CITY, BSysFlag.NO));
			logger.debug("普通：1000万元以上:160元封顶==="+ B2ePABankTransFeeUtil.getPABankFee(11000000, UnionFlag.OTHER_BANK,AddrFlag.OTHER_CITY, BSysFlag.NO));					
			logger.debug("---------------------------------------------------------------------");
			logger.debug("加急：1万元（含）以下:4元;==="+ B2ePABankTransFeeUtil.getPABankFee(2000, UnionFlag.OTHER_BANK,AddrFlag.SAME_CITY, BSysFlag.YES));
			logger.debug("加急：1万元至10万元（含）:8元;==="+ B2ePABankTransFeeUtil.getPABankFee(20000, UnionFlag.OTHER_BANK,AddrFlag.OTHER_CITY, BSysFlag.YES));
			logger.debug("加急：10万元至50万元（含）:12元;==="+ B2ePABankTransFeeUtil.getPABankFee(200000, UnionFlag.OTHER_BANK,AddrFlag.OTHER_CITY, BSysFlag.YES));
			logger.debug("加急：50万元至100万元（含）:16元;==="+ B2ePABankTransFeeUtil.getPABankFee(700000, UnionFlag.OTHER_BANK,AddrFlag.OTHER_CITY, BSysFlag.YES));
			logger.debug("加急：100万元至1000万元（含）:0.02‰;==="+ B2ePABankTransFeeUtil.getPABankFee(9561234.39, UnionFlag.OTHER_BANK,AddrFlag.OTHER_CITY, BSysFlag.YES));
			logger.debug("加急：1000万元以上:200元封顶==="+ B2ePABankTransFeeUtil.getPABankFee(11000000, UnionFlag.OTHER_BANK,AddrFlag.OTHER_CITY, BSysFlag.YES));				
			
		} catch (Exception e) {
			logger.error("in test main method error:",e);
		}
	}

}
