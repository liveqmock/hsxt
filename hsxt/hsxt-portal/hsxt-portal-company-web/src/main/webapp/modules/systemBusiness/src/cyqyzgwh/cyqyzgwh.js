define(['text!systemBusinessTpl/cyqyzgwh/cyqyzgwh.html',
		'text!systemBusinessTpl/cyqyzgwh/cyqyzgzx.html',
		'text!systemBusinessTpl/cyqyzgwh/cyqyzgzx_qr.html',
		'systemBusinessDat/systemBusiness',
		'companyInfoDat/gsdjxx/gsdjxx'
		], function(tpl, cyqyzgzxTpl, cyqyzgzx_qrTpl,systemBusiness,dataModoule){
	return {
		showPage : function(){
			var self = this;
			//查询企业状态信息 当为注销
			systemBusiness.searchEntStatusInfo({},function(res){
				//后台返回值
				var reponse = {
						status : res.data.info.baseStatus,
						appDate : ''
				};
				
				if(res.data.memb){
					reponse.appDate = res.data.memb.applyDate;
					//如果是申请驳回 4：服务公司驳回，5：地区平台审批驳回， 6：地区平台复核驳回
					if(res.data.memb.status == 4 || res.data.memb.status == 5 || res.data.memb.status == 6 ){
						reponse.status = '999'; //自定义状态  用户显示驳回信息
						reponse.memb = res.data.memb;
					}
				}
				
				$('#busibox').html(_.template(tpl, reponse));
				
				if (reponse.status == '1' || reponse.status == '999') 
				{
					//成员企业资格注销按钮
					$('#btn_cyqyzgzx').click(function(){
						//显示头部菜单
						comm.liActive($('#cyqyzx'), '#cyqyzgwh');
						
						var param = comm.getRequestParams();
						
						$('#busibox').html(_.template(cyqyzgzxTpl,{companyName:param.custEntName}));
						
						//绑定下载按钮
						cacheUtil.findDocList(function(map){
							comm.initDownload("#moduleFile", map.busList, '1004');
						});
						
						self.initUpload_btn();
						
						//下一步按钮
						$('#btn_cyqyzgzx_xyb').click(function(){
							if (!self.validateData()) 
							{
								return;
							}
							var param = comm.getRequestParams();
							
							//查询下一页面的初始数据(账户信息) userType  用户类型	1：非持卡人 2：持卡人 3：操作员 4：企业
							systemBusiness.searchBusinessAcountInfo({userType:"4"},function(resback){
								var resData={
										cyqyzgzx_reason : $("#cyqyzgzx_reason").val(), //申请原因
										transfee : resback.data.transfee,	//货币转换费
										pointfee : resback.data.pointfee,	//积分账户余额
										currence : resback.data.currence,	//结算币种
										transrat : resback.data.transrat,	//货币转换比率
										companyName:param.custEntName		//账户名称
								};
								$('#cyqyzgzx_1').addClass('none');
								$('#cyqyzgzx_2').removeClass('none').html(_.template(cyqyzgzx_qrTpl,resData));
								
								//银行账户列表
								var accountlist = resback.data.acountlist;
								var options = new Array();
								if(accountlist != null && accountlist != '' && accountlist.length > 0)
								{
									for(var i = 0; i < accountlist.length; i++)
									{
										var obj = accountlist[i];
										var bankAcct = obj.bankAccNo;  //银行账号
										var accId = obj.accId;  //银行账户编号（主键系统自动生成）
										var _bankAcct = bankAcct.substring(0,4) + " **** **** " + bankAcct.substring(bankAcct.length -4,bankAcct.length);
										if(i == 0)
										{	 //默认选中第一条
											
											if(accountlist[i].isValidAccount == '1')
											{
												var _temp = {name:'【已验证】' + accountlist[i].bankName + " " + _bankAcct,value:accId+"|"+_bankAcct+"|"+obj.bankName+"|"+obj.countryNo+":"+obj.provinceNo+":"+obj.cityNo,selected:true};
												options.push(_temp);
											}
											else
											{
												var _temp = {name:'【未验证】' + accountlist[i].bankName + " " + _bankAcct,value:accId+"|"+_bankAcct+"|"+obj.bankName+"|"+obj.countryNo+":"+obj.provinceNo+":"+obj.cityNo,selected:true};
												options.push(_temp);
											}
										}
										else
										{
											if(accountlist[i].isValidAccount == '1')
											{
												var _temp = {name:'【已验证】' + accountlist[i].bankName + " " + _bankAcct,value:accId+"|"+_bankAcct+"|"+obj.bankName+"|"+obj.countryNo+":"+obj.provinceNo+":"+obj.cityNo};
												options.push(_temp);
											}
											else
											{
												var _temp = {name:'【未验证】' + accountlist[i].bankName + " " + _bankAcct,value:accId+"|"+_bankAcct+"|"+obj.bankName+"|"+obj.countryNo+":"+obj.provinceNo+":"+obj.cityNo};
												options.push(_temp);
											}
											
										}
										
									}
								}
								
								//下拉框数据
								$('#cyqyzgzx_yhzh').selectList({
									width: 337,
									optionWidth: 332,
									borderWidth: 1,
									borderColor: '#ccc',
									options:eval(options)
								}).change(function(e){
									var v = $(this).attr('optionValue');
									var _obj = v.split("|");
									$("#cyqyzgzx_qr_account").text(_obj[1]);
									$("#cyqyzgzx_qr_banke").text(_obj[2]);
									var areaStr = _obj[3];
									cacheUtil.syncGetRegionByCode(null,areaStr.split(":")[1],areaStr.split(":")[2],"",function(res){
										$("#cyqyzgzx_qr_area").text(res);
									});
								}).trigger("change");
								
								//上一步按钮
								$('#btn_cyqyzgzx_syb').click(function(){
									$('#cyqyzgzx_1').removeClass('none');
									$('#cyqyzgzx_2').addClass('none').html('');
								});
								
								//提交按钮
								$('#btn_cyqyzgzx_tj').click(function(){
									if (!self.validateData2()) 
									{
										return;
									}
									
									
									//上传文件  
									comm.uploadFile(null,['apply_file'],function(data){
										var entAllInfo = comm.getCache("companyInfo", "entAllInfo");
										if(entAllInfo == null){
											dataModoule.findEntAllInfo(null, function(response){
												comm.setCache("companyInfo", "entAllInfo", response.data);
												entAllInfo = response.data;
												self.commitData(entAllInfo,data,res.data.info.baseStatus);
											});
										}else{
											self.commitData(entAllInfo,data,res.data.info.baseStatus);
										}
									},function(err){
										
										
									},{});
									
								});
								
							});
										
							
						});
					});
				}
				
			});
		},
		commitData : function (entAllInfo,data,baseStatus){
			var param = comm.getRequestParams();
			var postData = {
					entCustId : param.entCustId,
					entResNo : param.hsResNo,
					entCustName:param.custEntName,		//账户名称
					applyReason : $("#cyqyzgzx_reason").val(), //申请原因
					oldStatus : baseStatus,
					entAddress : entAllInfo.entRegAddr,//企业地址
					linkman : entAllInfo.contactPerson,
					linkmanMobile : entAllInfo.contactPhone,
					bizApplyFile :data.apply_file,	//申请书id  返回的文件系统中的文件id
					bankAcctId : $("#cyqyzgzx_yhzh").attr("optionvalue").split("|")[0],
					createdBy : param.cookieOperNoName
					
			};
			//提交企业注销成员企业资格的申请
			systemBusiness.memberEnterpriseApplyQuit(postData,function(res2){
					//弹出申请提交成功提示框
					comm.alert(comm.lang("systemBusiness").apply_post_success);
					//回到成员企业资格维护
					$('#cyqyzgwh').trigger('click');
			});
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
				formID : '#cyqyzgzx_form',
				rules : {
					apply_file : {
						required : true
					},
					cyqyzgzx_reason : {
						required : true,
						maxlength:300
					}
				},
				messages : {
					apply_file : {
						required : comm.lang("systemBusiness").fileNotNull
					},
					cyqyzgzx_reason : {
						required : comm.lang("systemBusiness").reasonNotNull,
						maxlength:comm.lang("systemBusiness").reasonMax300
					}
				},
                errorPlacement: function(error, element) {
                    $(element).attr("title",$(error).text()).tooltip({
                            position:{
                                    my:"left+100 top+15",
                                    at:"left top+15"        
                            }
                    }).tooltip("open");
                    $(".ui-tooltip").css("max-width","230px");       
				},
				success:function(element){
				                $(element).tooltip();
				                $(element).tooltip("destroy");
				}
			});
		},
		validateData2 : function(){
			return comm.valid({
				formID : '#cyqyzgzxqr_form',
				rules : {
					cyqyzgzx_yhzh:{
						required:true
					}
				},
				messages : {
					cyqyzgzx_yhzh:{
						required:comm.lang("systemBusiness").cyqyzgwh_selectAccount
					}
				}
			});
		}
	};
});