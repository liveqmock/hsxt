define(['text!companyInfoTpl/lxxx/lxxx.html',
        'companyInfoDat/gsdjxx/gsdjxx',
        'companyInfoDat/lxxx/lxxx',
		'companyInfoLan'],function(lxxxTpl, dataModoule, lxxx){
	return {
		showPage : function(){
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#busibox').html(_.template(lxxxTpl, data));
			$('#btn_lxxx_yz').click(function(){
				var params = {};
				var email = $("#email").val().trim();
				if(email == null || email == ""){
					//comm.alert('邮箱地址为空，请修改邮箱地址。');
					return;
				}
				params.email = email;
				var hideEmail = email.substr(0, 2) + "****" + email.substr(email.indexOf("@"), email.length);
				params.userName = $.cookie('custName');
				params.entResNo = $.cookie('pointNo');
				lxxx.validEmail(params,function(){
					comm.alert('系统已发送验证邮件到'+hideEmail+'，请及时登录邮箱完成绑定。');
				});
			});
			$('#ModifyBt_qiyelianxi').click(function(){
				$('#qyxx_lxxxxg').click();
			});
			//联系人授权委托书
			if(data && data.contactProxy){
				comm.initPicPreview("#img", data.contactProxy, "");
				$("#img").attr("src", comm.getFsServerUrl(data.contactProxy));
			}
			
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			dataModoule.findEntAllInfo(null, function(res){
				comm.setCache("companyInfo", "entAllInfo", res.data);
				self.initForm(res.data);
			});
		}
	}
}); 

