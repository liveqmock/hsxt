define(['text!debitTpl/lzgl/tab.html',
		'debitSrc/lzgl/lzgl',
		'debitSrc/lzgl/yjjfyfk',
		'debitSrc/lzgl/jnxtsynf',
		'debitSrc/lzgl/dhhsb'
		], function(tab, lzgl, yjjfyfk, jnxtsynf, dhhsb){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//临账关联
			$('#lzgl').click(function(){
				lzgl.showPage();
				comm.liActive($('#lzgl'), '#gl');
				this.hideBt($('#qrglDiv'));
			}.bind(this));
			
			$('#yjjfyfk').click(function(){
				yjjfyfk.showPage();
				comm.liActive($('#yjjfyfk'), '#gl');
				this.hideBt($('#qrglDiv'));
			}.bind(this));
			
			$('#jnxtsynf').click(function(){
				jnxtsynf.showPage();
				comm.liActive($('#jnxtsynf'), '#gl');
				this.hideBt($('#qrglDiv'));
			}.bind(this));
			
			$('#dhhsb').click(function(){
				dhhsb.showPage();
				comm.liActive($('#dhhsb'), '#gl');
				this.hideBt($('#qrglDiv'));
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050405000000");
			
			//遍历临账关联的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //临账关联
				 if(match[i].permId =='050405010000')
				 {
					 $('#lzgl').show();
					 $('#lzgl').click();
					 
				 }
			}
			
		},
		hideBt : function(divObj){
			divObj.addClass('none');	
		}
	}	
});