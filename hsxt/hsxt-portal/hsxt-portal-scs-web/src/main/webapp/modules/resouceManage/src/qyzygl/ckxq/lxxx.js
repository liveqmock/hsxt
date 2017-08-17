define(['text!resouceManageTpl/qyzygl/ckxq/lxxx.html',
        'resouceManageDat/resouceManage',
		'resouceManageLan'],function(lxxxTpl, dataModoule){
	return {
		showPage : function(){
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#ckxq_view').html(_.template(lxxxTpl, data));
			$('#ModifyBt_qiyelianxi').click(function(){
				$('#qyxx_lxxxxg').click();
			});
			//联系人授权委托书
			var helpAgreement = '';
			if(data && data.contactProxy){
				comm.initPicPreview("#img", data.contactProxy, "");
				$("#img").attr("src", comm.getFsServerUrl(data.contactProxy));
				var custType = data.custType;
 				var startResType = data.startResType;
 				if(3 == custType && 2 == startResType){
 					$("#helpAgreement_img").attr("src", "resources/img/noImg.gif");
 					helpAgreement = data.helpAgreement;
 					if(null != helpAgreement && '' != helpAgreement){
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
		initData : function(){
			var self = this;
			var entAllInfo = comm.getCache("resouceManage", "entAllInfo");
			if(entAllInfo == null){
				dataModoule.findCompanyAllInfo({companyCustId:$("#resEntCustId").val()}, function(res){
					comm.setCache("resouceManage", "entAllInfo", res.data);
					self.initForm(res.data);
				});
			}else{
				self.initForm(entAllInfo);
			}
		}
	}
}); 

