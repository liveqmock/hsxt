define(['text!companyInfoTpl/gsdjxx/gsdjxxxg.html',
        'companyInfoDat/gsdjxx/gsdjxx',
		'companyInfoLan'],function(gsdjxxxgTpl, dataModoule){
	var gsdjxxxgPage = {
		showPage : function(){
			gsdjxxxgPage.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#busibox').html(_.template(gsdjxxxgTpl, data));
			
			var busiLicenseImg = data.busiLicenseImg;
			$("#gsdjxxxg_busi_license_img").attr("src", comm.getFsServerUrl(busiLicenseImg));
			
			comm.initUploadBtn(['#gsdjxxxg_busi_license_file'], ['#gsdjxxxg_busi_license_span'], 81, 52);
			
			//绑定取消
			$('#backBt_gongshang').click(function(){
				$('#qyxx_gsdjxx').click();
			});
			
			//绑定保存
			$('#submitBt_gongshang').click(function(){
				gsdjxxxgPage.saveFile();
			});
			
			//成立日期
			$("#buildDate").datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				maxDate : comm.getCurrDate()
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
					gsdjxxxgPage.initForm(res.data);
				});
			}else{
				gsdjxxxgPage.initForm(entAllInfo);
			}
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#qygsdjxx_form").validate({
				 rules : {
					entCustName : {
						required : true,
						rangelength : [2, 80]
					},
					entRegAddr : {
						required : true,
						rangelength : [2, 128]
					},
					legalPerson : {
						required : true,
						rangelength : [2, 20]
					},
					busiLicenseNo : {
						required : true,
						businessLicence : true
					},
					nature : {
						required : true
					},
					buildDate : {
						required : true,
						date : true,
					},
					businessScope : {
						rangelength:[0, 300]
					},
					endDate : {
						rangelength:[0, 50]
					}
				},
				messages : {
					entCustName : {
						required : comm.lang("companyInfo")[36372],
						rangelength:comm.lang("companyInfo")[36373]
					},
					entRegAddr : {
						required : comm.lang("companyInfo")[36358],
						rangelength:comm.lang("companyInfo")[36359]
					},
					legalPerson : {
						required : comm.lang("companyInfo")[36360],
						rangelength:comm.lang("companyInfo")[36361]
					},
					busiLicenseNo : {
						required : comm.lang("companyInfo")[36362],
						businessLicence:comm.lang("companyInfo")[36363]
					},
					nature : {
						required : comm.lang("companyInfo")[36351],
						rangelength:comm.lang("companyInfo")[36027]
					},
					buildDate : {
						required : comm.lang("companyInfo")[36354],
						date : comm.lang("companyInfo")[36029]
					},
					businessScope : {
						rangelength: comm.lang("companyInfo")[36036]
					},
					endDate : {
						rangelength:comm.lang("companyInfo")[36037]
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
		 * 保存文件
		 */
		saveFile : function(){
			if(!gsdjxxxgPage.validateData().form()){
				return;
			}
			gsdjxxxgPage.saveData();
//			var ids = [];
//			var delFileIds = [];
//			if(comm.isNotEmpty($("#gsdjxxxg_busi_license_file").val())){
//				if(comm.isNotEmpty($("#busiLicenseImg").val())){
//					delFileIds[delFileIds.length] = $("#busiLicenseImg").val();
//				}
//				ids[ids.length] = "gsdjxxxg_busi_license_file";
//			}
//			if(ids.length == 0){
//				gsdjxxxgPage.saveData();
//			}else{
//				comm.uploadFile(null, ids, function(data){
//					if(data.gsdjxxxg_busi_license_file){
//						$("#busiLicenseImg").val(data.gsdjxxxg_busi_license_file);
//						gsdjxxxgPage.saveData();
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
			params.entCustName = $.trim($('#entCustName').val());
			params.nature = $.trim($("#nature").val());//企业类型
			params.buildDate = $.trim($("#buildDate").val());//成立日期
			params.legalPerson = $.trim($('#legalPerson').val());//法人代表
			params.businessScope = $.trim($("#businessScope").val());//经营范围
			params.endDate = $.trim($("#endDate").val());//营业期限
			params.busiLicenseNo = $.trim($('#busiLicenseNo').val());//营业执照号
			params.entRegAddr = $.trim($('#entRegAddr').val());//企业注册地址
			params.busiLicenseImg = $.trim($("#busiLicenseImg").val());//营业执照附件
			dataModoule.updateEntExtInfo(params, function(res){
				//更新缓存数据
				comm.delCache("companyInfo", "entAllInfo");
				comm.setCookie('custEntName', params.entCustName);
				comm.setCookie('entName', params.entCustName);
				comm.alert({content:comm.lang("companyInfo")[22000], callOk:function(){
					$("#qyxx_gsdjxx").click();
				}});
			});
		}
	};
	return gsdjxxxgPage
}); 

 