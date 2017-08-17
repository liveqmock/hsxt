define(['text!infoManageTpl/glqyxxgl/sub_tab_2.html',
		'infoManageSrc/glqyxxgl/qyxtzcxx',
		'infoManageSrc/glqyxxgl/qygsdjxx',
		'infoManageSrc/glqyxxgl/qylxxx',
		'infoManageSrc/glqyxxgl/qyyhzhxx'
		], function(tab, qyxtzcxx, qygsdjxx, qylxxx, qyyhzhxx){
	return {
		showPage : function(obj){
			$("#glqyxxwhTpl").addClass("none");
			$('#glqyxxwh_operation').removeClass("none");
			$('#glqyxxwh_operation').html(_.template(tab));	
			
			/*企业系统注册信息*/
			$('#qyxtzcxx').click(function(e) {
                qyxtzcxx.showPage(obj);
				comm.liActive($('#qyxtzcxx'));
            }.bind(this)).click(); 
			
			/*企业工商登记信息*/
			$('#qygsdjxx').click(function(e) {
                qygsdjxx.showPage(obj);
				comm.liActive($('#qygsdjxx'));
            }.bind(this));
			
			/*企业联系信息*/
			$('#qylxxx').click(function(e) {
                qylxxx.showPage(obj);
				comm.liActive($('#qylxxx'));
            }.bind(this)); 
			
			/*企业银行账户信息*/
			$('#qyyhzhxx').click(function(e) {
                qyyhzhxx.showPage(obj);
				comm.liActive($('#qyyhzhxx'));
            }.bind(this)); 
		}	
	}	
});