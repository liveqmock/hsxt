define(['text!infoManageTpl/smrzsp/ywblxx.html',
		'text!infoManageTpl/smrzsp/ywblxx_xg.html',
		'text!infoManageTpl/smrzsp/ywblxx_xg_dialog.html',
		'infoManageDat/infoManage'], 
		function(ywblxxTpl, ywblxx_xgTpl, ywblxx_xg_dialogTpl,infoManage){
	var ywblxxPage = {
		showPage : function(obj){
			
			comm.delCache("commCache", "provCity");
			cacheUtil.syncGetRegionByCode(obj.countryNo,null,null,'',function(res){
				
				obj.countryText = res;
				
				$('#infobox').html(_.template(ywblxxTpl,obj));
				if(obj.num == 'xfz'||obj.num == 'xfzspcx'){
					//设置证件照url
					comm.initPicPreview("#cerpica", obj.cerpica);
					comm.initPicPreview("#cerpicb", obj.cerpicb);
					comm.initPicPreview("#cerpich", obj.cerpich);
				}else{
					//营业执照
					comm.initPicPreview("#licensePic", obj.licensePic);
				}
				
				//点击【取消】【返回】按钮返回列表页面
				$('#xfz_ywblxx_cancel').click(function(){
					comm.goBackListPage("busibox2","busibox",$('#xfzsmrzsp'),'');
				});
				$('#xfzcx_ywblxx_cancel').click(function(){
					comm.goBackListPage("busibox2","busibox",$('#xfzsmrzspcx'),'');
				});
				$('#tgqy_ywblxx_cancel').click(function(){
					comm.goBackListPage("busibox2","busibox",$('#tgqysmrzsp'),'');
				});
				$('#cyqy_ywblxx_cancel').click(function(){
					comm.goBackListPage("busibox2","busibox",$('#cyqysmrzsp'),'');
				});
				$('#fwgs_ywblxx_cancel').click(function(){
					comm.goBackListPage("busibox2","busibox",$('#fwgssmrzsp'),'');
				});
				$('#qyspcx_ywblxx_cancel').click(function(){
					comm.goBackListPage("busibox2","busibox",$('#qysmrzspcx'),'');
				});
				
				
				$('#ywblxx_modify').click(function(){	
					$('#infobox').html(_.template(ywblxx_xgTpl,obj));
					
					/*证件类型下拉列表*/
					if(obj.num == 'xfz'){
						
						/** 证件类型 **/
						comm.initSelect("#certificates_type",comm.lang("infoManage").realNameCreType,295);
						
						/**性别下拉列表 **/
						comm.initSelect("#sex_type",comm.lang("infoManage").sexType,295);
						
						/** 日期控件  营业执照 类型 成立日期 **/
						if(obj.certype == '3'){
							$("#entBuildDate").datepicker({dateFormat : 'yy-mm-dd'});
						}else{
							$("#validDate").datepicker({dateFormat : 'yy-mm-dd'});
						}
						
						//加载消费者样例图片
						if(obj.certype == '1'){  //身份证
							cacheUtil.findDocList(function(map){
								comm.initPicPreview("#person_demo_1", comm.getPicServerUrl(map.picList, '1001'));
								comm.initPicPreview("#person_demo_2", comm.getPicServerUrl(map.picList, '1002'));
								comm.initPicPreview("#person_demo_3", comm.getPicServerUrl(map.picList, '1005'));
							});
						}else if(obj.certype == '2'){	//护照
							cacheUtil.findDocList(function(map){
								comm.initPicPreview("#person_demo_1", comm.getPicServerUrl(map.picList, '1027'));
								comm.initPicPreview("#person_demo_3", comm.getPicServerUrl(map.picList, '1022'));
							});
						}else if(obj.certype == '3'){	//营业执照
							cacheUtil.findDocList(function(map){
								comm.initPicPreview("#person_demo_1", comm.getPicServerUrl(map.picList, '1010'));
								comm.initPicPreview("#person_demo_3", comm.getPicServerUrl(map.picList, '1023'));
							});
						}
						
					}else{
						
						//加载企业样例图片
						cacheUtil.findDocList(function(map){
							comm.initPicPreview("#ent_demo_3", comm.getPicServerUrl(map.picList, '1010'));
						});
					}
					
						
					//初始化图片
					if(obj.num == "xfz"){
						
						$("#thum-1").html("<img width='107' id='cerpica_img' height='64' src='"+comm.getFsServerUrl(obj.cerpica)+"'/>");
						
						//绑定图片预览
						var btnIds ;
						var imgIds ;
						
						//身份证或护照类型 有背面照片、国籍   营业执照类型无背面照片、国籍
						if(obj.certype == '1' || obj.certype == '2'){
							
							$("#thum-2").html("<img width='107' id='cerpicb_img' height='64' src='"+comm.getFsServerUrl(obj.cerpicb)+"'/>");
							btnIds = ['#cerpica', '#cerpicb', '#cerpich'];
							imgIds = ['#thum-1', '#thum-2', '#thum-3'];
							//加载国家信息从缓存中读取
							cacheUtil.findCacheContryAll(function(contryList){
								comm.initOption('#countryName', contryList , 'countryName','countryNo',true,"");
							});
							
						}else{
							btnIds = ['#cerpica', '#cerpich'];
							imgIds = ['#thum-1', '#thum-3'];
						}
						$("#thum-3").html("<img width='107' id='cerpich_img' height='64' src='"+comm.getFsServerUrl(obj.cerpich)+"'/>");
						
						comm.initUploadBtn(btnIds, imgIds);
					}else{
						
						$("#thum-3").html("<img width='107' id='licensePic_img' height='64' src='"+comm.getFsServerUrl(obj.licensePic)+"'/>");
						
						//绑定图片预览 
						var btnIds = ['#licensePic'];
						var imgIds = ['#thum-3'];
						comm.initPicPreview("#licensePic_img", obj.licensePic);
						comm.initUploadBtn(btnIds, imgIds);
					}
					
					
					$('#ywblxx_xg_cancel').triggerWith('#ywblxx');
					
					//将身份证x变X
					$("#credentialsNo").change(function(){
						if(obj.certype == '1'){
							//身份证号码将x转成X
							var creNoNew = $("#credentialsNo").val();
							creNoNew = creNoNew.replace(/x/g,'X');
							$('#credentialsNo').val(creNoNew);
						}
					});
					
					$('#ywblxx_xg_save').click(function(){
						
						if(obj.num == 'xfz'){
							
							if(!ywblxxPage.personInfo_validateData().form()){
								return;
							}else{
								//初始化修改个人（消费者）实名认证信息页面
								ywblxxPage.initChangeData4Person(obj);
							}
						}else{
							//验证输入合法性
							if(!ywblxxPage.entInfo_validateData(obj).form()){
								return;
							}else{
								//初始化修改企业实名认证信息页面
								ywblxxPage.initChangeData4Ent(obj);
							}
						}
						
					});
					
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
		//初始化修改个人（消费者）实名认证
		initChangeData4Person : function(obj){
			/**trImageId ,trImageSrc 是针对IE7，IE8的变量，主要用户弹出确认页面中的【变更后内容】中图片的滤镜图片的生成
			 * 它不同与Google  火狐  在上传时有一个临时图片路径生成，见 <img> 的 data-src属性，ie的预览是滤镜效果无路径
			 * 所以需要在弹出的确认对话框中在生成一次滤镜**/
			var trImageId = [];
			var trImageSrc = [];
			
			var changNum = 0;
			var changJson = {};		//存入修改的对象及内容
			var trs = "";
			var flag = true;
			var fileIds = []; 		//存入文件有修改的对象元素id
			var delFileIds = [];	//存入文件有修改的原文件id ,在上传新文件时需将原文件删除,节省文件系统空间
			//检查输入项有无改变的对象
			$(".isChange").each(function(a,b){
				
				//描述
				var desc = $(b).parent().prev().prev().text();
				//属性名
				var pname = $(b).attr("name");
				//修改后的值
				var nvalue = $(b).attr("value");
				
				if(comm.removeNull(obj[pname]) != comm.removeNull(nvalue) && comm.trim(nvalue) != "")
				{
					changNum++;
					
					changJson[pname] = {"old": comm.removeNull(obj[pname]) , "new": nvalue};
					
					trs = trs + "<tr><td class='view_item'>" + desc + "</td><td class='view_text'>" + comm.removeNull(obj[pname]) + "</td><td class='view_text'>" + nvalue + "</td></tr>";
				}
			});
			
			//当类型为身份证或护照时  有性别和国籍  营业执照则没有
			if(obj.certype == '1' || obj.certype == '2'){
				
				//对性别做判断是否有改变
				var nsex = $("#sex_type").val();
				if(comm.removeNull(obj.sexText) != comm.removeNull(nsex) && comm.trim(nsex) != ""){
					changNum++;
					changJson["sex"] = {"old":comm.removeNull(obj.sexText),"new":nsex};
					trs = trs + "<tr><td class='view_item'>"+comm.lang("infoManage").smrzPersonItem.sex+"</td><td class='view_text'>" + comm.removeNull(obj.sexText) + "</td><td class='view_text'>" + nsex + "</td></tr>";
				}
				//对国籍做判断
				var countryName = $("#countryName").find("option:selected").text();
				var countryNo = $("#countryName").val();
				if(comm.removeNull(obj.countryNo) != comm.removeNull(countryNo) && comm.trim(countryNo) != ""){
					changNum++;
					changJson["countryName"] = {"old":comm.removeNull(obj.countryText),"new":countryName};
					trs = trs + "<tr><td class='view_item'>"+comm.lang("infoManage").smrzPersonItem.countryName+"</td><td class='view_text'>" + comm.removeNull(obj.countryText) + "</td><td class='view_text'>" + countryName + "</td></tr>";
				}
			}
			//检查附件（图片文件）有无改变的对象
			$(".certificateSample_img").each(function(a,b){
				//描述
				var desc = $(b).parent().prev().text();
				//属性名称
				var pname = $(b).attr("tabindex");
				//新url
				var nurl = $(b).children().first().attr('src');//.attr("data-imgsrc");
				//原url
				var ourl = comm.getFsServerUrl(obj[pname]);
				if(comm.isNotEmpty($("#"+pname).val())){	//nurl != null && nurl != '' && nurl != ourl
				
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
					"<span class='fl ml5 mt5 sample_img' style='margin-bottom:2px'><img id='dialog"+pname+"' src='"+nurl+"' width='81' height='52' /></span></td></tr>";
					
					changNum++;
					flag = false;
					fileIds[fileIds.length] = pname;
					delFileIds[delFileIds.length] = obj[pname];
				}
				
			});
			
			if(comm.isEmpty(trs) && flag){
				comm.warn_alert(comm.lang("infoManage").noUpdate);
				return;
			}
			$('#confirm_dialogBox > div').html(_.template(ywblxx_xg_dialogTpl,obj));
			
			
			//证件正面
			comm.initPicPreview("#dialog_cerpica", obj.cerpica);
			
			//证件反面  当类型为身份证时有
			if(obj.certype == '1'){
				comm.initPicPreview("#dialog_cerpicb", obj.cerpicb);
			}else{
				
			}
			
			//手持证件
			comm.initPicPreview("#dialog_cerpich", obj.cerpich);
			
			//将修改项加到table中
			$('#personTab tr:eq(1)').before(trs);
			
			var height = 460 + 40 * changNum;
			/*弹出框*/
			$( "#confirm_dialogBox" ).dialog({
				title:comm.lang("infoManage").smrzconfirUpdata,//"实名认证修改确认",
				modal:true,
				width:"800",
				height:height,
				buttons:{ 
					"确定":function(){
						
						ywblxxPage.updatePerson($(this),obj,changJson,fileIds,delFileIds);
						
					},
					"取消":function(){
						$(this).dialog("destroy");
					}
				}
			  });	
			
			//ie8 下生成滤镜图片
			ywblxxPage.initTrsImage(trImageId, trImageSrc);
			
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
//			$("#verificationMode").selectListValue("1");
			
			window.setTimeout(function(){
				$("#doubleUserName").attr("value","");
				$("#doublePassword").val("");	
			},100); 
		},
		
		//初始化修改企业实名认证信息
		initChangeData4Ent : function(obj){
			/**trImageId ,trImageSrc 是针对IE7，IE8的变量，主要用户弹出确认页面中的【变更后内容】中图片的滤镜图片的生成
			 * 它不同与Google  火狐  在上传时有一个临时图片路径生成，见 <img> 的 data-src属性，ie的预览是滤镜效果无路径
			 * 所以需要在弹出的确认对话框中在生成一次滤镜**/
			var trImageId = [];
			var trImageSrc = [];
			
			var changNum = 0;
			var changJson = {};		//存入修改的对象及内容
			var trs = "";
			var flag = true;
			var fileIds = []; 		//存入文件有修改的对象元素id
			var delFileIds = [];	//存入文件有修改的原文件id ,在上传新文件时需将原文件删除,节省文件系统空间
			//检查输入项有无改变的对象
			$(".isChange").each(function(a,b){
				
				//描述
				var desc = $(b).parent().prev().prev().text();
				//属性名
				var pname = $(b).attr("name");
				//修改后的值
				var nvalue = $(b).attr("value");
				
				if(comm.removeNull(obj[pname]) != comm.removeNull(nvalue) && comm.removeNull(nvalue)!="")
				{
					
					changNum++;
					
					changJson[pname] = {"old": comm.removeNull(obj[pname]) , "new": nvalue};
					
					trs = trs + "<tr><td class='view_item'>" + desc + "</td><td class='view_text' style='word-wrap:break-word;word-break:break-all;'>" + comm.removeNull(obj[pname]) + "</td><td class='view_text'>" + nvalue + "</td></tr>";
				}
			});
			
			//检查附件（图片文件）有无改变的对象
			$(".certificateSample_img").each(function(a,b){
				//描述
				var desc = $(b).parent().prev().text();
				//属性名称
				var pname = $(b).attr("tabindex");
				//新url
				var nurl = $(b).children().first().attr('src');//.attr("data-imgsrc");
				//原url
				var ourl = comm.getFsServerUrl(obj[pname]);
				if(comm.isNotEmpty($("#"+pname).val())){ //nurl != null && nurl != '' && nurl != ourl
					
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
					"<span class='fl ml5 mt5 sample_img' style='margin-bottom:2px'><img id='dialog"+pname+"' src='"+nurl+"' width='81' height='52' /></span></td></tr>";
					
					changNum++;
					flag = false;
					fileIds[fileIds.length] = pname;
					delFileIds[delFileIds.length] = obj[pname];
				}
				
			});
			
			if(comm.isEmpty(trs) && flag){
				comm.warn_alert(comm.lang("infoManage").noUpdate);
				return;
			}
			
			$('#confirm_dialogBox > div').html(_.template(ywblxx_xg_dialogTpl,obj));
			
			//营业执照
			comm.initPicPreview("#dialog_licensePic", obj.licensePic);
			
			//将修改项加到table中
			$('#entTab tr:eq(1)').before(trs);
			
			var height = 460 + 40 * changNum;
			/*弹出框*/
			$( "#confirm_dialogBox" ).dialog({
				title:comm.lang("infoManage").smrzconfirUpdata,//"实名认证修改确认",
				modal:true,
				width:"800",
				height:height,
				buttons:{ 
					"确定":function(){
						
						//执行修改
						ywblxxPage.updateEnt($(this),obj,changJson,fileIds,delFileIds);
						
					},
					"取消":function(){
						$(this).dialog("destroy");
					}
				}
			  });
			
			//ie8 下生成滤镜图片
			ywblxxPage.initTrsImage(trImageId, trImageSrc);
			
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
//			$("#verificationMode").selectListValue("1");
			
			window.setTimeout(function(){
				$("#doubleUserName").attr("value","");
				$("#doublePassword").val("");	
			},100); 
		},
		
		//提交修改企业实名认证
		updateEnt : function(dialog,obj,changJson,fileIds,delFileIds){
			
			//验证双签输入
			if(!ywblxxPage.validateData().form()){
				
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
						changJson[fileIds[key]] = {"old":comm.removeNull(obj[fileIds[key]]),"new":data[fileIds[key]]};
					}
					
					//获取cookie中的客户号等信息
					var param = comm.getRequestParams(param);
					
					//企业客户类型 2:成员 3:托管 4:服务
					var custType = "";
					if(obj.num == 'tgqy')
					{
						custType = 3;
					}
					else if(obj.num == 'cyqy')
					{
						custType = 2;
					}
					else if(obj.num == 'fwgs')
					{
						custType = 4;
					}
					var postData = {
						applyId : obj.applyId,
						entResNo : obj.entResNo,
						entCustId : obj.entCustId,
						custType : custType,
						entCustName : comm.isNotEmpty($("#entCustName").val())?$("#entCustName").val():obj.entCustName,
						entAddr : comm.isNotEmpty($("#entAddr").val())?$("#entAddr").val():obj.entAddr,
						linkman : obj.linkman,
						mobile : obj.mobile,
						legalName : comm.isNotEmpty($("#legalName").val())?$("#legalName").val():obj.legalName,
						licenseNo : comm.isNotEmpty($("#licenseNo").val())?$("#licenseNo").val():obj.licenseNo,
						optCustId : param.custId,
						optName : param.cookieOperNoName,
						optEntName : param.custEntName,
						optRemark : $("#optRemark").val(),
						dblOptCustId : verifyRes.data,
						changeContent : JSON.stringify(changJson),
						licensePic : obj.licensePic
					};
					
					//如果附件有修改  则将postData中相应的附件改为现在的值
					if(fileIds.length > 0){
						for(var key = 0; key < fileIds.length; key++){
							if(fileIds[key] == "licensePic"){
								postData.licensePic = data[fileIds[key]];
							}
						}
					}
					
					infoManage.modifyEntRealNameIdentific(postData,function(res){
						
							comm.alert({
								content:comm.lang("infoManage")[obj.num + "smrzUpdataSuccess"],callOk:function(){
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
		
		// 提交 修改个人（消费者）实名认证
		updatePerson : function(dialog,obj,changJson,fileIds,delFileIds){
			
			//验证双签输入
			if(!ywblxxPage.validateData().form()){
				
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
						
						for(var key = 0;key < fileIds.length;key++){
							changJson[fileIds[key]] = {"old":comm.removeNull(obj[fileIds[key]]),"new":data[fileIds[key]]};
						}
						var param = comm.getRequestParams(param);
						var postData = {
								applyId : obj.applyId,
								/**营业执照类型 **/
								entName : comm.isEmpty($("#entName").val())?obj.entName:$("#entName").val(),		//企业名称
								credentialsNo : comm.isEmpty($("#credentialsNo").val())?obj.credentialsNo:$("#credentialsNo").val(), //证件号码
								entRegAddr : comm.isEmpty($("#entRegAddr").val())?obj.entRegAddr:$("#entRegAddr").val(), 	//注册地址
								entBuildDate : comm.isEmpty($("#entBuildDate").val())?obj.entBuildDate:$("#entBuildDate").val(),	//成立日期
								entType : comm.isEmpty($("#entType").val())?obj.entType:$("#entType").val(),			//企业类型
								
								//护照、身份证
								name : comm.isEmpty($("#name").val())?obj.name:$("#name").val(),      		//姓名
								sex : comm.isEmpty($("#sex_type").attr("optionvalue"))?obj.sex:$("#sex_type").attr("optionvalue"),  //性别
								countryNo : comm.isEmpty($("#countryName").val())?obj.countryNo:$("#countryName").val(),       //国籍代码
								countryName : comm.isEmpty($("#countryName").val())?obj.countryName:$("#countryName").find("option:selected").text(),  //国籍名称
								validDate : comm.isEmpty($("#validDate").val())?obj.validDate:$("#validDate").val(),			//证件有效期
								
								//身份证
								birthAddr : comm.isEmpty($("#birthAddr").val())?obj.birthAddr:$("#birthAddr").val(),			//户籍地址 、出生地点
								licenceIssuing : comm.isEmpty($("#licenceIssuing").val())?obj.licenceIssuing:$("#licenceIssuing").val(),		//发证机关、签发机关
								profession : comm.isEmpty($("#profession").val())?obj.profession:$("#profession").val(),		//职业
								
								//护照
								issuePlace : comm.isEmpty($("#issuePlace").val())?obj.issuePlace:$("#issuePlace").val(),		//签发地点
								nation : obj.nation,
								certype : obj.certype,
								postScript : comm.isEmpty($("#postScript").val())?obj.postScript:$("#postScript").val(),		//认证附言
								optCustId : param.custId,
								optName : param.cookieOperNoName,
								optEntName : param.custEntName,
								optRemark : $("#optRemark").val(),
								dblOptCustId : verifyRes.data,
								changeContent : JSON.stringify(changJson),
								
								cerpica : obj.cerpica,
								cerpicb : obj.cerpicb,
								cerpich : obj.cerpich
						};
						
						//如果附件有修改  则将postData中相应的附件改为现在的值
						if(fileIds.length > 0){
							for(var key = 0;key < fileIds.length;key++){
								if(fileIds[key] == "cerpica"){
									postData.cerpica = data[fileIds[key]];
								}
								if(fileIds[key] == "cerpicb"){
									postData.cerpicb = data[fileIds[key]];
								}
								if(fileIds[key] == "cerpich"){
									postData.cerpich = data[fileIds[key]];
								}
							}
						}
						//提交修改
						infoManage.modifyPerRealNameIdentific(postData,function(res){
							
							comm.alert({
								content:comm.lang("infoManage").xfzsmrzUpdataSuccess,callOk:function(){
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
		validateData : function(){
			 var validate = $("#ywblxx_xg_dialog").validate({
				rules : {
					verificationMode : {
						required : true
					},
					doubleUserName:{
						required : true
					},
					optRemark : {
						rangelength : [2, 300]
					},
					doublePassword:{
						required : true
					}
				},
				messages : {
					verificationMode : {
						required : comm.lang("infoManage")[36050]
					},
					doubleUserName:{
						required : comm.lang("infoManage")[34505]
					},
					optRemark : {
						rangelength : comm.lang("infoManage")[36006]
					},
					doublePassword:{
						required : comm.lang("infoManage")[34506]
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
		personInfo_validateData : function(){

			var validate = $("#ywblxx_xg_personinfo_form").validate({
				rules : {
					credentialsNo : {
						
					}
				},
				messages : {
					credentialsNo : {
						
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
			
			//法人代表证件类型
			var certype = $("#certificates_type").attr("optionvalue");
			if(comm.isNotEmpty(certype) && certype == '1'){
				validate.settings.rules.credentialsNo = {IDCard : true};
				validate.settings.messages.credentialsNo = {IDCard:comm.lang("infoManage")[36038]};
			}else if(comm.isNotEmpty(certype) && certype == '2'){
				validate.settings.rules.credentialsNo = {passport : true};
				validate.settings.messages.credentialsNo = {passport:comm.lang("infoManage")[36039]};
			}else if(certype == '3'){
				validate.settings.rules.credentialsNo = {businessLicence : true};
				validate.settings.messages.credentialsNo = {businessLicence:comm.lang("infoManage")[36033]};
			}
			return validate;
		
		},
		entInfo_validateData:function(obj){

			var validate = $("#ywblxx_xg_entinfo_form").validate({
				rules : {
					
					licenseNo:{
						businessLicence : true
					}
				},
				messages : {
					licenseNo:{
						businessLicence:comm.lang("infoManage")[36033]
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top-10",
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
		}
	};
	return ywblxxPage
});