define(['text!areaPlatformTpl/sub/dqptxxwh/dqptxxwh.html',
		'text!areaPlatformTpl/sub/dqptxxwh/dqptxxwh_opt.html',
		'areaPlatformDat/plat'
		],function( dqptxxwhTpl, dqptxxwh_optTpl,plat ){
	var areaPlatformGrid = null;
	var areaPlatform = {
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(dqptxxwhTpl )) ;
			areaPlatform.loadGrid();
			areaPlatform.opt_query();
			areaPlatform.opt_add();	
		},
		//加载表格
		loadGrid:function(){
			areaPlatformGrid = comm.getCommBsGrid("dqptxxwh_ql",{domain:'gpf_res',url:'platList'},{},this.opt_edit,this.opt_sync);
		},
		//查询
		opt_query: function () {
            $("#dqptxxwh_tb_cx").click(function(){
				var params = {
						platNo : $("#platResNo_search").val(), //地区平台互生号
						entCustName : $("#platName_search").val()//地区平台名称
				};
				areaPlatformGrid.search(params);
			});
        },
		//验证
		validate : function(){
			return comm.valid({
				formID : '#dqptxxwh_optForm',
				left:200,
				rules : {
					dqptxxwh_opt_dqpt : {
						required : true	
					},
					entResNo : {
						required : true	
					},
					entCustName : {
						required : true	
					},
					emailA : {
						required : true	
					},
					emailB : {
						required : true	
					}
				},
				messages : {
					dqptxxwh_opt_dqpt : {
						required : '必填'
					},
					entResNo : {
						required : '必填'
					},
					entCustName : {
						required : '必填'
					},
					emailA : {
						required : '必填'
					},
					emailB : {
						required : '必填'
					}
				}
			});
		},
		//添加
        opt_add : function(){
        	$('#dqptxxwh_tb_xz').click(function(){
				$('#dqptxxwhDlg').html(dqptxxwh_optTpl);	
				/*弹出框*/
				$( "#dqptxxwhDlg" ).dialog({
					title:"新增地区平台信息",
					width:"1000",
					height:"300",
					modal:true,
					closeIcon : true,
					buttons:{ 
						"保存":function(){
							if (!areaPlatform.validate()) {
								return;
							}
							var data =$('#dqptxxwh_optForm').serializeJson();
							var loginInfo = comm.getLoginInfo();
							var optData = {
								createdOptId   : loginInfo.operatorId,
								createdOptName : loginInfo.name,
								updatedOptId   : loginInfo.operatorId,
								updatedOptName : loginInfo.name
							};
							plat.addPlatInfo($.extend(data,optData), function(response){
								if(response){
									comm.yes_alert("操作成功");
									$("#dqptxxwhDlg").dialog( "destroy" );
									$('#dqptxxwh_tb_cx').trigger('click');
								}
							});
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
						}
					}
				});
				/*end*/	
				$('#dqptxxwh_opt_dqptdm,#dqptxxwh_opt_dqpt,#dqptxxwh_opt_dqpthsh, #dqptxxwh_opt_dqptqymc,#dqptxxwh_opt_xtglyAllyx, #dqptxxwh_opt_xtglyBllyx').val('');
				
				areaPlatform.initSelectPlatList('#dqptxxwh_opt_dqpt').change(function(e){
					$('#dqptxxwh_opt_dqpt').val($(this).val());
					$('#dqptxxwh_opt_dqptdm').val($("#dqptxxwh_opt_dqpt").attr("optionvalue"));
					$('#dqptxxwh_opt_dqpthsh').val('00000000'+$("#dqptxxwh_opt_dqpt").attr("optionvalue"));
					$('#dqptxxwh_opt_dqptqymc').val($(this).val());
				});
        	});	
		},
		//修改
		opt_edit : function(record){
			if(record.syncFlag==false){
				var link = $('<a>修改</a>').click(function(e){
					$('#dqptxxwhDlg').html(dqptxxwh_optTpl);
					/*弹出框*/
					$( "#dqptxxwhDlg" ).dialog({
						title:"修改地区平台信息",
						width:"1000",
						height:"300",
						modal:true,
						closeIcon : true,
						buttons:{ 
							"保存":function(){
								if (!areaPlatform.validate()) {
									return;
								}
								var data =$('#dqptxxwh_optForm').serializeJson();
								var loginInfo = comm.getLoginInfo();
								var optData = {
									createdOptId   : loginInfo.operatorId,
									createdOptName : loginInfo.name,
									updatedOptId   : loginInfo.operatorId,
									updatedOptName : loginInfo.name
								};
								plat.updatePlatInfo($.extend(data,optData), function(response){
									if(response){
										comm.yes_alert("操作成功");
										$("#dqptxxwhDlg").dialog( "destroy" );
										$('#dqptxxwh_tb_cx').trigger('click');
									}
								});
							},
							"取消":function(){
								 $( this ).dialog( "destroy" );
							}
						}
					});
					$('#dqptxxwh_opt_dqpt').val(record.entCustName);
					$('#dqptxxwh_opt_dqptdm').val(record.platNo);
					$('#dqptxxwh_opt_dqpthsh').val(record.entResNo);
					$('#dqptxxwh_opt_dqptqymc').val(record.entCustName);
					$('#dqptxxwh_opt_xtglyAllyx').val(record.emailA);
					$('#dqptxxwh_opt_xtglyBllyx').val(record.emailB);
				}.bind(this)) ;
				return link;
			}
		},
		//同步数据
		opt_sync : function(record){
			if(record.syncFlag==false){	
				var link = $('<a>同步</a>').click(function(e) {
					comm.confirm({
		                width: 500,
		                imgFlag: true,
		                imgClass: 'tips_ques',
		                content: '确定同步地区平台[' + record.entCustName + ']到用户中心？',
		                callOk: function () {
		                	plat.syncPlatToUc({platNo:record.platNo},function(resp){
								if(resp){
									comm.yes_alert("操作成功");
									$('#dqptxxwh_tb_cx').trigger('click');
								}
							});
		                }
		            });
				}.bind(this));
				return link;
			}
		},
		/**
		 * 初始化下拉框
		 * @param objId 需要绑定的下拉框元素
		 * @param objArray 枚举内容（来源于国际化枚举对象）
		 * @param width 选择框宽度（可选参数）
		 * @param defaultVal 默认值（可选参数）
		 * @param defaultOptions 默认选项
		 */
		initSelectPlatList : function(objId, width, defaultVal, defaultOptions){
			var objArray = null;
			plat.unInitPlatList(null,function(response){
				objArray = response;
			});
			var options = [];
			if(defaultOptions){
				options.push(defaultOptions);
			}
			for(key in objArray){
				options.push({name:objArray[key].entCustName, value:objArray[key].platNo});
			}
			var content = {options : options};
			if(width){
				content.width = width;
				content.optionWidth = width;
			}
			var select = $(objId).selectList(content);
			if(defaultVal != null && defaultVal != undefined){
				select.selectListValue(defaultVal);
			}
			return select;
		}
	};
	return areaPlatform;
}); 

 