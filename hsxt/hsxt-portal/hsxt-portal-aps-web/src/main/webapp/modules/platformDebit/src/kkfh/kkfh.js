define(['text!platformDebitTpl/kkfh/kkfh.html',
		'text!platformDebitTpl/kkfh/kkfh_ck.html',
		'text!platformDebitTpl/kkfh/kkfh_fh.html',
        'platformDebitDat/platformDebit',
        'commDat/common',
        'platformDebitLan'
		], function(kkfhTpl, kkfh_ckTpl, kkfh_fhTpl,platformDebit,common){
	var kkfhFun = {
		kkfhBsGrid:null,		//扣款复核分页
		showPage : function(){
			$('#busibox').html(kkfhTpl);
			
			//分页查询
			$("#bjglqry_btn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				
				kkfhFun.pageQuery();
			});
			
			//初始化文本值
			comm.initSelect("#txtQueryState",comm.lang("platformDebit").deductionStatusEnum);
			comm.initSelect("#quickDate",comm.lang("platformDebit").quickDateEnum);
			comm.initBeginEndTime("#search_startDate","#search_endDate");
			
			//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE',
					'month3' : 'get3MonthSE'
				}[$(this).attr("optionvalue")];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_startDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
		},
		chaKan : function(obj){
			$('#kkfh_ck_dlg').html(kkfh_ckTpl).dialog({
				title : '查看详情',
				width : 900,
				height: 300,
				modal : true,
				closeIcon : true,
				buttons : {
					'关闭' : function(){
						$(this).dialog('destroy');	
					}
				}	
			});
			
			$('#sqrq_txt').text(obj.applyDate);
			$('#hsh_txt').text(obj.entResNo);
			$('#dwmc_txt').text(comm.navNull(obj.entCustName));
			$('#kkrq_txt').text(comm.navNull(obj.apprDate));
			$('#kkyy_txt').html(comm.navNull(obj.applyRemark));
			$('#kkrq_txt').html(comm.navNull(obj.apprDate));
			$('#zt_txt').html(comm.lang("platformDebit").deductionStatusEnum[obj.status]);
			$('#kkje_txt').html(comm.formatMoneyNumber(obj.amount));
		},
		/** 复核 */
		fuhe : function(obj){
			$('#kkfh_fh_dlg').html(kkfh_fhTpl).dialog({
				title : '复核',
				width : 900,
				height: 350,
				modal : true,
				closeIcon : true,
				buttons : {
					'确定' : function(){
						var dialogSelf=this;
						
						//表单参数验证
						if(!kkfhFun.kkfhValid()){
							return ;
						}
						
						//复核入参
						var apprParam={
								applyId:obj.applyId,
								status:$("input[name='radStatus']:checked").val(),
								apprRemark:$("#txtApprRemark").val()
						};
						
						//复核
						platformDebit.apprHsbDeduction(apprParam,function(rsp){
							comm.alert({imgClass : 'tips_yes',content : '扣款复核成功',callOk:function(){
								$(dialogSelf).dialog('destroy');	
								kkfhFun.kkfhBsGrid.refreshPage();
							}});
						});
					},
					'取消' : function(){
						$(this).dialog('destroy');	
					}
				}	
			});
			
			$('#fh_sqrq_txt').text(obj.applyDate);
			$('#fh_hsh_txt').text(obj.entResNo);
			$('#fh_dwmc_txt').text(comm.navNull(obj.entCustName));
			$('#fh_kkje_txt').text(comm.formatMoneyNumber(obj.amount));
			$('#fh_kkrq_txt').text(comm.navNull(obj.apprDate));
			$('#fh_zt_txt').text(comm.lang("platformDebit").deductionStatusEnum[obj.status]);	
			$('#fh_kkyy_txt').html(obj.applyRemark);
		},
		/** 分页查询 */
		pageQuery:function(){
			//查询参数
			var queryParam = {
				"search_queryStartDate" : $("#search_startDate").val(),
				"search_queryEndDate" : $("#search_endDate").val(),
				"search_queryResNo" : $("#txtQueryResNo").val()
			};
			
			kkfhFun.kkfhBsGrid = platformDebit.deductReviewPage("searchTable", queryParam,
			function(record, rowIndex, colIndex, options){
				//状态
				if(colIndex==3){
					return comm.formatMoneyNumber(record["amount"]);
				}
				if(colIndex==5){
					var text = $("<p>"+record["applyRemark"]+"</p>").text();
					return "<span title="+text+">"+text+"</span>";
				}
				//状态
				if(colIndex==6){
					return comm.lang("platformDebit").deductionStatusEnum[record["status"]];
				}
				if(colIndex == 7){
					var link1 = $('<a>查看</a>').click(function(e) {
						kkfhFun.chaKan(record);
					});
					return link1;
				}
			},
			function(record, reowIndex, colIndex, options){
				if(colIndex == 7){
					var link1 = $('<a>复核</a>').click(function(e) {
						kkfhFun.fuhe(record);
					});
					return link1;
				}
			},
			function(record, rowIndex, colIndex, options){
				if(colIndex == 8){
					return comm.workflow_operate('拒绝受理', '扣款复核业务', function(){
						common.workTaskRefuseAccept({"bizNo":record.applyId},function(rsp){
							comm.alert({imgClass: 'tips_yes' ,content:comm.lang("platformDebit")[22000],callOk:function(){
								kkfhFun.kkfhBsGrid.refreshPage();
							}});
						});
					});
				}
			},
			function(record, rowIndex, colIndex, options){
				if(colIndex == 8){
					return comm.workflow_operate('挂起', '扣款复核业务', function(){
						require(["workoptSrc/gdgq"],function(gdgq){
							gdgq.guaQi(record.applyId,kkfhFun.kkfhBsGrid);
						});
					});
				}
			});
		},
		guaQiFun:function(){
			
		}
		,
		/** 扣款复核验证 */
		kkfhValid : function(){
			return comm.valid({
				left:100,
				top:20 ,
				formID:'#kkfhform',
				rules : {
					radStatus : {
						required : true
					}
				},
				messages : {
					radStatus : {
						required : "请选择复核状态",
					}
				}
			});	
		}
	}
	return kkfhFun;
});
