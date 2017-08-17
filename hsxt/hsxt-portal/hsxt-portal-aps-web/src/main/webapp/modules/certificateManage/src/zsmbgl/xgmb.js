define(['text!certificateManageTpl/zsmbgl/xgmb.html',
        'certificateManageDat/certificate',
		'certificateManageLan'
		], function(xgmbTpl, dataModule){
	return {
		searchTable : null,
		showPage : function(obj){

			comm.liActive_add($('#xgmb'));
			$('#busibox').html(_.template(xgmbTpl, obj));
			var self = this;
			self.initUpload_btn();
			
			$("#tempFileId").change(function(){
				var fileName = $("#tempFileId").val();
				var fileType = (fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length)).toLowerCase();
				if( fileType!='htm' && fileType!='html' ){
					comm.error_alert(comm.lang("certificateManage").templetFileError);
					$("#tempFileId").val("");
					return false;
				}
			});
			
			comm.initPicPreview("#business_apply", obj.tempPicId);
			$("#business_apply").html("<img width='107' height='64' src='"+comm.getFsServerUrl(obj.tempPicId)+"'/>");
			
			if(obj.custType == 3 ){
				$('#zsmbwh_qylx_ul').removeClass('none') ; 
			}
			/*下拉列表*/
			comm.initSelect('#enterpriseType', comm.lang("certificateManage").custTypeSelector,180,obj.custType);
			
			$("#enterpriseType").change(function(e){
				self.initResourcesType();
			});
			
			self.initResourcesType();
			//comm.initSelect('#resourcesType', comm.lang("certificateManage").resTypeEnum,160,obj.resType);
			
			/*end*/
			$("#xgmb_bc_btn").click(function(){
				if($("#templetName").val().trim()==""){
					comm.error_alert(comm.lang("certificateManage")[36409]);
					return false;
				}
				var xgParam = {
						templetId	:	$("#templetId").val().trim(),
						templetName	:	$("#templetName").val().trim(),
						updatedBy	:	$.cookie('custId'),
						updatedName	:	$.cookie("custName")
				};
				
				var custType = $("#enterpriseType").attr("optionvalue");
				if(custType && custType!=""){
					xgParam.custType = custType;
				}else{
					comm.error_alert(comm.lang("certificateManage").custTypeInvalid);
					return false;
				}
				
				var resourcesType = $("#resourcesType").attr("optionvalue");
				if(resourcesType){
					xgParam.resType = resourcesType;
				}
				if($("#tempFileId").val()==null || $("#tempFileId").val() == ""){
					comm.error_alert(comm.lang("certificateManage")[36415]);
					return false;
				}
				if($("#tempPicId").val()==null || $("#tempPicId").val() == ""){
					comm.error_alert(comm.lang("certificateManage")[36414]);
					return false;
				}
				if($("#tempFileId").val()!="" && $("#tempPicId").val()!=""){
					//上传文件
					var uploadUrl=comm.domainList['apsWeb']+comm.UrlList["fileupload"];
					comm.uploadFile(uploadUrl,["tempFileId","tempPicId"],function(data){
						var tempFileId = data.tempFileId;
						var tempPicId = data.tempPicId;
						xgParam.tempFileId = tempFileId;
						xgParam.tempPicId = tempPicId;
						if(tempFileId==null || tempFileId == ""){
							comm.error_alert(comm.lang("certificateManage")[36415]);
							return false;
						}
						if(tempPicId==null || tempPicId == ""){
							comm.error_alert(comm.lang("certificateManage")[36414]);
							return false;
						}
						comm.initUploadBtn(["#fileApply"],["#imgApplyImg"],85,64);
						
						dataModule.modifyCertificateTemp(xgParam, function(res){
							if(res.retCode=22000){
								$("#zsmbwh").click();
							}
						});
					},function(){
						
					},{annexType:"html",permission:"1"});
				}
				
				
			});
			$('#back_zsmbwh').triggerWith('#zsmbwh');
		},
		initResourcesType : function (){
			var value= $("#enterpriseType").attr("optionvalue");
			switch(value){
				case '4':
				 	$('#zsmbwh_qylx_ul').addClass('none') ; 
					break;
				case '3':
				    $('#zsmbwh_qylx_ul').removeClass('none') ; 
				    comm.initSelect('#resourcesType', comm.lang("certificateManage").TResTypeEnum,157,2);
					break;
				case '2': 
					$('#zsmbwh_qylx_ul').removeClass('none') ; 
					comm.initSelect('#resourcesType', comm.lang("certificateManage").BResTypeEnum,157);					
					break;	 
			}
		},
		initUpload_btn : function (){
			
			var btnIds = ['#tempPicId'];
			var imgIds = ['#business_apply'];
			$("body").on("change", "", function(){
				for(var k in imgIds){
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			
			comm.initUploadBtn(btnIds, imgIds);
		}
	}	
});