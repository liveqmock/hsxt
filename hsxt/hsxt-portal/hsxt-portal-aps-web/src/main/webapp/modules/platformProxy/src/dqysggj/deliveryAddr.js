define(['text!platformProxyTpl/dqysggj/addAddress.html',  
        'platformProxyDat/platformProxy'
], function(addAddressTpl,systemBusinessAjax){
	//收货地址
	var deliveryAddr = {
		//新增
		addAddr : function (companyCustId,callback){
			$('#add_address_btn').click(function(){
				$('#addAddress_dialog').html(_.template(addAddressTpl));	
				/*弹出框*/
				$( "#addAddress_dialog" ).dialog({
					title:"添加新地址",
					width:"800",
					height:"580",
					modal:true,
					buttons:{ 
						"关闭":function(){
							$('input').tooltip().tooltip('destroy');
							$('textarea').tooltip().tooltip('destroy');
							$( this ).dialog( "destroy" );
						}
					}
				});
				//加载地区数据
				comm.initProvSelect('#province_slt_add', {}, 230, null);
				comm.initCitySelect('#city_slt_add', {}, 230, null);
				//加载数据
				cacheUtil.findCacheSystemInfo(function(sysRes){
					cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function(provArray){
						comm.initProvSelect('#province_slt_add', provArray, 230, null).change(function(e){
							cacheUtil.findCacheCityByParent(sysRes.countryNo, $(this).attr('optionValue'), function(cityArray){
								comm.initCitySelect('#city_slt_add', cityArray, 230).selectListIndex(0);
							});
						});
					});
				});
				
				$('#saveAddress_btn_add').click(function(){
					if(!validateAddAddress()){
						return;	
					}
					var isDefault =0;
					$("#addForm [name='isDefault']").each(function(){
						if($(this).attr("checked")){
							isDefault = $(this).val();
						}
	                });
					var param = {
							receiver:$('#consigneeName_add').val(),
							countryNo:$.cookie('countryCode'),
							provinceNo:$("#province_slt_add").attr("optionvalue"),
							cityNo:$("#city_slt_add").attr("optionvalue"),
							address:$('#fullAddress_add').val(),
							telphone:$('#addForm #telphone').val(),
							mobile:$('#addForm #mobile').val(),
							postCode:$('#addForm #postCode').val(),
							isDefault:isDefault
					};
					systemBusinessAjax.addDeliverAddress({companyCustId:companyCustId,addr:encodeURIComponent(JSON.stringify(param))},function(resp){
						if(resp){
							callback(resp);
							$('#addAddress_dialog').empty();	
							$("#addAddress_dialog").dialog('destroy');	
						}
					});
				});	
			});
		},	
	};
	function validateAddAddress(){
		return comm.valid({
			formID : '#addForm',
			rules : {
				province_slt_add : {
					required : true	
				},
				city_slt_add : {
					required : true		
				},
				fullAddress_add : {
					required : true		
				},
				consigneeName_add : {
					required : true		
				},
				mobile : {
					required : true	,
					mobileNo: true
				},
				telphone : {
					telphone: true
				},
				postCode : {
					digits:true,
					maxlength: 6
				}
			},
			messages : {
				province_slt_add : {
					required : comm.lang("platformProxy").toolManage_provinceIsNotNull			
				},
				city_slt_add : {
					required : comm.lang("platformProxy").toolManage_cityIsNotNull		
				},
				fullAddress_add : {
					required : comm.lang("platformProxy").toolManage_fullAddressIsNotNull	
				},
				consigneeName_add : {
					required : comm.lang("platformProxy").toolManage_receiverIsNotNull	
				},
				mobile : {
					required : comm.lang("platformProxy").toolManage_mobileIsNotNull
				},
				postCode : {
					digits:comm.lang("platformProxy").toolManage_digits,
					maxlength: comm.lang("platformProxy").toolManage_maxLenght
				}
			}
		});	
	}
	
	return 	deliveryAddr;
});