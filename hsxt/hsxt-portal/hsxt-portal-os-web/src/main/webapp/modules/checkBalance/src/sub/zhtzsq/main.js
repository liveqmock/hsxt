define(['text!checkBalanceTpl/sub/zhtzsq/zhtzsq.html',
		'text!checkBalanceTpl/sub/zhtzsq/zhtzsq_opt.html',
		'checkBalanceDat/checkBalance', 
		'checkBalanceLan' 
		],function(zhtzsqTpl, zhtzsq_optTpl,checkBalanceDat ){
	return {
 
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(zhtzsqTpl) ;
			
			var self = this;
		    
		    /*下拉列表*/
			$("#zhtzsq_opt_zhlx").selectList({
				width:250,
				optionWidth:255,
				options:[
					{name:'互生币账户定向消费币',value:'20120',selected : true},
					{name:'互生币账户流通币',value:'20110'},
					{name:'互生币慈善救助基金账户',value:'20130'},
					{name:'积分账户',value:'10110'},
					{name:'货币账户',value:'30110'},
					{name:'积分城市税收对接账户',value:'10510'},
					{name:'货币城市税收对接账户',value:'30310'},
					{name:'互生币城市税收对接账户',value:'20610'},
					{name:'积分投资账户',value:'10410'}
				]
			});
			
			/*end*/
			
 		   	/*富文本框*/
			$('#zhtzsq_opt_tzyy').xheditor({
				upLinkUrl:"upload.php",
				upLinkExt:"zip,rar,txt",
				upImgUrl:"upload.php",
				upImgExt:"jpg,jpeg,gif,png",
				upFlashUrl:"upload.php",
				upFlashExt:"swf",
				upMediaUrl:"upload.php",
				upMediaExt:"wmv,avi,wma,mp3,mid",
				width:773,
				height:250
			}); 
			/*end*/
			/** 获取用户名称 */
			$('#zhtzsq_opt_hsht').blur(function(){
				if(!self.validCardNo()) {
					return;
				}
				var approvalParam={
						"resNo":$("#zhtzsq_opt_hsht").val() };
				checkBalanceDat.getUsernameByResNo(approvalParam,function(rsp){
					$('#zhtzsq_opt_dwmc').val(rsp.data);	
					
				 });
				$('#zhtzsq_opt_zhlx').change();
				
			});
			/** 获取帐户余额*/
			$('#zhtzsq_opt_zhlx').change(function(){
				if(!self.saveValid()) {
					return;
				}
				var approvalParam={
						"resNo":$("#zhtzsq_opt_hsht").val() ,
						"acctType":$("#zhtzsq_opt_zhlx").attr("optionValue")
				};
				checkBalanceDat.getBalance(approvalParam,function(rsp){
					$('#zhtzsq_opt_zhye').val(rsp.data);	
					
				 });
			});
			
			$('#zhtzsq_btn_tj').click(function(){
				// 验证数据
				if(!self.saveValid()) {
					return;
				}
				// 添加调账
				var approvalParam={
						"acctType":$("#zhtzsq_opt_zhlx").attr("optionValue"),
						"amount":$("#zhtzsq_opt_tzje").val(),
                        "name":$("#zhtzsq_opt_hsht").val(),
                        "remark":$("#zhtzsq_opt_tzyy").val(),
                        "checkType":$('input:radio[name="zhtzsq_opt_zj"]:checked').val(),
						"resNo":$("#zhtzsq_opt_hsht").val(),
						"checker":$.cookie('custId'),
						'date' :new Date().getTime()
				 };
				
				checkBalanceDat.addCheckBalance(approvalParam,function(rsp){
					alert("保存成功");
					$("#zhtzsq_opt_hsht").val("");
					$("#zhtzsq_opt_tzyy").val("");
					$("#zhtzsq_opt_tzje").val("");
					$("#zhtzsq_opt_dwmc").val("");
					$("#zhtzsq_opt_zhye").val("0");
				 });
			});
			
			$('#zhtzsq_btn_fq').click(function(){
				$("#zhtzsq_opt_hsht").val("");
					$("#zhtzsq_opt_tzyy").val("");
					$("#zhtzsq_opt_tzje").val("");
					$("#zhtzsq_opt_dwmc").val("");
					$("#zhtzsq_opt_zhye").val("1");
			});
		},
		validCardNo : function(){
			return comm.valid({
				left:260,
				top:0 ,
				formID:'#zhtzsqForm',
				rules : {
					zhtzsq_opt_hsht : {
						required : true,
						hsCardNo : true
					}
				},
				messages : {
					zhtzsq_opt_hsht : {
						required : "必填",
						hsCardNo : "输入正确的互生号"
					}
				}
			});	
		},

		saveValid : function(){
			return comm.valid({
				left:260,
				top:0 ,
				formID:'#zhtzsqForm',
				rules : {
					zhtzsq_opt_hsht : {
						required : true,
						hsCardNo : true
					},
					zhtzsq_opt_tzje : {
						required : true	,
						huobi : true
					},
					zhtzsq_opt_zhlx : {
						required : true
					},
					zhtzsq_opt_tzyy : {
						required : true
					}
				},
				messages : {
					zhtzsq_opt_hsht : {
						required : "必填",
						hsCardNo : "输入正确的互生号"
					},
					zhtzsq_opt_tzje : {
						required : "必填",
						huobi : "输入正确的货币格式"
					},
					zhtzsq_opt_zhlx : {
						required : '选择账户类型'
					},
					zhtzsq_opt_tzyy : {
						required : '请填写调账原因'
					}
				}
	 
			
			});	
				
		}
	}
}); 


 