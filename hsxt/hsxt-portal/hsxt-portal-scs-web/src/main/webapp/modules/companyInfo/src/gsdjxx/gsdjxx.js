define(['text!companyInfoTpl/gsdjxx/gsdjxx.html',
        'companyInfoDat/gsdjxx/gsdjxx',
        'text!companyInfoTpl/gsdjxx/map.html',
        'companyInfoSrc/gsdjxx/map',
		'companyInfoLan'],function(gsdjxxTpl, dataModoule, mapTpl, maps){
	var self =  {
		showPage : function(){
			self.initForm();
			self.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#busibox').html(_.template(gsdjxxTpl, data));
			if(data){
				$("#legalCreTypeText").html(comm.getNameByEnumId(data.credentialType, comm.lang("common").creTypes));
				var entAddr = data.entRegAddr;
				if(entAddr.length>15){
					entAddr = entAddr.substring(0,15)+'...';
				}
				$('#gsdjEntAddr').text(entAddr);
				$('#gsdjEntAddr').attr('title',data.entRegAddr);
				comm.initPicPreview("#busi_license_img", data.busiLicenseImg, "");
				$("#busi_license_img").attr("src", comm.getFsServerUrl(data.busiLicenseImg));
			}
			$('#ModifyBt_gongshang').click(function(){
				$('#qyxx_gsdjxxxg').click();
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
		 * 保存经纬度
		 */
		saveLngData : function(){
			if($("#latitude").val() == ""){
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

 