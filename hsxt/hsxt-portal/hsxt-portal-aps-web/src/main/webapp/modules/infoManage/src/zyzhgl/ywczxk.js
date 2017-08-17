define(['text!infoManageTpl/zyzhgl/ywczxk.html',
		'text!infoManageTpl/zyzhgl/ywczxksub.html',
		'infoManageDat/infoManage',
		'infoManageLan'
], function(ywczxkTpl, ywczxksubTpl,infoManage){
	return {
		showPage : function(num,custID){
			if('xfz' == num){
				$('#consumerBox').html(_.template(ywczxkTpl,{num:num}));
			}else{
				$('#infobox').html(_.template(ywczxkTpl,{num:num}));
			}
				
			var data = {custID:custID};
			
			infoManage.findBusinessPmInfo(data,function(res){
				var obj = res.data;
				var pvToHsb = comm.lang("infoManage").businessPermison[obj.PV_TO_HSB];
				var pvInvest = comm.lang("infoManage").businessPermison[obj.PV_INVEST];
				var buyHsb = comm.lang("infoManage").businessPermison[obj.BUY_HSB];
				var hsbToCash = comm.lang("infoManage").businessPermison[obj.HSB_TO_CASH];
				var cashToBank = comm.lang("infoManage").businessPermison[obj.CASH_TO_BANK];
				
				var pvToHSBRemark = obj.PV_TO_HSBRemark;
				var pvINVESTRemark = obj.PV_INVESTRemark;
				var buyHSBRemark = obj.BUY_HSBRemark;
				var cashToBANKRemark = obj.CASH_TO_BANKRemark;
				var hsbToCASHRemark = obj.HSB_TO_CASHRemark;
				switch(num){
				case 'tgqy':
					$("#tgqy_jfzhsb_query").text(pvToHsb);
					$("#tgqy_jftz_query").text(pvInvest);
					$("#tgqy_dhhsb_query").text(buyHsb);
					$("#tgqy_hsbzhb_query").text(hsbToCash);
					$("#tgqy_hbzyh_query").text(cashToBank);
					
					$("#tgqy_jfzhsb_query_info").text(pvToHSBRemark);
					$("#tgqy_jftz_query_info").text(pvINVESTRemark);
					$("#tgqy_dhhsb_query_info").text(buyHSBRemark);
					$("#tgqy_hsbzhb_query_info").text(hsbToCASHRemark);
					$("#tgqy_hbzyh_query_info").text(cashToBANKRemark);
					break;
				case 'cyqy':
					$("#cyqy_jfzhsb_query").text(pvToHsb);
					$("#cyqy_jftz_query").text(pvInvest);
					$("#cyqy_dhhsb_query").text(buyHsb);
					$("#cyqy_hsbzhb_query").text(hsbToCash);
					$("#cyqy_hbzyh_query").text(cashToBank);
					
					$("#cyqy_jfzhsb_query_info").text(pvToHSBRemark);
					$("#cyqy_jftz_query_info").text(pvINVESTRemark);
					$("#cyqy_dhhsb_query_info").text(buyHSBRemark);
					$("#cyqy_hsbzhb_query_info").text(hsbToCASHRemark);
					$("#cyqy_hbzyh_query_info").text(cashToBANKRemark);
					break;
				case 'fwgs':
					$("#fwgs_jfzhsb_query").text(pvToHsb);
					$("#fwgs_jftz_query").text(pvInvest);
					$("#fwgs_dhhsb_query").text(buyHsb);
					$("#fwgs_hsbzhb_query").text(hsbToCash);
					$("#fwgs_hbzyh_query").text(cashToBank);

					$("#fwgs_jfzhsb_query_info").text(pvToHSBRemark);
					$("#fwgs_jftz_query_info").text(pvINVESTRemark);
					$("#fwgs_dhhsb_query_info").text(buyHSBRemark);
					$("#fwgs_hsbzhb_query_info").text(hsbToCASHRemark);
					$("#fwgs_hbzyh_query_info").text(cashToBANKRemark);
					break;
				case 'xfz':
					$("#xfz_jfzhsb_query").text(pvToHsb);
					$("#xfz_jftz_query").text(pvInvest);
					$("#xfz_dhhsb_query").text(buyHsb);
					$("#xfz_hsbzhb_query").text(hsbToCash);
					$("#xfz_hbzyh_query").text(cashToBank);
					
					$("#xfz_jfzhsb_query_info").text(pvToHSBRemark);
					$("#xfz_jftz_query_info").text(pvINVESTRemark);
					$("#xfz_dhhsb_query_info").text(buyHSBRemark);
					$("#xfz_hsbzhb_query_info").text(hsbToCASHRemark);
					$("#xfz_hbzyh_query_info").text(cashToBANKRemark);
					
					break;
					default:
						break;
				}
				//console.log(obj);
			});
			
			$('#back_tgqyzy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#tgqyzy'),'#ckqyxxxx, #ck');
			});
			$('#back_cyqyzy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#cyqyzy'),'#ckqyxxxx, #ck');
			});
			$('#back_fwgszy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#fwgszy'),'#ckqyxxxx, #ck');
				
			});
			
			$('#back_xfzzy').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#xfzzy'),'#ckqyxxxx, #ck');
			});
			
			$("#sub_ywczxk").click(function(){
				$('#sltpgl-edit-dialog > p').html(_.template(ywczxksubTpl));
				$('#sltpgl-edit-dialog').dialog({
					title : "双签操作",
					open: function (event, ui) {
						$(".ui-dialog-titlebar-close", $(this).parent()).show();
					},
					width : 500,
					buttons: {
						"确定":function(){
							$(this).dialog("destroy")
						},
						"取消":function(){
							$(this).dialog("destroy")
						}
					}
				});		
			});
		}	
	}	
});