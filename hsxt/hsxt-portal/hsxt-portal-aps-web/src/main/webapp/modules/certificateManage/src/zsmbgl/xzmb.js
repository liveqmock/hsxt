define(['text!certificateManageTpl/zsmbgl/xzmb.html',
        'certificateManageDat/certificate',
		'certificateManageLan'
		], function(xzmbTpl, dataModule){
	return {
		searchTable : null,
		showPage : function(){
			comm.liActive_add($('#xzmb'));
			$('#busibox').html(_.template(xzmbTpl));
			
			var self = this;
			self.initUpload_btn();
			
			$("#tempFileId").change(function(){
				var fileName = $("#tempFileId").val();
				var fileType = (fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length)).toLowerCase();
				if( fileType != 'htm' && fileType != 'html' ){
					comm.error_alert(comm.lang("certificateManage").templetFileError);
					$("#tempFileId").val("");
					return false;
				}
				$("#img1").attr("src","resources/img/yesTemplate.gif");
			});
			
			comm.initSelect('#custType', comm.lang("certificateManage").custTypeSelector,180);
			
			$("#custType").change(function(e){
				var value= $("#custType").attr("optionvalue");
				switch(value){
				case '4':
				 	$('#zsmbwh_qylx_ul').addClass('none') ; 
					break;
				case '3':
				    $('#zsmbwh_qylx_ul').removeClass('none');
				    comm.initSelect('#resourcesType', comm.lang("certificateManage").TResTypeEnum,157);
					break;
				case '2': 
					$('#zsmbwh_qylx_ul').removeClass('none');
					comm.initSelect('#resourcesType', comm.lang("certificateManage").BResTypeEnum,157);
					break;	 
				}
			});
			/*
			$("#resourcesType").selectList({
				width:157,
				optionWidth:157,
				options:[
				         {name:'创业资源',value:'1'},
				         {name:'首段资源',value:'2'},
				         {name:'全部资源',value:'3'}
						]			 
			}).change(function(e){
				console.log($(this).val() );
			});
			*/
			$("#xzmb_bc_btn").click(function(){
				if($("#templetName").val().trim()==""){
					comm.error_alert(comm.lang("certificateManage")[36409]);
					return false;
				}
				var xzParam = {
						templetName	:	$("#templetName").val().trim(),
						createdBy	:	$.cookie('custId'),
						createdName	:	$.cookie("custName")
				};
				
				var custType = $("#custType").attr("optionvalue");
				if(custType && custType!=""){
					xzParam.custType = custType;
				}else{
					comm.error_alert(comm.lang("certificateManage").custTypeInvalid);
					return false;
				}
				
				var resourcesType = $("#resourcesType").attr("optionvalue");
				if(resourcesType){
					xzParam.resType = resourcesType;
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
						if(tempFileId==null || tempFileId == ""){
							comm.error_alert(comm.lang("certificateManage")[36415]);
							return false;
						}
						if(tempPicId==null || tempPicId == ""){
							comm.error_alert(comm.lang("certificateManage")[36414]);
							return false;
						}
						xzParam.tempFileId = tempFileId;
						xzParam.tempPicId = tempPicId;
						comm.initUploadBtn(["#fileApply"],["#imgApplyImg"],85,64);
						
						dataModule.createCertificateTemp(xzParam, function(res){
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