define([
"text!receivablesmanageTpl/dfkdd/dfkdd.html",
"text!receivablesmanageTpl/dfkdd/dfkdd_ck.html"/*,
"text!receivablesmanageTpl/dfkdd/dd_xg.html",
"text!receivablesmanageTpl/dfkdd/dd_xg_2.html"*/
,"receivablesmanageDat/receivablesmanage",
"receivablesmanageLan"
],function(dfkddTpl, dfkdd_ckTpl/*,dd_xgTpl,dd_xg_2Tpl*/,receivablesmanage){
	var dfkddFun = {
		showPage : function(){
			$('#busibox').html(_.template(dfkddTpl));	
			
			// 日期控件
			comm.initBeginEndTime('#search_startDate','#search_endDate');
			
			// 订单类型 
			comm.initSelect("#order_type",comm.lang("receivablesmanage").OrderType);
			
			// 查询事件
			$("#btnQuery").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				dfkddFun.pageQuery();
			});
			
			//双签类型
			comm.initSelect("#verificationMode_gb",comm.lang("receivablesmanage").checkTypeEnum);
			/*下拉列表*/
			$("#verificationMode_gb").change(function(e){
				var val = $(this).attr('optionValue');
				switch(val){
					case '1':
						$('#fhy_userName_gb, #fhy_password_gb').removeClass('none');
						$('#verificationMode_prompt_gb').addClass('none');
						break;	
						
					case '2':
						$('#fhy_userName_gb, #fhy_password_gb').addClass('none');
						$('#verificationMode_prompt_gb').removeClass('none');
						break;
						
					case '3':
						$('#fhy_userName_gb, #fhy_password_gb').addClass('none');
						$('#verificationMode_prompt_gb').removeClass('none');
						break;
				}
			});
		},
		chaKan : function(orderNo){
			//查找订单详情
			var getParam={"orderNo":orderNo};
			receivablesmanage.getOrderDetail(getParam,function(rsp){
				$('#dfkdd_detail').html(_.template(dfkdd_ckTpl,rsp.data));	
				
				if(rsp.data.orderOperator){
					if(rsp.data.orderOperator.indexOf("(") > -1){
						$("#tOrderOper").text(rsp.data.orderOperator);
					}else{
						//订单操作员
						var params = {};
						params.orderCustType = rsp.data.custType;
						params.orderOperator = rsp.data.orderOperator;
						receivablesmanage.findOrderOperator(params, function(res){
							var operator = res.data;
							if(operator){
								$("#tOrderOper").text(comm.removeNull(operator.userName)+"("+comm.removeNull(operator.realName)+")");
							}
						});
					}
				}
				
				$('#dfkdd_detail').removeClass('none');
				$('#dfkddTpl').addClass('none');
				$('#dfkdd_ckTpl').removeClass('none');
				$('#busibox').addClass('none');
				comm.liActive_add($('#ckdd'));
				//返回按钮
				$('#back_dfkdd').click(function(){
					//隐藏头部菜单
					$('#dfkdd_detail').addClass('none');
					$('#dfkdd_ckTpl').addClass('none');
					$('#dfkddTpl').removeClass('none');
					$('#busibox').removeClass('none');
					$('#ckdd').addClass("tabNone").find('a').removeClass('active');
					$('#dfkdd').find('a').addClass('active');
				});
				
			});
		},
		gbdd : function(th1,orderType){
			var confirmObj = {	
		 	imgFlag:true ,             //显示图片
			width:550,		 	 
			content : '你确定要关闭订单号为：'+th1+'的订单？',			 
			callOk :function(){
				$( "#orderClose_content" ).dialog({
					title:"复核信息",
					width:"400",
					height:"250",
					modal:true,
					buttons:{ 
						"确定":function(){
							//1、验证有效数据
							if (!dfkddFun.validateData()) {
								return false;
							} 
							
							/** 双签验证 */
							comm.verifyDoublePwd($("#txtCheckAccount").val(),$("#txtCheckPwd").val(),$("#verificationMode_gb").val(),
									function(){
								//2、参数组合
								var closeParam={"orderNo":th1,"orderType":orderType};
								
								//3、关闭订单
								receivablesmanage.closeOrder(closeParam,function(rsp){
									$("#txtCheckAccount,#txtCheckPwd,#txtCheckViews").val("");  //清空数据文本
									$("#orderClose_content").dialog("destroy");
									$("#btnQuery").click();
								});
							});
								
						},
						"取消":function(){
							$("#orderClose_content").dialog("destroy");
						}
					}
				});					
			}
		}
		comm.confirm(confirmObj);
		},
		/** 分页查询 */
		pageQuery:function(){
			//查询参数
			var queryParam={
						"search_hsResNo":$("#txtHsResNo").val(),"search_startDate":$("#search_startDate").val(),
						"search_endDate":$("#search_endDate").val(),"search_orderType":$("#order_type").attr("optionvalue"),
						"search_custNameCondition":$("#txtCustName").val(),"search_orderStatus":1
						};
			var gridObj= receivablesmanage.businessOrderPage("searchTable",queryParam,function(record, rowIndex, colIndex, options){
				//交易类型
				if(colIndex==3){
					return  comm.lang("receivablesmanage").OrderType[gridObj.getColumnValue(rowIndex,"orderType")];
	            }
				
				//订单金额，根据订单支付方式判断
				if(colIndex==5){
	                if(record.payChannel==200){
	                	return comm.formatMoneyNumber(record.orderHsbAmount);
	                }else {
	                	return comm.formatMoneyNumber(record.orderCashAmount);
	                }
	            }

				//折合应付本币(人民币)
				if(colIndex==6){
                    if(record.payChannel==200){
                    	//return comm.formatMoneyNumber(record.orderHsbAmount*parseFloat(record.exchangeRate));
                    	return comm.formatMoneyNumber(record.orderHsbAmount);
                    }else {
                    	//return comm.formatMoneyNumber(record.orderCashAmount*parseFloat(record.exchangeRate));
                    	return comm.formatMoneyNumber(record.orderCashAmount);
                    }
                }

				if(colIndex==7){
					var link1 = $('<a>查看</a>').click(function(e) {
						dfkddFun.chaKan(gridObj.getColumnValue(rowIndex,"orderNo"));
					}) ;
					return link1 ;
				}
				
			},
			null,
			null,
			function(record, rowIndex, colIndex, options){
				if(colIndex==8 && colIndex==7 && dfkddFun.orderTypeIsExist(record["orderType"])){
					var link2 = $('<a>关闭订单</a>').click(function(e){
						dfkddFun.gbdd(record["orderNo"],record["orderType"]);		
					});	
					return link2;
				}
			});
		},
		//验证订单类型存在
		orderTypeIsExist:function(type){
			//匹配类型
			var orderType=["103","104","105","106","107"];
			//验证是否匹配上
			if(orderType.indexOf(type)>-1){
				return true;
			}
			return false;
		},
		/** 订单关闭数据格式验证 */
		validateData : function() {
			return comm.valid({
				formID : '#gbdx_form',
				rules : {
					txtCheckAccount : {
						required : true,
					},
					txtCheckPwd : {
						required : true,
					}
				},
				messages : {
					txtCheckAccount : {
						required :  comm.lang("receivablesmanage")[34025]
					},
					txtCheckPwd : {
						required : comm.lang("receivablesmanage")[34026]
					}
				}
			});
		},
	}
	return dfkddFun;
});