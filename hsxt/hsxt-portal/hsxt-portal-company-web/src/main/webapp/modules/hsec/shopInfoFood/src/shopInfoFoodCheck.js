define(["hsec_tablePointSrc/tablePoint"],
		function(tablePoint) {
			var check = {
				foodInputCheck : function() {
					check.clearError();
					var inputNull = new Array();//错误集合
					var param = {};//错误对象
					//验证名称
					if($("#shopName").val() == null || $("#shopName").val() == ""){
						param = {};
						param["error"] = "营业点名称未输入";
						param["errorTr"] = $("#shopName").parents("tr").eq(0);
						inputNull.push(param);
					}
					//验证区域
					if($("#quyu").val() == null || $("#quyu").val() == ""){
						param = {};
						param["error"] = "区域未选择";
						param["errorTr"] = $("#quyu").parents("tr").eq(0);
						inputNull.push(param);
					}
					
					//详细地址
					var addr = $.trim($("#addr").val());
					if(addr == null || addr == ""){
						param = {};
						param["error"] = "详细地址未填写";
						param["errorTr"] = $("#addr").parents("tr").eq(0);
						inputNull.push(param);
					}
					
					//详细地址
					var xyMap = $.trim($("#xyMap").val());
					if(xyMap == null || xyMap == ""){
						param = {};
						param["error"] = "请选择营业点位置";
						param["errorTr"] = $("#xyMap").parents("tr").eq(0);
						inputNull.push(param);
					}
					
					//营业点照片
					$.each($("#shopImgList li"),function(k,v){
						   if($(v).hasClass("shopImgZhu")){
							   if($(v).find(".imgObj").html().length > 0 && $(v).find(".imgObj").html() != null){
							   }else{
								   	param = {};
									param["error"] = "缺少营业点主图";
									param["errorTr"] = $("#shopImgList").parents("tr").eq(0);
									inputNull.push(param);
								    return false;
							   }
						   } 
					})
					
					//证件图片
					var checkBool = 0;
					$.each($("#imgUploadUl li"),function(k,v){
						var img = $(v).find("span").find("img").attr("src");
							   if(img == null || img ==""){
								    checkBool = 1;
								    return false;
							   }
					})
						if(checkBool == 1){
							param = {};
							param["error"] = "缺少证件图片";
							param["errorTr"] = $("#imgUploadUl").parents("tr").eq(0);
							inputNull.push(param);
						}
					
					//餐厅分类
					var checkBool = 0;
					$.each($("#shopTypes ul li"),function(k,v){
							   if($(v).find("input[type='checkbox']").is(":checked")){
								   checkBool = 1;
								   return false;
							   }
					})
					if(checkBool != 1){
						param = {};
						param["error"] = "未选择餐厅分类";
						param["errorTr"] = $("#shopTypes").parents("tr").eq(0);
						inputNull.push(param);
					}
					
					//经营特色
					var checkBool = 0;
					$.each($("#shopTeSe ul li"),function(k,v){
							   if($(v).find("input[type='checkbox']").is(":checked")){
								   checkBool = 1;
								   return false;
							   }
					})
					if(checkBool != 1){
						param = {};
						param["error"] = "未选择经营特色";
						param["errorTr"] = $("#shopTeSe").parents("tr").eq(0);
						inputNull.push(param);
					}
					
					//供餐类别
					var checkBool = 0;
					$.each($("#shopMealType ul li"),function(k,v){
							   if($(v).find("input[type='checkbox']").is(":checked")){
								   checkBool = 1;
								   return false;
							   }
					})
					if(checkBool != 1){
						param = {};
						param["error"] = "未选择供餐类别";
						param["errorTr"] = $("#shopMealType").parents("tr").eq(0);
						inputNull.push(param);
					}
					
					//经营模式
					var managementModel = $.trim($("#managementModel").val());
					if(managementModel == null || managementModel == ""){
						param = {};
						param["error"] = "未选择经营模式";
						param["errorTr"] = $("#managementModel").parents("tr").eq(0);
						inputNull.push(param);
					}
					
					//营业时间
						var timeSelectBool = true;
						$.each($(".timeSelect"),function(k,v){
							if($(v).val()　!= null &&　$(v).val()　!= ""){
								timeSelectBool = false;
							}
						})　
						if(timeSelectBool){
							param = {};
							param["error"] = "未选择营业时间";
							param["errorTr"] = $(".timeSelect").eq(0).parents("tr").eq(0);
							inputNull.push(param);
						}
						
						var count = 0 ;
						
						 
						if ($('.time2').is(':visible')){
							$('#ls-jbxx .timesec').each(function(key,item){							 
								if ($(item).val()==''){
									count ++;
								}
							})
							if(count >0){
								param = {};
								param["error"] = "未选择营业时间";
								$('#date_add .redError').text("未选择营业时间");
								param["errorTr"] = $(".time1").eq(0).parents("tr").eq(0);
								inputNull.push(param);
							}
						} else {
							$('.time1 .timesec').each(function(key,item){							 
								if ($(item).val()==''){
									count ++;
								}
							})
							
							if(count >0){
								param = {};
								param["error"] = "未选择营业时间";
								$('#date_add .redError').text("未选择营业时间");
								param["errorTr"] = $(".time1").eq(0).parents("tr").eq(0);
								inputNull.push(param);
							}
						}
						
				var tel = /^[\d-,]+$/;
				//服务热线
				var hotLine = $.trim($("#hotLine").val());
				if(hotLine == null || hotLine == "" || !tel.test(hotLine)){
					param = {};
					param["error"] = "服务热线条件:数字,横杆（-）,逗号（,）,非空!";
					param["errorTr"] = $("#hotLine").parents("tr").eq(0);
					inputNull.push(param);
				}
				
				//订餐电话
				var reservePhoneNo = $.trim($("#reservePhoneNo").val());
				if(reservePhoneNo == null || reservePhoneNo == "" || !tel.test(reservePhoneNo)){
					param = {};
					param["error"] = "订餐电话条件:数字,横杆（-）,逗号（,）,非空!";
					param["errorTr"] = $("#reservePhoneNo").parents("tr").eq(0);
					inputNull.push(param);
				}
				
				//人均消费
				var costAvg = $.trim($("#costAvg").val());
				if(costAvg == null || costAvg == ""){
					param = {};
					param["error"] = "未选择人均消费";
					param["errorTr"] = $("#costAvg").parents("tr").eq(0);
					inputNull.push(param);
				}
				
				//装修风格
//				var decorateStyle = $.trim($("#decorateStyle").val());
//				if(decorateStyle == null || decorateStyle == ""){
//					param = {};
//					param["error"] = "未选择装修风格";
//					param["errorTr"] = $("#decorateStyle").parents("tr").eq(0);
//					inputNull.push(param);
//				}
				
				//面积（平方米）
//				var areaSize = $.trim($("#areaSize").val());
//				if(areaSize == null || areaSize == ""){
//					param = {};
//					param["error"] = "未选择面积";
//					param["errorTr"] = $("#areaSize").parents("tr").eq(0);
//					inputNull.push(param);
//				}
				
//				//周边环境
//				var zhoubianInput = $.trim($(".zhoubianInput").val());
//				if(zhoubianInput == null || zhoubianInput == ""){
//					param = {};
//					param["error"] = "未选择周边环境";
//					param["errorTr"] = $(".zhoubianInput").parents("tr").eq(0);
//					inputNull.push(param);
//				}
				
				
				if(inputNull.length > 0){
					var errorStr = "";
					$.each(inputNull,function(k,v){
						errorStr +="<br/>"+k+"."+v.error;
						$(v.errorTr).find(".redError").html(v.error);
					})
					tablePoint.tablePoint("<div style='text-align:left;padding-left: 9%;'>"+errorStr+"</div>");
					return false;
				}
				
				return true;
				},
				clearError : function(){
					$.each($("#cy-jbxx tr"),function(k,v){
						$(v).find(".redError").html("");
					})
				},
				shopServiceCheck : function(){
					var inputNull = new Array();//错误集合
					var param = {};//错误对象
					var c = /^[0-9]*[0-9][0-9]*$/;//验证整数
					var c1=/\.\d{3,}/;//验证小数位数为2位
						//支持使用互生消费抵扣券
						if($("#diKou").is(':checked')){
							var diKouValue = $.trim($("#diKouValue").val());
							if(diKouValue == null || diKouValue == "" || !c.test(diKouValue) || diKouValue < 100 || diKouValue > 10000){
								param = {};
								param["error"] = "消费抵扣券使用金额条件(100~10000),整数。";
								param["errorTr"] = $("#diKouValue").parents("tr").eq(0);
								inputNull.push(param);
							}
						}
						//支持外卖送餐
						if($("#waimai").is(':checked')){
							var range = $.trim($("#range").val());
							var waiMaiTime = $.trim($("#waiMaiTime").val());
							var qiSongPrice = $.trim($("#qiSongPrice").val());
							var peiSongPrice = $.trim($("#peiSongPrice").val());
							var manPirce = $.trim($("#manPirce").val());
							var jianPrice = $.trim($("#jianPrice").val());

								if(!isNaN(range) && !c1.test(range) && range != null && range != "" && range > 0){
									$("#range").val((new Number(range)));
								}else{
									param = {};
									param["error"] = "送餐距离输入条件（大于0）,数字。";
									param["errorTr"] = $("#range").parents("tr").eq(0);
									inputNull.push(param);
								}
								
							if(waiMaiTime == null || waiMaiTime == "" || !c.test(waiMaiTime) || waiMaiTime < 1 || waiMaiTime > 120){
								param = {};
								param["error"] = "预计送餐时长条件（1~120）,整数。";
								param["errorTr"] = $("#waiMaiTime").parents("tr").eq(0);
								inputNull.push(param);
							}
							if(qiSongPrice == null || qiSongPrice == "" || !c.test(qiSongPrice) || qiSongPrice < 1){
								param = {};
								param["error"] = "起送价条件（大于0）,整数。";
								param["errorTr"] = $("#qiSongPrice").parents("tr").eq(0);
								inputNull.push(param);
							}
							if(peiSongPrice == null || peiSongPrice == "" || !c.test(peiSongPrice) || peiSongPrice < 0){
								param = {};
								param["error"] = "配送费条件（大于等于0）,整数.";
								param["errorTr"] = $("#peiSongPrice").parents("tr").eq(0);
								inputNull.push(param);
							}
							if((manPirce != null && manPirce != "") || (jianPrice != null && jianPrice != "")){
								if(!c.test(manPirce) || manPirce < 0 || !c.test(jianPrice) || Number(peiSongPrice) < Number(jianPrice)){
									param = {};
									param["error"] = "订单减免配送费条件（减不大于配送费）,正整数.";
									param["errorTr"] = $("#manPirce").parents("tr").eq(0);
									inputNull.push(param);
								}
							}
						}
					//支持预约
						if($("#yuyue").is(':checked')){
							var yuyueBaiFenBi = $.trim($("#yuyueBaiFenBi").val());
							var yuyueDay = $.trim($("#yuyueDay").val());
							//选中配置预付定金金额  //modifly by zhanghh : 2016-04-23
							if($("#dingJinSeZi").is(':checked')){//yuyueBaiFenBi == null || yuyueBaiFenBi == ""  || 
								if(!c.test(yuyueBaiFenBi) || yuyueBaiFenBi < 100 || yuyueBaiFenBi > 5000  || (yuyueBaiFenBi % 100 != 0)){
									param = {};
									param["error"] = "配置预付定金金额条件（100~5000）,100的整倍数.";
									param["errorTr"] = $("#yuyueBaiFenBi").parents("tr").eq(0);
									inputNull.push(param);
								}
							}
							/*if(yuyueBaiFenBi == null || yuyueBaiFenBi == "" || !c.test(yuyueBaiFenBi) || yuyueBaiFenBi < 0 || yuyueBaiFenBi > 100){
								param = {};
								param["error"] = "预定金额比例条件（0%~100%）,整数.";
								param["errorTr"] = $("#yuyueBaiFenBi").parents("tr").eq(0);
								inputNull.push(param);
							}*/
							if(yuyueDay == null || yuyueDay == "" || !c.test(yuyueDay) || yuyueDay < 0){
								param = {};
								param["error"] = "可预定天数条件（大于等于0）,整数.";
								param["errorTr"] = $("#yuyueDay").parents("tr").eq(0);
								inputNull.push(param);
							}
						}
					//支持其他服务
						if($("#qita").is(':checked')){
							var qiTaStr = $.trim($("#qiTaStr").val());
							if(qiTaStr == null || qiTaStr == ""){
								param = {};
								param["error"] = "未输入支持其他服务描述";
								param["errorTr"] = $("#qiTaStr").parents("tr").eq(0);
								inputNull.push(param);
							}
						}
						
						if(inputNull.length > 0){
							var errorStr = "";
							$.each(inputNull,function(k,v){
								errorStr +="<br/>"+k+"."+v.error;
								$(v.errorTr).find(".redError").html(v.error);
							})
							tablePoint.tablePoint("<div style='text-align:left;padding-left: 10%;'>"+errorStr+"</div>");
							return false;
						}
						return true;
				}
			}
			return check;
		});
