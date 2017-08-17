define(['text!systemBusinessTpl/toolManage/cardStyleBuy/loadCardStyle.html',
		'text!systemBusinessTpl/toolManage/cardStyleBuy/viewCardStyle.html',
		'text!systemBusinessTpl/toolManage/cardStyleBuy/submitCardStyle.html',
		'text!systemBusinessTpl/toolManage/cardStyleBuy/confirmStyle.html',
		'text!systemBusinessTpl/toolManage/cardStyleBuy/uploadCardStyle.html',
		'systemBusinessDat/systemBusiness',
], function(loadCardStyleTpl, viewCardStyleTpl, submitCardStyleTpl,confirmStyleTpl,uploadCardStyleTpl,systemBusinessAjax){

	var gridObj = null;
	
var self = {
	showPage : function(){
		$('#busibox').html(_.template(loadCardStyleTpl));	
		//加载表格数据
		self.loadGrid();
		//加载卡样
		self.loadCardStyle();
		
		//个性卡订制服务下单
		$('#sqgxk_btn').click(function(){
			self.showTpl($('#submitCardStyle'));
			$('#sourceFileName').html('');
			comm.liActive_add($('#gjgl_sq_2'));
			$('#submitCardStyle').html(submitCardStyleTpl);
			//返回
			$('#sq_back_btn').click(function(){
				$('#upLoadFile #remark').tooltip().tooltip("destroy");
				$('#upLoadFile #cardStyleName').tooltip().tooltip("destroy");
				$('#upLoadFile #sourceFileHid').val('');
				self.showTpl($('#cardStyle'));
				comm.liActive($('#gxkdzfw'), '#gjgl_xgsx, #gjgl_ck, #gjgl_zf, #gjgl_sq, #gjgl_sq_2');	
			});

			$("#sourceFile").unbind('change').bind('change', function(){
				var val = $(this).val();
				$('#sourceFileName').html(val.replace(/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi,"$1")+"."+val.replace(/.+\./,""));
			});
			
			//下单
			$('#sq_pay_btn').click(function(){
				if(!self.validateData('#upLoadFile',true,false,false)){
					return;
				}
				comm.i_confirm(comm.lang("systemBusiness").isSubmitCardStyleOrder,function(){
					//上传文件
					self.uploadFile('upLoadFile',function(resp){
						var param = {
							cardStyleName : $('#cardStyleName').val(),
							remark:$('#remark').val()
						};
						if(resp){
							param.sourceFile = resp.sourceFile,
							$('#upLoadFile #sourceFileHid').val(resp.sourceFile);
						}
						systemBusinessAjax.submitSpecialCardStyleOrder(param,function(resp){
							if(resp){
								var isPay = resp.data.pay;
								if(comm.isNotEmpty(isPay)&&isPay){
									$("#gjsgcx").trigger('click');
								}else{
									$('#upLoadFile #sourceFileHid').val('');
									self.showTpl($('#pay_div'));
									comm.liActive_add($('#gjgl_zf'));
									$('#gjgl_sq_2').addClass('tabNone');						
									require(['systemBusinessSrc/toolManage/pay/pay'], function(pay){
										var obj = resp.data;
										obj.paymentMethod = 'hsbPay';
										obj.isFormat = 'format';
										obj.orderType = '107';
										obj.isFrist = 'true';
										pay.showPage(obj);	
									});
								}
							}
						});
					});
				});
			});
		});
	},

	//加载表格
	loadGrid : function(){
		gridObj = comm.getCommBsGrid("searchCardStyle_Table","special_card_style_list",null,this.detail,this.confirm,this.upload);
	},
	//加载卡样
	loadCardStyle : function(){
		systemBusinessAjax.entSpecialCardStyle(null,function(resp){
			if(resp){
				var obj = resp.data;
				if(obj.length > 0){
					$('#cardStyleListDiv').removeClass('none');
					var cardStyleList = $('#cardStyleList').empty();
					for(var i=0;i<obj.length;i++){
						var ioc = '';
						if(comm.isEmpty(obj[i].microPic)){
							ioc = '<i class="certificateSample_img certificateSample_02"></i>';
						}else{
							ioc = '<img src="'+systemBusinessAjax.getFsServerUrl(obj[i].microPic)+'" width="120" height="70">';
						}
						var id = "confirmCardStyle-"+(i+1);
						//comm.valToLongShow(obj[i].cardStyleName,8)
						var li = $('<li><div class="mt5 mb5 sampleBox">'+ioc+'</div>'+
								'<div class="mt10  clearfix"><span class="gjsg-list-name mr30">'+obj[i].cardStyleName+'</span></div>'+
								'<div class="mt5 clearfix" style="font-size:13px"><span class="f1"><a id='+id+'>下载卡样确认书样本</a></span> | ' +
								'<span><a class="viewCardStyleImg" data-imgsrc="'+systemBusinessAjax.getFsServerUrl(obj[i].microPic)+'" >预览</a></span>'+
							    '</div></li>');
						cardStyleList.append(li);
						comm.initDownload("#cardStyleList #"+id, {1000:{fileId:obj[i].confirmFile, docName:''}}, 1000);
					}
					//预览确认卡样
					$('#cardStyleList .viewCardStyleImg').click(function(){
						$('#viewConfirmCardStyle > p').html('<img src="'+$(this).attr('data-imgsrc')+'"/>');
						$('#viewConfirmCardStyle').dialog({width:'auto',height:'auto',title:comm.lang("systemBusiness").toolManage_viewCardStyle ,closeIcon:true,buttons:{"关闭":function(){$(this).dialog( "destroy" );}}});
					});
				}
			}
		});
	},
	//上传素材 
	upload : function(record, rowIndex, colIndex, options){
		if(colIndex == 4 &&(comm.isEmpty(record.isConfirm)||record.isConfirm ==false)){	
			var link = $('&nbsp;&nbsp;<a>'+comm.lang("systemBusiness").toolManage_cardStyleOpt['uploadFile']+'</a>&nbsp;&nbsp;').click(function(e) {
				$('#uploadCardStyle').html(_.template(uploadCardStyleTpl,record)); 
				self.showTpl($('#uploadCardStyle'));
				comm.liActive_add($('#uploadCardStyleTab'));
				if(record.mSourceFile){
					comm.initDownload("#uploadCardStyleForm #markAccessoryShow", {1000:{fileId:record.mSourceFile, docName:''}}, 1000);
				}
				//提交
				$('#uploadBtn').click(function(){
					var vaildFile = comm.isNotEmpty($('#uploadCardStyleForm #sourceFileHid').val())?false:true;
					if(!self.validateData('#uploadCardStyleForm',false,vaildFile,false)){
						return;
					}
					comm.i_confirm(comm.lang("systemBusiness").isSubmitUpLoadCardStyle,function(){
						self.uploadFile('uploadCardStyleForm',function(resp){
							if(resp){
								var param = {
									orderNo:record.orderNo,
									sourceFile:resp.sourceFile,
									remark:$('#remark').val()
								};
								$('#uploadCardStyleForm #sourceFileHid').val(resp.sourceFile);
								systemBusinessAjax.addSpecialCardStyle(param,function(resp){
									if(resp){
										comm.yes_alert(comm.lang("systemBusiness").toolManage_uploadCardStyleSucc,400,function(){
											$('#uploadCardStyleForm #sourceFileHid').val('');
											$('#uploadBackBtn').trigger('click');
											self.loadGrid();
											self.loadCardStyle();
										});
									}
								});
							}
						});
					});
				});
				//返回
				$('#uploadBackBtn').click(function(){
					$('#uploadCardStyleForm #sourceFileHid').val('');
					$('#uploadCardStyleForm #remark').tooltip().tooltip("destroy");
					$('#uploadCardStyleForm #sourceFile').tooltip().tooltip("destroy");
					self.showTpl($('#cardStyle'));	
					comm.liActive($('#gxkdzfw'), '#gjgl_xgsx, #gjgl_ck, #gjgl_zf, #gjgl_sq, #gjgl_sq_2, #uploadCardStyleTab, #confirmStyleTab');
				});
			}.bind(this));
			return link;
		}
	},
	//确认卡样
	confirm : function(record, rowIndex, colIndex, options){
		if(colIndex == 4 && record.isConfirm ==false){	
			var link = $('&nbsp;&nbsp;<a>'+comm.lang("systemBusiness").toolManage_cardStyleOpt['confirm']+'</a>&nbsp;&nbsp;').click(function(e) {
				$('#confirmStyle').html(_.template(confirmStyleTpl,record));
				self.showTpl($('#confirmStyle'));
				comm.liActive_add($('#confirmStyleTab'));
				if(record.microPic){
					$('#confirmCardStyleDiv').removeClass("none");

					$("#confirmStyle #microPicShow").show();
					$("#confirmStyle #microPicShow").attr('src',comm.getFsServerUrl(record.microPic));
					comm.initPicPreview("#confirmStyle #microPicShow", record.microPic);

					if(record.mSourceFile){
						comm.initDownload("#confirmStyle #markAccessoryShow", {1000:{fileId:record.mSourceFile, docName:''}}, 1000);
					}
					//提交
					$('#confirmBtn').click(function(){
						if(!self.validateData('#confirmForm',false,false,true)){
							return;
						}
						var param = {
							orderNo:$('#orderNo').val(),
							remark:$('#remark').val()
						};
						systemBusinessAjax.confirmCardStyle(param,function(resp){
							if(resp){
								comm.yes_alert(comm.lang("systemBusiness").toolManage_confirmCardStyleSucc,400,function(){
									$('#confirmBackBtn').trigger('click');
									self.loadGrid();
									self.loadCardStyle();
								});
							}
						});
					});
					//返回
					$('#confirmBackBtn').click(function(){
						$('#confirmForm #remark').tooltip().tooltip("destroy");
						$('#confirmForm #checkboxConfirm').tooltip().tooltip("destroy");
						self.showTpl($('#cardStyle'));
						comm.liActive($('#gxkdzfw'), '#gjgl_xgsx, #gjgl_ck, #gjgl_zf, #gjgl_sq, #gjgl_sq_2, #uploadCardStyleTab, #confirmStyleTab');
					});
				}else{
					$('#notUploadCardStyle').removeClass("none");
					//返回
					$('#noConfirmBackBtn').click(function(){
						self.showTpl($('#cardStyle'));
						comm.liActive($('#gxkdzfw'), '#gjgl_xgsx, #gjgl_ck, #gjgl_zf, #gjgl_sq, #gjgl_sq_2, #uploadCardStyleTab, #confirmStyleTab');
					});
				}
			}.bind(this));
			return link;
		}
	},
	//查看
	detail : function(record, rowIndex, colIndex, options){
		if(colIndex == 0){
			return comm.formatMoneyNumber(record.cardStyleFee);
		}else if(colIndex == 1){
			return comm.lang("systemBusiness").toolManage_confirm[comm.navNull(record.isConfirm)];
		}else if(colIndex == 4){	
			var link = $('&nbsp;&nbsp;<a>'+comm.lang("systemBusiness").toolManage_cardStyleOpt['detail']+'</a>&nbsp;&nbsp;').click(function(e) {
				$('#viewCardStyle').html(_.template(viewCardStyleTpl,record));
				if(record.microPic){
					$("#viewCardStyle #microPicShow").show();
					$("#viewCardStyle #microPicShow").attr('src',comm.getFsServerUrl(record.microPic));
					comm.initPicPreview("#viewCardStyle #microPicShow", record.microPic);
				}
				if(record.confirmFile){
					$("#viewCardStyle #upDownConfirmFileShow").show();
					comm.initDownload("#viewCardStyle #upDownConfirmFileShow", {1000:{fileId:record.confirmFile, docName:''}}, 1000);
				}
				if(record.mSourceFile){
					comm.initDownload("#viewCardStyle #markAccessoryShow", {1000:{fileId:record.mSourceFile, docName:''}}, 1000);
				}
				self.showTpl($('#viewCardStyle'));
				comm.liActive_add($('#gjgl_ck'));			
				$('#view_back_btn').click(function(){
					self.showTpl($('#cardStyle'));	
					comm.liActive($('#gxkdzfw'), '#gjgl_xgsx, #gjgl_ck, #gjgl_zf, #gjgl_sq, #gjgl_sq_2');
				});
			}.bind(this));
			return link;
		}
	},
	//显示页面
	showTpl : function(tplObj){
		$('#cardStyle, #viewCardStyle, #pay_div, #confirmStyle, #uploadCardStyle, #submitCardStyle').addClass('none');
		tplObj.removeClass('none');
	},
	//上传文件 
	uploadFile : function(formId,callBack){
		var sourceFileHid = $('#'+formId+' #sourceFileHid').val();
		if(comm.isNotEmpty(sourceFileHid)){
			var resp = {
				sourceFile:sourceFileHid
			};
			callBack(resp);
		}else{
			if(comm.isNotEmpty($('#'+formId+' #sourceFile').val())){
				var ids = ['sourceFile'];
				comm.uploadFile(null, ids, function(resp){
					if(resp){
						callBack(resp);
					}
				},function(err){
				},null);
			}else{
				var resp = {
					sourceFile:null
				};
				callBack(resp);
			}
		}
	},
	validateData : function(formID,styleName,file,check){
		return comm.valid({
			formID : formID,
			rules : {
				cardStyleName :{
					required :styleName,
					rangelength : [2, 20]
				},
				remark : {
					rangelength : [2, 300]
				},
				sourceFile : {
					required : file		
				},
				checkboxConfirm : {
					required : check		
				}
			},
			messages : {
				cardStyleName : {
					required : comm.lang("systemBusiness").toolManage_cardStyleNameIsNotNull,
					rangelength : comm.lang("systemBusiness").toolManage_cardStyleNameLengthIs20
				},
				remark : {
					rangelength : comm.lang("systemBusiness").toolManage_remarkLengthIs300
				},
				sourceFile : {
					required : comm.lang("systemBusiness").toolManage_uploadSourceFile		
				},
				checkboxConfirm : {
					required : comm.lang("systemBusiness").toolManage_selectedConfirmCheckbox		
				}
			}
		});	
	}
};	
return self;	
});