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

	
reqConfig.setLocalPath("coDeclaration"); 

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
require(["coDeclarationSrc/alert"],function( ){	
 
});
require(["coDeclarationSrc/select"],function( ){	
 
});
//加载日期
$( "#establishDate" ).datepicker({dateFormat:'yy-mm-dd'});	
$( "#startDate" ).datepicker({dateFormat:'yy-mm-dd'});
$( "#stopDate" ).datepicker({dateFormat:'yy-mm-dd'});	
//输入框获取焦点时候显示提示内容
$("#zzdqyglh").focus(function(){
	$("#zzdqyglh").attr("title","如因业务关系需填写下属企业管理号，须填写完并刷新增值点数据后再选择增值点，否则不需输入直接选择增值点则可。").tooltip({
		position:{
			my:"left top+33",
			at:"left top"	
		}
	}).tooltip("open");
});

$("#zzdqyglh").blur(function(){
	$("#zzdqyglh").attr("title","").tooltip( "destroy" );
});
$("#zzdqyglh1").focus(function(){
	$("#zzdqyglh1").attr("title","如因业务关系需填写下属企业管理号，须填写完并刷新增值点数据后再选择增值点，否则不需输入直接选择增值点则可。").tooltip({
		position:{
			my:"left top+33",
			at:"left top"	
		}
	}).tooltip("open");
});

$("#zzdqyglh1").blur(function(){
	$("#zzdqyglh1").attr("title","").tooltip( "destroy" );
});
$("#zzdqyglh2").focus(function(){
	$("#zzdqyglh2").attr("title","如因业务关系需填写下属企业管理号，须填写完并刷新增值点数据后再选择增值点，否则不需输入直接选择增值点则可。").tooltip({
		position:{
			my:"left top+33",
			at:"left top"	
		}
	}).tooltip("open");
});

$("#zzdqyglh2").blur(function(){
	$("#zzdqyglh2").attr("title","").tooltip( "destroy" );
});
//顺序选配和人工选配企业管理号切换
$("#sxxp1").click(function(){
	if($(this).is(":checked")){
		//$("#xpqyglh1").attr("disabled")
		$("#xpqyglh1").removeClass("none");
		$("#xpqyglh2").addClass("none");
	}
});
$("#rgxp1").click(function(){
	if($(this).is(":checked")){
		//$("#xpqyglh1").removeAttr("disabled")
		$("#xpqyglh1").addClass("none");
		$("#xpqyglh2").removeClass("none");
	}
});
$("#sxxp2").click(function(){
	if($(this).is(":checked")){
		$("#xpqyglh3").removeClass("none");
		$("#xpqyglh4").addClass("none");
	}
});
$("#rgxp2").click(function(){
	if($(this).is(":checked")){
		$("#xpqyglh3").addClass("none");
		$("#xpqyglh4").removeClass("none");
	}
});
//自动完成
$(function() {
	  var availableTags = [
		"05001110000",
		"05001120000",
		"05001130000",
		"05001140000",
		"05001150000",
		"05001160000",
		"05001170000",
		"05001180000",
		"05001190000",
	  ];
		$( "#xpqyglh2" ).autocomplete({
		  source: availableTags
		});
});
$(function() {
	  var availableTags = [
		"05001110000",
		"05001120000",
		"05001130000",
		"05001140000",
		"05001150000",
		"05001160000",
		"05001170000",
		"05001180000",
		"05001190000",
	  ];
		$( "#xpqyglh4" ).autocomplete({
		  source: availableTags
		});
});
//调用自动完成组合框
$("#brandSelect").combobox();
	$(".ui-autocomplete").css({
		"max-height":"120px",
		"overflow-y":"auto",
		"overflow-x":"hidden",
		"height":"120px",
		"border":"1px solid #CCC"
	});
$(".combobox_style").find("a").attr("title","显示所有选项");
//end


});
