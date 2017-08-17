define(['text!invoiceTpl/dqptspfp/dqptspfp.html',
		'text!invoiceTpl/dqptspfp/dqptspfp_ck.html',
		'text!invoiceTpl/dqptspfp/dqptspfp_kp.html',
		'text!invoiceTpl/dqptspfp/shenqingdan.html',
		'text!invoiceTpl/dqptspfp/dqptspfp_sp_dialog.html',
		'invoiceSrc/qyxx/qyxx_tab',
		'invoiceDat/invoice',
		'invoiceLan'],function(dqptspfpTpl, dqptspfp_ckTpl, dqptspfp_kpTpl,shenqingdanTpl,dqptspfp_sp_dialogTpl, qyxx_tab,dataModoule){
	var dqptspfpFun = {
		json_xz : null,
		invoiceAmount:null,
		invoiceCode:null,
		invoiceNo:null,
		zt:null,
		showPage : function(){
			$('#busibox').html(_.template(dqptspfpTpl));
			
			//初始化下拉选择框
			comm.initSelect('#search_status', comm.lang("invoice").delivery_status2);
			comm.initSelect('#search_bizType', comm.lang("invoice").bizType, 185);
			
			//分页查询
			$("#platsp_query").click(function(){
				var params=$("#platsp_form").serializeJson();
				$.extend(params,{"search_status":$("#search_status").attr("optionvalue")});
				$.extend(params,{"search_bizType":$("#search_bizType").attr("optionvalue")});
				dataModoule.listInvoice("searchTable",params,dqptspfpFun.detail,dqptspfpFun.edit,dqptspfpFun.queryApply);
			});
	
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 0){
				var link1 =  $('<a>' + record.resNo + '</a>').click(function(e){
					dqptspfpFun.ck_qyxx(record.custId);
				});	
				return link1;
			}else if(colIndex==2){
				return comm.lang("invoice").custType[record.custType];
			}else if(colIndex==3){
				var result = comm.lang("invoice").bizType[record.bizType];
				return "<span title='"+result+"'>" + result + "</span>"; 
			}else if(colIndex==4){
				return comm.formatMoneyNumber(record.allAmount);
			}else if(colIndex == 6){
				return comm.lang("invoice").delivery_status2[record.status];
			}else if(colIndex == 7){
		        	if(record.status==0){
		        		var link2 = $('<a>开发票</a>').click(function(e) {
						      dqptspfpFun.kaiFaPiao(record);
			            });
			            return link2;
			        }else{
		        		var link3 = $('<a>查看</a>').click(function(e) {
							dataModoule.getInvoiceById({invoiceId:record.invoiceId,invoiceMaker:0},function(res){
								dqptspfpFun.chaKan(res.data);
							});	
					    });	
					   return link3;
				   }		
			}			
		},
		edit : function(record, rowIndex, colIndex, options){
				if(colIndex == '7') {
		        	if(record.status== 0){
						var link2 = $('<a>审批驳回</a>').click(function(e) {
							dqptspfpFun.spbh(record);
						});
						return link2;
			         }	
				}
        },
        /**
         * 查看申请单
         */
        queryApply:function(record, rowIndex, colIndex, options){
			var link3 = null;
			if(colIndex == '0'){return;};
			if(colIndex == '7'){
				if (record.status == 0) {
					link3 = $('<a>查看申请单</a>').click(function(e) {
						dqptspfpFun.chaKan1(record);
					});
				}/* else {
					link3 = $('<a>查看</a>').click(function(e) {
						dqptspfpFun.chaKan(record);
					});
				}*/
			}	
			return link3;
        },
        /**
         * 查看申请详单
         */
		chaKan1 : function(obj){
			//查找发票内容
			dataModoule.getInvoiceById({invoiceId:obj.invoiceId,invoiceMaker:0},function(invoiceRsp){
				//查找企业信息
				dataModoule.allCompanyInfor({customId:obj.custId},function(entRsp){
					//打印
					$('#dqptspfp_cksqd').html(_.template(shenqingdanTpl,{"invoice":invoiceRsp.data,"ent":entRsp.data})).dialog({
						title:"查看申请单",
						width:"800",
						modal:true,
						closeIcon: true,
						buttons:{ 
							"打印":function(){
								 var prnhtml = $('#dqptspfp_cksqd').html();    
								 winname = window.open('', "_blank",'');
								 
								 //动态添加css文件
								 var head =winname.document.getElementsByTagName('head')[0];
						         head.appendChild(dqptspfpFun.crateCssLink("/resources/css/base.css"));
						         head.appendChild(dqptspfpFun.crateCssLink("/resources/css/style.css"));
						         //填充打印内容
						         winname.document.body.innerHTML=prnhtml;
						         //打印
						         winname.print(); 
							},
							"取消":function(){
								 $(this).dialog("destroy");
							}
						}
					});
				});
			});
		},
		/**
		 * 动态创建css对象
		 */
		crateCssLink:function(path){
			var locationUrl=window.location.href;
			var currentUrl=locationUrl.substring(0,locationUrl.lastIndexOf('/'));
			 
		    var link = winname.document.createElement('link');
	        link.href = currentUrl+path;
	        link.rel = 'stylesheet';
	        link.type = 'text/css';
	        return link;
		},
        chaKan : function(obj){
			var that = this;
			comm.liActive_add($('#ck'));
			obj.postWayName=comm.lang("invoice").post_way[obj.postWay];
			obj.invoiceTypeName=comm.lang("invoice").invoice_type[obj.invoiceType];
			$('#dqptspfp_ckTpl').html(_.template(dqptspfp_ckTpl, obj));
			that.showTpl($('#dqptspfp_ckTpl'));
			
			//获取审批驳回经办人
			if(obj.status==1){
				comm.groupOperInfo(obj.updatedBy,function(rsp){
					$("#tdUpdateBy").html(rsp);
				})
			}
            
			/*var json_ck = obj.invoiceLists;
			if(json_ck!=null && json_ck.length!=0){
				var gridObj_ck = $.fn.bsgrid.init('searchTable_ck', {				 
					pageSizeSelect: true ,
					pageSize: 10 ,
					stripeRows: true,  // 行色彩分隔
					displayBlankRows: false ,   // 显示空白行
					localData:json_ck
					
				});
			}*/
			
			
			//返回
			$('#ck_back_dqptspfp').click(function(){
				comm.liActive($('#dqptspfp'), '#ck, #lrfp, #ckqyxx');
				that.showTpl($('#dqptspfpTpl'));		
			});
		},
		kaiFaPiao : function(obj){
			var that = this;
			$('#dqptspfp_kpTpl').html(_.template(dqptspfp_kpTpl, obj));	
			comm.liActive_add($('#lrfp'));
			this.showTpl($('#dqptspfp_kpTpl'));
			
			json_xz=[];
			invoiceAmount=new Array();
			invoiceCode=new Array();
			invoiceNo=new Array();
			zt=new Array();
			/*新增发票事件*/
			$('#xzfp_btn').click(function(){
				if(!dqptspfpFun.xzfpValid()){return;}
				var param = $('#invoice_addform').serializeJson(),
					obj = {};
				if(isNaN(param.invoiceAmount)){
					 comm.error_alert("请输入数字",400);
					 return;
				}else if(param.invoiceAmount<=0){
					 comm.error_alert("请输入正数",400);
					 return;
				}
				obj.th_1 = param.invoiceCode;
				obj.th_2 = param.invoiceNo;
				obj.th_3 = param.invoiceAmount;
				obj.zt = '1';
				
				json_xz.push(obj);						
				dqptspfpFun.fpjl_talbe_show(json_xz);	
				invoiceAmount.push(param.invoiceAmount);
				invoiceCode.push(param.invoiceCode);
				invoiceNo.push(param.invoiceNo);
				zt.push(obj.zt);
				
				$('#invoiceCode, #invoiceNo, #invoiceAmount').val('');
						
			});
			
			$('#invoice_submit').click(function(){
				var params=$("#invoice_addform").serializeJson();
				$.extend(params,{"invoiceNos":invoiceNo.join(',')});
				$.extend(params,{"invoiceCodes":invoiceCode.join(',')});
				$.extend(params,{"invoiceAmounts":invoiceAmount.join(',')});
				$.extend(params,{"zts":zt.join(',')});
				dataModoule.platApproveInvoice(params,function(res){
					comm.yes_alert("开发票成功",400);
					$('#back_dqptspfp').click();
					$("#platsp_query").click();		
				});
			});
			
			$('#back_dqptspfp').click(function(){
				comm.liActive($('#dqptspfp'), '#lrfp, #ck, #ckqyxx');
				that.showTpl($('#dqptspfpTpl'));	
				$('#invoiceCode').tooltip().tooltip("destroy");
				$('#invoiceNo').tooltip().tooltip("destroy");
				$('#invoiceAmount').tooltip().tooltip("destroy");
			});
		},
		fpjl_talbe_show : function(json){
			comm.getEasyBsGrid('searchTable_kp', json, function(record, rowIndex, colIndex, options){
				if(colIndex == 3){
					return comm.formatMoneyNumber(record.th_3);
				}
				return $('<a>删除</a>').click(function(e) {
					var row = (options.curPage-1)*10+rowIndex;
					dqptspfpFun.shanChu(row);	
				});
			});
		},
	shanChu : function(row){
		comm.confirm({
			imgFlag : true,
			imgClass : 'tips_ques',
			content : '确认删除此发票？',
			callOk : function(){
				//json_xz[row].zt = '0';
				//zt[row]='0';
				json_xz.splice(row,1);
				zt.splice(row,1);
				invoiceAmount.splice(row,1);
				invoiceCode.splice(row,1);
				invoiceNo.splice(row,1);
				dqptspfpFun.fpjl_talbe_show(json_xz);
			}
		});	
	},
		/**
		 * 发票审核驳回
		 */
		spbh : function(obj){
			$('#dqptspfp_spbh').html(dqptspfp_sp_dialogTpl);	
			
			/*弹出框*/
			$( "#dqptspfp_spbh" ).dialog({
				title:"审批信息",
				width:"380",
				height:'220',
				modal:true,
				buttons:{ 
					"确定":function(){
						var dself=this;
						
						//有效数据验证
						if(!dqptspfpFun.validateData().form()){
							return false;
						}
						
						var param = {invoiceId:obj.invoiceId,allAmount:obj.allAmount,refuseRemark:$("#refuseRemark").val()}; 
						 dataModoule.rejectionInvoice(param, function(res){
							$(dself).dialog( "destroy" );
							comm.yes_alert("发票审核驳回",400);
							$("#platsp_query").click();	
						});	
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});s
		},
		ck_qyxx : function(custId){
			var that = this;
			comm.liActive_add($('#ckqyxx'));
			this.showTpl($('#ckqyxxTpl'));
			qyxx_tab.showPage(custId);
			
			$('#back_btn').click(function(){
				comm.liActive($('#dqptspfp'), '#ck, #xz, #ckqyxx');
				that.showTpl($('#dqptspfpTpl'));
			});		
		},
		showTpl : function(tplObj){
			$('#dqptspfpTpl, #dqptspfp_ckTpl, #dqptspfp_kpTpl, #ckqyxxTpl').addClass('none');
			tplObj.removeClass('none');
		},
		/**
		 * 新增发票表单验证
		 */
		xzfpValid : function(){
			return comm.valid({
				left:100,
				top:20 ,
				formID:'#invoice_addform',
				rules : {
					invoiceCode : {
						required : true
					},
					invoiceNo : {
						required : true	
					},
					invoiceAmount : {
						required : true			
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
		 * 审批驳回表单校验
		 */
		validateData : function(){
			var valida = $("#spbh_apply").validate({ 
				rules : {
					refuseRemark : {
						required : true,
						rangelength:[0, 300]
					}
				},
				messages : {
					refuseRemark : {
						required : comm.lang("invoice")[36549],
						rangelength:comm.lang("invoice").refuseRemarkmaxLength
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
			return valida;
		}
	}	
	return dqptspfpFun;
});