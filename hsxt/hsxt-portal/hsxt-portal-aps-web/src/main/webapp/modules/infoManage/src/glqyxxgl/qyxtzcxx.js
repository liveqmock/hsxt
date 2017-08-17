define(['text!infoManageTpl/glqyxxgl/qyxtzcxx.html',
		'text!infoManageTpl/glqyxxgl/qyxtzcxx_xg.html',
		'text!infoManageTpl/glqyxxgl/qyxtzcxx_xg_dialog.html',
		'infoManageDat/infoManage',
		'infoManageLan'
		], function(qyxtzcxxTpl, qyxtzcxx_xgTpl, qyxtzcxx_xg_dialogTpl, dataModoule){
	var self =  {
		showPage : function(obj){
			
			this.initData(obj.entCustId);
			
			/*end*/
		},
		/**
		 * 初始化数据
		 */
		initData : function(custID){
			var entAllInfo = null;
			dataModoule.resourceFindEntAllInfo({custID:custID}, function(res){
				entAllInfo = res.data.entInfo;
				self.initForm(entAllInfo);
			});
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			comm.delCache('infoManage', 'glqyxxgl-qyxtzcxx-info');
			comm.setCache('infoManage', 'glqyxxgl-qyxtzcxx-info', data);
			$('#infobox').html(_.template(qyxtzcxxTpl, data)).append(_.template(qyxtzcxx_xgTpl, data));
			//获取地区信息
			cacheUtil.syncGetRegionByCode(null, data.provinceNo, data.cityNo, "", function(resdata){
				$("#placeName").html(resdata);
				$("#placeName_xg").val(resdata);
			});
			
			
			/*按钮事件*/
			$('#qyxtzcxx_cancel').click(function(){
				$("#glqyxxwhTpl").removeClass("none");
				$('#glqyxxwh_operation').addClass("none").html("");
				comm.liActive($('#glqyxxwh'),'#xgqyxx');
			});
			
			
			//点击修改按钮
			$('#qyxtzcxx_modify').click(function(){
				
				$('#qyxtzcxxTpl').addClass('none');	
				$('#qyxtzcxx_xgTpl').removeClass('none');
				$('#qyxtzcxx_xg_cancel').triggerWith('#qyxtzcxx');
				
				//点击修改提交按钮
				$('#qyxtzcxx_xg_btn').click(function(){
					self.initChangeData();
				});
			});
			
		},
		/**
		 * 初始化提交页面
		 */
		initChangeData : function(){
			var ckArray = [];//存入需要检查是否改变的对象
			$(".isChange").each(function(){
				var desc = $(this).prev()[0].innerText;
				ckArray.push({name:this.name, value:this.value, desc:desc});
			});
			var odata = comm.getCache('infoManage', 'glqyxxgl-qyxtzcxx-info');
			var ndata = {};
			var ndatas = comm.cloneJSON(odata);
			var trs = "";
			var chg = [];
			for(var key =0; key < ckArray.length; key++){
				if(comm.removeNull(odata[ckArray[key].name]) != ckArray[key].value){
					trs+="<tr><td class=\"view_item\">"+ckArray[key].desc+"</td><td class=\"view_text\">"+comm.removeNull(odata[ckArray[key].name])+"</td><td class=\"view_text\">"+ckArray[key].value+"</td></tr>";
//					chg[ckArray[key].name] = {"updateValueOld":comm.removeNull(odata[ckArray[key].name]), "new":ckArray[key].value};
					chg.push({"logType":2,"updateField":ckArray[key].name,"updateFieldName":ckArray[key].desc,"updateValueOld":comm.removeNull(odata[ckArray[key].name]), "updateValueNew":ckArray[key].value});
					ndata[ckArray[key].name] = ckArray[key].value;
					ndatas[ckArray[key].name] = ckArray[key].value;
				}
			}
			if(trs == ""){
				comm.warn_alert(comm.lang("infoManage").noUpdate);
				return;
			}
			//提交
			$('#dialogBox > p').html(_.template(qyxtzcxx_xg_dialogTpl));
			$('#copTable tr:eq(1)').before(trs);
			self.initVerificationMode();
			$('#dialogBox').dialog({
				title:comm.lang("infoManage").ent_update_prompt,
				width:900,
				height:500,
				buttons:{
					'确定':function(){
						if(!self.validateViewMarkData().form()){
							return;
						}
						var verificationMode = $("#verificationMode").attr('optionValue');
						if(verificationMode != "1"){
							comm.warn_alert(comm.lang("infoManage").notSupportValidateMode);
							return ;
						}
						//验证双签
						//comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function(verifyRes){
						comm.getToken(null,function(resp){
							if(resp){
								ndata.dbUserName = $("#doubleUserName").val();
								ndata.loginPwd = comm.encrypt($('#doublePassword').val(),resp.data);
								ndata.secretKey = resp.data;
								ndata.optRemark = $("#viewMark").val();
								ndata.changeContent = JSON.stringify(chg);
								ndata.belongEntCustId = ndatas.entCustId;
								ndata.typeEum = "2";
								dataModoule.updateEntInfo(ndata, function(res){
									comm.alert({content:comm.lang("infoManage")[22000], callOk:function(){
										$('#dialogBox').dialog('destroy');
										self.initForm(ndatas);
									}});
								});
							}
						});
						
						//});
					},
					'取消':function(){
						$(this).dialog('destroy');
					}
				}
			});	
		},
		
		/**
		 * 初始化验证方式
		 */
		initVerificationMode : function(){
			comm.initSelect("#verificationMode", comm.lang("infoManage").verificationMode, null, null).change(function(e){
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
		/**
		 * 表单校验
		 */
		validateViewMarkData : function(){
			
			var validate = $("#confirm_dialog_form").validate({
				rules : {
					verificationMode : {
						required : true
					},
					viewMark : {
						rangelength : [0, 300]
					},
					doubleUserName : {
						required : false
					},
					doublePassword : {
						required : false
					},
				},
				messages : {
					verificationMode : {
						required : comm.lang("infoManage")[36050]
					},
					viewMark : {
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
	return self
});