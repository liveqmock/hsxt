define(["text!receivablesmanageTpl/qkdcx/xtzyf.html",
		"text!receivablesmanageTpl/qkdcx/xtzyf_ck.html",
		"receivablesmanageDat/receivablesmanage",
		"receivablesmanageLan"
	],function(xtzyfTpl, xtzyf_ckTpl,receivablesmanage){
	var xtzyfFun = {
		showPage : function(){
			$('#busibox').html(_.template(xtzyfTpl));
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			
			/** 查询事件 */
			$("#btnQuery").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				xtzyfFun.pageQuery();
			});
			
		},
		chaKan : function(obj){
			$('#busibox_ck').html(_.template(xtzyf_ckTpl,obj));
			
		/*	$('#xtzyfTpl').addClass('none');
			$('#xtzyf_ckTpl').removeClass('none');	
			comm.liActive_add($('#ckdd2'));*/
			
			$('#xtzyf_ckTpl').removeClass('none');
			$('#busibox').addClass('none');
			comm.liActive_add($('#ckdd2'));
			
			//$('#back_xtzyf').triggerWith('#xtzyf');
			//返回按钮
			$('#back_xtzyf').click(function(){
				//隐藏头部菜单
				$('#xtzyf_ckTpl').addClass('none');
				$('#busibox').removeClass('none');
				$('#ckdd2').addClass("tabNone").find('a').removeClass('active');
				$('#xtzyf').find('a').addClass('active');
			});
			
		},
		/** 分页查询 */
		pageQuery:function(){
			//查询参数
			var queryParam={
						"search_resNoCondition":$("#txtHsResNo").val(),"search_custNameCondition":$("#txtCustName").val(),
						"search_startDate":$("#search_startDate").val(),"search_endDate":$("#search_endDate").val()
						};
			receivablesmanage.debtOrderPage("searchTable",queryParam,function(record, rowIndex, colIndex, options){
				
				//订单状态
				if(colIndex==5){
                    return comm.lang("receivablesmanage").PayStatus[record["payStatus"]];
                }
				//操作列
				if(colIndex==6){
					var link1 = $('<a>查看</a>').click(function(e) {
						xtzyfFun.chaKan(record);
					});
					return link1 ;
				}
			});
		}
	}	
	return xtzyfFun;
});	
