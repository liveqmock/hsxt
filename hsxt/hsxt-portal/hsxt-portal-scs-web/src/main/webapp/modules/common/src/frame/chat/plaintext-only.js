define([], function(){
	return plaintext = {
	
		showPage : function(htmlObj){
			plaintext.htmlObj = htmlObj;
			if(htmlObj[0].addEventListener){
				htmlObj[0].addEventListener("paste", plaintext.pasteHandler, false);
			}else{
				htmlObj[0].attachEvent("onpaste", plaintext.pasteHandler);
			}
		},
		
        pasteHandler : function(){
            setTimeout(function(){
            	plaintext.htmlObj.find("div").removeAttr("class");
            	plaintext.htmlObj.find("span").removeAttr("class");
				plaintext.htmlObj.find("span").removeAttr("style");
				$.each(plaintext.htmlObj.find("img"), function(){
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
