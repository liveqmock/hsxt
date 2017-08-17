define(['text!companyInfoTpl/yhzhxx/yhzhxx.html',
		'text!companyInfoTpl/yhzhxx/yhzhxx_tj.html',
		'text!companyInfoTpl/yhzhxx/yhzhxx_xq.html',
		'companyInfoDat/companyInfo'
		], function(yhzhxxTpl, yhzhxx_tjTpl,yhzhxx_xqTpl, companyInfo){
	return {
		showPage : function(){
			var self = this;
			var info = companyInfo.getSystemInfo();
			companyInfo.findCardList({"custType" : "e","entCustId":info.entCustId},function (response) {
					var data= response.data;
					data==null?"":data;
					
					$('#contentWidth_4').html(_.template(yhzhxxTpl,{"list":data,"platform_currency":companyInfo.getSystemInfo().platform_currency} ));
					
					self.bindEventAdd();
					
					self.bindEventDel();
					if(data!=""){
						self.bindEventDetail(data);
					}
					
			});
		},
		//通过id得到数据 
		getDataById: function(bankAccounId, resData){
			var data;
			$.each(resData, function(k,v){
				if(v.accId === bankAccounId){
					data = v;
					return data;
				}
			});
			//拼接结算货币
			return data;
		},
	
		//验证新增的数据 
		validateAddData : function () {
			var bankCode = $.trim($('#brandSelect_add').val());
			if(bankCode==null||bankCode=='') {
				$('.custom-combobox').attr("title", '银行不能为空').tooltip({
					position : {
						my : "left+430 top+5",
						at : "left top"
					}
				}).tooltip("open");
				$(".ui-tooltip").css("max-width", "230px");
				return false;
			} else {
				$(".custom-combobox").tooltip().tooltip("destroy");
			}
			return comm.valid({
				left : 10,
				top : 20,
				formID : '#yhzhxx_tj_form',
				rules : {
					provinceName :{
						required : true
					},
					
					cityName :{
						required : true
					},
					input_bankAccNo:{
						required : true,
						equalTo: '#input_bankAccNo_c',
						bankNo : [16,19]
					}
					
				},
				messages : {
					input_bankAccNo:{
						required : comm.lang("companyInfo").yhzhxx.bank_no_empty,
						equalTo: comm.lang("companyInfo").universal.input_unequable,
						bankNo:comm.lang("companyInfo").universal.input_bank_num
					},
					provinceName :{
						required : comm.lang("companyInfo").universal.not_empty_province
					},
					cityName :{
						required : comm.lang("companyInfo").universal.not_empty_city
					}
				}
			});
		},
		
		bindEventDel: function(){
			var dc= comm.lang("companyInfo").yhzhxx.del_bank_acc_confirm;
			var loginInfo=companyInfo.getSystemInfo();
			//删除银行
			$('.deleteBt_yinghang').click(function(){
				var that=this;
				comm.confirm({
					content: dc,
					imgFlag: 1,
					callOk: function () {
						companyInfo.delBankCard({'bankAccId':$(that).attr('data-id'),"custType" : "e"}, function (response) {
							$('#qyxx_yhzhxx').click();	
						});
					}
				});
			});
		},
		
		bindEventAdd: function(){
			var self = this;
			
			//添加
			/*按钮事件*/
			$('#tj_btn').click(function(){
				
					var info = companyInfo.getSystemInfo();
					var data={"platform_currency":info.platform_currency,"login_name":info.entCustName};
					
					$('#yhzhxxTpl').addClass('none');
					$('#yhzhxxTpl_xq').removeClass('none').html(_.template(yhzhxx_tjTpl,data));
					comm.liActive_add($('#tjyhzh'));
					
					comm.initCombox('#brandSelect_add',comm.lang("companyInfo")['bankList'],null,true);
				
					var provinceList = comm.lang("companyInfo")["provinces"];
					
					/*省份下拉框*/
					$("#province_slt").selectList({
						width:140,
						borderWidth:1,
						borderColor:'#CCC',
						optionWidth: 180,
						optionHeight: 180,
						options: provinceList
					}).change(function(e){
						/*城市下拉框*/
						var v = $(this).attr('optionvalue');
						var cityList=self.getCitys(v);
						$("#city_slt").val('').attr('optionvalue', '').selectList({
							width:140,
							borderWidth:1,
							borderColor:'#CCC',
							optionWidth: 140,
							optionHeight: 180,
							options:cityList
						});
					});
					
					$("#city_slt").selectList({
						width:140,
						borderWidth:1,
						borderColor:'#CCC',
						optionWidth: 140,
						optionHeight: 180
					});
					
					$("#city_slt").selectList({width:220});
					
					/*end*/
					
					$('#tj_cancel').click(function(){
						$('#qyxx_yhzhxx').click();	
					});
					
					$('#tj_tj').click(function(){
						if (!self.validateAddData("yhzhxx_tj_form")) {
							return;
						}
						
						var add_data={
							resNo : info.resNo,
							entCustId : info.entCustId,
							bankCode :  $('#brandSelect_add').attr('value'),
							bankName :  $('#brandSelect_add option:selected').text(),
							provinceNo       :  $('#province_slt').attr('optionvalue'),
							provinceName       :  $('#province_slt').attr('text'),
							cityName           :  $('#city_slt').attr('text'),
							cityNo           :  $('#city_slt').attr('optionvalue'),
							bankAccNo       :  $('#input_bankAccNo').attr('value'),
							isDefaultAccount   :  $('input:radio[name="bankDefault"]:checked').val(),
							custType : info.custType,
							countryCode : info.countryCode,
							countryName : info.countryName
						}
						
						companyInfo.addBankCard(add_data, function(response){
							$('#qyxx_yhzhxx').click();	
						});	
					});
			 });
		},
		
		getCitys : function(provinceCode){
			var citys=comm.lang("companyInfo")["citys"];
			var data=[];
			$.each(citys,function(i,v){
				if(provinceCode==v.provinceCode){
					data.push(v);
				}
			});
			
			return data;
		},
		
		bindEventDetail: function(resData){
			var self = this;
			//查看
			$('.info_btn').click(function(){
				$('#yhzhxxTpl').addClass('none');
				var data=self.getDataById($(this).attr('data-id'),resData);
				$('#yhzhxxTpl_xq').removeClass('none').html(_.template(yhzhxx_xqTpl,$.extend({"platform_currency":companyInfo.getSystemInfo().platform_currency},data) ));
				//显示详情菜单
			//	comm.liActive_add($('#qyxx_yhzhxxxq'));	
				
				//返回按钮
				$('#back_yhzhxx').click(function(){
					$('#yhzhxxTpl').removeClass('none');
					$('#yhzhxxTpl_xq').addClass('none').html('');
					//显示列表菜单
					comm.liActive($('#qyxx_yhzhxx'), '#tjyhzh, #ckxq');
				});
				
			});
		}
		
	}
});