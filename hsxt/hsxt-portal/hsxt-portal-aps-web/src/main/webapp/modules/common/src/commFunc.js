define(["jquery","underscore"] , function($,_) {
		return window.comm = {
			    /*
					取当前日期
				 */
				getCurrDate : function(){
					var date = new Date();
					var year = date.getFullYear();
					var month = date.getMonth()+1;
					var day = date.getDate();
					var hour = date.getHours();
					var minute = date.getMinutes();
					var second = date.getSeconds();
					return year +'-'+((month<10)?("0"+month):month) + '-' + ((day<10)?("0"+day):day);
				},
				// 获取今天开始和结束
				getTodaySE : function () {
					var d1 = new Date();
					return [d1.format(), d1.format()];
				},
				// 获取最近一周开始和结束
				getWeekSE : function () {
					var d1 = new Date();
					var e = d1.getFullYear() + "-" +(d1.getMonth()+1) + "-" + d1.getDate();
					d1.setDate(d1.getDate() - 6); // 上一周
					var s = d1.getFullYear() + "-" +(d1.getMonth()+1) + "-" + d1.getDate();
					return [s, e];
				},
				// 获取最近一月开始和结束
				getMonthSE : function () {
				    var d1 = new Date();
	                var e = d1.format();
	                d1.setMonth(d1.getMonth() - 1);
	                var s = d1.format();
	                return [s, e];
				},
				//获取最近三个月开始和结束
				get3MonthSE:function (){					
				    var d1 = new Date();
	                var e = d1.format();
	                d1.setMonth(d1.getMonth() - 3);
	                var s = d1.format();
	                return [s, e];
				},
				//获取最近六个月开始和结束
				get6MonthSE:function (){
				    var d1 = new Date();
	                var e = d1.format();
	                d1.setMonth(d1.getMonth() - 6);
	                var s = d1.format();
	                return [s, e];
				},
				//获取最近一年开始和结束
				get1YearsSE:function (){
					var today = new Date();
					var e = today.getFullYear() + "-" +(today.getMonth()+1) + "-" + today.getDate();
					today.setFullYear(today.getFullYear() - 1);
					var s = today.getFullYear() + "-" +(today.getMonth()+1) + "-" + (today.getDate()+1);
					return [s, e];
				},
				// 获取最近三年开始和结束
				get3YearsSE : function () {
					var today = new Date();
					var e = today.getFullYear() + "-" +(today.getMonth()+1) + "-" + today.getDate();
					today.setFullYear(today.getFullYear() - 3);
					var s = today.getFullYear() + "-" +(today.getMonth()+1) + "-" + (today.getDate()+1);
					return [s, e];
				},
				
				
				//将时间由毫秒数转化成日期格式
				formatDate : function(date, format) {
					if(!date || date =="0"){
						return "";
					}
					if (!format) {
						format = "yyyy-MM-dd hh:mm:ss";
					}
					if(typeof date == "string"){
						if(date.length == 8){
							var arr = [date.substr(0,4),date.substr(4,2),date.substr(6,2)];
						}else if(date.length == 14){
							var arr = [date.substr(0,4),date.substr(4,2),date.substr(6,2),date.substr(8,2),date.substr(10,2),date.substr(12,2)];
						}else{
							var arr = date.split(/[^0-9]+/);
						}
						
						format = format.replace(/([a-z])\1+/ig,function(all,$1){
							var result = {
								y : ~~arr[0],
								M : ~~arr[1],
								d : ~~arr[2],
								h : ~~arr[3],
								m : ~~arr[4],
								s : ~~arr[5]  			
							}[$1];
							if(result!=undefined&&result<10){
								result = "0"+result
							}
							return result || "";
						});
						return format;
					}
					format = format.replace(/([a-z])\1+/ig,function(all){
						var first = all.charAt(0);
						if("y M d h m s".indexOf(first)>=0){
							if(first=="y"){
								return all.length>2
									? date.getFullYear()
									: (date.getFullYear()+"").substr(2);
							}
							var result = {
								M : date.getMonth() + 1,
								d : date.getDate(),
								h : date.getHours(),
								m : date.getMinutes(),
								s : date.getSeconds()    			
							}[first];
							result!=undefined&&result<10
								&&(result = "0"+result);
							return result;
						}else{
							return all;
						}
					});
					return format;
				},
				/**     
				 * 两个日期相减得到月数差 格式: yyyy-MM-dd    返回正整数
				 *  @param  beginTime  开始日期     
				 *  @param  endTime   结束日期     
				 ** @param  sign   年月日分隔符:-     
				 ***/ 				
				diffMonth: function(beginTime,endTime,sign){     
					var beginDate = this.formatDate(beginTime,'yyyy'+(sign||'/')+'MM'+(sign||'/')+'dd' ).split((sign||'/'));//拆分开始日期的年月日     
					var endDate = this.formatDate(endTime, 'yyyy'+(sign||'/')+'MM'+(sign||'/')+'dd' ).split((sign||'/'));//拆分开始日期的年月日 
    	    		var bMonth = parseInt(beginDate[0]) * 12 + parseInt(beginDate[1]);//得到开始日期的月数     
				    var eMonth = parseInt(endDate[0]) * 12 + parseInt(endDate[1]);//得到结束日期的月数     
				    var totalMonth = Math.abs(eMonth - bMonth);//获取月数     
				    return totalMonth;    
			    },
				/**     
				 * 两个日期相减得到月数差 格式: yyyy-MM-dd     
				 *  @param  beginTime  开始日期     
				 *  @param  endTime   结束日期     
				 ** @param  sign   年月日分隔符:-     
				 ***/ 				
				monthMinus: function(beginTime,endTime,sign){     
					var beginDate = this.formatDate(beginTime,'yyyy'+(sign||'/')+'MM'+(sign||'/')+'dd' ).split((sign||'/'));//拆分开始日期的年月日     
					var endDate = this.formatDate(endTime, 'yyyy'+(sign||'/')+'MM'+(sign||'/')+'dd' ).split((sign||'/'));//拆分开始日期的年月日 
    	    		var bMonth = parseInt(beginDate[0]) * 12 + parseInt(beginDate[1]);//得到开始日期的月数     
				    var eMonth = parseInt(endDate[0]) * 12 + parseInt(endDate[1]);//得到结束日期的月数     
				    return (eMonth - bMonth);//获取月数     
			    },
				/*
				 * 替换特殊符号
				 *
				 */
				
				
				/*
					格式化金额
				 */
				  formatMoneyNumber : function(moneyNum) {
						var result = isNaN((1 * moneyNum).toFixed(2)) ? (new Number(0).toFixed(2)) : (1 * moneyNum).toFixed(2);
						return /\./.test(result) ? result.replace(/(\d{1,3})(?=(\d{3})+\.)/g, "$1,") : result.replace(/(\d{1,3})(?=(\d{3})+\b)/g, "$1,");
				  },
				
				setFocus:function(obj,text){//输入框获得焦点时时触发
	
					obj.style.color="black";
					if(obj.value == text){
						obj.style.color="black";
						obj.value="";
						}
				} ,
					
				setBlur: function(obj,text){//输入框失去焦点时触发
					
					if(obj.value == ""){
						obj.style.color="#999997";
						obj.value=text;
					}
				} ,
				
				/*
				 * 设置，获取，删除全局缓存
				 */
				setCache : function(module, name ,data){
					if (!this.cache[module]){
						this.cache[module] = {};
					} 
					if (!this.cache[module][name]){
						this.cache[module][name] = data;
					} 
					
				} ,
				
				getCache : function(module, name){
					if (!this.cache[module]){
						return null ;
					}
						
					if (!this.cache[module][name]){
						return null;
					} 
					return this.cache[module][name];
				} ,
				
				delCache : function(module, name){
					if (!this.cache[module]){
						return  ;
					}  else {
						delete this.cache[module][name];
					}
				} ,
				
				
				
				/*
				 * require 更改，以适应多域操作
				 * 				 
				 */				 
				 Require : function(urlNames,succback,errback,optional){
					var urlArr = [];
					$.each(urlNames,function(key,urlObj){						 
						var thisUrl = null;
						if ($.isPlainObject(urlObj) ){			 	 
							//thisUrl = comm.getDomainUrl( urlObj.url  , comm.clearDomain(urlObj.domain)   ) || '';	
							thisUrl = comm.getDomainUrl( urlObj.url  , urlObj.domain   )  ;							
						} else{
							thisUrl = comm.getDomainUrl( urlObj  , Config.domain )  ;							
						}
						urlArr.push(thisUrl);
						
					});					
					require(urlArr,succback,errback,optional) ;					 
				 }  ,
				 
				 Define : function(urlNames, succback, optional) {
					var urlArr = [];
					$.each(urlNames,function(key,urlObj){					 
						var thisUrl = null;
						if ($.isPlainObject(urlObj) ){
							//有指定域名
							if ( urlObj.url.indexOf('text!') >-1 || urlObj.url.indexOf('css!') >-1  ){
								//模板路径
								thisUrl = comm.formatTextUrl( urlObj.url , comm.domainList[urlObj.domain] ) ;								
							} else {
								//js 路径								
								thisUrl = comm.getDomainUrl( urlObj.url  , urlObj.domain  );	
							} 		 	 
													
						} else{
							thisUrl = urlObj;
							/*//未指定域名
							if ( urlObj.url.indexOf('text!') >-1 || urlObj.url.indexOf('css!') >-1  ){
								//模板路径
								
							} else {
								//js 路径
								
								
							}*/
							
							//thisUrl = comm.getDomainUrl( urlObj  , Config.domain ) ;							
						}
						urlArr.push(thisUrl) ;
						
					});					
					define(urlArr,succback,optional) ;		
					 
				 },
				 /*
				  * 替换当前的域名地址
				  */
				 emptyCurrPath: function(path){
					var rr = /http([s]?):\/\/(.*)\//g ;
					var mathArr = rr.exec(path);					
					return mathArr[0];
					
				 },
				  
				 formatTextUrl: function(url,domain){
				 
					//去掉域名地址：require无.js全路径有点问题 
					var domainName =  domain.replace('http://'+location.host+'/',''),
						urlName = null ;
					if ( !domain ){
						return "";	
					} else {
						//设置需要代理路径的url
						var urlArr = url.split("!");
							urlName =  urlArr[0]+'!'+domainName+urlArr[1];
						return urlName ;
					}
				 } ,
				  
				 
				 //国际化文案
				 langConfig : {},
				 //获取相应模块下的字段文案
				 lang : function(name) {
					var str = this.langConfig[name] || '';
					return str;
				 } ,
				 
				 /*
				  * 过滤特殊字符,如
				  * <,>,',",(,),[,],{,},%,\,/,^,@,&,_
				  *
				  *
				  */
				 formatStr :function(str){
					 var string =  str;
					 	 string = string.replace(/</gm,'＜');
						 string = string.replace(/>/gm,'＞');
						 string = string.replace(/\'/gm,'＇');
						 string = string.replace(/\"/gm,'＂');
						 string = string.replace(/\(/gm,'（');
						 string = string.replace(/\)/gm,'）');
						 string = string.replace(/\[/gm,'［');
						 string = string.replace(/\]/gm,'］');
						 string = string.replace(/\{/gm,'｛');
						 string = string.replace(/\}/gm,'｝');
						 string = string.replace(/\%/gm,'％');
						 string = string.replace(/\\/gm,'＼');
						 string = string.replace(/\//gm,'／');
						 string = string.replace(/\^/gm,'＾');
						 string = string.replace(/@/gm,'＠');
						 string = string.replace(/&/gm,'＆');
						 string = string.replace(/_/gm,'＿');
					 return string;
				 } ,
				 
				 /***
				  *  确认对话框
				  *   
				  *
				  ***/				 
				 confirm : function(obj){
					 if (! $('#dialog-confirm')[0] ){
						$('<div id="dialog-confirm"  title="" class="none"><p></p></div>').appendTo($(document.body));	 
					 } 
					 $('#dialog-confirm').attr('title',obj.title || '温馨提示')	 ;
					 if (obj.imgFlag){					 	
					 	$('#dialog-confirm > p').html('<i class="icon_tips '+ ( obj.imgClass || "tips_ques" )  +'"></i><span class="fl fb f14 ml10 pt9">'+(obj.content || '您真的要删除吗？')+'</span>');
					 } else {
					 	$('#dialog-confirm > p').html(obj.content || '您真的要删除吗？');
					 }					 
					 var btnOkName = obj.ok || '确定',
					 	  btnOther = obj.del || '删除',
					 	  btnCancelName = obj.cancel || '取消';				     
					 var btnObj ={};
					 btnObj[btnOkName] =   function(){ obj.callOk && obj.callOk(); $(this).dialog("destroy");$("#dialog-confirm").remove();};
					 obj.del && (btnObj[btnOther] = function(){ obj.callOther && obj.callOther(); $(this).dialog("destroy");$("#dialog-confirm").remove();});
					 btnObj[btnCancelName] = function(){ obj.callCancel && obj.callCancel() ;$(this).dialog("destroy");$("#dialog-confirm").remove();}; 					 
					 if (obj.timeClose &&  /^\d+$/.test(obj.timeClose )  ){
					 	setTimeout(function(){
					 		if ($("#dialog-confirm").length ){
					 			$("#dialog-confirm").dialog('destroy');
					 			$("#dialog-confirm").remove();
					 		}
					 	},obj.timeClose*1000);
					 }					 
					 $("#dialog-confirm").dialog({  
					 	 width : obj.width  || 320 ,	 
						 height: obj.height || 160 , 
						 open: function (event, ui) {
							obj.closeButton && $(".ui-dialog-titlebar-close", $(this).parent()).show();
						 },
						 buttons: btnObj 
					 });	 
				 } ,
				 
				 
				 /**
				  * 提示对话框
				  *
				  */
				 alert : function(content){
					 if (! $('#dialog-alert')[0] ){
						$('<div id="dialog-alert"  title="" class="none"><p></p></div>').appendTo($(document.body));	 
					 } 
					 $('#dialog-alert').attr('title', content.title || '温馨提示')	 ;
					 if  (typeof content =='object'){
					 	$('#dialog-alert > p').html('<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
  							'<tr>'+
    							'<td height="50"><div class="clearfix">'+
    								'<i class="icon_tips  '+ ( content.imgClass || "tips_yes" )  +'"></i>'+
        							'<span class="fl fb f14 ml10 pt9">'+(content.content || '' )+'</span>'+
    								'</div></td>'+
									'</tr>'+
									'</tbody></table>'); 
					 } else{
					 	$('#dialog-alert > p').html(content || '操作!'); 
					 } 				 
					 if (content.timeClose &&  /^\d+$/.test(content.timeClose )  ){
					 	setTimeout(function(){
					 		if ($("#dialog-alert").length ){
					 			$("#dialog-alert").dialog('destroy');
					 			$("#dialog-alert").remove();
					 		}
					 	},content.timeClose*1000);
					 }
					 $("#dialog-alert").dialog({ 
						 width : content.width  || 360 ,
						 height: content.height || 160 , 	
						 open: function (event, ui) {
							content.closeButton && $(".ui-dialog-titlebar-close", $(this).parent()).show();
						 },			  
						 buttons: {
							'确定' :  function(){ 
								 if  (typeof content =='object' &&  content.callOk && typeof  content.callOk  == 'function'){
								 	  content.callOk();
								 }
								$(this).dialog("destroy");
								$("#dialog-alert").remove();
							} 	 
						 }
					 })	 
				 } ,	
				 valid :function(validObj){
					//"表单中form的id
					return $(validObj.formID).validate({ 
							rules : validObj.rules,
							messages : validObj.messages,
							success:function(element){
									if (validObj.success){
										validObj.success(element);
										 return true;
									}
							 
								    $(element).tooltip();
								    $(element).tooltip("destroy");
								    
								    if ($(element)[0] ){								    	 
								    	$($(element)[0].control).tooltip();
								    	$($(element)[0].control).tooltip("destroy");								     
								    }  
							},
							errorPlacement: function(error, element) { //此项配置可以省略，则使用默认
							 
						        	if (validObj.error){
						        		validObj.error(error, element);						        		
						        		return false;
						        	}	
						        	
						        	$(element).attr("title",$(error).text()).tooltip({
										position:{
											my:"left+"+( validObj.left || '100')+" top+"+(validObj.top || '5'),
											at:"left top"	
										}
									}).tooltip("open");
									$(".ui-tooltip").css("max-width","230px");   					        	
						        	//error.appendTo(element.parent().next());  //把错误提示插入到当前元素中的父元素的同辈元素的下一个元素中    
							}
						}).form();					
				   },
		/*
		  * 页面标签状态切换，默认显示标签状态切换
		  * 参数：
		  *	liObj: 当前的标签对象，如：$('#xx')
		  * idStr: 所有新增的标签对像的id，如：'#xx,#xx'
		  * idStr2: 用于切换详情div层
		  */
		 liActive : function(liObj, idStr, idStr2){
			if(idStr !=''){
				var o = $(idStr);
				o.addClass('tabNone');
				if (o.length == 1) {
					o.siblings('.tabNone').first().addClass('bgImgNone');
				}
				if(idStr2){
					$('#busibox').removeClass('none');
					$(idStr2).addClass('none').html('');
				}
			}	
			liObj.parent('ul').find('a').removeClass('active');
			liObj.removeClass('tabNone').find('a').addClass('active');	
		 },
		 /*
		  * 页面标签状态切换，切换到新增的页面标签
		  * 参数：
		  * liObj: 当前的标签对象，如：$('#xx')
		  */
		liActive_add : function(liObj){
			liObj.removeClass('tabNone');
			liObj.parent('ul').find('a').removeClass('active');
			liObj.find('a').addClass('active');
			liObj.next().removeClass('bgImgNone');
		},
		convertFloat : function (n) {
			var r = parseFloat(n || '0');
			return isNaN(r) ? 0 : r;
		},
		 
		getWidth : function (widthObj) {
			var widthStr = $.trim(widthObj).toLowerCase().replace('px', '');
			var widthInt = parseInt(widthStr);
			if (isNaN(widthInt)) {
				return 0;
			} else {
				return widthInt;
			}
		},
		
		/*实现表格滚动的方法
		 * str : 表格的id
		 */
		scrollTable : function(str){
			var that = this;
			$('#' + str + ' tr:eq(0)').clone().prependTo('#' + str + ' thead');
			$('#' + str + ' tr:eq(0)').css({'z-index': 10, position: 'fixed'}).width($('#' + str + ' tr:eq(1)').width());
			$('#' + str + '_pt_outTab').remove();
			
			var thObjs = $('#' + str + ' tr:eq(1) th');
			thObjs.each(function (i) {
				var thObj = $(this);
				$('#' + str + ' tr:eq(0) th:eq(' + i + ')').height(thObj.height()).width(thObj.width() + that.getWidth(thObj.css('border-left-width')));
			});	
		},
		
		/* 实现select multiple左右添加和删除功能
		 * 参数：
		 * formSelect : 源列表的id, 如：#leftSelect;
		 * toSelect : 目标列表的id, 如：#rightSelect;
		 * alertTxt :  列表为空时显示的文本信息。
		 */
		 
		 selectMultiple_operation : function(formSelect, toSelect, alertTxt){
			if($(formSelect + " option:selected").length > 0){ 
				/*$(formSelect + " option:selected").each(function(){ 					
					$(toSelect).append("<option value='" + $(this).val() + "'>" + $(this).text() + "</option>"); 
					$(this).remove();		   
				});*/
				var optionValue = $(formSelect + " option:selected");
				optionValue.appendTo(toSelect);
			}
			else{
				comm.alert({
					imgFlag : true,
					imgClass : 'tips_error',
					content : alertTxt
				});	
			}	 
		},
		selectMultiple_operation_dbClick : function(currentOption, toSelect){
			var optionValue = $('option:selected', currentOption);
			optionValue.appendTo(toSelect);
		},
		/*日程安排标签滚动 2016-05-07 modify by kuangrb*/
		tab_scroll : function(currentUl){
			$('.tab_arrow_right, .tab_arrow_left').unbind('click');
			var ul_width = 0,
				click_num = 0,
				move_width = 0,
				currentUl_left = 0,
				subNavLi = [],
				div_width = currentUl.parent('div').width();
				currentUl_length = currentUl.children("li").length;
			
			currentUl.children("li").each(function(i){
				subNavLi[i] = $(this).width() + 20;
				ul_width += subNavLi[i];
			});
			
			currentUl.css("left", 0);
			
			if(ul_width > div_width){
				currentUl.css("width", ul_width);
				$(".tab_arrow_right, .tab_arrow_left").removeClass('tabNone');	
			}
			else{
				$(".tab_arrow_right, .tab_arrow_left").addClass('tabNone');
			}
			
			$(".tab_arrow_right").click(function(){

				if(Math.abs(currentUl_left) < (ul_width - div_width)){
					move_width = currentUl_left - subNavLi[click_num];
					currentUl.css("left", move_width + "px");
					currentUl_left = currentUl.position().left;
					click_num += 1;
				}

			});
			
			$(".tab_arrow_left").click(function(){
				
				if(currentUl_left < 0){
					move_width = currentUl_left + subNavLi[click_num - 1];
					currentUl.css("left",move_width + "px");
					currentUl_left = currentUl.position().left;
					click_num -= 1;
				}
				
			});	
			
		},
		
		/*
		 *  解决运算精度问题
		 *  
		 */		
		accDiv : function(arg1,arg2){
			//两数相除
		    var t1=0,t2=0,r1,r2;
		    try{t1=arg1.toString().split(".")[1].length}catch(e){}
		    try{t2=arg2.toString().split(".")[1].length}catch(e){}
		    with(Math){
		        r1=Number(arg1.toString().replace(".",""))
		        r2=Number(arg2.toString().replace(".",""))
		        return (r1/r2)*pow(10,t2-t1);
		    }
		} ,
		accMul : function(arg1,arg2)	{
			//两数相乘
		    var m=0,s1=arg1.toString(),s2=arg2.toString();
		    try{m+=s1.split(".")[1].length}catch(e){}
		    try{m+=s2.split(".")[1].length}catch(e){}
		    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
		} ,
		accAdd : function(arg1,arg2){
			//两数相加
		    var r1,r2,m;
		    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
		    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
		    m=Math.pow(10,Math.max(r1,r2))
		    return (arg1*m+arg2*m)/m
		} 	
	};
});