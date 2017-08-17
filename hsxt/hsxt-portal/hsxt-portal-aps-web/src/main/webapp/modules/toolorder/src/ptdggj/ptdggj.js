define(['text!toolorderTpl/ptdggj/ptdggj.html',
		'text!toolorderTpl/ptdggj/sggjlx.html',
		'text!toolorderTpl/ptdggj/ddxq.html',
		'text!toolorderTpl/ptdggj/ddtjcg.html',
		'toolorderDat/toolorder'
		],function(ptdggjTpl, sggjlxTpl, ddxqTpl, ddtjcgTpl,toolorder){
	return {
		showPage : function(){
			
			var self = this;
			
			$('#busibox').html(_.template(ptdggjTpl)).append(sggjlxTpl);//.append(ddxqTpl).append(ddtjcgTpl);
			
			//上一步 、下一步 按钮切换
			$('#next_sggjlx').click(function(){
				if (!self.validateData()) 
				{
					return;
				}
				var entName = $("#ptdggjEntName").val();
				if(entName == null || entName == ""){
					comm.alert(comm.lang("toolorder").entNotAvilabel);
					return;
				}
				self.showTpl($('#sggjlxTpl'));
			});
			
			$('#last_ptdggj, #confirm_btn').click(function(){
				self.showTpl($('#ptdggjTpl'));	
			});
			
			//查询企业重要信息
			$("#searchEntInfoBtn").click(function(){
				//验证
				if (!self.validateData()) 
				{
					return;
				}
				var entResNo = $("#entResNo").val();
				
				//查询企业重要信息  06002110000
				toolorder.queryEntMainInfoByResNo({resNo:entResNo},function(res){
					
					$("#entName").text(res.data.entName);
					$("#ptdggjEntName").val(res.data.entName);
					$("#contactPerson").text(res.data.contactPerson);
					$("#contactPhone").text(res.data.contactPhone);
					$("#sggjlxEntName").text(res.data.entName);
					$("#sggjlxEntResNo").val(res.data.entResNo);
					$("#sggjlxEntCustId").val(res.data.entCustId)
				});
				
			});
			
			
			//初始化可以购买的工具类型
			toolorder.queryToolType({},function(res){
				var options = [];
				var list = res.data;
				for(var i = 0; i < list.length; i++){
					//默认选中互生卡
					if(list[i].categoryCode == "P_CARD")
					{
						options.push({name:list[i].productName, value:list[i].productId + ":" + list[i].price + ":" + list[i].categoryCode,selected: true});
						$("#gjsg_tool_price").text(list[i].price);
						
						//查询该工具可以购买的数量
						toolorder.queryToolNum({categoryCode:list[i].categoryCode},function(res){
							$("#toolMaxNum").val(res.data);
						});
					}
					else
					{
						options.push({name:list[i].productName, value:list[i].productId + ":" + list[i].price + ":" + list[i].categoryCode});
						
					}
				}
				
				/*下拉列表*/
				$("#gjmc").selectList({
					width:330,
					options:options
				}).change(function(e){
					
						var currentValue = $(this).attr("optionValue");
						
						var categoryCode = currentValue.split(":")[2];
						
						var price = currentValue.split(":")[1];
						
						var param = {
								categoryCode : categoryCode
						};
						//查询该工具可以购买的数量
						toolorder.queryToolNum(param,function(res){
							$("#toolMaxNum").val(res.data);
						});
						
						if(currentValue.indexOf("P_CARD") > 0)
						{
							$('#hskxz').show();
							$('#sglx').hide();
						}
						else
						{
							$('#sglx').show();
							$('#hskxz').hide();
						}
						
						$("#gjsg_tool_price").text(price);
						//计算总价
						var num = $("#gjsg_toolNum").val();
						var re = /^[1-9]+[0-9]*]*$/;
						 if (re.test(num)){
							 var totalFee = price * 100 * num/100;
							 $("#totalFee").text(totalFee);
						 }
						 if(num == "" || num == null){
							 $("#totalFee").text(0);
						 }
				});
				/*end*/	
				
			});
			
			//查询互生卡卡样
			toolorder.queryCarTypes({},function(res){
				var styles = res.data;
				var html = "";
				for(var i = 0 ; i < styles.length ; i++){
					if(i == 0)
					{
						html = html + '<input type="radio" id="c'+i+'" name="carStyles" value="'+styles[i].cardStyleId+'"  checked="checked" /><label for="c1"><i class="card card_01"></i></label>';
					}
					else
					{
						html = html + '<input type="radio" id="c1'+i+'" name="carStyles" value="'+styles[i].cardStyleId+'" /><label for="c1"><i class="card card_01"></i></label>';
					}
				}
				$("#carStylesDiv").append(html);
			});
			
			//申购数量键盘事件，实时计算订单总价
			$("#gjsg_toolNum").keyup(function(e){
				if(e.keyCode >=48 && e.keyCode <=57 || e.keyCode == 8 || e.keyCode >= 96 && e.keyCode <= 105){
					var price = $("#gjsg_tool_price").text();
					var num = $(this).val();
					var re = /^[1-9]+[0-9]*]*$/;
					 if (re.test(num)){
						 var totalFee = price * 100 * num/100;
						 $("#totalFee").text(totalFee);
					 }
					 if(num == "" || num == null){
						 $("#totalFee").text(0);
					 }
				}
			});
			
			$('#next_ddxq').click(function(){
				if (!self.validateData_sggjlx()) 
				{
					return;
				}
				var optionValue = $("#gjmc").attr("optionValue");
				var toolType = optionValue.split(":")[2];
				var toolId = optionValue.split(":")[0];
				if(toolType == "P_CARD")
				{
					var toolNum = $("#gjsg_toolNum").val();
					var toolMaxNum = $("#toolMaxNum").val();
					if(toolNum != toolMaxNum && toolNum%1000 != 0)
					{
						comm.alert(comm.lang("toolorder").gjsg_hsknum);
						return;
					}
				}
				toolorder.queryAddress({},function(res){
					var data = {
							toolId : toolId,				//工具类型id
							toolName : $("#gjmc").val(),	//工具类型名称
							toolType : toolType,			//工具类型
							cardStyleId : $("input[name='carStyles']:checked").val(),	//卡样id
							companyName : $("#sggjlxEntName").text(),	//企业名称
							entCustId : $("#sggjlxEntCustId").val(),	//企业客户号
							entResNo : $("#sggjlxEntResNo").val(),		//企业互生号
							tool_price : $("#gjsg_tool_price").text(),	//工具单价
							toolNum : $("#gjsg_toolNum").val(),		//订单数量
							totalFee : $("#totalFee").text(),		//订单总价
							contact : res.data.contact,				//收货地址
							enter : res.data.enter					//企业地址
					};
					
					$("#gjsg_1").addClass('none');
					$("#gjsg_2").removeClass('none').html(_.template(ddxqTpl,data));
					
					$('#last_sggjlx').click(function(){
						$("#gjsg_2").addClass('none');
						$("#gjsg_1").removeClass('none');
					});
					
					$('#ddtj_btn').click(function(){
						self.commitOrder(self);	
					});
				});
			});
			
			
			
		},
		commitOrder : function(self){
			var $addrs= $('input[name="address"]:checked ').parent().parent().find("span");
			var userName = $addrs.eq(2).text();
			var mobiel = $addrs.eq(3).text();
			var address = $addrs.eq(4).text();
			var companyName = $addrs.eq(5).text();
			
			var postData = {
					entResNo : $("#tjddEntResNo").val(),	//互生号
					entCustId : $("#tjddEntCustId").val(),	//客户号
					entCustName : $("#tjdd_companyName").text(),//客户名称
					custType : "6",				//客户类型，地区平台
					orderAmount : $("#tjddTotalFee").text(),//代购订单金额
					status : 0,	
					reqOperator : '111',
					'detail[0].productId' : $("#tjdd_toolId").val(),
					'detail[0].categoryCode' : $("#tjdd_toolType").val(),
					'detail[0].productName' : $("#tjdd_toolName").text(),
					'detail[0].price' : $("#tjdd_toolPrice").text(),
					'detail[0].quantity' : $("#tjdd_toolNum").text(),
					'detail[0].totalAmount' : $("#tjdd_totalFee").text(),
					'detail[0].cardStyleId' : $("#tjdd_cardStyleId").val(),
					'deliverInfo.hsCustId' : $("#tjddEntCustId").val(),
					'deliverInfo.streetAddr' : address,
					'deliverInfo.fullAddr' : address,
					'deliverInfo.linkman' : userName,
					'deliverInfo.mobile' : mobiel,
					'deliverInfo.createdby' : '111'
					
			};
			toolorder.commitProxyOrder(postData,function(res){
				comm.alert(comm.lang("toolorder").orderCommitSuccess);
				$('#ptdggj').click();
			});
		},
		

//			

		/* 显示模版方法
		 * tpl:模版对象
		 */
		showTpl : function(tpl){
			$('#ptdggjTpl, #sggjlxTpl, #ddxqTpl').addClass('none');
			tpl.removeClass('none');
		},
		validateData : function(){
			return comm.valid({
				formID : '#ptdggj_form1',
				rules : {
					entResNo:{
						required : true
					}
				},
				messages : {
					entResNo:{
						required : comm.lang("toolorder")[34007]
					}
				}
			});
		},
		validateData_sggjlx : function(){
			return comm.valid({
				formID : '#gjsg_1_form',
				rules : {
					toolName:{
						required : true
					},
					gjsg_toolNum:{
						required : true,
						digits:true,
						max:$("#toolMaxNum").val()
					},
					hsk_style:{
						required : true
					},
					gjsg_sglx:{
						required : true
					}
				},
				messages : {
					toolName:{
						required : comm.lang("toolorder").gjsg_toolNameSelect
					},
					gjsg_toolNum:{
						required : comm.lang("toolorder").gjsg_toolNumNotNull,
						digits:comm.lang("toolorder").gjsg_toolNumMustDigits,
						max:comm.lang("toolorder").gjsg_toolMaxNum
					},
					hsk_style:{
						required : comm.lang("toolorder").gjsg_hskStyle
					},
					gjsg_sglx:{
						required : comm.lang("toolorder").gjsg_sglx
					}
				}
			});
		}
	}
});