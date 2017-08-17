define(['text!accountManageTpl/csssdjzh/mxcx.html',
			'text!accountManageTpl/csssdjzh/mxcx_xhjfns.html',
			'text!accountManageTpl/csssdjzh/mxcx_jfhsfpns.html',
			'text!accountManageTpl/csssdjzh/mxcx_jfzzzfpns.html',
			'text!accountManageTpl/csssdjzh/mxcx_lwhns.html',
			'text!accountManageTpl/csssdjzh/mxcx_syfwhns.html',
	        "accountManageDat/csssdjzh/csssdjzh",
	        "accountManageLan"
			],function(mxcxTpl, 
					mxcx_xhjfnsTpl, 
					mxcx_jfhsfpnsTpl,
					mxcx_jfzzzfpnsTpl, 
					mxcx_lwhnsTpl, 
					mxcx_syfwhnsTpl,
					csssdjzh){
	var csssdjzhMxcx={		
		mxcx_xhjfnsTpl:mxcx_xhjfnsTpl,
		mxcx_jfhsfpnsTpl:mxcx_jfhsfpnsTpl,
		mxcx_jfzzzfpnsTpl:mxcx_jfzzzfpnsTpl,
		mxcx_lwhnsTpl:mxcx_lwhnsTpl,
		mxcx_syfwhnsTpl:mxcx_syfwhnsTpl,
		showPage : function(){
			$('#busibox').html(_.template(mxcxTpl)) ;			 
		 	//载入下拉参数  
		 	comm.initSelect($("#quickDate"),comm.lang("accountManage").quickDateEnum,null,"");
		 	comm.initSelect($("#optTerm"),comm.lang("accountManage").taxRateTypeEnum,null,null);
		 	
		 	//时间控件
			comm.initBeginEndTime("#search_startDate","#search_endDate");
			   
			//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr("optionvalue")];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_startDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
		   //查询
		   $("#btnQuery").click(function(){
			   var valid = comm.queryDateVaild("search_form");
			   if(!valid.form()){
				   return false;
			   }
			   csssdjzhMxcx.pageQuery();
		   });
		},
		/** 分页查询 */ 
		pageQuery:function(){
			var param = {"search_accType":$("#optTerm").attr("optionvalue"),"search_isTaxAccount":true,"search_beginDate":$("#search_startDate").val(),"search_endDate":$("#search_endDate").val()};
			csssdjzh.cityTaxDetailedPage(param,function(record, rowIndex, colIndex, options){
				//渲染列:交易时间
				if(colIndex == 1){
					return comm.formatDate(record.createdDate,'yyyy-MM-dd');
				}
				//渲染列:交易类型
				if(colIndex == 2){
					return comm.lang("accountManage").transTypeEnum[record["transType"]];
				}
				//渲染列:业务类型
				if(colIndex == 3){
					return comm.lang("accountManage").taxRateTypeEnum[record["accType"]];
				} 
				//渲染列：发生金额
				if(colIndex==4){
					return comm.formatMoneyNumber(record["amount"]);	
				}
				 
				//渲染列:详情
				var link = $('<a>查看</a>').click(function(e) {
					switch (record.transType) {
						case 'U26000'://积分再增值分配
							csssdjzhMxcx.commDetiledShow(mxcx_jfzzzfpnsTpl,record,true);
							break;
						case 'U16000'://积分互生分配
							csssdjzhMxcx.commDetiledShow(mxcx_jfhsfpnsTpl,record,true);
							break;
						case 'G21000'://消费积分分配
							csssdjzhMxcx.commDetiledShow(mxcx_xhjfnsTpl,record,true);
							break;
						case 'U36000'://劳务费收入分配
							csssdjzhMxcx.commDetiledShow(mxcx_lwhnsTpl,record,true);
							break;
						case 'G15000'://互生币商业收入
							csssdjzhMxcx.commDetiledShow(mxcx_syfwhnsTpl,record,true);
							break;
						default:
							break;
					}
				}.bind(this)) ;
				return link;
		});
	},
	//公共详情显示
	commDetiledShow :function(html,record,isQuery,width){
		width = comm.isNotEmpty(width)?width:480;
		var transNo=comm.navNull(record.sourceTransNo,record.transNo);
		var transType=record.transType;
		var transTypeName=comm.lang("accountManage").transTypeEnum[record["transType"]];
		var businessTypeName=comm.lang("accountManage").businessTypeEnum[record["businessType"]];
		var amount=comm.formatMoneyNumber(record.amount);
		var transDate=comm.formatDate(record.createdDate,'yyyy-MM-dd hh:mm:ss');
		var exchangeRate = '';
		var currencyNameCn;
		cacheUtil.findCacheSystemInfo(function(res){
			exchangeRate=comm.formatTransRate(res.exchangeRate);
			currencyNameCn=res.currencyNameCn;
		});
		if(comm.isNotEmpty(html)){
			if(isQuery){
				var param = {
						transNo:record.relTransNo,
						transType:record.transType
				};
				if(record.transSys=='PS'||param.transNo=='gjx'){
					param.transNo=record.transNo;
				}
				csssdjzh.getAccOptDetailed(param,function(resp){
					if(resp){
						var obj = resp.data.data;
						var data = null;
						if(comm.isNotEmpty(obj)){
							        data=obj;
									data.transNo=transNo,
									data.transDate=transDate,
									data.transTypeName=transTypeName,
									data.businessTypeName=businessTypeName,
									data.currencyNameCn=currencyNameCn;
									data.exchangeRate = exchangeRate;
									data.amount=amount;
									data.channel=comm.navNull(comm.lang("common").accessChannel[obj.channel]),
									
									data.remark=comm.navNull(record.remark)
							$('#ssss_mxcx_xq_dialog > p').html(_.template(html,data));
							$('#ssss_mxcx_xq_dialog').dialog({width:width,title:transTypeName+"详情",closeIcon:true}); 
						}else{
							comm.yes_alert(comm.lang("accountManage").dataNotExist);
						}
					}
				});
			}else{
				var data = {
						transNo:transNo,
						transDate:transDate,
						transTypeName:transTypeName,
						businessTypeName:businessTypeName,
				        amount:amount,
				        currencyNameCn:currencyNameCn,
				        exchangeRate:exchangeRate,
				        remark:comm.navNull(record.remark)
				};
				$('#ssss_mxcx_xq_dialog > p').html(_.template(html,data));
				$('#ssss_mxcx_xq_dialog').dialog({width:width,title:transTypeName+"详情",closeIcon:true}); 
			}
		}
	}
	}
	return csssdjzhMxcx;
}); 

 