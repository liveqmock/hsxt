define(['text!systemBusinessTpl/wsdjjfgl/wsdjjfblxg.html',
        'systemBusinessDat/systemBusiness'],function(tpl,systemBusiness){
	var  wsdjjfblxg={
		showPage : function(){
			systemBusiness.queryEntPointRate({},function(res){
				var trans = {};
				if(res.data != ""){
					trans.status = '1';
					var rates = res.data;
				}else{
					trans.status = '0';
				}
				var backData = res.data;
				trans.yrate1 = comm.formatTransRate(backData[0]);
				trans.yrate2 = comm.formatTransRate(backData[1]);
				trans.yrate3 = comm.formatTransRate(backData[2]);
				trans.yrate4 = comm.formatTransRate(backData[3]);
				trans.yrate5 = comm.formatTransRate(backData[4]);
				
				$('#busibox').html(_.template(tpl,trans));
				
				//提交按钮
				$('#btn_wsdjjfblxg_tj').click(function(){
					if (!wsdjjfblxg.validateData()) {
						return;
					}
					var rate1 = $.trim($("#rate1").val());
					var rate2 = $.trim($("#rate2").val());
					var rate3 = $.trim($("#rate3").val());
					var rate4 = $.trim($("#rate4").val());
					var rate5 = $.trim($("#rate5").val());
					
					if(rate1=="" && rate2=="" && rate3=="" && rate4=="" &&rate5==""){
						comm.error_alert("无数据变更！",400);
						return;
					}
					if(rate1==""){
						rate1=backData[0];
					}
					if(rate2==""){
						rate2=backData[1];
					}
					if(rate3==""){
						rate3=backData[2];
					}
					if(rate4==""){
						rate4=backData[3];
					}
					if(rate5==""){
						rate5=backData[4];
					}
					var rate = comm.formatTransRate(rate1) + "," + comm.formatTransRate(rate2) + "," + comm.formatTransRate(rate3) + "," + comm.formatTransRate(rate4) + "," + comm.formatTransRate(rate5);
					var postData = {
							rate : rate
					};
					systemBusiness.updateEntPointRate(postData,function(res){
						comm.yes_alert("修改积分比例成功",400);
						wsdjjfblxg.showPage();
					});
					
				});
			});
		},
		validateData : function(){
			var deafultRang = $.cookie('startResType')==5?0.01:0.0001;
			return comm.valid({
				formID : '#wsdjjfblxg_form',
				rules : {
					rate1:{
						number : true,
						range: [deafultRang,0.3],
						rangelength:[3,6]
					},
					rate2:{
						number : true,
						range: [deafultRang,0.3],
						rangelength:[3,6]
					},
					rate3:{
						number : true,
						range: [deafultRang,0.3],
						rangelength:[3,6]
					},
					rate4:{
						number : true,
						range: [deafultRang,0.3],
						rangelength:[3,6]
					},
					rate5:{
						number : true,
						range: [deafultRang,0.3],
						rangelength:[3,6]
					}
				},
				messages : {
					rate1:{
						number : comm.lang("systemBusiness").pointNum,
						range : comm.strFormat(comm.lang("systemBusiness").pointRange,[deafultRang]),
						rangelength:comm.lang("systemBusiness").pointLengthRange
					},
					rate2:{
						number : comm.lang("systemBusiness").pointNum,
						range : comm.strFormat(comm.lang("systemBusiness").pointRange,[deafultRang]),
						rangelength:comm.lang("systemBusiness").pointLengthRange
					},
					rate3:{
						number : comm.lang("systemBusiness").pointNum,
						range : comm.strFormat(comm.lang("systemBusiness").pointRange,[deafultRang]),
						rangelength:comm.lang("systemBusiness").pointLengthRange
					},
					rate4:{
						number : comm.lang("systemBusiness").pointNum,
						range : comm.strFormat(comm.lang("systemBusiness").pointRange,[deafultRang]),
						rangelength:comm.lang("systemBusiness").pointLengthRange
					},
					rate5:{
						number : comm.lang("systemBusiness").pointNum,
						range : comm.strFormat(comm.lang("systemBusiness").pointRange,[deafultRang]),
						rangelength:comm.lang("systemBusiness").pointLengthRange
					}
				}
			});
		}
	}
	return wsdjjfblxg;
});