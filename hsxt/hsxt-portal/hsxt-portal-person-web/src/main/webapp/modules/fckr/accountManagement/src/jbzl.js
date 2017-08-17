define(["text!fckr_accountManagementTpl/jbzl.html",
        "fckr_accountManagementDat/accountManagement"], function (tpl,accountManagementDataModel) {
	var jbzl = {
		show : function(){
			//积分明细查询表格结果
			var searchTable = null;
			//加载页面
			$("#myhs_zhgl_box").html(tpl);
			
			
			//获取平台基本信息 
			cacheUtil.findCacheSystemInfo(function(systemInfo){
				//界面数据加载
				$("#countryCodeHid").val(systemInfo.countryNo);				//保存当前国家
				
				//根据国家获取下属省份
				cacheUtil.findCacheProvinceByParent(systemInfo.countryNo,function(provinceList){
					//加载省份信息
					comm.initOption('#province', provinceList , 'provinceName','provinceNo',false);
					
					//加载城市信息
					cacheUtil.findCacheCityByParent(systemInfo.countryNo, $('#province').val(), function(cityArray){
						//加载省份信息
						comm.initOption('#city', cityArray , 'cityName','cityNo',false);
					});
				});
			});
			
			accountManagementDataModel.findNetworkInfoByCustId(null, function(response){
				//获取数据
				var networkInfo = response.data;
				$("#xm_input").val(networkInfo.name);					//真实姓名
				$("#nickName").val(networkInfo.nickname);			//昵称
				comm.initPicPreview("#imp_headPicLoadImage");
                $("#imp_headPicLoadImage").attr("src",comm.getFsServerUrl(networkInfo.headShot));
				
				//图片绑定预览
				if(comm.getFsServerUrl(networkInfo.headShot)){
					comm.initTmpPicPreview('#imp_headPicLoadImage', $('#imp_headPicLoadImage').children().first().attr('src'));
				}
				
				jbzl.initTmpPicPreview();
				
                $("input[type=radio][name=sex][value="+networkInfo.sex+"]").attr("checked",true);
				$("#age").val(networkInfo.age);						//年龄
				$("#occupation").val(networkInfo.job);				//职业
				$("#school").val(networkInfo.graduateSchool);		//毕业院校
				$("#bloodType").val(networkInfo.blood);				//血型
				$("#hobbies").val(networkInfo.hobby);				//爱好
				$("#qqNo").val(networkInfo.qqNo);					//QQ号
				$("#weixinNo").val(networkInfo.weixin);				//微信号
				if(comm.isNotEmpty(networkInfo.countryNo))
				{
					$("#countryCodeHid").val(networkInfo.countryNo);	//国家
				}else{
					networkInfo.countryNo = $("#countryCodeHid").val();
				}
				
				cacheUtil.findCacheProvinceByParent(networkInfo.countryNo,function(provinceList){
					//加载省份信息
					$("#province").empty();
					comm.initOption('#province', provinceList , 'provinceName','provinceNo',false);
					$("#province").val(networkInfo.provinceNo);			//省
				});
				
				
				//加载城市信息
				cacheUtil.findCacheCityByParent(networkInfo.countryNo, networkInfo.provinceNo, function(cityArray){
					//加载省份信息
					$("#city").empty();
					comm.initOption('#city', cityArray , 'cityName','cityNo',false);
					$("#city").val(networkInfo.cityNo);					//市
				});
				
				//$("#area").val(networkInfo.areaNo);				//区
				$("#zipCode").val(networkInfo.postcode);			//邮编
				$("#address").val(networkInfo.homeAddr);			//地址

			});
			
			$("#province").change(function(){
				var countryCodeHid = $("#countryCodeHid").val();
				var provinceCode = $('#province').val();
				$("#city").empty();
				//加载城市信息
				cacheUtil.findCacheCityByParent(countryCodeHid, provinceCode, function(cityArray){
					//加载省份信息
					comm.initOption('#city', cityArray , 'cityName','cityNo',false);
				});
			}); 
			
			$('#bc_btn').click(function(){
				var valid = jbzl.validateData();
				if (!valid.form()) {
					return;
				}
				//封装传递的json参数
				var networkInfo={};
				
				networkInfo["zName"]=$("#xm_input").val();				//真实姓名
				networkInfo["nickName"]=$("#nickName").val();		//昵称
				networkInfo["headShot"]=$("#headPic").val();		//头像
				networkInfo["sex"]=$("input[name='sex']:checked").val();	//性别
				networkInfo["age"]=$("#age").val();					//年龄
				networkInfo["job"]=$("#occupation").val();			//职业
				networkInfo["graduateSchool"]=$("#school").val();	//毕业院校
				networkInfo["blood"]=$("#bloodType").val();			//血型
				networkInfo["hobby"]=$("#hobbies").val();			//爱好
				networkInfo["qqNo"]=$("#qqNo").val();				//QQ号
				networkInfo["weixin"]=$("#weixinNo").val();			//微信号
				networkInfo["countryNo"]=$("#countryCodeHid").val();		//国家
				networkInfo["provinceNo"]=$("#province").val();				//省
				networkInfo["cityNo"]=$("#city").val();		//市
//				networkInfo["city"]=$("#city").val();				//区
				networkInfo["postcode"]=$("#zipCode").val();			//邮编
				networkInfo["homeAddr"]=$("#address").val();			//地址
				
				var imp_headPicBlogImage = $("#imp_headPicBlogImage").val();
				if(imp_headPicBlogImage != ""){
					var ids = ["imp_headPicBlogImage"];
					
					comm.uploadFile(null, ids, function (response) {
						if(response.imp_headPicBlogImage){
							networkInfo["headShot"]=response.imp_headPicBlogImage;		//头像
							comm.i_confirm('确认保存基本资料', function () {
								//保存网络信息
								accountManagementDataModel.updateNetworkInfo(networkInfo, function(cityArray){
									//电商需要使用这个地址
									comm.setCookie("hs_headPic",response.imp_headPicBlogImage);
									comm.setCookie("headPic",response.imp_headPicBlogImage);
									
									comm.i_alert("保存数据成功");
									jbzl.initTmpPicPreview();
								});
							})
						}
						
					}, function(){
						//失败回掉函数初始化图片加载
						jbzl.initTmpPicPreview();
					});
				}else{
					comm.i_confirm('确认保存基本资料', function () {
						//保存网络信息
						accountManagementDataModel.updateNetworkInfo(networkInfo, function(cityArray){
							comm.i_alert("保存数据成功");
							jbzl.initTmpPicPreview();
						});
					})
				}
				
				
//				networkInfo["headPic"]=$("#img").attr("data-url");
//				networkInfo["headBigPic"]=$("#img").attr("data-srcUrl");
				//确认提交询问框
				
				
				
				
			});
		},
		/**
		 * 绑定图片预览
		 */
		initTmpPicPreview : function(){
			var btnIds = ['#imp_headPicBlogImage'];
			var imgIds = ['#imp_headPicLoadImage'];
			$("body").on("change", "", function(){
				comm.initTmpPicPreview('#imp_headPicLoadImage', $('#imp_headPicLoadImage').children().first().attr('src'));
			});
			comm.initUploadBtn(btnIds, imgIds, 107, 64,"resources/images/user_img.png");
		},
		validateData : function(){
			return $("#jbzlForm").validate({
				rules : {
					xm_input : {
						required : true
					},
					nickName: {
						required : true
					},
					age:{
						digits:true,
						range:[0,120]
					},
					qqNo:{
						digits:true
					},
					zipCode:{
						zipCode:true
					}
				},
				messages : {
					xm_input : {
						required : '必填'
					},
					nickName:{
						required : '必填'
					},
					age:{
						digits:"年龄只能为数字",
						range:"年龄只能在1~120岁之间"
					},
					qqNo:{
						digits:"QQ号只能为数字"
					},
					zipCode:{
						zipCode:"邮编格式不正确"
					}
				},
				errorPlacement : function (error, element) {
					/*if ($(element).attr('name') == 'quickDate') {
						element = element.parent();
					}*/
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "top-18",
							at : "left+250"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					/*if ($(element).attr('name') == 'quickDate') {
						$(element.parent()).tooltip();
						$(element.parent()).tooltip("destroy");
					} else {*/
						$(element).tooltip();
						$(element).tooltip("destroy");
					/*}*/
				}
			});
		}
		
	};
	return jbzl
});
