define(['text!infoManageTpl/zyml/xfzzyyl_ck.html'], function(xfzzyyl_ckTpl){
	return {
		showPage : function(obj){
			var cardType = obj.authInfo.cerType;
			$('#infobox').html(_.template(xfzzyyl_ckTpl,{obj:obj}));
			if(null != cardType && '' != cardType){
				$("#card_"+cardType).show();
			}else{
				$("#card_1").show();
			}
			
			$('#back_xfzzyyl').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#xfzzyyl'),'#ckqyxxxx, #ck');
			});
			if(null != obj.authInfo.cerPica && '' != obj.authInfo.cerPica){
				comm.initPicPreview("#pic_1_1", obj.authInfo.cerPica, "");
				comm.initPicPreview("#pic_2_1", obj.authInfo.cerPica, "");
				comm.initPicPreview("#pic_3_1", obj.authInfo.cerPica, "");
			}
			if(null != obj.authInfo.cerPicb && '' != obj.authInfo.cerPicb){
				comm.initPicPreview("#pic_1_2", obj.authInfo.cerPicb, "");
			}
			if(null != obj.authInfo.cerPich && '' != obj.authInfo.cerPich){
				comm.initPicPreview("#pic_1_3", obj.authInfo.cerPich, "");
				comm.initPicPreview("#pic_2_3", obj.authInfo.cerPich, "");
				comm.initPicPreview("#pic_3_3", obj.authInfo.cerPich, "");
			}
		}	
	}	
});