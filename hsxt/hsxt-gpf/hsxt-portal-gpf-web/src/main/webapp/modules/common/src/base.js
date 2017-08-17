define(  function() { 
		window.Config = {
			//是否屏蔽打印日志，上生产需屏蔽
			isLog  : true ,     
			//生产环境还是测试环境（以备扩展）
			isTest : true ,
			//设置连接的域名，域名列表见同目录的domainList.js
			domain: "gpf_um",
			showFooter:  true ,
			init : function(){	
				/*						
				var log  = window.console && window.console.log  ,
					 dir   =  window.console &&  window.console.dir ,
					 self  = this ;
				if (!log){
						 
						window.console = {} ;
						window.console.log = function(msg){
							comm.alert({imgFlag:true,content:msg});
							//self.isLog &&log.apply(window.console,arguments) ;
						};
						window.console.dir = function(msg){
							comm.alert({imgFlag:true,content:JSON.stringify(msg)});
							//self.isLog &&log.apply(window.console,arguments) ;
						} ;  
				}	 
				*/
				
				 
				
			}
			
			
		} ;
		
		Config.init() ;
		
		return Config ;
});