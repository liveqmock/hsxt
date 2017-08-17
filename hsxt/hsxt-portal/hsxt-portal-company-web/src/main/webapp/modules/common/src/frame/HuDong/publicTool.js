define([],function(){   

	
	var publicTool = {
			 DatetimeNew : function(msgid) {
				 if(msgid != null && msgid !=""){
				   var d = new Date();
					d.setTime(msgid);
					str = '';
					str += d.getFullYear() + '-'; //获取当前年份 
					str += d.getMonth() + 1 + '-'; //获取当前月份（0——11） 
					str += d.getDate();
				 }else{
					 return "";
				 }
					return str;
			},
			NowtimeNew : function(msgid) {
				if(msgid != null && msgid !=""){
				var d = new Date();
				d.setTime(msgid);
				str = '';
				str += d.getFullYear() + '.'; //获取当前年份 
				str += d.getMonth() + 1 + '.'; //获取当前月份（0——11） 
				str += d.getDate() + ' ';
				if(d.getHours()<10){
					str += "0" +　d.getHours() + ':';
				}else{
					str += d.getHours() + ':';
				}
				if(d.getMinutes()<10){
					
					str += "0" +　d.getMinutes() + ':';
				}else{
					
					str += d.getMinutes() + ':';
				}
				if(d.getSeconds() <10 ){
					str += "0" + d.getSeconds() + '';
					
				}else{
					
					str += d.getSeconds() + '';
					
				}
				}else{
					return "";
				}
				return str;
		},
		//字符串替换为图片函数
		Replace_img : function(str){
			var num=str.replace(/\b(0+)/gi,"");
			//var reg =new RegExp("\\[(.| )+?\\]","igm");
			var s = '<i class="i_biaoqing"><img src="resources/img/biaoqing/'+num+'.png"></i>';
			//var reg = /\[[^\)]+\]/g;
			return s;
		
		},
		biaoqingchuli : function(inputValue){
			var imgnum;
			var reg =new RegExp("\\[(.| )+?\\]","igm");
			var arr= inputValue.match(reg);
			if(arr!=null)
			{
				for(i=0;i<arr.length;i++)
				{
					var s = inputValue.indexOf(arr[i]);
					imgnum=inputValue.substr(s,5);
					inputValue=inputValue.replace(imgnum,publicTool.Replace_img(imgnum.substr(1,3)));
				}
			}
			return inputValue;
		},
		valueReplace : function(e){ 
			e=e.toString().replace(new RegExp('(["\"])', 'g'),"”"); 
			return e; 
			},
		xiaoxitishi : function(){
			var bool = 0;
			$.each($("#zxmsg").find("ul>li"),function(k,v){
				if($(v).find(".count").length > 0){
					bool = 1;
					$("#zxspanid").addClass("count");
					return;
				}
			})
			if(bool == 0){
				$("#zxspanid").removeClass("count");
			}else{
				bool = 0;
			}
			
			$.each($("#ddmsg").find("ul>li"),function(k,v){
				if($(v).find(".count").length > 0){
					bool = 1;
					$("#ddspanid").addClass("count");
					return;
				}
			})
			if(bool == 0){
				$("#ddspanid").removeClass("count");
			}else{
				bool = 0;
			}
			
			$.each($("#nbmsg").find("ul>li"),function(k,v){
				if($(v).find(".count").length > 0){
					bool = 1;
					$("#znspanid").addClass("count");
					return;
				}
			})
			if(bool == 0){
				$("#znspanid").removeClass("count");
			}else{
				bool = 0;
			}
		},
		removeCount : function($this){
			var count = $($this).find(".count");
			if($(count).length > 0){
				$(count).removeClass("count");
				publicTool.xiaoxitishi();
			}
		},
		newStr : function(xmppType){
			if(xmppType == 0){
				$.each($("#zxmsg").find("ul>li"),function(k,v){
					if($(v).find(".count").length > 0){
						$("#zxspanid").addClass("count");
						return;
					}
				})
			}else if(xmppType == 1){
				$("#ddspanid").addClass("count");
			}else if(xmppType == 2){
				$("#znspanid").addClass("count");
			}
		},
		getRandom : function(n){
	        return Math.floor(Math.random()*n+1);
	    }
	}
	return publicTool;
})