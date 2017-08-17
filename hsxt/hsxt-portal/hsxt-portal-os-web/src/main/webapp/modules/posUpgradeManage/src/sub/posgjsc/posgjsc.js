define(['text!posUpgradeManageTpl/sub/posgjsc/posgjsc.html',
        'posUpgradeManageLan'
		],function(posgjscTpl){
	return {
 
		showPage : function(tabid){
			 var self = this;
			 $('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(posgjscTpl)) ;
		
			 //选择POS机固件上传文件
			 $("#posUpLoad").live('change',function(){
				 var path = $("#posUpLoad").val();
				 $("#posFile").val(path);
				 if(!self.validate()){
					return false; 
				 };
				 var strs = path.split("\\");
				 var fileName = strs[strs.length-1];
				 var name = fileName.split("\.");
				 //截取版本号
				 $("#posUpgradeVerNo").val(name[0]);
				 var strss = fileName.split("\_");
				 var langStr = strss[4];
				 var lang = langStr.split("\.");
				 //截取语言类型
				 $("#language").val(lang[0]);
				 //文件上传服务
				 var fsServerUrl = comm.domainList["fsServerUrls"]
				 $("#fileHttpUrl").val(fsServerUrl);
				 $("#remind").css("display","none");
			 });
			 
			//保存POS机固件上传文件
			 $("#save").live('click',function(){
				 if(!self.validate()||!self.formValidate()){
					 return false;
				 }else{
					 comm.uploadFile(null,["posUpLoad"],function(data){//上传文件
							
						    var param = {};
							var posFileKey = data.posUpLoad;//保存上传成功的文件KEY值
							var fileMD5 = data.fileMD5;//文件MD5加密
							$("#fileCrcCode").val(fileMD5);
						    param.posFileKey = posFileKey;
						    param.isUpgrade = "Y";
							param.posDeviceType = $("#posDeviceType").val();
							param.posMachineNo = $("#posMachineNo").val();
							param.posUpgradeVerNo = $("#posUpgradeVerNo").val();
							param.language = $("#language").val();
							param.fileHttpUrl = $("#fileHttpUrl").val();
							param.fileCrcCode = data.fileMD5;
							param.fileBytes = data.fileSize;//文件大小
							param.isForceUpgrade = $("input[name='isForceUpgrade']:checked").val();
							comm.requestFun("saveFile",param,function(obj){
								comm.yes_alert_info("",comm.lang("posUpgradeManage").uploadInfo['uploadSuccess']);
								$('#fileForm')[0].reset(); 
							},comm.lang("posUpgradeManage"));
						    
						 },function(err){$("#posSelect").text('');},{annexType:"so"});
				 }
			 });
		},
		
		//生成CrcCode
		createCrcCode : function(filePath){
			var param = {filePath:filePath};
			comm.requestFun("createCrcCode",param,function(obj){
				var fileCrcCode = obj.fileCrcCode;
				$("#fileCrcCode").val(fileCrcCode);
			},"");
		},
		
		validate : function(){
			
			 var posSelect = $("#posUpLoad").val();
			 if(posSelect == '' || posSelect == null){
				 comm.yes_alert_info("",comm.lang("posUpgradeManage").uploadInfo['select']);
				 return false;
			 }
			 var selectStr = posSelect.split("\\");
			 var name = selectStr[selectStr.length-1];
			 var fileName = name.split("\.");
			 if(fileName.length != 2){
				 comm.yes_alert_info("",comm.lang("posUpgradeManage").uploadInfo['name']);
				 $("#remind").css("display","block");
				 $("#posFile").val('');
				 return false;
			 }
			 var strs = fileName[0];
			 var strss = strs.split("\_");
			 if(strss.length != 5){
				 comm.yes_alert_info("",comm.lang("posUpgradeManage").uploadInfo['name']);
				 $("#remind").css("display","block");
				 $("#posFile").val('');
				 return false;
			 }
			 if(strss[0] != 'hs' || strss[1] != 'pos'){
				 comm.yes_alert_info("",comm.lang("posUpgradeManage").uploadInfo['name']);
				 $("#remind").css("display","block");
				 $("#posFile").val('');
				 return false;
			 }
			 if(!$.isNumeric(strss[2]) || !$.isNumeric(strss[3])){
				 comm.yes_alert_info("",comm.lang("posUpgradeManage").uploadInfo['name']);
				 $("#remind").css("display","block");
				 $("#posFile").val('');
				 return false;
			 }
			 if(strss[2].length != 6){
				 comm.yes_alert_info("",comm.lang("posUpgradeManage").uploadInfo['verson']);
				 $("#remind").css("display","block");
				 $("#posFile").val('');
				 return false;
			 }
			 if(strss[3].length != 2){
				 comm.yes_alert_info("",comm.lang("posUpgradeManage").uploadInfo['verson1']);
				 $("#remind").css("display","block");
				 $("#posFile").val('');
				 return false;
			 }
			 
			 var extStart = posSelect.lastIndexOf(".");
			 var type = posSelect.substring(extStart, posSelect.length);
			 //检查上传文件的类型是否符合.so文件
			 if(type != ".so"){
				 comm.yes_alert_info("",comm.lang("posUpgradeManage").uploadInfo['selectType']);
				 $("#posFile").val('');
				 return false;
			 }
			 return true;
			
		},
		formValidate : function(){
			return comm.valid({
				formID : '#fileForm',
				left : 270,
				top : -1,
				rules : {
					posFile : {
						required : true	
					},
					posDeviceType : {
						required : true	
					},
					/*posMachineNo : {
						required : true	
					},*/
					posUpgradeVerNo : {
						required : true	
					},
					language : {
						required : true	
					},
					fileHttpUrl : {
						required : true	
					}/*,
					fileCrcCode : {
						required : true	
					}*/
				},
				messages : {
					posFile : {
						required : comm.lang("posUpgradeManage").alertInfo['required']
					},
					posDeviceType : {
						required : comm.lang("posUpgradeManage").alertInfo['required']
					},
					/*posMachineNo : {
						required : comm.lang("posUpgradeManage").alertInfo['required']
					},*/
					posUpgradeVerNo : {
						required : comm.lang("posUpgradeManage").alertInfo['required']
					},
					language : {
						required : comm.lang("posUpgradeManage").alertInfo['required']
					},
					fileHttpUrl : {
						required : comm.lang("posUpgradeManage").alertInfo['required']
					}/*,
					fileCrcCode : {
						required : comm.lang("posUpgradeManage").alertInfo['required']
					}*/
				}
			});
		}
	}
}); 

 