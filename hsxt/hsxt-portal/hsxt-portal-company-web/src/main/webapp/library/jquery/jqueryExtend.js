(function($){
		
//		 $.fn.serializeJson=function(){
//			 var serializeObj={};
//			 $(this.serializeArray()).each(function(){
//			 serializeObj[this.name]=this.value;
//			 });
//			 return serializeObj;
//		 };
		 
		 $.fn.itemDetailUrl=function(domain){
			 $(this).unbind("click").bind("click",function(){
				 var did = domain+"?"+$(this).attr("data-url");
				 if(did){
					 window.open(did);
				 }else{
					 alert("商品信息异常！");
				 }
				 
			 });
		 };
		 
		 $.fn.orderDetailUrl=function(){
			 $(this).click(function(){
				 var did = $(this).attr("data-id");
				 comm.navOrderDUrl(did);
			 });
		 };
		 $.fn.extend({
				BigPicLook:function(options){
					var defaults={
					"pic_list":new Array(),
					"index":0,
					"maxli":0,
					"strpiclist":"",
					"strbigbox":"",
					"ul":"",
					"li":"",
					"pic_w":600,
					"pic_h":400,
					"width":600,
					"height":400
					}
					
					var options=$.extend(defaults,options);
					$(this).find("img").click(function(){
						options.strpiclist="";
						options.strbigbox="";
						options.index=$(this).parents("li").index();
						options.ul=$(this).parent().parent();
						options.ul.children("li").each(function(index, element) {
							options.strpiclist+="<li><div class='pic_box'><img src='"+options.ul.children("li").eq(index).find("img").attr("src")+"' /></div></li>";
		                });
						options.maxli=options.ul.children("li").last().index();
						
						options.strbigbox="<div class='all_thickdiv' style='display: block;'></div>"+
					              "<div class='bigpicture_box'><div class='l_r_tab prebtn'></div><div class='l_r_tab nextbtn'></div><div class='close_btn'></div>"+
								  "<div class='pic_list'><ul>"+options.strpiclist+"</ul></div>"+
								  "</div>";
					
					$(document.body).append(options.strbigbox);
						lishow();
					function lishow(){
						options.li=$(document.body).find(".bigpicture_box .pic_list ul li").eq(options.index);
						options.li.show().siblings("li").hide();
						options.pic_w=options.li.find("img").width();
						options.pic_h=options.li.find("img").height();
						
						
						
						
						if(options.pic_w>options.width&&options.pic_h<options.height)
						{	
							options.li.find("img").width(options.width);
							options.li.find("img").height();
						}
						if(options.pic_w<options.width&&options.pic_h>options.height)
						{	
							options.li.find("img").width();
							options.li.find("img").height();
						}
						if(options.pic_w>options.width&&options.pic_h>options.height)
						{
							if(options.pic_w/options.pic_h<3/2)
							{
								options.li.find("img").width();
								options.li.find("img").height(options.height);
							}
							else
							{
								options.li.find("img").width(options.width);
								options.li.find("img").height();
							}
						}
						$(".bigpicture_box").css({"width":options.width,"height":options.height,"margin-left":-options.width/2,"margin-top":-options.height/2});
						$(".bigpicture_box .pic_list li .pic_box").css({"width":options.width,"height":options.height});
					}
						
						$(".bigpicture_box").on("click",".close_btn",function(){
							$(".all_thickdiv").remove();
							$(".bigpicture_box").remove();
							
						});
						
						$(".bigpicture_box").on("click",".prebtn",function(){
							options.index--;
							if(options.index<0)
							{options.index=options.maxli;}
							lishow();
							});
						$(".bigpicture_box").on("click",".nextbtn",function(){
							options.index++;
							if(options.index>options.maxli)
							{options.index=0;}
							lishow();
							});
					
					});
				}
		 });
		 
		 jQuery.extend({
			 /**
			  * 公用提示框
			  */
			 commAlert:function(content,callback) {
				 var $this = $('#dialog');
				 $this.dialog({title:'提示信息',width:312,height:161,resizable:false,
					 open: function() {
						$this.html(content);
			         },
					 buttons:{
						"确定":function(){
							$this.dialog("destroy");
							if(callback){
								callback();
							}
						}
					}  
				});	
			},
			/**
			  * 确认提示框
			  */
			 confirmAlert:function(title,content,okCall,cancelCall) {
				 var $this = $('#dialog');
				 $this.dialog({title:title,width:312,height:161,resizable:false,
					 open: function() {
						$this.html(content);
			         },
					 buttons:{
						"确定":function(){
							$this.dialog("destroy");
							if(okCall){
								okCall();
							}
						},
						"取消":function(){
							$this.dialog("destroy");
							if(cancelCall){
								cancelCall();
							}
						}
					}
					
				});	
			},
			 /**
			 * 页面获取请求url 参数
			 */
			getQueryString:function(name) {
				var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
				var search =  encodeURI(window.location.search);
			    var r = search.substr(1).match(reg);
			    if (r != null) return unescape(r[2]); return null;
			},
			replaceParam:function(name,val,rVal) {
				var url=document.URL;
				var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
				var r = window.location.search.substr(1).match(reg);
			    if(r){
			    	var v=eval("/"+name+"="+rVal+"/g");
					url =url.replace(v,name+"="+val);
			    }else{
			    	url = url +"&"+name+"="+val;
			    }
			    return url;
			},
			/**
			 * 将url参数转成对象
			 */
			getParam:function() {
			  var url=document.URL;
			  var param={};
			   if(url.lastIndexOf("?")>0){
			        url = url.substring(url.lastIndexOf("?")+1,url.length);
					var arr = url.split("&");
					for(var i=0;i<arr.length;i++){
					   var key = arr[i].split("=")[0];
					   var val = arr[i].split("=")[1]
					   param[key] = val;
					}
			   }
			   return param;
			},
			/**
			 * 刷新url链接
			 */
			pushState:function(url){
				var flag = false;
				try{
					if (window.applicationCache) {
		                flag = true;
		            }
				}catch (e) {
					flag = false;
				}
				if(flag){
					history.pushState({}, "refreshList", url);
				}else{
					//location.href=url;
				}
			},
			animateTop :function(obj,num){
				$("html,body").animate({scrollTop:obj.offset().top},num);
			},
			/**
			 * 数字转金额格式
			 */
			formatMoney:function(number,thousand, decimal){
				
				var result = isNaN((1 * number).toFixed(2)) ? (new Number(0).toFixed(2)) : (1 * number).toFixed(2);
				return result;
//				var places = 2;
//		        thousand = thousand || ",";
//		        decimal = decimal || ".";
//		        var negative = number < 0 ? "-" : "",
//		            i = parseInt(number = Math.abs(+number || 0).toFixed(2), 10) + "",
//		            j = (j = i.length) > 3 ? j % 3 : 0;
//		        return negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
			},
			/**
			 * 收藏
			 */
			addBookmark:function(a, title, url){
				url = url || a.href;
				title = title || a.title;
				try{ // IE
					window.external.addFavorite(url, title);
				} catch(e) {
					try{ // Firefox
						window.sidebar.addPanel(title, url, "");
					} catch(e) {
						if (/Opera/.test(window.navigator.userAgent)) { // Opera
							a.rel = "sidebar";
							a.href = url;
							return true;
						}
						$.commAlert('加入收藏失败，请使用 Ctrl+D 进行添加')
					}
				}
				return false;
			},
			// 处理分页过多问题
			getPageArr : function(tp,cp){
				var c = 5;
		  		var arr = new Array();
				if(cp <= c){
					if(tp>c){
						for(var i = 1;i<=c;i++){
							arr.push(i);
						}
						arr.push("...");
					}else{
						for(var i = 1; i<=tp;i++){
							arr.push(i);
						}
					}
				}else{
					arr.push("1");
					arr.push("2");
					arr.push("...");
					if(tp >cp+2){
						for(var i = cp-2; i<=cp+2;i++){
							arr.push(i);
						}
						arr.push("...");
					}else{
						for(var i = cp-2; i<=tp;i++){
							arr.push(i);
						}
					}
				}
				return arr;
			},
			// 防止html代码注入
			inject : function(val){
				//val = val.replace(/<script>/g," ");
				//val = val.replace(/<\/script>/g," ");
				val = val.replace(/<\//g," ");
				val = val.replace(/</g," ");
				val = val.replace(/>/g," ");
				return val;
			},
			getAddress : function(obj){
				var city = obj.city;
				var cityArr = ["北京市","重庆市","天津市","上海市"];
				$.each(cityArr,function(key,c){
					if(c.indexOf(city)>=0){
						city="";
					}
				});
				
				var address = obj.province + city + (obj.area ? obj.area : "" ) +obj.addr;
		  		try{
	  				var areaArr = eval(obj.area);
		  			address = obj.province + city + (areaArr[1] ? areaArr[1] : "" ) +obj.addr
		  		}catch(e){}
		  		return address;
			}
		});
})(jQuery);