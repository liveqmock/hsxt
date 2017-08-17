define(['text!infoManageTpl/zyml/ywczxk.html',
		'text!infoManageTpl/zyml/ywczxksub.html',
		'infoManageDat/infoManage',
		'infoManageLan'
], function(ywczxkTpl, ywczxksubTpl,infoManage){
	return {
		showPage : function(num,custID){
			$('#infobox').html(_.template(ywczxkTpl,{num:num}));	
			
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
						
						$("#tgqy_jfzhsb_info_query").text(pvToHSBRemark);
						$("#tgqy_jftz_info_query").text(pvINVESTRemark);
						$("#tgqy_dhhsb_info_query").text(buyHSBRemark);
						$("#tgqy_hsbzhb_info_query").text(hsbToCASHRemark);
						$("#tgqy_hbzyh_info_query").text(cashToBANKRemark);
						break;
					case 'cyqy':
						$("#cyqy_jfzhsb_query").text(pvToHsb);
						$("#cyqy_jftz_query").text(pvInvest);
						$("#cyqy_dhhsb_query").text(buyHsb);
						$("#cyqy_hsbzhb_query").text(hsbToCash);
						$("#cyqy_hbzyh_query").text(cashToBank);
						
						$("#cyqy_jfzhsb_info_query").text(pvToHSBRemark);
						$("#cyqy_jftz_info_query").text(pvINVESTRemark);
						$("#cyqy_dhhsb_info_query").text(buyHSBRemark);
						$("#cyqy_hsbzhb_info_query").text(hsbToCASHRemark);
						$("#cyqy_hbzyh_info_query").text(cashToBANKRemark);
						break;
					case 'fwgs':
						$("#fwgs_jfzhsb_query").text(pvToHsb);
						$("#fwgs_jftz_query").text(pvInvest);
						$("#fwgs_dhhsb_query").text(buyHsb);
						$("#fwgs_hsbzhb_query").text(hsbToCash);
						$("#fwgs_hbzyh_query").text(cashToBank);

						$("#fwgs_jfzhsb_info_query").text(pvToHSBRemark);
						$("#fwgs_jftz_info_query").text(pvINVESTRemark);
						$("#fwgs_dhhsb_info_query").text(buyHSBRemark);
						$("#fwgs_hsbzhb_info_query").text(hsbToCASHRemark);
						$("#fwgs_hbzyh_info_query").text(cashToBANKRemark);
						break;
					case 'xfz':
						$("#xfz_jfzhsb_query").text(pvToHsb);
						$("#xfz_jftz_query").text(pvInvest);
						$("#xfz_dhhsb_query").text(buyHsb);
						$("#xfz_hsbzhb_query").text(hsbToCash);
						$("#xfz_hbzyh_query").text(cashToBank);
						
						$("#xfz_jfzhsb_info_query").text(pvToHSBRemark);
						$("#xfz_jftz_info_query").text(pvINVESTRemark);
						$("#xfz_dhhsb_info_query").text(buyHSBRemark);
						$("#xfz_hsbzhb_info_query").text(hsbToCASHRemark);
						$("#xfz_hbzyh_info_query").text(cashToBANKRemark);
						
						break;
					default:
						break;
				}
				//console.log(obj);
			});
			
			$('#back_tgqyzyyl').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#tgqyzyyl'),'#ckqyxxxx, #ck');
			});
			$('#back_cyqyzyyl').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#cyqyzyyl'),'#ckqyxxxx, #ck');
			});
			$('#back_fwgszyyl').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#fwgszyyl'),'#ckqyxxxx, #ck');
			});
			
			$('#back_xfzzyyl').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#xfzzyyl'),'#ckqyxxxx, #ck');
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
		},
		detail:function(){
			comm.re
		}
	}	
});