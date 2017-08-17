define(["text!commTpl/frame/sideBar.html","commSrc/frame/menu"],function(tpl,menu){
  
	//加载左边导航栏 
 	$('#menu-box').html(  tpl );
 	
 	//设置sidebar高度
 	//$('#main').css({ width:'100%',"height": $(document).height() - 70 - (Config.showFooter?40:0)  } )  ;
 
 	$('#menu-box').css({ "height": $(document).height() - 70 - (Config.showFooter?40:0)  }) ;
 	
 	$('#main-tab').parent().css({ width: document.body.scrollWidth-192-29 });
 	$('#main-tab').css({ width: document.body.scrollWidth-192-29 });
 	
 	$('#main-content').css({ width: document.body.scrollWidth-192-49, 'height' : $('#mainLeftTd').height()-71 }) ;
 	
 	function setUlWidth(){
 		//重设右边标签UL的宽度（li宽度的累加）
		var ulWidth = 20 ;	
 		var ulCurrWidth  = $('#main-tab').width();
 		
		$('#main-tab > li:visible').each(function(key,liObj){
			ulWidth+=$(liObj).width() +20;		
		});	
		$('#main-tab').css('width',ulWidth);
 	}
 	
 	function closeTips(){
 		//判断是否有提示框，有则删除
 		$('div[role="tooltip"]').length && $('div[role="tooltip"]').remove();
 	}
 	
 	
 	function showTab(menuObj){
 		
 		//判断是否存在首页，存在则隐藏
		if ( $('#main-tab>li[data-tabid="1"]').is(':visible')  ){
			$('#main-tab>li[data-tabid="1"]').addClass('none');
			$('#main-content > div[data-contentid="1"]').addClass('none') ;	
		}
		
		closeTips();
 		
 		
 		var thisUL =  $('#main-tab'),
 			 liTabId = menuObj.liTabId ,
 			 tabName = menuObj.tabName ,
 			 modulePath = menuObj.modulePath ;	
 		//menuLevel  1:一级菜单  ，2：二级菜单  , 3: 三级菜单 
 		/*
		 *   创建导航tab和内容
		 * 
		 */		 
		var liCount =  $('#main-tab > li').length ;	 
		var getLi = $('#main-tab > li[data-tabid="'+liTabId+'"]') ;	
		
		if ( getLi && getLi.length ){
			//已存在的tab则显示此tab及内容（是否考虑刷新页面再定夺？？？）
			
			//置灰相邻的标签 
			getLi.siblings().removeClass('menu-tab-hover');		 
			//显示当前的标签及关闭图标
			getLi.removeClass('none');
			getLi.addClass('menu-tab-hover');
			//显示当前内容
			var getDiv = $('#main-content > div[data-contentid="'+liTabId+'"]') ;
				  getDiv.removeClass('none');				  
			//隐藏相邻的内容
			getDiv.siblings().addClass('none');	
			
			setUlWidth();
			 
			
		} else {
			//把相邻的li置灰，并隐藏关闭图标
			$('#main-tab > li').removeClass('menu-tab-hover');
			//缀加标签,置为高亮，并显示关闭图标
			var getLi = $('<li  data-tabid="'+liTabId+'" class="menu-tab-hover">'+tabName +'</li>');			
			      getLi.appendTo( $('#main-tab') );
			       
			      getLi.click(function(e){
			      		$(this).siblings().removeClass('menu-tab-hover'); 
			      		$(this).addClass('menu-tab-hover'); 
			      		//显示相应内容
			      		$('#main-content > div[data-contentid="'+liTabId+'"]').siblings().addClass('none');
			      	    $('#main-content > div[data-contentid="'+liTabId+'"]').removeClass('none');
			      	    closeTips();
			      });		
			    setUlWidth() ; 
			    
			//加分隔线
			$('<span class="menu-tab-split"></span>').appendTo(getLi); 
			//缀加关闭按钮
			var closeI = $('<i class="menu-tab-closeBt"></i>') ;
			getLi.append(closeI);	 
				 closeI.click(function(e){				 
				 	e.stopPropagation();
				 	closeTips();
				 	$(this).parents('#navTab').data('move','');
				 	//隐藏当前标签和内容
				 	getLi.addClass('none');
				 	$('#main-content > div[data-contentid="'+liTabId+'"]').addClass('none');			 	
				 	//激活中tab,需把激活状态转移到上一个tab,如果不存在上一个tab，则转移到下一个tab
				 	if (getLi.hasClass('menu-tab-hover')){
				 		//var prevLiObj = getLi.prevAll(':not([class="none"])') ,
				 		//	 nextLiObj = getLi.nextAll(':not([class="none"])')  ,
				 		var prevLiObj = getLi.prevAll(':visible') ,
				 			 nextLiObj = getLi.nextAll(':visible')  ,
				 			 prevLength = prevLiObj.length,
				 			 nextLength = nextLiObj.length,
				 			 prevLi = null ,
				 			 prevLiTabId = null ,
				 			 nextLi = null  ,
				 			 nextLiTabId = null ;				 		 
				 		if (prevLength)	 {
				 			prevLi = $(prevLiObj[0]) ;
				 			prevLiTabId = prevLi.data('tabid')  ;
				 			prevLi.addClass('menu-tab-hover') ;
				 			$('#main-content > div[data-contentid="'+prevLiTabId+'"]').removeClass('none') ;
				 			//如果li  left<0 ，则需右移
				 			
				 			if ( prevLi.offset().left < $('#navTab').offset().left ){
				 				//debugger
				 				//console.log(prevLi.offset().left - $('#navTab').offset().left)
				 				prevLi.parent().css('left' , ( prevLi.parent().css('left').replace('px','')*1 +  $('#navTab').offset().left    )+'px' ) ;
				 			}	
				 						 
				 		} else if (nextLength){
				 			nextLi = $(nextLiObj[0]) ;
				 			nextLiTabId = nextLi.data('tabid')  ;
				 			nextLi.addClass('menu-tab-hover') ;
				 			$('#main-content > div[data-contentid="'+nextLiTabId+'"]').removeClass('none') ;	
				 		} 
				 	}
				 	setUlWidth(); 				 	
				 }); 
			//缀加标签对应的内容框(空的div)
			$('#main-content > div').addClass('none');
			$('#main-content').append('<div data-contentid="'+liTabId+'" class="acct-ct "></div>');
			//向内容框添加对应的模板内容
			require([modulePath],function(content){
				content.showPage(liTabId); 
			}) ;
		} ; 
		
		/****添加li判断位置，如果超出右边界，则左移****/		
		var divPosX    = $('#navTab').offset().left + $('#navTab').width() ,
		 	  ulRightPosX = $('#main-tab').offset().left + $('#main-tab').width() - 20 ; 
		//console.log( divPosX + ' - ' +ulRightPosX +' =  '+ ( ulRightPosX - divPosX ) ) ;
		//console.log(   ulRightPosX   +'   |   '+  divPosX  + '   |   ' +  (getLi.offset().left + getLi.width() )   ) ;	  	
	  	if ( ulRightPosX > divPosX && ( getLi.offset().left + getLi.width() ) > divPosX ){	 
	  		//console.log('aaa:  ' + ( getLi.offset().left - $('#navTab').offset().left )  );	  	 
	  	 	$('#main-tab').css({ 'left' :   $('#main-tab').css('left').replace('px','') -(ulRightPosX-divPosX)  }) ; 
	 	} else if ( getLi.offset().left <  $('#navTab').offset().left   ) {
	 		//console.log('bbb:  ' + (getLi.offset().left - $('#navTab').offset().left ) );
	 		$('#main-tab').css({ 'left' :    $('#main-tab').css('left').replace('px','')*1 +($('#navTab').offset().left - getLi.offset().left)    }) ; 
	 	} 
	  	
	  	
		
	    
		
 	};
 	
 	
 	
	//折叠一级菜单
	$('#menu-box > h1').click(function(e){	 
		var first = $(this).next('div').data('first')  || $(this).data('first')  ,
			  file = $(this).data('file'),				  
			  liTabId= $(this).data('tabid'),
			  tabName = $(this).text(),
			  modulePath=null,
			  self = this ;
		modulePath = first+'Src/main/'+( file || 'main' ) ;	 	  
		//折叠其它的一级 
		
		$(this).siblings('h1').removeClass('menu-one-hover').next('div').hide(); 
		if ($(this).hasClass('menu-one-haveSub') ){  //有下级菜单才响应
			if ($(this).find('a').hasClass('menu-arrow-down')){
				$(this).find('a').removeClass('menu-arrow-down');
			} else {
				$(this).find('a').addClass('menu-arrow-down');
				$(this).siblings().find('a').removeClass('menu-arrow-down');
			}		
			//存在二级
			$(this).next().toggle(100,function(e){	
					//if ( !$(self).data('tabid') || !$(this).is(':visible')  )	{
					if ( !$(self).data('tabid')   )	{	
						return ;
					} ;		 
					//显示对应的标签及内容
					var menuObj = { 
						liTabId : liTabId ,
						tabName: tabName ,
						modulePath: modulePath 
					};					
					showTab(menuObj);
			});
		} else {
			//只有一级		
			if (  $(self).data('first') && $(self).data('tabid')  ){	
				//显示对应的标签及内容
					var menuObj = { 
						liTabId : liTabId ,
						tabName: tabName ,
						modulePath: modulePath 
					};					
					showTab(menuObj);			 
			}
		}
	}); 
	
	//折叠二级菜单
	$('#menu-box > div > h2').click(function(e){		 
		var first = $(this).parent('div').data('first')  || $(this).parent('div').prev('h1').data('first') ,
			  file = $(this).data('file') ,
			  second     = $(this).data('second') ||  $(this).next().data('second') ,		  
			  liTabId     = $(this).data('tabid'),
			  tabName = $(this).text(),
			  modulePath=null,
			  self = this ;
		modulePath = first+'Src/sub/'+second +'/'+ (file || 'main') ;	 
		//折叠本div中的相邻二级
		$(this).addClass('active');
		$(this).siblings('h2').removeClass('active');	 
	 	$('#menu-box li').removeClass('menu-three-hover');
		$(this).siblings('h2').removeClass('menu-one-hover').next('ul').hide();		
		//只有二级 
		if ( !$(this).next('ul').length && $(self).data('tabid')  ){			
			var menuObj = { 
				liTabId : liTabId ,
				tabName: tabName ,
				modulePath: modulePath 
			};					
			showTab(menuObj) ; 			
			return;
		}
		//存在三级
		$(this).next('ul').toggle(100,function(){			 
			//if ( !$(self).data('tabid') || !$(this).is(':visible') )	{
			if ( !$(self).data('tabid')  )	{	
				return ;
			} ;		 
			//显示对应的标签及内容
			var menuObj = { 
				liTabId : liTabId ,
				tabName: tabName ,
				modulePath: modulePath 
			};					
			showTab(menuObj) ; 
		}); 
	});
	
	//三级菜单
	$('#menu-box > div > ul > li').click(function(e){
		
		$('#menu-box > div > ul > li').removeClass('menu-three-hover');
		//$(this).siblings().removeClass('menu-three-hover');
		if (!$(this).hasClass('menu-three-hover')){
			$(this).addClass('menu-three-hover') ;
		} 
		//TODO ...  
 
		var first =$(this).parents('div').eq(0).data('first') ,   
			  second  = $(this).parent().data('second')  ,
			  third = $(this).data('third') ,
			  file    = $(this).data('file') ,
			  liTabId = $(this).data('tabid'),
			  tabName= $(this).text(),
			  modulePath = null ;
			  modulePath = first+'Src/'+second+'/'+third +'/'+( file || 'main') ;	 
			  
		if ( ! third) {return;};
			  
		/*
		 *   创建导航tab和内容
		 * 
		 */		
		var menuObj = {
			liTabId : liTabId,
			tabName: tabName,
			modulePath: modulePath
		};
		showTab( menuObj );
		
		 
		
	});
	
	
	 
	
	return null;	
});