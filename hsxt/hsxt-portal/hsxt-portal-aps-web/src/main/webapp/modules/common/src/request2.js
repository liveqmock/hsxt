define(['GY','commSrc/commUrl', 'commSrc/comm'], function(GY,url) {
 	/*
	 * 2014/12/22修正多个接口请求问题
	 * 2015/02/04加了错误代码拦截，如可导航到登录或指定的路径
	 */
    // 请求中的XHR队列
    var xhrs = {};
    var requestData = {};
    var limits = {};
    
    var length;
    
    var ajaxSettings = $.ajaxSettings;

    var responseCache = {
    	
    }

    // 接口数据缓存对象
	
	comm.killRequest = function(urlList){
		// urlList 需要取消掉的url的地址     如果urlList为空 表示取消掉所有的请求
		if(urlList){
			if(!$.isArray(urlList)){
				urlList =[urlList];
			}
			$.each(urlList,function(i,url){
				if(url in xhrs){
					xhrs[url].isAbort="1";
					xhrs[url].abort();
					delete xhrs[url];
				}
			});	
		}else{
			for(key in xhrs) {
				xhrs[key].isAbort="1";
				xhrs[key].abort();
			}
			//隐藏加载进度图标，后期扩展
 			
		}
 
		 
	};
 
	//清楚缓存数据....
	comm.clearCache = function(urlList){
		if(urlList){
			if(!$.isArray(urlList)){
				urlList =[urlList];
			}
			$.each(urlList,function(i,url){
			 
				if(url in responseCache){
					responseCache[url] = false;
				}
			});	
		}else{
			for(var url in responseCache) {
				 
				responseCache[url] = false;
			}			
		}
	}
	 
	//格式化 options
    function formatOptions(options){
	 
    	var isJson = true ;
	 
		//comm.getDomainUrl( name  , Config.domain )
		if (options.domain){
			options.urlKey =comm.getDomainUrl(  options.url  , options.domain );		
			//options.urlKey = options.url;
    		options.url = comm.getDomainUrl(  options.url  , options.domain );	
		} else {
			options.urlKey =comm.getDomainUrl(  options.url  , Config.domain );		
			//options.urlKey = options.url;
    		options.url = comm.getDomainUrl(  options.url  , Config.domain );
		} 
    	//60秒后断开请求....
    	options.timeout = options.timeout || 60000;
        // 响应后处理
        var complete = options.complete || function(){};
        // 成功回调函数
        var success  = options.success || function(){};
        var error    = options.error || function(){};
        
 
        !options.type 
        	&&(options.type = "post");      
		 
	  	if(!options.dataType){
	        if(isJson){
	        	options.dataType = 'json'; 
		         	
	        }else{
	        	options.dataType = 'text/xml';        	
	        }
	  	}

        options.error = function(response,type) {
 
        	
        	if(!response){return;}
			//如果ajax的请求是要求json格式而接口返回非json数据 也会进入error 类型是parsererror
			if(type == "parsererror"){
				error(response);
				return;
			}
			
			var request = requestData[response.url]
        	//如果是手动取消掉的   不需要弹框			
			if(!response.isAbort){
				if(!response.noerror){
				 
					alert("网络连接超时,请检查您的网络设置或重试？");
				 
				}
			}
        }

        options.complete = function(response) {
        	// 移除队列
        	delete requestData[options.urlKey];
        	delete xhrs[options.urlKey];
            if(complete) {
                complete(response);
            }
            //ajaxSettings.complete();        
        }     		
    }
    			
    /*
     * 串行发送请求
     */ 
    function processResquestSyn(lists,responseLists,errorList,callback,error){
    	var my = arguments.callee,
    		isJson = true,
    	    options = lists.shift();
    	    		
    	formatOptions(options);        	
        
        options.error = function(response) {
            responseLists.push("error");
			errorList.push(true);
			
			var n = length-lists.length-1;
 
			//如果还有其他请求  并且没有限制条件        	
        	if(lists.length>0&&!limits[n]){        		
            	my(lists,responseLists,errorList,callback,error);	
        	}else{
        		length>1
					&&callback.apply(null,responseLists);
				error.apply(null,errorList);
        	}        	
        }
        
        var success = function(response) {
            
            responseLists.push(response);
			errorList.push(undefined);
			/*
			//session过期....
			if(response&&response.retCode){
				var errCode = response.errCode;
				if( errCode =="100" || errCode =="400"){
					//超时  清空掉之前缓存的数据.......
					comm.clearCache();	
					alert('系统出错');
									
				 
					return false;
				}
				//缓存的多个接口
				if((errCode == "000" || errCode == "00") && (options.urlKey in responseCache))	{
					responseCache[options.urlKey] = response;
				}
			}
			*/
			
			if(response&&response.retCode){ 
					var errCode = response.retCode;
					if(errCode){
						//特殊代码处理	
						var evenObj = options.even;
						switch(errCode){
							case comm.retCode[0]:
								//出错跳转
								comm.navIndex('login') ;
								return;
								break ;
							case comm.retCode[1]:							
								if (evenObj.value.moduleName &&　evenObj.value.urlName){
									//指定模块名
									comm.navUrl(evenObj.value.moduleName ,evenObj.value.urlName);
								} else　if( evenObj.value.urlName ) {
									//未指定模块名
									location.href = evenObj.value.urlName;
								}
								return;
								break ;
							case comm.retCode[2]:{
								
							}
							default:
							
							break ;	
						}
						
						/*
						if( errCode =="100" || errCode =="400"){
							//超时  清空掉之前缓存的数据.......
							comm.clearCache();
						 	
							return false;
						}
					 	
						//缓存的多个接口
						if((errCode == "000" || errCode == "00") && (options.urlKey in responseCache))	{
							responseCache[options.urlKey] = response;
						}	
						 */				
					}
			}
			
			
				            
            if(lists.length>0){
            	var n = length-lists.length-1;
            	if(!limits[n]||(limits[n]&&limits[n](response))){
            		my(lists,responseLists,errorList,callback,error);
            	}else{
            		$.each(new Array(length-responseLists.length),function(){
            			responseLists.push("error");
            		});
 					
 					try{
						//当请求全部正确的时候
						callback.apply(null,responseLists);					
					 }catch(e){
						alert('系统繁忙，请稍后重试！');
						 
					}               		            		
            	}						              	
            }else{
				try{
	            	callback.apply(this,responseLists); 
	            	errorList.join("")!=""
	            		&&error.apply(null,errorList);						
				}catch(e){
					alert('系统繁忙，请稍后重试！');
					 
				}           	          	
            }         		
        }
        
		//如果设置了缓存  并且存在缓存的数据  不从服务器拉数据.......
		if(!options.noCache&&responseCache[options.urlKey]){
			success(responseCache[options.urlKey]);				
		}else{
	        options.success = success;
	        if(typeof options.beforeSendCallback == "function"){
	        	ajaxSettings.beforeSendCallback = function(xhr){options.beforeSendCallback(xhr)}	
	        }
            
			var xhr = $.ajax(options);
			xhr.url = options.urlKey;
			xhrs[options.urlKey] = xhr;
		}	                                                	
    }

/*
 同步发送请求
*/
	function processResquest(lists,responseLists,callback,error){
 		
    	var isJson = true,
    		len = lists.length,
    		errArr = new Array(len),
    		arr    = new Array(len);
		
		$.each(lists,function(i,options){
		 		
			options.error = function(){
				len--;
				arr[i] = "error";
				errArr[i]=true;
		 
				if(len==0){
					length>1
						&&callback.apply(null,arr);
					error();
				}      													
			};
			formatOptions(options);			
			var success = function(response) {
		 
				len--;
				arr[i] = response;
				
				if(response){ 
					var errCode = response.retCode;
					if(errCode){
						//特殊代码处理	
						var evenObj = options.even;
						switch(errCode){
							case comm.retCode[0]:
								//出错跳转
								comm.navIndex('login') ;
								return;
								break ;
							case comm.retCode[1]:							
								if (evenObj.value.moduleName &&　evenObj.value.urlName){
									//指定模块名
									comm.navUrl(evenObj.value.moduleName ,evenObj.value.urlName);
								} else　if( evenObj.value.urlName ) {
									//未指定模块名
									location.href = evenObj.value.urlName;
								}
								return;
								break ;
							case comm.retCode[2]:{
								
							}
							default:
							
							break ;	
						}
						
						/*
						if( errCode =="100" || errCode =="400"){
							//超时  清空掉之前缓存的数据.......
							comm.clearCache();
						 	
							return false;
						}
					 	
						//缓存的多个接口
						if((errCode == "000" || errCode == "00") && (options.urlKey in responseCache))	{
							responseCache[options.urlKey] = response;
						}	
						 */				
					}																						
				}
				if(len==0){						
					try{
						//当请求全部正确的时候
						callback.apply(null,arr);
						errArr.join("")!=""
						 	&&error.apply(null,errArr); 						
					}catch(e){
						alert( "系统繁忙，请稍后重试！");
						 
					}						
				}
	        }
			//如果设置了缓存  并且存在缓存的数据  不从服务器拉数据.......
			if(!options.noCache&&responseCache[options.urlKey]){
				success(responseCache[options.urlKey]);				
			}else{
		        options.success = success;
		        if(typeof options.beforeSendCallback == "function"){
		        	ajaxSettings.beforeSendCallback = function(xhr){options.beforeSendCallback(xhr)}	
		        }
				var xhr = $.ajax(options);
				xhr.url = options.urlKey;
				xhrs[options.urlKey] = xhr;
			}
		});  				
	}

    comm.Request = function(name, options) { 
   		limits = {};	 
		var success  = $.isFunction(options.success) ?options.success: function(){},
			error = $.isFunction(options.error) ?options.error: function(){};    	
		if( $.isArray(name) ){
			
			length = name.length ;
			if(options.syn){				
				$.each(name,function(i,d){
					 
					requestData[d.url] = options;
					if(typeof d.limit == "function")
						limits[i] = d.limit ;
				});
				processResquestSyn(name,[],[],success,error) ;				
			}else{
				processResquest(name,[],success,error) ;
			}			
		} else if ($.isPlainObject(name) ){			 	 
			options.url = comm.getDomainUrl( name.url  , name.domain ) || '';
			requestData[name] = options;						
			processResquest([options],[],success,error) ;
			length = 1 ; 
			
		} else{
			options.url = comm.getDomainUrl( name  , Config.domain ) || '';
			requestData[name] = options;						
			processResquest([options],[],success,error) ;
			length = 1 ;
		}
    }
    

    
    return comm.Request;
});










 














