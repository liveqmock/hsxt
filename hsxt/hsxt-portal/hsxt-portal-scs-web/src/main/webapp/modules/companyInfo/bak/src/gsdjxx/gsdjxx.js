define(['text!companyInfoTpl/gsdjxx/gsdjxx.html','text!companyInfoTpl/gsdjxx/gsdjxxxg.html','companyInfoDat/companyInfo' ],function(gsdjxxTpl ,gsdjxxxg,companyInfo){
	return {
		 
		showPage : function(){
		 	
		 	var self = this;
		 	
		 	var sysInfo = companyInfo.getSystemInfo();
			
			companyInfo.findSaicRegisterInfo({"entCustId" : sysInfo.entCustId},function (response) {
					var resData=response.data;
					$('#busibox').html(_.template(gsdjxxTpl,resData)).append(_.template(gsdjxxxg,resData));
					
					self.edit(resData);
					
					//企业类型
					var types=comm.lang("companyInfo")["custEntTypes"];
					$.each(types,function(i,v){
						if(resData.entCustType==v.value){
							$("#companyType_1").text(v.name);
						}
					})
					
					//
					/*日期控件*/
					$("#buildDate").datepicker({
						dateFormat : 'yy-mm-dd',
						maxDate: comm. getCurrDate()
					});
					
					$("#endDate").datepicker({
						dateFormat : 'yy-mm-dd',
						minDate: $("#buildDate").val()
					});
					
					 $("#longTime").change(function(){
						 if( $("#longTime").prop("checked")){
							 $("#endDate").val("");
							 $("#endDate").attr('disabled','disabled');
							 
						 }else {
							 $("#endDate").val($("#expirationDate_old").val());
							 $("#endDate").removeAttr("disabled");
							 
						 }
					 });
					 
			});
		},
		
		edit : function(data){
			var self = this;
			$('#ModifyBt_gongshang').triggerWith('#qyxx_gsdjxxxg');
			//取消按钮
			$('#backBt_gongshang').triggerWith('#gsdjxx');
		 	
		 	$('#gsdjxx_tpl').removeClass('none');
		 	
		 	var types=comm.lang("companyInfo")["custEntTypes"];
		 	
			$("#companyType").val('').attr('optionvalue', data.entCustType).selectList({
				width:192,
				height:28,
				borderWidth:1,
				borderColor:'#CCC',
				optionWidth: 192,
				optionHeight: 180,
				options:types
			});
		 	
		 	
		 	$('#ModifyBt_gongshang').click(function () {
		 		
				$('#gsdjxx_tpl').addClass('none');
				$('#gsdjxxxg_tpl').removeClass('none');
				
				comm.liActive($("#qyxx_gsdjxxxg"),"#qyxx_gsdjxx");
				$("#qyxx_gsdjxxxg").css("display","block");
			});
		 	
		 	$('#backBt_gongshang').click(function(){
		 		
		 		$('#gsdjxxxg_tpl').addClass('none');
				$('#gsdjxx_tpl').removeClass('none');
				$("#qyxx_gsdjxxxg").css("display","none");
				comm.liActive($("#qyxx_gsdjxx"));
		 	});
			
			//提交按钮
			$('#submitBt_gongshang').click(function () {
				if (!self.validateData()) {
					return;
				}
				var data =$('#gxdjxxxg_form').serializeJson();
				var sysInfo = companyInfo.getSystemInfo();
				
				var data1={
						"entCustId":sysInfo.entCustId,
						"entCustType" : $("#companyType").attr("optionvalue")
				}
				
				companyInfo.updateEntBaseInfo($.extend(data,data1}), function(response){
					$('#qyxx_gsdjxx').click();
				});

			});				
			
		},
		
		validateData : function () {
				var companyType=$("#companyType").val();
				var creationDate=$("#buildDate").val();
				var businessArea=$("#businessArea").val();
				var expirationDate=$("#endDate").val();
				
				var companyType_old=$("#companyType_old").val();
				var creationDate_old=$("#creationDate_old").val();
				var businessArea_old=$("#businessArea_old").val();
				var expirationDate_old=$("#expirationDate_old").val();
			   //都没有修改不用提交 
				if (companyType == companyType_old && creationDate_old == creationDate
						&& businessArea_old == businessArea
						&& expirationDate_old == expirationDate) {
						comm.alert(comm.lang("companyInfo").universal.unchanged);
						
						return false
				}
			
				return comm.valid({
					left : 10,
					top : 20,
					formID : '#gxdjxxxg_form',
					rules : {
						companyType : {
							required : true
						},
						buildDate : {
							required : true
						},
						contactNo : {
							required : true,
							mobileNo : 11
						},
						businessScope : {
							 maxlength : 300
						 }
					},
					messages : {
						companyType : {
							required : comm.lang("companyInfo")['gxdjxx']['not_empty_company_type']		
						},
						buildDate : {
							required : comm.lang("companyInfo")['gxdjxx']['not_empty_create_date']
						},
						contactNo : { 
							required :comm.lang("companyInfo")['gxdjxx']['not_empty_contact_tel'],
							mobileNo :comm.lang("companyInfo")['gxdjxx']['input_tel_illage']
						},
						businessScope : {
							maxlength : comm.lang("companyInfo")['gxdjxx']['input_busScope_out_length']
						}
					}
				});
		}
		
	}
}); 

 