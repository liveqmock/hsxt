define(["text!systemBusinessTpl/toolManage/deliveryAddr/modifyAddress.html",  
        'systemBusinessDat/systemBusiness'
], function(modifyAddressTpl,systemBusinessAjax){
	
	//收货地址
	var deliveryAddr = {
		//新增
		addAddr : function (addAddressTpl,callback){
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
				cacheUtil.findCacheProvinceByParent($.cookie('countryCode'),function(provArray){
					comm.initProvSelect('#province_slt_add', provArray, 230, null).change(function(e){
						cacheUtil.findCacheCityByParent($.cookie('countryCode'), $(this).attr('optionValue'), function(cityArray){
							comm.initCitySelect('#city_slt_add', cityArray, 230).selectListIndex(0);
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
					systemBusinessAjax.addDeliverAddress({addr:encodeURIComponent(JSON.stringify(param))},function(resp){
						if(resp){
							callback(resp);
							$('#addAddress_dialog').empty();	
							$("#addAddress_dialog").dialog('destroy');	
						}
					});
				});	
			});
		},	
		//删除或修改	
		delOrmodAddr : function (callback){
			$("#entAddrList a").each(function(){
			    $(this).on("click",function(){
			    	var addrId = $(this).attr('data-addrId');
			    	var type = $(this).attr('data-type');
			    	if(type == 'removeAddr'){
			    		comm.i_confirm('您确定删除该地址？',function(){
				    		systemBusinessAjax.removeDeliverAddress({addrId:addrId},function(resp){
				    			resp.type = type;
				    			callback(resp);
				    		});
						});
			    	}else if(type == 'modifyAddr'){
			    		systemBusinessAjax.queryDeliverAddress({addrId:addrId},function(resp){
				    		$('#modifyAddress_dialog').html(_.template(modifyAddressTpl,resp.data));	
							/*弹出框*/
							$( "#modifyAddress_dialog" ).dialog({
								title:"修改当前地址",
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
							
							//加载数据
							cacheUtil.findCacheProvinceByParent(null,function(provArray){
								comm.initProvSelect('#province_slt', provArray, 230, resp.data.provinceNo);
							});
							cacheUtil.findCacheCityByParent(null, resp.data.provinceNo, function(cityArray){
								comm.initCitySelect('#city_slt', cityArray, 230,resp.data.cityNo);
							});
							$('#province_slt').change(function(e){
								cacheUtil.findCacheCityByParent(null, $(this).attr('optionValue'), function(cityArray){
									comm.initCitySelect('#city_slt', cityArray, 230).selectListIndex(0);
								});
							});
							$('#saveAddress_btn').click(function(){
								if(!validateModifyAddress()){
									return;	
								}
								var isDefault =0;
								$("#modifyForm [name='isDefault']").each(function(){
									if($(this).attr("checked")){
										isDefault = $(this).val();
									}
				                });
								var param = {
										addrId:addrId,
										receiver:$('#consigneeName').val(),
										countryNo:$.cookie('countryCode'),
										provinceNo:$("#province_slt").attr("optionvalue"),
										cityNo:$("#city_slt").attr("optionvalue"),
										address:$('#fullAddress').val(),
										postCode:$('#modifyForm #postCode').val(),
										telphone:$('#modifyForm #telphone').val(),
										mobile:$('#modifyForm #mobile').val(),
										isDefault:isDefault
								};
								systemBusinessAjax.modifyDeliverAddress({addr:encodeURIComponent(JSON.stringify(param))},function(resp){
									if(resp){
										resp.type = type;
										callback(resp);
										$("#modifyAddress_dialog").dialog('destroy');
									}
								});
							});
				    	});
			    	}
			    });
			});
		}
	};
	
	function validateModifyAddress(){
		return comm.valid({
			formID : '#modifyForm',
			rules : {
				province_slt : {
					required : true	
				},
				city_slt : {
					required : true		
				},
				fullAddress : {
					required : true	,
					rangelength : [2, 128]
				},
				consigneeName : {
					required : true	,
					rangelength : [2, 20]
				},
				mobile : {
					required : true,
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
				province_slt : {
					required : comm.lang("systemBusiness").toolManage_provinceIsNotNull		
				},
				city_slt : {
					required : comm.lang("systemBusiness").toolManage_cityIsNotNull	
				},
				fullAddress : {
					required : comm.lang("systemBusiness").toolManage_fullAddressIsNotNull,
					rangelength : comm.lang("systemBusiness").fullAddressLentgh
				},
				consigneeName : {
					required : comm.lang("systemBusiness").toolManage_receiverIsNotNull,
					rangelength : comm.lang("systemBusiness").consigneeNameLentgh
				},
				mobile : {
					required : comm.lang("systemBusiness").toolManage_mobileIsNotNull
				},
				postCode : {
					digits:comm.lang("systemBusiness").toolManage_digits,
					maxlength: comm.lang("systemBusiness").toolManage_maxLenght
				}
			}
		});	
	}
	
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
					required : true,
					rangelength : [2, 128]
				},
				consigneeName_add : {
					required : true,
					rangelength : [2, 20]
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
					required : comm.lang("systemBusiness").toolManage_provinceIsNotNull			
				},
				city_slt_add : {
					required : comm.lang("systemBusiness").toolManage_cityIsNotNull		
				},
				fullAddress_add : {
					required : comm.lang("systemBusiness").toolManage_fullAddressIsNotNull,
					rangelength : comm.lang("systemBusiness").fullAddressLentgh
				},
				consigneeName_add : {
					required : comm.lang("systemBusiness").toolManage_receiverIsNotNull,
					rangelength : comm.lang("systemBusiness").consigneeNameLentgh
				},
				mobile : {
					required : comm.lang("systemBusiness").toolManage_mobileIsNotNull
				},
				postCode : {
					digits:comm.lang("systemBusiness").toolManage_digits,
					maxlength: comm.lang("systemBusiness").toolManage_maxLenght
				}
			}
		});	
	}
	
	return 	deliveryAddr;
});