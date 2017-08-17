define(['text!invoiceTpl/dqptjsfp/dqptjsfp.html',
		'text!invoiceTpl/dqptjsfp/dqptjsfp_ck.html',
		'text!invoiceTpl/dqptjsfp/dqptjsfp_xz.html',
		'text!invoiceTpl/dqptjsfp/dqptjsfp_qs.html',
		'text!invoiceTpl/dqptjsfp/dqptjsfp_jq.html',
		'invoiceSrc/qyxx/qyxx_tab',
		'invoiceDat/invoice',
		'invoiceLan'],function(dqptjsfpTpl, dqptjsfp_ckTpl, dqptjsfp_xzTpl, dqptjsfp_qsTpl, dqptjsfp_jqTpl, qyxx_tab,dataModoule){
	var dqptjsfpFun = {
		json_xz : null,
		invoiceAmount:null,
		invoiceCode:null,
		invoiceNo:null,
		zt:null,
		dqptjsfpBsGrid:null,
		showPage : function(){
			$('#busibox').html(_.template(dqptjsfpTpl))	;		
			
			//初始化下拉
			 comm.initSelect('#search_status', comm.lang("invoice").delivery_status);
			
			 //查询分页
			$("#jsfp_query").click(function(){  
				var params=$("#jsfp_form").serializeJson();
				$.extend(params,{"search_status":$("#search_status").attr("optionvalue")});
				dqptjsfpFun.dqptjsfpBsGrid = dataModoule.listInvoice("searchTable",params,dqptjsfpFun.detail,dqptjsfpFun.edit,dqptjsfpFun.add);
			});		
			
			//新增发票跳转按钮
			$('#addInvoices_btn').click(function(){
				dataModoule.findMainInfoDefaultBankByResNo(null,function(res){	
					 var obj=res.data.mainInfo;
					 var bankInfo=res.data.bankInfo;
					 if(!bankInfo){
						comm.error_alert("该企业无默认银行账户",400)
						return false;
					  }
					 obj.bankBranchName=bankInfo.bankName;
					 obj.bankNo=bankInfo.bankAccNo;	
					 obj.openContent="服务收益";
					 dqptjsfpFun.xinZhen(obj);	
				});
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			var link1 = null;
			if(colIndex == 6){
				link1 = $('<a>查看</a>').click(function(e) {					
					//dataModoule.getInvoiceById({invoiceId:record.invoiceId,invoiceMaker:1},function(res){
						dqptjsfpFun.chaKan(record);	
					//});									
				});
			}else if(colIndex == 0){
				link1 = $('<a>' + record.resNo + '</a>').click(function(e) {
					dqptjsfpFun.ck_qyxx(record.custId);						
				});
			}else if(colIndex == 5){
				return comm.lang("invoice").invoice_status[record.status];
			}else if(colIndex == 3){
				return comm.formatMoneyNumber(record.allAmount);
			}
			return link1;
		},		
		edit : function(record, rowIndex, colIndex, options){
			var link2 = null;
			if(colIndex == 6){
				if(record.status == 3){
					link2 = $('<a>签收</a>').click(function(e) {
						dqptjsfpFun.qianShou(record,function(){
							dqptjsfpFun.dqptjsfpBsGrid.refreshPage();
						});					
					});
				}				
			}
			return link2;
		},		
		add : function(record, rowIndex, colIndex, options){
			var link3 = null;
			if(colIndex == 6){
				if(record.status == 3){
					link3 = $('<a>拒签</a>').click(function(e) {
						dqptjsfpFun.juQian(record, function(){
							dqptjsfpFun.dqptjsfpBsGrid.refreshPage();
						});				
					});
				}				
			}
			return link3;
		},
		/**
		 * 查看详情
		 */
		chaKan: function(record){
			var that = this;
			comm.liActive_add($('#ck'));
			
			//获取发票详情
			dataModoule.getInvoiceById({invoiceId:record.invoiceId,invoiceMaker:1},function(res){
				var obj = res.data;
				$('#dqptjsfp_ckTpl').html(_.template(dqptjsfp_ckTpl, obj));
				that.showTpl($('#dqptjsfp_ckTpl'));
				
				var json_ck = obj.invoiceLists;
				if(json_ck!=null && json_ck.length!=0){
					comm.getEasyBsGrid('searchTable_ck',json_ck,dqptjsfpFun.chaKandetail);
				}
				
				//查看返回
				$('#ck_back_dqptjsfp').click(function(){
					comm.liActive($('#dqptjsfp'), '#ck, #xz, #ckqyxx');
					that.showTpl($('#dqptjsfpTpl'));	
					dqptjsfpFun.dqptjsfpBsGrid.refreshPage();
				});
				
				//签收
				$('#qianShou_btn').click(function(){
					that.qianShou(obj,function(){
						obj.status=4;
						dqptjsfpFun.chaKan(obj);
					});	
				});
				
				//拒签
				$('#juQian_btn').click(function(){
					that.juQian(obj, function(){
						obj.status=5;
						dqptjsfpFun.chaKan(obj);
					});
				});
			});		
		},
		chaKandetail : function(record, rowIndex, colIndex, options){
			if(colIndex == 3){
				return comm.formatMoneyNumber(record.invoiceAmount);
			}
		},
		/**
		 * 签收
		 */
		qianShou : function(obj,callBack){
			$('#dqptjsfp_qs').html(_.template(dqptjsfp_qsTpl,obj));
			/*弹出框*/
			$( "#dqptjsfp_qs" ).dialog({
				title:"选择签收方式",
				width:"360",
				height:"230",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"确定":function(){
						//验证签收方式
						var $receiveWay=$("#receiveWay").attr("optionvalue");
						if(!comm.isNotEmpty($receiveWay)){
							comm.i_alert("请选择签收方式！");
							return false;
						}
						
						var params = $("#qs_form").serializeJson();
						$.extend(params,{"receiveWay":$receiveWay});
						$( this ).dialog( "destroy" );
						dataModoule.signInvoice(params,function(res){
							comm.yes_alert("签收发票完成",400);
							callBack();
						});		
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			comm.initSelect('#receiveWay', comm.lang("invoice").receive_way_select,null,null,null,75);	
		},
		/**
		 * 拒签
		 */
		juQian : function(obj, callBack){
			$('#dqptjsfp_jq').html(dqptjsfp_jqTpl).dialog({
				title : '拒签原因',
				width : '380',
				modal : true,
				buttons : {
					'确定' : function(){
						if(!dqptjsfpFun.validateRefuse().form()){
							return;
						}
						var params = {
							invoiceId : obj.invoiceId,
							status : 5,
							invoiceMaker : 1,
							refuseRemark:$("#refuse").val()
						}
						dataModoule.signInvoice(params,function(res){
							comm.yes_alert("拒签发票完成",400);
							$("#dqptjsfp_jq").dialog('destroy');
							callBack();
						});
					},
					'取消' : function(){
						$(this).dialog('destroy');
					}	
				}	
			});
		},
		xinZhen : function(obj){
			comm.liActive_add($('#xz'));
			$('#dqptjsfp_xzTpl').html(_.template(dqptjsfp_xzTpl,obj));
			dqptjsfpFun.showTpl($('#dqptjsfp_xzTpl'));
			
			json_xz=[];
			invoiceAmount=new Array();
			invoiceCode=new Array();
			invoiceNo=new Array();
			zt=new Array();
			
			$("#billingEnt").blur(function(){
				var resNo=$("#billingEnt").val();
				if(resNo!=null && resNo!=""){
					dataModoule.mainCompanyInforByResNo({resNo:resNo},function(res){	
							var obj=res.data;
								$("#custNameId").text(obj.entName);
								$("#billingEnt").val(resNo);
					});
			   }
			});						
			dqptjsfpFun.fpxz_init();
		},
		fpxz_init : function(){
			/*新增发票事件*/
			$('#jsfp_xzbtn').click(function(){	
				if(!dqptjsfpFun.xzfpValid()){return;}
				var param = $('#dqptjsfp_xzform').serializeJson(),
					obj = {};
				obj.th_1 = param.invoiceCode;
				obj.th_2 = param.invoiceNo;
				obj.th_3 = param.invoiceAmount;
				obj.zt = '1';
				
				json_xz.push(obj);						
				dqptjsfpFun.fpjl_talbe_show(json_xz);	
				invoiceAmount.push(param.invoiceAmount);
				invoiceCode.push(param.invoiceCode);
				invoiceNo.push(param.invoiceNo);
				zt.push(obj.zt);
				
				$('#invoiceCode, #invoiceNo, #invoiceAmount').val('');
						
			});
			
			//返回事件
			$('#xz_back_btn').click(function(){
				comm.liActive($('#dqptjsfp'), '#ck, #xz, #ckqyxx');
				dqptjsfpFun.showTpl($('#dqptjsfpTpl'));	
			});
			
			//新增发票
			$('#xz_confirm_btn').click(function(){
				var params=$("#dqptjsfp_xzform").serializeJson();
				$.extend(params,{"invoiceNos":invoiceNo.join(',')});
				$.extend(params,{"invoiceCodes":invoiceCode.join(',')});
				$.extend(params,{"invoiceAmounts":invoiceAmount.join(',')});
				$.extend(params,{"zts":zt.join(',')});
				dataModoule.custOpenInvoice(params,function(res){
					comm.yes_alert("操作成功！",400);
					$('#dqptjsfp').click();
				});
			});
		},
		fpjl_talbe_show : function(json){
				var autoLoad=true;
				if(!json || json.length == 0){
					autoLoad=false;
				}
				gridObj_xz = $.fn.bsgrid.init('searchTable_xz', {				 
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				autoLoad:autoLoad,
				localData:json,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var link1 = null;
						
//						if(record.zt == '0'){
//							link1 = '<span class="gray">已删除</span>';
//						}
//						else{
							link1 = $('<a>删除</a>').click(function(e) {
							
								dqptjsfpFun.shanChu(rowIndex);
	
							}.bind(this) ) ;	
//						}
						
						return link1;
						
					}.bind(this)
				}
				
			});			
		},
		shanChu : function(row){
			comm.confirm({
				imgFlag : true,
				imgClass : 'tips_ques',
				content : '确认删除此发票？',
				callOk : function(){
					json_xz.splice(row,1);
					zt.splice(row,1);
					invoiceAmount.splice(row,1);
					invoiceCode.splice(row,1);
					invoiceNo.splice(row,1);
					dqptjsfpFun.fpjl_talbe_show(json_xz);
				}
			});	
		},
		ck_qyxx : function(custId){
			var that = this;
			comm.liActive_add($('#ckqyxx'));
			this.showTpl($('#ckqyxxTpl'));
			qyxx_tab.showPage(custId);
			
			$('#back_btn').click(function(){
				comm.liActive($('#dqptjsfp'), '#ck, #xz, #ckqyxx');
				that.showTpl($('#dqptjsfpTpl'));
			});		
		},
		showTpl : function(tplObj){
			$('#dqptjsfpTpl, #dqptjsfp_ckTpl, #dqptjsfp_xzTpl, #ckqyxxTpl').addClass('none');
			tplObj.removeClass('none');
		},
		xzfpValid : function(){
			return comm.valid({
				left:100,
				top:20 ,
				formID:'#dqptjsfp_xzform',
				rules : {
					invoiceCode : {
						required : true
					},
					invoiceNo : {
						required : true	
					},
					invoiceAmount : {
						required : true,
						huobi :true
					}
		
				},
				messages : {
					invoiceCode : {
						required : comm.lang("invoice")[35124]
					},
					invoiceNo : {
						required : comm.lang("invoice")[35126]
					},
					invoiceAmount : {
						required : comm.lang("invoice")[35125]
					}
				}
	 
			});		
		},
		/**
		 * 表单校验
		 */
		validateRefuse : function(){
			return $("#refuse_form").validate({
				rules : {
					refuse : {
						rangelength : [0, 300],
					}
				},
				messages : {
					refuse : {
						rangelength : comm.lang("invoice").invoiceRefuse,
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
	
	return dqptjsfpFun;
	
});