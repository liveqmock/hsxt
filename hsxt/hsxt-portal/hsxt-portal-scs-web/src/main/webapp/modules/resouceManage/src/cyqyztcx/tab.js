define(['text!resouceManageTpl/cyqyztcx/tab.html',
			'resouceManageSrc/cyqyztcx/cyqyztcx' ,
			'resouceManageSrc/cyqyztcx/cyqyzg_zx',
			'resouceManageSrc/qyzygl/ckxq/xtdjxx',
			'resouceManageSrc/qyzygl/ckxq/gsdjxx',
			'resouceManageSrc/qyzygl/ckxq/lxxx',
			'resouceManageSrc/qyzygl/ckxq/yhzhxx',
			'resouceManageSrc/qyzygl/ckxq/qysczl'
			],function(tab,cyqyztcx, cyqyzg_zx, qyxtzcxx, qygsdjxx, qylxxx, qyyhzhxx, qysczl){
	return {	 
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab));
			// 
			$('#cyqyztcx_cyqyztcx').click(function(e) {				 		 
				$('#busibox').removeClass('none');
				$("#cyqyztcx_zgzx").hide();
                cyqyztcx.showPage();
				comm.liActive($('#cyqyztcx_cyqyztcx'));
		 		$('#cx_content_detail').addClass('none'); 
            }.bind(this));
			
			$('#cyqyztcx_zgzx').click(function(e) {
				$('#busibox').removeClass('none');
				$("#cyqyztcx_zgzx").show();
                cyqyzg_zx.showPage();
				comm.liActive_add($('#cyqyztcx_zgzx'));
		 		$('#cx_content_detail').addClass('none'); 
            }.bind(this));			
	 
			$('#ckxq_qyxtzcxx').click(function(e) {		 
                qyxtzcxx.showPage();
				comm.liActive($('#ckxq_qyxtzcxx'));
            }.bind(this));  
			
			$('#ckxq_qygsdjxx').click(function(e) {		 
                qygsdjxx.showPage();
				comm.liActive($('#ckxq_qygsdjxx'));
            }.bind(this));  
            
            
            $('#ckxq_qylxxx').click(function(e) {		 
                qylxxx.showPage();
				comm.liActive($('#ckxq_qylxxx'));
            }.bind(this));  
            
            $('#ckxq_qyyhzhxx').click(function(e) {		 
                qyyhzhxx.showPage();
				comm.liActive($('#ckxq_qyyhzhxx'));
            }.bind(this));  
            
			$('#ckxq_qysczl').click(function(e) {		 
                qysczl.showPage();
				comm.liActive($('#ckxq_qysczl'));
            }.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030403000000");
			
			//遍历意向客户查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //意向客户查询
				 if(match[i].permId =='030403010000')
				 {
					 $('#cyqyztcx_cyqyztcx').show();
					 $('#cyqyztcx_cyqyztcx').click();
					 
				 }
			}
		},
		liActive: function(obj){
			obj.css('display','');
			obj.find('a').addClass('active');
			if (obj.attr('id')=='cyqyztcx_cyqyztcx'){
				obj.siblings().css('display','none');
			} 
			obj.siblings().find('a').removeClass('active');
		} 
	}
}); 
