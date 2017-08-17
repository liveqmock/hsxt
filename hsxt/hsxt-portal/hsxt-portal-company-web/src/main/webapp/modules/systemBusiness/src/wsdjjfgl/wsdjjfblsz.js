define(['text!systemBusinessTpl/wsdjjfgl/wsdjjfblsz.html',
        'systemBusinessDat/systemBusiness',
        'systemBusinessSrc/wsdjjfgl/wsdjjfblxg',
        'systemBusinessSrc/wsdjjfgl/tab',
        ],function(tpl,systemBusiness,wsdjjfblxg,tab){
	var wsdjjfblsz={
		showPage : function(){
			systemBusiness.queryEntPointRate({},function(res){
				var trans = {};
				if(res.data == ""){
					trans.status = '1';
				}else{
					trans.status = '0';
				}
				$('#busibox').html(_.template(tpl,trans));
				
				//提交按钮
				$('#btn_wsdjjfblsz_tj').click(function(){
					if (!wsdjjfblsz.validateData()) {
						return;
					}
					var rate1 = $("#rate1").val();
					var rate2 = $("#rate2").val();
					var rate3 = $("#rate3").val();
					var rate4 = $("#rate4").val();
					var rate5 = $("#rate5").val();
					
					var rate = comm.formatTransRate(rate1) + "," + comm.formatTransRate(rate2) + "," + comm.formatTransRate(rate3) + "," + comm.formatTransRate(rate4) + "," + comm.formatTransRate(rate5);
					var postData = {
							rate : rate
					};
					systemBusiness.setEntPointRate(postData,function(res){
						comm.yes_alert("设置积分比例成功",400);
						wsdjjfblxg.showPage();
						$('#wsdjjfgl_wsdjjfblxg').show();
						$('#wsdjjfgl_wsdjjfblsz').css('display','none');
					});
				});
			});
		},
		validateData : function(){
			var deafultRang = $.cookie('startResType')==5?0.01:0.0001;
			return comm.valid({
				formID : '#wsdjjfblsz_form',
				rules : {
					rate1:{
						required : true,
						number : true,
						range: [deafultRang,0.3],
						rangelength:[3,6]
					},
					rate2:{
						required : true,
						number : true,
						range: [deafultRang,0.3],
						rangelength:[3,6]
					},
					rate3:{
						required : true,
						number : true,
						range: [deafultRang,0.3],
						rangelength:[3,6]
					},
					rate4:{
						required : true,
						number : true,
						range: [deafultRang,0.3],
						rangelength:[3,6]
					},
					rate5:{
						required : true,
						number : true,
						range: [deafultRang,0.3],
						rangelength:[3,6]
					}
				},
				messages : {
					rate1:{
						required : comm.lang("systemBusiness").pointrate,
						number : comm.lang("systemBusiness").pointNum,
						range : comm.strFormat(comm.lang("systemBusiness").pointRange,[deafultRang]),
						rangelength:comm.lang("systemBusiness").pointLengthRange
					},
					rate2:{
						required : comm.lang("systemBusiness").pointrate,
						number : comm.lang("systemBusiness").pointNum,
						range : comm.strFormat(comm.lang("systemBusiness").pointRange,[deafultRang]),
						rangelength:comm.lang("systemBusiness").pointLengthRange
					},
					rate3:{
						required : comm.lang("systemBusiness").pointrate,
						number : comm.lang("systemBusiness").pointNum,
						range : comm.strFormat(comm.lang("systemBusiness").pointRange,[deafultRang]),
						rangelength:comm.lang("systemBusiness").pointLengthRange
					},
					rate4:{
						required : comm.lang("systemBusiness").pointrate,
						number : comm.lang("systemBusiness").pointNum,
						range : comm.strFormat(comm.lang("systemBusiness").pointRange,[deafultRang]),
						rangelength:comm.lang("systemBusiness").pointLengthRange
					},
					rate5:{
						required : comm.lang("systemBusiness").pointrate,
						number : comm.lang("systemBusiness").pointNum,
						range : comm.strFormat(comm.lang("systemBusiness").pointRange,[deafultRang]),
						rangelength:comm.lang("systemBusiness").pointLengthRange
					}
				}
			});
		}
	}
	return wsdjjfblsz;
});