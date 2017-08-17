define(  function() {
    var self = this ;
		window.Config = {
			//是否屏蔽打印日志，上生产需屏蔽
			isLog  : true ,
			//生产环境还是测试环境（以备扩展）
			isTest : false ,
			//设置连接的域名，域名列表见同目录的domainList.js
			domain: "local",

			init : function(){

				if ( window.console ){
					var log  = window.console.log ,
						dir  = window.console.dir ;

					window.console.log = function(){
						self.isLog
							&&log.apply(window.console,arguments) ;
					};
					window.console.dir = function(){
						self.isLog
							&&dir.apply(window.console,arguments) ;
					} ;
				} else {
					window.console ={
						log : function(msg){
							self.isLog && alert(msg);
						},
						dir : function(obj){
							self.isLog && alert($.param(obj));
						}
					}
				}

			}

		} ;

		window.Config.init() ;

		return window.Config ;
});