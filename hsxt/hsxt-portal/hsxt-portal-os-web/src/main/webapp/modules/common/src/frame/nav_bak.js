define(["text!commTpl/frame/nav.html" ],function(tpl){
	//加载中间菜单  
	//$('#menu-tab').html(_.template(tpl  ,  comm.lang('common')   ) );
	/*
     *  设置左边坚标签功能
	 *  设置td宽度
	 *  设置tab box的宽度
	 */	
	 return {
	 	setTabs : function(){
	 			$('#menu-box').parent().css("width", $('#menu-box').width() );		
				$('#main-tab').parent().css('width',  $(window).width() -$('#menu-box').width()-40 );
				 
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
				
				//绑定ul标签动作
		 
				$('.menu-tab').data('move',0);
				$('.menu-tab').data('posX' ,0);		
				$('.menu-tab').data('left'   ,0);
				
				$('.menu-tab').mousedown(function(e){			 
					//moveFlag = true ;
					//posX =  e.PageX;
					$('.menu-tab').data('move','1');
					$('.menu-tab').data('posX',e.pageX) ;
				 	$('.menu-tab').data('left', $('#main-tab').position().left );
					//console.log($('.menu-tab').data('posX') );
				});
				
				$('.menu-tab').mousemove(function(e){	
					//如果ul宽度小于父div宽度，则不
					if (  $('#main-tab').width()  <=$('.menu-tab').width()+100 ) {  return ;} ;
				 
					if ( $('.menu-tab').data('move') == "1"  ){		 
						   var dist = e.pageX - $('.menu-tab').data('posX')*1   ; 
						    //console.log(-($('#main-tab').width()-$('#main-tab').parent().width() -100) )
							if ( $('#main-tab').css('left').replace('px','') > 0      ){
								$('#main-tab').css('left'  , ( $('.menu-tab').data('left') +dist )/2 +'px' ) ;
							} else {
								$('#main-tab').css('left'  , ( $('.menu-tab').data('left') +dist ) +'px' ) ;
							} 
					}
				}); 
		 		$('.menu-tab').bind('mouseup mouseleave',function(e){ 	
		 			
		 			if  ( $('#main-tab').css('left').replace('px','') == '0' ) { return ;};	 
		 			 
					$('.menu-tab').data('move',0);
					$('.menu-tab').data('posX',0) ;
					$('.menu-tab').data('left',0)   ;
					 
					if ( $('#main-tab').css('left').replace('px','') > 0  ){
						$('#main-tab').animate({left:'0px'},'fast');
						return;
					}  
					if (  $('#main-tab').css('left').replace('px','') < -($('#main-tab').width()-$('#main-tab').parent().width() -100) && $('#main-tab >li').length>5 ){
						$('#main-tab').animate({left: -($('#main-tab').width()-$('#main-tab').parent().width()-100)+ 'px'},'fast');
						return;
					}  
				}); 
				
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
				 
	 			
	 	}
	 	 	
	 }
	 	 
		
	
	return null ;	
});