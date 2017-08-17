define(['text!toolmanageTpl/gjpzxx/gjxjsp.html',
		'text!toolmanageTpl/gjpzxx/gjxjsp_sp.html',
		'text!toolmanageTpl/gjpzxx/gjxjsp_gq_sq_dialog.html',
		'toolmanageDat/gjpzxx/gjliebxx',
		"commDat/common",
		'toolmanageLan'
		], function(gjxjspTpl, gjxjsp_spTpl,gjxjsp_gq_sq_dialogTpl,toolmanage,commonAjax){
	var self = null;
	return {
		showPage : function(){
			$('#busibox').html(gjxjspTpl);	
			self = this;
			$("#queryBtn").click(function(){
				self.initData();
			});
		},
	    initData:function(){
				var param = {};
				param.search_exeCustId = comm.getSysCookie('custId');
				param.search_productName = $("#search_productName").val().trim();
				param.search_categoryCode = $("#search_categoryCode").val().trim();
				var bsgrid = toolmanage.findToolDoawnList(param,self.detail,self.hangUp,self.refuseAccept);
	    },
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.formatMoneyNumber(record.price);
			}else if(colIndex == 3){
				return comm.lang("toolmanage").toolStatues[record.enableStatus];
			}else if(colIndex == 4){
				return comm.lang("toolmanage").ratifyStatues[record.status];
			}else if(colIndex == 6){
				var link1 = $('<a>审批</a>').click(function(e) {
					self.shenPi(record);
				}.bind(this) ) ;
				return link1;
			}
		},
		//拒绝受理
		refuseAccept : function(record, rowIndex, colIndex, options){
			if(colIndex==7){
				var link = $('<a>'+comm.lang("toolmanage").refuseAccept+'</a>').click(function(e) {
					comm.i_confirm(comm.lang("toolmanage").optRefuseAccept,function(){
						commonAjax.workTaskRefuseAccept({bizNo:record.applyId},function(resp){
							comm.yes_alert(comm.lang("toolmanage").workTaskRefuseAcceptSucc,null,function(){
								 self.initData();
							});
						});
					});
				});
				return link;
			}
		},
		//挂起
		hangUpOld : function(record, rowIndex, colIndex, options){
			if(colIndex==7){
				var link = $('<a>'+comm.lang("toolmanage").hangUp+'</a>').click(function(e) {
					comm.i_confirm(comm.lang("toolmanage").optHangUp,function(){
						commonAjax.workTaskHangUp({bizNo:record.applyId},function(resp){
							comm.yes_alert(comm.lang("toolmanage").workTaskHangUp,null,function(){
								   self.initData();
							});
						});
					});
				});
				return link;
			}
		},
		
		//挂起
		hangUp : function(record, rowIndex, colIndex, options){
			if(colIndex==7){
				var link = $('<a>'+comm.lang("toolmanage").hangUp+'</a>').click(function(e) {
					comm.i_confirm(comm.lang("toolmanage").optHangUp,function(){
						$('#dialogBox_fh_sq').html(_.template(gjxjsp_gq_sq_dialogTpl));
						$( "#dialogBox_fh_sq" ).dialog({
							title:"工单挂起",
							width:"800",
							height:"340",
							modal:true,
							buttons:{
								"挂起":function(){
									if($('#verificationMode').attr('optionValue')!='1'){
										return;
									}
									if(!self.validateGq()){
										return;
									}
									comm.verifyDoublePwd($("#gdgqUserName").val(), $("#gdgqPassWord").val(), 1, function(resp){
										if(resp){
											$( '#dialogBox_fh_sq' ).dialog( "destroy" );
											commonAjax.workTaskHangUp({bizNo:record.applyId,remark:$('#remark').val()},function(resp){
												comm.yes_alert(comm.lang("toolmanage").workTaskHangUp,null,function(){
													 self.initData();
												});
											});
										}
									});
								},
								"取消":function(){
									$('#gdgqUserName, #gdgqPassWord, #remark').tooltip().tooltip("destroy");
									$( '#dialogBox_fh_sq' ).dialog( "destroy" );
								}
							}
						});
						/*下拉列表*/
						comm.initSelect("#verificationMode",
							comm.lang("toolmanage").verificationMode).change(function(e){
							var val = $(this).attr('optionValue');
							switch(val){
								case '1':
									$('#gdgqUserName_li, #gdgqPassWord_li').removeClass('none');
									$('#verificationMode_prompt').addClass('none');
									break;

								case '2':
									$('#gdgqUserName_li, #gdgqPassWord_li').addClass('none');
									$('#verificationMode_prompt').removeClass('none');
									break;

								case '3':
									$('#gdgqUserName_li, #gdgqPassWord_li').addClass('none');
									$('#verificationMode_prompt').removeClass('none');
									break;
							}
						});
						$("#verificationMode").selectListValue("1");
					});
				});
				return link;
			}
		},
		
		validateGq : function(){
			return comm.valid({
				formID : '#ptdggjfh_gq_sq_form',
				rules : {
					gdgqUserName:{
						required : true
					},
					gdgqPassWord:{
						required : true
					},
					remark:{
						maxlength : 300
					}
				},
				messages : {
					gdgqUserName:{
						required : comm.lang("toolmanage").apprUserNameIsNotNull
					},
					gdgqPassWord:{
						required : comm.lang("toolmanage").apprPassWordIsNotNull
					},
					remark:{
						maxlength : comm.lang("toolmanage").remarkLength
					}
				}
			});
		},
		
		
		
		initPicPreview : function(objId, fileId, title, divId, width, height) {
			title = !title?"图片查看":title;
			width = !width?"800":width;
			height = !height?"600":height;
			divId = !divId?"#showImage50":divId;
			logId = divId+"Div";
			$(objId).attr("data-imgSrc", comm.getFsServerUrl(fileId));
				var buttons={};
				 buttons['关闭'] =function(){
					$(this).dialog("destroy");
				};
				var url= comm.getFsServerUrl(fileId);
				if (null != url && "" != url) {
					var imgHtml = "<img alt=\"图片加载失败...\" src=\""+url+"\" id=\"showImage50\" style=\"width:100%; height:100%;\">";
					$(logId).html(imgHtml);
					$(logId).dialog({
						title : title,
						width : width,
						height : height,
						modal : true,
						buttons : buttons
					});
				}
		},
		
		/**
		 * 表单校验
		 * @param formName 表单名称
		 */
		validateData : function(formName){
			 return $(formName).validate({
				rules : {
					apprRemark : {
						maxlength:300
					}
				},
				messages : {
					apprRemark : {
						maxlength:comm.lang("toolmanage").shenpimaxlength
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
		},
		
		
		shenPi : function(obj){
			$('#sp_dialog').html(gjxjsp_spTpl).dialog({
				title : '工具下架审批',
				width : '630',
				closeIcon : true,
				modal : true,
				buttons : {
					'确定' : function(){
						var sell = this;
						if(!self.validateData("#gjxjsp_form").form()){
							return;
						}
						var param = {};
						param.applyId = obj.applyId;
						param.apprRemark = $("#apprRemark").val();
						param.pass = false;
						if($("#rd1").attr("checked")){
							param.pass = true;
						}
						toolmanage.apprToolProductForDown(param,function(){
							comm.yes_alert(comm.lang("toolmanage").priceUpateResult);
							$(sell).dialog('destroy');
							$("#searchTable_pt_refreshPage").click();
						});
					},
					'取消' : function(){
						$(this).dialog('destroy');	
					}		
				}	
			});
			
			$('#xj_gjmc_txt').text(obj.toolProductName || "");
			$('#xj_gjlb_txt').text(obj.toolCategoryName || "");
			$('#xj_jg_txt').text(comm.formatMoneyNumber(obj.price)|| "");
			$('#xj_jldw_txt').text(obj.toolUnit|| "");
			$('#xj_gjtp_txt').text(obj.toolMicroPic);
			$('#xj_gjsm_txt').text(obj.toolDescription||"");
			$('#xj_xjyy_txt').text(comm.removeNull(obj.downReason));
			$('#xj_gjtp_txt').html('<a class="blue" id="gjxgtp">点击查看图片<img style="display:none"  id="gjlbxgImg" src="" width="80" height="20"  title="点击查看图片" /></a>');

			$("#gjxgtp").click(function(){
				self.initPicPreview("#gjlbxgImg", obj.toolMicroPic, "");
			});
		}		
	}	
});
