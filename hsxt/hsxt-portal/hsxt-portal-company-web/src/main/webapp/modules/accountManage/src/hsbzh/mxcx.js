define([ 'text!accountManageTpl/hsbzh/mxcx/mxcx.html',
				"accountManageDat/hsbzh/hsbzh",'accountManageDat/accountManage',
				"accountManageLan" ],
		function(tpl,hsbzh,accountManage) {
			return hsbzhMxcx = {
				showPage : function() {
					$('#busibox').html(tpl);
					//载入下拉参数
				 	comm.initSelect($("#quickDate"),comm.lang("accountManage").quickDateEnum,null);
				 	comm.initSelect($("#subject"),comm.lang("accountManage").businessEnum,null,0,{name:'全部',value:'0'});
				 	var entResType=comm.getRequestParams().entResType;
				 	if(entResType==3){
				 	 comm.initSelect($("#optHsbType"),comm.lang("accountManage").hsbAccTypeEnum,null,"20110");
				 	}else{
				 		comm.initSelect($("#optHsbType"),comm.lang("accountManage").hsbAccTypeEnum2,null,"20110");
				 	}
				 	//时间控件
					comm.initBeginEndTime("#search_beginDate",'#search_endDate');
					   
					//快捷选择日期事件
					$("#quickDate").bind("change", function () {
						var method = {
							'today' : 'getTodaySE',
							'week' : 'getWeekSE',
							'month' : 'getMonthSE'
						}[$(this).attr("optionvalue")];
						var arr = method ? comm[method]() : ['', ''];
						$("#search_beginDate").val(arr[0]);
						$("#search_endDate").val(arr[1]);
					});
				   //查询
				   $("#btnQuery").click(function(){
					   var valid = comm.queryDateVaild('hsb_form');
					   
					   //互生币类型验证
					   $("#optHsbType").rules("add", {  
						   required : true,  
			                messages : {  
			                	required : "请选择互生币类型!"  
			                }  
			            });  
					   
					   if(!valid.form())
					   {
							return;
					   }
					   hsbzhMxcx.pageQuery();
				   });
				},
				
				/** 分页查询 */ 
				pageQuery:function(){
					var params = {
						"search_businessType":comm.navNull($("#subject").attr('optionValue'),'0'),
						"search_accType":comm.navNull($("#optHsbType").attr("optionValue"),'20110'),
						"search_beginDate":comm.navNull($("#search_beginDate").val()),
						"search_endDate":comm.navNull($("#search_endDate").val()),
						};
					accountManage.detailedPage('tableDetail',params,hsbzhMxcx.detail);
					
				},
				detail : function(record, rowIndex, colIndex, options){
						var transTypeName=hsbzhMxcx.getBusiType(record.transSys,record.transType,record.businessType);
						//渲染列:交易时间
						if(colIndex == 0){
							return comm.formatDate(record.createdDate,'yyyy-MM-dd');
						}
						//渲染列:交易类型
						if(colIndex == 1){
							return transTypeName;
						}
						
						//渲染列:业务类型
						if(colIndex == 2){
							
							return comm.lang("accountManage").businessTypeEnum[record["businessType"]];
						 } 
						
						//渲染列:发生积分数
						if(colIndex == 3){
							return comm.formatMoneyNumber(record.amount);
						 } 
						
						//渲染列:业交易后余额
						if(colIndex == 4){
							if(record.accBalanceNew==null){
								return "--";
							}else{
								return comm.formatMoneyNumber(record.accBalanceNew);	
							}
						} 
					},
					getBusiType : function(transSys,transType,businessType){
						var transTypeName= comm.lang("accountManage").transTypeEnum[transType];
						if(!transTypeName && transSys=='PS'){
								var tr=transType.charAt(1)+transType.charAt(3);
								if(tr=='10'){
									transTypeName="积分金额扣除";
								}else if(tr=='11'){
									transTypeName="网上订单退货";
								}else if(tr=='12'){
									transTypeName="网上订单退货";
								}else if(tr=='20'){
									transTypeName="积分金额扣除";
								}else if(tr=='21'){
									transTypeName="消费积分撤单";
								}else if(tr=='22'){
									transTypeName="消费积分退货";
								}else if(tr=='13'){
									transTypeName="积分金额扣除";
								}else if(tr=='14'){
									transTypeName="积分金额扣除";
								}
						}
						return transTypeName;
				}
			}
		});