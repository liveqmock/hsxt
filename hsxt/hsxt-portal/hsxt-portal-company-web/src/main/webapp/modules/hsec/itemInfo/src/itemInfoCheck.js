define(["hsec_tablePointSrc/tablePoint"], function(tablePoint) {
		var shippingBool = false;
		var checkInput = {
				validate : function(check) {
					$("#item_add_form").validate({
						onkeyup: function(element) { $(element).valid(); },
						//设置验证规则   
						rules: {
							"name": {
								required: true,
								maxlength:40
							},
							"sellPoint": {
								required: true,
								maxlength:100
							},
							"brandName": {
								maxlength:25
							},
							"originalPrice": {
								required: true,
								max:999999.99,
								min:0,
								num2:true
							},
							"code": {
								maxlength:25
							},
							"factoryName": {
								maxlength:100
							},
							"guanlian": {
								required: true
							},
							"itemSummary": {
								maxlength:150
							}
						},
						//设置错误信息  
						messages: {
							"name": {
								required: "",
								maxlength: ""
							},
							"sellPoint": {
								required: "",
								maxlength: ""
							},
							"brandName": {
								maxlength: "长度不能大于25个字符"
							},
							"originalPrice": {
								required: "请输入价格",
								max: "价格不能超过999999.99",
								min: "价格不能低于0",
								num2:"小数位不能超过两位"
							},
							"code": {
								maxlength: "长度不能大于25个字符"
							},
							"factoryName": {
								maxlength: "长度不能大于100个字符"
							},
							"guanlian": {
								required: "请选择关联营业点"
							},
							"itemSummary": {
								maxlength: ""
							}
						},
						submitHandler:function(){
							if(check.update == 1){
								check["update"] = 0;
								return false;
							}
							var bool = checkInput.inputCheckOne(0);
							//提交
							if(bool){
								check.shop.saveItems();
							}
							return false;
						},
						errorElement:"span",
						errorPlacement: function(error, element){
							if(element.is(':checkbox')){
								error.appendTo(element.parent().parent().parent().next().addClass("red"));
							}else{
								error.addClass("red");
								error.attr("style","padding-left:10px");
								error.appendTo(element.parent());
							}
						}
					});
					
					//是否包邮
					$("#noShipping").unbind().on('click',function(){
						if($(this).is(':checked') == true){
								$("#checkShipping").hide();
								$("#shippingSelect:first option:first").attr("selected","selected");
							}else{
								$("#checkShipping").show();
						}
					})
					$("input[name=hasCoupon]").unbind().on('click',function(){
						$("#dikouquan").toggle();
					});
					$(".spucheck").unbind().on('click',function(){
						var spuError = $(this).find(".spuError");
						$(spuError).html("");
					});
					
					$("#item_add_form").submit();
				},
				response : function() {
					var navObj = {};
					require(["spweb/modules/itemInfo/src/itemList/itemListInfoHead"],function(src){	
						navObj.shop = src;
						navObj.shop.bindData();	
						 
					})
				},
				shippingCheck : function() {
						shippingBool = false;
					$("#shippingForm").validate({
						onkeyup: function(element) { $(element).valid(); },
						//设置验证规则   
						rules: {
							"name": {
								required: true,
								maxlength:17
							},
							"charge": {
								digits:true,
								max:999999,
								min:0
							},
							"postage": {
								required: true,
								max:999999.99,
								min:0
							},
							"remark": {
								maxlength:50
							}
						},
						//设置错误信息  
						messages: {
							"name": {
								required: "请输入运费模板名称",
								maxlength:"长度不能大于17个字符"
							},
							"charge": {
								digits:"请输入正整数",
								max:"包邮价取值范围:0~999999",
								min:"包邮价取值范围:0~999999"
							},
							"postage": {
								required: "请输入运费",
								max:"运费取值范围:0~999999.99",
								min:"运费取值范围:0~999999.99"
							},
							"remark": {
								maxlength:"长度不能大于50个字符"
							}
						},
						submitHandler:function(){
							shippingBool = true;
							return false;
						},
						errorElement:"span",
						errorPlacement: function(error, element){
							error.appendTo(element.parent().addClass("red"));
						}
					});
				},
				submitShipping : function(){
					$("#shippingForm").submit();
					 return shippingBool;
				},
				inputCheckOne : function(num){
					if(num >= 1 || num == 0){
						var itemName = $.trim($("#itemName").val());
						if(itemName == null || itemName == ""){
							tablePoint.tablePoint("商品名称不能为空,请输入商品名称.");
							return false;
						}
						var sellPoint = $.trim($("#sellPoint").val());
						if(sellPoint == null || sellPoint == ""){
							tablePoint.tablePoint("卖点信息不能为空,请输入卖点信息.");
							return false;
						}
						
						var brand = $.trim($("#brand").val());
						if(("无,没有").indexOf(brand) > -1){
							$("#brand").val("");
						}
						
						var originalPrice = $.trim($("#originalPrice").val());
						if(originalPrice == null || originalPrice == "" || originalPrice > 999999.99 || originalPrice <= 0){
							tablePoint.tablePoint("价格不符合规范,请重新输入.");
							return false;
						}
						//商品图片上传验证
						var count = 0;
						$.each($(".multimage-gallery ul li"),function(k,v){
							   if($(v).find(".preview").find("img").length > 0){
								   count++;
							   }
							   if($(v).hasClass("primaryItem")){
								   if($(v).find(".preview").find("img").length > 0){
									   count++;
								   }else{
									   count = -1;
									   $("#itemPhotoCheck").html("请选择主图,橙色框内为主图设置!");
									   return false;
								   }
							   } 
						})
						if(count == -1){
							return false;
						}
						if(count < 1){
							$("#itemPhotoCheck").html("请上传商品图片!如:JPG,JPEG,PNG,GIF,BMP,不超过1024KB,不超过"+$(".multimage-gallery ul li").length+"张!建议图片大于700X700!");
							return false;
					    }
				}
				if(num >= 2 || num == 0){	
					var spuli = $("#itemspu").find("li");
					var spubool = true;
					var spuIndex = 0;
					$.each(spuli,function(key,value){
						spuIndex = $(this).parents("#itemspu").find("li").index($(value));
						var spuError = $(value).find(".spuError");
						if($(value).attr("displayType") == 1){
							var text = $(value).find("input[type='text']");
							var value = $.trim($(text).val());
							if(value == "" || value == null || value.length > 30){
								$(spuError).html("属性不能为空.");
								spubool = false;
								return false;
							}else{
								$(spuError).html("");
							}
						}else if($(value).attr("displayType") == 2){
											
						}else if($(value).attr("displayType") == 3){
							var bool = $(value).find("input[type='checkbox']:checked");
							if(bool.length < 1){
								$(spuError).html("请选择！");
								spubool = false;
								return false;
							}else{
								$(spuError).html("");
							}	
						}else if($(value).attr("displayType") == 4){
							var bool = $(value).find("input[type='radio']:checked");
							if(bool.length < 1){
								$(spuError).html("请选择！");
								spubool = false;
								return false;
							}else{
								$(spuError).html("");
							}
						}
					})
					if(spubool == false){
						clickNode(1);
						var heiSpu = spuIndex * (spuli.eq(0).height() + 10);
						$('#itemMain').prop('scrollTop',heiSpu);
						tablePoint.tablePoint("商品属性未填全,请补全属性.");
						return false;
					}
					//SKU验证
					var tablesku = "tablesku";
					var skuMain = "skuMain";
					var createTable = "createTable";
					var imgLoad = "imgLoad";
					if($("#skuTabs").find("div").length > 1){
						$.each($("#skuTabs").find("div"),function(k,v){
							if($(v).attr("isshow") == 2 && $(v).attr("aria-hidden") == "false"){
									tablesku = "tablesku2";
									skuMain = "skuMainAdd";
									createTable = "createTableAdd";
									imgLoad = "imgLoadAdd";
							}
						})
					}
					var skuCheck = $("#"+tablesku+" tbody:eq(1)");
					if(skuCheck.length < 1){
						clickNode(1);
						tablePoint.tablePoint("商品属性有值不全!请补全属性!");
						return false;
					}else{
						var bool = false;
						var thisTr,thisTrHeight;
						var skuError = new Array();
						$.each($(skuCheck).find("tr"),function(k,v){
							var input1 = $(v).find("input[type='text']").eq(0);
							var input2 = $(v).find("input[type='text']").eq(1);
							if(input1.val() != null && input1.val() != "" && input2.val() != null && input2.val() != ""){
								if(input1.val() * 0.0001 > input2.val()){
									$(input2).parents("td").addClass("bg_purple");
									skuError.push("第"+(Number(k)+1)+"行,积分值不得小于0.01%!");
									bool = true;
								}else{
									if($(input1).parents("td").hasClass("bg_purple")){
										$(input1).parents("td").removeClass("bg_purple");
									}
									if($(input2).parents("td").hasClass("bg_purple")){
										$(input2).parents("td").removeClass("bg_purple");
									}
								}
							}else{
								if(input1.val() == null || input1.val() == ""){
									$(input1).parents("td").addClass("bg_purple");
								}
								if(input2.val() == null || input2.val() == ""){
									$(input2).parents("td").addClass("bg_purple");
								}
								skuError.push("第"+(Number(k)+1)+"行,属性有值未填,请补全!");
								bool = true;
							}
						})
						if(bool){
							var errorStr = "";
							$.each(skuError,function(k,v){
								errorStr += "<p style='padding-left:26%;text-align:left;'>"+v+"</p>";
							})
							tablePoint.tablePoint("<span>"+errorStr+"</span>");
						}
						if(bool == false){
							$.each($(skuCheck).find("input[type='text']"),function(k,v){
									if($(v).val() == null  || $(v).val() == ""){
										thisTrHeight = $(v).parents("tr").height();
										thisTr = $(this).parents("table").find("tr").index($(this).parents("tr"));
										$(v).parents("td").addClass("bg_purple");
										bool = true;
									}
								
								
								if($(v).is(".price1")){
									if(!($(v).val() > 0 && $(v).val() < 999999.99)){
										thisTrHeight = $(v).parents("tr").height();
										thisTr = $(this).parents("table").find("tr").index($(this).parents("tr"));
										$(v).parents("td").addClass("bg_purple");
										bool = true;
									}
								}
								
								if($(v).is(".jifen1")){
									if($(v).val() < 0.05){
										thisTrHeight = $(v).parents("tr").height();
										thisTr = $(this).parents("table").find("tr").index($(this).parents("tr"));
										$(v).parents("td").addClass("bg_purple");
										bool = true;
									}
								}
								
								if($(v).is(".jifen2")){
									if($(v).val() > 30){
										thisTrHeight = $(v).parents("tr").height();
										thisTr = $(this).parents("table").find("tr").index($(this).parents("tr"));
										$(v).parents("td").addClass("bg_purple");
										bool = true;
									}
								}
									
							})
							if(bool){
								tablePoint.tablePoint("商品属性输入错误!请更正!<br/>1.属性值不能为空。<br/>2.价格输入范围只能0~999999.99.<br/>3.积分值输入范围不能小于0.05.<br/>4.积分百分比输入范围不能大于30%.");
							}
						}
						if(bool){
							clickNode(1);
							$('#itemMain').prop('scrollTop',$("#itemspu").height()+$("#"+skuMain).height()+thisTr*thisTrHeight);
							return false;
						}else{
							$(skuCheck).find("td").removeClass("bg_purple");
						}
					}
					//SKU图片验证
					var skuImgCheck = $("#"+imgLoad+" li");
					if(skuImgCheck.length > 0){
						var imgIndex = 0;
						var bool = false;
						$.each($(skuImgCheck).find("b"),function(k,v){
							imgIndex = $(this).parents("#"+imgLoad).find("li").index($(this).parents("li"));
							if($(v).find("img").length < 1){
								tablePoint.tablePoint("商品属性图片未上传!如:JPG,JPEG,PNG,GIF,BMP,不超过1024KB,不超过5张!建议图片大于700X700!");
								bool = true;
								return false;
							}
						})
						if(bool){
							clickNode(1);
							var imgheight = imgIndex * ($(skuImgCheck).eq(0).height()+20);
							$('#itemMain').prop('scrollTop',$("#itemspu").height() + $("#"+skuMain).height() + $("#"+createTable).height() + imgheight);
							return false;
						}
					}
				}	
				if(num >= 3 || num == 0){	
					// 抵扣券
					if($("input[name=hasCoupon]:checked").length>0 && $("input[name=dkq]:checked").length==0){
						clickNode(2);
						tablePoint.tablePoint("请选择使用的抵扣券!");
						return false;
					}
					//运费验证
					var no = $("#noShipping").is(':checked');
					if(no == false){
						if($("#shippingSelect").val() == null || $("#shippingSelect").val() == ""){
							clickNode(2);
							tablePoint.tablePoint("请选择运费类型!");
							return false;
						}
					}
					//主营分类验证
					if($(".new_del").length < 1){
						clickNode(2);
						tablePoint.tablePoint("请添加主营分类!");
						return false;
					}
					//营业点
					var listShopIdsBool = true;
					var storeSelect = $(".storeSelect");
					$.each($(storeSelect).find("span"),function(k,v){
						if($(v).find(".listShopIds").is(':checked')){
							listShopIdsBool = false;
							return false;
						}
					})
					if(listShopIdsBool){
						clickNode(2);
						tablePoint.tablePoint("请选择营业点!");
						return false;
					}
				}
				if(num == 0){
					//商品详情验证
						if($.trim($('#xheditor').val()) == null || $.trim($('#xheditor').val()) == ""){
							tablePoint.tablePoint("商品详情不能为空!");
							return false;
						}
						if($('#xheditor').val().length > 9000){
							tablePoint.tablePoint("商品详情过长,请修改后,重新保存!");
							return false;
						}
				    }
				return true;
				}
			}
			   function clickNode(num){
		    	 $.each($("ul.tabList li span"),function(k,v){
		    		 if(k == num){
		    			 $(this).addClass("active").parent().siblings('li').children('span').removeClass("active");
					 	 $('#goods_page1,#goods_page2,#goods_page3,#goods_page4').hide().eq($('ul.tabList li span').index(this)).show();
		    		 }
		    	 })
		       }
			return checkInput;
});

