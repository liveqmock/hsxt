define(['text!companyInfoTpl/lxxx/lxxxxg.html',
        'companyInfoDat/lxxx/lxxx',
		'companyInfoLan'],function(lxxxxgTpl, dataModoule){
	var lxxxxgPage = {
		showPage : function(){
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#busibox').html(_.template(lxxxxgTpl, data));
			$('#backBt_qiyelianxi').click(function(){
			 	$('#qyxx_lxxx').click();
			});
			$('#submitBt_qiyelianxi').click(function(){
			 	lxxxxgPage.saveLink();
			});
			//联系人授权委托书,如果不为空则隐藏上传按钮、下载模板按钮
			$("#certificateFile").val(data.contactProxy);
			$("#img-2").attr("src", comm.getFsServerUrl(data.contactProxy));
//			加载下载模板
			cacheUtil.findDocList(function(map){
				comm.initDownload("#certificateFileId", map.comList, '1007');
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var entAllInfo = comm.getCache("companyInfo", "entAllInfo");
			if(entAllInfo == null){
				dataModoule.findEntAllInfo(null, function(res){
					comm.setCache("companyInfo", "entAllInfo", res.data);
					lxxxxgPage.initForm(res.data);
				});
			}else{
				lxxxxgPage.initForm(entAllInfo);
			}
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			$.validator.addMethod('mobile', function( value, element ){
			    // /^1\d{10}$/ 来自支付宝的正则
			    return this.optional( element ) || /^1\d{10}$/.test( value );
			}, '');
			 var validate = $("#qylxxx_form").validate({
				 rules : {
					contactPerson: {
						required : true,
						rangelength : [2, 20]
					},
					contactPhone : {
						required : true,
						mobile : true
					},
					contactAddr : {
						required : false,
						rangelength : [2, 128]
					},
					postCode : {
						required : false,
						zipCode : true,
					},
					contactEmail : {
						required : false,
						email2 : true
					},
				},
				messages : {
					contactPerson: {
						required : comm.lang("companyInfo")[36368],
						rangelength : comm.lang("companyInfo")[36369]
					},
					contactPhone : {
						required : comm.lang("companyInfo")[36370],
						mobile : comm.lang("companyInfo")[36371]
					},
					contactAddr : {
						rangelength : comm.lang("companyInfo")[33232]
					},
					postCode : {
						zipCode : comm.lang("companyInfo")[33236]
					},
					contactEmail : {
						email2 : comm.lang("companyInfo")[33238]
					},
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
			//检查附件信息，如果存在附件信息则不检查文件必填
			var cerFile = $("#certificateFile").val();
			if(cerFile != ""){
				validate.settings.rules.certificate = {required : false};
				validate.settings.messages.certificate = {required : comm.lang("companyInfo")[33239]};
			}else{
				validate.settings.rules.certificate = {required : true};
				validate.settings.messages.certificate = {required : comm.lang("companyInfo")[33239]};
			}
			return validate;
		},
		/**
		 * /绑定图片预览
		 */
		picPreview : function(){
			$("body").on("change", "", function(){
				comm.initTmpPicPreview('#bimg', $('#bimg').children().first().attr('src'));
			});
			comm.initUploadBtn(['#certificate'], ['#bimg'], 81, 52);
		},
		/**
		 * 保存联系信息
		 */
		saveLink : function(){
			if(!lxxxxgPage.validateData().form()){
				return;
			}
			lxxxxgPage.saveData();
//			var ids = [];
//			var delFileIds = [];
//			if($("#certificate").val() != ""){
//				if($("#certificateFile").val() != ""){
//					delFileIds[delFileIds.length] = $("#certificateFile").val();
//				}
//				ids[ids.length] = "certificate";
//			}
//			if(ids.length == 0){
//				lxxxxgPage.saveData();
//			}else{
//				comm.uploadFile(null, ids, function(data){
//					if(data.certificate){
//						$("#certificateFile").val(data.certificate);
//						lxxxxgPage.saveData();
//					}
//				}, function(err){
//				}, {delFileIds : delFileIds});
//			}
		},
		/**
		 * 保存数据
		 */
		saveData : function(){
			var params = {};
			params.contactPhone = $.trim($("#contactPhone").val());//联系电话
			params.contactPerson = $.trim($('#contactPerson').val());
			params.contactAddr = $("#contactAddr").val();//联系地址
			params.postCode = $("#postCode").val();//邮政编码
			params.contactEmail = $("#contactEmail").val();//安全邮箱
			params.contactProxy = $("#certificateFile").val();//联系人授权委托书
			dataModoule.updateLinkInfo(params, function(res){
				comm.delCache("companyInfo", "entAllInfo");
				comm.alert({content:comm.lang("companyInfo")[22000], callOk:function(){
					$('#qyxx_lxxx').click();
				}});
			});
		}
	};
	return lxxxxgPage
}); 

 