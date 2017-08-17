define(["text!commTpl/frame/chat/chatBox.html",
		"text!commTpl/frame/chat/expressionSymbol.html",
		"text!commTpl/frame/chat/chat_area.html",
		"text!commTpl/frame/chat/chat_tab.html",
		"commSrc/frame/chat/plaintext-only"
		], function(chatBoxTpl, expressionSymbolTpl, chat_areaTpl, chat_tabTpl, plaintext){
	return {
		showChatBox : function(person){
			if($.browser.msie && $.browser.version*1 < 9){
				comm.warn_alert("您的IE浏览器版本过低，请安装IE9或更高版本再使用本功能。", 495);
				return;
			}
			var self = this;

			if($('#chatBox').length == '0'){
				$('#chatBox_div').append(chatBoxTpl);
				this.show_box($('#chatBox'), 770, 556);
			}
			
			self.add_chatBox(person);
			self.bindingEvents();
		},
		
		/*显示窗口方法*/
		show_box : function(boxTpl, w, h){
			
			var that = this,
				ww = that.getWindowWidth(),
				wh = that.getWindowHeight();
			
			var w_half = w / 2,
				h_half = h / 2,
				lt = ww * 0.5 - w_half, 
				tp = wh * 0.5 - h_half;
			if(tp < 0){tp = 0;}
			boxTpl.removeClass('none').css({"left" : lt + "px", "top" : tp + "px"});
			//focus_box(boxTpl);
		},
		
		/*添加聊天窗口方法*/
		add_chatBox : function(person){
			var that = this;
			
			var userId = person.userCustId;
			var	userImg_src = person.userImg;
			var	userName = person.userName;
			var	chatBox_tab = $('#chatBox_tab');
			var	chatBox_area = $('#chatBox_area');
			var chat_pop_msg_person_btn = "person_chat_"+person.userCustId;
			
			/*加载头像切换按钮*/
			if(chatBox_tab.find('li').length == 0){
				chatBox_tab.append(chat_tabTpl);
				chatBox_tab.find('li:first').attr('id', 'li_' + userId);
				chatBox_tab.find('li:first').children('a').attr('data-close-btn-id', userId);
				$('#li_' + userId).addClass('bgColor')
					.find('img').attr('src', userImg_src).end()
					.find('.chat_box_tab_text').text(userName);
			}	
					
			if(chatBox_tab.find('li[id="li_' + userId + '"]').length == 0){
				chatBox_tab.append(chat_tabTpl);
				chatBox_tab.find('li').removeClass('bgColor');
				chatBox_tab.find('li:last').attr('id', 'li_' + userId);
				chatBox_tab.find('li:last').children('a').attr('data-close-btn-id', userId);
				$('#li_' + userId).addClass('bgColor')
					.find('img').attr('src', userImg_src).end()
					.find('.chat_box_tab_text').text(userName);
			}else{
				chatBox_tab.find('li').removeClass('bgColor');
				chatBox_tab.find('li[id="li_' + userId + '"]').addClass('bgColor');
			}
			
			chatBox_tab.children('li').on('mouseover', function(){
				$(this).children('a').show();	
			}).on('mouseout', function(){
				$(this).children('a').hide();		
			});
			/*end*/
			
			/*加载聊天区域*/
			if(chatBox_area.children('div').length == 0){
				chatBox_area.append(chat_areaTpl);
				chatBox_area.children('div:first').attr('id', 'div_' + userId);
				chatBox_area.children('div:first').find('.send_btn_l').attr('id', chat_pop_msg_person_btn);
				chatBox_area.children('div:first').find('.file_icon_btn').attr('id', "pic_send_person_btn_"+userId);
				chatBox_area.children('div:first').find('#pic_send_person').attr('name', "pic_send_person_"+userId);
				chatBox_area.children('div:first').find('#pic_send_person').attr('id', "pic_send_person_"+userId);
				chatBox_area.children('div:first').find('.chatBox_closeBtn').attr('data-close-btn-id', userId);
				$('#div_' + userId).removeClass('none').addClass('currentBox')
					.find('.chat_title_img').children('img').attr('src', userImg_src).end().end();
				if(person.perType == "w_nc_" || person.hsCardId == userName){
					$('#div_' + userId).find('.person_custId_txt').text("");
					$('#div_' + userId).find('.chat_title_name_txt').text(userName);
				}else{
					$('#div_' + userId).find('.person_custId_txt').text("( "+person.hsCardId+" )");
					$('#div_' + userId).find('.chat_title_name_txt').text(userName);
				}
			}
			if(chatBox_area.children('div[id="div_' + userId + '"]').length == 0){
				chatBox_area.append(chat_areaTpl);
				chatBox_area.children('div').addClass('none').removeClass('currentBox');
				chatBox_area.children('div:last').attr('id', 'div_' + userId);
				chatBox_area.children('div:last').find('.send_btn_l').attr('id', chat_pop_msg_person_btn);
				chatBox_area.children('div:last').find('.chatBox_closeBtn').attr('data-close-btn-id', userId);
				chatBox_area.children('div:last').find('.file_icon_btn').attr('id', "pic_send_person_btn_"+userId);
				chatBox_area.children('div:last').find('#pic_send_person').attr('name', "pic_send_person_"+userId);
				chatBox_area.children('div:last').find('#pic_send_person').attr('id', "pic_send_person_"+userId);
				$('#div_' + userId).removeClass('none').addClass('currentBox')
					.find('.chat_title_img').children('img').attr('src', userImg_src).end().end();
				if(person.perType == "w_nc_" || person.hsCardId == userName){
					$('#div_' + userId).find('.person_custId_txt').text("");
					$('#div_' + userId).find('.chat_title_name_txt').text(userName);
				}else{
					$('#div_' + userId).find('.person_custId_txt').text("( "+person.hsCardId+" )");
					$('#div_' + userId).find('.chat_title_name_txt').text(userName);
				}
			}else{
				chatBox_area.children('div').addClass('none').removeClass('currentBox');
				chatBox_area.children('div[id="div_' + userId + '"]').removeClass('none').addClass('currentBox');
				chatBox_area.find('div.biaoqing_box').hide();
				chatBox_area.children('div[id="div_' + userId + '"]').find('.talk_history').html("");
			}
			
			that.initPersonUpload(userId, person.perType);
			
			//消费者聊天-发送消息事件
			$('#'+chat_pop_msg_person_btn).unbind('click').bind('click', function(){
				var content = $.trim(that.getCurrentBoxObj().find('.tool_talkinput_textarea').html());
				if (!content){
					return;
				}
//				content = $('<p>'+$.hsChat.Replace_Code(content)+'</p>').text();
				content = $.hsChat.Replace_Code(content);
				$.hsChat.sendPerMessage(content, false, "00", person.perType);
			});
			
			$(document).keydown(function(e){
				if(e.ctrlKey && e.keyCode == 13){
					$('#'+chat_pop_msg_person_btn).click();
				}
			});
			
		},
		
		/*窗口绑定事件*/
		bindingEvents : function(){
			
			var that = this,
				chatBox = $('#chatBox'),
				chatBox_tab = $('#chatBox_tab'),
				chatBox_area = $('#chatBox_area'),
				currentBoxObj = that.getCurrentBoxObj();
				$('#li_'+$.hsChat.getCurrentCustId()).removeClass("highLight_bg");//移除高亮
				
			plaintext.showPage(currentBoxObj, true);
			
			/*用户名切换窗口事件*/
			chatBox_tab.children('li').unbind('click')
				.bind('click', function(){
					var li_id = $(this).attr('id')
						div_id = li_id.replace('li', 'div');
					//$("#"+li_id).removeClass("highLight_bg");//移除高亮
					chatBox_tab.children('li').removeClass('bgColor');
					$(this).addClass('bgColor');
					chatBox_area.children('div').addClass('none').removeClass('currentBox');
					chatBox_area.children('div[id="' + div_id +'"]').removeClass('none').addClass('currentBox');
					/*$('.talk_history').jScrollPane();*///历史展示窗口滚动条
					that.msgBoxScroll(currentBoxObj);
					
					currentBoxObj = that.getCurrentBoxObj();
					chatBox_area.find('div.biaoqing_box').hide();
				});
			/*end*/
			
			/*设置拖动窗口*/
			var ww, wh, msx, msy,
				dragging = false;			
		
			$('.chat_title').unbind('mousedown')
				.mousedown(function(e){
					ww = that.getWindowWidth();
					wh = that.getWindowHeight();
					
					dragging = true;
					e = e||window.event; 
					msx = e.clientX - chatBox.position().left;
					msy = e.clientY - chatBox.position().top;
					return false;
				});	
			
			document.onmousemove = function(e){
				if (dragging) {
					var xx = e.clientX - msx;
					var yy = e.clientY - msy;
					if(xx < 0){
						xx = 0;
					}
					if(xx > ww - 770){//对话窗口宽度
						xx = ww - 770;
					}
					if(yy < 0){
						yy = 0;
					}
					if(yy > wh - 556){//对话窗口高度
						yy = wh - 556;
					}
					chatBox.css({"left" : xx + "px", "top" : yy + "px"});
					return false;
				 }
			 };
			 
			$(document).mouseup(function(e) {
				dragging = false;
				/*$("#msg_pop_box")[0].releaseCapture();*/
				e.cancelBubble = true;
				return;
			});	
			/*end*/
			
			/*关闭窗口事件*/
			$('.chatBox_closeBtn').unbind('click')
				.bind('click' ,function(){
					var close_id = $(this).attr('data-close-btn-id');	
					chatBox_area.children('div[id="div_' + close_id + '"]').remove();
					chatBox_tab.children('li[id="li_' + close_id + '"]').remove();
					
					if(chatBox_tab.children('li').hasClass('bgColor')){
						chatBox_tab.children('li.bgColor').trigger('click');
					}
					else{
						chatBox_tab.children('li:first').trigger('click');	
					}
					if(chatBox_tab.children('li').length == 0){
						$('#chatBox_div').html('');
					}
				});
			
			/*end*/
			
			/*$('.talk_history').jScrollPane();//历史展示窗口滚动条*/
			that.msgBoxScroll(currentBoxObj);
			
			
			/*表情操作区域*/
			var textarea_html = '';
			currentBoxObj.find('a.biaoqing_more_btn').unbind('click')
				.on('click', function(){
					if($(this).children('div.biaoqing_box').length == 0){
						$(this).append(expressionSymbolTpl);	
					}
					if($(this).children('div.biaoqing_box').is(':hidden')){
						$(this).children('div.biaoqing_box').show()
							.children('i').unbind('click')
								.bind('click',function(){
									textarea_html = currentBoxObj.find('.tool_talkinput_textarea').html();
									currentBoxObj.find('div.tool_talkinput_textarea').html(textarea_html + $(this).html());
								});		
					}
					else{
						$(this).children('div.biaoqing_box').hide();
					}
				});
				
			currentBoxObj.find('div.tool_talkinput_textarea').unbind('focus')
				.focus(function(){
					if(!currentBoxObj.find('div.biaoqing_box').is(':hidden')){
						currentBoxObj.find('div.biaoqing_box').hide();	
					}
				});
				
				
			/*end*/
			
			/*商品信息/订单信息切换*/
			currentBoxObj.find('div.product_tab a').bind('mouseover', function(){
				var dataTab = $(this).attr('data-tab');
				currentBoxObj.find('div.product_tab a').removeClass('hover');
				$(this).addClass('hover');				
				currentBoxObj.find('div.product_content').children('div').addClass('none');
				currentBoxObj.find('div.product_content').children('div[data-content="'+ dataTab +'"]').removeClass('none');
				currentBoxObj.find('span.bottom_line').animate({left: dataTab == '0' ? '19px' : '115px'}, 100);
			});
			
			/*end*/
			
			
		},
		
		/*获取当前窗口的div对象*/
		getCurrentBoxObj : function(){
			return $('#chatBox_area').children('div[class="currentBox"]');
		},
		
		/*获取可视窗口宽度*/
		getWindowWidth : function(){
			var ww = $(window).width();
			$(window).resize(function(){
				ww = $(window).width();
			});
			return ww;
		},
		
		/*获取可视窗口高度*/
		getWindowHeight : function(){
			var hh = $(window).height();
			$(window).resize(function(){
				hh = $(window).height();
			});
			return hh;
		},
		msgBoxScroll : function (obj){
			var scroll = false;
			//滚动显示消息
			if (!scroll){
				scroll = true;
				var curObj = obj.find(".talk_history");
				curObj.animate({scrollTop: curObj.scrollTop()+10000}, 0, function(){
					scroll = false;
				}); 
			} 
		},
		/*初始化消费者上传图片*/
		initPersonUpload : function(userId, perType){
			//限制图片类型及大小
			comm.initChatUploadBtn(["#pic_send_person_"+userId], ["#div_002"]);
			$("#pic_send_person_btn_"+userId).unbind('change').bind('change', function(){
				var file = $("#pic_send_person_"+userId).val();
				if(file != ""){
					comm.uploadChartFile(["pic_send_person_"+userId], function(data){
						$("#pic_send_person_"+userId).val("");
						comm.initChatUploadBtn(["#pic_send_person_"+userId], ["#div_001"]);
						$.hsChat.sendPerMessage(data, false, "10", perType);
					}, function(){
						$("#pic_send_person_"+userId).val("");
						comm.initChatUploadBtn(["#pic_send_person_"+userId], ["#div_001"]);
					});
				}
			});
		},
	}

});
