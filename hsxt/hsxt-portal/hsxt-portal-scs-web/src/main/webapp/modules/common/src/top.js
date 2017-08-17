define(['commLan'],function(){
	var top={
			//扩展日期格式化方法format()
			addDate_format:function(){
				Date.prototype.format=function(){
					var month="0";
					if(this.getMonth()+1>=10)month=this.getMonth()+1;
					else month+=this.getMonth()+1;
					var day="0";
					if(this.getDate()>=10)day=this.getDate();
					else day+=this.getDate();
					var str=this.getFullYear()+"-"+month+"-"+day;	
					return str;
				};
				
				Date.prototype.formatTime=function(){
					var month="0";
					if(this.getMonth()+1>=10)month=this.getMonth()+1;
					else month+=this.getMonth()+1;
					var day="0";
					if(this.getDate()>=10)day=this.getDate();
					else day+=this.getDate();
					var hour="0";
					if(this.getHours()>=10)hour=this.getHours();
					else hour+=this.getHours();
					var mins="0";
					if(this.getMinutes()>=10)mins=this.getMinutes();
					else mins+=this.getMinutes();
					var seconds="0";
					if(this.getSeconds()>=10)seconds=this.getSeconds();
					else seconds+=this.getSeconds();
					var str=this.getFullYear()+"-"+month+"-"+day+" "+hour+":"+mins+":"+seconds;	
					return str;
				};
			}
	
				//获取今天开始和结束
				,getTodaySE:function(){
					var d1=new Date();
					return [d1.format(),d1.format()];
				}
			
				//获取最近一周开始和结束
				,getWeekSE:function (){
					var d1=new Date();
					var e=d1.format();
					d1.setDate(d1.getDate()-6);//上一周
					var s=d1.format();
					return [s,e];
				}
			
				//获取最近一月开始和结束
				,getMonthSE:function (){
					var today = new Date();
					var day = today.getDate();
					var e = new Date().format();
					today.setDate(0);
					var maxDay = today.getDate();
					if(day<=maxDay){
						today.setDate(day);
					}
					var s= today.format();
					return [s,e];
				}
			
				//获取最近一年开始和结束
				,getYearsSE:function (){
					var d1=new Date();
					var e=d1.format();
					d1.setFullYear(d1.getFullYear()-1);//上一个月
					var s=d1.format();
					return [s,e];
				}
				
				//获取最近三年开始和结束
				,get3YearsSE:function (){
					var d1=new Date();
					var e=d1.format();
					d1.setFullYear(d1.getFullYear()-3);//上三年
					var s=d1.format();
					return [s,e];
				}
				
				//读取缓存基础数据
				,loadCache:function (callback){
					comm.Request('loadCache',{
						type:'get',
						dataType:'json',
						async : false,
						success:function(response){
							callback(response);	
						},
						error: function(){
							alert(comm.lang('common').ConnError);
						}
					});	
				}
				
				//读取登录用户数据
				,getLoginUser:function (callback){
					comm.Request('getLoginUser',{
						type:'get',
						dataType:'json',
						async : false,
						success:function(response){
							callback(response);	
						},
						error: function(){
							alert(comm.lang('common').ConnError);
						}
					}) ;	
				}
				/**
				 * 转换银行名称
				 * @param bankNo
				 * @returns {String}
				 */
				,getBankName : function(bankNo){
					var bankName="";
					$.each(comm.getCache("company","cache").bankList,function(i,o){
						if(o.bankCode==bankNo)bankName= o.bankName;
					});
					return bankName;
				},
				isDoubleBankName:function(bankList,bankCode,bankAccount){
					var isFlag=false;
					if(null!=bankList){
						$.each(bankList,function(i,o){
							if(o.bankCode==bankCode&&o.bankAccount==bankAccount)isFlag=true;
						});
					}
					
					return isFlag;
				}
				//加载省份下拉框
				,add_areaListForCity:function(id1,id2){
					var country=this.getArea("156","0");
					if(country!=null){
						$(id1).empty();//清空列表
//						$(id1).append('<option value="">-请选择-</option>');
						$.each(country.childs,function(i,o){
							$(id1).append('<option value="'+o.areaNo+'" '
									+'>'+o.areaName+'</option>');
						});
						
						
						$(id2).selectbox({width:145}).selectRefresh();
						$(id1).selectbox({width:145,height:180}).bind("change",function(){
							var areaNo=$(id1).val();
							var province=top.getArea(areaNo,"1");
							if(province!=null){
								$(id2).empty();//清空列表
//								$(id2).append('<option value="">-请选择-</option>');
								$.each(province.childs,function(i,o){
									$(id2).append('<option value="'+o.areaNo+'" '
											+'>'+o.areaName+'</option>');
								});
								$(id2).selectbox({width:145}).selectRefresh();
							}else{
								$(id2).empty();//清空列表
//								$(id2).append('<option value="">-请选择-</option>');
								$(id2).selectbox({width:145}).selectRefresh();
							}	
							$(id2).selectIndex(0);//默认选第一个
						});
						
						$(id1).selectIndex(0);//默认选第一个
					}	
				}
				,getArea:function(areaNo,type){
					var area=null;
					
					if(type=="0"){//国家
						$.each(comm.getCache("company","cache").countryList,function(i,o){
							if(o.areaNo==areaNo){	
								area= o;
							}
						});

					}
					
					if(type=="1"){//省份
						$.each(comm.getCache("company","cache").countryList,function(i,o){
							
							$.each(o.childs,function(j,o2){//alert(o2.areaNo+":"+areaNo);
								if(o2.areaNo==areaNo){//alert(o2.areaName);
									area= o2;
								}
							});
							
						});

					}
					
					if(type=="2"){//城市
						$.each(comm.getCache("company","cache").countryList,function(i,o){
							
							$.each(o.childs,function(j,o2){
								$.each(o2.childs,function(k,o3){
									if(o3.areaNo==areaNo){
										area= o3;
									}
								});
							});
							
						});

					}
					
					return area;
				}
				,getQueryString : function (name) {
					var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
					var r = window.location.search.substr(1).match(reg);
					if (r != null) return unescape(r[2]); return null;
				}
				
				//交易类型转换描述
				,transCodeRender: function(transCode,transType){
					switch(transCode){
					case 'L_L_ACCU_POINT':{
						//return '本地卡本地消费积分';
						return comm.lang('common').L_L_ACCU_POINT  ;
					}
					case 'L_E_ACCU_POINT':{
						//return '本地卡异地消费积分';
						return comm.lang('common').L_E_ACCU_POINT ;
					}
					case 'E_L_ACCU_POINT':{
						//return '异地卡本地消费积分';
						return comm.lang('common').E_L_ACCU_POINT;
					}
					case 'HSEC_V_ACCU_POINT':{
						//return '异地卡本地消费积分';
						return comm.lang('common').HSEC_V_ACCU_POINT;
					}
					
					case 'CONSUME_POINT_ACCU_POINT':{
						//return '消费积分分配';
						return comm.lang('common').CONSUME_POINT_ACCU_POINT;
					}
					case 'L_L_CANCEL_ACCU_POINT':{
						//return '本地卡本地消费积分撤单';
						return comm.lang('common').L_L_CANCEL_ACCU_POINT;
					}
					case 'C_INET_HSB_RECHARGE':{
						//return '兑换互生币';
						return comm.lang('common').C_INET_HSB_RECHARGE;
					}
					case 'C_HSB_P_HSB_RECHARGE':{
						//return '代消费者购互生币';
						return comm.lang('common').C_HSB_P_HSB_RECHARGE;
					}
					case 'C_HSB_P_HSB_EARN_FEE':{
						//return '代兑手续费收入';
						return comm.lang('common').C_HSB_P_HSB_EARN_FEE;
					}
					case 'L_E_CANCEL_ACCU_POINT':{
						//return '本地卡异地消费积分撤单';
						return comm.lang('common').L_E_CANCEL_ACCU_POINT;
					}
					case 'E_L_CANCEL_ACCU_POINT':{
						//return '异地卡本地消费积分撤单';
						return comm.lang('common').E_L_CANCEL_ACCU_POINT;
					}
					case 'C_POINT_TO_CASH':{
						//return '积分转货币账户';
						return comm.lang('common').C_POINT_TO_CASH;						
						
					}
					case 'C_PRETRANS_CASH':{
						//return '货币转银行';
						return comm.lang('common').C_PRETRANS_CASH;						
						
					}
					case 'C_POINT_INVEST':{
						//return '积分投资';
						return comm.lang('common').C_POINT_INVEST;
					}
					case 'C_POINT_TO_HSB':{
						//return '积分转互生币';
						return comm.lang('common').C_POINT_TO_HSB;
					}
					case 'C_INVEST_BONUS':{
						return '投资分红';
					}
//					case 'F_DIST_HS_POINT':{
//						//return '积分互生分配';
//						return comm.lang('common').F_DIST_HS_POINT;
//					}
//					case 'F_DIST_EXT_POINT':{
//						//return '积分再增值分配';
//						return comm.lang('common').F_DIST_EXT_POINT;
//					}
					case 'C_PREPAY_POINT_TO_CASH':{
						//return '预付款转入';
						return comm.lang('common').C_PREPAY_POINT_TO_CASH;
					}
					
					case 'C_TRANS_REFUND':{
						//return '货币转银行失败转入';
						return comm.lang('common').C_TRANS_REFUND;
					}
					case 'C_INET_PAY_ANNUAL_FEE':{
						//return '网银缴年费';
						return comm.lang('common').C_INET_PAY_ANNUAL_FEE;
					}
					case 'C_HSB_PAY_ANNUAL_FEE':{
						//return '流通币缴年费';
						return comm.lang('common').C_HSB_PAY_ANNUAL_FEE;
					}
					case 'HSEC_CANCEL_V_ACCU_POINT':{
						//return '消费积分撤单';
						return comm.lang('common').HSEC_CANCEL_V_ACCU_POINT   ;
					}
					case 'C_INAL_PAY_ANNUAL_FEE':{
						//return '货币账户缴年费';
						return comm.lang('common').C_INAL_PAY_ANNUAL_FEE   ;
					}
					case 'INET_DISPOSE_PAY_POINT':{
						//return '网银预付积分充值';
						return comm.lang('common').INET_DISPOSE_PAY_POINT   ;
					}
					case 'INAL_DISPOSE_PAY_POINT':{
						//return '货币账户预缴预付积分';
						return comm.lang('common').INAL_DISPOSE_PAY_POINT   ;
					}
					case 'C_INET_SALES_PAY':{
						//return '网银申购工具';
						return comm.lang('common').C_INET_SALES_PAY   ;
					}
					case 'C_HSB_SALES_PAY':{
						//return '企业流通币购买工具';
						return comm.lang('common').C_HSB_SALES_PAY   ;
					}
					case 'C_INAL_SALES_PAY':{
						//return '货币账户申购工具';
						return comm.lang('common').C_INAL_SALES_PAY   ;
					}
					case 'C_HSB_TO_CASH':{
						//return '互生币转货币账户';
						return comm.lang('common').C_HSB_TO_CASH   ;
					}
					case 'C_HSB_DISPOSE_PAY_POINT':{
						//return '预缴积分预付款';
						return comm.lang('common').C_HSB_DISPOSE_PAY_POINT   ;
					}
					case 'C_ACCU_POINT_DEDUCT_TAX':{
						//return '企业消费积分纳税';
						return comm.lang('common').C_ACCU_POINT_DEDUCT_TAX   ;
					}
					case 'C_HS_POINT_DEDUCT_TAX':{
						//return '积分互生分配纳税';
						return comm.lang('common').C_HS_POINT_DEDUCT_TAX   ;
					}
					case 'C_EXT_POINT_DEDUCT_TAX':{
						//return '积分再增值分配纳税';
						return comm.lang('common').C_EXT_POINT_DEDUCT_TAX   ;
					}
					
//					case 'C_HSB_INCOME_DEDUCT_TAX':{
//						return '商城销售收入';
//					}
					case 'HSEC_HSB_EMALL_PAY_SETTLE':{
						return '商城销售收入';
					}
					case 'HSEC_HSB_STORE_PAY_SETTLE':{
						return '线下销售收入';
					}
					case 'HSEC_CASH_EMALL_PAY_SETTLE':{
						return '商城销售收入';
					}
					
					transCodeRender
					
					case 'C_CASH_INCOME_DEDUCT_TAX':{
						return '劳务费纳税';
					}
					case 'C_INAL_HSB_RECHARGE':{
						//return '企业货币账户购买流通币';
						return comm.lang('common').C_INAL_HSB_RECHARGE   ;
					}
					case 'C_TEMP_HSB_RECHARGE':{
						return comm.lang('common').C_TEMP_HSB_RECHARGE   ;
					}
					//管理费
					case 'T_PAY_FIRST_RES_FEE':{
						//return '托管企业申报首段资源扣款';
						return comm.lang('common').T_PAY_FIRST_RES_FEE   ;
					}
					case 'T_PAY_FOUND_RES_FEE':{
						//return '托管企业申报创业资源扣款';
						return comm.lang('common').T_PAY_FOUND_RES_FEE   ;
					}
					case 'T_PAY_WHOLE_RES_FEE':{
						//return '托管企业申报全部资源扣款';
						return comm.lang('common').T_PAY_WHOLE_RES_FEE   ;
					}
					case 'B_PAY_RES_FEE':{
						//return '成员企业申报扣款';
						return comm.lang('common').B_PAY_RES_FEE   ;
					}
					case 'S_PAY_RES_FEE':{
						//return '服务公司申报扣款';
						return comm.lang('common').S_PAY_RES_FEE   ;
					}
					case 'S_INAL_PAY_ANNUAL_FEE':{
						//return '货币账户支付';
						return comm.lang('common').S_INAL_PAY_ANNUAL_FEE   ;
					}
					case 'S_HSB_TO_DIRECT':{
						//return '转慈善救助';
						return comm.lang('common').S_HSB_TO_DIRECT   ;
					}
					case 'C_HSB_TO_DIRECT':{
						//return '转慈善救助';
						return comm.lang('common').C_HSB_TO_DIRECT   ;
					}
					
					//积分预付款账户收入
					case 'C_INAL_DISPOSE_PAY_POINT':{
						//return '货币账户预缴预付积分';
						return comm.lang('common').C_INAL_DISPOSE_PAY_POINT   ;
					}
					case 'C_INET_DISPOSE_PAY_POINT':{
						//return '网银预缴预付积分';
						return comm.lang('common').C_INET_DISPOSE_PAY_POINT   ;
					}
					case 'C_PREPAY_POINT_REFUND':{
						//return '积分预付款退回失败转入';
						return comm.lang('common').C_PREPAY_POINT_REFUND   ;
					}
					//积分预付款账户支出-消费积分
					case 'E_L_ACCU_POINT':{
						//return '异地卡本地消费积分';
						return comm.lang('common').E_L_ACCU_POINT   ;
					}
					
					//互生币支付撤单
					case 'HSEC_S_HSB_CCY_CANCEL_PAY':{
						return comm.lang('common').HSEC_S_HSB_CCY_CANCEL_PAY;
					}
					//互生币支付撤单
					case 'HSEC_S_HSB_DC_CANCEL_PAY':{
						return comm.lang('common').HSEC_S_HSB_DC_CANCEL_PAY;
					}
					//互生币支付退货
					case 'HSEC_S_HSB_CCY_STORE_REFUND':{
						return comm.lang('common').HSEC_S_HSB_CCY_STORE_REFUND;
					}
					//互生币支付退货
					case 'HSEC_S_HSB_DC_STORE_REFUND':{
						return comm.lang('common').HSEC_S_HSB_DC_STORE_REFUND;
					}
					//网上订单退货
					case 'HSEC_S_HSB_CCY_EMALL_REFUND':{
						return comm.lang('common').HSEC_S_HSB_CCY_EMALL_REFUND;
					}
					//网上订单退货
					case 'HSEC_S_HSB_DC_EMALL_REFUND':{
						return comm.lang('common').HSEC_S_HSB_DC_EMALL_REFUND;
					}
					//网上订单退货
					case 'HSEC_S_EBANK_EMALL_P_REFUND':{
						return comm.lang('common').HSEC_S_EBANK_EMALL_P_REFUND;
					}
					//网上订单退货
					case 'HSEC_EBANK_EMALL_V_REFUND':{
						return comm.lang('common').HSEC_EBANK_EMALL_V_REFUND;
					}
					//申报预缴
					case 'T_INET_PAY_FIRST_RES_FEE':{
						return comm.lang('common').T_INET_PAY_FIRST_RES_FEE;
					}
					//申报预缴
					case 'T_TEMP_PAY_FIRST_RES_FEE':{
						return comm.lang('common').T_TEMP_PAY_FIRST_RES_FEE;
					}
					//申报预缴
					case 'T_INET_PAY_FOUND_RES_FEE':{
						return comm.lang('common').T_INET_PAY_FOUND_RES_FEE;
					}
					//申报预缴
					case 'T_TEMP_PAY_FOUND_RES_FEE':{
						return comm.lang('common').T_TEMP_PAY_FOUND_RES_FEE;
					}
					//申报预缴
					case 'T_INET_PAY_WHOLE_RES_FEE':{
						return comm.lang('common').T_INET_PAY_WHOLE_RES_FEE;
					}
					//申报预缴
					case 'T_TEMP_PAY_WHOLE_RES_FEE':{
						return comm.lang('common').T_TEMP_PAY_WHOLE_RES_FEE;
					}
					//申报预缴
					case 'B_INET_PAY_RES_FEE':{
						return comm.lang('common').B_INET_PAY_RES_FEE;
					}
					//申报预缴
					case 'B_TEMP_PAY_RES_FEE':{
						return comm.lang('common').B_TEMP_PAY_RES_FEE;
					}
					
					//积分预付款账户支出-退回
					case 'C_PREPAY_POINT_TRANS':{
						//return '积分预付款退回';
						return comm.lang('common').C_PREPAY_POINT_TRANS;
					}
				    //货币转银行撤单-退回
					case 'C_CANCEL_PRETRANS_CASH':{
						//return '货币转银行撤单';
						return "货币转银行撤单";
					}

				//积分预付款平账
				case 'F_TICK_UP_PREPAY_POINT':{
					if(transType=='CPOINT')return '预付款平账转入';//预付款平账转入
					if(transType=='CASH')return '预付款平账转出';//预付款平账转出
				}
                //临账积分预付款
                case 'C_TEMP_DISPOSE_PAY_POINT':{
                    if(transType=='CPOINT')return '线下预缴';//线下预缴
                    if(transType=='CASH')return '';//
                }
					default:return transCode;
					}
				}
				
				//支付方式转换
				,payModeRender:function(payMode){
					switch(payMode){
					case 'CASHACCT':return "货币账户";
					case 'EBANK':return "网银支付";
					case 'HSB':return "互生币支付";
					case 'CASH':return "现金支付";
					case 'TEMP':return "转账汇款";
					case 'REMITTANCE':return "转账汇款";
					case 'RECEIVABLE':return "应收帐";
					default:return payMode;
					}
				}
				
				//渠道代码转换
				,channelCodeRender:function(channelCode){
					switch(channelCode){
					case 'WEB':return "网页终端";
					case 'POS':return "POS终端";
					case 'MCR':return "刷卡器终端";
					case 'MOBILE':return "移动终端";
					case 'HSPAD':return "互生平板";
					case 'SYS':return "系统终端";
					case 'IVR':return "语音终端";
					case 'SHOP':return "经营平台";
					default:return channelCode;
					}
				}
				
				//消费类别转换
				,consumeTypeRender:function(consumeType){
					switch(consumeType){
					case 'E-COMMERCE':return "网上商城";
					case 'SHOPNBC':return "营业点";
					default:return consumeType;
					}
				}
				
				//业务受理结果转换
				,resultRender:function(result){
					switch(result){
					case 'PENDING_PAY':return "处理中";
					case 'PAYING':return'银行处理中';
					case 'PAYED':return "成功";
					case 'PAY_FAIL':return "失败";
					case 'CANCELED':return "撤单";
					default:return result;
					}
				}
				
				,orderStatusRender:function(orderStatus){
					switch(orderStatus){
					case 'PENDING_PAY':{
						return '待支付';
					}
					case 'PENDING_PAY_CONFIRM':{
						return '待支付确认';
					}
					case 'PAY_CONFIRM':{
						return '已支付确认';
					}
					case 'PAYED':{
						return '已支付';
					}
					case 'PAY_FAIL':{
						return '支付失败';
					}
					case 'CANCELED':{
						return '已撤单';
					}
					case 'PENDING_CONFIRM':{
						return '待确认';
					}
					case 'CONFIRM':{
						return '已确认';
					}
					case 'ABANDONED':{
						return '已关闭';
					}
					default:return orderStatus;
					}
				}
				
				//是否消费积分扣除
				,isJifenkouchu:function(transCode){
					switch(transCode){
					case 'L_L_ACCU_POINT':return true;
					case 'L_E_ACCU_POINT':return true;
					case 'E_L_ACCU_POINT':return true;
					case 'HSEC_V_ACCU_POINT':return true;
					default:return false;
					}
				}
				
				//是否消费积分撤单
				,isJifenchedan:function(transCode){
					switch(transCode){
					case 'L_L_CANCEL_ACCU_POINT':return true;
					case 'L_E_CANCEL_ACCU_POINT':return true;
					case 'E_L_CANCEL_ACCU_POINT':return true;
					case 'HSEC_CANCEL_V_ACCU_POINT':return true;
					default:return false;
					}
				}
				
				//是否消费积分分配
				,isXiaofeijifen:function(transCode){
					switch(transCode){
					case 'C_ACCU_POINT_DEDUCT_TAX':return true;
					default:return false;
					}
				}
				
				//是否缴年费
				,isJiaonianfei:function(transCode){
					switch(transCode){
					case 'C_INET_PAY_ANNUAL_FEE':return true;
					case 'C_HSB_PAY_ANNUAL_FEE':return true;
					default:return false;
					}
				}
				
				//是否网银兑换互生币
				,isWangyinhsb:function(transCode){
					switch(transCode){
					case 'C_INET_HSB_RECHARGE':return true;
					default:return false;
					}
				}
				
				//是否货币兑换互生币
				,isHuobihsb:function(transCode){
					switch(transCode){
					case 'C_INAL_HSB_RECHARGE':return true;
					default:return false;
					}
				}
				
				//是否互生币预缴积分预付款
				,isHsbyujiao:function(transCode){
					switch(transCode){
					case 'C_HSB_DISPOSE_PAY_POINT':return true;
					default:return false;
					}
				}
				
				

				//jquery.validate扩展验证
				,addMethod_validator : function (){
					//验证表单自定义方法,用于对比两个日期，第一个日期不能大于第二个日期（用于对比结束日期）
					$.validator.addMethod("endDate",function(value,element,params){ 
						 var startDate = value;
				         var endDate = $(params).val();//参数是结束日期的ID       
				         if(startDate==""||endDate=="")return true;      
				         var reg = new RegExp('-','g');
				         startDate=new Date(Date.parse(startDate.replace(reg, "/")));
				         endDate=new Date(Date.parse(endDate.replace(reg, "/")));
				         return startDate<=endDate;        	
					},comm.lang('common').VEndDate  );
					
					
					//验证表单自定义方法,用于对比两个日期，第一个日期不能大于第二个日期（用于对比开始日期）
					$.validator.addMethod("beginDate",function(value,element,params){ 
						 var startDate = $(params).val();//参数是开始日期的ID    
				         var endDate = value;
				         if(startDate==""||endDate=="")return true;
				         var reg = new RegExp('-','g');
				         startDate=new Date(Date.parse(startDate.replace(reg, "/")));
				         endDate=new Date(Date.parse(endDate.replace(reg, "/")));
				         return startDate<=endDate;        	
					}, comm.lang('common').VBeginDate );
					
					//验证表单自定义方法,用于对比对比文本框的值不能大于某span或div里的值
					$.validator.addMethod("greater",function(value,element,params){
						 var val = $(params).text();//某span或div里的值    
						 if(val=="")val=$(params).val();
				         if(value==""||val==""||isNaN(value)||isNaN(val))return true;      
				         return parseInt(value)<=parseInt(val);        	
					},comm.lang('common').VGreater );
					
					//验证表单自定义方法,用于对比对比文本框的值不能小于某span或div里的值
					$.validator.addMethod("less",function(value,element,params){
						 var val = $(params).text();//某span或div里的值    
						 if(val=="")val=$(params).val();
				         if(value==""||val==""||isNaN(value)||isNaN(val))return true;      
				         return parseInt(value)>=parseInt(val);        	
					},comm.lang('common').VLess );
					
					//100的整数验证
					$.validator.addMethod("isNumTimes", function(value,element) {
					      if(value%100==0){
					    	  return true;
					      }else{
					    	  return false;
					      }
					}, comm.lang('common').VIsNumTimes );
					
					//验证表单自定义方法,用于对比对比文本框的值是否合法的货币格式(小数点左面最多9位，小数点右面最多2位)
					$.validator.addMethod("huobi",function(value,element,params){
						 if(params){
							 var reg=/^(([1-9]{1}\d{0,8})|0)(\.\d{1,2})?$/;
							 return reg.test(value);
						 }	 
						 return true;      	
					},comm.lang('common').VHuoBi );
					
					// 手机号码验证
					$.validator.addMethod("mobile", function(value, element) {
					    var length = value.length;
					    var mobile =  /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
					    return this.optional(element) || (length == 11 && mobile.test(value));
					}, comm.lang('common').VMobile );
					
				}
				,add_bankList:function(id,data){
					$(id).empty();//清空银行列表
					$(id).append('<option value="">-请选择-</option>');
					if(!data){
						$.each(comm.getCache("company","cache").bankList,function(i,o){
							$(id).append('<option value="'+o.bankCode+'" '
									+'>'+o.bankName+'</option>');
						});
					}else{
						$.each(data,function(i,o){
							$(id).append('<option value="'+o.bankCode+'" '
									+'>'+o.bankName+'</option>');
						});
					}
					$(id).combobox();
					$(".ui-autocomplete").css({
						"max-height":"120px",
						"overflow-y":"auto",
						"overflow-x":"hidden",
						"height":"120px",
						"border":"1px solid #CCC"
					});
					$(".combobox_style").find("a").attr("title","显示所有选项");
				},
				/**
				 * 转换终端名称
				 */
				convertTerminalName:function(terminalName){
					switch(terminalName){
					case 'MCR':{
						return '刷卡器终端';
					}
					case 'POS':{
						return 'POS终端';
					}
					case 'WEB':{
						return '网页终端';
					}
					case 'IOS':
					case 'ANDROID':
					case 'MOBILE':{
						return '移动终端';
					}
					case 'SHOP':{
						return '经营平台';
					}
					default:return terminalName;
					}
				}
				
	}

	return top;
});

