define(['text!infoManageTpl/zyml/lxxx.html',
        'infoManageDat/infoManage',
		'infoManageLan'], function(lxxxTpl, dataModoule){
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
			
			$('#back_tgqyzyyl').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#tgqyzyyl'),'#ckqyxxxx, #ck');
			});
			$('#back_cyqyzyyl').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#cyqyzyyl'),'#ckqyxxxx, #ck');
			});
			$('#back_fwgszyyl').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#fwgszyyl'),'#ckqyxxxx, #ck');
			});
			//联系人授权委托书
			if(data && data.contactProxy){
				comm.initPicPreview("#aimg", data.contactProxy, "");
				$("#aimg").attr("src", comm.getFsServerUrl(data.contactProxy));
			}
			
			
			 
			var helpAgreement = '';
			if(data){
				var custType = data.custType;
				var startResType = data.startResType;
				if(3 == custType && 2 == startResType){
					helpAgreement = data.helpAgreement;
					if(null != helpAgreement && '' != helpAgreement){
						$("#helpAgreement_img").attr("src", comm.getFsServerUrl(helpAgreement));
					}
				}
			}
			
			$("#helpAgreement_img").click(function(){//托管企业（创业资源）创业帮扶协议图片预览
		        comm.initTmpPicPreview("#helpAgreement_span",comm.getFsServerUrl(helpAgreement),"联系人授权委托书预览");
		    });
			
			//邮箱验证
			$('#btn_lxxx_yz').click(function(){
				var params = {};
				var email = $("#lxxx_contactEmail").text();
				if(email == null || email == ""){
					//comm.alert('邮箱地址为空，请修改邮箱地址。');
					return;
				}
				params.email = email;
				var hideEmail = email.substr(0, 2) + "****" + email.substr(email.indexOf("@"), email.length);
				params.userName = comm.getSysCookie('custName');
				params.entResNo = comm.getSysCookie('pointNo');
				dataModoule.validEmail(params,function(){
					comm.alert('系统已发送验证邮件到'+hideEmail+'，请及时登录邮箱完成绑定。');
				});
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