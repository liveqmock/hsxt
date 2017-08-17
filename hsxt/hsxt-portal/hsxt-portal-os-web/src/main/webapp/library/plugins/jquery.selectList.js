/*
 *  
 * Copyright 2014 Wanhuacheng
 * 1、表单系列化功能
 * 2、combobox功能  
 * 3、释放click和ajax请求功能
 * 4、ie8 placeholder兼容
 */
;(function($){
	/*
	 *  表单序列化成json
	 */
	 $.fn.serializeJson=function(){
		var serializeObj={};
		var array=this.serializeArray();
		var str=this.serialize();
		$(array).each(function(){
		if(serializeObj[this.name]){
		if($.isArray(serializeObj[this.name])){
		serializeObj[this.name].push(this.value);
		}else{
		serializeObj[this.name]=[serializeObj[this.name],this.value];
		}
		}else{
		serializeObj[this.name]=this.value;
		}
		});
		return serializeObj;
	 };
	 
	 /*
	  *  自定combobox
	  *  实现即可输入，也可下拉列表选择 
	  *
	  */ 
	  $.widget( "custom.combobox", {
		  _create: function() {
			this.wrapper = $( "<span>" )
			  .addClass( "custom-combobox" )
			  .insertAfter( this.element );
	 
			this.element.hide();
			this._createAutocomplete();
			this._createShowAllButton();
		  },
	 
		  _createAutocomplete: function() {
			var selected = this.element.children( ":selected" ),
			  value = selected.val() ? selected.text() : "";
	 
			this.input = $( "<input>" )
			  .appendTo( this.wrapper )
			  .val( value )
			  .attr( "title", "" )
			  .addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
			  .autocomplete({
				delay: 0,
				minLength: 0,
				source: $.proxy( this, "_source" )
			  })
			  .tooltip({
				tooltipClass: "ui-state-highlight"
			  });
	 
			this._on( this.input, {
			  autocompleteselect: function( event, ui ) {
				ui.item.option.selected = true;
				this._trigger( "select", event, {
				  item: ui.item.option
				});
			  },
	 
			  autocompletechange: "_removeIfInvalid"
			});
		  },
	 
		  _createShowAllButton: function() {
			var input = this.input,
			  wasOpen = false;
	 
			$( "<a>" )
			  .attr( "tabIndex", -1 )
			  .attr( "title", "Show All Items" )
			  .tooltip()
			  .appendTo( this.wrapper )
			  .button({
				icons: {
				  primary: "ui-icon-triangle-1-s"
				},
				text: false
			  })
			  .removeClass( "ui-corner-all" )
			  .addClass( "custom-combobox-toggle ui-corner-right" )
			  .mousedown(function() {
				wasOpen = input.autocomplete( "widget" ).is( ":visible" );
			  })
			  .click(function() {
				input.focus();
	 
				// 如果已经可见则关闭
				if ( wasOpen ) {
				  return;
				}
	 
				// 传递空字符串作为搜索的值，显示所有的结果
				input.autocomplete( "search", "" );
			  });
		  },
	 
		  _source: function( request, response ) {
			var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
			response( this.element.children( "option" ).map(function() {
			  var text = $( this ).text();
			  if ( this.value && ( !request.term || matcher.test(text) ) )
				return {
				  label: text,
				  value: text,
				  option: this
				};
			}) );
		  },
	 
		  _removeIfInvalid: function( event, ui ) {
	 
			// 选择一项，不执行其他动作
			if ( ui.item ) {
			  return;
			}
	 
			// 搜索一个匹配（不区分大小写）
			var value = this.input.val(),
			  valueLowerCase = value.toLowerCase(),
			  valid = false;
			this.element.children( "option" ).each(function() {
			  if ( $( this ).text().toLowerCase() === valueLowerCase ) {
				this.selected = valid = true;
				return false;
			  }
			});
	 
			// 找到一个匹配，不执行其他动作
			if ( valid ) {
			  return;
			}
	 
			// 移除无效的值
			this.input
			  .val( "" )
			  .attr( "title", value + " didn't match any item" )
			  .tooltip( "open" );
			this.element.val( "" );
			this._delay(function() {
			  this.input.tooltip( "close" ).attr( "title", "" );
			}, 2500 );
			this.input.data( "ui-autocomplete" ).term = "";
		  },
	 
		  _destroy: function() {
			this.wrapper.remove();
			this.element.show();
		  }
		});
		
 	/*
	 * 解绑所有事件（若有）
	 * 取消正在请求的接口
	 */
	$.fn.release = function(events,xhrKeys){
		//解绑已绑定的事件
		if (events) {
		 	$(this).unbind(events);
		} else {
			$(this).unbind();
		}
		//取消已请求的接口
		if (xhrKeys){
			comm.killRequest(xhrKeys);
		} else {
			comm.killRequest();	
		}
		return $(this);
	}  
	
	/*
	 * 中文日期默认格式(2015/6/5)
	 * 开发者直接$( "#dateID" ).datepicker(); 
	 *
	 */
	$.datepicker.regional['zh-CN'] = {
        clearText: '清除',
        clearStatus: '清除已选日期',
        closeText: '关闭',
        closeStatus: '不改变当前选择',
        prevText: '<上月',
        prevStatus: '显示上月',
        prevBigText: '<<',
        prevBigStatus: '显示上一年',
        nextText: '下月>',
        nextStatus: '显示下月',
        nextBigText: '>>',
        nextBigStatus: '显示下一年',
        currentText: '今天',
        currentStatus: '显示本月',
        monthNames: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],
        monthNamesShort: ['1月','2月','3月','4月','5月','6月', '7月','8月','9月','10月','11月','12月'],
        monthStatus: '选择月份',
        yearStatus: '选择年份',
        weekHeader: '周',
        weekStatus: '年内周次',
        dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
        dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
        dayNamesMin: ['日','一','二','三','四','五','六'],
        dayStatus: '设置 DD 为一周起始',
        dateStatus: '选择 m月 d日, DD',
        dateFormat: 'yy-mm-dd',
		changeMonth: true,
        changeYear: true,
        firstDay: 1,
        initStatus: '请选择日期',
        isRTL: false};
	$.datepicker.setDefaults($.datepicker.regional['zh-CN']); 
	
	/*
	 * 判断是否是IE及版
	 *
	 */
	$.extend({
		isIE: function(){
			if (window.navigator.userAgent.indexOf("MSIE") >= 1){
				return true;				 
			}
			return false ;
		},
		lessIE : function(version){
			if (this.isIE() ){
				return $.browser.version < version ;
			} else {
				return false;	
			}
		}
	});
	
	/**
	 * input placeHolder for IE8 
	 *
	 *
	 */
	 
	$.fn.placeholder = function(options){	
		if (!this.attr('placeholder')) return; 
		var self = this ; 
		if ( $.isIE() &&  $.lessIE(10.0)   ){			 
			var div = $('<div class="aaaa" style="position:relative;"></div>') ;			
			this.wrap(div) ;
			
			var left = options && options.left || 0 ; 
			var top  = options && options.top  || 2 ;
			//create  placeholder span
			var span = $('<span style="float: left;  margin-left: -'+(this.width()-left)+'px;margin-top: '+ top  +'px;  z-index: 121;  color: gray;  position:absolute;">'+ this.attr('placeholder') +'</span>');
				span.click(function(e) {					
                    $(self).focus();
                });
			
			this.after(span) ;
			this.off().live({ 
				keyup : function(e) {
					e.preventDefault(); 
					if (self.val() && self.val().length >0){						 
						self.next('span').hide();
					} else {
						self.next('span').show();
					}	
					return false;			
				} 
				
			}).keydown(function(e){  
					self.next('span').hide();					 
			});  
		}
		 
	}
	
	/*
	 * select plugin （兼容ie8或以上）
	 *  
	 *
	 */
	$.fn.extend({
		
		selectList : function(settings){ 			
			var settings = settings || {} ; 
			var setDeault = settings.setDeault || false; //是否设置缺省值
				width = settings.width || 100 ,//选择框宽度
				height= (!settings.height || settings.height< 24)? 24 : settings.height  ,
				optionWidth=settings.optionWidth || 120, //列表宽度
				optionHeight=settings.optionHeight ,//列表高度
				overflowX = settings.overflowX || 'hidden' ,
				overflowY = settings.overflowY || 'auto',
				fontSize=settings.fontSize || 14 ,
				self = null,
				olList = null ,
				options= [] ;
				 
				if (settings.options && $.isArray(settings.options) && settings.options.length>0){
					options= settings.options;	
				} else if ( this.attr('options') && $.isArray( eval('('+this.attr('options')+')') )  ){
					options= eval('('+this.attr('options')+')') ;				
				} 
			//取列表	
			function _getListOption(olList,options){
				olList.empty();	
				self.val(name);		
				for (var i = 0 ; i<options.length ;i++){						
					var li = $('<li value="'+options[i].value+'">'+options[i].name+'</li>').appendTo(olList);
						li.css({
							'width':'100%', 
							'padding-left': '0.3em', 
							'font-size': fontSize+'px', 
							'height': height+'px',
							'line-height':height+'px',
							'border-bottom':'solid 1px #CCC',
							'overflow':'hidden',
							'text-overflow':'ellipsis',
							'white-space':'nowrap'						
						});	
					if (i==0 && setDeault){
						self.val(name);
					}	
				}	
			}
				 
			//options= options || ($(this).attr('options') && eval('('+ $(this).attr('options')+')') );
			var divInputDialog = $(this).parent('div[class="div-input-dialog"]') ;
			if (divInputDialog.length){
				_getListOption($(this).next('ol'),options);			
				return this;
			}
			
			this.css({
					'position': 'relative' ,
					'width': width + 'px',
					'height': height +'px',
					'display': 'block',
					'font-size': fontSize+'px',
					'border': 'solid 1px #6C6',
					'line-height': height+'px',
					'padding': '0px 0px 0px 5px',
					'border-radius':'5px',
					'overflow':'hidden',
					'text-overflow':'ellipsis',
					'white-space':'nowrap'				 
					//'background-image':'url(resources/img/select_bt_down.gif)',
					//'background-repeat':'no-repeat',
					//'background-position':'right center' 
				});
			
			
			
			this.wrap('<div class="div-input-dialog" style="width:'+ (width+5) +'px;"></div');
			
			var spanIcon = $('<span id="spanIcon" class="span-icon-select" ></span>') ;
				spanIcon.css({
					'position':'relative',
					'float':'right',
					'width':'24px',
					'height':'24px',
					'margin-top':'-'+ (25+(height-24)/2) +'px',			 
					'display':'block',				 
					'background-image':'url(resources/img/select_bt_down.gif)',
					'background-repeat':'no-repeat',
					'background-position':'right center' , 
					'z-index':10  
				});	
				//spanIcon.insertAfter(this).css('margin-left', (thisWidth*1+10)+'px').click($.proxy(function(e){
				spanIcon.insertAfter(this).click($.proxy(function(e){	
					 this.trigger('click');
				},this));	
			
			
			 
				
			if (!self.next('ol')[0] && options.length){
				$('<ol  class="ol-input-dialog none"></ol>').insertAfter(self).css({				
					'position':'absolute', 
					'list-style-type':'none', 
					'margin-top':'-1px', 
					'padding': '0', 
					'width': optionWidth+'px',
					'height':optionHeight+'px' ,
					'overflow-x':overflowX,
					'overflow-y':overflowY,
					'border':'solid 1px #6C9', 
					'background-color':'#FFF ',
					'z-index':100			
				});
			  
				_getListOption(self.next('ol'),options);
				 
			} 
			var ol = self.next('ol');
			self.on('blur',function(e){
				options.length && setTimeout(function(){ol.addClass('none');},200);			
			});
			
			self.live('click' , function(e) {
				 
				console.log(self.attr('id'))
				if (options.length && ol[0] ){
					if (ol.hasClass('none')){
						ol.removeClass('none');
					} else {
						ol.addClass('none');
					}					
				} 	
			}); 
					 
			
			ol.on('mouseleave',function(e){
				//$(this).addClass('none');
			}).on('mouseover' ,'li' ,function(e){
				$(this).addClass('bgGray');
			}).on('mouseout' ,'li' ,function(e){
				if (!$(this).attr('selected')){
					$(this).removeClass('bgGray');
				}
			}).on('click' ,'li' ,function(e){
				e.preventDefault();
				e.stopPropagation();			
				ol.find('li').each(function(key,li){
					//清除选中状态
					$(li).removeClass('bgGray').removeAttr('selected');
				});
				//设置新的选中状态
				$(this).addClass('bgGray').attr('selected','true');					
				
				ol.prev('input').val($(this).text());
				
				ol.prev('input').attr('optionValue',$(this).attr('value') );
				
				ol.addClass('none');
				 
				self.trigger('change');
			});
						
			return this.each(function() {
				self = $(this);
				
				
			}) ;
		}
	}); 
	
	
	
	
	
	/* 
	$.fn.selectList = function(settings){
		var settings = settings || {} ; 
		var setDeault = settings.setDeault || false; //是否设置缺省值
			width = settings.width || 100 ,//选择框宽度
			height= (!settings.height || settings.height< 24)? 24 : settings.height  ,
			optionWidth=settings.optionWidth || 120, //列表宽度
			optionHeight=settings.optionHeight ,//列表高度
			overflowX = settings.overflowX || 'hidden' ,
			overflowY = settings.overflowY || 'auto',
			fontSize=settings.fontSize || 14 ,
			self = this ,
			olList = null ,
			options= [] ;
			 
			if (settings.options && $.isArray(settings.options) && settings.options.length>0){
				options= settings.options;	
			} else if ( this.attr('options') && $.isArray( eval('('+this.attr('options')+')') )  ){
				options= eval('('+this.attr('options')+')') ;				
			} 
		//取列表	
		function _getListOption(olList,options){
			olList.empty();	
			self.val(name);		
			for (var i = 0 ; i<options.length ;i++){						
				var li = $('<li value="'+options[i].value+'">'+options[i].name+'</li>').appendTo(olList);
					li.css({
						'width':'100%', 
						'padding-left': '0.3em', 
						'font-size': fontSize+'px', 
						'height': height+'px',
						'line-height':height+'px',
						'border-bottom':'solid 1px #CCC',
						'overflow':'hidden',
						'text-overflow':'ellipsis',
						'white-space':'nowrap'						
					});	
				if (i==0 && setDeault){
					self.val(name);
				}	
			}	
		}
			 
		//options= options || ($(this).attr('options') && eval('('+ $(this).attr('options')+')') );
		var divInputDialog = $(this).parent('div[class="div-input-dialog"]') ;
		if (divInputDialog.length){
			_getListOption($(this).next('ol'),options);			
			return this;
		}
		
		this.css({
				'position': 'relative' ,
				'width': width + 'px',
				'height': height +'px',
				'display': 'block',
				'font-size': fontSize+'px',
				'border': 'solid 1px #6C6',
				'line-height': height+'px',
				'padding': '0px 0px 0px 5px',
				'border-radius':'5px',
		  		'overflow':'hidden',
		  		'text-overflow':'ellipsis',
				'white-space':'nowrap'				 
				//'background-image':'url(resources/img/select_bt_down.gif)',
				//'background-repeat':'no-repeat',
				//'background-position':'right center' 
			});
		
		
		
		this.wrap('<div class="div-input-dialog" style="width:'+ (width+5) +'px;"></div');
		
		var spanIcon = $('<span id="spanIcon" class="span-icon-select" ></span>') ;
			spanIcon.css({
				'position':'relative',
				'float':'right',
				'width':'24px',
				'height':'24px',
				'margin-top':'-'+ (25+(height-24)/2) +'px',			 
				'display':'block',				 
				'background-image':'url(resources/img/select_bt_down.gif)',
				'background-repeat':'no-repeat',
				'background-position':'right center' , 
				'z-index':10  
			});	
			//spanIcon.insertAfter(this).css('margin-left', (thisWidth*1+10)+'px').click($.proxy(function(e){
			spanIcon.insertAfter(this).click($.proxy(function(e){	
				 this.trigger('click');
			},this));	
		
		
		 
			
		if (!self.next('ol')[0] && options.length){
			$('<ol  class="ol-input-dialog none"></ol>').insertAfter(self).css({				
				'position':'absolute', 
				'list-style-type':'none', 
				'margin-top':'-1px', 
				'padding': '0', 
				'width': optionWidth+'px',
				'height':optionHeight+'px' ,
				'overflow-x':overflowX,
				'overflow-y':overflowY,
				'border':'solid 1px #6C9', 
				'background-color':'#FFF ',
				'z-index':100			
			});
		  
			_getListOption(self.next('ol'),options);
			 
		} 
		var ol = self.next('ol');
		self.on('blur',function(e){
			options.length && setTimeout(function(){ol.addClass('none');},200);			
		});
		
		self.click(function(e) {
			 
		 	console.log(self.attr('id'))
			if (options.length && ol[0] ){
				if (ol.hasClass('none')){
					ol.removeClass('none');
				} else {
					ol.addClass('none');
				}					
			} 	
        }); 
				 
		
		ol.on('mouseleave',function(e){
			//$(this).addClass('none');
		}).on('mouseover' ,'li' ,function(e){
			$(this).addClass('bgGray');
		}).on('mouseout' ,'li' ,function(e){
			if (!$(this).attr('selected')){
				$(this).removeClass('bgGray');
			}
		}).on('click' ,'li' ,function(e){
			e.preventDefault();
			e.stopPropagation();			
			ol.find('li').each(function(key,li){
				//清除选中状态
				$(li).removeClass('bgGray').removeAttr('selected');
			});
			//设置新的选中状态
			$(this).addClass('bgGray').attr('selected','true');					
			
			ol.prev('input').val($(this).text());
			
			ol.prev('input').attr('optionValue',$(this).attr('value') );
			
			ol.addClass('none');
			 
			self.trigger('change');
		});
					
		return this;
	}
	 */
	 	
	 
})(jQuery);

