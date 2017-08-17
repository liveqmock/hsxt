define(['text!infoManageTpl/zyzhgl/gsdjxx.html',
        'infoManageDat/infoManage',
		'infoManageLan'], function(gsdjxxTpl, dataModoule){
	return {
		showPage : function(num, custID){
			this.initData(custID, num);
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data, num){
			data.num = num;
			$('#infobox').html(_.template(gsdjxxTpl, data));
			if(data){
				var busiLicenseImg = data.busiLicenseImg;
				if(null != busiLicenseImg && '' != busiLicenseImg){
					$("#busi_license_img").attr("src", comm.getFsServerUrl(busiLicenseImg));
				}
			}
			
			$('#back_tgqyzy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#tgqyzy'),'#ckqyxxxx, #ck');
			});
			$('#back_cyqyzy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#cyqyzy'),'#ckqyxxxx, #ck');
			});
			$('#back_fwgszy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#fwgszy'),'#ckqyxxxx, #ck');
				
			});
			
			 $("#busi_license_img").click(function(){//营业执照扫描件图片预览
			        comm.initTmpPicPreview("#query_busi_license_span",comm.getFsServerUrl(busiLicenseImg),"营业执照扫描件图片预览");
			    });
			if(data){
				$("#legalCreTypeText").html(comm.getNameByEnumId(data.credentialType, comm.lang("common").creTypes));
			}
		},
		/**
		 * 初始化数据
		 */
		initData : function(custID, num){
			var self = this;
			var entAllInfo = comm.getCache("infoManage", "entAllInfo");
			if(entAllInfo == null){
				dataModoule.resourceFindEntAllInfo({custID:custID}, function(res){
					comm.setCache("infoManage", "entAllInfo", res.data);
					self.initForm(res.data.entInfo, num);
				});
			}else{
				self.initForm(entAllInfo.entInfo, num);
			}
		}
	}	
});