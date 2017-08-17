/**
 * jQuery custom selectboxes
 * 
 * modify by 万华成
 * 
 * 新增selectRefresh()方法，以解决用trigger("append")的问题
 * 2015-03-04 新增combobox使用selectVal（返回上一页时，保留数据视图功能）
 * 2015-03-10 新增超长加省略符且tooltip提示
 *
**/
(function($) { 

	jQuery.fn.selectbox = function(options){
	/* Default settings */
	var settings = {
		className: 'jquery-selectbox',
		animationSpeed: "normal",
		listboxMaxSize: 10,
		replaceInvisible: false
	};
	var commonClass = 'jquery-custom-selectboxes-replaced';
	var listOpen = false;
	var showList = function(listObj) {
		var selectbox = listObj.parents('.' + settings.className + '');
		listObj.slideDown(settings.animationSpeed, function(){
			listOpen = true;
		});
		selectbox.addClass('selecthover');
		jQuery(document).bind('click', onBlurList);
		return listObj;
	}
	var hideList = function(listObj) {
	 
		var selectbox = listObj.parents('.' + settings.className + '');
		listObj.slideUp(settings.animationSpeed, function(){
			listOpen = false;
			jQuery(this).parents('.' + settings.className + '').removeClass('selecthover');
		});
		jQuery(document).unbind('click', onBlurList);
		return listObj;
	}
	var onBlurList = function(e) {
		var trgt = e.target;
		var currentListElements = jQuery('.' + settings.className + '-list:visible').parent().find('*').andSelf();
		if(jQuery.inArray(trgt, currentListElements)<0 && listOpen) {
			hideList( jQuery('.' + commonClass + '-list') );
		}
		return false;
	}
	
	
	
	/* Processing settings */
	settings = jQuery.extend(settings, options  );
	
	
	/* append each select option 
	 * add by wanhuacheng
	 * begin
	 */
	var appendOption = function(_this,settings,replacement){
			jQuery('option', _this).each(function(k,v){				
				
				var v = jQuery(v);
				var listElement =  jQuery('<span class="' + settings.className + '-item value-'+v.val()+' item-'+k+'" title="'+ v.text() +'">' + v.text() + '</span>');	
				listElement.click(function(){
					var thisListElement = jQuery(this);
					var thisReplacment = thisListElement.parents('.'+settings.className);
					var thisIndex = thisListElement[0].className.split(' ');
					for( k1 in thisIndex ) {
						if(/^item-[0-9]+$/.test(thisIndex[k1])) {
							thisIndex = parseInt(thisIndex[k1].replace('item-',''), 10);
							break;
						}
					};
					var thisValue = thisListElement[0].className.split(' ');
					for( k1 in thisValue ) {
						if(/^value-.+$/.test(thisValue[k1])) {
							thisValue = thisValue[k1].replace('value-','');
							break;
						}
					};
					thisReplacment
						.find('.' + settings.className + '-currentItem')
						.text(thisListElement.text());
					/*	
					thisReplacment
						.find('select')
						.val(thisValue)
						.triggerHandler('change');
					 */	
					var thisSublist = thisReplacment.find('.' + settings.className + '-list');
					if(thisSublist.filter(":visible").length > 0) {
						hideList( thisSublist );
					}else{
						showList( thisSublist );
					}
				}).bind('mouseenter',function(){
					jQuery(this).addClass('listelementhover');
				}).bind('mouseleave',function(){
					jQuery(this).removeClass('listelementhover');
				});
				jQuery('.' + settings.className + '-list', replacement).append(listElement);
				if(v.filter(':selected').length > 0) {
					jQuery('.'+settings.className + '-currentItem', replacement).text(v.text());
				}
			});
		
	}
	/* end */
	
	/* Wrapping all passed elements */
	return this.each(function() {
		var _this = jQuery(this);
		/* bind append event 
		 * add by wanhuacheng
		 * begin
		 */
		$(document).on('append',_this,function(){	
			//empty span otpion
			_this.prev('span').prev('div').empty();	
			appendOption(_this,settings,replacement);
		})
		/* how to use
		 * _this.trigger('append')
		 * 
		 */
	 
		/* end */
		
		if(_this.filter(':visible').length == 0 && !settings.replaceInvisible)
			return;
		if (!options){
			selectDiv = '<div class="' + settings.className + ' ' + commonClass + '">';
			buttonDiv = '<div class="' + settings.className + '-moreButton" />'	 
			optionDiv = '<div class="' + settings.className + '-list ' + commonClass + '-list" />'	;	
		} else {
			selectDiv = '<div class="' + settings.className + ' ' + commonClass + '" style="width:'+options.width+'px;">';	
			buttonDiv = '<div class="' + settings.className + '-moreButton" style="width:'+options.width+'px" />'
			if (options.height > 0){			
				optionDiv = '<div class="' + settings.className + '-list ' + commonClass + '-list" style="width:'+options.width+'px;height:'+ (options.height) +'px;" />';
			} else {
				optionDiv = '<div class="' + settings.className + '-list ' + commonClass + '-list" style="width:'+options.width+'px;" />';
			}
		}
			
		var replacement = jQuery(
				selectDiv + buttonDiv + optionDiv +
				'<span class="' + settings.className + '-currentItem" />' + '</div>'
		);
		
		appendOption(_this,settings,replacement);
		
		/*
		jQuery('option', _this).each(function(k,v){			
			
			var v = jQuery(v);
			var listElement =  jQuery('<span class="' + settings.className + '-item value-'+v.val()+' item-'+k+'">' + v.text() + '</span>');	
			listElement.click(function(){
				var thisListElement = jQuery(this);
				var thisReplacment = thisListElement.parents('.'+settings.className);
				var thisIndex = thisListElement[0].className.split(' ');
				for( k1 in thisIndex ) {
					if(/^item-[0-9]+$/.test(thisIndex[k1])) {
						thisIndex = parseInt(thisIndex[k1].replace('item-',''), 10);
						break;
					}
				};
				var thisValue = thisListElement[0].className.split(' ');
				for( k1 in thisValue ) {
					if(/^value-.+$/.test(thisValue[k1])) {
						thisValue = thisValue[k1].replace('value-','');
						break;
					}
				};
				thisReplacment
					.find('.' + settings.className + '-currentItem')
					.text(thisListElement.text());
				thisReplacment
					.find('select')
					.val(thisValue)
					.triggerHandler('change');
				var thisSublist = thisReplacment.find('.' + settings.className + '-list');
				if(thisSublist.filter(":visible").length > 0) {
					hideList( thisSublist );
				}else{
					showList( thisSublist );
				}
			}).bind('mouseenter',function(){
				jQuery(this).addClass('listelementhover');
			}).bind('mouseleave',function(){
				jQuery(this).removeClass('listelementhover');
			});
			jQuery('.' + settings.className + '-list', replacement).append(listElement);
			if(v.filter(':selected').length > 0) {
				jQuery('.'+settings.className + '-currentItem', replacement).text(v.text());
			}
		});
		
		*/
		
		replacement.find('.' + settings.className + '-moreButton').click(function(){
			var thisMoreButton = jQuery(this);
			var otherLists = jQuery('.' + settings.className + '-list')
				.not(thisMoreButton.siblings('.' + settings.className + '-list'));
			hideList( otherLists );
			var thisList = thisMoreButton.siblings('.' + settings.className + '-list');
			if(thisList.filter(":visible").length > 0) {
				hideList( thisList );
			}else{
				showList( thisList );
			}
		}).bind('mouseenter',function(){
			jQuery(this).addClass('morebuttonhover');
		}).bind('mouseleave',function(){
			jQuery(this).removeClass('morebuttonhover');
		});
		_this.hide().replaceWith(replacement).appendTo(replacement);
		var thisListBox = replacement.find('.' + settings.className + '-list');
		var thisListBoxSize = thisListBox.find('.' + settings.className + '-item').length;
		if(thisListBoxSize > settings.listboxMaxSize)
			thisListBoxSize = settings.listboxMaxSize;
		if(thisListBoxSize == 0)
			thisListBoxSize = 1;	
		var thisListBoxWidth = Math.round(_this.width() + 5);
		 
	});
}
jQuery.fn.unselectbox = function(){
	var commonClass = 'jquery-custom-selectboxes-replaced';
	return this.each(function() {
		var selectToRemove = jQuery(this).filter('.' + commonClass);
		selectToRemove.replaceWith(selectToRemove.find('select').show());		
	});
}

 
jQuery.fn.selectRefresh = function(){ 
	var _this = jQuery(this);
	//清空span选项
	_this.prev('span').prev('div').empty();
	
	if (_this.children('option').length ==0){
		_this.prev('span').text('');
		return;
	}
	
	_this.children('option').each(function(key,el){
 
		//设置标题
		if (key==0){
			_this.prev('span').text(el.text);
		}
		var span = '<span class="jquery-selectbox-item value-'+el.value+' item-'+key+' "  data-index="'+key+'" title="'+el.text+'">'+el.text+'</span>';
		
		_this.prev('span').prev('div').append(span) ;	 
		
	});
}

//扩展设置下拉框的值   2015/1/09 by kongjt
$.fn.selectVal=function(key,change){
	var t = $(this);
	if(t.find('option[value="'+key+'"]').length>0){
		var _val=t.find('option[value="'+key+'"]').eq(0).text();
		t.find('option').attr("selected", false);
		t.find('option[value="'+key+'"]').attr("selected", true);
		//selectBox
		t.prev('.jquery-selectbox-currentItem').text(_val);
		//combobox
		t.next('.custom-combobox').find('.custom-combobox-input').val(_val);
		
		if(!change)t.triggerHandler('change');
		return t;
	}
};

//扩展设置下拉框的Index   2015/1/13 by kongjt
$.fn.selectIndex=function(key,change){
	if(isNaN(key))return;
	var t = $(this);
	if(t.find('option').length>0){
		var _val=t.find('option').eq(key).text();
		t.find('option').attr("selected", false);
		t.find('option').eq(key).attr("selected", true);
		//selectBox
		t.prev('.jquery-selectbox-currentItem').text(_val);
		//combobox
		t.next('.custom-combobox').find('.custom-combobox-input').val(_val);
		if(!change)t.triggerHandler('change');
		return t;
	}
};

//加hover功能
$(document).on('mouseenter','.jquery-selectbox-item',function(){
	jQuery(this).addClass('listelementhover');
});
$(document).on('mouseleave','.jquery-selectbox-item',function(){
	//debugger
	jQuery(this).removeClass('listelementhover');
});
// 切换选择功能
$(document).on('click','.jquery-selectbox-item',function(e){	
	 
	jQuery(this).parent().next('.jquery-selectbox-currentItem').text( jQuery(this).text() );
	//debugger
	//设置选中索引
	var startPos = jQuery(this).attr('class').indexOf('value-')*1+6;
	var endPos   = jQuery(this).attr('class').indexOf('item-')*1-1;
	var index = jQuery(this).attr('class').substring(startPos,endPos);
	
	var sel =   jQuery(this).parents('.jquery-selectbox').find('select');
		 
		sel.val(index);
		sel.triggerHandler('change');
	 
	// 隐藏选择面板
	$(this).parent('.jquery-selectbox-list').slideUp("normal",function(){
			// 清除选中样式
		    jQuery(this).removeClass('listelementhover');			
			 
	});
      
	 
});
  
})(jQuery);     