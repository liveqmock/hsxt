/**
 * 意外伤害保障申请
 */
define(["text!pointWelfareTpl/accidentInsurance.html", "text!pointWelfareTpl/accidentInsurance2.html"], function (tpl, tpl2) {
	var accidentInsurance = {
		welfareQualify:null,//福利资格
		applyDetail:null,//审批详情
		show : function(dataModule){
			var self = this;
			var mobile;	//手机号码
			var cerNo;	//证件号码
			var realName;	//客户真实姓名
			var realNameStatus;	//客户实名认证状态
			//加载页面
			$("#myhs_zhgl_box").html(tpl + tpl2);
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
			
			//获取用户绑定手机号码
			dataModule.findMobileByCustId(null,function (response) {
				mobile = response.data.mobile;	//手机号赋值
			});
			
			//加载样例图片
			cacheUtil.findDocList(function(map){
				comm.initPicPreview("#accInsurance_demo_href_1", comm.getPicServerUrl(map.picList, '1015'), null);
				comm.initPicPreview("#accInsurance_demo_href_2", comm.getPicServerUrl(map.picList, '1016'), null);
				comm.initPicPreview("#accInsurance_demo_href_3", comm.getPicServerUrl(map.picList, '1001'), null);
				comm.initPicPreview("#accInsurance_demo_href_4", comm.getPicServerUrl(map.picList, '1002'), null);
				comm.initPicPreview("#accInsurance_demo_href_5", comm.getPicServerUrl(map.picList, '1017'), null);
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
			
			//加载数据
			dataModule.queryConsumerInfo(null,function(response){
				
				//互生卡号、用户id
				cerNo=response.data.cerNo;		
				//realName=response.data.realName;
				realName=comm.getCookie('custName');
				realNameStatus=response.data.realNameStatus;
				//3  营业执照
				if ('3' == response.data.certype) {
					$('#accInsurance').removeClass('none');
					$("#No_Note_a").removeClass('none');
					$('#li_allFailureDateList').addClass('none');	
					return false;
				}
				
				dataModule.findWelfareQualify({welfareType:0},function (response) {
					if(response.data!=null&&response.data.welfareQualify!=null){
						self.welfareQualify = response.data.welfareQualify;
					}
					//未获得积分福利
					if(self.welfareQualify==null){
						$('#accInsurance').removeClass('none');
						$("#No_Note_a").removeClass('none');
						$('#li_allFailureDateList').addClass('none');	
						return false;
					}
					//如果是免费医疗资格
					if(self.welfareQualify.welfareType==1){
						$("#accInsurance_noRigthe_div").removeClass('none');
						return false;
					}
					self.wsApply();
				});
				
			});
			
			//立即申请单击事件
			$('#applyBtn_submit').click(function(){
				$("#accInsurance").addClass('none');
				$("#accInsurance_apply").removeClass('none');
			});
			
			//输入医保卡号 医保中心的受理回执复印件 、消费者本人的社会保障卡复印件 必输 
			$('#healthCardNo').change(function(){
				$('#healthCardNo').val(self.checkReceiveVal($('#healthCardNo').val()));
				if($('#healthCardNo').val().trim()==""){
					$('.hscard_tip').hide();
				}else{
					$('.hscard_tip').show();
				}
				 
			});
			
			//确认提交单击事件
			$('#medicalApply_submit').click(function(){
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
				
					//申请提交校验
					var valid = accidentInsurance.validateData();
					if (!valid.form()) {
						return;
					}
					
				
				comm.i_confirm('确认提交申请吗？', function () {
					var ids = ['hscPositive', 'hscReverse','cerPositive', 'cerReverse', 'medicalProve', 'otherProve1', 	'otherProve2',		'otherProve3',		'otherProve4',		'otherProve5', 'medicalAccept', 'costCount','ssc', 'imr', 'ofr', 'ddc', 'cdl'];
					//图片上传
					comm.uploadFile(null, ids, function (response) {
						if(response!=null){
							accidentInsurance.setInputImgPath(response);
							accidentInsurance.initTmpPicPreview();
							var params = $("#applyMedicalForm").serializeJson();
							params.custId=$.cookie('custId');						// 客户ID
							params.hsResNo=$.cookie('resNo');						// 互生号
							params.proposerName=realName;				// 申请人姓名
							params.proposerPhone=mobile;			// 申请人电话
							params.proposerPapersNo=cerNo;	// 申请人证件号码
							params.explain="意外伤害保障申请";// 说明
							
							dataModule.applyAccidentSecurity(params,function (response) {
								$('#ul_myhs_right_tab li a[data-id="4"]').trigger('click');
								comm.yes_alert(comm.lang("pointWelfare").accidentInsurance.applySuccess);
							});
						}
						
					}, function(res){
						
					}, null);
					
				});	
			});
			
			//保障条款隐藏显示
			$(".myhs_jffl_bztl_tit").click(function () {
				$(".myhs_jffl_bztl_txt").slideToggle(800);
			});
			
			
			//互生意外伤害医疗保障证明材料说明
			$("#acc_Thick_btn2").bind('click', function () {
				$("button").attr("style","");
				var title = "申请意外伤害医疗保障金所需证明和资料";
				var content = "<div style='width:340px; line-height:24px;'><p>1. 注册并经实名认证过的互生积分卡；</p>"
					 + "<p>2. 申请人的法定身份证明；</p>"
					 + "<p>3. 要求申请人所能提供的与确认保障事故的性质、原因、伤害程度等相关的其他证明和资料。</p>"
					 + "<p>4. 本人的医保卡号；</p>"
					 + "<p>5. 没有医保卡号的被保障人需要提交以下资料:</p>"
					 + "<dl class='pl20'><dd>(1) 二级以上（含二级）医院或本平台认可的其他医疗机构出具的医疗费用原始结算凭证、诊断证明及病历等相关资料；</dd>"
					 + "<dd>(2) 对于已经从当地社会基本医疗保险、公费医疗或其他途径获得补偿或给付的，需提供相应机构或单位出具的医疗费用结算证明；</dd>"
					 + "<dd>(3) 若由代理人代为申请保障金，则还应提供授权委托书、代理人法定身份证明等文件。</dd></dl>"
					 + "<p style='text-align:right;line-height:30px;' class='cl-red'>[互生平台系统]</p></div>";
				comm.Thickdiv(title, content);
			});
			
			this.initTmpPicPreview();
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
		wsApply : function(){
			var self = this;
			//意外伤害
			$('#accInsurance').removeClass('none');
			$("#Y_Note_a").removeClass('none');	
			$("#applyBtn_submitDiv").removeClass('none');
			$("#effectDate").text(self.welfareQualify.effectDate);	//生效日期
			$("#failureDate").text(self.welfareQualify.failureDate);	//失效日期
			var allFailureDateList= self.welfareQualify.allFailureDateList;
			if(allFailureDateList==null||allFailureDateList==''){
				$("#li_allFailureDateList").hide();
			}else{
				$("#allFailureDateList").text("失效日期：").next().css("word-break","break-all").text(allFailureDateList);//积分福利失效日期列表
			}
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
			var btnIds = ['#hscPositive', '#hscReverse','#cerPositive', '#cerReverse', '#medicalProve','#otherProve1', 	'#otherProve2',		'#otherProve3',		'#otherProve4',		'#otherProve5', '#medicalAccept', '#costCount','#ssc', '#imr', '#ofr', '#ddc', '#cdl'];
			var imgIds = ['#hscPositiveImg', '#hscReverseImg','#cerPositiveImg', '#cerReverseImg', '#medicalProveImg','#otherProveImg1',	'#otherProveImg2',	'#otherProveImg3',	'#otherProveImg4',	'#otherProveImg5', '#medicalAcceptImg', '#costCountImg','#sscImg', '#imrImg', '#ofrImg', '#ddcImg', '#cdlImg'];
			$("body").on("change", "", function(){
				for(var k in imgIds){
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			comm.initUploadBtn(btnIds, imgIds, 81, 52);
		},
		validateData : function(){
			
			jQuery.validator.addMethod('healthNo', function(value, element) {
				if(comm.isEmpty($.trim($('#healthCardNo').val()))){
					return true;
				}
				return comm.isNotEmpty(value);
			}, '');
			
			var validate = $("#applyMedicalForm").validate({
				rules : {
					/*互生卡正面图片路径*/
					hscPositive : {
						required : $("#hscPositiveImg").is(":visible")
					},
					/*互生卡背面图片路径 径*/
					hscReverse : {
						required : $("#hscReverseImg").is(":visible")
					},
					/*身份证正面图片路径*/
					cerPositive : {
						required : $("#cerPositiveImg").is(":visible")
					},
					/*身份证背面图片路径*/
					cerReverse : {
						required : $("#cerReverseImg").is(":visible")
					},
					/*医疗证明图片路径*/
					medicalProve : {
						required : $("#hscPositiveImg").is(":visible")
					},
					/*住院病历复印件 图片路径*/
					imr : {
						required : $("#imrImg").is(":visible")
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
					/*原始收费收据复印件 图片路径*/
					ofr : {
						required : $("#ofrImg").is(":visible")
					},
					/*诊断证明书复印件 图片路径*/
					ddc : {
						required : $("#ddcImg").is(":visible")
					},
					/*费用明细清单复印件图片路径*/
					cdl : {
						required : $("#cdlImg").is(":visible")
					}
					
				},
				messages : {
					/*互生卡正面图片路径*/
					hscPositive : {
						required : comm.lang("pointWelfare").accidentInsurance.hscPositive
					},
					/*互生卡背面图片路径 径*/
					hscReverse : {
						required : comm.lang("pointWelfare").accidentInsurance.hscReverse
					},
					/*身份证正面图片路径*/
					cerPositive : {
						required : comm.lang("pointWelfare").accidentInsurance.cerPositive
					},
					/*身份证背面图片路径*/
					cerReverse : {
						required : comm.lang("pointWelfare").accidentInsurance.cerReverse
					},
					/*医疗证明图片路径*/
					medicalProve : {
						required : comm.lang("pointWelfare").accidentInsurance.medicalProve
					},
					/*住院病历复印件 图片路径*/
					imr : {
						required : comm.lang("pointWelfare").accidentInsurance.imr
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
					/*原始收费收据复印件 图片路径*/
					ofr : {
						required : comm.lang("pointWelfare").accidentInsurance.ofr	
					},
					/*诊断证明书复印件 图片路径*/
					ddc : {
						required : comm.lang("pointWelfare").accidentInsurance.ddc		
					},
					/*费用明细清单复印件图片路径*/
					cdl : {
						required : comm.lang("pointWelfare").accidentInsurance.cdl	
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
								my : "left+60 top+15",
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
			if($("#medicalProve").val() != ""){
				validate.settings.rules.medicalProve = {required : false};
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
	
			return validate;
		}
	};
	return accidentInsurance
});
