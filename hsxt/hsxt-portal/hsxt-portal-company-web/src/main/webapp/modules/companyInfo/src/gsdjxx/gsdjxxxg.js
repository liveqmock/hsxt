define(['text!companyInfoTpl/gsdjxx/gsdjxx_xg.html',
        'text!companyInfoTpl/gsdjxx/map.html',
        'companyInfoDat/gsdjxx/gsdjxx',
		'companyInfoLan'],function(gsdjxxxgTpl, mapTpl, dataModoule){
	var self =  {
		showPage : function(){
			self.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#busibox').html(_.template(gsdjxxxgTpl, data)).append(mapTpl);
			//绑定取消
			$('#backBt_gongshang').click(function(){
				$('#gsdjxx').click();
			});
			
			//绑定保存
			$('#submitBt_gongshang').click(function(){
				self.saveData();
				
			});
			
			//成立日期
			$("#buildDate").datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				maxDate : comm.getCurrDate()
			});
			
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var entAllInfo = comm.getCache("companyInfo", "entAllInfo");
			if(entAllInfo == null){
				dataModoule.findEntAllInfo(null, function(res){
					comm.setCache("companyInfo", "entAllInfo", res.data);
					self.initForm(res.data);
				});
			}else{
				self.initForm(entAllInfo);
			}
		},
		
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#qygsdjxx_form").validate({
				 rules : {
					nature : {
						required : true,
						rangelength:[2, 20]
					},
					buildDate : {
						required : true,
						date : true
					},
					businessScope : {
						rangelength:[0, 300]
					},
					endDate : {
						rangelength:[0, 50]
					}
				},
				messages : {
					nature : {
						required : comm.lang("companyInfo")[31044],
						rangelength:comm.lang("companyInfo")[31045]
					},
					buildDate : {
						required : comm.lang("companyInfo")[31046],
						date : comm.lang("companyInfo")[31047]
					},
					businessScope : {
						rangelength: comm.lang("companyInfo")[31050]
					},
					endDate : {
						rangelength:comm.lang("companyInfo")[31051]
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});

			return validate;
		},
		
		/**
		 * 保存数据
		 */
		saveData : function(){
			if(!self.validateData().form()){
				return;
			}
			//营业期限为时间格式时，不能小于成立时间
			if(comm.checkTimeFormat($("#endDate").val())){
				if(!self.timeCompare($("#endDate").val(),$("#buildDate").val())){
					comm.alert({imgClass: 'tips_i' ,content:comm.lang("companyInfo").timeTermCompare });
					return false;
				}
			}
			
			var params = {};
			params.nature = $("#nature").val();//企业类型
			params.buildDate = $("#buildDate").val();//成立日期
			params.businessScope = $("#businessScope").val();//经营范围
			params.endDate = $("#endDate").val();//营业期限
			
			dataModoule.updateEntExtInfo(params, function(res){
				//更新缓存数据
				comm.delCache("companyInfo", "entAllInfo");
				comm.alert({content:comm.lang("companyInfo")[22000], callOk:function(){
					$('#gsdjxx').click();
				}});
			});
		},
		/** 时间比较 */
        timeCompare:function(beginDate,endDate){
        	var d1 = new Date(beginDate.replace(/\-/g, "\/"));  
        	var d2 = new Date(endDate.replace(/\-/g, "\/"));  
        	if(d1 >=d2)
        		return true;  
        	else 
        		return false;
        }
	};
	
	return self;
}); 

 