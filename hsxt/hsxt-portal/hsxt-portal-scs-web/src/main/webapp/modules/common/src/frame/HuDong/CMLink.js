define(["commSrc/frame/HuDong/consultationXmpp"],function(consultationXmpp){   

	
	var link = {
			 leftClickLink : function() {
					$("#huDongMain2").on("click",".hd_leftMenu>ul>li>p",function(){
							if($(this).parent().attr("typebool") == "market"){
							}else{
								consultationXmpp.leftLick(this);
						}
					})
			 }
	}
	return link;
})