define(function() {


//自定义下拉列表样式

$("#codCompanyType").selectbox({width:210}).bind('change', function(){
	//console.log($(this).val());
	if ($(this).val() == 0){
		location.href="企业申报-托管企业申报.html";
		}
	if ($(this).val() == 1){
		location.href="企业申报-成员企业申报.html";
		}
	if ($(this).val() == 2){
		location.href="企业申报-服务公司申报.html";
		}
});
$("#codPersonType").selectbox({width:210});

$("#companyName").selectbox({width:190});

$("#brandSelect").selectbox({width:190});
$("#area_1").selectbox({width:86});
$("#area_2").selectbox({width:86});

$("#resNo").selectbox({width:120});

$("#resNo1").selectbox({width:210});

$("#applyType").selectbox({width:100});

$("#applyState").selectbox({width:100});
$("#certificatesType_2").selectbox({width:300});

});

