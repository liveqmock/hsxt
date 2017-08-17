define(['text!infoManageTpl/zyml/sub_tab_p.html',
		'infoManageSrc/zyml/xfzjbxx',
		'infoManageSrc/zyml/ywczxk',
		'infoManageSrc/zyml/xhzxxxx_sj',
		'infoManageSrc/zyml/xhzxxxx_yx',
		'infoManageSrc/zyml/xhzxxxx_yhk',
		'infoManageSrc/zyml/xhzxxxx_kjk_p'
		],function(tab, xfzjbxx, ywczxk,xhzxxxx_sj, xhzxxxx_yx ,xhzxxxx_yhk , xhzxxxx_kjk){
	return {	 
		showPage : function(num, obj){
			
			$('#busibox2').html(_.template(tab));
			comm.goDetaiPage("busibox","busibox2");
			
			/*消费者基本信息*/
			$('#xfzjbxx').click(function(e) {
				obj['cerStr'] = comm.getNameByEnumId(obj.authInfo.cerType,comm.lang("infoManage").realNameCreType);
				obj['sexStr'] = comm.getNameByEnumId(obj.authInfo.sex,comm.lang("infoManage").personSex);
				if(obj.authInfo.cerPica!=''){
					obj['cerPicaStr'] = comm.domainList.fsServerUrl+obj.authInfo.cerPica;
				}
				if(obj.authInfo.cerPicb!=''){
					obj['cerPicbStr'] = comm.domainList.fsServerUrl+obj.authInfo.cerPicb;
				}
				if(obj.authInfo.cerPich!=''){
					obj['cerPichStr'] = comm.domainList.fsServerUrl+obj.authInfo.cerPich;
				}
                xfzjbxx.showPage(obj);
				comm.liActive($('#xfzjbxx'));
            }.bind(this)).click();
			
			
			$('#ywczxk_sj').click(function(e) {
				var mobile = obj.baseInfo.mobile;
				obj['mobileStr'] = mobile;
				var isAuthMobileStr = '未认证';
				if(mobile==null||mobile==''){
					isAuthMobileStr ='未设置';
				}
				if(obj.baseInfo.isAuthMobile==1){
					isAuthMobileStr = '已认证';
				}
				obj['isAuthMobileStr']=isAuthMobileStr;
                xhzxxxx_sj.showPage(obj);
				comm.liActive($('#ywczxk_sj'));
            }.bind(this));			
			
			$('#ywczxk_yx').click(function(e) {
				var email = obj.baseInfo.email;
				obj['emailStr'] = email;
				var isAuthEmailStr = '未认证';
				if(email==null||email==''){
					isAuthEmailStr ='未设置';
				}
				if(obj.baseInfo.isAuthEmail==1){
					isAuthEmailStr = '已认证';
				}
				obj['isAuthEmailStr']=isAuthEmailStr;
                xhzxxxx_yx.showPage(obj);
				comm.liActive($('#ywczxk_yx'));
            }.bind(this));
			
			$('#ywczxk_yhk').click(function(e) {
                xhzxxxx_yhk.showPage(obj);
				comm.liActive($('#ywczxk_yhk'));
            }.bind(this));
            
            $('#ywczxk_kjk').click(function(e) {
                xhzxxxx_kjk.showPage(obj);
				comm.liActive($('#ywczxk_kjk'));
            }.bind(this));
			 
			/*业务操作许可*/
			$('#ywczxk').click(function(e) {
                ywczxk.showPage(num,obj.baseInfo.perCustId);
				comm.liActive($('#ywczxk'));
            }.bind(this));
            
		
		}

	}
}); 

