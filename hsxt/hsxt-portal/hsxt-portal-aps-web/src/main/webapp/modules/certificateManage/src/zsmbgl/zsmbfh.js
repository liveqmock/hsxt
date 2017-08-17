define(['text!certificateManageTpl/zsmbgl/zsmbfh.html',
		'text!certificateManageTpl/zsmbgl/zsmbwh_ck_dialog.html',
		'text!certificateManageTpl/zsmbgl/zsmbfh_fh.html',
		'text!certificateManageTpl/zsmbgl/zsmbfh_fh_dialog.html',
		'text!certificateManageTpl/zsmbgl/zsmbfh_xz.html',
		'certificateManageDat/certificate',
		'certificateManageLan'
		], function(zsmbfhTpl, zsmbwh_ck_dialogTpl, zsmbfh_fhTpl, zsmbfh_fh_dialogTpl, zsmbfh_xzTpl, dataModule){
	var self= {
		searchTable:null,
		showPage : function(){
			$('#busibox').html(_.template(zsmbfhTpl));	
			
			/*下拉列表*/
			
			comm.initSelect('#templateType', comm.lang("certificateManage").templetTypeEnum, null, null, {name:'全部', value:''});
			/*end*/
			
			/*表格数据模拟*/
			
			//var self = this;
			$("#qry_mbfh_btn").click(function(){
				var params = {
						search_templetName		: $("#templetName").val().trim()
				};
				var templetType = $("#templetType").attr("optionvalue");
				if(templetType){
					params.search_templetType = templetType;
				}
				
				self.searchTable = comm.getCommBsGrid("searchTable","find_certificate_temp_appr_by_page",params,comm.lang("certificateManage"),self.detail,self.edit,self.del);
			});
			
			/*end*/
			
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex==1){
				return comm.lang("certificateManage").templetTypeEnum[record.templetType];
			}
			if(colIndex==2){
				return comm.lang("certificateManage").custTypeEnum[record.custType];
			}
			if(colIndex==3){
				return comm.lang("certificateManage").templetStatusEnum[record.apprStatus];
			}
			if(colIndex==5){
				var link1 = $('<a>查看</a>').click(function(e) {
					var obj = self.searchTable.getRecord(rowIndex);
					obj.custTypeName = comm.lang("certificateManage").custTypeEnum[obj.custType];
					obj.resTypeName = comm.lang("certificateManage").resTypeEnum[obj.resType];
					$('#dialogBox').html(_.template(zsmbwh_ck_dialogTpl, obj));	
					$('#viewContent').click(function(){
						var width = 1000;
						var imgId = "thirdCertificate";
						if(obj.custType==4){
							//第三方证书
							width = 1000;
							imgId = "thirdCertificate";
						}
						if(obj.custType==3){
							//销售证书模板
							width = 1150;
							imgId = "qualificationCertificate";
						}
						
						var htmPath = comm.getFsServerUrl(obj.tempFileId);//"http://192.168.41.171:8080/hsxt-portal-aps-web/modules/certificateManage/tpl/zsmb/dsfzsmb.html";//comm.getFsServerUrl(obj.tempFileId);
						var imgPath = comm.getFsServerUrl(obj.tempPicId);//"http://192.168.41.171:8080/hsxt-portal-aps-web/resources/img/3.jpg";//comm.getFsServerUrl(obj.tempPicId);
						
						$.get(htmPath, function(res){
							$('#dialogBox').html(res);
							$('#'+imgId).css("background-image","url("+imgPath+")");
							/*弹出框*/
							$( "#dialogBox" ).dialog({
								title:"查看模板",
								width:width,
								modal:true,
								closeIcon : true,
								buttons:{
									"关闭":function(){
										 $( "#dialogBox" ).dialog( "destroy" );
									},
								}
							});
						});
					});
					/*弹出框*/
					$( "#dialogBox" ).dialog({
						title:"查看模版",
						width:"900",
						modal:true,
						buttons:{
							"关闭":function(){
								 $( this ).dialog( "destroy" );
							}
						}
					});
				});
				return link1;
			}
		},
		
		edit : function(record, rowIndex, colIndex, options){
			if(colIndex==5){
				var link2 = null;
				if(self.searchTable.getColumnValue(rowIndex, 'apprStatus') == '2'|| self.searchTable.getColumnValue(rowIndex, 'apprStatus') == '3'){
					link2 = $('<a>复核</a>').click(function(e) {
						var obj = self.searchTable.getRecord(rowIndex);
						dataModule.queryCertiLastTemplateAppr({templetId:obj.templetId},function(res){
							obj = res.data;
							comm.liActive_add($('#fh'));
							obj.custTypeName = comm.lang("certificateManage").custTypeEnum[obj.templet.custType];
							obj.resTypeName = comm.lang("certificateManage").resTypeEnum[obj.templet.resType];
							$('#busibox').html(_.template(zsmbfh_fhTpl, obj));
							$('#viewContent').click(function(){
								var width = 1000;
								var imgId = "thirdCertificate";
								if(obj.templet.custType==4){
									//第三方证书
									width = 1000;
									imgId = "thirdCertificate";
								}
								if(obj.templet.custType==3){
									//销售证书模板
									width = 1137;
									imgId = "qualificationCertificate";
								}
								
								var htmPath = comm.getFsServerUrl(obj.templet.tempFileId);//"http://192.168.41.171:8080/hsxt-portal-aps-web/modules/certificateManage/tpl/zsmb/dsfzsmb.html";//comm.getFsServerUrl(obj.tempFileId);
								var imgPath = comm.getFsServerUrl(obj.templet.tempPicId);//"http://192.168.41.171:8080/hsxt-portal-aps-web/resources/img/3.jpg";//comm.getFsServerUrl(obj.tempPicId);
								
								$.get(htmPath, function(res){
									$('#dialogBox').html(res);
									$('#'+imgId).css("background-image","url("+imgPath+")");
									/*弹出框*/
									$( "#dialogBox" ).dialog({
										title:"查看模板",
										width:width,
										modal:true,
										closeIcon : true,
										buttons:{
											"关闭":function(){
												 $( "#dialogBox" ).dialog( "destroy" );
											},
										}
									});
								});
							});
							$('#back_zsmbfh').triggerWith('#zsmbfh');
							$('#fhtjDiv').removeClass('none');
							$('#fhtj_btn').click(function(){
								
								$('#dialogBox').html(zsmbfh_fh_dialogTpl);
								/*弹出框*/
								$( "#dialogBox" ).dialog({
									title:"复核确认",
									width:"400",
									height:"220",
									modal:true,
									buttons:{
										"确定":function(){
											var fhParam = {
													templetId	:	obj.templet.templetId,
													apprStatus	:	obj.apprStatus,
													apprResult	:	$('input:radio[name="isPass"]:checked').val(),
													apprOperator:	$.cookie('custId'),
													apprName	:	$.cookie('custName'),
													apprRemark	:	$("#apprRemark").val().trim()
											};
											
											dataModule.certificateTempAppr(fhParam,function(res){
												if(res.retCode=22000){
													$('#dialogBox').dialog( "destroy" );
													$('#zsmbfh').click();
												}else{
													
												}
											});
										},
										"取消":function(){
											$('#dialogBox').dialog( "destroy" );
										}
									}
								});
								/*end*/
							});
						});
						
					});
				}
				return link2;
			}
		}
	};
	return self;
});