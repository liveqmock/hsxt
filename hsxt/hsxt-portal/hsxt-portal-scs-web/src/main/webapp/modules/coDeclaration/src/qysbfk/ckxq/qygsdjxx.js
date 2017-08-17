define(['text!coDeclarationTpl/qysbfk/ckxq/qygsdjxx.html',
		'text!coDeclarationTpl/qysbfk/ckxq/qygsdjxx_dialog.html',
		'coDeclarationDat/qysbfk/ckxq/qygsdjxx',
		'coDeclarationLan'
        ],function(qygsdjxxTpl, qygsdjxx_dialogTpl, dataModoule){
   var 	cacheData = null;
	return qysbfk_qygsdjxx_services = {	 	
		showPage : function(){
		 	this.initData();
		 	this.initForm();
		},
		/**
		 * 绑定图片预览
		 */
		picPreview : function(){
			$("body").on("change", "", function(){
				comm.initTmpPicPreview('#thum-3', $('#thum-3').children().first().attr('src'));
			});
			comm.initUploadBtn(['#busLicenceFile'], ['#thum-3'],87,52);
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;
			$('#ckxq_xg').css('display','');		 
		 	//取消		 
		 	$('#ckxq_qx').click(function(){		 		
		 		if($('#ckxq_xg').text() == '保　存' ){			 			
		 			$('#ckxq_xg').text('修　改');
		 			$("#skqr_tj").show();
		 			self.initShowForm(cacheData, false);
		 		} else {
		 			$('#cx_content_list').removeClass('none');
		 			$('#cx_content_detail').addClass('none');
		 		}		 		
		 	});
		 	//修改
		 	$('#ckxq_xg').click(function(){	
		 		if ($(this).text() !='保　存' ){
		 			$('#qygsdjxx_1').addClass('none');
		 			$('#qygsdjxx_2').removeClass('none');
		 			$(this).text('保　存');
		 			$("#skqr_tj").hide();
		 		} else {
		 			self.initChangeData();
		 		}		 		
		 	});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			dataModoule.findDeclareEntByApplyId({"applyId": $("#editApplyId").val()}, function(res){
				cacheData = null;
				cacheData = res.data;
				self.initShowForm(res.data, false);
			});
		},
		/**
		 * 初始化显示页面
		 * @param data 注册信息
		 * @param isCallBack 是否是回调成功
		 */
		initShowForm : function(data, isCallBack){
			var self = this;
			comm.delCache('coDeclaration', 'qysbfk-qygsdjxx-info');
			comm.setCache('coDeclaration', 'qysbfk-qygsdjxx-info', data);
			if(data){
				$('#ckxq_view').html('').append(_.template(qygsdjxxTpl, data));
				//成立日期
				$("#establishingDate").datepicker({
					changeMonth : true,
					changeYear : true,
					dateFormat : "yy-mm-dd",
					maxDate : comm.getCurrDate()
				});
				
				$("#establishingDate").val((data.establishingDate?data.establishingDate:""));
				
				if((data.licenseAptitude && data.licenseAptitude.fileId)){
					comm.initPicPreview("#thum-3", data.licenseAptitude.fileId, "");
					comm.initPicPreview("#vthum-3", data.licenseAptitude.fileId, "");
					$("#thum-3").html("<img width='107' height='64' src='" + comm.getFsServerUrl(data.licenseAptitude.fileId) + "'/>");
					$("#vthum-3").html("<img width='107' height='64' src='" + comm.getFsServerUrl(data.licenseAptitude.fileId) + "'/>");
					qysbfk_qygsdjxx_services.busLicenceApitId = comm.removeNull(data.licenseAptitude.aptitudeId);
					qysbfk_qygsdjxx_services.busLicenceFileiId = comm.removeNull(data.licenseAptitude.fileId);
				}
			}
			if(isCallBack){
				$('#ckxq_xg').text('修改');
				$("#skqr_tj").show();
				//$('#ckxq_xg').click();
			}
			//加载样例图片
			cacheUtil.findDocList(function (map) {
				comm.initPicPreview("#case-fileId-3", comm.getPicServerUrl(map.picList, '1010'), null);
			});
			
			qysbfk_qygsdjxx_services.picPreview();
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#qygsdjxx_form").validate({
				 rules : {
					entName : {
						required : true,
						rangelength:[2, 64]
					},
					legalName : {
						required : true,
						rangelength:[2, 20]
					},
					entType : {
						required : false,
						rangelength:[2, 20]
					},
					establishingDate : {
						required : false,
						date : true
					},
					entAddress : {
						required : true,
						rangelength:[2, 128]
					},
					licenseNo : {
						required : true,
						businessLicence:true
					},
					dealArea : {
						required : false,
						rangelength:[0, 300]
					},
					limitDate : {
						required : false,
						rangelength:[0, 50]
					}
				},
				messages : {
					entName : {
						required : comm.lang("coDeclaration")[32617],
						rangelength:comm.lang("coDeclaration")[32630]
					},
					legalName : {
						required :  comm.lang("coDeclaration")[32620],
						rangelength:comm.lang("coDeclaration")[32632]
					},
					entType : {
						required : comm.lang("coDeclaration")[32673],
						rangelength:comm.lang("coDeclaration")[32674]
					},
					establishingDate : {
						required : comm.lang("coDeclaration")[32675],
						date : comm.lang("coDeclaration")[32676]
					},
					entAddress : {
						required : comm.lang("coDeclaration")[32618],
						rangelength:comm.lang("coDeclaration")[32631]
					},
					licenseNo : {
						required : comm.lang("coDeclaration")[32619],
						businessLicence:comm.lang("coDeclaration")[32507]
					},
					dealArea : {
						required : comm.lang("coDeclaration")[32626],
						rangelength: comm.lang("coDeclaration")[32628]
					},
					limitDate : {
						required : comm.lang("coDeclaration")[32677],
						rangelength:comm.lang("coDeclaration")[32678]
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
			if(comm.removeNull(qysbfk_qygsdjxx_services.busLicenceFileiId) != ""){
				validate.settings.rules.busLicenceFile = {required : false};
				validate.settings.messages.busLicenceFile = {required : ''};
			}else{
				validate.settings.rules.busLicenceFile = {required : true};
				validate.settings.messages.busLicenceFile = {required : comm.lang("coDeclaration")[32639]};
			}
			return validate;
		},
		/**
		 * 表单校验
		 */
		validateViewMarkData : function(){
			return $("#qygsdjxx_dialog_form").validate({
				rules : {
					viewMark : {
						rangelength : [0, 300],
					}
				},
				messages : {
					viewMark : {
						rangelength : comm.lang("coDeclaration")[32599]
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
		/**
		 * 初始化提交页面
		 */
		initChangeData : function(){
			var self = this;
			if(!self.validateData().form()){
				return;
			}
			var ckArray = [];//存入需要检查是否改变的对象
			$(".isChange").each(function(){
				var desc = $(this).prev()[0].innerText;
				ckArray.push({name:this.name, value:this.value, desc:desc});
			});
			var odata = comm.getCache('coDeclaration', 'qysbfk-qygsdjxx-info');
			var ndata = comm.cloneJSON(odata);
			var trs = "";
			var chg = {};
			for(var key in ckArray){
				if(comm.removeNull(odata[ckArray[key].name]) != ckArray[key].value){
					trs+="<tr><td class=\"view_item\">"+ckArray[key].desc+"</td><td class=\"view_text\">"+comm.removeNull(odata[ckArray[key].name])+"</td><td class=\"view_text\">"+ckArray[key].value+"</td></tr>";
					chg[ckArray[key].name] = {"old":comm.removeNull(odata[ckArray[key].name]), "new":ckArray[key].value};
					ndata[ckArray[key].name] = ckArray[key].value;
				}
			}
			
			var oldArray = [];//修改前图片信息
			var newArray = [];//修改后图片信息
			//处理营业执照扫描件
			if($("#busLicenceFile").val() != ""){
				var fId = qysbfk_qygsdjxx_services.busLicenceFileiId;
				var newPic = "<a href=\"#\" id=\"prv-pic-new\" class=\"blue\">查看</a>";
				var oldPic = "无";
				if(fId != ""){
					oldPic = "<a href=\"#\" id=\"prv-pic-old\" class=\"blue\">查看</a>";
					oldArray.push({picId:"#prv-pic-old", fileId: fId, title:"修改前营业执照扫描件"});
				}
				newArray.push({picId:"#prv-pic-new", fileId: '#thum-3', title:"修改后营业执照扫描件"});
				trs+="<tr><td class=\"view_item\">营业执照扫描件</td><td class=\"view_text\">"+oldPic+"</td><td class=\"view_text\">"+newPic+"</td></tr>";
			}
			
 			if(trs == ""){
 				comm.warn_alert(comm.lang("coDeclaration").noUpdate);
 				return;
 			}
 			//提交
 			$('#qygsdjxx_dialog > p').html(_.template(qygsdjxx_dialogTpl));
 			$('#copTable tr:eq(1)').before(trs);
			
			for(var key in oldArray){
				comm.initPicPreview(oldArray[key].picId, oldArray[key].fileId, oldArray[key].title);
			}
			for(var key in newArray){
				comm.initTmpPicPreview(newArray[key].picId, $(newArray[key].fileId).children().first().attr('src'), newArray[key].title);
			}
			
 			$('#qygsdjxx_dialog').dialog({
 				width:800,
 				buttons:{
 					'确定':function(){
 						if(!self.validateViewMarkData().form()){
 							return;
 						}
						var ids = [];
						var delFileIds = [];
						if($("#busLicenceFile").val() != ""){
							if(comm.removeNull(qysbfk_qygsdjxx_services.busLicenceFileiId) != ""){
								delFileIds[delFileIds.length] = qysbfk_qygsdjxx_services.busLicenceFileiId;
							}
							ids[ids.length] = "busLicenceFile";
						}
						if(ids.length == 0){
							qysbfk_qygsdjxx_services.saveInfo(ndata, chg);
						}else{
							comm.uploadFile(null, ids, function(data){
								if(data.busLicenceFile){
									qysbfk_qygsdjxx_services.busLicenceFileiId = data.busLicenceFile;
								}
								qysbfk_qygsdjxx_services.saveInfo(ndata, chg);
								qysbfk_qygsdjxx_services.picPreview();
							}, function(err){
								qysbfk_qygsdjxx_services.picPreview();
							}, {delFileIds : delFileIds});
						}
 					},
 					'取消':function(){
 						$(this).dialog('destroy');
 					}
 				}
 			});	
		},
		saveInfo : function(ndata, chg){
			delete ndata.licenseAptitude;
			ndata.optRemark = $("#viewMark").val();
			ndata.changeContent = JSON.stringify(chg);
			ndata.busLicenceApitId = comm.removeNull(qysbfk_qygsdjxx_services.busLicenceApitId);
			ndata.busLicenceFileiId = comm.removeNull(qysbfk_qygsdjxx_services.busLicenceFileiId);
			dataModoule.saveDeclareEnt(ndata, function(res){
				if(res && res.data && res.data.licenseAptitude && res.data.licenseAptitude.aptitudeId){
					cacheData = res.data;
					qysbfk_qygsdjxx_services.busLicenceApitId = res.data.licenseAptitude.aptitudeId;
					ndata.licenseAptitude = {};
					ndata.licenseAptitude.aptitudeId = res.data.licenseAptitude.aptitudeId;
					ndata.licenseAptitude.fileId = res.data.licenseAptitude.fileId;
				}
				comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
					$('#qygsdjxx_dialog').dialog('destroy');
					qysbfk_qygsdjxx_services.initShowForm(ndata, true);
				}});
			});
		}
	}
}); 
