define(["hsec_tablePointSrc/tablePoint",
        "text!hsec_shopInfoFoodTpl/shopInfoFoodUpdate/shopInfoFoodUpdate.html",
        "text!hsec_shopInfoFoodTpl/baiduMap/baiduMap.html",
        "hsec_shopInfoFoodDat/shopFood",
        "hsec_shopInfoFoodSrc/shopInfoFoodCheck",
        "text!hsec_shopInfoFoodTpl/shopInfoFoodMain/tab.html",
        "hsec_tablePointSrc/jquery.charcount"],
		function(tablePoint,shopInfoFoodAddTpl,baiduMapTpl,shopFoodAjax,shopInfoFoodCheckJs,tabTpl) {
			var Location;//商圈
			var shopMain = null;
			var shop = {
				bindFootInit : function(info,shopId) {
						shopMain = info;
				shopFoodAjax.shopFoodInfo("shopId="+shopId,function(response) {
						Location = response.data.Location;
						try{
							if(response.data.onService != null){
								response.data["serviceStatus"] = response.data.onService[0].status;
							}else{
								response.data["serviceStatus"] = 0;
							}
						}catch(e){
								response.data["serviceStatus"] = 0;
						}	
						$('.operationsInner').html(_.template(tabTpl));
						$("#wsscgl_yydlb").find("a").removeClass("active").addClass("stdlb");
						$(".tabList").append('<li><a class="active">新增/修改营业点</a></li>');
						var html = _.template(shopInfoFoodAddTpl, response.data);
						$("#busibox").html(html);
						shop.bindClick();
						shop.bindFuWuClick();
						shop.submit();
					})

				},
				bindClick : function(){
					
					/*$('#openingHoursRemark').charcount({
						maxLength: 50,
						preventOverage: true,
						position : 'after'
					});
					$('#introduce').charcount({
						maxLength: 200,
						preventOverage: true,
						position : 'after'
					});
					$('#qiTaStr').charcount({
						maxLength: 50,
						preventOverage: true,
						position : 'after'
					});*/
				$("#openingHoursRemark").on("keyup keypress charcount change",function(){
					var lt = $.trim($(this).val()).length;
					var max_lt = $(this).attr("maxlength");
					if(max_lt < lt){
						$(this).val($(this).val().substring(0,50));
					}
					$(this).parent().parent().find("td .red").html( max_lt-lt>=0?max_lt-lt:0)
				}).trigger('charcount');
				$("#introduce").on("keyup keypress charcount change",function(){
					var lt = $.trim($(this).val()).length;
					var max_lt = $(this).attr("maxlength");
					if(max_lt < lt){
						$(this).val($(this).val().substring(0,200));
					}
					$(this).parent().parent().find("td .red").html( max_lt-lt>=0?max_lt-lt:0)
				}).trigger('charcount');
				$("#qiTaStr").on("keyup keypress charcount change",function(){
					var lt = $.trim($(this).val()).length;
					var max_lt = $(this).attr("maxlength");
					if(max_lt < lt){
						$(this).val($(this).val().substring(0,50));
					}
					$(this).parent().parent().find("td .inp").html( max_lt-lt>=0?max_lt-lt:0)
				}).trigger('charcount');
				
					$(".yw-shopService").unbind().on("click",function(){
						require(["hsec_provisioningFoodSrc/tab"],function(src){
							src.showPage();//当服务为：0未开通，2暂停时，提示开通跳转  modifly by zhanghh 2016-05-14
						})
					})
					
//					$("#stdbj").on("click",".stdlb",function(){
//						shopMain.bindData();
//					})
					$(".tabList").on("click",".stdlb",function(){
						$(this).addClass("active");
						$("#wsscgl_yydlb").next().remove();
						shopMain.bindData();
					})
					
					$("#zhoubian").on("mouseleave",function(){
						$("#zhoubianDiv").hide();
					})
					
					$("#xinxiMain").on("click","li>a",function(){
						if($(this).hasClass("jbxx")){
							$("#cy-mjfw").hide();
							$("#cy-jbxx").show();
						}else{
//							var checkBool = shopInfoFoodCheckJs.foodInputCheck();
//							if(checkBool == false){
//								return;
//							}
							$("#cy-jbxx").hide();
							$("#cy-mjfw").show();
						}
						$("#xinxiMain > li > a").removeClass("active");
						$(this).addClass("active");
					})
					//下一步
					$(".xiaBu").on("click",function(){
//						var checkBool = shopInfoFoodCheckJs.foodInputCheck();
//						if(checkBool == false){
//							return;
//						}
						$("#xinxiMain > li > a").removeClass("active");
						$(".mjfw").addClass("active");
						$("#cy-jbxx").hide();
						$("#cy-mjfw").show();
					})
					//上一步
					$(".shangBu").on("click",function(){
						$("#xinxiMain > li > a").removeClass("active");
						$(".jbxx").addClass("active");
						$("#cy-jbxx").show();
						$("#cy-mjfw").hide();
					})
					//地图定位
					$("#addPosition").unbind().on('click',function(){
						$('#dlg2').html(baiduMapTpl);
						require(["hsec_shopInfoFoodSrc/baiduMap/map"],function(shopMap){
						      var contactWay = $("#hotLine").val();
						      var xyMap = $("#xyMap").val();
						      var x = "",y = "";
						      if(xyMap.indexOf(",") > -1){
						    	  var xySplit = xyMap.split(",");
						    	  x = xySplit[1];
						    	  y = xySplit[0];
						      }
							
		  				$("#dlg2").dialog({
								title : "百度地图选择",
								width : "628",
								modal : true,
								open : function(){
									shopMap.initMap("",contactWay,x,y,function(){
										shopMap.xyzuobiao(function(x,y){
											shopMap.initMap("",contactWay,x,y);
										});
									});
								},
								close: function() { 
								        $(this).dialog('destroy');
								},
								buttons : {
									'关闭' : function() {
										$(this).dialog("destroy");
									}
								}
							   });
						   });
					});
					
					//区域下拉框
					$("#quyu").unbind().on('change',function(){
						var id = $(this).val();
						var html = "<option value=''>所属商圈</option>";
						for(var i = 0 ;i<Location.length;i++){
							if(id == Location[i].areaCode){
								html += "<option value='"+Location[i].id+"'>"+Location[i].locationName+"</option>";
							}
						}
						$("#section").html(html);
					});
					
					
					
					/*营业点照片start*/
					//图片上传
					$('#shopImgList').off('change', '.shop_inputFile').on('change', '.shop_inputFile', function() {
						shop.imgUpload(this,$(this).parent());
					})
					$("#shopImgList").on("mouseover mouseout","li",function(event){
						 if(event.type == "mouseover"){
							 if($(this).find(".imgObj").html().length > 0 && $(this).find(".imgObj").html() != null){
									$(this).find(".delImg").show();
								}
						 }else if(event.type == "mouseout"){
							 $(this).find(".delImg").hide();
						 }
					})
					/*
					$("#shopImgList").on("click",".toleft",function(){
						var thisLi = $(this).parents("li");
						var thisLiPrev = $(this).parents("li").prev();
						if(thisLiPrev.length > 0){
							var htmlch = $(thisLi).find(".imgObject").html();
							var htmlch2 = $(thisLi).find(".preview").html();
							var htmlpa = $(thisLiPrev).find(".imgObject").html();
							var htmlpa2 = $(thisLiPrev).find(".preview").html();
							$(thisLiPrev).find(".imgObject").html(htmlch);
							$(thisLiPrev).find(".preview").html(htmlch2);
							$(thisLi).find(".imgObject").html(htmlpa);
							$(thisLi).find(".preview").html(htmlpa2);
						}
						if($(thisLi).find(".preview").find("img").length < 1){
							$(thisLi).removeClass("imgHoverItem");
						}
					})
					$("#shopImgList").on("click",".toright",function(){
						var thisLi = $(this).parents("li");
						var thisLiPrev = $(this).parents("li").next();
						if(thisLiPrev.length > 0){
							var htmlch = $(thisLi).find(".imgObject").html();
							var htmlch2 = $(thisLi).find(".preview").html();
							var htmlpa = $(thisLiPrev).find(".imgObject").html();
							var htmlpa2 = $(thisLiPrev).find(".preview").html();
							$(thisLiPrev).find(".imgObject").html(htmlch);
							$(thisLiPrev).find(".preview").html(htmlch2);
							$(thisLi).find(".imgObject").html(htmlpa);
							$(thisLi).find(".preview").html(htmlpa2);
						}
						if($(thisLi).find(".preview").find("img").length < 1){
							$(thisLi).removeClass("imgHoverItem");
						}
					})
					*/
					$("#shopImgList").on("click",".delImg",function(){
						var thisLi = $(this).parents("li");
						 $( "#dlg1" ).dialog({
						      modal: true,
						      title : "删除图片",
						      open : function(){
						    	  $( "#dlg1" ).find(".table-del").html("是否删除图片?");
						      },
						      buttons: {
						        确定: function(){
						        	$(thisLi).find(".imgObj").html("");
									$(thisLi).find(".imgShow").attr("src","").hide();
									$(this).dialog('destroy');
						        },
						        取消: function(){
						        	 $(this).dialog('destroy');
						        }
						      }
						 })
					})
					/*营业点照片End*/
					
					/*证件照start*/
					$('#imgUploadUl').off('change', '.fileZhengjian').on('change', '.fileZhengjian', function() {	
						shop.imgUpload2($(this),$(this).parent());
					})
					
					/**营业点时间start*/
				$(".timeXiaLa").unbind().on('change',function(){
					if($(this).val() != null && $(this).val() != ""){
						$(this).next().show();
					}else{
						$(this).next().hide();
					}
				})
				
				$(".date_add").unbind().on('click',function(){
					$(this).parents(".time1").next(".time2").show();
					$(this).hide();
				})
				
				$(".delTime").unbind().on('click',function(){
					$(this).parents(".time2").prev(".time1").find(".date_add").show();
					$(this).parents("tr").hide();
					$(this).parents("tr").find("select").find("option:first").prop("selected", 'selected').change();
					$(this).parents("tr").find("input[type='text']").val("");
				})
				//修改时时间控制：Bug：20607
				$("#shopPanel").on('change',".timeSelect",function(){
					var objThis = $(this);
					var bool = 0;
					$.each($("#shopPanel .timeSelect"),function(k,v){
						if($(v).val() == null || $(v).val() == ""){
							bool++;
						} 
						var sk =0;//标记  //当前选中的值 
						if($(objThis).val() != $(v).val()){
							$.each($(v).find("option"),function(kop,vop){
								sk++;
								if($(vop).val() == $(objThis).val() &&　$(vop).val()　!= ""){
									if(67==$(objThis).val().replaceAll(",","")){
										$(vop).prop("disabled", false);
									}else{
										$(vop).prop("disabled", true);
									}
									var str = $(vop).val().replaceAll(",","");
									if(str==123456||str==1234567){//当一至六，日隐藏同时时间2隐藏
										$("#data_add").hide();
										$(".time2").hide();
									}else if (str==67){//当六，日时隐藏添加按钮
										$("#data_add").hide();
									}else {
										$("#data_add").show();
										$(".date_add").show();
									}
								}else{ 
									var strs = $(objThis).val().replaceAll(",","");
									if(strs==12345){
										if(sk==3||sk==4){
											$(vop).prop("disabled", true);
										}
									}
								}
							})
						}else {
							$("#data_add").show();
							$(".date_add").show();
						}
					})
					
					if(bool > 1){
						$.each($("#shopPanel .timeSelect"),function(k,v){
								$.each($(v).find("option"),function(kop,vop){
										$(vop).prop("disabled", false);
								})
						})
					}
				})
				
				/*$("#shopPanel").on('change',".timeSelect2",function(){
					var objThis = $(this);
					var bool = 0;
					$.each($("#shopPanel .timeSelect2"),function(k,v){
						if($(v).val() == null || $(v).val() == ""){
							bool++;
						}
						if($(objThis).val() != $(v).val()){
							$.each($(v).find("option"),function(kop,vop){
								if($(vop).val() == $(objThis).val() &&　$(vop).val()　!= ""){
									$(vop).prop("disabled", true);
								}else{
									$(vop).prop("disabled", false);
								}
							})
						}
					})
					
					if(bool > 1){
						$.each($("#shopPanel .timeSelect2"),function(k,v){
								$.each($(v).find("option"),function(kop,vop){
										$(vop).prop("disabled", false);
								})
						})
					}
				})*/
				/*营业点时间end*/
				
				$("#zhoubian").on("click",".zhoubianInput",function(){
						$("#zhoubianDiv").show();
					})
					$("#zhoubianDiv").on("click","input",function(){
					var zhoubian="";
					$.each($("#zhoubianDiv>ul>li"),function(k,v){
						var input = $(v).find("input[type='checkbox']");
						if($(input).is(":checked")){
							zhoubian += $(input).attr("valueName")+" "; 
						}
					})
						$("#zhoubian .zhoubianInput").val(zhoubian);
					})	
				$("#gArrow").on("click",function(){
					
					if($("#zhoubianDiv").is(":hidden")){
						$("#zhoubianDiv").show();
					}else{
						$("#zhoubianDiv").hide();
					}
				})	
				},
				/**商品图片上传*/
				imgUpload : function(imgthis,thisParent){
				        	var file = $(imgthis).val();  
				            //判断上传的文件的格式是否正确  
				            var fileType = file.substring(file.lastIndexOf(".")+1);  
				            if(("JPG,JPEG,PNG,GIF,BMP").indexOf(fileType.toUpperCase()) == -1){  
				                return;  
				            }else{  
				        		shopFoodAjax.upLoadFile($(imgthis).attr("id"), function(response) {
									 var urlimg,imgName;
									 urlimg = response.tfs;
									 var pics = eval("("+response.pics+")");
									 imgName = pics[0].web[0].name;
											   $(thisParent).find(".imgObj").html(JSON.stringify(pics[0]));
											   $(thisParent).find(".imgShow").attr("src",urlimg+imgName).show();
											   return false;
								});
				 }	
				},
				imgUpload2 : function($this,parent){
		        	var file = $($this).val();  
		        	  var size = 0;
				        if (document.all){
				        	
				        }else{
				        	size = document.getElementById($($this).attr("id")).files[0].size;
				        	size = tablePoint.formatFileSize(size);
				        }
		            //判断上传的文件的格式是否正确  
		            var fileType = file.substring(file.lastIndexOf(".")+1);  
		            if(("JPG,JPEG,PNG,GIF,BMP").indexOf(fileType.toUpperCase()) == -1 || size > 1024){  
		                return;  
		            }else{  
		            			shopFoodAjax.upLoadFile($($this).attr("id"), function(response) {
		            				 var urlimg,imgName;
									 urlimg = response.tfs;
									 var pics = eval("("+response.pics+")");
									 imgName = pics[0].web[0].name;
									 $(parent).find("img").show();
									 $(parent).find("img").attr("src",urlimg+imgName);
									 $(parent).find(".imgObj").html(JSON.stringify(pics[0]));
							});
		            }	
		
				},
				bindFuWuClick : function(){
					$("#diKou").on("click",function(){
						if($(this).is(':checked')){
							$("#diKou1").show();
						}else{
							$("#diKou1").hide();
						}
					})
					
					$("#waimai").on("click",function(){
						if($(this).is(':checked')){
							$(".waimai1").show();
						}else{
							$(".waimai1").hide();
						}
					})
					//预约业务变更开始
					$("#yuyue").on("click",function(){
						if($(this).is(':checked')){
							$(".yuyueChildren").show();
							$(".displayYuYue").show();
						}else{
							$(".yuyueChildren").hide();
							$(".displayYuYue").hide();
						}
					})
					//配置预付定金金额
					$("#dingJinSeZi").on('click',function(){
						if($(this).is(':checked')){
							$(".displayYuYue").show();
						}else{
							$("#yuyueBaiFenBi").attr("value","");
							$(".displayYuYue").hide();
						}
					});
					//预约业务变更结束
					$("#qita").on("click",function(){
						if($(this).is(':checked')){
							$("#qita1").show();
						}else{
							$("#qita1").hide();
						}
					})
					
				},
				submit : function(){
					$("#submit").on("click",function(){
						//验证数据start
						var checkBool = shopInfoFoodCheckJs.foodInputCheck();
						if(checkBool == false){
							return false;
						}
						var checkBool = shopInfoFoodCheckJs.shopServiceCheck();
						if(checkBool == false){
							return false;
						}
						//验证数据end
						var param = {};
						var salerShopStore = {};
						//营业点详情
						var foodType = {};
						//获取区域
						var address = {};
						address["aId"] = $("#quyu").val();
						address["aValue"] = $("#quyu").find('option:selected').text();
						//商圈
						if($("#section").val() != null && $("#section").val() != ""){
							address["lId"] = $("#section").val();
							address["lValue"] = $("#section").find('option:selected').text();
						}else{
							address["lId"] = "";
							address["lValue"] = "";
						}
						address["xyMap"] = $("#xyMap").val();
						param["address"] = JSON.stringify(address);
						salerShopStore["pics"] = JSON.stringify(shopImgList());
						/*实体部分数据start*/
						salerShopStore["name"] = $.trim($("#shopName").val());
						salerShopStore["id"] = $.trim($("#shopPanel").attr("shopId"));
						salerShopStore["addr"] = $.trim($("#addr").val());
						salerShopStore["cityCode"] = $("#chengshiId").val();
						salerShopStore["licensePic"] = JSON.stringify(shopImgZhengJianList());
						salerShopStore["managementModel"] = managementModel();
						salerShopStore["brand"] = $("#brand").val();
						salerShopStore["openingHours"] = JSON.stringify(shopTimeList());
						salerShopStore["openingHoursRemark"] = $.trim($("#openingHoursRemark").val());
						salerShopStore["hotLine"] = $.trim($("#hotLine").val());
						salerShopStore["introduce"] = $.trim($("#introduce").val());
						salerShopStore["aroundInfo"] = JSON.stringify(zhoubian());
						salerShopStore["type"] = 2;
						salerShopStore["supportService"] = JSON.stringify(supportService());
						salerShopStore["specialServiceRemak"] = JSON.stringify(supportServiceDesc());
						/*实体部分数据end*/
						
						/**营业点详情*/
						foodType["costAvgStr"] = JSON.stringify(costAvgStr());
						foodType["foodType"] = JSON.stringify(shopTypeList($("#shopTypes")));
						foodType["businessType"] = JSON.stringify(shopTypeList($("#shopTeSe")));
						foodType["mealType"] = JSON.stringify(shopTypeList($("#shopMealType")));
						foodType["orderHours"] = JSON.stringify(orderHours());
						foodType["specialService"] = JSON.stringify(supportService());
						foodType["reservePhoneNo"] = $.trim($("#reservePhoneNo").val());
						foodType["decorateStyle"] = JSON.stringify(decorateStyle());
						foodType["areaSize"] = JSON.stringify(areaSize());
						salerShopStore["salerShopStoreDetail"] = foodType;
						param["sss"] = JSON.stringify(salerShopStore);
						/**营业点详情*/
						shopFoodAjax.updateSalerShopStore($.param(param),function(response) {
							if(response.retCode == 200){
								tablePoint.tablePoint("修改营业点成功!",function(){
									//shopMain.bindData();
									$(".stdlb").click();
								})
							}else if(response.retCode == 210){
								tablePoint.tablePoint("修改营业点名称已存在!请修改后重试!");
							} else{
								tablePoint.tablePoint("修改营业点失败!请稍后重试!");
							}
						})
					})
					//经营模式
					function managementModel(){
						return $("#managementModel").val();;
					}
					//营业点图片
					function shopImgList(){
						var itemsType = new Array();
						var trList = $("#shopImgList li");
						$.each(trList,function(key,value){
							var htmlImg = $(this).find(".imgObj").html();
							if($.trim(htmlImg) != null && $.trim(htmlImg) != ""){
								itemsType.push(eval("("+htmlImg+")"));
							}
						})
						return itemsType;
					}
					function zhoubian(){
						var zhoubian = new Array();
						$.each($("#zhoubianDiv>ul>li"),function(k,v){
							var input = $(v).find("input[type='checkbox']");
							if($(input).is(":checked")){
								var param = {};
								param["id"] = $(input).val();
								param["value"] = $(input).attr("valueName");
								zhoubian.push(param);
							}
						})
						return zhoubian;
					}
					function costAvgStr(){
						var param = {};
						param["id"] = $("#costAvg").val();
						param["value"] = $("#costAvg").find("option:selected").text();
						return param;
					}
					function decorateStyle(){
						var param = {};
						param["id"] = $("#decorateStyle").val();
						param["value"] = $("#decorateStyle").find("option:selected").text();
						return param;
					}
					function areaSize(){
						var param = {};
						param["id"] = $("#areaSize").val();
						param["value"] = $("#areaSize").find("option:selected").text();
						return param;
					}
					//营业执照
					function shopImgZhengJianList(){
						var itemsType = new Array();
						var trList = $("#imgUploadUl>li");
						$.each(trList,function(key,value){
							var htmlImg = $(this).find(".imgObj").html();
							if($.trim(htmlImg) != null && $.trim(htmlImg) != ""){
									itemsType.push(eval("("+htmlImg+")"));
							}
						})
						return itemsType;
					}
					
					//餐厅分类.经营特色,供餐类别
					function shopTypeList($this){
						var shopType = new Array();
						var trList = $($this).find("input[type='checkbox']");
						$.each(trList,function(k,v){
							if($(v).is(":checked")){
								var param = {};
								//param["id"] = $(v).val();
								param["id"] = $(v).attr("value");
								param["value"] = $(v).attr("valueName");
								shopType.push(param);
							}
						})
						return shopType;
					}
					
					//营业点营业时间
					function shopTimeList(){		
						var timeList = new Array();
						for (var i = 1; i < 3; i++) {
							var paramTime = {};
							var selectTime = $(".time"+i+" select").find("option:selected");
							var time = $(selectTime).val();
							var timeName = $(selectTime).eq(0).text();
							if(time != null && time != ""){
								paramTime["time"] = timeName;
								paramTime["value"] = time;
								var itemInput = $(".timeSpan"+i+" select");    
							    for (var j = 0; j < itemInput.length; j++) {
							    	paramTime["timeList"+j] = $(itemInput[j]).find("option:selected").text();
							    };
							    timeList.push(paramTime);
							}
						}
							if(timeList.length < 1){
								tablePoint.tablePoint("抱歉,请选择营业时间!");
								return false;
							}
						return timeList;
					}
					
					//营业点接单时间
					function orderHours(){		
						var paramTime = {};
								var itemInput = $("#orderHours select");    
							    for (var j = 0; j < itemInput.length; j++) {
							    	paramTime["timeList"+j] = $(itemInput[j]).find("option:selected").text();
							    };
						return paramTime;
					}
					//支持的服务
					function supportService(){
						var paramFuwu = {};
						if($("#diKou").is(":checked")){
							paramFuwu["10"] = "消费抵扣劵";
						}
						if($("#waimai").is(":checked")){
							paramFuwu["11"] = "外卖送餐";
						}
						if($("#yuyue").is(":checked")){
							paramFuwu["12"] = "预约";
						}
						if($("#tingChe").is(":checked")){
							paramFuwu["13"] = "支持停车";
						}
						if($("#ziti").is(":checked")){
							paramFuwu["15"] = "门店自提";
						}
						return paramFuwu;
					}
					//支持的服务描述
					function supportServiceDesc(){
						var paramFuwu = {};
						if($("#diKou").is(":checked")){
							var param = {};
							param["diKou"] = $.trim($("#diKouValue").val());
							paramFuwu["10"] = param;
						}
						if($("#waimai").is(":checked")){
							var param = {};
							param["range"] = $.trim($("#range").val());//范围
							param["waiMaiTime"] = $.trim($("#waiMaiTime").val());//送餐时间
							param["qiSongPrice"] = $.trim($("#qiSongPrice").val());//起送价格
							param["peiSongPrice"] = $.trim($("#peiSongPrice").val());//配送费
							param["manPirce"] = $.trim($('#manPirce').val());//满多少金额
							param["jianPrice"] = $.trim($('#jianPrice').val());//满多少金额
							paramFuwu["11"] = param;
						}
						if($("#yuyue").is(":checked")){
							var param = {};
							param["baiFenBi"] = $.trim($("#yuyueBaiFenBi").val());//百分比
							param["yuyueDay"] = $.trim($("#yuyueDay").val());//预约天数
							paramFuwu["12"] = param;
						}
						if($("#tingChe").is(":checked")){
							paramFuwu["13"] = "支持停车";
						}
						if($("#qita").is(":checked")){
							var param = {};
							param["14"] = "其他服务";//范围
							param["qiTa"] = $.trim($("#qiTaStr").val());//范围
							paramFuwu["14"] = param;
						}
						if($("#ziti").is(":checked")){
							paramFuwu["15"] = "门店自提";
						}
						return paramFuwu;
					}
				}
			}
			return shop;
		});
