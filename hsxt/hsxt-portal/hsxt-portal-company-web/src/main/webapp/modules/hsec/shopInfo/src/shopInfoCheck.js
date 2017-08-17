define(["hsec_tablePointSrc/tablePoint"],
		function(tablePoint) {
			var check = {
				lsInputCheck : function() {
					check.clearError();
					var inputNull = new Array();//错误集合
					var param = {};//错误对象
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
						
						//营业时间 时分秒
						/*
						var timesecBool = true;
						var timesecBool1 = true;
						var timesecBool2 = true;
						var timesecBool3 = true;
						$.each($(".timesec1"),function(k,v){
							if($(v).val()　!= null &&　$(v).val()　!= ""){
								timesecBool = false;
							}
						})　
						$.each($(".timesec2"),function(k,v){
							if($(v).val()　!= null &&　$(v).val()　!= ""){
								timesecBool1 = false;
							}
						})　
						$.each($(".timesec3"),function(k,v){
							if($(v).val()　!= null &&　$(v).val()　!= ""){
								timesecBool2 = false;
							}
						})　
						$.each($(".timesec4"),function(k,v){
							if($(v).val()　!= null &&　$(v).val()　!= ""){
								timesecBool3 = false;
							}
						})　
						*/
						
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
						
						
				//服务热线
				var hotLine = $.trim($("#hotLine").val());
				if(hotLine == null || hotLine == ""){
					param = {};
					param["error"] = "未填写服务热线";
					param["errorTr"] = $("#hotLine").parents("tr").eq(0);
					inputNull.push(param);
				}
				
				var trList = $("#zhuyinType [type='checkbox']:checked");
				if(trList.length < 1){
					param = {};
					param["error"] = "未选择主营分类";
					param["errorTr"] = $("#zhuyinType").parents("tr").eq(0);
					inputNull.push(param);
				}
				
				if(inputNull.length > 0){
					var errorStr = "";
					$.each(inputNull,function(k,v){
						errorStr +="<br/>"+k+"."+v.error;
						$(v.errorTr).find(".redError").html(v.error);
					})
					tablePoint.tablePoint(errorStr);
					return false;
				}
				
				return true;
				},
				clearError : function(){
					$.each($("#ls-jbxx tr"),function(k,v){
						$(v).find(".redError").html("");
					})
				},
				shopServiceCheck : function(){
					var inputNull = new Array();//错误集合
					var param = {};//错误对象
					
						//支持使用互生消费抵扣券
						if($("#songhuo").is(':checked')){
							var km = $.trim($("#km").val());
							var estimateTime = $.trim($("#estimateTime").val());
							if(km == null || km == ""){
								param = {};
								param["error"] = "未输入送货范围";
								param["errorTr"] = $("#km").parents("tr").eq(0);
								inputNull.push(param);
							}
							if(estimateTime == null || estimateTime == ""){
								param = {};
								param["error"] = "未输入预计送货时长";
								param["errorTr"] = $("#estimateTime").parents("tr").eq(0);
								inputNull.push(param);
							}
							
							var songda = $('input:radio[name="songda"]');
							if(!songda.is(":checked")){
								param = {};
								param["error"] = "未输入即时送达";
								param["errorTr"] = $(songda).parents("tr").eq(0);
								inputNull.push(param);
							}
							var fukuang = $('input:radio[name="fukuang"]');
							if(!fukuang.is(":checked")){
								param = {};
								param["error"] = "未输入货到付款";
								param["errorTr"] = $(fukuang).parents("tr").eq(0);
								inputNull.push(param);
							}
						}
						if(inputNull.length > 0){
							var errorStr = "";
							$.each(inputNull,function(k,v){
								errorStr +="<br/>"+k+"."+v.error;
								$(v.errorTr).find(".redError").html(v.error);
							})
							tablePoint.tablePoint(errorStr);
							return false;
						}
						return true;
				}
			}
			return check;
		});
