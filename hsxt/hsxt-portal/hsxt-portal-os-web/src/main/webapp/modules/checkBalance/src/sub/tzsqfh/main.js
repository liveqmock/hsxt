define(['text!checkBalanceTpl/sub/tzsqfh/tzsqfh.html',
		'text!checkBalanceTpl/sub/tzsqfh/tzsqfh_opt.html',
		'checkBalanceDat/checkBalance', 
		'checkBalanceLan' 
		],function(tzsqfhTpl, tzsqfh_optTpl,checkBalanceDat){
	return {
		self :null,
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(tzsqfhTpl) ;
			
			self = this;
			
			/*日期控件*/			
			$("#tzsqfh_queryList_timeRange_start").datepicker({
				dateFormat : 'yy-mm-dd',
				onSelect : function(dateTxt, inst){
					var d = dateTxt.replace('-', '/');
					$("#tzsqcs_queryList_timeRange_end").datepicker('option', 'minDate', new Date(d));	
				}
			});
			$("#tzsqfh_queryList_timeRange_end").datepicker({
				dateFormat : 'yy-mm-dd',
				onSelect : function(dateTxt, inst){
					var d = dateTxt.replace('-', '/');
					$("#tzsqfh_queryList_timeRange_start").datepicker('option', 'maxDate', new Date(d));	
				}
			});
			
			/*下拉列表*/
			$("#tzsqfh_queryList_kjrq").selectList({
				borderWidth : 1,
				borderColor : '#999',
				options:[
					{name:'近一日',value:'today'},
					{name:'近一周',value:'week'},
					{name:'近一月',value:'month'},
					{name:'近三月',value:'3month'}
				]
			}).change(function(e){
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE',
					'3month' : 'get3MonthSE'
				}[$(this).attr('optionValue')];
				var arr = method ? comm[method]() : ['', ''];
				$("#tzsqfh_queryList_timeRange_start").val(arr[0]);
				$("#tzsqfh_queryList_timeRange_end").val(arr[1]);
			});
			
			// 加载开始、结束日期
			$("#tzsqfh_queryList_timeRange_start, #tzsqfh_queryList_timeRange_end").datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				maxDate : comm.getCurrDate()
			});

			self.getCheckBalances();
			
			// 按钮事件绑定
			$('#fh_btnCheckBalanceQuery').click(function() {
				self.getCheckBalances();
			});				
		},
		
		
		getCheckBalances:function(){
			params = {custId : $.cookie('custId')};
			params['status'] = 1;
			
			var startDate = $("#tzsqfh_queryList_timeRange_start").val();
			if (startDate != '')
				params['startDate'] = startDate;
			
			var resNo = $("#tzsqfh_queryList_hsh").val();
			if (resNo != '')
				params['resNo'] = resNo;
			
			var endDate = $("#tzsqfh_queryList_timeRange_end").val();
			if (endDate != '')
				params['endDate'] = endDate;
			
			params['date'] = new Date().getTime();
			// 分页查询			
			gridObj = $.fn.bsgrid.init('queryList_tzsqfh', {				 
				url : comm.domainList.osWeb + comm.UrlList["getCheckBalances"],
				otherParames : params,
				
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				
				operate : {	
					del: function(record, rowIndex, colIndex, options){
						if(colIndex == 9){
							var opt_btn = $('<a>复核</a>').click(function(e) {
							this.opt_fuHe(record);
							
							}.bind(this) ) ;
							
							return opt_btn;
						}
					}.bind(this),
					detail : function(record, rowIndex, colIndex, options){
						if(colIndex == 3){
							if(record['acctType']=='20120'){
								return '互生币帐户定向消费币';
							}else if(record['acctType']=='20110'){
								return '互生币帐户流通币帐户';
							}
							else if(record['acctType']=='20130'){
								return '互生币慈善救助基金帐户';
							}
							else if(record['acctType']=='10110'){
								return '积分帐户';
							}
							else if(record['acctType']=='30110'){
								return '货币帐户';
							}
							else if(record['acctType']=='10510'){
								return '积分城市税收对接帐户';
							}
							else if(record['acctType']=='30310'){
								return '货币城市税收对接帐户';
							}
							else if(record['acctType']=='20610'){
								return '互生币城市税收对接帐户';
							}
							else if(record['acctType']=='10410'){
								return '积分投资帐户';
							}
						}
						else if(colIndex == 4){
							if(record['checkType']=='1'){
								return '增';
							}else{
								return "减";
							}
						}
						else if(colIndex == 8){
							if(record['status']=='0'){
								return '待初审';
							}else if(record['status']=='1'){
								return "待复核";
							}else if(record['status']=='2'){
								return "调账成功";
							}else if(record['status']=='3'){
								return "初审驳回";
							}else if(record['status']=='4'){
								return "复核驳回";
							}
						}
						else if(colIndex == 9){
						var opt_btn = $('<a>详情</a>').click(function(e) {
							
							this.opt_detail(record);
							
						}.bind(this) ) ;
                         
						return opt_btn;
						}
					}.bind(this)
				}
			});
		},
		
		opt_detail : function(obj){
			$('#tzsqfhDlg').html(tzsqfh_optTpl)
				.dialog({
					title:"详情",
					width:"1000",
					height:"800",
					modal:true,
					closeIcon : true,
					buttons:{ 
						"关闭":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
			
			/*表单值初始化*/
			$('#tzsqfh_opt_sqrq').val(obj.createDate);
			$('#tzsqfh_opt_hsh').val(obj.resNo);
			$('#tzsqfh_opt_dwmc').val(obj.name);
			
			if(obj.acctType == "20120"){
				$('#tzsqfh_opt_zhlx').val("互生币帐户定向消费币");
			}else if(obj.acctType == "20110"){
				$('#tzsqfh_opt_zhlx').val("互生币帐户流通币帐户");
			}else if(obj.acctType == "20130"){
				$('#tzsqfh_opt_zhlx').val("互生币慈善救助基金帐户");
			}else if(obj.acctType == "10110"){
				$('#tzsqfh_opt_zhlx').val("积分帐户");
			}else if(obj.acctType == "30110"){
				$('#tzsqfh_opt_zhlx').val("货币帐户");
			}else if(obj.acctType == "10510"){
				$('#tzsqfh_opt_zhlx').val("积分城市税收对接帐户");
			}else if(obj.acctType == "30310"){
				$('#tzsqfh_opt_zhlx').val("货币城市税收对接帐户");
			}else if(obj.acctType == "20610"){
				$('#tzsqfh_opt_zhlx').val("互生币城市税收对接帐户");
			}else if(obj.acctType == "10410"){
				$('#tzsqfh_opt_zhlx').val("积分投资帐户");
			}
			
			if(obj.checkType == 1){
				$('#tzsqfh_opt_zjx').val("增");
		    }
		    else{
		    	$('#tzsqfh_opt_zjx').val("减");
		    }
			
			$('#tzsqfh_opt_tzje').val(obj.amount);
			$('#tzsqfh_opt_tzrq').val(obj.balanceDate);
			if(obj.status == 1){
				$('#tzsqfh_opt_zt').val("待复核");
		    }else if(obj.status == 0){
		    	$('#tzsqfh_opt_zt').val("待初审");
		    }else if(obj.status == 2){
		    	$('#tzsqfh_opt_zt').val("调账成功");
		    }else if(obj.status == 3){
		    	$('#tzsqfh_opt_zt').val("初审驳回");
		    }else if(obj.status == 3){
		    	$('#tzsqfh_opt_zt').val("复核驳回");
		    }
			
			$('#tzsqfh_opt_tzyy').html(obj.remark);
			
			/*end*/
			
			$('#tzsqfh_ckspjl_div').removeClass('none');
			
			$('#tzsqfh_spjl_ck_btn').click(function(){
				$('#tzsqfh_spjl_div').removeClass('none');
				var params = {};
				params['balanceId'] = obj.id;
				checkBalanceDat.getCheckBalanceResults('tzsqfh_spjl_talbe',params, self.detail);
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 1){
				if(record['checkType']==1){
					return '初审';
				}
				else{
					return "复核";
				}
			}
		},
		
		opt_fuHe : function(obj){
			$('#tzsqfhDlg').html(tzsqfh_optTpl)
				.dialog({
					title:"复核",
					width:"1000",
					modal:true,
					closeIcon : true,
					buttons:{ 
						"确定":function(){
							// 调用后台保存复核数据
							var checkResult = $('input:radio[name="tzsqfh_opt_fhjg"]:checked').val();
							var approvalParam={
									    "checker":$.cookie('custName'),
										"remark":$("#tzsqfh_opt_fhyj").val(),
	                                    "checkResult":checkResult,
	                                    "resultType":2,
	                                    "checkBalanceId":$("#checkBalanceId").val()
							 };
							
							checkBalanceDat.addCheckBalanceResult(approvalParam,function(rsp){
								alert("保存成功");
								$('#tzsqfhDlg').dialog( "destroy" );
								$('#fh_btnCheckBalanceQuery').click();
							 });
						},
						"取消":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
			
			/*表单值初始化*/
			$('#tzsqfh_opt_sqrq').val(obj.createDate);
			$('#tzsqfh_opt_hsh').val(obj.resNo);
			$('#tzsqfh_opt_dwmc').val(obj.name);
			$('#checkBalanceId').val(obj.id);
			if(obj.acctType == "20120"){
				$('#tzsqfh_opt_zhlx').val("互生币帐户定向消费币");
			}else if(obj.acctType == "20110"){
				$('#tzsqfh_opt_zhlx').val("互生币帐户流通币帐户");
			}else if(obj.acctType == "20130"){
				$('#tzsqfh_opt_zhlx').val("互生币慈善救助基金帐户");
			}else if(obj.acctType == "10110"){
				$('#tzsqfh_opt_zhlx').val("积分帐户");
			}else if(obj.acctType == "30110"){
				$('#tzsqfh_opt_zhlx').val("货币帐户");
			}else if(obj.acctType == "10510"){
				$('#tzsqfh_opt_zhlx').val("积分城市税收对接帐户");
			}else if(obj.acctType == "30310"){
				$('#tzsqfh_opt_zhlx').val("货币城市税收对接帐户");
			}else if(obj.acctType == "20610"){
				$('#tzsqfh_opt_zhlx').val("互生币城市税收对接帐户");
			}else if(obj.acctType == "10410"){
				$('#tzsqfh_opt_zhlx').val("积分投资帐户");
			}
			
			if(obj.checkType == 1){
				$('#tzsqfh_opt_zjx').val("增");
		    }
		    else{
		    	$('#tzsqfh_opt_zjx').val("减");
		    }
			
			$('#tzsqfh_opt_tzje').val(obj.amount);
			$('#tzsqfh_opt_tzrq').val(obj.balanceDate);
			if(obj.status == 0){
				$('#tzsqfh_opt_zt').val("待初审");
		    }else if(obj.status == 1){
		    	$('#tzsqfh_opt_zt').val("待复核");
		    }else if(obj.status == 2){
		    	$('#tzsqfh_opt_zt').val("调账成功");
		    }else if(obj.status == 3){
		    	$('#tzsqfh_opt_zt').val("初审驳回");
		    }else if(obj.status == 4){
		    	$('#tzsqfh_opt_zt').val("复核驳回");
		    }
			/*end*/	
			$('#tzsqfh_opt_tzyy').html(obj.remark);
			$('#tzsqfh_fhyj').removeClass('none');
		}
	};
}); 

 