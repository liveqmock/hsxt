define(["commSrc/commFunc"] , function() {
		window.comm = {				
				//401出错跳转，402出错提示 
				retCode : ['401','402','403','404','500','','','','8888','9999'],
				
				/*
				 * 设置全局缓存
				 */
				cache :  {},				
				
			    /*
				 *   取模块首页路径
				 */
				 getIndex : function(moduleName){
					 	return globalPath+"modules/"+ moduleName +"/tpl/index.html" ;
				 },
				 /*
				  *导航（链接）到模块的首页
				  */
				 navIndex : function(moduleName) {
						location.href = comm.getIndex(moduleName) ; 
				 },
				 
				 /*
				  * 取任何指定的页面路径
				  */
				 getUrl : function(moduleName,urlName){					 
						 return globalPath+"modules/"+ moduleName +"/tpl/"+urlName ;
				 },
				 /*
				  *导航（链接）到指定的模块及页面路径
				  */
				 navUrl : function(moduleName,urlName) {
					 	location.href = comm.getUrl(moduleName,urlName) ; 
				 },
			   
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
					return year +'-'+month + '-' + day;
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
				   
				  //自动宽度
				  autoWidth:function(){
						var container = document.getElementById("wrap");
						var contentWidth_1 = document.getElementById("contentWidth_1");//宽度为500px所定义的ID
						var contentWidth_2 = document.getElementById("contentWidth_2");//宽度为320px所定义的ID
						var contentWidth_3 = document.getElementById("contentWidth_3");//宽度为800px所定义的ID
						var contentWidth_4 = document.getElementById("contentWidth_4");//宽度为700px所定义的ID
						var width = document.documentElement.clientWidth;//获取浏览器可见区域的宽度
							
						if(width <= 1200){
							container.style.width  = "1200px";
						}else{
							container.style.width  = "100%";	
						}
						if(width <= 830){
							if(contentWidth_1){
								contentWidth_1.style.width = "500px";
							}
							if(contentWidth_2){
								contentWidth_2.style.width= "320px";
							}
							if(contentWidth_3){
								contentWidth_3.style.width= "800px";
							}
							if(contentWidth_4){
								contentWidth_4.style.width= "700px";
							}
							
						}else{
							if(contentWidth_1){
								contentWidth_1.style.width = "100%";
							}
							if(contentWidth_2){
								contentWidth_2.style.width = "100%";
							}
							if(contentWidth_3){
								contentWidth_3.style.width = "100%";
							}
							if(contentWidth_4){
								contentWidth_4.style.width = "100%";
						}						
					}
							
				} ,
				
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
					 $('#dialog-confirm > p').html(obj.content || '您真的要删除吗？');
					 var btnOkName = obj.ok || '确定',
					 	 btnOther = obj.del || '删除',
					 	 btnCancelName = obj.cancel || '取消';
				     
					 var btnObj ={};
					 btnObj[btnOkName] =   function(){ obj.callOk && obj.callOk(); $(this).dialog("destroy");};
					 obj.other && (btnObj[btnOther] = function(){ obj.callOther && obj.callOther(); $(this).dialog("destroy");});
					 btnObj[btnCancelName] = function(){ obj.callCancel && obj.callCancel() ;$(this).dialog("destroy");}; 
					 
					 $("#dialog-confirm").dialog({  
					 	 width : obj.width  || 320 ,	 
						 height: obj.height || 160 , 
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
					 $('#dialog-alert').attr('title', '温馨提示')	 ;
					 $('#dialog-alert > p').html(content || '操作!'); 
					 
					 $("#dialog-alert").dialog({ 
						 width : 320 ,
						 height: 160 , 				  
						 buttons: {
							'确定' :  function(){  $(this).dialog("destroy");} 	 
						 }
					 });	 
				 } ,
				 
				   
				 
				
									
				 
		} ;
	 
		return window.comm ;
});