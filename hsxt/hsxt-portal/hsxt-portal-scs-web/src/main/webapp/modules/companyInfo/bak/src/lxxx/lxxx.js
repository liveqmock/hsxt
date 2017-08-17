define(['text!companyInfoTpl/lxxx/lxxx.html','text!companyInfoTpl/lxxx/lxxxxg.html',
		"companyInfoDat/companyInfo" ],function(lxxxTpl, lxxx_xgTpl,companyInfo ){
	return {
		 
		showPage : function(){
			var self = this;
		
			//获得联系
			var sysInfo = companyInfo.getSystemInfo();
			companyInfo.findContactInfo({"entCustId":sysInfo.entCustId},function (response) {
					var responseData=response.data;
					var authProxyFile=responseData.authProxyFile;
					$('#busibox').html(_.template(lxxxTpl, responseData)).append(_.template(lxxx_xgTpl, responseData));
					
					self.init_btn();
					
					self.sendMailBtn();
					
//					if(authProxyFile!=null&&authProxyFile!=''){
//						var picUrl=comm.getFsServerUrl(responseData.authProxyFile);
//						//显示图片
//						$("#grant_permission_i").attr("src",picUrl);
//						$("#grant_permission_i_xg").attr("src",picUrl);
//						//绑定图片查看器
//						comm.bindPicViewer("#grant_permission_i",picUrl);
//						comm.bindPicViewer("#grant_permission_i_xg",picUrl);
//						
//						//隐藏上传按键
//						$("#authProxyFile").parent().hide();
//						
//					}else {
						//上传
						comm.initUploadBtn(["#authProxyFile"],["#imgDiv"],81,52);
						
						$("body").on("change","",function(){
							var obj=$("#imgDiv").children().get(0);
							comm.bindPicViewer(obj,obj.src);
						});
						
//					}
					$("#powerOfAttorneyFileId_sample").attr("href",comm.getFsServerUrl(responseData.powerOfAttorneyFileId));
					
			});
		},
		
		sendMailBtn : function(){
			/*发邮件提示*/
			$('#btn_lxxx_yz').click(function(){
				var close = comm.lang("companyInfo")["universal"]["close"],
				title=comm.lang("companyInfo").lxxx.mail_valid_alert_title;
				var buttons = {};
				
				 buttons[close] = function(){
                    $('#dlgEmail_content').dialog('destroy');
				};
				
				companyInfo.validationEmail({"companyMail":$("#companyMail").text()},function(){
					/*弹出框*/
					$( "#dlgEmail_content" ).dialog({
						title:title,
						modal:true,
						width:"400",
						height:"160",
						buttons:buttons
					});
					
				});
				
			});	
		},
		
		init_btn : function (){
			var self = this;
			//修改
			$('#xg_btn').click(function(){
				 $('#lxxxTpl').addClass('none');
				 $('#lxxx_xgTpl').removeClass('none');
				 comm.liActive_add($('#qyxx_lxxxxg'));
				 $('#qyxx_lxxxxg').css("display","block");
			});
			
			//取消
			$('#backBt_qiyelianxi').triggerWith("#qyxx_lxxx");
			
			//提交 
			$('#submitBt_qiyelianxi').click(function(){
				 if(!self.validateData()){
					 return ;
				 }
				 
				 //判断是否需要上传图
				
				 
				 var data=$('#lxxx_xgTpl_form').serializeJson();
				 
				 var authProxyFile_old = $("#authProxyFile_old").val();
				 var authProxyFile = $("#authProxyFile").val();
				 var sysInfo = companyInfo.getSystemInfo();
				 //上传文件
				 if(comm.isEmpty(authProxyFile_old)&&!comm.isEmpty(authProxyFile)){
					 //上传成功后，需要提交文件返回的文件ID
					 var success = function(resData){
						 data=$.extend(data,{"authProxyFile":resData.authProxyFile,"entCustId":sysInfo.entCustId});
						 companyInfo.updateContactInfo(data, function(response){
							$('#qyxx_lxxx').click();
						 },function(){
							 self.initUpload_btn();
						 });
					 };
					 
					 //失败后要重新初始化上传按键 ，否则不能重要选择文件
					 var error = function(resData){
						 self.initUpload_btn();
					 };
					 //上传
					 var ids =['authProxyFile'];
					 comm.uploadFile(comm.getUploadFilePath(),ids,success,error);
				 }else {
					 companyInfo.updateContactInfo($.extend(data,{"entCustId":sysInfo.entCustId}), function(response){
							$('#qyxx_lxxx').click();
						 });
				 }
					
			 });
			
		},
		
		validateData : function () {
			var address=$("#address").val();
			var postalCode=$("#postalCode").val();
			var website=$("#website").val();
			var mail=$("#mail").val();
			var officePhone=$("#officePhone").val();
			var faxNo=$("#faxNo").val();
			var contactsJob=$("#contactsJob").val();
			var qq=$("#qq").val();
			
			var address_old=$("#address_old").val();
			var postalCode_old=$("#postalCode_old").val();
			var website_old=$("#website_old").val();
			var mail_old=$("#mail_old").val();
			var officePhone_old=$("#officePhone_old").val();
			var faxNo_old=$("#faxNo_old").val();
			var contactsJob_old=$("#contactsJob_old").val();
			var qq_old=$("#qq_old").val();
			
			var authProxyFile_old = $("#authProxyFile_old").val();
			var authProxyFile = $("#authProxyFile").val();
			
			
			//没有修改不提交 
			if(address==address_old &&postalCode==postalCode_old
					&& website==website_old &&mail == mail_old
					&&officePhone ==officePhone_old && faxNo==faxNo_old && contactsJob==contactsJob_old &&
					qq==qq_old&&!(comm.isEmpty(authProxyFile_old)&&!comm.isEmpty(authProxyFile))){
				comm.alert(comm.lang("companyInfo").universal.unchanged);
				return false;
			}
			
			
			return comm.valid({
				left : 10,
				top : 20,
				formID : '#lxxx_xgTpl_form',
				rules : {
					webSite : {
						url : true
					},
					contactEmail : {
						email2 : true
					},
					
					officeQq : {
						qq : [5,15]
					},
					
					officeFax : {
						fax : [5,15]
					},
					
					officeTel : {
						telphone : [5,11]
					}
				},

				messages : {
					webSite : {
						url : comm.lang("companyInfo").lxxx.illage_input_url
					},
					contactEmail : {
						email2 : comm.lang("companyInfo").lxxx.illage_input_email
					},
					officeQq : {
						qq : comm.lang("companyInfo").lxxx.illage_input_qq
					},
					officeFax : {
						fax : comm.lang("companyInfo").lxxx.illage_input_fax
					},
					
					officeTel : {
						telphone :comm.lang("companyInfo").lxxx.illage_input_telphone
					}
				}
			});
		}
	}
}); 

 