define(['text!resouceManageTpl/qyzygl/qyxx/qyyhzhxx.html'], function(qyyhzhxxTpl){
	return{
		showPage : function(obj){
			$('#infobox').html(_.template(qyyhzhxxTpl,obj));	
		}	
	}
});