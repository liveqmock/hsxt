define(['text!infoManageTpl/zyzhgl/sub_tab.html',
		'infoManageSrc/zyzhgl/xtdjxx',
		'infoManageSrc/zyzhgl/gsdjxx',
		'infoManageSrc/zyzhgl/lxxx',
		'infoManageSrc/zyzhgl/yhzhxx',
		'infoManageSrc/zyzhgl/ywczxk',
		'infoManageSrc/zyzhgl/xhzxxxx_kjk'
		],function(tab, xtdjxx, gsdjxx, lxxx, yhzhxx, ywczxk,xhzxxxx_kjk){
	return {	 
		showPage : function(num, record, custID){		
			$('#ent_list').html(_.template(tab));
			
			/*系统登记信息*/
			$('#xtdjxx').click(function(e) {
                xtdjxx.showPage(num, record, custID);
				comm.liActive($('#xtdjxx'));
            }.bind(this)).click();
			
			/*工商登记信息*/
			$('#gsdjxx').click(function(e) {
                gsdjxx.showPage(num, custID);
				comm.liActive($('#gsdjxx'));
            }.bind(this)); 
			
			/*联系信息*/
			$('#lxxx').click(function(e) {
                lxxx.showPage(num, custID);
				comm.liActive($('#lxxx'));
            }.bind(this)); 
			
			/*银行账户信息*/
			$('#yhzhxx').click(function(e) {
                yhzhxx.showPage(num, custID);
				comm.liActive($('#yhzhxx'));
            }.bind(this)); 
            
            $('#zyzhgl_kjk').click(function(e) {
                xhzxxxx_kjk.showPage(num, custID);
				comm.liActive($('#zyzhgl_kjk'));
            }.bind(this)); 
			
			/*业务操作许可*/
			$('#ywczxk').click(function(e) {
                ywczxk.showPage(num, custID);
				comm.liActive($('#ywczxk'));
            }.bind(this));
			
			 //返回列表
		 	$('#ent_back').click(function(){
		 		 $('#busibox').removeClass('none');
		 		 $('#ent_list').addClass('none');
		 		 $("#ckqyxxxx").addClass("tabNone");
		 		 comm.liActive($("#"+num+"zy"));
		 		 //$('#ent_detail').addClass('none') ;
		 	});

		}

	}
}); 

