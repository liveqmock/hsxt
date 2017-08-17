define(['text!infoManageTpl/zyzhgl/lxxx.html',
        'infoManageDat/infoManage',
		'infoManageLan'], function(lxxxTpl){
	return {
		showPage : function(num, custID){
			this.initData(custID, num);
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data, num){
			data.num = num;
			$('#infobox').html(_.template(lxxxTpl, data));
			$('#back_tgqyzy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#tgqyzy'),'#ckqyxxxx, #ck');
			});
			$('#back_cyqyzy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#cyqyzy'),'#ckqyxxxx, #ck');
			});
			$('#back_fwgszy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#fwgszy'),'#ckqyxxxx, #ck');
				
			});
			//联系人授权委托书
			if(data && data.contactProxy){
				comm.initPicPreview("#aimg", data.contactProxy, "");
				$("#aimg").attr("src", comm.getFsServerUrl(data.contactProxy));
			}
			
			if(data){
				var custType = data.custType;
				var startResType = data.startResType;
				if(3 == custType && 2 == startResType){
					var helpAgreement = data.helpAgreement;
					if(comm.isNotEmpty(helpAgreement)){
						$("#helpAgreement_img").attr("src", comm.getFsServerUrl(helpAgreement));
					}
				}
			}
			
			$("#helpAgreement_img").click(function(){//托管企业（创业资源）创业帮扶协议图片预览
		        comm.initTmpPicPreview("#helpAgreement_span",comm.getFsServerUrl(helpAgreement),"联系人授权委托书预览");
		    });
			
		},
		/**
		 * 初始化数据
		 */
		initData : function(custID, num){
			var self = this;
			var entAllInfo = comm.getCache("infoManage", "entAllInfo");
			if(entAllInfo == null){
				dataModoule.resourceFindEntAllInfo({custID:custID}, function(res){
					comm.setCache("infoManage", "entAllInfo", res.data);
					self.initForm(res.data.entInfo, num);
				});
			}else{
				self.initForm(entAllInfo.entInfo, num);
			}
		}
	}	
});