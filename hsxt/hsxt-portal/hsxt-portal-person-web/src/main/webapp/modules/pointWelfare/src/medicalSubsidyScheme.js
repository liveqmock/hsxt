/**
 * 免费医疗补贴计划
 */
define(["text!pointWelfareTpl/medicalSubsidyScheme.html"], function (tpl) {
	var medicalSubsidyScheme = {
		welfareQualify:null,//福利资格
		applyDetail:null,//审批详情
		show : function(dataModule){
			var self = this;
			var mobile;	//手机号码
			var cerNo;	//证件号码
			var realName;	//客户真实姓名
			var realNameStatus;	//客户实名认证状态
			//加载页面
			$("#myhs_zhgl_box").html(tpl);
			
			$("#otherProve1").change(function(){
				$("#otherProveSpan2").show();
			});
			$("#otherProve2").change(function(){
				$("#otherProveSpan3").show();
			});
			$("#otherProve3").change(function(){
				$("#otherProveSpan4").show();
			});
			$("#otherProve4").change(function(){
				$("#otherProveSpan5").show();
			});
			
			$('#healthCardNo').unbind("keypress").bind("keypress",function(e){
				var browserName=navigator.userAgent.toLowerCase();
				if(/msie/i.test(browserName) && !/opera/.test(browserName)){
					//IE
			    }else if(/firefox/i.test(browserName)){
			        //Firefox
			    	var keycode = e.which;
			    	if(keyCode<48 || keyCode>57){
						return false;
					}
					var realkey = String.fromCharCode(e.which);
					$('#healthCardNo').val(self.checkReceiveVal($('#healthCardNo').val()));
					return ;
			    }else if(/chrome/i.test(browserName) && /webkit/i.test(browserName) && /mozilla/i.test(browserName)){
			        //Chrome
			    }else if(/opera/i.test(browserName)){
			        //Opera
			    }else if(/webkit/i.test(browserName) &&!(/chrome/i.test(browserName) && /webkit/i.test(browserName) && /mozilla/i.test(browserName))){
			        //Safari
			    }else{
			        //未知浏览器
			    }
				if(e.keyCode<48 || e.keyCode>57){
					return false;
				}
				$('#healthCardNo').val(self.checkReceiveVal($('#healthCardNo').val()));
			});
			
			//获取用户绑定手机号码
			dataModule.findMobileByCustId(null,function (response) {
				mobile = response.data.mobile;	//手机号赋值
			});
			
			//加载数据
			dataModule.queryConsumerInfo(null,function(response){
				cerNo=response.data.cerNo;		
				//realName=response.data.realName;
				realName=comm.getCookie('custName');
				realNameStatus=response.data.realNameStatus;
				
				//3营业执照
				if ('3' == response.data.certype) {
					$('#medicalsub_div').removeClass('none');
					$("#N_note").removeClass('none');	
					return false;
				}
				
				dataModule.findWelfareQualify({welfareType:1},function (response) {
					if(response.data!=null&&response.data.welfareQualify!=null){
						self.welfareQualify = response.data.welfareQualify;
					}
					if(response.data!=null&&response.data.applyDetail!=null){
						self.applyDetail = response.data.applyDetail;
					}
					//未获得积分福利
					if(self.welfareQualify==null||self.welfareQualify.welfareType!=1){
						$('#medicalsub_div').removeClass('none');
						$("#N_note").removeClass('none');	
						return false;
					}
					//已获得积分福利
					self.wsApply();
				});
			});
			
			//输入医保卡号 医保中心的受理回执复印件 、消费者本人的社会保障卡复印件 必输 
			$('#healthCardNo').change(function(){
				$('#healthCardNo').val(self.checkReceiveVal($('#healthCardNo').val()));
				if($('#healthCardNo').val()!=""){
					$('.hscard_tip').show(); 
				}else{
					$('.hscard_tip').hide(); 
				}
				
			});
			
			//互生医疗补贴计划提交
			$('#medicalSubmit').click(function(){
				//判断是否绑定手机
				if(comm.isEmpty(mobile)){
					comm.warn_alert(comm.lang("pointWelfare").mobileNotBind);
					return false;
				}
				
				//判断是否实名认证
				if(3!=realNameStatus){
					comm.warn_alert(comm.lang("pointWelfare").noRealNameAuth);
					return false;
				}
				
				var valid = medicalSubsidyScheme.validateData();
				if (!valid.form()) {
					return;
				}
				comm.i_confirm('确认提交申请吗？', function () {
					var ids = ['hscPositive', 		'hscReverse',		'cerPositive', 	'cerReverse',		'otherProve1', 		'otherProve2', 		'otherProve3', 		'otherProve4', 		'otherProve5', 		'medicalAccept', 		'costCount',	'ssc', 	'imr', 	'ofr', 	'ddc', 	'cdl', 	'omr'];
					//图片上传
					comm.uploadFile(null, ids, function (response) {
						if(response!=null){
							medicalSubsidyScheme.setInputImgPath(response);
							medicalSubsidyScheme.initTmpPicPreview();
							var params = $("#medical_form").serializeJson();
							params.custId = $.cookie('custId');	// 客户ID
							params.hsResNo = $.cookie('resNo');	// 互生号
							params.proposerName = realName;	// 申请人姓名
							params.proposerPhone= mobile;	//String	是	申请人电话    需要消费者基本信息查询返回手机号
							params.proposerPapersNo = cerNo;//String	是	申请人证件号码
							params.applyDate = new Date();	//String	是	申请日期	//后台自己生成
							params.explain="互生医疗补贴计划申请";	//String	否	说明
							
							dataModule.applyMedicalSubsidies(params,function (response) {
								//跳转到积分福利查询
								$('#ul_myhs_right_tab li a[data-id="4"]').trigger('click');
								comm.yes_alert("申请互生医疗补贴计划提交成功");
							});
						}
						
					}, function(res){
						
					}, null);
					
				});	
		
				
			});
			
			//加载样例图片
			cacheUtil.findDocList(function(map){
				comm.initPicPreview("#subSidyPlan_demo_href_1", comm.getPicServerUrl(map.picList, '1015'), null);
				comm.initPicPreview("#subSidyPlan_demo_href_2", comm.getPicServerUrl(map.picList, '1016'), null);
				comm.initPicPreview("#subSidyPlan_demo_href_3", comm.getPicServerUrl(map.picList, '1001'), null);
				comm.initPicPreview("#subSidyPlan_demo_href_4", comm.getPicServerUrl(map.picList, '1002'), null);
			});
			
			this.initTmpPicPreview();
		},
		setInputImgPath:function(imgIds){
			for(var key in imgIds){  
				$('#'+key+'Path').val(imgIds[key]);
			}  
		},
		/**
		 * 绑定图片预览
		 */
		initTmpPicPreview : function(){
			var btnIds = ['#hscPositive', 		'#hscReverse',		'#cerPositive', 	'#cerReverse',		'#otherProve1', 	'#otherProve2',		'#otherProve3',		'#otherProve4',		'#otherProve5', 	'#medicalAccept', 		'#costCount',	'#ssc', 	'#imr', 	'#ofr', 	'#ddc', 	'#cdl', 	'#omr'];
			var imgIds = ['#hscPositiveImg', 	'#hscReverseImg',	'#cerPositiveImg', 	'#cerReverseImg',	'#otherProveImg1',	'#otherProveImg2',	'#otherProveImg3',	'#otherProveImg4',	'#otherProveImg5',	'#medicalAcceptImg', 	'#costCountImg','#sscImg', 	'#imrImg', 	'#ofrImg', 	'#ddcImg', 	'#cdlImg', 	'#omrImg'];
			$("body").on("change", "", function(){
				for(var k in imgIds){
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			comm.initUploadBtn(btnIds, imgIds, 81, 52);
		},
		checkReceiveVal	: function(str){
			var ret = "";
			for(var i=0;i<str.length;i++){
				if((str.charCodeAt(i)>44&&str.charCodeAt(i)<=57)||str.charCodeAt(i)==44){
					ret+=str.charAt(i);
				}
			}
			return ret;
		},
		wsApply :function(){
			var self = this;
			$('#medicalsub_div').removeClass('none');
			$("#Y_note").removeClass('none');	
			$("#medicalSubmitDiv").removeClass('none');
			$('#total_invert_point').text(comm.formatMoneyNumber(self.welfareQualify.investPoint));
			// 加载日期控件
			var minDate = self.welfareQualify.effectDate;
			var nowDate = comm.getCurrDate();
			var startDate = "";
			if( minDate != null && minDate != "" && minDate.length>=10){
				startDate = minDate.substr(0,10);//new Date(minDate).format('yyyy-MM-dd');
			}
			$("#startDate").datepicker({
				dateFormat : "yy-mm-dd",
				minDate : startDate,
				maxDate : nowDate
			});
			$("#endDate").datepicker({
				dateFormat : "yy-mm-dd",
				minDate : startDate,
				maxDate : nowDate
			});
		},
		validateData : function(){
			jQuery.validator.addMethod('healthNo', function(value, element) {
				if(comm.isEmpty($.trim($('#healthCardNo').val()))){
					return true;
				}
				return comm.isNotEmpty(value);
			}, '');
			
			var validate = $("#medical_form").validate({
				rules : {
					/*healthCardNo : {
						required : true
					},*/
					/*诊疗开始日期*/
					startDate : {
						required : true,
						endDate : "#endDate"
					},
					/*诊疗结束日期*/
					endDate : {
						required : true,
						beginDate : "#startDate"
					},
					/*所在城市*/
					city : {
						required : true
					},
					/*所在医院*/
					hospital : {
						required : true
					},
					/*互生卡正面*/
					hscPositive : {
						required : $("#hscPositiveImg").is(":visible")
					},
					/*互生卡背面*/
					hscReverse : {
						required : $("#hscReverseImg").is(":visible")
					},
					/*身份证正面*/
					cerPositive : {
						required : $("#cerPositiveImg").is(":visible")
					},
					/*身份证背面*/
					cerReverse : {
						required : $("#cerReverseImg").is(":visible")
					},
					/*原始收费收据复印件*/
					ofr : {
						required : $("#ofrImg").is(":visible")
					},
					/*费用明细清单复印件*/
					cdl : {
						required : $("#cdlImg").is(":visible")
					},
					/*门诊病例复印件*/
					omr : {
						required : $("#omrImg").is(":visible")
					},
					/*医保中心的受理 回执复印件 图片路径*/
					medicalAccept : {
						healthNo : true
					},
					/*医疗费用报销计算表复印件 图片路径*/
					costCount : {
						healthNo : true
					},
					/*消费者本人的社会保障卡复印件 图片路径*/
					ssc : {
						healthNo : true
					},
					/*病例诊断书复印件*/
					ddc : {
						required : $("#ddcImg").is(":visible")
					}
					
				},
				messages : {
					/*healthCardNo : {
						required : comm.lang("pointWelfare").medicalSubsidyScheme.cardNum,
						medicalCardNum : comm.lang("pointWelfare").medicalSubsidyScheme.cardNumError
					},*/
					startDate : {
						required : comm.lang("pointWelfare").medicalSubsidyScheme.startDate,
						endDate : comm.lang("pointWelfare").medicalSubsidyScheme.gtEndDate
					},
					/*诊疗结束日期*/
					endDate : {
						required : comm.lang("pointWelfare").medicalSubsidyScheme.endDate,
						beginDate : comm.lang("pointWelfare").medicalSubsidyScheme.lsBeginDate
					},
					/*所在城市*/
					city : {
						required : comm.lang("pointWelfare").medicalSubsidyScheme.city,
					},
					/*所在医院*/
					hospital : {
						required : comm.lang("pointWelfare").medicalSubsidyScheme.hospital,
					},
					/*互生卡正面*/
					hscPositive : {
						required : comm.lang("pointWelfare").medicalSubsidyScheme.hscPositive,
					},
					/*互生卡背面*/
					hscReverse : {
						required : comm.lang("pointWelfare").medicalSubsidyScheme.hscReverse,
					},
					/*身份证正面*/
					cerPositive : {
						required : comm.lang("pointWelfare").medicalSubsidyScheme.cerPositive,
					},
					/*身份证背面*/
					cerReverse : {
						required : comm.lang("pointWelfare").medicalSubsidyScheme.cerReverse,
					},
					/*原始收费收据复印件*/
					ofr : {
						required : comm.lang("pointWelfare").medicalSubsidyScheme.ofr,
					},
					/*费用明细清单复印件*/
					cdl : {
						required : comm.lang("pointWelfare").medicalSubsidyScheme.cdl,
					},
					/*门诊病例复印件*/
					omr : {
						required : comm.lang("pointWelfare").medicalSubsidyScheme.omr,
					},
					/*医保中心的受理 回执复印件 图片路径*/
					medicalAccept : {
						healthNo : comm.lang("pointWelfare").accidentInsurance.medicalAccept
					},
					/*医疗费用报销计算表复印件 图片路径*/
					costCount : {
						healthNo : comm.lang("pointWelfare").accidentInsurance.costCount
					},
					/*消费者本人的社会保障卡复印件 图片路径*/
					ssc : {
						healthNo : comm.lang("pointWelfare").accidentInsurance.ssc
					},
					/*病例诊断书复印件*/
					ddc : {
						required : comm.lang("pointWelfare").medicalSubsidyScheme.ddc,
					}
				},
				errorPlacement : function (error, element) {
					if ($(element).is(":text")) {
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+150 top",
								at : "left top"
							}
						}).tooltip("open");
						$(".ui-tooltip").css("max-width", "230px");
					} else {
						$(element.parent()).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+50 top+15",
								at : "left top"
							}
						}).tooltip("open");
					}
				},
				success : function (element) {
					if (!$(element).is(":text")) {
						element = element.parent();
					}
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});

			/**
			 * 如果是修改则无需对上传文件进行必填校验
			 */
			if($("#hscPositive").val() != ""){
				validate.settings.rules.hscPositive = {required : false};
			}
			if($("#hscReverse").val() != ""){
				validate.settings.rules.hscReverse = {required : false};
			}
			if($("#cerPositive").val() != ""){
				validate.settings.rules.cerPositive = {required : false};
			}
			if($("#cerReverse").val() != ""){
				validate.settings.rules.cerReverse = {required : false};
			}
			if($("#otherProve").val() != ""){
				validate.settings.rules.otherProve = {required : false};
			}
			if($("#medicalAccept").val() != ""){
				validate.settings.rules.medicalAccept = {required : false};
			}
			if($("#costCount").val() != ""){
				validate.settings.rules.costCount = {required : false};
			}
			if($("#ssc").val() != ""){
				validate.settings.rules.ssc = {required : false};
			}
			if($("#imr").val() != ""){
				validate.settings.rules.imr = {required : false};
			}
			if($("#ofr").val() != ""){
				validate.settings.rules.ofr = {required : false};
			}
			if($("#ddc").val() != ""){
				validate.settings.rules.ddc = {required : false};
			}
			if($("#cdl").val() != ""){
				validate.settings.rules.cdl = {required : false};
			}
			if($("#omr").val() != ""){
				validate.settings.rules.omr = {required : false};
			}
			return validate;
		}
	};
	return medicalSubsidyScheme
});
