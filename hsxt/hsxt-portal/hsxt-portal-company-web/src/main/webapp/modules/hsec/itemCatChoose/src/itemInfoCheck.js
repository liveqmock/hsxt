define(["hsec_tablePointSrc/tablePoint"], function(tablePoint) {
		var shippingBool = false;
		var checkInput = {
				inputCheckBool : function(type) {
					var itemName = $.trim($("#itemName").val());
					if(itemName == null || itemName == ""){
						tablePoint.tablePoint("商品名称不能为空!");
						return false;
					}
					
					var sellPoint = $.trim($("#sellPoint").val());
					if(sellPoint == null || sellPoint == ""){
						tablePoint.tablePoint("卖点信息不能为空!");
						return false;
					}
					
					//商品图片上传验证start
					var count = 0;
					if($(".multimage-gallery ul li").find(".preview").find("img").length < 1){
						tablePoint.tablePoint("请上传商品图片!");
						return false;
					}
					$.each($(".multimage-gallery ul li"),function(k,v){
						   if($(v).find(".preview").find("img").length > 0){
							   count++;
						   }
						   if($(v).hasClass("primaryItem")){
							   if($(v).find(".preview").find("img").length > 0){
								   count++;
							   }else{
								   count = -1;
								   tablePoint.tablePoint("请选择主图,橙色框内为主图设置!");
								   return false;
							   }
						   } 
					})
					if(count == -1){
						return false;
					}
					//end
					//商品详情验证start
					if($.trim($('#xheditor').val()) == null || $.trim($('#xheditor').val()) == ""){
						tablePoint.tablePoint("商品详情不能为空!");
						return false;
					}
					if($('#xheditor').val().length > 9000){
						tablePoint.tablePoint("商品详情当前输入长度"+$('#xheditor').val().length+",长度不能超过9000,请修改!");
						return false;
					}
					//end
					//运费模板start
					var isFreePostage = $('input:radio[name="isFreePostage"]:checked').val();
					   if(isFreePostage == 0){
			            	var postageId = $("#yunfeiMb").val();
			            	if(postageId == null || postageId == ""){
			            		tablePoint.tablePoint("请选择运费模板类型!");
								return false;
			            	}
			            }
					//end
					//抵扣劵验证start
					 var hasCoupon = $('input:radio[name="dikou"]:checked').val();
			         if(hasCoupon == 1){
			            	var dkq = $('input:radio[name="dkq"]:checked').val();
			            	if(dkq == null || dkq == ""){
			            		tablePoint.tablePoint("请选择使用抵扣劵类型!");
								return false;
			            	}
			         }
			        //end 
			        //关联营业点start
			     	var listShopIds = new Array();
				    $.each($(".listShopIds"),function(){
				          if ($(this).prop('checked') == true) {
				        	  listShopIds.push($(this).val());
				            }
				     });
				    if(listShopIds.length < 1){
				    	tablePoint.tablePoint("请选择上架此商品的营业点!");
						return false;
				    }
			        //end
				    if(type != 0){
				    //品牌验证start
					var brandBool = $("#itemPinpai").val();
					if(brandBool == "无" || brandBool == "没有"){
						$("#itemPinpai").val("");
						$("#itemPinpai").attr("valueId","");
					}
					//end
					//spu验证Start
					var spuBool = 0;
					var thisSPU = 0;
					var spuli = $("#itemspu").find(".spu");
					$.each(spuli,function(key,value){
						var inputstr = $.trim($(this).find("input[type='text']").val());
						if(inputstr == null || inputstr == ""){
							spuBool = 1;
							if(thisSPU == 0){
								thisSPU = $(this).height()*(key+1);
							}
							return false;
						}
					})
					if(spuBool == 1){
						tablePoint.tablePoint("请填写完整的商品属性!",function(){
							$('.search-cont').prop('scrollTop',$(".ppMoban").height()+thisSPU);
						});
						return false;
					}
					//end
					//SKU验证
					if($("#goodsCc").length > 0){
						var goodsCc = $.trim($("#goodsCc").val());
						if(goodsCc == null || goodsCc == ""){
								tablePoint.tablePoint("请选择商品类目!");
								return false;
						}
					}
					
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
						tablePoint.tablePoint("请填写商品属性！");
						return false;
					}else{
						var bool = false;
						var thisTr,thisTrHeight;
						var skuError = new Array();
						$.each($(skuCheck).find("tr"),function(k,v){
							var input1 = $(v).find("input[type='text']").eq(0);
							var input2 = $(v).find("input[type='text']").eq(1);
							var input3 = $(v).find("input[type='text']").eq(2);
							if(input1.val() != null && input1.val() != "" && input2.val() != null && input2.val() != ""){
								 if(input2.val() < 0.05){
									$(input2).parents("td").eq(0).addClass("bg_purple");
									skuError.push("第"+(Number(k)+1)+"行,积分值不能小于0.05!");
									bool = true;
								}else if(input1.val() / 10000 / 2 > input2.val()){
									$(input3).parents("td").eq(0).addClass("bg_purple");
									skuError.push("第"+(Number(k)+1)+"行,积分比例不能小于0.01%!");
									bool = true;
								}else{
									if($(input1).parents("td").eq(0).hasClass("bg_purple")){
										$(input1).parents("td").eq(0).removeClass("bg_purple");
									}
									if($(input2).parents("td").eq(0).hasClass("bg_purple")){
										$(input2).parents("td").eq(0).removeClass("bg_purple");
									}
									if($(input3).parents("td").eq(0).hasClass("bg_purple")){
										$(input3).parents("td").eq(0).removeClass("bg_purple");
									}
								}
							}else{
								if(input1.val() == null || input1.val() == ""){
									$(input1).parents("td").eq(0).addClass("bg_purple");
								}
								if(input2.val() == null || input2.val() == ""){
									$(input2).parents("td").eq(0).addClass("bg_purple");
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
										$(v).parents("td").eq(0).addClass("bg_purple");
										bool = true;
									}
								
								
								if($(v).is(".price1")){
									if(!($(v).val() > 0.33 && $(v).val() < 999999.99)){
										thisTrHeight = $(v).parents("tr").height();
										thisTr = $(this).parents("table").find("tr").index($(this).parents("tr"));
										$(v).parents("td").eq(0).addClass("bg_purple");
										bool = true;
									}
								}
								
								if($(v).is(".jifen1")){
									if($(v).val() < 0.05){
										thisTrHeight = $(v).parents("tr").height();
										thisTr = $(this).parents("table").find("tr").index($(this).parents("tr"));
										$(v).parents("td").eq(0).addClass("bg_purple");
										bool = true;
									}
								}
								
								if($(v).is(".jifen2")){
									if($(v).val() > 30){
										thisTrHeight = $(v).parents("tr").height();
										thisTr = $(this).parents("table").find("tr").index($(this).parents("tr"));
										$(v).parents("td").eq(0).addClass("bg_purple");
										bool = true;
									}
								}
									
							})
							if(bool){
								tablePoint.tablePoint("商品属性输入错误!请更正!<br/>1.属性值不能为空。<br/>2.价格输入范围只能0.34~999999.99.<br/>3.积分值输入范围不能小于0.05.<br/>4.积分百分比输入范围不能大于30%.");
							}
						}
						if(bool){
						
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
								tablePoint.tablePoint("请至少上传一张商品属性的主图片！");
								bool = true;
								return false;
							}
						})
						if(bool){
							var imgheight = imgIndex * ($(skuImgCheck).eq(0).height()+20);
							$('#itemMain').prop('scrollTop',$("#itemspu").height() + $("#"+skuMain).height() + $("#"+createTable).height() + imgheight);
							return false;
						}
					}
				}	
					//end

				    return true;
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
						min:1
					},
					"postage": {
						required: true,
						max:999999 ,
						min:0,
						digits:true
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
						max:"包邮价取值范围:1~999999",
						min:"包邮价取值范围:1~999999"
					},
					"postage": {
						required: "请输入运费",
						max:"运费取值范围:0~999999",
						min:"运费取值范围:0~999999",
						digits:"请输入正整数"
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
		}	
		}
			return checkInput;
});

