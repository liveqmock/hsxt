define(["text!commTpl/frame/nav.html" ],function(tpl){
	//加载中间菜单  
	//$('#menu-tab').html(_.template(tpl  ,  comm.lang('common')   ) );
	/*
     *  设置左边坚标签功能
	 *  设置td宽度
	 *  设置tab box的宽度
	 */	
	 return {
	 	setSideMenu : function(){
	 			$('#menu-box').parent().css("width", $('#menu-box').width() );	
	 			if ( $(window).width() -$('#menu-box').width()-40 > 1100 ){
	 				//$('#navTab').css('width',  $(window).width() -$('#menu-box').width()-40 );
	 			}
	 			 
				//$('table .main-content').css("width", $(window).width() -$('#menu-box').width()-40 );
				//设置左边菜单的显示和隐藏
				$('#sideTab').click(function(){
					if ( $('#menu-box').hasClass('none')  ){
						//设置键头方向 
						$('#sideTab >i').removeClass('sideTab_arrow_right');
						$('#sideTab >i').addClass('sideTab_arrow_left');
						
						$('#menu-box').removeClass('none');
						//设置左td宽度
						$('#menu-box').parent().css('width', $('#menu-box').width() );
						//设置右td宽度
						$('#main-content').css("width", $(window).width() -$('#menu-box').width()-40 );
					} else {
					    //设置键头方向 
					    $('#sideTab >i').addClass('sideTab_arrow_right');
						$('#sideTab >i').removeClass('sideTab_arrow_left');
						
						$('#menu-box').addClass('none');
						$('#menu-box').parent().css('width','0px');
						$('#main-content').css("width",'100%');
					}
				});
	 	} ,
	 	
	 	setSideTab  : function(){	 		
				//重设sideTab位置
				var resizeFlag = false ;
				$('#sideTab').css('top',($(window).height()/2-70+$(window).scrollTop() )+'px');
				$(window).resize(function(){
					if (resizeFlag) {return ;};
					resizeFlag = true ;
					setTimeout(function(){						 
						$('#sideTab').css('top',($(window).height()/2-70+$(window).scrollTop())+'px');		
						resizeFlag = false ;				
					},100);	
				});
				
				//滚动条，重设sideTab 位置
				var scrollFlag = false ;
				$(window).scroll(function(){
					if (scrollFlag) {return ;};
					scrollFlag = true ;
					setTimeout(function(){
						$('#sideTab').animate({'top' : ($(window).scrollTop()+ $(window).height()/2-70)  +'px' },'fast' ,'linear' ,function(){
							scrollFlag = false ;
						}) ;
					},100);					
					
				});
	 	} , 
	 	
	 	setTabs : function(){
	 			
				this.setSideMenu();
				//滚动提示
				$('#main-tab').mouseover(function(e){		
					if ($('#roll_box').hasClass('none')){
						$('#roll_box').removeClass('none');
					} 					
				});
				$('#main-tab').mouseleave(function(e){		
					if (!$('#roll_box').hasClass('none')){
						$('#roll_box').addClass('none');
					} 					
				});
				
				
				//绑定ul标签动作		 		
		 		 
				$('#navTab').data('move',0);
				$('#navTab').data('posX' ,0);		
				$('#navTab').data('left'   ,0);
				
				$('#navTab').mousedown(function(e){		 
					$(this).data('move','1');
					$(this).data('posX',e.pageX) ;
				 	$(this).data('left', $(this).find('ul').position().left );				 
				});
				
				$('#navTab').mousemove(function(e){	
					//如果ul宽度小于父div宽度，则不
					if (  $(this).find('ul').width()  <=$(this).width()+100 ) {  return ;} ;
				 
					if ( $(this).data('move') == "1"  ){		 
						   var dist = e.pageX - $(this).data('posX')*1   ; 
						    //console.log(-($(this).find('ul').width()-$(this).find('ul').parent().width() -100) )
							if ( $(this).find('ul').css('left').replace('px','') > 0      ){
								$(this).find('ul').css('left'  , ( $(this).data('left') +dist )/2 +'px' ) ;
							} else {
								$(this).find('ul').css('left'  , ( $(this).data('left') +dist ) +'px' ) ;
							} 
					}
				}); 
				
				$('#navTab').bind('mouseup',function(e){ 	
		 			
		 			if  ( $(this).find('ul').css('left').replace('px','') == '0' ) { return ;};	 
		 			 
					$(this).data('move',0);
					$(this).data('posX',0) ;
					$(this).data('left',0)   ;
					 
					if ( $(this).find('ul').css('left').replace('px','') > 0  ){
						$(this).find('ul').animate({left:'0px'},'fast');
						return;
					}  
					if (  $(this).find('ul').css('left').replace('px','') < -($(this).find('ul').width()-$(this).find('ul').parent().width() -100) && $('#main-tab >li').length>5 ){
						$(this).find('ul').animate({left: -($(this).find('ul').width()-$(this).find('ul').parent().width()-100)+ 'px'},'fast');
						return;
					}  
				}); 
				
		 		$('#navTab').bind('mouseleave',function(e){ 	
		 			
		 			if  ( $(this).find('ul').css('left').replace('px','') == '0' ) { return ;};	 
		 			 
					$(this).data('move',0);
					$(this).data('posX',0) ;
					$(this).data('left',0)   ;
					 
					if ( $(this).find('ul').css('left').replace('px','') > 0  ){
						$(this).find('ul').animate({left:'0px'},'fast');
						return;
					}  
					if (  $(this).find('ul').css('left').replace('px','') < -($(this).find('ul').width()-$(this).find('ul').parent().width() -100) && $('#main-tab >li').length>5 ){
						$(this).find('ul').animate({left: -($(this).find('ul').width()-$(this).find('ul').parent().width()-100)+ 'px'},'fast');
						return;
					}  
			 
				});  
				
				this.setSideTab(); 
				 
	 			
	 	}
	 	 	
	 }
	 	 
		
	
	return null ;	
});