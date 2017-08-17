define(['text!commTpl/frame/chat/companyChat.html',
		"text!commTpl/frame/chat/expressionSymbol.html",
		"commSrc/frame/chat/plaintext-only"
		], function(companyChatTpl, expressionSymbolTpl, plaintext){
	return {
		showChatBox : function(user){
			if($.browser.msie && $.browser.version*1 < 9){
				comm.warn_alert("您的IE浏览器版本过低，请安装IE9或更高版本再使用本功能。", 495);
				return;
			}
			var self = this;
			$('#companyChatBox_div').html(companyChatTpl);
			self.show_box($('#companyChatBox'), 620, 556);
			$('#chat_title_img_company img').attr('src', user.userImg);
			$('#chat_title_name_txt_company').text(user.realName+" ( "+user.userName+" )");
			
			$.hsChat.initEvent();
			$.hsChat.toUserId = user.userCustId;
			
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
			boxTpl.removeClass('none')
				.css({"left" : lt + "px", "top" : tp + "px"});
			//focus_box(boxTpl);		
		},
		
		/*窗口绑定事件*/
		bindingEvents : function(){
			
			var that = this;
			var	chatBox = $('#companyChatBox');
			
			plaintext.showPage($("#tool_talkinput_textarea_company"));
			
			/*设置拖动窗口*/
			var ww, wh, msx, msy,
				dragging = false;			
		
			$('.chat_title_company').unbind('mousedown')
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
					e = e||window.event; 
					var xx = e.clientX - msx;
					var yy = e.clientY - msy;
					if(xx < 0){
						xx = 0;
					}
					if(xx > ww - 620){//对话窗口宽度
						xx = ww - 620;
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
			
			//$('.talk_history_company').jScrollPane();//历史展示窗口滚动条
			
			//that.msgBoxScroll($('.talk_history_company'));
			
			/*关闭窗口事件*/
			$('.chatBox_closeBtn_company').unbind('click')
				.bind('click', function(){
					$('#companyChatBox').addClass('none');
				});
			/*end*/
			
			/*表情操作区域*/
			var textarea_html = '';
			$('#biaoqing_more_btn_company').unbind('click')
				.bind('click', function(){
					if($(this).children('div.biaoqing_box').length == 0){
						$(this).append(expressionSymbolTpl);	
					}
					if($(this).children('div.biaoqing_box').is(':hidden')){
						$(this).children('div.biaoqing_box').show()
							.children('i').unbind('click')
								.bind('click',function(){
									textarea_html = $('#tool_talkinput_textarea_company').html();
									$('#tool_talkinput_textarea_company').html(textarea_html+$(this).html());
								});		
					}
					else{
						$(this).children('div.biaoqing_box').hide();
					}	
				});
			
			$('#tool_talkinput_textarea_company').unbind('focus')
				.focus(function(){
					if(!$('#biaoqing_more_btn_company').find('div.biaoqing_box').is(':hidden')){
						$('#biaoqing_more_btn_company').find('div.biaoqing_box').hide();	
					}
				});
			
			//限制图片类型及大小
			comm.initChatUploadBtn(["#pic_send"], ["#div_001"]);
				
			$("#pic_send_btn").unbind('change').bind('change', function(){
				var file = $("#pic_send").val();
				if(file != ""){
					that.uploadFile(["pic_send"], function(data){
						$("#pic_send").val("");
						comm.initChatUploadBtn(["#pic_send"], ["#div_001"]);
						$.hsChat.sendMessage(data, false, "10");
					}, function(){
						$("#pic_send").val("");
						comm.initChatUploadBtn(["#pic_send"], ["#div_001"]);
					});
				}
			});
			/*end*/
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
		
		//滚动显示消息
		msgBoxScroll : function (obj){
			var scroll = false;
			if (!scroll){
				scroll = true;
				obj.animate({scrollTop: obj.scrollTop()+1000 },0,function(){scroll = false;}); 
			} 
		},
        /***
		 * 文件上传
		 * ids:文件的name
		 * callBack1:成功后的回调方法
		 * callBack2:失败后的回调方法
		 * param 参数
		 * 
		 */
		uploadFile : function(name, callBack1, callBack2){
			var url = comm.domainList['xmppImg']+"upload/imageUpload?type=default";
			url+="&fileType=image&channelType=1";
			url+="&custId="+comm.getRequestParams()['custId'];
			url+="&id="+comm.getRequestParams()['pointNo'];
			url+="&loginToken="+comm.getRequestParams()['token'];
			url+="&entResNo="+comm.getRequestParams()['pointNo'];
			$.ajaxFileUpload({
				url : url,
				secureuri : false, // 是否启用安全提交,默认为false
				fileElementId : name, // 文件选择框的id属性
				dataType : 'json', // 服务器返回的格式,可以是json或xml等
				data : {},
				type : "POST",
				success : function (response) { // 服务器响应成功时的处理函数
					comm.ipro_alert_close();
					if(response.state == 200){
						callBack1(response);
					}else {
						//上传异常提示
						comm.alert({
							content: "图片上传失败.",
							imgClass: 'tips_error'
						});
						callBack2();
					}
				},
				error: function(response){
					comm.ipro_alert_close();
					comm.error_alert("图片上传失败.");
					callBack2();
				}
			});
		}
	}	
});

