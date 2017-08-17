define(['text!companyInfoTpl/zyxxbg/ck.html',
        'companyInfoDat/zyxxbg/zyxxbg',
		'companyInfoLan'],function(ckTpl, dataModoule){
	return {
		showPage : function(){
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			$('#contentWidth_3_ck').html(_.template(ckTpl, data));
			$('#contentWidth_3').css('display','none');
			$('#contentWidth_3_ck').css('display','block');
			
			$("#status").html(comm.getNameByEnumId(data.status, comm.lang("companyInfo").spStatusEnum2));
			
			if(data.bizLicenseCrePicNew){
				var picFileId1= '<a href="#" id="picFileId-1" class="mr10 blue">营业执照</a><br>';
				$("#picFiles").append(picFileId1);
				comm.initPicPreview("#picFileId-1", data.bizLicenseCrePicNew,null,null,null,null,{top:'60px',left:'390px'});
			}
			
			if(data.linkAuthorizePicNew){
				var picFiledId7 = '<a href="#" id="picFileId-7" class="mr10 blue">联系人授权委托书</a><br>';
				$("#picFiles").append(picFiledId7);
				comm.initPicPreview("#picFileId-7", data.linkAuthorizePicNew,null,null,null,null,{top:'60px',left:'390px'});
			}
			
			if(data.imptappPic){
				var imptappPic = '<a href="#" id="picFileId-6" class="mr10 blue">重要信息变更业务办理申请书</a>';
				$("#picFiles").append(imptappPic);
				comm.initPicPreview("#picFileId-6", data.imptappPic,null,null,null,null,{top:'60px',left:'390px'});
			}
			
			//返回按钮
			$('#btn_fh').click(function(){
				//隐藏头部菜单
				$('#contentWidth_3').css('display', 'block');
				$('#contentWidth_3_ck').css('display','none');
				$('#zyxxbg_ck').css('display','none').find('a').removeClass('active');
				$('#zyxxbg_jlcx').find('a').addClass('active');
				
				
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			dataModoule.findEntChangeByApplyId({applyId:$("#applyId").val()}, function(res){
				self.initForm(res.data);
			});
		}
	}
}); 

 