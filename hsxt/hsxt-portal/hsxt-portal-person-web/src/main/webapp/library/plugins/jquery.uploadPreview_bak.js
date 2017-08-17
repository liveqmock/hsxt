/*
 * 2015-5-20 万华成
 * 解决ie6,7,8下兼容性问题
 */
(function($) {
    jQuery.fn.extend({
        uploadPreview: function(opts) {
            opts = jQuery.extend({
                width: 0,
                height: 0,
                imgDiv: "#imgDiv",
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
				
			imgDiv.css({'overflow':'hidden','width':opts.widths,'height':opts.height});	
            //imgDiv.width(opts.width);
            //imgDiv.height(opts.height);

            autoScaling = function() {
				
                if ($.browser.version == "7.0" || $.browser.version == "8.0") {
					imgDiv.get(0).filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = "image";
					 
					 						 
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
                    imgDiv.css({ "margin-left": left, "margin-top": top });
                    imgDiv.show();
					 
                }
            }
            _this.change(function() {
                if (this.value) {
                    if (!RegExp("\.(" + opts.imgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
                        alert("图片类型必须是" + opts.imgType.join("，") + "中的一种");
                        this.value = "";
                        return false;
                    }
                    imgDiv.hide();
                    if ($.browser.msie) {
                        if ($.browser.version == "6.0") {
                            var img = $("<img />");
                            //imgDiv.replaceWith(img);
                            //imgDiv = img;
/*
                            var image = new Image();
                            image.src = 'file:///' + this.value;
*/
                            img.attr('src', 'file:///' + this.value);
							img.css({"width":opts.width ,"height":opts.height});
							imgDiv.empty().append(img);
                            autoScaling();
                        }
                        else {
                            imgDiv.css({ filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image)" });
                            imgDiv.get(0).filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = "image";
                            try {

                                imgDiv.get(0).filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = getPath(_this[0]);	
								
								
                            } catch (e) {
                                alert("无效的图片文件！");
                                return;
                            }
							if (window.navigator.userAgent.indexOf("MSIE") >= 1 && ($.browser.version == "7.0" || $.browser.version == "8.0")   ) {								
								_this.next('input[placeholder="ie8"]').focus();
							}
							//_this[0].unselect();
							_this.trigger('blur');
                            setTimeout("autoScaling()", 10);

                        }
                    }
                    else {
                        var img = $("<img />");
         
					    img.attr('src', window.URL.createObjectURL(this.files.item(0)) );
                        img.css({ "vertical-align": "middle","width":opts.width ,"height":opts.height});
						imgDiv.empty().append(img);
						
                        setTimeout("autoScaling()", 10);
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


/*
* 
*时间:2014年08月19日
*介绍:图片上传预览插件 兼容浏览器(IE 谷歌 火狐) 不支持safari 当然如果是使用这些内核的浏览器都兼容
*网站:http://jquery.decadework.com http://www.oschina.net/code/snippet_1049351_26784
*QQ:200592114
*版本:1.2
*参数说明: Img:图片ID;Width:预览宽度;Height:预览高度;ImgType:支持文件类型;Callback:选择文件后回调方法;
*插件说明: 基于JQUERY扩展,需要引用JQUERY库。
*使用方法:  <div>
<img id="ImgPr" width="120" height="120" /></div>
<input type="file" id="up" />
 
把需要进行预览的IMG标签外 套一个DIV 然后给上传控件ID给予uploadPreview事件
 
$("#up").uploadPreview({ Img: "ImgPr", Width: 120, Height: 120, ImgType: ["gif", "jpeg", "jpg", "bmp", "png"], Callback: function () { }});   
 
另外注意一下 使用该插件预览图片 选择文件的按钮在IE下不能是隐藏的 你可以换种方式隐藏 比如:top left 负几千像素
 
v1.2:更新jquery1.9以上版本 插件报错BUG 
*/

/*
jQuery.fn.extend({
    uploadPreview: function (opts) {
        var _self = this, _this = $(this);
        opts = jQuery.extend({
            Img: "ImgPr",
            Width: 140,
            Height: 90,
            ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
            Callback: function () { }
        }, opts || {});
        _self.getObjectURL = function (file) {
            var url = null;
            if (window.createObjectURL != undefined) {
                url = window.createObjectURL(file);
            } else if (window.URL != undefined) {
                url = window.URL.createObjectURL(file);
            } else if (window.webkitURL != undefined) {
                url = window.webkitURL.createObjectURL(file);
            }
            return url;
        }
        _this.change(function () {
            if (this.value) {
                if (!RegExp("\.(" + opts.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
                    alert("选择文件错误,图片类型必须是" + opts.ImgType.join("，") + "中的一种");
                    this.value = "";
                    return false;
                }
                if (navigator.userAgent.indexOf("MSIE") > -1) {
                    try {
                        $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]));
                    } catch (e) {
                        var src = "";
                        var obj = $("#" + opts.Img);
                        var div = obj.parent("div")[0];
                        _self.select();
                        if (top != self) {
                            window.parent.document.body.focus();
                        } else {
                            _self.blur();
                        }
                        src = document.selection.createRange().text;
                        document.selection.empty();
                        obj.hide();
                        obj.parent("div").css({
                            'filter': 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)',
                            'width': opts.Width + 'px',
                            'height': opts.Height + 'px'
                        });
                        div.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = src;
                    }
                } else {
                    $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]));
                }
                opts.Callback();
            }
        });
    }
});

*/