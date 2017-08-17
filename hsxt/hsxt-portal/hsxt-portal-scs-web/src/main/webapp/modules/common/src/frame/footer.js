
define(["text!commTpl/frame/footer.html"],function(tpl){
	//加载脚部文件
 
	$('#footer').html(tpl);
	/*右边模块折叠效果*/
	$('#serviceTab_btn').click(function(){
		var f = $('#service').toggleClass('none').hasClass('none');
		$(this).attr('title', !f ? '收起右栏' : '显示右栏').children('i').toggleClass('serviceTab_arrow_left');
		$('.content').css('margin-right', !f ? '248px' : '0');
	}).bind('mouseover', function(){
		$(this).removeClass('ui-state-default').addClass('ui-state-active');
	}).bind('mouseout', function(){
		$(this).removeClass('ui-state-active').addClass('ui-state-default');
	});
    /*end*/ 
     $('.operationsArea').resize( function(){       	 
		var operationsArea_height = $('.operationsArea').height();
		$('.sideBar_menu').height(operationsArea_height + 112);
		$('#sideBarBox').children('div').css('height', 'auto');			
	 });
	 
	return tpl;	
});	