define(['text!certificateManageTpl/zsmbgl/zsmbwh.html',
		'text!certificateManageTpl/zsmbgl/zsmbwh_ck_dialog.html',
		'certificateManageSrc/zsmbgl/xzmb',
		'certificateManageSrc/zsmbgl/xgmb',
		'certificateManageDat/certificate',
		'certificateManageLan'
		], function(zsmbwhTpl, zsmbwh_ck_dialogTpl, xzmb, xgmb, dataModule){
	var self= {
		searchTable : null,
		showPage : function(){
			$('#busibox').html(_.template(zsmbwhTpl));	
			
			/*下拉列表*/
			comm.initSelect('#templateType', comm.lang("certificateManage").templetTypeEnum, null, null, {name:'全部', value:''});
			/*end*/
			
			/*表格数据模拟*/
			//var self = this;
			$("#qry_mb_btn").click(function(){
				var params = {
						
						search_templetName		: $("#templetName").val().trim()
						
				};
				var templateType = $("#templateType").attr("optionvalue");
				if(templateType){
					params.search_templetType = templateType;
				}
				
				self.searchTable = comm.getCommBsGrid("searchTable","find_certificate_temp_by_page",params,comm.lang("certificateManage"),self.detail,self.edit,self.del);
			});
			
			/*end*/	
			
			$('#xzmb_btn').click(function(){
				xzmb.showPage();
			});
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
							width = 1137;
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
					
				}) ;
				
				return link1;
			}
		},
		
		del : function(record, rowIndex, colIndex, options){
			if(colIndex==5){
				var link2 = null;
				if(self.searchTable.getColumnValue(rowIndex, 'apprStatus') == '0'){
					link2 = $('<a>停用</a>').click(function(e) {
						var obj = self.searchTable.getRecord(rowIndex);
						var confirmObj = {
								imgFlag:true ,             //显示图片s
								content : '您确认停用此模版？',			 
								callOk :function(){
									var stopParam = {
											templetId	:	obj.templetId,
											custId		:	$.cookie('custId')
									}
									dataModule.stopCertificateTemp(stopParam,function(res){
										if(res.retCode=22000){
											$("#qry_mb_btn").click();
										}
									});
								}
						}
						comm.confirm(confirmObj);
					});
				}
				else if(self.searchTable.getColumnValue(rowIndex, 'apprStatus') == '1'){
					link2 = $('<a>启用</a>').click(function(e) {
						var obj = self.searchTable.getRecord(rowIndex);
						var confirmObj = {
								imgFlag:true ,             //显示图片s
								content : '您确认启用此模版？',			 
								callOk :function(){
									var stopParam = {
											templetId	:	obj.templetId,
											custId		:	$.cookie('custId')
									}
									dataModule.enableCertificateTemp(stopParam,function(res){
										if(res.retCode=22000){
											$("#qry_mb_btn").click();
										}
									});					
								}
						}
						comm.confirm(confirmObj);	
					}) ;
				}
				return link2;
			}
		},
		
		edit : function(record, rowIndex, colIndex, options){
			if(colIndex==5){
				var link3 = null;
				if(self.searchTable.getColumnValue(rowIndex, 'apprStatus') == '1'){
					link3 = $('<a>修改</a>').click(function(e) {
						var obj = self.searchTable.getRecord(rowIndex);
						xgmb.showPage(obj);
					});	
				}
				return link3;
			}
		},
		
		qiYon : function(){
			var confirmObj = {	
				imgFlag:true ,             //显示图片s
				content : '您确认启用此模版？',			 
				callOk :function(){
										
				}
			}
			comm.confirm(confirmObj);	
		},
		xiuGai : function(obj){
				
		}
	};
	return self;
});