/*
 * 2015-10-16 万华成
 * 解决ie6,7,8下兼容性问题
 */
(function($) {
    jQuery.fn.extend({
        uploadPreview: function(opts) {
            opts = jQuery.extend({
                width: 0,
                height: 0,
                imgDiv: opts.imgDiv || "#imgDiv",
                imgType: ["gif", "jpeg", "jpg", "bmp", "png"],
                maxwidth: 0,
                maxheight: 0,
                imgurl: null,
                callback: function() { return false; }
            }, opts || {});
            var _self = this;
            var _this = $(this);
			if (window.navigator.userAgent.indexOf("MSIE") >= 1 && ($.browser.version == "7.0" || $.browser.version == "8.0") ) {
				//兼容ie8或以下
				_this.after("<input type='text' placeholder='ie8' style='width:0px;'>");
			}
			
            var imgDiv = $(opts.imgDiv);
				
			imgDiv.css({'overflow':'hidden','width':opts.width+'px','height':opts.height+'px'});	
            //imgDiv.width(opts.width);
            //imgDiv.height(opts.height);

            autoScaling = function() {
				
                if ($.browser.version == "7.0" || $.browser.version == "8.0"|| $.browser.version == "9.0") {
					imgDiv.get(0).filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = "scale";
					 
					 						 
				}
                var img_width = imgDiv.width();
                var img_height = imgDiv.height();
                
				/*
				if (img_width > opts.maxwidth || img_height > opts.maxheight) {
                    alert("图片大小不符合要求");
                    //clearvalue(_this[0]);
                    //_this.trigger("blur"); //失去控件焦点
                    
                    //imgDiv.hide();
                    return false;
                }
*/
                if (img_width > 0 && img_height > 0) {
                    var rate = (opts.width / img_width < opts.height / img_height) ? opts.width / img_width : opts.height / img_height;
                    if (rate <= 1) {
                        if ($.browser.version == "7.0" || $.browser.version == "8.0") imgDiv.get(0).filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = "scale";
                        imgDiv.width(img_width * rate);
                        imgDiv.height(img_height * rate);
                    } else {
                        imgDiv.width(img_width);
                        imgDiv.height(img_height);
                    }
                    var left = (opts.width - imgDiv.width()) * 0.5;
                    var top = (opts.height - imgDiv.height()) * 0.5;
                    //imgDiv.css({ "margin-left": left, "margin-top": top });
                    
					 
                }
                imgDiv.show();
            }
            _this.change(function() {
                if (this.value) {
                    if (!RegExp("\.(" + opts.imgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
                        comm.alert({width:420,imgClass:'tips_i',imgFlag:true,content:"图片类型必须是" + opts.imgType.join("，") + "中的一种"});
                        this.value = "";
                        return false;
                    }
                    imgDiv.hide(); 
                    if ($.browser.msie && $.browser.version<10) {
                        if ($.browser.version == "6.0") {
                            var img = $("<img />");
                            //imgDiv.replaceWith(img);
                            //imgDiv = img;
/*
                            var image = new Image();
                            image.src = 'file:///' + this.value;
*/
                            img.attr('src', 'file:///' + this.value);
							img.css({"width":opts.width+'px' ,"height":opts.height+'px'});
							imgDiv.empty().append(img);
                            autoScaling();
                        }
                        else {
                        	 
                            imgDiv.css({ filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image)" });
                            imgDiv.get(0).filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = "image";
                            try { 
                                imgDiv.get(0).filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = getPath(_this[0]) ;	
							    imgDiv.children().hide(); 
                            } catch (e) {                            
                                comm.alert({width:420,imgClass:'tips_i',imgFlag:true,content:"无效的图片文件！"});
                                return;
                            }
                            _this.blur();	
							if (window.navigator.userAgent.indexOf("MSIE") >= 1 && ($.browser.version == "7.0" || $.browser.version == "8.0" || $.browser.version == "9.0")   ) {								
								_this.next('input[placeholder="ie8"]').focus();	
							}
							//_this[0].unselect();							 		
                            setTimeout("autoScaling()", 1);

                        }
                    }
                    else {
                        var img = $("<img />");
         
					    img.attr('src', window.URL.createObjectURL(this.files.item(0)) );
                        img.css({ "vertical-align": "middle","width":opts.width ,"height":opts.height});
						imgDiv.empty().append(img);
						
                        setTimeout("autoScaling()", 1);
                    }
                }
            });
        }
    });
})(jQuery);
//获得上传控件的值，obj为上传控件对象
function getPath(obj) {
    if (obj) {
        if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
            obj.select();
            return document.selection.createRange().text   ;
        }
        else if (window.navigator.userAgent.indexOf("Firefox") >= 1 || window.navigator.userAgent.indexOf("Chrome")) {
            if (obj.files) {
                //return obj.files.item(0).getAsDataURL();
				return window.URL.createObjectURL(this.files.item(0)) ;
            }
            return obj.value;
        }
        return obj.value;
    }
}
//清空上传控件的值，obj为上传控件对象
function clearvalue(obj) {
    obj.select();
    document.execCommand("delete");
}  