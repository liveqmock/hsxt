define([], function(){
	return {
		/**
		 * 收款用途
		 * @param use 收款用途键
		 */
		collectUse:function(use){
			var cuArray=new Array();
			if(use){
				//转换数组
				var cu=use.split(',');
				
				//循环转换成中文数组
				for(var i=0;i<cu.length;i++) {
					cuArray.push(comm.lang("debit").payUse[cu[i]]);
				}
			}
			
			return cuArray;
		}
	}	
});
