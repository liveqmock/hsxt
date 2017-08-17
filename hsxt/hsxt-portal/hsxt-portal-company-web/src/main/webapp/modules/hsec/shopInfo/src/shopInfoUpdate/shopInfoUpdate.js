// 修改
define([ "hsec_shopInfoSrc/shopInfoCheck",
         "text!hsec_shopInfoTpl/shopInfoUpdate/shopInfoUpdate.html",
          "text!hsec_shopInfoTpl/baiduMap/baiduMap.html"
         ,"hsec_shopInfoDat/shopInfo"
         ,"hsec_shopInfoSrc/shopInfoCheck"
         ,"hsec_tablePointSrc/tablePoint"
         ,"text!hsec_shopInfoFoodTpl/shopInfoFoodMain/tab.html"
         ],function(shopInfoCheck,shopInfoUpdateTpl,baiduMapTpl,ajaxRequest,shopInfoCheckJs,tablePoint,tabTpl) {
	var category;
	var Location;//商圈
	var shop;
	var shopInfoUpdate = {
			init : function(shopInfoHead,shopId){
				shop = shopInfoHead;
				if(Location != null){
					Location = '';
				}
				if(category != null){
					category = '';
				}
				shopInfoUpdate.update(shopId);
//				//add by zhanghh @2015-12-15
//				$('.operationsInner').html(_.template(tabTpl));
//				$(".tabList #wsscgl_yydlb").attr("style","display:none");
//				$(".tabList").append('<li><a class="active">新增/修改营业点</a></li>');
				$('.operationsInner').html(_.template(tabTpl));
				$("#wsscgl_yydlb").find("a").removeClass("active").addClass("stdlb");
				$(".tabList").append('<li><a class="active">新增/修改营业点</a></li>');
			},
			update : function(shopId){
				var id = shopId;
				if(id != null && id!="" && id!="undefined"){
						tablePoint.bindJiazai(".operationsArea", true);
					ajaxRequest.findSalerShop("id="+id, function(response) {	
						tablePoint.bindJiazai(".operationsArea", false);	
						category = response.data.SalerShopWebBean.lstMainPageCatAll;
						Location = response.data.Location;
//						var html = _.template(shopInfoUpdateTpl, response.data);
//						$('#stdlb').html(html);
//						$("#stdlb").html(_.template(tabTpl));
//						$("#wsscgl_yydlb").find("a").removeClass("active").addClass("stdlb");
//						$(".tabList").append('<li><a class="active">新增/修改营业点</a></li>');
						var html = _.template(shopInfoUpdateTpl, response.data);
						$("#busibox").html(html);
						shopInfoUpdate.bindClick();
						shopInfoUpdate.bindTableTwoClick();
					});
				}else{
					return false;
				}	
			},
			bindClick : function(){
//				$("#stdlb").on("click",".stdlb",function(){
//					shop.bindData();
//				})
				$(".tabList").on("click",".stdlb",function(){
					$(this).addClass("active");
					$("#wsscgl_yydlb").next().remove();
					shop.bindData();
				})
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
					//$(this).parent().find(".red").html( max_lt-lt>=0?max_lt-lt:0)
					$(this).parent().parent().find("td .red").html( max_lt-lt>=0?max_lt-lt:0)
				}).trigger('charcount');
				$("#introduce").on("keyup keypress charcount change",function(){
					var lt = $.trim($(this).val()).length;
					var max_lt = $(this).attr("maxlength");
					if(max_lt < lt){
						$(this).val($(this).val().substring(0,200));
					}
					//$(this).parent().find(".red").html( max_lt-lt>=0?max_lt-lt:0)
					$(this).parent().parent().find("td .red").html( max_lt-lt>=0?max_lt-lt:0)
				}).trigger('charcount');
				//下一步
				$(".xiaBu").on("click",function(){
					var checkBool = shopInfoCheckJs.lsInputCheck();
					if(checkBool == false){
						return;
					}
					$("#xinxiMain > li > a").removeClass("active");
					$(".mjfw").addClass("active");
					$("#ls-jbxx").hide();
					$("#ls-mjfw").css('display','block');
				})
				//上一步
				$(".shangBu").on("click",function(){
					$("#xinxiMain > li > a").removeClass("active");
					$(".jbxx").addClass("active");
					$("#ls-jbxx").show();
					$("#ls-mjfw").hide();
				})
				
				$("#xinxiMain").on("click","li>a",function(){
						if($(this).hasClass("jbxx")){
							$("#ls-mjfw").hide();
							$("#ls-jbxx").show();
						}else{
							var checkBool = shopInfoCheckJs.lsInputCheck();
							if(checkBool == false){
								return;
							}
							$("#ls-jbxx").hide();
							$("#ls-mjfw").css('display','block');
						}
						$("#xinxiMain > li > a").removeClass("active");
						$(this).addClass("active");
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
				
				$("#shopPanel").on('change',".timeSelect",function(){
					var objThis = $(this);
					var bool = 0;
					$.each($("#shopPanel .timeSelect"),function(k,v){
						if($(v).val() == null || $(v).val() == ""){
							bool++;
						}
						var sk =0;
						if($(objThis).val() != $(v).val()){
							$.each($(v).find("option"),function(kop,vop){
								sk++;
								if($(vop).val() == $(objThis).val()){
									if("周六至周日"==$(objThis).val()){
										$(vop).prop("disabled", false);
									}else{
										$(vop).prop("disabled", true);
									}
									//$(vop).prop("disabled", true);
									if($(vop).val()=="周一至周六"||$(vop).val()=="周一至周日"){
										$("#data_add").hide();
										$(".date_add").hide();
										$(".red").hide();
										$(".time2").hide();
									}else if($(vop).val()=="周六至周日"){
										$("#data_add").hide();
										$(".date_add").hide();
										$(".red").hide();
									}else {
										$("#data_add").show();
										$(".red").show();
										$(".date_add").show();
									}
								}else{
									if($(objThis).val()=="周一至周五"){
										if(sk==3||sk==4){
											$(vop).prop("disabled", true);
										}
									}
									//$(vop).prop("disabled", false);
								}
							})
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
				
				/*
				$("#shopPanel").on('change',".timeSelect",function(){
					var objThis = $(this);
					var bool = 0;
					$.each($("#shopPanel .timeSelect"),function(k,v){
						if($(v).val() == null || $(v).val() == ""){
							bool++;
						}
						var sk =0;
						if($(objThis).val() != $(v).val()){
							$.each($(v).find("option"),function(kop,vop){
								if($(vop).text() == $(objThis).val()){
									$(vop).prop("disabled", true);
								}else{
									$(vop).prop("disabled", false);
								}
							})
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
				
				$("#shopPanel").on('change',".timeSelect2",function(){
					var objThis = $(this);
					var bool = 0;
					$.each($("#shopPanel .timeSelect2"),function(k,v){
						if($(v).val() == null || $(v).val() == ""){
							bool++;
						}
						if($(objThis).val() != $(v).val()){
							$.each($(v).find("option"),function(kop,vop){
								if($(vop).text() == $(objThis).val()){
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
				
				$("#lsSubmit").on("click",function(){
					shopInfoUpdate.bindSubmit();
				})
			
			},
			bindTableTwoClick : function(){
				$("#songhuo").on("click",function(){
					if($(this).is(':checked')){
						$(".songhuo").show();
					}else{
						$(".songhuo").hide();
					}
				})
				
				$("#ziti").on("click",function(){
					if($(this).is(':checked')){
						$(".ziti").show();
					}else{
						$(".ziti").hide();
					}
				})
			},
			bindSubmit : function(){
				var checkBool = shopInfoCheckJs.lsInputCheck();
				if(checkBool == false){
					return;
				}
				checkBool = shopInfoCheckJs.shopServiceCheck();
				if(checkBool == false){
					return;
				}
				//验证数据end
				var param = {};
				//营业点实体
				var salerShopZhu = {};
				//营业点详情
				var salerShopZi = {}; 
				
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
				salerShopZhu["id"] = $.trim($("#shopPanel").attr("shopId"));
				salerShopZhu["addr"] = $.trim($("#addr").val());
				salerShopZhu["cityCode"] = $("#chengshiId").val();
				salerShopZhu["openingHours"] = JSON.stringify(shopTimeList());
				salerShopZhu["openingHoursRemark"] = $.trim($("#openingHoursRemark").val());
				salerShopZhu["hotLine"] = $.trim($("#hotLine").val());
				salerShopZhu["introduce"] = $.trim($("#introduce").val());
				salerShopZhu["supportServices"] = JSON.stringify(supportServices());
				salerShopZhu["supportServiceDesc"] = JSON.stringify(supportServiceDesc());
				param["salerShopstr"] = JSON.stringify(salerShopZhu);
				param["shopType"] = JSON.stringify(shopType());
				ajaxRequest.updateSalerShop(param, function(response) {	
					if(response.retCode == 200){
						tablePoint.tablePoint("修改营业点成功!",function(){
							//shop.bindData();
							$(".stdlb").click();
						});
					}else if(response.retCode=="206"){
						tablePoint.tablePoint("营业点信息不符合规范，包含违禁字:<br>"+response.data+"<br>请修改之后保存！");
					}else if(response.retCode=="210"){
						tablePoint.tablePoint("营业点信息不符合规范,存在相同的经纬度!请修改后,重新保存!");
					}else if(response.retCode=="211"){
						tablePoint.tablePoint("营业点信息不符合规范,存在相同的地址!请修改后,重新保存!");
					}else{
						tablePoint.tablePoint("修改营业点失败!请稍后重试!");
					}
				});
				
				//营业点营业时间
				function shopTimeList(){		
					var timeList = new Array();
					for (var i = 1; i < 3; i++) {
						var paramTime = {};
						var time = $(".time"+i+" select").find("option:selected").val();
						if(time != null && time != ""){
							paramTime["time"] = time;
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
				
				function shopType(){
					var shopType = new Array();
					var trList = $("#zhuyinType [type='checkbox']:checked");
					$.each(trList,function(key,value){
						var param = {};
						param["shopTypeId"] = $(this).attr("value");
						param["shopTypeName"] = $(this).attr("valuename");
						shopType[key] = param;
					})
					return shopType;
				}
				
				function supportServices(){
					var param = {};
						if($("#songhuo").is(':checked')){
							param["3"] = "送货上门";
							var songda = $('input:radio[name="songda"]:checked').val();
							if(songda == 1){
								param["4"] = "即时送达";
							}
							var fukuang = $('input:radio[name="fukuang"]:checked').val();
							if(fukuang == 1){
								param["5"] = "货到付款";
							}
						}
					
						if($("#ziti").is(':checked')){
							param["2"] = "门店自提";
						}
					return param;
				}
				function supportServiceDesc(){
					var param = {};
						if($("#songhuo").is(':checked')){
							param["km"] = $.trim($("#km").val());
							param["estimateTime"] = $.trim($("#estimateTime").val());
							param["itemTimeStart"] = $(".itemTimeStart").eq(0).val()+":"+$(".itemTimeStart").eq(1).val();
							param["itemTimeEnd"] = $(".itemTimeEnd").eq(0).val()+":"+$(".itemTimeEnd").eq(1).val();
						}
					
						if($("#ziti").is(':checked')){
							param["doorUpStart"] = $(".doorUpStart").eq(0).val()+":"+$(".doorUpStart").eq(1).val();
							param["doorUpEnd"] = $(".doorUpEnd").eq(0).val()+":"+$(".doorUpEnd").eq(1).val();
						}
					return param;
				}
			}
	}
	return shopInfoUpdate;
});

