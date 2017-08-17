define(function() {
			var point = {
					//显示弹窗
				tablePoint : function(point,callback){
						$("#table-point").html(point);
						 $( "#dlg0" ).dialog({
						 	  title:'提示信息',
						      modal: true,
						      width:420,
						      closeIcon:true,
							  open:function(){
								  //$(".ui-dialog-titlebar-close", $(this).parent()).hide();
							  	  $(".ui-dialog-titlebar-close").bind("click",function(){
							  	  	//$( this ).dialog( "close" );
							  	  	//$( this ).dialog( "destroy" );
							  	  });
							  },
						      buttons: {
						        确定: function() {
						        	$( this ).dialog( "destroy" );
						        	if(callback != null && callback != ""){
						        		callback(true);
						        	}
						        }
						      }
						  });
				},
				//确认删除
				tablePoint2 : function(title,point,callback){
						$("#table-point").html(point);
						 $( "#dlg0" ).dialog({
						 	  title:title,
						      modal: true,
						      width:420,
							  open:function(){
								  //$(".ui-dialog-titlebar-close", $(this).parent()).hide();
							  	  $(".ui-dialog-titlebar-close").bind("click",function(){
							  	  	//$( this ).dialog( "close" );
							  	  	//$( this ).dialog( "destroy" );
							  	  });
							  },
						      buttons: {
						        确定: function() {
						        	$( this ).dialog( "destroy" );
						        	if(callback != null && callback != ""){
						        		callback(true);
						        	}
						        },
						        取消: function(){
						        	$( this ).dialog( "destroy" );
						        }
						      }
						  });
				},
				//确认删除
				tablePoint3 : function(point,callback,callbackCancel){
						$("#table-point").html(point);
						 $( "#dlg0" ).dialog({
						 	  title:'提示信息',
						      modal: true,
						      width:420,
							  open:function(){
								  //$(".ui-dialog-titlebar-close", $(this).parent()).hide();
							  	  $(".ui-dialog-titlebar-close").bind("click",function(){
							  	  	//$( this ).dialog( "close" );
							  	  	//$( this ).dialog( "destroy" );
							  	  });
							  },
						      buttons: {
						        确定: function() {
						        	$( this ).dialog( "destroy" );
						        	if(callback != null && callback != ""){
						        		callback(true);
						        	}
						        },
						        取消: function(){
						        	$( this ).dialog( "destroy" );
						        	if(callbackCancel != null && callbackCancel != ""){
						        		callbackCancel(true);
						        	}
						        }
						      }
						  });
				},
				
				//计算图片KB
				formatFileSize : function(size){
				    size = parseFloat(size/1024);
				    return size;
				},
				//进度条低过dig。
				bindItemSKUJiazai : function(type,num){
					var html='<div class="overlay lay" style="opacity: 0.6;filter: Alpha(Opacity=60);"><span><i></i></span></div>'
					$(type).css({position:'relative'});
					
					if(num){
						$(type).append(html);
						$('.overlay').css('position','absolute').show();
						var hei=($('.overlay').height()-$('.overlay span').height())/2;
						var wid=($('.overlay').width()-$('.overlay span').width())/2;
						$('.overlay span').css({position:'absolute',top:hei,left:wid});
					}else{
						$(type).children("div[class='overlay lay']").remove();
					}
				},
				//进度条低过dig。
				bindJiazai : function(type,num){
					var html='<div class="overlay lay"><span><i></i></span></div>'
					$(type).css({position:'relative'});
					
					if(num){
						$(type).append(html);
						$('.overlay').css({'position':'absolute',"opacity":0.01}).show();
						var hei=($('.overlay').height()-$('.overlay span').height())/2;
						var wid=($('.overlay').width()-$('.overlay span').width())/2;
						$('.overlay span').css({position:'absolute',top:hei,left:wid});
						$('.overlay').stop().animate({opacity:0.6},600);
					}else{
						$('.overlay').stop().animate({opacity:0},600,function(){
							$(type).children("div[class='overlay lay']").remove();
						});
					}
				},
				//进度条高过dig。
				bindJiazaiUp : function(type,num){
					var html='<div style="z-index:101;opacity: 0.6;filter: Alpha(Opacity=60);" class="overlay lay"><span><i></i></span></div>'
					$(type).css({position:'relative'});
					if(num){
						$(type).append(html);
						$('.overlay').css('position','absolute').show();
						var hei=($('.overlay').height()-$('.overlay span').height())/2;
						var wid=($('.overlay').width()-$('.overlay span').width())/2;
						$('.overlay span').css({position:'absolute',top:hei,left:wid});
					}else{
						$(type).children("div[class='overlay lay']").remove();
					}
				},
				/*图片点击放大*/
				imgBig : function(arr, that) {
					$('#imgWarp').remove();

					// 初始化
					if (!$('#imgWarp').length) {
						var imgWarp = '<div style="top:20%;left:20%;padding:3px; background-color:#fff; position:absolute; box-shadow:0 0 5px #000; z-index:100"' +
							' id="imgWarp"><img style="max-width:1024px;max-height:768px;" src="' + $(that).attr('src') + ' " id="im"><img src="resources/img/close.png" ' +
							'style="position:absolute; top:-45px; right:-45px; cursor:pointer;" id="imgClose"></div>';
						var picPrev = '<div class="pic_prev" style="width:30px; height:50px; cursor:pointer; ' +
							'background:url(resources/img/lr.png) no-repeat; position:absolute"></div>';
						var picNext = '<div class="pic_next" style="width:30px; height:50px; cursor:pointer; ' +
							'background:url(resources/img/lr.png) 0 -60px no-repeat; position:absolute"></div>';
						var resizable = '<div style="width:16px; height:16px; position:absolute; right:0; bottom:0;' +
							'background:url(resources/img/resize.png) no-repeat;"></div>'
						$('body').append(imgWarp);
						$('#imgWarp').append(picPrev, picNext, resizable);

					} else {
						$('#imgWarp').show();
						$('#im').attr('src', $(that).attr('src')).css({
							height: 'auto',
							width: 'auto'
						});
						//$('#im').css({ height: 'auto', width: 'auto' });
					}

					if (arr.length > 1) {
						$('.pic_prev,.pic_next').show();
					} else {
						$('.pic_prev,.pic_next').hide();
					}

					rez();

					// 关闭事件

					$('#imgClose').unbind().click(function() {
						$('#imgWarp').remove();
						// $('#im').resizable("destroy").draggable("destroy").css({
						// 	height: 'auto',
						// 	width: 'auto'
						// });
					});


					// 左右切换
					// var n = $(that).index();
					var len = arr.length;
					// var imgS = $(that).parent().children();

					for (var i = 0; i < arr.length; i++) {
						if (arr[i] == $(that).attr('src')) {
							var n = i;
						}
					}

					function step(num) {
						var imgHei = $('#imgWarp').height();
						var imgWid = $('#imgWarp').width();
						n = num < 0 ? 0 : num < len ? num : len - 1;
						var SRC = arr[n];
						$('#im').attr('src', SRC).resizable("destroy");

						//$('#im').resizable( "destroy" );
						$('#im').css({
							height: imgHei,
							width: imgWid
						}).resizable({
							aspectRatio: true,
							maxHeight: 768,
							maxWidth: 1024,
							resize: function(event, ui) {
								rez();
							}
						});

						rez(imgHei, imgWid);

					}

					// 左右点击事件
					$('.pic_prev').unbind().click(function() {

						n--;
						step(n);
						//rez();

					});

					$('.pic_next').unbind().click(function() {

						n++;
						step(n);
						//rez();

					});

					// 位置调整


					function rez(imgInitH, imgInitW) {

						var bodyH = $('body').height();
						var imgH = $('#imgWarp').height();
						var imgW = $('#imgWarp').width();
						//if (bodyH < imgH) {	imgH = bodyH };
						if ($('#imgWarp').css('top') == 'auto' && !imgInitH) {
							var imgTop = bodyH > imgH ? (bodyH - imgH) / 2 : 15;
							var imgLeft = $('body').width() > $('#imgWarp').width() ? ($('body').width() - $('#imgWarp').width()) / 2 : 0;
						} else {

							var imgTop = parseInt($('#imgWarp').css('top')) + (imgInitH - imgH) / 2;
							var imgLeft = parseInt($('#imgWarp').css('left')) + (imgInitW - imgW) / 2;

						}
						$('.pic_prev').css({
							top: ($('#imgWarp').height() - 50) / 2,
							left: 0
						});
						$('.pic_next').css({
							top: ($('#imgWarp').height() - 50) / 2,
							right: 0
						});

						$('#imgWarp').css({
							top: imgTop,
							left: imgLeft
						});
					}

					// 拖拽缩放
					$('#imgWarp').draggable();
					$('#im').resizable({
						aspectRatio: true,
						maxHeight: 768,
						maxWidth: 1024,
						resize: function(event, ui) {
							rez()
						}
					});
				},
				checkBoxAll : function(mianId,tbodyId){
					$(mianId).unbind().on('click',function(){
						var chkbox = $(tbodyId);
						$(this).prop('checked')?chkbox.prop('checked',true):chkbox.prop('checked',false);
						$("input[type='checkbox']").not($(mianId)).on('click',function(){
							$(mianId).prop('checked',false);
						})
					})
				}
			}
			return point;
});

