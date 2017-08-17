define(['text!coDeclarationTpl/qysbfk/ckxq/qygsdjxx.html',
		'text!coDeclarationTpl/qysbfk/ckxq/qygsdjxx_dialog.html',
		'coDeclarationDat/qysbfk/ckxq/qygsdjxx',
		'coDeclarationLan'],function(qygsdjxxTpl, qygsdjxx_dialogTpl, dataModoule){
	var self = {	 	
		showPage : function(){
			self.initData();
			self.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
		 	$('#ckxq_xg').css('display','');	 
		 	//取消		 
		 	$('#ckxq_qx').click(function(){		 		
		 		if($('#ckxq_xg').text() == '保　存' ){			 			
		 			$('#ckxq_xg').text('修　改');
		 			$("#ckxq_qx").text('返　回');
		 			$("#skqr_tj").show();
		 			$('#qygsdjxx_1').removeClass('none');
		 			$('#qygsdjxx_2').addClass('none');
		 		} else {
		 			self.gotoList();
		 		}		 		
		 	});
		 	//修改
		 	$('#ckxq_xg').click(function(){	
		 		if ($(this).text() !='保　存' ){
		 			$('#qygsdjxx_1').addClass('none');
		 			$('#qygsdjxx_2').removeClass('none');
		 			$(this).text('保　存');
		 			$("#ckxq_qx").text('取　消');
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
			dataModoule.findDeclareEntByApplyId({"applyId": $("#applyId").val()}, function(res){
				self.initShowForm(res.data, false);
			});
		},
		/**
		 * /绑定图片预览
		 */
		picPreview : function(){
			$("body").on("change", "", function(){
				comm.initTmpPicPreview('#licenseImg', $('#licenseImg').children().first().attr('src'));
			});
			comm.initUploadBtn(['#licenseFileBtn'], ['#licenseImg'], 81, 52);
		},
		/**
		 * 初始化显示页面
		 * @param data 注册信息
		 * @param isCallBack 是否是回调成功
		 */
		initShowForm : function(data, isCallBack){
			comm.delCache('coDeclaration', 'qysbfk-qygsdjxx-info');
			comm.setCache('coDeclaration', 'qysbfk-qygsdjxx-info', data);
			if(data){
				$('#ckxq_view').html(_.template(qygsdjxxTpl, data));
				//成立日期
				$("#establishingDate").datepicker({
					changeMonth : true,
					changeYear : true,
					dateFormat : "yy-mm-dd",
					maxDate : comm.getCurrDate()
				});
				$("#establishingDate").val((data.establishingDate?data.establishingDate:""));
				if(data.licenseAptitude&&data.licenseAptitude.fileId){
					comm.initPicPreview("#licenseImg", data.licenseAptitude.fileId, "");
					comm.initPicPreview("#licenseImg2", data.licenseAptitude.fileId, "");
					$("#licenseImg2").attr("src", comm.getFsServerUrl(data.licenseAptitude.fileId));
					$("#licenseImg").children().first().attr("src", comm.getFsServerUrl(data.licenseAptitude.fileId));
					$("#licenseImg").css({'background':'none'});
				}
			}
			if(isCallBack){
				$('#ckxq_xg').text('修改');
				$('#ckxq_xg').click();
				$("#skqr_tj").show();
			}
			self.picPreview();
			//加载样例图片
			cacheUtil.findDocList(function (map) {
				comm.initPicPreview("#licenseDemoBtn", comm.getPicServerUrl(map.picList, '1010'), null);
			});
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
					/*legalCreType : {
						required : false
					},
					orgNo : {
						required : true,
						orgcode : true
					},*/
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
					/*linkmanMobile : {
						required : false,
						phoneCN : true
					},
					taxNo : {
						required : true,
						taxcode : true
					},*/
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
						required : comm.lang("coDeclaration")[33205],
						rangelength:comm.lang("coDeclaration")[33206]
					},
					legalName : {
						required :  comm.lang("coDeclaration")[33207],
						rangelength:comm.lang("coDeclaration")[33208]
					},
					entType : {
						required : comm.lang("coDeclaration")[33209],
						rangelength:comm.lang("coDeclaration")[33210]
					},
					/*legalCreType : {
						required : comm.lang("coDeclaration")[33211]
					},
					orgNo : {
						required : comm.lang("coDeclaration")[33212],
						orgcode :  comm.lang("coDeclaration")[33300]
					},*/
					establishingDate : {
						required : comm.lang("coDeclaration")[33213],
						date : comm.lang("coDeclaration")[33214]
					},
					entAddress : {
						required : comm.lang("coDeclaration")[33215],
						rangelength:comm.lang("coDeclaration")[33216]
					},
					licenseNo : {
						required : comm.lang("coDeclaration")[33217],
						businessLicence:comm.lang("coDeclaration")[33218]
					},
					/*linkmanMobile : {
						required : comm.lang("coDeclaration")[33219],
						phoneCN : comm.lang("coDeclaration")[33220]
					},
					taxNo : {
						required : comm.lang("coDeclaration")[33221],
						taxcode:comm.lang("coDeclaration")[33301]
					},*/
					dealArea : {
						required : comm.lang("coDeclaration")[33222],
						rangelength: comm.lang("coDeclaration")[33223]
					},
					limitDate : {
						required : comm.lang("coDeclaration")[33224],
						rangelength:comm.lang("coDeclaration")[33225]
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
			return validate;
		},
		/**
		 * 表单校验
		 */
		validateViewMarkData : function(){
			return $("#qygsdjxx_dialog_form").validate({
				rules : {
					viewMark : {
						rangelength : [0, 300]
					},
					doubleUserName : {
						required : true
					},
					doublePassword : {
						required : true
					}
				},
				messages : {
					viewMark : {
						rangelength : comm.lang("coDeclaration")[33204]
					},
					doubleUserName : {
						required : comm.lang("coDeclaration")[33255]
					},
					doublePassword : {
						required : comm.lang("coDeclaration")[33256]
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
		 * 初始化保存页面
		 */
		initChangeData : function(){
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
			for(var key = 0;key < ckArray.length; key++){
				if(comm.removeNull(odata[ckArray[key].name]) != ckArray[key].value){
					trs+="<tr><td class=\"view_item\">"+ckArray[key].desc+"</td><td style='word-break:break-all' class=\"view_text\">"+comm.removeNull(odata[ckArray[key].name])+"</td><td style='word-break:break-all' class=\"view_text\">"+ckArray[key].value+"</td></tr>";
					chg[ckArray[key].name] = {"old":comm.removeNull(odata[ckArray[key].name]), "new":ckArray[key].value};
					ndata[ckArray[key].name] = ckArray[key].value;
				}
			}

			var oldArray = [];//修改前图片信息
			var newArray = [];//修改后图片信息
			//处理营业执照扫描件
			if(comm.isNotEmpty($("#licenseFileBtn").val())){
				var fId = $("#licenseFile").val();
				var newPic = "<a href=\"#\" id=\"prv-pic-new\" class=\"blue\">查看</a>";
				var oldPic = "无";
				if(fId != ""){
					oldPic = "<a href=\"#\" id=\"prv-pic-old\" class=\"blue\">查看</a>";
					oldArray.push({picId:"#prv-pic-old", fileId: fId, title:"修改前营业执照扫描件"});
				}
				newArray.push({picId:"#prv-pic-new", fileId: '#licenseImg', title:"修改后营业执照扫描件"});
				trs+="<tr><td class=\"view_item\">营业执照扫描件</td><td class=\"view_text\">"+oldPic+"</td><td class=\"view_text\">"+newPic+"</td></tr>";
			}
			
			if(trs == ""){
				comm.warn_alert(comm.lang("coDeclaration").noUpdate);
				return;
			}
			//保存
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
						//验证双签
						comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function(verifyRes){
							ndata.dblOptCustId = verifyRes.data;
							ndata.optRemark = $("#viewMark").val();
							ndata.changeContent = JSON.stringify(chg);
							
							
							var ids = [];
	 						var delFileIds = [];
	 						if(comm.isNotEmpty($("#licenseFileBtn").val())){
	 							ids[0] = "licenseFileBtn";
	 							if(comm.isNotEmpty($("#licenseFile").val())){
	 								delFileIds[0] = $("#licenseFile").val();
	 							}
	 						}
	 						if(ids.length==0){
	 							self.saveData(ndata);
	 							return;
	 						}
							
	 						comm.uploadFile(null, ids, function(data){
								if(data.licenseFileBtn){
									$("#licenseFile").val(data.licenseFileBtn);
									ndata.licenseFile = data.licenseFileBtn;
								}
								self.saveData(ndata);
							}, function(err){
								self.picPreview();
							}, {delFileIds : delFileIds});
							
							
						});
					},
					'取消':function(){
						$(this).dialog('destroy');
					}
				}
			});	
		},
		/**
		 * 保存数据
		 * @param ndata 注册信息
		 */
		saveData : function(ndata){
			delete ndata.licenseAptitude;
			dataModoule.saveDeclareEnt(ndata, function(res){
				comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
					$('#qygsdjxx_dialog').dialog('destroy');
					$('#ckxq_qygsdjxx').click();
				}});
			});
		},
		/**
		 * 返回至列表
		 */
		gotoList : function(){
			var custType = $("#custType").val();
			if(custType == "4"){
				$('#qysbfk_fwgssbfk').click();
			}else if(custType == "2"){
				$('#qysbfk_cyqysbfk').click();
			}else{
				$('#qysbfk_tgqysbfk').click();
			}
		}
	};
	return self;
}); 
