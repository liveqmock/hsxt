define(['text!infoManageTpl/zyxxbgsp/ywblxx.html',
		'text!infoManageTpl/zyxxbgsp/ywblxx_xg.html',
		'text!infoManageTpl/zyxxbgsp/ywblxx_xg_dialog.html',
		'infoManageDat/infoManage'], 
		function(ywblxxTpl, ywblxx_xgTpl, ywblxx_xg_dialogTpl,infoManage){
	var ywblxx_page = {
			
		showPage : function(obj){
			
			/************只为获得rowspan的值******************/
			
			if(obj.num == 'xfz' || obj.num == 'xfzspcx')
			{
				var rowspan = 3;
				
				if(obj.creTypeNew == '1')//判断身份证项
				{	
					//姓名
					if(obj.nameOld != obj.nameNew && comm.isNotEmpty(obj.nameNew)){rowspan++;}
					//性别
					if(obj.sexOld != obj.sexNew && comm.isNotEmpty(obj.sexNew+"")){rowspan++;}
					//国籍
					if(obj.nationalityOld != obj.nationalityNew && comm.isNotEmpty(obj.nationalityNew)){rowspan++;}
					//证件号码
					if(obj.creNoOld != obj.creNoNew && comm.isNotEmpty(obj.creNoNew)){rowspan++;}
					//证件有效期
					if(obj.creExpireDateOld != obj.creExpireDateNew && comm.isNotEmpty(obj.creExpireDateNew)){rowspan++;}
					//发证机关
					if(obj.creIssueOrgOld != obj.creIssueOrgNew && comm.isNotEmpty(obj.creIssueOrgNew)){rowspan++;}
					//户籍地址
					if(obj.registorAddressOld != obj.registorAddressNew && comm.isNotEmpty(obj.registorAddressNew)){rowspan++;}
					
				}
				else if(obj.creTypeNew == '2') //判断护照
				{ 
					//姓名
					if(obj.nameOld != obj.nameNew && comm.isNotEmpty(obj.nameNew)){rowspan++;}
					//性别
					if(obj.sexOld != obj.sexNew && comm.isNotEmpty(obj.sexNew+"")){rowspan++;}
					//国籍
					if(obj.nationalityOld != obj.nationalityNew && comm.isNotEmpty(obj.nationalityNew)){rowspan++;}
					//证件号码
					if(obj.creNoOld != obj.creNoNew && comm.isNotEmpty(obj.creNoNew)){rowspan++;}
					//证件有效期
					if(obj.creExpireDateOld != obj.creExpireDateNew && comm.isNotEmpty(obj.creExpireDateNew)){rowspan++;}
					//出生地点
					if(obj.registorAddressOld != obj.registorAddressNew && comm.isNotEmpty(obj.registorAddressNew)){rowspan++;}
					//签发地点
					if(obj.issuePlaceOld != obj.issuePlaceNew && comm.isNotEmpty(obj.issuePlaceNew)){rowspan++;}
					//签发机关
					if(obj.creIssueOrgOld != obj.creIssueOrgNew && comm.isNotEmpty(obj.creIssueOrgNew)){rowspan++;}
				}
				else if(obj.creTypeNew == '3')//判断营业执照
				{
					//企业名称
					if(obj.entNameOld != obj.entNameNew && comm.isNotEmpty(obj.entNameNew)){rowspan++;}
					//营业执照号码
					if(obj.creNoOld != obj.creNoNew && comm.isNotEmpty(obj.creNoNew)){rowspan++;}
					//注册地址
					if(obj.entRegAddrOld != obj.entRegAddrNew && comm.isNotEmpty(obj.entRegAddrNew)){rowspan++;}
					//企业类型
					if(obj.entTypeOld != obj.entTypeNew && comm.isNotEmpty(obj.entTypeNew)){rowspan++;}
					//成立日期
					if(obj.entBuildDateOld != obj.entBuildDateNew && comm.isNotEmpty(obj.entBuildDateNew)){rowspan++;}
				}
				
				obj.rowspan = rowspan;
			}
			else
			{
				rowspan = 2;
				//企业名称
				if(obj.custNameOld != obj.custNameNew && comm.isNotEmpty(obj.custNameNew)){rowspan++;}
				//企业地址
				if(obj.custAddressOld != obj.custAddressNew && comm.isNotEmpty(obj.custAddressNew)){rowspan++;}
				//法人代表
				if(obj.legalRepOld != obj.legalRepNew && comm.isNotEmpty(obj.legalRepNew)){rowspan++;}
				//营业执照号
				if(obj.bizLicenseNoOld != obj.bizLicenseNoNew && comm.isNotEmpty(obj.bizLicenseNoNew)){rowspan++;}
				//联系人姓名
				if(obj.linkmanOld != obj.linkmanNew && comm.isNotEmpty(obj.linkmanNew)){rowspan++;}
				//联系人手机
				if(obj.linkmanMobileOld != obj.linkmanMobileNew && comm.isNotEmpty(obj.linkmanMobileNew)){rowspan++;}
				
				obj.rowspan = rowspan;
			}
			
			/************只为获得rowspan的值 end******************/
			
			$('#infobox').html(_.template(ywblxxTpl,obj));
			
			//点击【取消】【返回】按钮返回列表页面
			$('#xfz_ywblxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#xfzzyxxbgsp'),'');
			});
			$('#xfzspcx_ywblxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#xfzzyxxbgspcx'),'');
			});
			$('#tgqy_ywblxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#tgqyzyxxbgsp'),'');
			});
			$('#cyqy_ywblxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#cyqyzyxxbgsp'),'');
			});
			$('#fwgs_ywblxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#fwgszyxxbgsp'),'');
			});
			$('#qyspcx_ywblxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#qyzyxxbgspcx'),'');
			});
			
			//图片
			if(obj.num == 'xfz' || obj.num == 'xfzspcx'){
				cacheUtil.findCacheContryAll(function(contryList){
					$("#ywblxx_nationalityOld").text(comm.getCountryName(contryList,obj.nationalityOld));
					$("#ywblxx_nationalityNew").text(comm.getCountryName(contryList,obj.nationalityNew));
				});
				var creFacePic = comm.removeNull(obj.creFacePicNew) != ''?obj.creFacePicNew:obj.creFacePicOld;
				var creBackPic = comm.removeNull(obj.creBackPicNew) != ''?obj.creBackPicNew:obj.creBackPicOld;
				var creHoldPic = comm.removeNull(obj.creHoldPicNew) != ''?obj.creHoldPicNew:obj.creHoldPicOld;
				var registorAddressPic = comm.removeNull(obj.residenceAddrPic);
				comm.initPicPreview("#creFacePicOld", creFacePic);
				comm.initPicPreview("#creBackPicOld", creBackPic);
				comm.initPicPreview("#creHoldPicOld", creHoldPic);
				comm.initPicPreview("#registorAddressPicOld", registorAddressPic);
				
			}else{
				var bizLicenseCrePicNew = comm.removeNull(obj.bizLicenseCrePicNew);
				var linkAuthorizePicNew = comm.removeNull(obj.linkAuthorizePicNew);
				var imptappPic = obj.imptappPic;
				
				//营业执照
				comm.isNotEmpty(bizLicenseCrePicNew)?comm.initPicPreview("#bizLicenseCrePicNew1", bizLicenseCrePicNew):$("#bizLicenseCrePicNew1").hide();
				//联系人授权委托书
				comm.isNotEmpty(linkAuthorizePicNew)?comm.initPicPreview("#linkAuthorizePicNew1", linkAuthorizePicNew):$("#linkAuthorizePicNew1").hide();
				//重要信息变更业务办理申请书
				comm.isNotEmpty(imptappPic)?comm.initPicPreview("#imptappPic1", imptappPic):$("#imptappPic1").hide();
				
				//隐藏没有上传的附件
				if(comm.isEmpty(bizLicenseCrePicNew)){
					$("#bizLicenseCrePicNew1").hide();
					$("#bizLicenseCrePicNew1").next().remove();
				}
				if(comm.isEmpty(linkAuthorizePicNew)){
					$("#linkAuthorizePicNew1").hide();
					$("#linkAuthorizePicNew1").next().remove();
				}
				if(comm.isEmpty(imptappPic)){
					$("#imptappPic1").hide();
				}
				
			}
			
			$('#ywblxx_modify').click(function(){
				
				$('#infobox').html(_.template(ywblxx_xgTpl,obj));
				
				$('#confirm_dialogBox > div').html(_.template(ywblxx_xg_dialogTpl,obj));
				
				//初始化图片
				if(obj.num == "xfz"){
					
					//加载样例图片
					if(obj.creTypeNew == '1'){
						cacheUtil.findDocList(function(map){
							comm.initPicPreview("#person_demo_1", comm.getPicServerUrl(map.picList, '1001'), null);
							comm.initPicPreview("#person_demo_2", comm.getPicServerUrl(map.picList, '1002'), null);
							comm.initPicPreview("#person_demo_3", comm.getPicServerUrl(map.picList, '1005'), null);
							comm.initPicPreview("#person_demo_4", comm.getPicServerUrl(map.picList, '1030'), null);
						});
					}else if(obj.creTypeNew == '2'){
						cacheUtil.findDocList(function(map){
							comm.initPicPreview("#person_demo_1", comm.getPicServerUrl(map.picList, '1027'), null);
							comm.initPicPreview("#person_demo_3", comm.getPicServerUrl(map.picList, '1022'), null);
						});
					}else if(obj.creTypeNew == '3'){
						cacheUtil.findDocList(function(map){
							comm.initPicPreview("#person_demo_1", comm.getPicServerUrl(map.picList, '1010'), null);
							comm.initPicPreview("#person_demo_3", comm.getPicServerUrl(map.picList, '1023'), null);
						});
					}
					
					var creFacePic = comm.removeNull(obj.creFacePicNew) != ''?comm.getFsServerUrl(obj.creFacePicNew):comm.getFsServerUrl(obj.creFacePicOld);
					var creBackPic = comm.removeNull(obj.creBackPicNew) != ''?comm.getFsServerUrl(obj.creBackPicNew):comm.getFsServerUrl(obj.creBackPicOld);
					var creHoldPic = comm.removeNull(obj.creHoldPicNew) != ''?comm.getFsServerUrl(obj.creHoldPicNew):comm.getFsServerUrl(obj.creHoldPicOld);
					var residenceAddrPic = comm.getFsServerUrl(obj.residenceAddrPic);
					
					$("#thum-1").html("<img width='107' id='creFacePicNew_img' height='64' src='"+creFacePic+"'/>");
					$("#thum-2").html("<img width='107' id='creBackPicNew_img' height='64' src='"+creBackPic+"'/>");
					$("#thum-3").html("<img width='107' id='creHoldPicNew_img' height='64' src='"+creHoldPic+"'/>");
					$("#thum-4").html("<img width='107' id='residenceAddrPic_img' height='64' src='"+residenceAddrPic+"'/>");
					//绑定图片预览
					
					var btnIds = ['#creFacePicNew', '#creBackPicNew', '#creHoldPicNew','#residenceAddrPic'];
					var imgIds = ['#thum-1', '#thum-2', '#thum-3', '#thum-4'];

					comm.initUploadBtn(btnIds, imgIds);
					
					$("#creNoNew").change(function(){
						if($("#certificates_type").attr("optionvalue") == '1' || (comm.isEmpty($("#certificates_type").attr("optionvalue")) && obj.creTypeOld == '1')){
							//身份证号码将x转成X
							var creNoNew = $("#creNoNew").val();
							creNoNew = creNoNew.replace(/x/g,'X');
							$('#creNoNew').val(creNoNew);
						}
					});
					
					/**证件类型下拉列表**/
					comm.initSelect("#certificates_type",comm.lang("infoManage").realNameCreType,295);
					/**性别下拉列表 **/
					comm.initSelect("#sexNew",comm.lang("infoManage").personSex,295);
					//国籍
					cacheUtil.findCacheContryAll(function(contryList){
						$("#ywblxx_xg_nationalityOld").text(comm.getCountryName(contryList,obj.nationalityOld));
						comm.initOption('#nationalityNew', contryList , 'countryName','countryNo', true,obj.nationalityNew);
					});
					
					/*日期控件  企业成立日期*/
					$("#entBuildDateNew").datepicker({
						dateFormat : 'yy-mm-dd',
						maxDate: '+0D'
					});
					/*日期控件  证件有效期*/
					$("#creExpireDateNew").datepicker({
						dateFormat : 'yy-mm-dd',
						minDate: '+0D'
					});
					
					$("#long-term").click(function(){
						if('checked' == $("#long-term").attr('checked')){
							$('#creExpireDateNew').val(comm.lang("infoManage").longTerm);
						}
					});
					$("#creExpireDateNew").change(function(){
						$("#long-term").removeAttr('checked');
					});
					
					if(comm.lang("infoManage").longTerm == $("#creExpireDateNew").val()){
						$("#long-term").attr("checked","checked");
					}
					
				}else{
					
					//加载样例图片
					cacheUtil.findDocList(function(map){
						comm.initPicPreview("#ent_demo_3", comm.getPicServerUrl(map.picList, '1010'), null);
						comm.initDownload("#ent_demo_6", map.comList, '1007');
						comm.initDownload("#ent_demo_7", map.busList, '1001');
					});
					var bizLicenseCrePicNew = comm.getFsServerUrl(obj.bizLicenseCrePicNew);
					var linkAuthorizePicNew = comm.getFsServerUrl(obj.linkAuthorizePicNew);
					var imptappPic = comm.getFsServerUrl(obj.imptappPic);
					
					
					if(comm.isNotEmpty(obj.bizLicenseCrePicNew)){
						$("#thum-3").html("<img width='107' id='bizLicenseCrePicNew_img' height='64' src='"+bizLicenseCrePicNew+"'/>");
					}
					if(comm.isNotEmpty(obj.linkAuthorizePicNew)){
						$("#thum-6").html("<img width='107' id='linkAuthorizePicNew_img' height='64' src='"+linkAuthorizePicNew+"'/>");
					}
					$("#thum-7").html("<img width='107' id='imptappPic_img' height='64' src='"+imptappPic+"'/>");
					
					var btnIds = ['#bizLicenseCrePicNew', '#linkAuthorizePicNew', '#imptappPic'];
					var imgIds = ['#thum-3', '#thum-6', '#thum-7'];
					
					comm.initUploadBtn(btnIds, imgIds);
					
					
					
				}
				
				
				$('#ywblxx_xg_cancel').triggerWith('#ywblxx');
				
				$('#ywblxx_xg_save').click(function(){
					
					if(obj.num == 'xfz')
					{
						if(!ywblxx_page.validateData4ywblxx_xg_perInfo().form()){
							return;
						}
						//初始化修改个人（消费者）重要信息页面
						ywblxx_page.initChangeData4Person(obj);
					}
					else
					{
						if(ywblxx_page.rualVailData()){
							return;
						}
						//验证重要信息
						if(!ywblxx_page.validateData4ywblxx_xg_entInfo(obj).form()){
							return;
						}
						//初始化修改企业实名认证信息页面
						ywblxx_page.initChangeData4Ent(obj);
					}
				  
				});
	
			});
		},
		initTrsImage : function(trImageId,trImageSrc){
			if(trImageId && trImageId.length > 0 && trImageSrc && trImageSrc.length > 0){
				for(var i = 0; i < trImageId.length; i++){
					var dom = document.getElementById(trImageId[i]);
					$("#"+trImageId[i]).attr("src","/resources/img/space.png");
					dom.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod = scale)";
					dom.filters("DXImageTransform.Microsoft.AlphaImageLoader").src = trImageSrc[i];
				}
			}
		},
		/**
		 * 业务规则验证
		 * @param credentialType  证件类型
		 * @param submitFlag 是否为提交判断 true：提交判断  ，false  输入控件change判断
		 */
		rualVailData :function (){
			var custNameNew = $.trim($("#custNameNew").val());//变更后的企业名称
			var custAddressNew = $.trim($("#custAddressNew").val());//变更后的企业地址
			var linkmanNew = $.trim($("#linkmanNew").val());//变更后的联系人姓名
			var linkmanMobileNew = $.trim($("#linkmanMobileNew").val());//变更后的联系人手机
			var legalRepNew = $.trim($("#legalRepNew").val());//变更后的法人代表
			var bizLicenseNoNew = $.trim($("#bizLicenseNoNew").val());//变更后的营业执照号
			
				
			//判断有无信息修改
			if(comm.isEmpty(custNameNew)&&comm.isEmpty(custAddressNew)&&comm.isEmpty(linkmanNew)
					&&comm.isEmpty(linkmanMobileNew)&&comm.isEmpty(legalRepNew)&&comm.isEmpty(bizLicenseNoNew)){
				comm.error_alert(comm.lang("infoManage").noUpdate);
				return true;
			}
			//不单独提供申请营业执照号、组织机构代码证号、纳税人识别号的重要信息的变更业务，修改企业名称、企业地址、法人代表姓名 ，同时可选择修改这三个证件号码；
			if(comm.isEmpty(custNameNew)&&comm.isEmpty(custAddressNew)&&comm.isEmpty(legalRepNew)){
				if(comm.isNotEmpty(bizLicenseNoNew)){
					comm.warn_alert(comm.lang("infoManage").notAloneChangeLicenseNo,"550");
					return true;
				}
			}
			return false;
		},
		
		
		/**
		 * 表单校验
		 */
		validateData4ywblxx_xg_perInfo : function(){
			var validate = $("#ywblxx_xg_perInfo_form").validate({
				rules : {
					nameNew : {
						custname : true
					}
				},
				messages : {
					nameNew : {
						custname : comm.lang("infoManage")[34571]
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
		validateData4ywblxx_xg_entInfo : function(obj){
			 var validate = $("#ywblxx_xg_entInfo_form").validate({
				rules : {
					custNameNew : {
						rangelength : [2, 64],
						equalContent : "#custNameOld"
					},
					custAddressNew : {
						rangelength : [2, 128],
						equalContent : "#custAddressOld"
					},
					legalRepNew : {
						rangelength : [2, 20],
						equalContent : "#legalRepOld"
					},
					bizLicenseNoNew : {
						rangelength : [7, 20],
						businessLicence:true,
						equalContent : "#bizLicenseNoOld",
					},
					linkmanNew : {
						rangelength : [2, 20],
						equalContent : "#linkmanOld"
					},
					linkmanMobileNew : {
						mobileNo : true,
						equalContent : "#linkmanMobileOld"
					},
					bizLicenseCrePicNew:{
						required:function(){
							return (comm.isNotEmpty($("#custNameNew").val())||comm.isNotEmpty($("#custAddressNew").val())||comm.isNotEmpty($("#legalRepNew").val())||comm.isNotEmpty($("#bizLicenseNoNew").val()))&&comm.isEmpty(obj.bizLicenseCrePicNew);
						}
					},
					linkAuthorizePicNew:{
						required:function(){
							return (comm.isNotEmpty($("#linkmanNew").val())||comm.isNotEmpty($("#linkmanMobileNew").val()))&&comm.isEmpty(obj.linkAuthorizePicNew);
						}
					}
				},
				messages : {
					custNameNew : {
						rangelength : comm.lang("infoManage")[31077],
						equalContent : comm.lang("infoManage").changeValSame
					},
					custAddressNew : {
						rangelength : comm.lang("infoManage")[31079],
						equalContent : comm.lang("infoManage").changeValSame
					},
					legalRepNew : {
						rangelength : comm.lang("infoManage")[31080],
						equalContent : comm.lang("infoManage").changeValSame
					},
					bizLicenseNoNew : {
						rangelength :comm.lang("infoManage")[31097],
						businessLicence:comm.lang("infoManage")[31081],
						equalContent : comm.lang("infoManage").changeValSame
					},
					linkmanNew : {
						rangelength : comm.lang("infoManage")[31082],
						equalContent : comm.lang("infoManage").changeValSame
					},
					linkmanMobileNew : {
						mobileNo : comm.lang("infoManage")[31083],
						equalContent : comm.lang("infoManage").changeValSame
					},
					bizLicenseCrePicNew:{
						required:comm.lang("infoManage")[31074]
					},
					linkAuthorizePicNew:{
						required:comm.lang("infoManage")[31058]
					}
				},
				errorPlacement : function (error, element) {
					if ($(element).is(":text")) {
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left top",
								at : "left top"
							}
						}).tooltip("open");
						$(".ui-tooltip").css("max-width", "230px");
					}else{
						$(element.parent()).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left top+30",
								at : "left top"
							}
						}).tooltip("open");
					}
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
			
			return validate;
		},
		
		
		//初始化修改个人（消费者）重要信息变更
		initChangeData4Person : function(obj){
			/**trImageId ,trImageSrc 是针对IE7，IE8的变量，主要用户弹出确认页面中的【变更后内容】中图片的滤镜图片的生成
			 * 它不同与Google  火狐  在上传时有一个临时图片路径生成，见 <img> 的 data-src属性，ie的预览是滤镜效果无路径
			 * 所以需要在弹出的确认对话框中在生成一次滤镜**/
			var trImageId = [];
			var trImageSrc = [];
			
			var changNum = 0;
			var changJson = {};		//存入修改的对象及内容
			var changFiled = [];	//存入有修改的属性名,在提交时
			var trs = "";
			var fileIds = []; 		//存入文件有修改的对象元素id
			var delFileIds = [];	//存入文件有修改的原文件id ,在上传新文件时需将原文件删除,节省文件系统空间
			var changItem = [];
			//检查输入项有无改变的对象
			$(".isChange").each(function(a,b){
				
				//描述
				var desc = $(b).parent().prev().prev().text();
				//修改后属性名
				var nname = $(b).attr("name");
				//修改后的值
				var nvalue = $(b).attr("value");
				//修改前属性名
				var oname = $(b).prev().attr("name");
				//修改前的值
				var ovalue = $(b).prev().attr("value");
				
				if(comm.removeNull(nvalue) != "" && comm.removeNull(nvalue) != comm.removeNull(ovalue))
				{
					
					changNum++;
					
					changJson[nname] = {"old": comm.removeNull(ovalue) , "new": comm.removeNull(nvalue)};
					
					changItem[changItem.length] = desc;
					
					trs = trs + "<tr><td class='view_item'>" + desc + "</td><td class='view_text' style='word-wrap:break-word;word-break:break-all;'>" + ovalue + "</td><td class='view_text' style='word-wrap:break-word;word-break:break-all;'>" + nvalue + "</td></tr>";
				}
			});
			//对国籍做判断
			var nationalityNew = $("#nationalityNew").val();
			var nationalityOld = $("#nationalityOld").val();
			if(comm.removeNull(nationalityNew) != "" && comm.removeNull(nationalityNew) != comm.removeNull(nationalityOld)){
				changNum++;
				
				changJson["nationalityNew"] = {"old": comm.removeNull(nationalityOld) , "new": comm.removeNull(nationalityNew)};
				
				changItem[changItem.length] = $("#nationalityNew").parent().parent().parent().find("td:eq(0)").text();
				
				trs = trs + "<tr><td class='view_item'>" + $("#nationalityNew").parent().parent().parent().find("td:eq(0)").text() + "</td><td class='view_text'>" + $("#nationalityNew").parent().parent().parent().find("td:eq(1)").text() + "</td><td class='view_text'>" + $("#nationalityNew").find("option:selected").text() + "</td></tr>";
			}
			//对性别做判断是否有改变
			var sexNew = $("#sexNew").attr("optionvalue");
			var sexOld = $("#sexOld").val();
			var sexdesc = $("#sexNew").parent().parent().prev().prev().text();
			if(comm.removeNull(sexNew) != "" && comm.removeNull(sexOld) != comm.removeNull(sexNew)){
				changNum++;
				changJson["sexNew"] = {"old":comm.removeNull(sexOld),"new":comm.removeNull(sexNew)};
				changItem[changItem.length] = sexdesc;
				trs = trs + "<tr><td class='view_item'>"+sexdesc+"</td><td class='view_text'>" + $("#sexNew").parent().parent().parent().find("td:eq(1)").text() + "</td><td class='view_text'>" + $("#sexNew").val() + "</td></tr>";
			}
			
			//检查附件（图片文件）有无改变的对象
			$(".certificateSample_img").each(function(a,b){
				//描述
				var desc = $(b).parent().prev().text();
				//旧值属性名
				var oname = $(b).parent().attr("id");
				//新值属性名称
				var pname = $(b).attr("tabindex");
				//新url
				var nurl = $(b).children().first().attr('src');//.attr("data-imgsrc");
				//原url
				var ourl = comm.getFsServerUrl(obj[oname]);
				if(comm.isNotEmpty($("#"+pname).val()) && $(b).is(":visible")){ //comm.isNotEmpty(nurl) && nurl != ourl && nurl.indexOf("userId") < 0
					//判断是否ie8浏览器 
					if($.browser.msie && ($.browser.version == '8.0' || $.browser.version == '7.0')){
						$("#"+pname).select();
						nurl = document.selection.createRange().text;
						trImageId[trImageId.length] = "dialog" + pname;
						trImageSrc[trImageSrc.length] = nurl;
					}
					
					trs = trs + "<tr><td class='view_item'>" + desc + "</td><td class='view_text'>" +
					"<span class='fl ml5 mt5 sample_img' style='margin-bottom:2px'><img  src='"+ourl+"' width='81' height='52' /></span></td>" +
					"<td class='view_text'>" + 
					"<span class='fl ml5 mt5 sample_img' style='margin-bottom:2px'><img id='dialog"+pname+"'  src='"+nurl+"' width='81' height='52' /></span></td></tr>";
					
					changNum++;
					fileIds[fileIds.length] = pname;
					changItem[changItem.length] = desc;
					delFileIds[delFileIds.length] = comm.removeNull(obj[pname]) != ''?obj[pname]:obj[oname];
				}
				
			});
			
			if(trs == ""){
				comm.warn_alert(comm.lang("infoManage").noUpdate);
				return ;
			}
			
			$('#confirm_dialogBox > div').html(_.template(ywblxx_xg_dialogTpl,obj));
			
			var creFacePic = comm.removeNull(obj.creFacePicNew) != ''?obj.creFacePicNew:obj.creFacePicOld;
			var creBackPic = comm.removeNull(obj.creBackPicNew) != ''?obj.creBackPicNew:obj.creBackPicOld;
			var creHoldPic = comm.removeNull(obj.creHoldPicNew) != ''?obj.creHoldPicNew:obj.creHoldPicOld;
			var registorAddressPic = comm.removeNull(obj.residenceAddrPic);
			
			//证件正面
			comm.initPicPreview("#dialog_creFacePicOld", creFacePic);
			//证件反面
			comm.initPicPreview("#dialog_creBackPicOld", creBackPic);
			//手持证件
			comm.initPicPreview("#dialog_creHoldPicOld", creHoldPic);
			//户籍变更证明
			comm.initPicPreview("#dialog_registorAddressPicOld", registorAddressPic);
			
			//将修改项加到table中
			$('#personTab tr:eq(1)').before(trs);
			
			var height = 560 + 40 * changNum;
			/*弹出框*/
			$( "#confirm_dialogBox" ).dialog({
				title:comm.lang("infoManage").zyxxbgconfirUpdata,//"重要信息变更修改确认",
				modal:true,
				width:"800",
				height:height,
				position:"absolute",
				buttons:{ 
					"确定":function(){
						
						ywblxx_page.updatePerson($(this),obj,changJson,fileIds,delFileIds,changItem);
						
					},
					"取消":function(){
						$(this).dialog("destroy");
					}
				}
			  });	
			
			//IE8下生成弹出框中【变更后内容】中的图片滤镜
			ywblxx_page.initTrsImage(trImageId, trImageSrc);
		  //验证方式下拉框
			comm.initSelect("#verificationMode",comm.lang("infoManage").verificationMode,185).change(function(e){
				var val = $(this).attr('optionValue');
				switch(val){
					case '1':
						$('#fhy_userName, #fhy_password').removeClass('none');
						$('#verificationMode_prompt').addClass('none');
						break;	
						
					case '2':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').removeClass('none');
						break;
						
					case '3':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').removeClass('none');
						break;
					default:
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').addClass('none');
				}
			});
//			$("#verificationMode").selectListValue("1");
			window.setTimeout(function(){
				$("#doubleUserName").attr("value","");
				$("#doublePassword").val("");	
			},100); 
		},
		
		
		
		// 提交 修改个人（消费者）重要信息变更
		updatePerson : function(dialog,obj,changJson,fileIds,delFileIds,changItem){
			
			//验证双签输入
			if(!ywblxx_page.validateData().form()){
				
				return;
			}
			var verificationMode = $("#verificationMode").attr('optionValue');
			if(verificationMode != "1"){
				comm.warn_alert(comm.lang("infoManage").notSupportValidateMode);
				return ;
			}
			//验证双签
			comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function(verifyRes){
					
					//上传有修改的文件
					comm.uploadFile(null, fileIds, function(data){
						
						for(var key = 0;key < fileIds.length; key++){
							changJson[fileIds[key]] = {"old":obj[fileIds[key]],"new":data[fileIds[key]]};
						}
						var param = comm.getRequestParams(param);
						var postData = {
								applyId : obj.applyId,
								changeItem : changItem.join(","),
								
								/************ 身份证、护照 start**************/
								//变更前
								nameOld : obj.nameOld,  					//姓名
								sexOld : obj.sexOld,						//性别
								nationalityOld : obj.nationalityOld, 		//国籍
								creTypeOld : obj.creTypeOld,				//证件类型
								creNoOld : obj.creNoOld,					//证件号码
								creExpireDateOld : obj.creExpireDateOld,	//证件有效期
								//变更后
								nameNew : $("#nameNew").val(),  			//姓名
								sexNew : $("#sexNew").attr("optionvalue"), 	//性别
								nationalityNew : $("#nationalityNew").val(), //国籍
								creTypeNew : obj.creTypeOld,				//证件类型
								creNoNew : $("#creNoNew").val(),  			//证件号码
								creExpireDateNew : $("#creExpireDateNew").val(),//证件有效期
								
								//身份证的户籍地址，护照的出生地址
								registorAddressOld : obj.registorAddressOld,//户籍地址、出生地址
								registorAddressNew : $("#registorAddressNew").val(),
								
								/*********** 身份证独有 *********/
								creIssueOrgOld : obj.creIssueOrgOld,		//发证机关
								creIssueOrgNew : $("creIssueOrgNew").val(),
								
								/******************** 护照独有 ***********/
								issuePlaceOld : obj.issuePlaceOld,			//签发地点
								issuePlaceNew :$("#issuePlaceNew").val(),
								creIssueOrgOld : obj.creIssueOrgOld,		//签发机关
								creIssueOrgNew : $("#creIssueOrgNew").val(),
								
								/************ 身份证、护照 end**************/
								
								/**************** 营业执照 ****************/
								entNameOld : obj.entNameOld,				//企业名称
								entNameNew : $("#entNameNew").val(),
								entRegAddrOld : obj.entRegAddrOld,			//注册地址
								entRegAddrNew : $("#entRegAddrNew").val(),
								entTypeOld : obj.entTypeOld,				//企业类型
								entTypeNew : $("#entTypeNew").val(),
								entBuildDateOld : obj.entBuildDateOld,		//成立日期
								entBuildDateNew : $("#entBuildDateNew").val(),
								/**************** 营业执照 end ************/
								
								applyReason:$("#applyReason").val(),		//变更原因
								
								//操作信息
								optCustId : param.custId,
								optName : param.cookieOperNoName,
								optEntName : param.custEntName,
								optRemark : $("#optRemark").val(),
								dblOptCustId : verifyRes.data,
								changeContent : JSON.stringify(changJson),
								
								//附件信息
								residenceAddrPic : obj.residenceAddrPic,	//户籍变更证明
								creFacePicOld : obj.creFacePicOld,			//证件正面
								creBackPicOld : obj.creBackPicOld,			//证件反面
								creHoldPicOld : obj.creHoldPicOld,			//手持证件照

								creFacePicNew : obj.creFacePicNew,
								creBackPicNew : obj.creBackPicNew,
								creHoldPicNew : obj.creHoldPicNew
						};

						//如果附件有修改  则将postData中相应的附件改为现在的值
						if(fileIds.length > 0){
							for(var key = 0; key < fileIds.length; key++){
								if(fileIds[key] == "creFacePicNew"){
									postData.creFacePicNew = data[fileIds[key]];
								}
								if(fileIds[key] == "creBackPicNew"){
									postData.creBackPicNew = data[fileIds[key]];
								}
								if(fileIds[key] == "creHoldPicNew"){
									postData.creHoldPicNew = data[fileIds[key]];
								}
								if(fileIds[key] == "residenceAddrPic"){
									postData.residenceAddrPic = data[fileIds[key]];
								}
							}
						}
						//提交修改
						infoManage.modifyPerImportantInfo(postData,function(res){
							
							comm.alert({
								content:comm.lang("infoManage").xfzzyxxUpdataSuccess,callOk:function(){
									//返回列表
									comm.goDetaiPage("busibox2","busibox");
									
									//关闭弹出框
									dialog.dialog( "destroy" );
								}	
							});
							
						});
					},function(err){
//						comm.error_alert(comm.lang("infoManage")[err.retCode]);
					},{delFileIds:delFileIds});
					
			});
		},
		
		initChangeData4Ent : function(obj){
			trImageId = [];
			trImageSrc = [];
			var changNum = 0;
			var changJson = {};		//存入修改的对象及内容
			var changFiled = [];	//存入有修改的属性名,在提交时
			var trs = "";
			var fileIds = []; 		//存入文件有修改的对象元素id
			var delFileIds = [];	//存入文件有修改的原文件id ,在上传新文件时需将原文件删除,节省文件系统空间
			//检查输入项有无改变的对象
			$(".isChange").each(function(a,b){
				
				//描述
				var desc = $(b).parent().prev().prev().text();
				//修改后属性名
				var nname = $(b).attr("name");
				//修改后的值
				var nvalue = $(b).attr("value");
				//修改前属性名
				var oname = $(b).prev().attr("name");
				//修改前的值
				var ovalue = $(b).prev().attr("value");
				
				if(comm.removeNull(nvalue) != '' && comm.removeNull(nvalue) != comm.removeNull(ovalue))
				{
					
					changNum++;
					
					changJson[nname] = {"old": comm.removeNull(ovalue) , "new": comm.removeNull(nvalue)};
					
					trs = trs + "<tr><td class='view_item'>" + desc + "</td><td class='view_text' style='word-wrap:break-word;word-break:break-all;'>" + ovalue + "</td><td class='view_text' style='word-wrap:break-word;word-break:break-all;'>" + nvalue + "</td></tr>";
				}
			});
			
			//检查附件（图片文件）有无改变的对象
			$(".certificateSample_img").each(function(a,b){
				
				//描述
				var desc = $(b).parent().prev().text();
				//旧值属性名
				var oname = $(b).parent().attr("tabindex");
				//新值属性名称
				var pname = $(b).attr("tabindex");
				//新url
				var nurl = $(b).children().first().attr('src');//.attr("data-imgsrc");
				//原url
				var ourl = comm.getFsServerUrl(obj[oname]);
				
				
				
				if(comm.isNotEmpty($("#"+pname).val()) && $(b).is(":visible")){ //comm.isNotEmpty(nurl) && nurl != ourl && nurl.indexOf("userId") < 0
					
					//判断是否ie8浏览器  ie8是个变态 要小心
					if($.browser.msie && ($.browser.version == '8.0' || $.browser.version == '7.0')){
						$("#"+pname).select();
						nurl = document.selection.createRange().text;
						trImageId[trImageId.length] = "dialog" + pname;
						trImageSrc[trImageSrc.length] = nurl;
					}
						
					trs = trs + "<tr><td class='view_item'>" + desc + "</td><td class='view_text'>" +
					"<span class='fl ml5 mt5 sample_img' style='margin-bottom:2px'><img  src='"+ourl+"' width='81' height='52' /></span></td>" +
					"<td class='view_text'>" + 
					"<span class='fl ml5 mt5 sample_img' style='margin-bottom:2px'><img id='dialog"+pname+"' src='"+nurl+"' width='81' height='52' /></span></td></tr>";
						
					changNum++;
					
					fileIds[fileIds.length] = pname;
					delFileIds[delFileIds.length] = obj[pname];
				}
				
				
			});
			
			
			$('#confirm_dialogBox > div').html(_.template(ywblxx_xg_dialogTpl,obj));
			
			//营业执照
			comm.initPicPreview("#dialog_bizLicenseCrePicNew", obj.bizLicenseCrePicNew);
			//联系人授权委托书
			comm.initPicPreview("#dialog_linkAuthorizePicNew", obj.linkAuthorizePicNew);
			//重要信息变更业务办理申请书
			comm.initPicPreview("#dialog_imptappPic", obj.imptappPic);
			
			//隐藏没有上传文件的附件框
			if(comm.isEmpty(obj.bizLicenseCrePicNew)){
				$("#dialog_bizLicenseCrePicNew").hide();
				$("#dialog_bizLicenseCrePicNew").next().hide();
			}
			if(comm.isEmpty(obj.linkAuthorizePicNew)){
				$("#dialog_linkAuthorizePicNew").hide();
				$("#dialog_linkAuthorizePicNew").next().hide();
			}
			
			//将修改项加到table中
			$('#entTab tr:eq(1)').before(trs);
			
			var height = 460 + 40 * changNum;
			/*弹出框*/
			$( "#confirm_dialogBox" ).dialog({
				title:comm.lang("infoManage").zyxxbgconfirUpdata,//"重要信息变更修改确认",
				modal:true,
				width:"800",
				height:height,
				top:160,
				position: "absolute",
				buttons:{ 
					"确定":function(){
						
						//执行修改
						ywblxx_page.updateEnt($(this),obj,changJson,fileIds,delFileIds);
						
					},
					"取消":function(){
						$(this).dialog("destroy");
					}
				}
			  });	
			
			ywblxx_page.initTrsImage(trImageId,trImageSrc);
			
		  //验证方式下拉框
			comm.initSelect("#verificationMode",comm.lang("infoManage").verificationMode,185).change(function(e){
				var val = $(this).attr('optionValue');
				switch(val){
					case '1':
						$('#fhy_userName, #fhy_password').removeClass('none');
						$('#verificationMode_prompt').addClass('none');
						break;	
						
					case '2':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').removeClass('none');
						break;
						
					case '3':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').removeClass('none');
						break;
				}
			});
			
			window.setTimeout(function(){
				$("#doubleUserName").attr("value","");
				$("#doublePassword").val("");	
			},100); 
		},
		
		//提交修改企业实名认证
		updateEnt : function(dialog,obj,changJson,fileIds,delFileIds){
			
			//验证双签输入
			if(!ywblxx_page.validateData().form()){
				
				return;
			}
			var verificationMode = $("#verificationMode").attr('optionValue');
			if(verificationMode != "1"){
				comm.warn_alert(comm.lang("infoManage").notSupportValidateMode);
				return ;
			}
			//验证双签
			comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function(verifyRes){
				
				//上传有修改的文件
				comm.uploadFile(null, fileIds, function(data){
					
					for(var key = 0; key < fileIds.length; key++){
						changJson[fileIds[key]] = {"old":obj[fileIds[key]],"new":data[fileIds[key]]};
					}
					
					//获取cookie中的客户号等信息
					var param = comm.getRequestParams(param);
					
					var postData = {
						applyId : obj.applyId,
						custNameOld : obj.custNameOld,
						custNameEnOld : obj.custNameEnOld,
						custAddressOld : obj.custAddressOld,
						linkmanOld : obj.linkmanOld,
						linkmanMobileOld : obj.linkmanMobileOld,
						legalRepOld : obj.legalRepOld,
						legalRepCreTypeOld : obj.legalRepCreTypeOld,
						legalRepCreNoOld : obj.legalRepCreNoOld,
						bizLicenseNoOld : obj.bizLicenseNoOld,
						organizerNoOld : obj.organizerNoOld,
						taxpayerNoOld : obj.taxpayerNoOld,
						legalRepCreFacePicOld : obj.legalRepCreFacePicOld,
						legalRepCreBackPicOld : obj.legalRepCreBackPicOld,
						bizLicenseCrePicOld : obj.bizLicenseCrePicOld,
						organizerCrePicOld : obj.organizerCrePicOld,
						taxpayerCrePicOld : obj.taxpayerCrePicOld,
						linkAuthorizePicOld : obj.linkAuthorizePicOld,
						custNameNew : $("#custNameNew").val(),
						custNameEnNew : $("#custNameEnNew").val(),
						custAddressNew : $("#custAddressNew").val(),
						linkmanNew : $("#linkmanNew").val(),
						linkmanMobileNew : $("#linkmanMobileNew").val(),
						legalRepNew : $("#legalRepNew").val(),
						legalRepCreNoNew : $("#legalRepCreNoNew").val(),
						legalRepCreTypeNew : $("#certificates_type").attr("optionvalue"),
						bizLicenseNoNew : $("#bizLicenseNoNew").val(),
						organizerNoNew : $("#organizerNoNew").val(),
						taxpayerNoNew : $("#taxpayerNoNew").val(),
						
						imptappPic : obj.imptappPic,
						bizLicenseCrePicNew : obj.bizLicenseCrePicNew,
						linkAuthorizePicNew : obj.linkAuthorizePicNew,
						
						optCustId : param.custId,
						optName : param.cookieOperNoName,
						optEntName : param.custEntName,
						optRemark : $("#optRemark").val(),
						dblOptCustId : verifyRes.data,
						changeContent : JSON.stringify(changJson)
					};
					//如果附件有修改  则将postData中相应的附件改为现在的值
					if(fileIds.length > 0){
						for(var key = 0; key < fileIds.length; key++){
							
							if(fileIds[key] == "bizLicenseCrePicNew"){
								postData.bizLicenseCrePicNew = data[fileIds[key]];
							}
							if(fileIds[key] == "linkAuthorizePicNew"){
								postData.linkAuthorizePicNew = data[fileIds[key]];
							}
							if(fileIds[key] == "imptappPic"){
								postData.imptappPic = data[fileIds[key]];
							}
						}
					}
					
					infoManage.modifyEntimportantinfochange(postData,function(res){
						
						comm.alert({
							content:comm.lang("infoManage")[obj.num + "zyxxUpdataSuccess"],callOk:function(){
								//返回列表
								comm.goDetaiPage("busibox2","busibox");
								
								//关闭弹出框
								dialog.dialog( "destroy" );
							}
						});
							
					});
					
				},function(err){
					
				},{delFileIds:delFileIds});
				
			});
		},
		validateData : function(){
			var validate = $("#ywblxx_xg_dialog").validate({
				rules : {
					verificationMode : {
						required : true
					},
					optRemark : {
						rangelength : [2, 300]
					},
					doubleUserName : {
						required : true
					},
					doublePassword : {
						required : true
					},
				},
				messages : {
					verificationMode : {
						required : comm.lang("infoManage")[36050]
					},
					optRemark : {
						rangelength : comm.lang("infoManage")[36006]
					},
					doubleUserName : {
						required : comm.lang("infoManage")[36047]
					},
					doublePassword : {
						required : comm.lang("infoManage")[36048]
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
			
			var type = $("#verificationMode").attr("optionvalue");
			if(type == '1'){
				validate.settings.rules.doubleUserName = {required : true};
				validate.settings.rules.doublePassword = {required : true};
			}else{
				validate.settings.rules.doubleUserName = {required : false};
				validate.settings.rules.doublePassword = {required : false};
			}
			
			return validate;
		}
	};
	return ywblxx_page
});