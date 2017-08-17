define(['text!invoiceTpl/dqptkfpywcx/dqptkfpywcx.html',
		'text!invoiceTpl/dqptkfpywcx/dqptkfpywcx_ck.html',
		'text!invoiceTpl/dqptkfpywcx/dqptkfpywcx_qs.html',
		'text!invoiceTpl/dqptkfpywcx/dqptkfpywcx_xgps.html',
		'invoiceSrc/qyxx/qyxx_tab',
		'invoiceDat/invoice',
		'invoiceLan'],function(dqptkfpywcxTpl, dqptkfpywcx_ckTpl, dqptkfpywcx_qsTpl, dqptkfpywcx_xgpsTpl, qyxx_tab,dataModoule){
	var dqptkpywcx = {
		showPage : function(){
			$('#busibox').html(_.template(dqptkfpywcxTpl));
			
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			//初始化查询状态下拉值
			comm.initSelect('#search_status', comm.lang("invoice").delivery_status3);
	
			//查询
			$("#platkp_query").click(function(){
				if(!comm.queryDateVaild("platkp_form").form()){
					return;
				}
				var params=$("#platkp_form").serializeJson();
				$.extend(params,{"search_status":$("#search_status").attr("optionvalue")});
				dqptkpywcx.searchTable = dataModoule.listInvoice("searchTable",params,dqptkpywcx.detail,dqptkpywcx.edit);
			});
		},
		detail : function(record, rowIndex, colIndex, options){	
			if(colIndex == 0){
				var link1 = $("<a title='" + record.resNo +"'>" + record.resNo + "</a>").click(function(e) {
					dqptkpywcx.ck_qyxx(record.custId);
				});
				return link1;
			}else if(colIndex == 3){
				return comm.formatMoneyNumber(record.allAmount);
			}else if(colIndex == 4){
				var invoiceType = comm.lang("invoice").invoice_type[record.invoiceType];
				invoiceType = invoiceType ? invoiceType : '';
				return "<span title='"+invoiceType+"'>" + invoiceType + "</span>";
			}else if(colIndex == 6){
				return comm.lang("invoice").post_way[record.postWay];
			}else if(colIndex == 9){
				return comm.lang("invoice").invoice_status[record.status];
			}else if(colIndex == 10){
				var result = comm.getNameByEnumId(record.receiveWay, comm.lang("invoice").receive_way);
				return "<span title='"+result+"'>" + result + "</span>"; 
			}else if(colIndex == 12){
				var link2 = $('<a>查看</a>').click(function(e) {
					dataModoule.getInvoiceById({invoiceId:record.invoiceId,invoiceMaker:0},function(res){
						dqptkpywcx.chaKan(res.data);
					});					
				});
				return link2;
			}
		},				
		edit : function(record, rowIndex, colIndex, options){			
			if(colIndex == 12 && record.status == 3){
				var link1 = $('<a>签收</a>').click(function(e) {
					dqptkpywcx.qianShou(record);
				});
				return link1;
			}	
			if(colIndex == 12 && record.status== 2){
				var link1 = $('<a>配送</a>').click(function(e) {
					dataModoule.getInvoiceById({invoiceId:record.invoiceId,invoiceMaker:0},function(res){
						dqptkpywcx.xgps(res.data);
					});		
				}) ;
				return link1;
			}				
		},
		ck_qyxx : function(custId){
			var that = this;
			comm.liActive_add($('#ckqyxx'));
			this.showTpl($('#ckqyxxTpl'));
			qyxx_tab.showPage(custId);
			
			$('#back_btn').click(function(){
				comm.liActive($('#dqptkfpywcx_dqptkfpywcx'), '#ckqyxx, #ckfpxx');
				that.showTpl($('#dqptkfpywcxTpl'));
			});
						
		},
		chaKan : function(obj){
			var that = this;
			comm.liActive_add($('#ckfpxx'));
			obj.postWayName=comm.lang("invoice").post_way[obj.postWay];
			obj.invoiceTypeName=comm.lang("invoice").invoice_type[obj.invoiceType];
			$('#dqptkfpywcx_ckTpl').html(_.template(dqptkfpywcx_ckTpl, obj));
			
			this.showTpl($('#dqptkfpywcx_ckTpl'));
			var json_ck = obj.invoiceLists;
			if(json_ck!=null && json_ck.length!=0){
//				var gridObj_ck = $.fn.bsgrid.init('searchTable_ck', {				 
//					pageSizeSelect: true ,
//					pageSize: 10 ,
//					stripeRows: true,  //行色彩分隔 
//					displayBlankRows: false ,   //显示空白行
//					localData:json_ck					
//				});
				comm.getEasyBsGrid('searchTable_ck',json_ck,dqptkpywcx.chaKandetail);
			}

			if(obj.status == '4'){
				$('#qsfs_name').text('签收方式');
				$('#qsfs_txt').text(comm.lang("invoice").receive_way[obj.receiveWay]);	
			}
			else{
				$('#qsfs_name').text('');
				$('#qsfs_txt').text('');	
			}
			
			$('#ck_back_dqptkfpywcx').click(function(){
				comm.liActive($('#dqptkfpywcx_dqptkfpywcx'), '#ckqyxx, #ckfpxx');
				that.showTpl($('#dqptkfpywcxTpl'));		
			});
				
		},
		chaKandetail : function(record, rowIndex, colIndex, options){
			if(colIndex == 3){
				return comm.formatMoneyNumber(record.invoiceAmount);
			}
		},
		qianShou : function(obj){			
			$('#dqptkfpywcx_qs').html(_.template(dqptkfpywcx_qsTpl,obj));
			/*弹出框*/
			$( "#dqptkfpywcx_qs" ).dialog({
				title:"选择签收方式",
				width:"360",
				height:"260",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"确定":function(){
						//签收方式非空验证
						if(comm.isEmpty($("#receiveWay").attr("optionvalue")))
						{
							comm.alert({
								content: '请选择签收方式!',
								imgClass: 'tips_error'
							});
							return false ;
						}
						var params = $("#qs_form"). serializeJson();
						$.extend(params,{"receiveWay":$("#receiveWay").attr("optionvalue")});
						$( this ).dialog( "destroy" );
						dataModoule.signInvoice(params,function(res){
							comm.yes_alert("签收发票完成",400);
							$("#platkp_query").click();	
						});		
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			comm.initSelect('#receiveWay', comm.lang("invoice").receive_way);	
		},
		/**
		 * 配送
		 */
		xgps : function(obj){
			var that = this;
			comm.liActive_add($('#xgps'));
			obj.postWayName=comm.lang("invoice").post_way[obj.postWay];
			obj.invoiceTypeName=comm.lang("invoice").invoice_type[obj.invoiceType];
			obj.allAmount = comm.formatMoneyNumberAps(obj.allAmount);
			$('#dqptkfpywcx_xgpsTpl').html(_.template(dqptkfpywcx_xgpsTpl, obj));
			that.showTpl($('#dqptkfpywcx_xgpsTpl'));
			
			var json_xgps = obj.invoiceLists;
			try{
				//金额格式化防止报错
				json_xgps[0].invoiceAmount = comm.formatMoneyNumberAps(json_xgps[0].invoiceAmount);
			}catch(ex){}
			
			if(json_xgps!=null && json_xgps.length!=0){			
				var gridObj_xgps = $.fn.bsgrid.init('searchTable_xgps', {				 
					pageSizeSelect: true ,
					pageSize: 10 ,
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false ,   //显示空白行
					localData:json_xgps,
					
				});
			}

			//配送
			$('#xgps_submit').click(function(){
				//配送方式
				var $postWay = comm.navNull($("input[name='postWay']:checked").val())
				if($postWay!="1" && !dqptkpywcx.distributionValida()){
					return false;
				}
				
				var params = $("#xgps_form"). serializeJson();
				dataModoule.changePostWay(params,function(res){
					comm.yes_alert("修改配送完成",400);
					$("#xgps_back_dqptkfpywcx").click();	
				});		
			});
			
			//返回
			$('#xgps_back_dqptkfpywcx').click(function(){
				comm.liActive($('#dqptkfpywcx_dqptkfpywcx'), '#ckqyxx, #ckfpxx, #xgps');
				that.showTpl($('#dqptkfpywcxTpl'));
				dqptkpywcx.searchTable.refreshPage();				
			});
			
		},
		showTpl : function(tplObj){
			$('#dqptkfpywcxTpl, #ckqyxxTpl, #dqptkfpywcx_ckTpl, #dqptkfpywcx_xgpsTpl').addClass('none');
			tplObj.removeClass('none');	
		},
		/**
		 * 配送数据验证
		 */
		distributionValida:function(){
			return comm.valid({
				formID : '#xgps_form',
				rules : {
					postWay : {
						required : true
					},
					expressName : {
						required : true
						
					},
					trackingNo : {
						required : true
					}
				},
				messages : {
					postWay : {
						required : comm.lang("invoice").select_postWay_prompt
					},
					expressName : {
						required : comm.lang("invoice").input_expressName_prompt
						
					},
					trackingNo : {
						required :  comm.lang("invoice").input_trackingNo_prompt
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		}
	} 
	
	return dqptkpywcx;
});