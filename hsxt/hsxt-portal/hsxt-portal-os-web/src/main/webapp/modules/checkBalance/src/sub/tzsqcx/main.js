define(['text!checkBalanceTpl/sub/tzsqcx/tzsqcx.html',
		'text!checkBalanceTpl/sub/tzsqcx/tzsqcx_opt.html',
		'checkBalanceDat/checkBalance', 
		'checkBalanceLan' 
		],function(tzsqcxTpl, tzsqcx_optTpl,checkBalanceDat){
	return {
 
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(tzsqcxTpl) ;
			
			var self = this;
		
			
			$("#tzsqcx_queryList_zt").selectList({
				borderWidth : 1,
				borderColor : '#999',
				options:[
					{name:'全部',value:'',selected : true},
					{name:'待初审',value:'0'},
					{name:'待复核',value:'1'},
					{name:'调账驳回',value:'3,4'},
					{name:'调账成功',value:'2'}
				]
			}).change(function(e){
				console.log($(this).val() );
			});
			/*end*/
			
			/*日期控件*/			
			$("#tzsqcx_queryList_timeRange_start").datepicker({
				dateFormat : 'yy-mm-dd',
				onSelect : function(dateTxt, inst){
					var d = dateTxt.replace('-', '/');
					$("#tzsqcx_queryList_timeRange_end").datepicker('option', 'minDate', new Date(d));	
				}
			});
			$("#tzsqcx_queryList_timeRange_end").datepicker({
				dateFormat : 'yy-mm-dd',
				onSelect : function(dateTxt, inst){
					var d = dateTxt.replace('-', '/');
					$("#tzsqcx_queryList_timeRange_start").datepicker('option', 'maxDate', new Date(d));	
				}
			});
			
			/*下拉列表*/
			$("#tzsqcx_queryList_kjrq").selectList({
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
				$("#tzsqcx_queryList_timeRange_start").val(arr[0]);
				$("#tzsqcx_queryList_timeRange_end").val(arr[1]);
			});
			
			// 加载开始、结束日期
			$("#tzsqcx_queryList_timeRange_start, #tzsqcx_queryList_timeRange_end").datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				maxDate : comm.getCurrDate()
			});
			
			/*end*/	
			self.getCheckBalances();
			
			// 按钮事件绑定
			$('#cx_btnCheckBalanceQuery').click(function() {
				self.getCheckBalances();
			});
		},
		
		getCheckBalances:function(){
			params = {custId : $.cookie('custId')};
			
			var status = $("#tzsqcx_queryList_zt").attr("optionValue");
			if (status != ''){
				params['status'] = status;
			}
			var startDate = $("#tzsqcx_queryList_timeRange_start").val();
			if (startDate != '')
				params['startDate'] = startDate;
			
			var resNo = $("#tzsqcx_queryList_hsh").val();
			if (resNo != '')
				params['resNo'] = resNo;
			
			var endDate = $("#tzsqcx_queryList_timeRange_end").val();
			if (endDate != '')
				params['endDate'] = endDate;
			
			params['date'] = new Date().getTime();
			// 分页查询			
			gridObj = $.fn.bsgrid.init('queryList_tzsqcx', {				 
				url : comm.domainList.osWeb + comm.UrlList["getCheckBalances"],
				otherParames : params,
				
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				
				operate : {	
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
			var self = this;
			$('#tzsqcxDlg').html(tzsqcx_optTpl)
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
			$('#tzsqcx_opt_sqrq').val(obj.createDateString);
			$('#tzsqcx_opt_tzrq').val(obj.balanceDateString);
			$('#tzsqcx_opt_hsh').val(obj.resNo);
			$('#tzsqcx_opt_dwmc').val(obj.name);
			
			if(obj.acctType == "20120"){
				$('#tzsqcx_opt_zhlx').val("互生币帐户定向消费币");
			}else if(obj.acctType == "20110"){
				$('#tzsqcx_opt_zhlx').val("互生币帐户流通币帐户");
			}else if(obj.acctType == "20130"){
				$('#tzsqcx_opt_zhlx').val("互生币慈善救助基金帐户");
			}else if(obj.acctType == "10110"){
				$('#tzsqcx_opt_zhlx').val("积分帐户");
			}else if(obj.acctType == "30110"){
				$('#tzsqcx_opt_zhlx').val("货币帐户");
			}else if(obj.acctType == "30310"){
				$('#tzsqcx_opt_zhlx').val("货币城市税收对接帐户");
			}else if(obj.acctType == "20610"){
				$('#tzsqcx_opt_zhlx').val("互生币城市税收对接帐户");
			}else if(obj.acctType == "10410"){
				$('#tzsqcx_opt_zhlx').val("积分投资帐户");
			}
			
			if(obj.checkType == 1){
				$('#tzsqcx_opt_zjx').val("增");
		    }
		    else{
		    	$('#tzsqcx_opt_zjx').val("减");
		    }
			
			$('#tzsqcx_opt_tzje').val(obj.amount);
			if(obj.status == 0){
				$('#tzsqcx_opt_zt').val("待初审");
		    }else if(obj.status == 1){
		    	$('#tzsqcx_opt_zt').val("待复核");
		    }else if(obj.status == 2){
		    	$('#tzsqcx_opt_zt').val("调账成功");
		    }else if(obj.status == 3){
		    	$('#tzsqcx_opt_zt').val("初审驳回");
		    }else if(obj.status == 4){
		    	$('#tzsqcx_opt_zt').val("复核驳回");
		    }
			
			$('#tzsqcx_opt_tzyy').html(obj.remark);
			
			/*end*/
			
			$('#spjl_ck_btn').click(function(){
				$('#spjl_div').removeClass('none');
				var params = {};
				params['balanceId'] = obj.id;
				checkBalanceDat.getCheckBalanceResults('spjl_talbe',params, self.detail);
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
		}
	};
}); 

 