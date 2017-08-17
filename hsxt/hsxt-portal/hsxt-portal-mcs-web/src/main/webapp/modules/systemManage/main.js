/*
 *加载全局对象
 * 1.含有jquery ,jquery plugins , underscore,jquery.cookie,jquery.datatable,jquery.dialog,jquery.validate   项目的公用方法等
 * 2.使用公用方法：comm.functionName(XXX)...
 * 3.引用公用的路径 
       公用src :   'commSrc'
	   公用css:    'commCss'
	   公用模板：'commTpl'
	   公用数据：'commDat'
 * 4.模板自动填充数据，局部刷新
 */

	
reqConfig.setLocalPath("systemManage"); 

require.config(reqConfig);



define(['GY'], function(GY) {
 

/*
 *  加载头部 
 *
 */
require(["commSrc/frame/header",],function(tpl){	

});

 
/*
 *  加载左边导航栏
 *
 */
require(["commSrc/frame/sideBar"],function(tpl){	

});
 	 

/*
 *  加载右边服务模块
 *
 */
require(["commSrc/frame/rightBar"],function(tpl){	

});


/*
 *  加载中间菜单
 *
*/
require(["commSrc/frame/nav"],function(tpl){	

}); 


/*
 *  加载页脚
 *
 */
require(["commSrc/frame/footer"],function(src){	
 
});

/*
 *  加载脚本
 *
 */

require(["systemManageSrc/select"],function( ){	
 
});
require(["systemManageSrc/click"],function( ){	
 
});
require(["systemManageSrc/alert"],function( ){	
 
});

$( ".serviceItem" ).accordion();
	
});
