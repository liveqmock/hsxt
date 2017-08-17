define(['text!infoManageTpl/zyzhgl/xfzzy_ck.html'], function(xfzzy_ckTpl){
	return {
		showPage : function(obj){
			$('#consumerBox').html(_.template(xfzzy_ckTpl,{obj:obj}));
			if('3' == obj.authInfo.cerType){
				$('#card_3').show();
				$('#card_2').hide();
				$('#card_1').hide();
			}else if('2' == obj.authInfo.cerType){
				$('#card_3').hide();
				$('#card_2').show();
				$('#card_1').hide();
			}else if('1' == obj.authInfo.cerType){
				$('#card_3').hide();
				$('#card_2').hide();
				$('#card_1').show();
			}else{
				$('#card_3').show();
				$('#card_2').hide();
				$('#card_1').hide();
			}
			$('#back_xfzzy').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#xfzzy'),'#ckqyxxxx, #ck');
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
