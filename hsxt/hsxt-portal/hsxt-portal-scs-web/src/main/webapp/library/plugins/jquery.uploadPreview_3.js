/*
 * 2015-5-20 万华成
 * 解决ie6,7,8下兼容性问题
 */
 
/*
(function($){
var props = ['Width', 'Height'],prop;

while (prop = props.pop()) {
(function (natural, prop) {
  $.fn[natural] = (natural in new Image()) ? 
  function () {
  return this[0][natural];
  } : 
  function () {
  var 
  node = this[0],
  img,
  value;

  if (node.tagName.toLowerCase() === 'img') {
	img = new Image();
	img.src = node.src,
	value = img[prop];
  }
  return value;
  };
}('natural' + prop, prop.toLowerCase()));
}
}(jQuery));
*/ 
 
 // 更新：
// 05.27: 1、保证回调执行顺序：error > ready > load；2、回调函数this指向img本身
// 04-02: 1、增加图片完全加载后的回调 2、提高性能

/**
 * 图片头数据加载就绪事件 - 更快获取图片尺寸
 * @version	2011.05.27
 * @see		http://blog.phpdr.net/js-get-image-size.html
 * @param	{String}	图片路径
 * @param	{Function}	尺寸就绪
 * @param	{Function}	加载完毕 (可选)
 * @param	{Function}	加载错误 (可选)
 * @example imgReady('http://www.google.com.hk/intl/zh-CN/images/logo_cn.png', function () {
		alert('size ready: width=' + this.width + '; height=' + this.height);
	});

var imgReady = (function () {
	var list = [], intervalId = null,

	// 用来执行队列
	tick = function () {
		var i = 0;
		for (; i < list.length; i++) {
			list[i].end ? list.splice(i--, 1) : list[i]();
		};
		!list.length && stop();
	},

	// 停止所有定时器队列
	stop = function () {
		clearInterval(intervalId);
		intervalId = null;
	};

	return function (url, ready, load, error) {
		var onready, width, height, newWidth, newHeight,
			img = new Image();

		img.src = url;

		// 如果图片被缓存，则直接返回缓存数据
		if (img.complete) {
			ready.call(img);
			load && load.call(img);
			return;
		};

		width = img.width;
		height = img.height;

		// 加载错误后的事件
		img.onerror = function () {
			error && error.call(img);
			onready.end = true;
			img = img.onload = img.onerror = null;
		};

		// 图片尺寸就绪
		onready = function () {
			newWidth = img.width;
			newHeight = img.height;
			if (newWidth !== width || newHeight !== height ||
				// 如果图片已经在其他地方加载可使用面积检测
				newWidth * newHeight > 1024
			) {
				ready.call(img);
				onready.end = true;
			};
		};
		onready();

		// 完全加载完毕的事件
		img.onload = function () {
			// onload在定时器时间差范围内可能比onready快
			// 这里进行检查并保证onready优先执行
			!onready.end && onready();

			load && load.call(img);

			// IE gif动画会循环执行onload，置空onload即可
			img = img.onload = img.onerror = null;
		};

		// 加入队列中定期执行
		if (!onready.end) {
			list.push(onready);
			// 无论何时只允许出现一个定时器，减少浏览器性能损耗
			if (intervalId === null) intervalId = setInterval(tick, 40);
		};
	};
})();
 
  */
 

//图片预加载
function readyImage(url,callback){
    var img = new Image(); 
	img.src=url;
	if(img.complete){  
		var imgWh = [img.width,img.height];
		callback(i,imgWh);
	}else{
		img.onload  = function(){
			var imgWh = [img.width,img.height];
			callback(i,imgWh);
		}
	}
}

 
(function($) {
    jQuery.fn.extend({
        uploadPreview: function(opts) {
			var _self = this;
            var _this = $(this);
			if (!opts.width && $("#imgDiv").width() > 0 ) {
				opts.width = $("#imgDiv").width() ;
			}
			if (!opts.height && $("#imgDiv").height() > 0 ) {
				opts.height = $("#imgDiv").height() ;
			} 
			
            opts = jQuery.extend({
                width: 100  ,
                height:100   ,
                imgDiv: "#imgDiv",
                imgType: ["gif", "jpeg", "jpg", "bmp", "png"],
                maxwidth: 1000 ,
                maxheight: 1000,
                imgurl: null,
                callback: function() { return false; }
            }, opts || { });
            
			if (window.navigator.userAgent.indexOf("MSIE") >= 1 && ($.browser.version == "7.0" || $.browser.version == "8.0") ) {
				//兼容ie8或以下
				_this.after("<input type='text' placeholder='ie8' style='width:0px;'>");
			}
			
            var imgDiv = $(opts.imgDiv);
				
			//imgDiv.css({'overflow':'auto','width':opts.widths,'height':opts.height});	
			//若有自定宽高值，则重设imgDiv框的宽高值，否则使用imgDiv的css样式宽高值
            opts && opts.width  && imgDiv.width(opts.width);
            opts && opts.height && imgDiv.height(opts.height);

            autoScaling = function() {
				
                if ($.browser.version == "7.0" || $.browser.version == "8.0") {
					imgDiv.get(0).filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = "image"; 
				}
                var img_width  = imgDiv.width() ;
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
			 	var img_realWidth ,img_realHeight,img_realRate; 
				var img_bak = new Image() ;
				
				if ($.browser.version == "7.0" || $.browser.version == "8.0") {
					imgDiv.get(0).filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = "scale" ;
					var rate , whichScale ;
					/* 
					img_bak.onload = function() {
					  alert('successfully loaded');
					};
					
					img_bak.onerror = function() {
					  alert('broken image!');
					}; 
					 */
					//imgDiv.find('img').height(img_height);					 
					//imgDiv.find('img').width(img_width);
				 	
					imgDiv.show(); 
				 	 
					
					
				} else {
				 
					img_bak.onload = function(){ 
						img_realWidth = img_bak.width ;
						img_realHeight= img_bak.height;	
						img_realRate  = img_realWidth/img_realHeight ;
						//alert(img_bak.width);
						if (img_realWidth >  0 && img_realHeight > 0){						
							//var rate = ( img_realWidth / img_width < img_realHeight / img_height ) ? img_realWidth / img_width : img_realHeight / img_height;
							var rate , whichScale ;
							if ( img_realWidth / img_width < img_realHeight / img_height ){
								//缩放原始高度
								rate = img_height / img_realHeight ;
								whichScale = 'H' ;
							} else {
								//缩放原始宽度
								rate = img_width / img_realWidth  ;
								whichScale = 'W' ;
							}
							if ( whichScale === 'H' && rate < 1  ){
								imgDiv.find('img').height(img_height);
							} else if (whichScale === 'W' && rate < 1) {
								imgDiv.find('img').width(img_width);
							}
							
							
							/*
							if (rate <= 1) {
								if ($.browser.version == "7.0" || $.browser.version == "8.0") imgDiv.get(0).filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = "scale" ;
								imgDiv.find('img').width(img_width  );
								//imgDiv.find('img').height(img_height * rate);
								//imgDiv.find('img').width(300);
							} else {
								imgDiv.find('img').width(img_width);
								//imgDiv.find('img').height(img_height);
								//imgDiv.find('img').width(300);
							}	
							 */
							
							imgDiv.show(); 
							
						} 
					}
									
				
				}
				//debugger
				
				$(img_bak).attr('src',(imgDiv.find('img')[0] && imgDiv.find('img')[0].src) || imgDiv.find('img').src );
				//img_bak.src = 'https://www.baidu.com/img/bd_logo1.png';
				//img_bak.src =  (imgDiv.find('img')[0] && imgDiv.find('img')[0].src) || imgDiv.find('img').src  ; // +'&time='+ (new Date()).getTime() ; 
				//alert(img_bak.width);
			 	
				 
				$('#imgDiv img').imgAreaSelect({ selectionColor: 'blue', x1:0, y1:0, x2: 950, maxWidth:950,minWidth:1,y2:400,minHeight:10,maxHeight:400, selectionOpacity: 0, onSelectEnd: function(){},aspectRatio:"1:1",fadeSpeed:true,handles:true ,imageHeight:0 });
				  
 			 	//alert( img_bak.width );
				/*  
                if (img_width > 0 && img_height > 0) {
                    var rate = (opts.width / img_width < opts.height / img_height) ? opts.width / img_width : opts.height / img_height;
                    if (rate <= 1) {
                        if ($.browser.version == "7.0" || $.browser.version == "8.0") imgDiv.get(0).filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = "scale";
                        imgDiv.width(img_width * rate);
                        imgDiv.height(img_height * rate);
						//imgDiv.find('img').width(300);
                    } else {
                        imgDiv.width(img_width);
                        imgDiv.height(img_height);
						//imgDiv.find('img').width(300);
                    }
                    //var left = (opts.width - imgDiv.width()) * 0.5;
                    //var top = (opts.height - imgDiv.height()) * 0.5;
                    //imgDiv.css({ "margin-left": left, "margin-top": top });
					 				
                    imgDiv.show(); 
                }
				 */
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
                        //img.css({ "vertical-align": "middle","width":opts.width ,"height":opts.height});
						img.css({ "vertical-align": "middle" });
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