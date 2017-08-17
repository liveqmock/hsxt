/*
 * 修改by 万华成
 * 2015-10-16 解决ie6,7,8下兼容性问题 
 * 2016-03-18 提示框统一 ,加了文件大小检测
 */
;function readyImage(url,callback){var img=new Image();img.src=url;if(img.complete){var imgWh=[img.width,img.height];callback(i,imgWh);}else{img.onload=function(){var imgWh=[img.width,img.height];callback(i,imgWh);}}}
(function($){jQuery.fn.extend({uploadPreview:function(opts){opts=jQuery.extend({width:0,height:0,imgDiv:opts.imgDiv||"#imgDiv",imgType:["gif","jpeg","jpg","bmp","png"],maxwidth:0,maxheight:0,imgurl:null,callback:function(){return false;}},opts||{});var _self=this;var _this=$(this);if(window.navigator.userAgent.indexOf("MSIE")>=1&&($.browser.version=="7.0"||$.browser.version=="8.0")){_this.after("<input type='text' placeholder='ie8' style='width:0px;'>");}
var imgDiv=$(opts.imgDiv);imgDiv.css({'overflow':'hidden','width':opts.width+'px','height':opts.height+'px'});autoScaling=function(){if($.browser.version=="7.0"||$.browser.version=="8.0"||$.browser.version=="9.0"){imgDiv.get(0).filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod="scale";}
var img_width=imgDiv.width();var img_height=imgDiv.height();if(img_width>0&&img_height>0){var rate=(opts.width/img_width<opts.height/img_height)?opts.width/img_width:opts.height/img_height;if(rate<=1){if($.browser.version=="7.0"||$.browser.version=="8.0")imgDiv.get(0).filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod="scale";imgDiv.width(img_width*rate);imgDiv.height(img_height*rate);}else{imgDiv.width(img_width);imgDiv.height(img_height);}
var left=(opts.width-imgDiv.width())*0.5;var top=(opts.height-imgDiv.height())*0.5;}
imgDiv.show();}
_this.change(function(){if(this.value){if(!RegExp("\.("+opts.imgType.join("|")+")$","i").test(this.value.toLowerCase())){if(typeof comm.alert=="function"){comm.alert({width:420,imgClass:'tips_i',imgFlag:true,content:"图片类型必须是"+opts.imgType.join("，")+"中的一种"});}else{alert("图片类型必须是"+opts.imgType.join("，")+"中的一种");}
this.value="";return false;}
var fileInput=$(this)[0];byteSize=fileInput.files[0]&&fileInput.files[0].size;if(opts.maxSize&&byteSize&&(byteSize/(1024*1024)).toFixed(2)>opts.maxSize){if(typeof comm.alert=="function"){comm.alert({width:420,imgClass:'tips_i',imgFlag:true,content:"图片大小不超过"+opts.maxSize+"M"});}else{alert("图片大小不超过"+(byteSize/(1024*1024)).toFixed(2)+"M");}
$(this).parent().prev().find('img').attr('src',opts.imgurl||'resources/img/noImg.gif');var currFile=$(this),newFile;newFile=currFile.clone().val("");currFile.after(newFile);currFile.remove();newFile.uploadPreview(opts);return;};imgDiv.hide();if($.browser.msie&&$.browser.version<10){if($.browser.version=="6.0"){var img=$("<img />");img.attr('src','file:///'+this.value);img.css({"width":opts.width+'px',"height":opts.height+'px'});imgDiv.empty().append(img);autoScaling();}
else{imgDiv.css({filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image)"});imgDiv.get(0).filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod="image";try{imgDiv.get(0).filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src=getPath(_this[0]);imgDiv.children().hide();}catch(e){if(typeof comm.alert=="function"){comm.alert({width:420,imgClass:'tips_i',imgFlag:true,content:"无效的图片文件！"});}else{alert("无效的图片文件！");}
return;}
_this.blur();if(window.navigator.userAgent.indexOf("MSIE")>=1&&($.browser.version=="7.0"||$.browser.version=="8.0"||$.browser.version=="9.0")){_this.next('input[placeholder="ie8"]').focus();}
setTimeout("autoScaling()",1);}}
else{var img=$("<img />");img.attr('src',window.URL.createObjectURL(this.files.item(0)));img.css({"vertical-align":"middle","width":opts.width,"height":opts.height});imgDiv.empty().append(img);setTimeout("autoScaling()",1);}}});}});})(jQuery);function getPath(obj){if(obj){if(window.navigator.userAgent.indexOf("MSIE")>=1){obj.select();return document.selection.createRange().text;}
else if(window.navigator.userAgent.indexOf("Firefox")>=1||window.navigator.userAgent.indexOf("Chrome")){if(obj.files){return window.URL.createObjectURL(this.files.item(0));}
return obj.value;}
return obj.value;}}
function clearvalue(obj){obj.select();document.execCommand("delete");}
 