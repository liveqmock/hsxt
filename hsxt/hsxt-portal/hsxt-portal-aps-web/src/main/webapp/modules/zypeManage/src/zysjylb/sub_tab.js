define(['text!zypeManageTpl/zysjylb/sub_tab.html',
		'zypeManageSrc/zysjylb/zysjylb_01',
        "zypeManageDat/zypeManage"
		], function(tab, zysjylb_01,zypeManage){
	return {
		showPage : function(){
			//获取地区平台下的管理公司
			zypeManage.getEntResList(null,function(res){
				$('#busibox').html(_.template(tab,res.data));
				
				$('#uEnt li').bind("click",function(){
					comm.liActive($(this), '#xq'); //给点击的元素添加样式
					zysjylb_01.showPage();	//加载html页
				}).eq(0).click();	
			});
			
		}	
	}	
});