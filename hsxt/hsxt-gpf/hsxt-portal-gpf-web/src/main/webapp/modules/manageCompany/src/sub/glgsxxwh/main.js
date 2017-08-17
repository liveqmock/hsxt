define(['text!manageCompanyTpl/sub/glgsxxwh/glgsxxwh.html',
		'text!manageCompanyTpl/sub/glgsxxwh/glgsxxwh_opt.html',
		'manageCompanyDat/manage',
		'areaPlatformDat/plat'
		],function( glgsxxwhTpl, glgsxxwh_optTpl,manage,plat ){
		var platGrid = null;
		var manageGrid = null;
        var manageCompany = {
        	showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(glgsxxwhTpl )) ;
			manageCompany.loadGrid();
			manageCompany.opt_query_manage();
			manageCompany.opt_add_manage();	
		},
		//加载表格
		loadGrid:function(){
			manageGrid = comm.getCommBsGrid("glgsxxwh_ql",{domain:'gpf_res',url:'manageEntList'},{},this.opt_sync_uc,this.opt_sync_bs);
		},
		//查询管理公司
		opt_query_manage: function () {
            $("#glgsxxwh_tb_cx").click(function(){
            	var params = {
    					entResNo : $("#search_entResNo").val(), //管理公司互生号
    					entCustName : $("#search_entCustName").val()//管理公司名称
    			};
            	manageGrid.search(params);
			});
        },
        //新增
		opt_add_manage : function(){
			$('#glgsxxwh_tb_xz').click(function(){
				$('#glgsxxwhDlg').html(glgsxxwh_optTpl);	
				/*弹出框*/
				$("#glgsxxwhDlg").dialog({
					title:"新增管理公司信息",
					width:"1000",
					modal:true,
					closeIcon : true,
					buttons:{ 
						"保存":function(){
							if (!manageCompany.validate()) {
								return;
							}
							var rows = platGrid.getCheckedRowsRecords();
	                        if (rows && rows.length > 0) {
	                            var PlatMents = {};
	                            var loginInfo = comm.getLoginInfo();
								var flag = true;
	                            $.each(rows, function (index, record) {
	                            	PlatMents['platMentList[' + index +'].entResNo'] = $('#glgsxxwh_opt_glgshsh').val();
	                            	PlatMents['platMentList[' + index +'].entCustName'] = $('#glgsxxwh_opt_glgsmc').val();
	                            	PlatMents['platMentList[' + index +'].platNo'] = record.platNo;
									var initQuota = $.trim($('#initQuota'+record.platNo).val());
									if(!initQuota){
										flag = false;
										comm.alert({
											imgClass: 'tips_warn',
											content: record.entCustName+"对应的计划内配额数为空"
										});
										return;
									}
	                            	PlatMents['platMentList[' + index +'].initQuota'] = initQuota;
									var email = $.trim($('#email'+record.platNo).val());
									if(!email){
										flag = false;
										comm.alert({
											imgClass: 'tips_warn',
											content: record.entCustName+"对应的管理公司邮箱为空"
										});
										return;
									}
	                            	PlatMents['platMentList[' + index +'].email'] = email;
	                            	PlatMents['platMentList[' + index +'].createdOptId'] = loginInfo.operatorId;
	                            	PlatMents['platMentList[' + index +'].createdOptName'] = loginInfo.name;
	                            	PlatMents['platMentList[' + index +'].updatedOptId'] = loginInfo.operatorId;
	                            	PlatMents['platMentList[' + index +'].updatedOptName'] = loginInfo.name;
	                            });
								if(flag) {
									manage.addPlatMent(PlatMents, function(response){
										if(response){
											comm.yes_alert("操作成功");
											$("#glgsxxwhDlg").dialog( "destroy" );
											$('#glgsxxwh_tb_cx').trigger('click');
										}
									});
								}
	                        } else {
	                            comm.alert({
	                                imgClass: 'tips_warn',
	                                content: '请选择关联地区平台!'
	                            });
	                        }
						},
						"取消":function(){
							 $("#glgsxxwhDlg").dialog( "destroy" );
						}
					}
				});
				$('#glgsxxwh_opt_glgshsh, #glgsxxwh_opt_glgsmc').val('');
				platGrid = comm.getCommBsGrid("glgsxxwh_opt_ql",{domain:'gpf_res',url:'platList'},{},manageCompany.opt_edit_quota);
			});
		},
		
		opt_edit_quota : function(record, rowIndex, colIndex, options){
			var opt_text; 
				if(colIndex == 4){
					opt_text = $('<input type="text" id="email'+record.platNo+'" name="email" value="" class="tr" style="text-align:left;">').bind(this);
				}else if (colIndex == 5){
					opt_text = $('<input type="text" id="initQuota'+record.platNo+'" name="initQuota" value="" class="tr" style="text-align:left;" maxlength="3">').bind(this);
				}
			return opt_text;
		},
		//同步数据到用户中心
		opt_sync_uc : function(record){
			if(record.ucSync==false){	
				var sync_uc_link = $('<a>同步UC</a>').click(function(e) {
					comm.confirm({
		                width: 500,
		                imgFlag: true,
		                imgClass: 'tips_ques',
		                content: '确定同步管理公司[' + record.entCustName + ']到用户中心？',
		                callOk: function () {
		                	manage.syncManageToUc({platNo:record.platNo,entResNo:record.entResNo},function(resp){
								if(resp){
									comm.yes_alert("操作成功");
									$('#glgsxxwh_tb_cx').trigger('click');
								}
							});
		                }
		            });
				}.bind(this));
				return sync_uc_link;
			}
		},
		//同步数据到业务系统
		opt_sync_bs : function(record){
			if(record.bsSync==false){	
				var sync_bs_link = $('<a>同步BS</a>').click(function(e) {
					comm.confirm({
		                width: 500,
		                imgFlag: true,
		                imgClass: 'tips_ques',
		                content: '确定同步管理公司[' + record.entCustName + ']到业务系统？',
		                callOk: function () {
		                	manage.syncManageToBs({platNo:record.platNo,entResNo:record.entResNo},function(resp){
								if(resp){
									comm.yes_alert("操作成功");
									$('#glgsxxwh_tb_cx').trigger('click');
								}
							});
		                }
		            });
					
				}.bind(this));
				return sync_bs_link;
			}
		},
		validate : function(){
			return comm.valid({
				formID : '#glgsxxwh_optForm',
				top: -3,
				left : 200,
				rules : {
					entResNo : {
						required : true	
					},
					entCustName : {
						required : true	
					}
				},
				messages : {
					entResNo : {
						required : '管理公司互生号不能为空'
					},
					entCustName : {
						required : '管理公司名称不能为空'
					}
				}
			});
		}
	};
	return manageCompany;
}); 

 