define(['text!resouceManageTpl/qyzygl/qyxx/qyxtzcxx.html'], function(qyxtzcxxTpl){
	return{
		showPage : function(obj){
			//加载地区缓存
			cacheUtil.findProvCity();
			$('#infobox').html(_.template(qyxtzcxxTpl,obj));	
		}	
	}
});