define(['text!coDeclarationTpl/csyw/sub_tab.html',
		'coDeclarationSrc/csyw/sbxx',
		'coDeclarationSrc/csyw/qyxtzcxx',
		'coDeclarationSrc/csyw/qygsdjxx',
		'coDeclarationSrc/csyw/qylxxx',
		'coDeclarationSrc/csyw/qyyhzhxx',
		'coDeclarationSrc/csyw/qysczl',
		'coDeclarationSrc/csyw/blztxx'
		],function(tab, sbxx, qyxtzcxx, qygsdjxx, qylxxx, qyyhzhxx, qysczl, blztxx){
	return {	 
		showPage : function(){		
			$('#busibox').html(_.template(tab)) ;
			
			var num =$('.tabList > li > a[class="active"]').attr('data-num') ;
			//console.log(num);

			/*申报信息*/
			$('#sbxx').click(function(e) {
                sbxx.showPage(num);
				comm.liActive($('#sbxx'));
            }.bind(this)).click(); 
			
			/*企业系统注册信息*/
			$('#qyxtzcxx').click(function(e) {
                qyxtzcxx.showPage(num);
				comm.liActive($('#qyxtzcxx'));
            }.bind(this));
			
			/*企业工商登记信息*/
			$('#qygsdjxx').click(function(e) {
                qygsdjxx.showPage(num);
				comm.liActive($('#qygsdjxx'));
            }.bind(this)); 
			
			/*企业联系信息*/
			$('#qylxxx').click(function(e) {
                qylxxx.showPage(num);
				comm.liActive($('#qylxxx'));
            }.bind(this)); 
			
			/*企业银行账户信息*/
			$('#qyyhzhxx').click(function(e) {
                qyyhzhxx.showPage(num);
				comm.liActive($('#qyyhzhxx'));
            }.bind(this)); 
			
			/*企业上传资料*/
			$('#qysczl').click(function(e) {
                qysczl.showPage(num);
				comm.liActive($('#qysczl'));
            }.bind(this)); 
			
			/*办理状态信息*/
			$('#blztxx').click(function(e) {
                blztxx.showPage(num);
				comm.liActive($('#blztxx'));
            }.bind(this));  
			
			/*审核提交弹出框*/
			$('#shtj_btn').click(function(){
				$('#shtj_content').dialog({
					title:'审核确认',
					modal:true,
					width:'400',
					height:'220',
					buttons:{ 
						"确定":function(){
							$('#shtj_content').dialog('destroy');
						},
						"取消":function(){
							 $('#shtj_content').dialog('destroy');
						}
					}
			
				  });
			});
			/*end*/

		}

	}
}); 

