define(['text!eshopInfoTpl/jbxx/jbxx.html',
		'text!eshopInfoTpl/jbxx/pjxx.html'
		], function(tpl, pjxxTpl){
	return {
		showPage : function(){
			var self = this;
			
			//从后台获取数据
			var response = {
				zt : '',
				zhpf : '',
				mspf : '',
				fwpf : '',
				sxpf : '',
				qyglh : '0100101000 (二级域名：0100101000.hsxt.xxxxxx)',
				dq1 : '中国',
				dq2 : '广东',
				dq3 : '深圳',
				gsmc : '深圳大苹果科技有限公司',
				scmc : '深圳苹果',
				sctb : '',
				sclxr : '李三',
				lxdh : '0755-11245121',
				lxyx : 'adsf@163.com',
				jyfw : '经营范围',
				scms : '商城描述'
			};
			//头部评价信息
			$('#jbxx_pjxx').html(_.template(pjxxTpl, response));
			//主体页面
			$('#busibox').html(_.template(tpl, response));
			
			//商城图片展开
			$("#jbxx_btn_more").mouseover(function(){
				$(this).hide();	
				$("#jbxx_imgBox").addClass("imgBoxBig");
				$("#jbxx_imgMask").removeClass("imgMask_style");
			});
			//商城图片折叠隐藏
			$("#jbxx_imgBox").mouseleave(function(){
				$(this).removeClass("imgBoxBig");
				$("#jbxx_imgMask").addClass("imgMask_style");
				$("#jbxx_btn_more").show();
			});
			//显示删除图片按钮
			$('#jbxx_imgLists > li:not(".noImgLi")').hover(function(){
				$(this).children(".delImg").show();
			}, function(){
				$(this).children(".delImg").hide();
			});
			//删除图片
			$("#jbxx_imgBox .delImg").on('click', function(){
				$(this).parent("li").remove();
			});
			
			//开通商城按钮
			$('#jbxx_btn_ktsc').click(function(){
				//此处代码用于展示页面数据，请删除
					var response = {
						zt : 'W',
						zhpf : '4.9',
						mspf : '4.8',
						fwpf : '4.7',
						sxpf : '4.0',
						qyglh : '0100101000 (二级域名：0100101000.hsxt.xxxxxx)',
						dq1 : '中国',
						dq2 : '广东',
						dq3 : '深圳',
						gsmc : '深圳大苹果科技有限公司',
						scmc : '深圳苹果',
						sctb : 'resources/img/sj_01.gif',
						sclxr : '李三',
						lxdh : '0755-11245121',
						lxyx : 'adsf@163.com',
						jyfw : '经营范围',
						scms : '商城描述'
					};
					//头部评价信息
					$('#jbxx_pjxx').html(_.template(pjxxTpl, response));
					//主体页面
					$('#busibox').html(_.template(tpl, response));
				//此处代码用于展示页面数据，请删除
				
				//提交后台数据后，重新加载页面，根据状态显示不同页面
				//$('#wsscxx_jbxx').trigger('click');
			});
			//修改按钮
			$('#jbxx_btn_xg').click(function(){
				
			});
			//申请暂时按钮
			$('#jbxx_btn_sqzs').click(function(){
				
			});
			//申请暂停按钮
			$('#jbxx_btn_sqzt').click(function(){
				
			});
		}
	};
});