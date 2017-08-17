define(['text!infoManageTpl/zyzhgl/sub_tab_p.html',
		'infoManageSrc/zyzhgl/xfzjbxx',
		'infoManageSrc/zyzhgl/ywczxk',
		'infoManageSrc/zyzhgl/xhzxxxx_sj',
		'infoManageSrc/zyzhgl/xhzxxxx_yx',
		'infoManageSrc/zyzhgl/xhzxxxx_yhk',
		'infoManageSrc/zyzhgl/xhzxxxx_kjk_p',
 
		],function(tab, xfzjbxx, ywczxk,xhzxxxx_sj, xhzxxxx_yx ,xhzxxxx_yhk , xhzxxxx_kjk ){
	return {	 
		showPage : function(num,obj){		
			$('#busibox2').html(_.template(tab));
			comm.goDetaiPage("busibox","busibox2");
			
			/*消费者基本信息*/
			$('#xfzjbxx').click(function(e) {
				obj['cerStr'] = comm.getNameByEnumId(obj.authInfo.cerType,comm.lang("infoManage").realNameCreType);
				obj['sexStr'] = comm.getNameByEnumId(obj.authInfo.sex,comm.lang("infoManage").personSex);
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
				obj['isAuthMobileStr']= isAuthMobileStr;
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
                xhzxxxx_yx.showPage('xfz',obj);
				comm.liActive($('#ywczxk_yx'));
            }.bind(this));
			
			$('#ywczxk_yhk').click(function(e) {
                xhzxxxx_yhk.showPage(num,obj.baseInfo.perCustId);
				comm.liActive($('#ywczxk_yhk'));
            }.bind(this));
            
            $('#ywczxk_kjk').click(function(e) {
                xhzxxxx_kjk.showPage(obj);
				comm.liActive($('#ywczxk_kjk'));
            }.bind(this));
			 
			
			
			/*业务操作许可*/
			$('#ywczxk_p').click(function(e) {
                ywczxk.showPage(num,obj.baseInfo.perCustId);
				comm.liActive($('#ywczxk'));
            }.bind(this));
            
            
            
			 //返回列表
		 	$('#ent_back').click(function(){
		 		 $('#busibox').removeClass('none');
		 		 $('#ent_list').removeClass('none');
		 		 $('#ent_detail').addClass('none') ;
		 	});
            

		}

	}
}); 

