define([], function(){
	return plaintext = {
	
		showPage : function(htmlObj, isCusts){
			plaintext.isCusts = isCusts;
			plaintext.htmlObj = htmlObj;
			if(htmlObj[0].addEventListener){
				htmlObj[0].addEventListener("paste", plaintext.pasteHandler, false);
			}else{
				htmlObj[0].attachEvent("onpaste", plaintext.pasteHandler);
			}
		},
		
        pasteHandler : function(){
            setTimeout(function(){
            	var images = null;
            	if(plaintext.isCusts){
            		images = plaintext.htmlObj.find("div .tool_talkinput_textarea").find("img");
            		plaintext.htmlObj.find("div .tool_talkinput_textarea").find("div").removeAttr("class");
                	plaintext.htmlObj.find("div .tool_talkinput_textarea").find("span").removeAttr("class");
    				plaintext.htmlObj.find("div .tool_talkinput_textarea").find("span").removeAttr("style");
            	}else{
            		images = plaintext.htmlObj.find("img");
            		plaintext.htmlObj.find("div").removeAttr("class");
                	plaintext.htmlObj.find("span").removeAttr("class");
    				plaintext.htmlObj.find("span").removeAttr("style");
            	}
				$.each(images, function(){
					var imgSrc = $(this).attr("src");
					if(imgSrc.indexOf("http") > -1){
						var index = imgSrc.indexOf("resources/img/biaoqing/");
						if(index > 0){
							$(this).removeAttr("style");
							$(this).attr("src", imgSrc.substr(index));
						}else{
							$(this).remove();
						}
					};
				});
            }, 1);
        }
	}
});
