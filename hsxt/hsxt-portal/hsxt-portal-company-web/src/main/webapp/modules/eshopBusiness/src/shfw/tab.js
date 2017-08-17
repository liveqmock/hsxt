define(['text!eshopBusinessTpl/shfw/tab.html',
		'eshopBusinessSrc/shfw/shfwlb'
		], function(tpl, shfwlb){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//售后服务列表
			$('#shfw_shfwlb').click(function(e){
				if(shfwlb.loadFlag == null){
					shfwlb.showPage();
				}
				shfwlb.loadFlag = null;
				$('#shfw_tabNav').addClass('none');
				$('#shfwlb_content1').removeClass('none');
				$('#shfwlb_content2').addClass('none').html('');
				$('#shfwlb_content3').addClass('none').html('');
				$(this).attr('class', 'tabNone');
				$('#shfw_shxq').attr('class', 'bgImgNone');
				$('#shfw_clgc').removeAttr('class').find('a').removeAttr('class');
			}).click();
			
			//菜单-售后详情
			$('#shfw_shxq').click(function(e){
				//菜单区切换
				comm.liActive($(this));
				$('#shfw_tabNav').removeClass('none');
				//内容区切换
				$('#shfwlb_content1').addClass('none');
				$('#shfwlb_content2').removeClass('none');
				$('#shfwlb_content3').addClass('none');
			});
			
			//菜单-处理过程
			$('#shfw_clgc').click(function(e){
				//菜单区切换
				comm.liActive($(this));
				$('#shfw_tabNav').removeClass('none');
				//内容区切换
				$('#shfwlb_content1').addClass('none');
				$('#shfwlb_content2').addClass('none');
				$('#shfwlb_content3').removeClass('none');
			});
		}
	}
});