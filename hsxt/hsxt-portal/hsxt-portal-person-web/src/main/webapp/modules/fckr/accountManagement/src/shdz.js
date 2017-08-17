define(["text!fckr_accountManagementTpl/shdz.html",
        "text!fckr_accountManagementTpl/addressEdit.html",
        "fckr_accountManagementDat/accountManagement"], function (aTpl,aTplEdit,accountManagementData) {
	var shdz = {
		
		show : function(){
			/**
			 * 下拉框验证 add by peter.li 
			 * penggs 
			 */
			jQuery.validator.addMethod("selectNone", function(value, element) { 
				if(element.style.display=="none"){
					return true;
				}
				return this.optional(element) || value != "-1"; 
			}, "必须选择一项");
			/**
			 * 手机号码验证 
			 * penggs
			 */
			jQuery.validator.addMethod("mobileZN", function(value, element) { 
			    var length = value.length; 
			    //var mobile = /^(((13[0-9])|(15[0-9])|(18[0-9]))+\d{8})$/
			    var mobile =  /^1\d{10}$/g;
			    return this.optional(element) || (length == 11 && mobile.test(value)); 
			}, "手机号码格式错误"); 

			/**
			 * 电话号码验证 (中国的电话验证)
			 * penggs
			 */
			jQuery.validator.addMethod("phoneZN", function(value, element) { 
				var tel = /^\d{3,5}-?\d{7,9}$/; 
				return this.optional(element) || (tel.test(value)); 
			}, "电话号码格式错误"); 
			
			//加载初始化界面
			accountManagementData.findReceiveAddrByCustId(null,function(response){
				//加载页面
				var html = _.template(aTpl,response);
				$("#myhs_zhgl_box").html(html);
				shdz.validate();
				var html1 = _.template(aTplEdit,response);
				$("#updateAddress").html(html1);
				
				
				shdz.initSlect();
				//删除收货地址
				$(".delete").click(function(){
					var id = $(this).attr("val");
					accountManagementData.removeAddr({addrId:id},function(response) {
						comm.yes_alert('删除收货地址信息成功');
						shdz.show();
					});
				});
				
				//设置默认收货地址
				$(".setDefult").click(function(){
					var id=$(this).attr("val");
					accountManagementData.setDefaultAddr({addrId:id},function(response) {
						comm.yes_alert('设置默认收货地址信息成功');
						shdz.show();
					});
				});
				
				//查询收货地址详情
				$(".edit").click(function(){
					var id = $(this).attr("val");
					accountManagementData.findReceiveAddrInfo({addrId:id},function(response) {
						
						var html = _.template(aTplEdit,response);
						$("#updateAddress").html(html);
						//根据国家获取下属省份
						cacheUtil.findCacheProvinceByParent(response.data.countryNo,function(provinceList){
							//加载省份信息
							comm.initOption('#provinceNo', provinceList , 'provinceName','provinceNo',false);
							$("#provinceNo").val(response.data.provinceNo);
						});
						
						//加载城市信息
						cacheUtil.findCacheCityByParent(response.data.countryNo, response.data.provinceNo, function(cityArray){
							//加载省份信息
							comm.initOption('#cityNo', cityArray , 'cityName','cityNo',false);
							$("#cityNo").val(response.data.cityNo);
						});
						//点击保存
						$('#bc_btn').click(function(){
							var valid = shdz.validate();
							if (!valid.form()) {
								return;
							}
							var param = $("#addressForm").serializeJson();
							param["countryNo"] = $("#countryCodeHid").val(); 
							param["isDefault"] = $("#isDefault").is(":checked") ? "1": "0";
							//执行修改方法
							accountManagementData.updateReceiveAddr(param,function(){
								if($.trim($("#addrId").val()) != ''){
									comm.yes_alert('修改收货地址信息成功');
								}else{
									comm.yes_alert('添加收货地址信息成功');
								}
								shdz.show();
							});
						});
						$("#provinceNo").change(function(){
							var countryCodeHid = $("#countryCodeHid").val();
							var provinceCode = $('#provinceNo').val();
							$("#cityNo").empty();
							//加载城市信息
							cacheUtil.findCacheCityByParent(countryCodeHid, provinceCode, function(cityArray){
								//加载省份信息
								comm.initOption('#cityNo', cityArray , 'cityName','cityNo',false);
							});
						});
					});
				});
				
				$("#provinceNo").change(function(){
					var countryCodeHid = $("#countryCodeHid").val();
					var provinceCode = $('#provinceNo').val();
					$("#cityNo").empty();
					//加载城市信息
					cacheUtil.findCacheCityByParent(countryCodeHid, provinceCode, function(cityArray){
						//加载省份信息
						comm.initOption('#cityNo', cityArray , 'cityName','cityNo',false);
					});
				});
				
				//点击保存
				$('#bc_btn').click(function(){
					var valid = shdz.validate();
					if (!valid.form()) {
						return;
					}
					
					var param = $("#addressForm").serializeJson();
					param["countryNo"] = $("#countryCodeHid").val(); 
					param["isDefault"] = $("#isDefault").is(":checked") ? "1": "0";
					//执行修改方法
					accountManagementData.updateReceiveAddr(param,function(){
						if($.trim($("#addrId").val()) != ''){
							comm.yes_alert('修改收货地址信息成功');
						}else{
							comm.yes_alert('添加收货地址信息成功');
						}
						shdz.show();
					});
				});
			});
		},
		initSlect :function(){
			//获取平台基本信息 
			cacheUtil.findCacheSystemInfo(function(systemInfo){
				//界面数据加载
				$("#countryCodeHid").val(systemInfo.countryNo);				//保存当前国家
				
				//根据国家获取下属省份
				cacheUtil.findCacheProvinceByParent(systemInfo.countryNo,function(provinceList){
					//加载省份信息
					comm.initOption('#provinceNo', provinceList , 'provinceName','provinceNo',false);
					
					//加载城市信息
					cacheUtil.findCacheCityByParent(systemInfo.countryNo, $('#province').val(), function(cityArray){
						//加载省份信息
						comm.initOption('#cityNo', cityArray , 'cityName','cityNo',false);
					});
				});
			});
		},
		validate : function(){
			return $("#addressForm").validate({
				rules : {
					receiver: {
						required: true,
						rangelength:[2,30]
					},
					mobile:　{required: {depends:function(){return ($('input[name=telphone]').val().length <= 0);  }  },mobileZN:true},
					telphone:{required: {depends:function(){return ($('input[name=mobile]').val().length <= 0);  }  },phoneZN:true},
					provinceNo:{selectNone:true},
					cityNo:{selectNone:true},
					address:{required: true},
					postCode:{required:true,zipCode:true}
				},
				messages : {
					receiver: {required: "请填写收货人",rangelength:"收货人至少输入2个字符"},
					mobile:　{required: "手机或固定电话至少填写一项"},
					telphone :{required: "手机或固定电话至少填写一项"},
					address: {required: "请填写详细地址"},
					postCode:{required: "请填写邮编"}
				},
				errorElement:"label",
				errorPlacement: function(error, element){
					if (element.attr("name") == "mobile" || element.attr("name") == "telphone" ){
						error.appendTo($('input[name=telphone]').next())
			         }else{  
			        	 error.appendTo(element.parent().find(".errorInfo"));
			         }  
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		}
	};
	return shdz
});
