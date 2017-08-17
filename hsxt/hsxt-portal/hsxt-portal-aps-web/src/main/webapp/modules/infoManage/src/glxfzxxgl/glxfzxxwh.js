define(['text!infoManageTpl/glxfzxxgl/glxfzxxwh.html',
		'text!infoManageTpl/glxfzxxgl/glxfzxxwh_xg.html',
		'text!infoManageTpl/glxfzxxgl/glxfzxxwh_xg_dialog.html',
		'infoManageDat/glxfzxxgl/glxfzxxgl'
		], function(glxfzxxwhTpl, glxfzxxwh_xgTpl, glxfzxxwh_xg_dialogTpl,infoManage){
	var self = null;
	var glxfzxxwhPage = {
		showPage : function(){
			$('#busibox').html(_.template(glxfzxxwhTpl));	
			
			self = glxfzxxwhPage;
			$("#qry_xfz_btn").click(function(){
				//查询参数
				var params={
								search_entResNo:comm.getSysCookie('entResNo'),//企业互生号
								search_belongPerResNo:$.trim($('#search_belongPerResNo').val()),	
								search_belongPerName:$.trim($('#search_belongPerName').val())	
							};
				gridObj = comm.getCommBsGrid("searchTable","queryRelateConsumerInfo",params,comm.lang("infoManage"),self.update,self.detail);
				/*cacheUtil.findCacheContryAll(function(){
				});*/
			});
			
			
			$("#qry_xgjl_btn").click(function(){
				self.xiuGaijl();
			});
		},
		/**
		 * 绑定图片预览
		 */
		initPicPreview : function(){
			var btnIds = ['#file1', '#file2','#file3'];
			var imgIds = ['#yl_1', '#yl_2','#yl_3'];
			$("body").on("change", "", function(){
				for(var k in imgIds){
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			comm.initUploadBtn(btnIds, imgIds, 81, 52);
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return  comm.lang("infoManage").idCardType[record["idType"]];
			}else if(colIndex == 5){//消费者状态	
				return comm.getNameByEnumId(record['realnameStatus'], comm.lang("infoManage").consumerRealNameAuthSatus);
			}else if(colIndex == 6){//消费者状态完成时间
				switch (record['realnameStatus']) {
//					case 1:
//						return record['createDate'];
					case 2:
						return record['realnameRegDate'];
					case 3:
						return record['realnameAuthDate'];
				}
			}else if(colIndex == 7){//互生号状态
				 var link1 = comm.getNameByEnumId(record['baseStatus'], comm.lang("infoManage").HSCardStatusEnum);
				 return link1 ;
			}else if(colIndex == 8){//修改记录
				 var link1 = $('<a>'+comm.lang("infoManage").updateRecordTitle+'</a>').click(function(e){
					 
					 	$("#belongCustId").val(record.custId)
					 	
						self.xiuGaijl(record);
					 	
						$('#glqyxxwh_jl').dialog({
						 	width:800,
						 	closeIcon:true,
						 	title: comm.lang("infoManage").updateRecordTitle ,
						 	buttons:{
						 		'确定':function(){
						 			$(this).dialog('destroy');
						 		}
						 	}
						 });
					}.bind(this));
				 return link1 ;
			}
		},
		update : function(record, rowIndex, colIndex, options){
			if(colIndex == 8){//修改
				 var link1 = $('<a>'+comm.lang("infoManage").updateTitle+'</a>').click(function(e) {
						var obj = gridObj.getRecord(rowIndex);
						infoManage.searchConsumerAllInfo({perCustId:obj.custId},function(res){
							//消费者全部信息
							var data = res.data;
							self.xiuGai(data);
						});
						
					}.bind(this) ) ;
				 return link1 ;
			}
		},
		xiuGaijl : function(obj){
			
				 var params = {};
				 params.search_belongCustId = $("#belongCustId").val();//客户ID
				 params.search_updateFieldName = $("#updateFieldName").val();
				 //初始化修改记录表格
				 gridObjs = comm.getCommBsGrid("xgjl_table","queryConsumberInfoBakList",params,comm.lang("infoManage"),function(record, rowIndex, colIndex, options){
					 if(colIndex == 2){
						 var obj = gridObjs.getRecord(rowIndex);
						 var fileId = obj.updateField;
						 var oldValue = obj.updateValueOld;
						 return self.initDataType(fileId,oldValue,colIndex,rowIndex);
					 }else if(colIndex == 3){
						 var obj = gridObjs.getRecord(rowIndex);
						 var fileId = obj.updateField;
						 var newValue = obj.updateValueNew;
						 return self.initDataType(fileId,newValue,colIndex,rowIndex);
					 }
				 });
		},
		initDataType : function(fileId,value,colIndex,rowIndex){
			var link;
			//图片查看链接
			if("cerPich" == fileId || "cerPicb" == fileId || "cerPica" == fileId){
				if(value){
					link = $('<a id="search_'+rowIndex+colIndex+'">查看图片</a>').click(function(e){
						comm.initPicPreview("#search_"+rowIndex+colIndex, value, "");
					}.bind(this));
				} 
			}else if("cerType" == fileId)
			{
				if(value){
					link = comm.lang("infoManage").idCardType[value];
				}
			}else if("sex" == fileId){
				if(value){
					link = comm.lang("infoManage").sexType[value];
				}
			}
			else{
				link = "<span title="+comm.removeNull(value)+">"+comm.removeNull(value)+"</span>";
			}
			return link;
		},
		xiuGai : function(data){
			comm.delCache('infoManage', 'glxfzxxwh-glxfzxxwh-xg-info');
			comm.setCache('infoManage', 'glxfzxxwh-glxfzxxwh-xg-info', data);
			comm.liActive_add($('#xgxfzxx'));
			
			$("#glxfzxxwhTpl").addClass('none');			
			$("#carder_detail").removeClass('none');	
			$('#carder_detail').html(_.template(glxfzxxwh_xgTpl, data));
			if(data.authInfo.validDate){
				if(comm.lang("infoManage").longTerm == data.authInfo.validDate){
					$("#long-term").attr("checked","checked");
				}
				$("#validDate").val(data.authInfo.validDate);
			}
			//图片预览
			self.initPicPreview();
			comm.initPicPreview("#yl_1", data.authInfo.cerPica);
			comm.initPicPreview("#yl_2", data.authInfo.cerPicb);
			comm.initPicPreview("#yl_3", data.authInfo.cerPich);
			var url_a = comm.getFsServerUrl(data.authInfo.cerPica);
			if('' == url_a){
				url_a = "resources/img/noImg.gif";
			}
			var url_b = comm.getFsServerUrl(data.authInfo.cerPicb);
			if('' == url_b){
				url_b = "resources/img/noImg.gif";
			}
			var url_h = comm.getFsServerUrl(data.authInfo.cerPich);
			if('' == url_h){
				url_h = "resources/img/noImg.gif";
			}
			$("#yl_1").html("<img width='81' height='52' src='"+url_a+"'/>");
			$("#yl_2").html("<img width='81' height='52' src='"+url_b+"'/>");
			$("#yl_3").html("<img width='81' height='52' src='"+url_h+"'/>");
			//加载样例图片
			if(data.authInfo.cerType == '1'){
				cacheUtil.findDocList(function(map){
					comm.initPicPreview("#sl_1", comm.getPicServerUrl(map.picList, '1001'), null);
					comm.initPicPreview("#sl_2", comm.getPicServerUrl(map.picList, '1002'), null);
					comm.initPicPreview("#sl_3", comm.getPicServerUrl(map.picList, '1005'), null);
				});
			}else if(data.authInfo.cerType == '2'){
				cacheUtil.findDocList(function(map){
					comm.initPicPreview("#sl_1", comm.getPicServerUrl(map.picList, '1027'), null);
					comm.initPicPreview("#sl_3", comm.getPicServerUrl(map.picList, '1022'), null);
				});
			}else if(data.authInfo.cerType == '3'){
				cacheUtil.findDocList(function(map){
					comm.initPicPreview("#sl_1", comm.getPicServerUrl(map.picList, '1010'), null);
					comm.initPicPreview("#sl_3", comm.getPicServerUrl(map.picList, '1023'), null);
				});
			}
			//初始化国家列表
			cacheUtil.findCacheContryAll(function(countryList){
				comm.initOption('#nationality', countryList , 'countryName','countryNo', true,data.authInfo.countryCode);
//				comm.initSelect("#nationality", countryList,180, data.authInfo.countryCode, false,null,"countryNo","countryName");
			});
			// 证件类型
			comm.initSelect("#document_type", comm.lang("infoManage").idCardType, 180, data.authInfo.cerType).change(function(){
				var value = $(this).attr("optionvalue");
				if(value == 1){
					$("#file2_li").show();
				}else if(value == 2){
					$("#file2_li").hide();
				}else if(value == 3){
					$("#file2_li").hide();
				}
			});
			//初始化日期
			self.initDate();
			// 修改取消、提交
			
			
			/*按钮事件*/
			$('#glxfzxxgl_xg_cancel').click(function(){
				$("#glxfzxxwhTpl").removeClass("none");
				$('#carder_detail').addClass("none").html("");
				comm.liActive($('#glxfzxxwh'),'#xgxfzxx');
			});
			$('#glxfzxxgl_xg_btn').click(function(){
				if(!self.validate_glxfzxxwh_xg().form()){
					return;
				}
				self.initChangeData();
			});
			//护照没有反面照片
			if(data.authInfo.cerType == 2){
				$("#file2_li").hide();
			}else if(data.authInfo.cerType == 3){
				$("#file2_li").hide();
			}
			
			$("#long-term").click(function(){
				if('checked' == $("#long-term").attr('checked')){
					$('#validDate').val(comm.lang("infoManage").longTerm);
				}
			});
			$("#validDate").change(function(){
				$("#long-term").removeAttr('checked');
			});
		},
		
		/**
		 * 上传资料图片
		 */
		aptitudeUpload : function(trs,chg,ndata,ndatas){
			
			var oldArray = [];//修改前图片信息
			var newArray = [];//修改后图片信息
			var ids = [];
			var oldPic1 = '无';
			var newPic1;
			if($("#file1").val() != ""){
				ids[ids.length] = "file1";
				newPic1 = "<a id=\"cerPica-new\" class=\"blue ml5\">查看证件正面</a>";
				var oFid = $("#cerPica_o").val();
				if(oFid != ""){
					oldPic1 = "<a id=\"cerPica-old\" class=\"blue ml5\">查看证件正面</a>";
					oldArray.push({picId:"#cerPica-old", fileId: oFid, title:"查看证件正面"});
				}
				newArray.push({picId:"#cerPica-new", fileId: '#yl_1', title:"查看证件正面"});
			}
			var oldPic2 = '无';
			var newPic2;
			if($("#file2").val() != ""){
				ids[ids.length] = "file2";
				newPic2 = "<a id=\"cerPicb-new\" class=\"blue ml5\">查看证件反面</a>";
				var oFid = $("#cerPicb_o").val();
				if(oFid != ""){
					oldPic2 = "<a id=\"cerPicb-old\" class=\"blue ml5\">查看证件反面</a>";
					oldArray.push({picId:"#cerPicb-old", fileId: oFid, title:"查看证件反面"});
				}
				newArray.push({picId:"#cerPicb-new", fileId: '#yl_2', title:"查看证件反面"});
			}
			var oldPic3 = '无';
			var newPic3;
			if($("#file3").val() != ""){
				ids[ids.length] = "file3";
				newPic3 = "<a id=\"cerPich-new\" class=\"blue ml5\">查看手持证件大头照</a>";
				var oFid = $("#cerPich_o").val();
				if(oFid != ""){
					oldPic3 = "<a id=\"cerPich-old\" class=\"blue ml5\">查看手持证件大头照</a>";
					oldArray.push({picId:"#cerPich-old", fileId: oFid, title:"查看手持证件大头照"});
				}
				newArray.push({picId:"#cerPich-new", fileId: '#yl_3', title:"查看手持证件大头照"});
			}
			
			if(ids.length == 0){
				self.commitData(trs,chg,ndata,ndatas,ids);
				
			}else{
				var oPics = '';
				var nPics = '';
				if(newPic1){
					oPics = oldPic1;
					nPics = newPic1;
				};
				if(newPic2){
					oPics = oPics + oldPic2 ;
					nPics = nPics + newPic2;
				};
				if(newPic3){
					oPics = oPics + oldPic3;
					nPics = nPics + newPic3;
				} ;
				trs+="<tr><td class=\"view_item\">附件上传</td><td class=\"view_text\">" + oPics +"</td><td class=\"view_text\">"+ nPics+ "</td></tr>";
				
				self.commitData(trs,chg,ndata,ndatas,ids);
				
				for(var key = 0; key < oldArray.length; key++){
					comm.initPicPreview(oldArray[key].picId, oldArray[key].fileId, oldArray[key].title);
				}
				for(var key = 0; key < newArray.length; key++){
					comm.initTmpPicPreview(newArray[key].picId, $(newArray[key].fileId).children().first().attr('src'), newArray[key].title);
				}
			}
		},
		
		/**
		 * 初始化提交页面
		 */
		initChangeData : function(){
			/*if(!self.validateData().form()){
				return;
			}*/
			var ckArray = [];//存入需要检查是否改变的对象--authInfo
			$(".isChange").each(function(){
				var desc = $(this).prev()[0].innerText;
				ckArray.push({name:this.name, value:this.value, desc:desc});
			});
			var ckArray_base = [];//存入需要检查是否改变的对象---baseInfo
			$(".isBaseChange").each(function(){
				var desc = $(this).prev()[0].innerText;
				ckArray_base.push({name:this.name, value:this.value, desc:desc});
			});
			var odata = comm.getCache('infoManage', 'glxfzxxwh-glxfzxxwh-xg-info');
			var odata_authInfo = odata.authInfo;
			var odata_baseInfo = odata.baseInfo;
			var ndata = {};
			var ndatas = comm.cloneJSON(odata);
			var trs = "";
			var chg = [];
			for(var key = 0; key < ckArray.length; key++){
				if(comm.removeNull(odata_authInfo[ckArray[key].name]) != ckArray[key].value){
					trs+="<tr><td class=\"view_item\">"+ckArray[key].desc+"</td><td class=\"view_text\">"+comm.removeNull(odata_authInfo[ckArray[key].name])+"</td><td class=\"view_text\">"+ckArray[key].value+"</td></tr>";
					chg.push({"logType":2,"updateField":ckArray[key].name,"updateFieldName":ckArray[key].desc,"updateValueOld":odata_authInfo[ckArray[key].name], "updateValueNew":ckArray[key].value});
					ndata[ckArray[key].name] = ckArray[key].value;
					ndatas.authInfo[ckArray[key].name] = ckArray[key].value;
				}
			}
			for(var key = 0; key < ckArray_base; key++){
				if(comm.removeNull(odata_baseInfo[ckArray_base[key].name]) != ckArray_base[key].value){
					trs+="<tr><td class=\"view_item\">"+ckArray_base[key].desc+"</td><td class=\"view_text\">"+comm.removeNull(odata_baseInfo[ckArray_base[key].name])+"</td><td class=\"view_text\">"+ckArray_base[key].value+"</td></tr>";
					chg.push({"logType":2,"updateField":ckArray_base[key].name,"updateFieldName":ckArray_base[key].desc,"updateValueOld":odata_baseInfo[ckArray_base[key].name], "updateValueNew":ckArray_base[key].value});
					ndata[ckArray_base[key].name] = ckArray_base[key].value;
					ndatas.baseInfo[ckArray_base[key].name] = ckArray_base[key].value;
				}
			}
			
			//比较证件类型
			var oType = comm.removeNull(odata.authInfo.cerType);
			var nType = comm.removeNull($("#certype").val());
			if(oType != nType){
				var oDesc = comm.getNameByEnumId(oType, comm.lang("infoManage").idCardType);
				var nDesc = comm.getNameByEnumId(nType, comm.lang("infoManage").idCardType);
				trs+="<tr><td class=\"view_item\">法人代表证件类型</td><td class=\"view_text\">"+oDesc+"</td><td class=\"view_text\">"+nDesc+"</td></tr>";
				chg.push({"logType":2,"updateField":'cerType',"updateFieldName":'证件类型',"updateValueOld":oType, "updateValueNew":nType});
				ndata.cerType = nType;
				ndatas.authInfo.cerType = nType;
			}
			if(oType == '1' || oType == '2'){
				//比较国籍
				var oCountryCode = comm.removeNull(odata.authInfo.countryCode);
				var nCountryCode = comm.removeNull($("#nationality").val());
				if(oCountryCode != nCountryCode){
					var oCountryDesc = cacheUtil.getCountryNameByNo(oCountryCode);
					var nCountryDesc = cacheUtil.getCountryNameByNo(nCountryCode);
					trs+="<tr><td class=\"view_item\">国籍</td><td class=\"view_text\">"+oCountryDesc+"</td><td class=\"view_text\">"+nCountryDesc+"</td></tr>";
					chg.push({"logType":2,"updateField":'countryCode',"updateFieldName":'国籍',"updateValueOld":oCountryCode, "updateValueNew":nCountryCode});
					ndata.countryCode = nCountryCode;
					ndatas.authInfo.countryCode = nCountryCode;
				}
				//比较性别
				var oSex = comm.removeNull(odata.authInfo.sex);
				var nSex = comm.removeNull($('input[name="sex"]:checked ').val());
				if(oSex != nSex){
					var oSexDesc = comm.getNameByEnumId(oSex, comm.lang("infoManage").sexType);
					var nSexDesc = comm.getNameByEnumId(nSex, comm.lang("infoManage").sexType);
					trs+="<tr><td class=\"view_item\">性别</td><td class=\"view_text\">"+oSexDesc+"</td><td class=\"view_text\">"+nSexDesc+"</td></tr>";
					chg.push({"logType":2,"updateField":'sex',"updateFieldName":'性别',"updateValueOld":oSex, "updateValueNew":nSex});
					ndata.sex = nSex;
					ndatas.authInfo.sex = nSex;
				}
			}
			//比较手机号码
			var oMobile = comm.removeNull(odata.baseInfo.mobile);
			var nMobile = comm.removeNull($("#mobile").val());
			if(oMobile != nMobile){
				trs+="<tr><td class=\"view_item\">手机号码</td><td class=\"view_text\">"+oMobile+"</td><td class=\"view_text\">"+nMobile+"</td></tr>";
				chg.push({"logType":2,"updateField":'mobile',"updateFieldName":'手机号码',"updateValueOld":oMobile, "updateValueNew":nMobile});
				ndata.mobile = nMobile;
				ndatas.baseInfo.mobile = nMobile;
			}
			self.aptitudeUpload(trs,chg,ndata,ndatas);
		},
		
		commitData : function(trs,chg,ndata,ndatas,ids){
			if(trs == ""){
				comm.warn_alert(comm.lang("infoManage").noUpdate);
				return;
			}
			//提交
			$('#dialogBox').html(_.template(glxfzxxwh_xg_dialogTpl));
			$('#copTable tr:eq(1)').before(trs);
			var size = trs.split("<tr>").length;
			var height = size * 30 +400;
			self.initVerificationMode();
			$('#dialogBox').dialog({
				width:700,
				height:height,
				buttons:{
					'确定':function(){
						if(!self.validateViewMarkData().form()){
							return;
						}
						var verificationMode = $("#verificationMode").attr('optionValue');
						if(verificationMode != "1"){
							comm.warn_alert(comm.lang("infoManage").notSupportValidateMode);
							return false;
						}
						if(ids.length == 0){
							self.saveData(ndata, ndatas, chg);
						}else{
							comm.uploadFile(null, ids, function(data){
								
									if(data.file1){
										chg.push({"logType":2,"updateField":'cerPica',"updateFieldName":'证件正面照',"updateValueOld":ndatas.authInfo.cerPica, "updateValueNew":data.file1});
										ndata.cerPica = data.file1;
									}
									if(data.file2){
										chg.push({"logType":2,"updateField":'cerPicb',"updateFieldName":'证件反面照',"updateValueOld":ndatas.authInfo.cerPicb, "updateValueNew":data.file2});
										ndata.cerPicb = data.file2;
									}
									if(data.file3){
										chg.push({"logType":2,"updateField":'cerPich',"updateFieldName":'手持证件照',"updateValueOld":ndatas.authInfo.cerPich, "updateValueNew":data.file3});
										ndata.cerPich = data.file3;
									}
									self.saveData(ndata,ndatas, chg);
							}, function(){
								self.initPicPreview();
							}, null);
						}
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
		saveData : function(ndata,ndatas, chg){
			comm.getToken(null,function(resp){
				if(resp){
					ndata.dbuserName = $("#doubleUserName").val();
					ndata.loginPwd = comm.encrypt($('#doublePassword').val(),resp.data);
					ndata.secretKey = resp.data;
					ndata.optRemark = $("#viewMark").val();
					ndata.changeContent = JSON.stringify(chg);
					ndata.belongPerCustId = ndatas.baseInfo.perCustId;
					ndata.typeEum = "2";
					infoManage.updateConsumerInfo(ndata, function(res){
						comm.alert({content:comm.lang("infoManage")[22000], callOk:function(){
							var perCustId = ndata.belongPerCustId;
							infoManage.searchConsumerAllInfo({perCustId:perCustId},function(res){
								//消费者全部信息
								var data = res.data;
								comm.delCache('infoManage', 'glxfzxxwh-glxfzxxwh-xg-info');
								comm.setCache('infoManage', 'glxfzxxwh-glxfzxxwh-xg-info', data);
							});
							$('#dialogBox').dialog('destroy');
							$("#xgxfzxx").click();
						}});
					});
				}
			});
		},
		initDate : function(){
			$("#validDate").datepicker({
				dateFormat : 'yy-mm-dd'
			});
			$("#entBuildDate").datepicker({
				dateFormat : 'yy-mm-dd',
				maxDate: "+0D"
			});
		},
		/**
		 * 表单校验
		 */
		validateViewMarkData : function(){
			
			return $("#confirm_dialog_form").validate({
				rules : {
					viewMark : {
						rangelength : [0, 300]
					},
					doubleUserName : {
						required : function(){
							return $("#fhy_userName").is(":visible");
						}
					},
					doublePassword : {
						required : function(){
							return $("#fhy_password").is(":visible");
						}
					},
					verificationMode:{
						required : true
					}
				},
				messages : {
					viewMark : {
						rangelength : comm.lang("infoManage")[36006]
					},
					doubleUserName : {
						required : comm.lang("infoManage")[36047]
					},
					doublePassword : {
						required : comm.lang("infoManage")[36048]
					},
					verificationMode:{
						required : comm.lang("infoManage")[36050]
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
		 * 初始化验证方式
		 */
		initVerificationMode : function(){
			comm.initSelect("#verificationMode", comm.lang("infoManage").verificationMode, 200, null).change(function(e){
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
			
			window.setTimeout(function(){
				$("#doubleUserName").attr("value","");
				$("#doublePassword").val("");	
			},100); 
		},
		validate_glxfzxxwh_xg : function(){
			var validate = $("#glxfzxxwh_xg_form").validate({
				 rules : {
					 cerNo : {
						required : false
					}
				},
				messages : {
					cerNo : {
						required:comm.lang("infoManage")[36040]
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
			var legalType = $("#certype").val();
			if(legalType == "1"){
				validate.settings.rules.cerNo = {required : false, IDCard : true};
				validate.settings.messages.cerNo = {required : comm.lang("infoManage")[36040],	IDCard : comm.lang("infoManage")[36038]};
			}else if(legalType == "2"){
				validate.settings.rules.cerNo = {required : false, passport : true};
				validate.settings.messages.cerNo = {required : comm.lang("infoManage")[36040],	passport : comm.lang("infoManage")[36039]};
			}else{
				validate.settings.rules.cerNo = {required : false,businessLicence:true};
				validate.settings.messages.cerNo = {required : comm.lang("infoManage")[36040],	businessLicence : comm.lang("infoManage")[36033]};
			}
			return validate;
			
		}
	};
	return glxfzxxwhPage
});