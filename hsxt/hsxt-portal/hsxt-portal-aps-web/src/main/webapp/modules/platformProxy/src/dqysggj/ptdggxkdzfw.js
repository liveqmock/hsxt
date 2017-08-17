define(['text!platformProxyTpl/dqysggj/ptdggxkdzfw.html',
		'text!platformProxyTpl/dqysggj/qyhsh.html',
		'text!platformProxyTpl/dqysggj/ddtjqr.html',
		'platformProxyDat/platformProxy',
		'platformProxyLan'
		], function(ptdggxkdzfwTpl, qyhshTpl, ddtjqrTpl,dataMoudle){
	var self = {
		companyCustId:null,
		companyCustName:null,
		showPage : function(){
			$('#busibox').html(ptdggxkdzfwTpl);
			$('#ptdggxkdzfwTpl').html(qyhshTpl);
			self.showTpl($('#ptdggxkdzfwTpl'));

			$('#qyhsh_query_btn').click(function(){
				var resNo=$("#companyResNo").val();
				if(comm.isEmpty(resNo)){
					comm.error_alert(comm.lang("platformProxy").pleaseInputResNo);
					return;
				}
				if(!comm.isTrustResNo(resNo)){
					comm.error_alert(comm.lang("platformProxy").isTEntHsResNo);
					return;
				}
				dataMoudle.companyInfor({resNo:resNo},function(res){
					var data=res.data;
					if(data!=null){
						companyCustId=data.entCustId;
						companyCustName=data.entName;
						$("#companyName").html(data.entName);
						$("#contactPerson").html(data.contactPerson);
						$("#contactPhone").html(data.contactPhone);
						$('#qyhsh_next_tpl').removeClass('none')
					}
				});
			});

			$('#qyhsh_next_btn').text('提交订单');

			$('#qyhsh_next_btn').click(function(){
				var param = {
					companyCustId:companyCustId,
					companyName : companyCustName
				};
				dataMoudle.addCardStyleFeeOrder(param,function(resp){
					if(resp){
						$('#busibox').html(_.template(ddtjqrTpl,resp.data));
						$('#ddqr_success').click(function(){
							self.showPage();
						})
					}
				});
			});

		},
		showTpl : function(tpl){
			$('#ptdggxkdzfwTpl').addClass('none');
			tpl.removeClass('none');
		}
	};
	return self;
});
