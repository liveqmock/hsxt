define(['text!platformDebitTpl/kksq/kksq.html',
        'platformDebitDat/platformDebit',
        'platformDebitLan'
        ], function(kksqTpl,platformDebit){
	var kksqFun = {
		isSubmit:false,			//是否能提交(根据扣款互生号查询的结果进行赋值)
		resNoType:null,			//扣款互生号类型
		resNoCustId:null,		//互生号客户号
		showPage : function(){
			$('#busibox').html(kksqTpl);
			var self = this;
			
			/*富文本框*/
			$('#debitReason').xheditor({
				 tools:"Cut,Copy,Paste,Pastetext,|,Blocktag,FontfaceFontSize,Bold,Italic,Underline,Strikethrough,FontColor,BackColor,SelectAll,Removeformat,|,Align,List,Outdent,Indent,|,Link,Unlink,Anchor,Hr,Table,|,Source,Preview,Fullscreen",
				//upLinkUrl:"upload.php",
				//upLinkExt:"zip,rar,txt",
				//upImgUrl:"upload.php",
				//upImgExt:"jpg,jpeg,gif,png",
				//upFlashUrl:"upload.php",
				//upFlashExt:"swf",
				//upMediaUrl:"upload.php",
				//upMediaExt:"wmv,avi,wma,mp3,mid",
				width:678,
				height:150
			}); 
			/*end*/
			
			//获取互生号关联的企业或持卡人名称
			$('#txtResNo').blur(function(){
				var $resNo = $(this).val();
				if(comm.navNull($resNo)==""){
					return false;
				}
				
				//重置为阻止提交
				kksqFun.isSubmit=false;	
				
				//获取名称
				platformDebit.getResNoInfo({"queryResNo":$resNo},function(rsp){
					var resNoInfo = rsp.data;	
					$('#txtUnitName').val(resNoInfo.name);			//互生号名称
					kksqFun.resNoCustId=resNoInfo.resNoCustId;		//互生号客户号
					kksqFun.resNoType=resNoInfo.custType;			//互生号类型
					kksqFun.superEntName=resNoInfo.superEntName;	//上级企业名称
					kksqFun.isSubmit=true;							//能申请提交
				});
			});
			
			//提交扣款申请
			$('#bc_btn').click(function(){
				if(!self.saveValid().form()){
					return false;	
				}
				
				//判断能否申请扣款
				if(!kksqFun.isSubmit){
					comm.alert({imgClass : 'tips_i',content : '请确认扣款互生号正确！'});
					return false;
				}
				
				var $amount = $("#txtAmount").val();		//扣款金额
				var $resNo =  $("#txtResNo").val();			//互生号
				var $debitReason = $('#debitReason').val();	//扣款原因
				var $resNoName = $('#txtUnitName').val();	//单位名称
				
				comm.confirm({
					imgFlag : true,
					imgClass : 'tips_ques',
					width : 800,
					content : '您正在对互生号为'+$resNo+'（'+$('#txtUnitName').val()+'）的'+(kksqFun.resNoType==1 ? '消费者':'企业')+'进行扣款操作，扣款金额'+comm.formatMoneyNumber($amount)+'互生币！',
					callOk : function(){
						var param = {
								"deductResNo":$resNo,"deductCustId":kksqFun.resNoCustId,
								"deductCustName":$resNoName,"deductCustType":kksqFun.resNoType,
								"applyRemark":$debitReason,"amount":$amount};
						
						//申请扣款
						platformDebit.applyHsbDeduction(param,function(rsp){
							comm.alert({imgClass : 'tips_yes',content : '申请扣款成功'});
							$('#subNav a[menuurl="platformDebitSrc/kksqjlcx/tab"]').click();
						});
					}
				});
			});
			
			$('#fq_btn').triggerWith('#subNav a[menuurl="platformDebitSrc/kksqjlcx/tab"]');
				
		},
		/** 申请扣款验证 */
		saveValid : function(){
			return $("#kksqForm").validate({
				rules : {
					txtResNo : {
						required : true,
						hsCardNo : true
					},
					txtAmount : {
						required : true	,
						huobi : true,
						min: function(){
							if($("#txtAmount").val()>0){
								return false;
							}else{
								return true ;
							}
						}	
					},
					debitReason: {
						maxlength :3000,
						required : function(){
							return !comm.isNotEmpty($("#debitReason").val());
						}	
					}
					
				},
				messages : {
					txtResNo : {
						required : "请输入正确的互生号",
						hsCardNo : "请输入正确的互生号"
					},
					txtAmount : {
						required : "请输入正确的金额",
						huobi : "请输入正确的金额",
						min:"请输入正确的金额",
					},
					debitReason: {
						maxlength : "扣款原因只能输入3000字符",
						required : "请输入正确的扣款原因"	
					}
				},
				errorPlacement : function (error, element) {
					if($(element).attr('name') == 'debitReason'){
						
						$(element.parent()).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+220",
								at : "top+30"
							}
						}).tooltip("open");
					}
					else{
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+120",
								at : "top+15"
							}
						}).tooltip("open");	
					}
					
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					/*if ($(element).attr('name') == 'brandSelect_add') {
						$(element.parent()).tooltip();
						$(element.parent()).tooltip("destroy");
					} else {*/
						$(element).tooltip();
						$(element).tooltip("destroy");
					/*}*/
				}
			});	
		}
	}	
	
	return kksqFun;
});
