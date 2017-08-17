define(['text!systemBusinessTpl/cyjfhdsq/cyjfhdsq.html',
		'systemBusinessDat/systemBusiness'
		], function(tpl,systemBusiness){
	return {
		showPage : function(){
			var self = this;
			systemBusiness.searchEntStatusInfo({searchType:"1"},function(res){
				
					var param = comm.getRequestParams();
					//后台返回值
					var reponse = {
							status : res.data.info.baseStatus,
							appDate:'',
							companyName : param.custEntName
					};
					if(res.data.poin){
						reponse.appDate = res.data.poin.applyDate;
						//如果是申请驳回 且类型是 参与积分活动申请
						//4：服务公司驳回，5：地区平台审批驳回， 6：地区平台复核驳回
						if((res.data.poin.status == 4 || res.data.poin.status == 5 || res.data.poin.status == 6) && res.data.poin.applyType == 1){
							reponse.status = '999'; //自定义状态  用户显示驳回信息
							reponse.poin = res.data.poin;
						}
						
						if(res.data.poin.status == 3 && reponse.status == '7' && res.data.poin.applyType == 0){
							reponse.status = '71';
						}
						
						if(res.data.poin.status < 3 && reponse.status == '7' && res.data.poin.applyType == 1){
							reponse.status = '72';
						}
					}
					
					$('#busibox').html(_.template(tpl, reponse));
					
					//绑定下载按钮
					cacheUtil.findDocList(function(map){
						comm.initDownload("#moduleFile", map.busList, '1006');
					});
					
					if(reponse.status == '1'){
						$("#tips_1").show();
					}else if(reponse.status == '71'){
						$("#tips_71").show();
					}else if(reponse.status == '72'){
						$("#tips_72").show();
					}else if(reponse.status == '6'){
						$("#tips_6").show();
					}else if(reponse.status == '2' || reponse.status == '3' || reponse.status == '4' || reponse.status == '5'){
						$("#tips_5").show();
					}else if(reponse.status == '8'){
						$("#tips_8").show();
					}else if(reponse.status == '999'){
						$("#tips_999").show();
					}
					
					self.initUpload_btn();
					
					//提交按钮
					$('#btn_cyjfhdsq_tj').click(function(){
						if (!self.validateData()) {
							return;
						}
						var confirmObj = {	
								imgFlag:true,
								width:480,
								content : comm.lang("systemBusiness").confirCyjfhd,	
								 													
								callOk :function(){
									//获取文件id
									self.uploadApplyFile(function(fileId){
										var param = comm.getRequestParams();
										
										var postData = {
												entCustId : param.entCustId,				//用户号
												entResNo : param.hsResNo, 					//互生号
												entCustName:param.custEntName,				//账户名称
												applyReason : $("#cyjfhdsq_reason").val(),  //申请原因
												oldStatus : res.data.info.baseStatus,		//企业基本状态
												bizApplyFile :fileId,						//申请书id
												createdBy : param.cookieOperNoName,					//创建人
												applyType : "1"								//申请类型  0：停止  1：参与
										};
										systemBusiness.pointActivityApply(postData,function(res2){
												//弹出申请提交成功提示框
//												comm.alert({width:500,imgClass: 'tips_i' ,content:comm.strFormat(comm.lang("systemBusiness").point_activity_apply_success,[comm.getTodaySE()[0]]) });
												$('#cyjfhdsq').trigger('click');
										});
									});
									
									self.initUpload_btn();
								}
							 }
						 comm.confirm(confirmObj) ; 
						
					});
					
					$("#back_btn").click(function(){
						$("#tips_999").hide();
						$("#tips_71").show();
					});
				
			});
		},
		//上传申请文件
		uploadApplyFile:function(callBack){
			if($("#apply_file").val()!=""){
				comm.uploadFile(null,['apply_file'],function(data){
					var fileId=data.apply_file;//文件id
					$("#hidApplyFileId").val(fileId);
					callBack(fileId);
				});
			}else{
				callBack($("#hidApplyFileId").val());
			}
		},
		initUpload_btn : function (){
			
			var btnIds = ['#apply_file'];
			var imgIds = ['#business_apply'];
			$("body").on("change", "", function(){
				for(var k in imgIds){
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			
			comm.initUploadBtn(btnIds, imgIds);
		},
		validateData : function(){
			return comm.valid({
				formID : '#cyjfhdsq_form',
				rules : {
					apply_file : {
						required : function(){
							var $fileId= comm.removeNull($("#hidApplyFileId").val());
							var $applyFile=$("#apply_file").val();
							if($applyFile=="" && $fileId==""){
								return true;
							}
							return false;
						}
					},
					cyjfhdsq_reason : {
						required : true,
						rangelength:[2,300]
					}
				},
				messages : {
					apply_file : {
						required : comm.lang("systemBusiness").fileNotNull
					},
					cyjfhdsq_reason : {
						required : comm.lang("systemBusiness").reasonNotNull,
						rangelength:comm.lang("systemBusiness").reasonRangelength
					}
				}
			});
		}
	};
});