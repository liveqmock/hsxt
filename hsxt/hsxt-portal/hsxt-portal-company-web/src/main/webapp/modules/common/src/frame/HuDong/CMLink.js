define(["commonSrc/frame/HuDong/marketXmpp",
		"commonSrc/frame/HuDong/consultationXmpp"],function(marketXmpp,consultationXmpp){   

	
	var link = {
			 leftClickLink : function() {
					$("#huDongMain2").on("click",".hd_leftMenu>ul>li>p",function(){
							if($(this).parent().attr("typebool") == "market"){
								marketXmpp.leftLick(this);
							}else{
								consultationXmpp.leftLick(this);
						}
					})
			 }
	}
	return link;
})