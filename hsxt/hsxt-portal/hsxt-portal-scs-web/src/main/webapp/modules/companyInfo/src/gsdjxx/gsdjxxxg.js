define(['text!companyInfoTpl/gsdjxx/gsdjxxxg.html',
        'text!companyInfoTpl/gsdjxx/map.html',
        'companyInfoDat/gsdjxx/gsdjxx',
        'companyInfoSrc/gsdjxx/map',
		'companyInfoLan'],function(gsdjxxxgTpl, mapTpl, dataModoule, maps){
	var self = {
		showPage : function(){
			self.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#busibox').html(_.template(gsdjxxxgTpl, data));
			//绑定取消
			$('#backBt_gongshang').click(function(){
				comm.confirm({
					imgFlag : true,
					imgClass : 'tips_ques',
					content : comm.lang("companyInfo").cancel_tips,
					callOk : function(){
						$('#qyxx_gsdjxx').click();
					} 	
				});
			});
			
			//绑定保存
			$('#submitBt_gongshang').click(function(){
				self.saveData();
			});
			
			//绑定地图标注
			$('#gsdjxx_map').click(function(){
				$("#mapTpl").html(_.template(mapTpl));
		 		$('#gsdjxx_map_dialog').dialog({
		 			title:'标注企业地图位置' ,
		 			width:800,
		 			height:480,
		 			buttons:{
		 				'取消':function(){
		 					$("#gsdjxx_map_dialog").dialog('destroy');
		 				},
		 				'保存':function(){
		 					self.saveLngData();
		 				}
		 			}	 			
		 		});
		 		maps.showPage();
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
					self.initForm(res.data);
				});
			}else{
				self.initForm(entAllInfo);
			}
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#qygsdjxx_form").validate({
				 rules : {
					 nature : {
						required : true,
						rangelength:[2, 20]
					},
					buildDate : {
						required : true,
						date : true
					},
					businessScope : {
						rangelength:[0, 300]
					},
					endDate : {
						rangelength:[0, 50]
					}
				},
				messages : {
					nature : {
						required : comm.lang("companyInfo")[32673],
						rangelength:comm.lang("companyInfo")[32674]
					},
					buildDate : {
						required : comm.lang("companyInfo")[32675],
						date : comm.lang("companyInfo")[32676]
					},
					businessScope : {
						rangelength: comm.lang("companyInfo")[32628]
					},
					endDate : {
						rangelength:comm.lang("companyInfo")[32678]
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
		 * 保存数据
		 */
		saveData : function(){
			if(!self.validateData().form()){
				return;
			}
			var params = {};
			params.nature = $("#nature").val();//企业类型
			params.buildDate = $("#buildDate").val();//成立日期
			params.businessScope = $("#businessScope").val();//经营范围
			params.endDate = $("#endDate").val();//营业期限
			//params.legalPersonId = $("#legalPersonId").val();//法人代表证件号码
			//params.officeTel = $("#contactPhone").val();//联系电话
			//params.credentialType = $("#credentialType").attr('optionValue');//法人代表证件类型
			dataModoule.updateEntExtInfo(params, function(res){
				//更新缓存
				comm.delCache("companyInfo", "entAllInfo");
				comm.alert({content:comm.lang("companyInfo")[22000], callOk:function(){
					$('#qyxx_gsdjxx').click();
				}});
			});
		},
		/**
		 * 保存经纬度
		 */
		saveLngData : function(){
			if(comm.isEmpty($("#latitude").val())){
				comm.warn_alert(comm.lang("companyInfo").lagIsNull);
				return;
			}
			var params = {};
			params.longitude = $("#longitude").val();//经度
			params.latitude = $("#latitude").val();//纬度
			dataModoule.updateLagInfo(params, function(res){
				//更新缓存数据
				var datas = comm.getCache("companyInfo", "entAllInfo");
				datas.longitude = params.longitude;
				datas.latitude = params.latitude;
				comm.alert({content:comm.lang("companyInfo").markSuccess, callOk:function(){
					$("#gsdjxx_map_dialog").dialog('destroy');
				}});
			});
		}
	};
	return self;
}); 

 