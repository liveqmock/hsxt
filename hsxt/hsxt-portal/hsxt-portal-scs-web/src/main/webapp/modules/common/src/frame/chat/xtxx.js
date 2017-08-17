define(["text!commonTpl/frame/chat/xtxx.html",
		"commDat/frame/chat"], function(xtxxTpl, dataModoule){
	return xtxxService = {
		showXtxxBox : function(msg, loadHtml, delId){
			var self = this;
			if(loadHtml){
				$('#xtxx_div').append(xtxxTpl);
			}
			var liId = "msg_li_"+msg.msgId;
			if($("#"+liId).length == 0){
				$("#msg-pv-tab").append("<li class=\"pr\" id=\""+liId+"\">"+msg.subject+"<i class=\"i_ico msgBox_close_icon pa\" style=\"top:10px;right:5px;display:none;\"></i></li>");
			}
			
			self.bindLiClick(liId, msg);
			
			if(delId){
				$("#msg_li_"+delId).remove();
			}
			this.show_box($('#xtxx_box'), 850, 506);
	        
			self.bindingEvents();
		},
		bindLiClick : function(liId, msg){
			var liId = "#"+liId;
			$(liId).mouseover(function(){
				$(liId).find(".msgBox_close_icon").show();
			});
			$(liId).mouseout(function(){
				$(liId).find(".msgBox_close_icon").hide();
			});
			$(liId).find(".msgBox_close_icon").click(function(){
				pushService.systemOpenList["msgIds"].splice(jQuery.inArray(msg.msgId+"", pushService.systemOpenList["msgIds"]), 1);
				if(pushService.systemOpenList["msgIds"].length == 0){
					$('.xtxx_closeBtn').click();
				}else{
					$(liId).remove();
					$("#msg_li_"+pushService.systemOpenList["msgIds"][0]).click();
				}
			});
			$(liId).click(function(){
				$("#msg-pv-tab").find("li").removeClass("hover");
				$(liId).addClass("hover");
				$(".msg-pv-title-font").html(comm.getNameByEnumId(msg.msgType, comm.lang("common").msgTitle)+" - "+msg.subject);
				$(".msg-pv-context-title-font").html(msg.subject);
				//互生消息-公开信、私信
				if(msg.msgType == 1001 || msg.msgType == 1002 || msg.msgType == 1003 || msg.msgType == 1004 || msg.msgType == 1005){
					$("#hs_message_div_1").show();
					$("#hs_message_div_2").hide();
					$("#hs_message").attr("src", eval("("+msg.content+")").pageUrl);
				}else{
					$("#hs_message_div_1").hide();
					$("#hs_message_div_2").show();
					$("#pushTime").html(pushService.getFullTime(msg.time));
					cacheUtil.findCacheSystemInfo(function(sysRes){
						dataModoule.findMainInfoByResNo({platEntResNo:sysRes.platResNo}, function(res){
							if(res && res.data){
								$("#pushPlatName").html(res.data.entName);
							}
						});
					});
					$("#pushContent").html(msg.content);
				}
			});
			$(liId).click();
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
			
			var that = this,
				xtxxBox = $('#xtxx_box');
			
			/*设置拖动窗口*/
			var ww, wh, msx, msy,
				dragging = false;			
		
			$('.msg-pv-title-box').unbind('mousedown')
				.mousedown(function(e){
					ww = that.getWindowWidth();
					wh = that.getWindowHeight();
					
					dragging = true;
					e = e||window.event; 
					msx = e.clientX - xtxxBox.position().left;
					msy = e.clientY - xtxxBox.position().top;
					return false;
				});	
			
			document.onmousemove = function(e){
				if (dragging) {
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
					xtxxBox.css({"left" : xx + "px", "top" : yy + "px"});
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
			$('.xtxx_closeBtn').unbind('click')
				.bind('click', function(){
					if(pushService.systemOpenList["msgIds"].length > 1){
						comm.confirm({
							imgFlag: true,
							title: "温馨提示",
							content: "您确定关闭所有消息窗口？",
							callOk: function () {
								$('#xtxx_box').addClass('none');
								$("#msg-pv-tab").empty();
								pushService.systemOpenList = {};
							},
							callCancel: function () {

							}
						});
					}else{
						$('#xtxx_box').addClass('none');
						$("#msg-pv-tab").empty();
						pushService.systemOpenList = {};
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
		}

		

	
		
	}

});
