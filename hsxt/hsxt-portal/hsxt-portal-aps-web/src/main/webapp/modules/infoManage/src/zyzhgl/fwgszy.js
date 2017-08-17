define(['text!infoManageTpl/zyzhgl/fwgszy.html',
		'text!infoManageTpl/zyzhgl/fwgszy_sz.html',
		'text!infoManageTpl/zyzhgl/fwgszy_sz_qr.html',
        'infoManageDat/infoManage',
		'infoManageLan'
		], function(fwgszyTpl, fwgszy_szTpl, fwgszy_sz_qrTpl, dataModoule){
	var self= {
		//self : null,
		showPage : function(){
			//self = this;
			self.initForm();
		},
		/**
		 *初始化界面 
		 */
		initForm : function(){
			$('#busibox').html(_.template(fwgszyTpl));
			
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			//绑定查询事件
			$('#queryBtn').click(function(){
				self.initData();
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			if(!comm.queryDateVaild("search_form").form()){
				return;
			}
			var params = {
					search_startDate:$("#search_startDate").val(),
					search_endDate:$("#search_endDate").val(),
					search_belongEntResNo:$("#search_belongEntResNo").val(),
					search_belongEntName:$("#search_belongEntName").val(),
					search_belongEntContacts:$("#search_belongEntContacts").val(),
					search_blongEntCustType:4
				};
			dataModoule.findResEntInfoList(params, self.detail);
		},
		/**
		 * 查看动作
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 0){//企业互生号
				return $('<a>'+ record['hsResNo'] +'</a>').click(function(e) {
					$('#busibox2').hide();
					$('#busibox').hide();	
					$('#ent_list').show();
					$('#ent_detail').show();
					require(['infoManageSrc/zyzhgl/sub_tab'], function(tab){
						comm.delCache("infoManage", "entAllInfo");
						tab.showPage('fwgs', record, record.custId);
					});
					comm.liActive_add($('#ckqyxxxx'));
				});
			}else if(colIndex == 6){//企业认证状态
				return comm.getNameByEnumId(record['realnameAuth'], comm.lang("infoManage").realNameAuthSatus);
			}else if(colIndex == 7){//企业状态
				return $('<a>设置</a>').click(function(e) {
					var data = {custID:record.custId};
					dataModoule.findBusinessPmInfo(data,function(res){
						var obj = res.data;
						self.set(record,obj);
					});
				});
			}
		},
		
		changVal:function(oldVal,id,vid,textId,oldInputValue){
			var text = $(textId).text();
			var flag = '0';
			var newVal=$("input[name='"+id+"']:checked").val();
			var newInputValue = $("#"+vid).val();
			if(oldVal!=newVal || newInputValue != oldInputValue){
				if("" == newInputValue && '1' == newVal){
					flag= '1';
					comm.error_alert(text + comm.lang("infoManage").mustbj);
				}else{
					flag = '2';
				}
			}
			return flag;
		},
		
		set : function(record,obj){
			$('#sz_dlg').html(_.template(fwgszy_szTpl,obj));
			
			//作一下校验，如果没有变更，则不需要输入
			var jfzhsbOld=$("input[name='jfzhsb']:checked").val();
			var jftzOld=$("input[name='jftz']:checked").val();
			var dhhsbOld=$("input[name='dhhsb']:checked").val();
			var hsbzhbOld=$("input[name='hsbzhb']:checked").val();
			var hbzyhOld=$("input[name='hbzyh']:checked").val();
			
			var jfzhsb_info_OldValue = $("#jfzhsb_info").val();
			var jftz_info_OldValue = $("#jftz_info").val();
			var dhhsb_info_OldValue = $("#dhhsb_info").val();
			var hsbzhb_info_OldValue = $("#hsbzhb_info").val();
			var hbzyh_info_OldValue = $("#hbzyh_info").val();
			
			var jfzhsb_status = '0';
			var jftz_status = '0';
			var dhhsb_status = '0';
			var hsbzhb_status = '0';
			var hbzyh_status = '0';
			/*弹出框*/
			$( "#sz_dlg" ).dialog({
				title:"业务操作许可设置",
				width:"550",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"确定":function(){
						
						
						//作一下校验，如果没有变更，则不需要输入
						jfzhsb_status = self.changVal(jfzhsbOld,"jfzhsb","jfzhsb_info","#jfzhsb_text",jfzhsb_info_OldValue);
						obj['jfzhsb_status'] = jfzhsb_status;
						if('1' == jfzhsb_status){
							return false;
						}
						jftz_status = self.changVal(jftzOld,"jftz","jftz_info","#jftz_text",jftz_info_OldValue);
						obj['jftz_status'] = jftz_status;
						if('1' == jftz_status){
							return false;
						}
						dhhsb_status = self.changVal(dhhsbOld,"dhhsb","dhhsb_info","#dhhsb_text",dhhsb_info_OldValue);
						obj['dhhsb_status'] = dhhsb_status;
						if('1' == dhhsb_status){
							return false;
						}
						hsbzhb_status = self.changVal(hsbzhbOld,"hsbzhb","hsbzhb_info","#hsbzhb_text",hsbzhb_info_OldValue);
						obj['hsbzhb_status'] = hsbzhb_status;
						if('1' == hsbzhb_status){
							return false;
						}
						hbzyh_status = self.changVal(hbzyhOld,"hbzyh","hbzyh_info","#hbzyh_text",hbzyh_info_OldValue);
						obj['hbzyh_status'] = hbzyh_status;
						if('1' == hbzyh_status){
							return false;
						}
						if('0' == jfzhsb_status && '0' == jftz_status && '0' == dhhsb_status && '0' == hsbzhb_status && '0' == hbzyh_status){
							comm.i_alert("业务未有任何变化");
							return ;
						}
						$( "#sz_dlg" ).dialog( "destroy" );
					//	$('#sz_qr_dlg').html(fwgszy_sz_qrTpl);
						$('#sz_qr_dlg').html(_.template(fwgszy_sz_qrTpl,obj));
						
						/*弹出框*/
						$( "#sz_qr_dlg" ).dialog({
							title:"业务操作许可设置确认",
							width:"550",
							height:"520",
							modal:true,
							closeIcon : true,
							buttons:{ 
								"确定":function(){
									var optionValue = $("#verificationMode").attr('optionValue');
									if(null == optionValue || '' == optionValue){
										comm.i_alert("请选择验证方式");
									}
									if(optionValue != '1' ){
										return;
									}
									$( "#sz_qr_dlg" ).dialog( "destroy" );
									comm.verifyDoublePwd($("#pk_double_username").val(), $("#pk_double_password").val(), 1, function(verifyRes){
										 var param = {};
										 param.custID = record.custId;
										 param.jfzhsb = $("input[name='jfzhsb']:checked").val();
										 param.jfzhsb_info = $("#jfzhsb_info").val();
										 param.jftz = $("input[name='jftz']:checked").val();
										 param.jftz_info = $("#jftz_info").val();
										 param.dhhsb = $("input[name='dhhsb']:checked").val();
										 param.dhhsb_info = $("#dhhsb_info").val();
										 param.hsbzhb = $("input[name='hsbzhb']:checked").val();
										 param.hsbzhb_info = $("#hsbzhb_info").val();
										 param.hbzyh = $("input[name='hbzyh']:checked").val();
										 param.hbzyh_info = $("#hbzyh_info").val();
										 dataModoule.setBusinessPmInfo(param, function(res){
                                                comm.yes_alert(comm.lang("infoManage").businessPermisonSetResult);
                                         });
									});
								},
								"取消":function(){
									 $( "#sz_qr_dlg" ).dialog( "destroy" );
								}
							}
						});
						/*end*/	
						
						/*下拉列表*/
						$("#verificationMode").selectList({
							width : 340,
							optionWidth : 345,
							options:[
								{name:'密码验证',value:'1'},
								{name:'指纹验证',value:'2'},
								{name:'刷卡验证',value:'3'}
							]
						}).change(function(e){
							var val = $(this).attr('optionValue');
							switch(val){
								case '1':
									$('#fhy_userName, #fhy_password').removeClass('none');
									$('#verificationMode_prompt').addClass('none');
									break;	
									
								case '2':
									$('#fhy_userName, #fhy_password').addClass('none');
									$('#verificationMode_prompt').removeClass('none');
									break;
									
								case '3':
									$('#fhy_userName, #fhy_password').addClass('none');
									$('#verificationMode_prompt').removeClass('none');
									break;
							}
						});
						/*end*/	
					
						/*end*/	
						$("#hsResNo").text(record.hsResNo);
						$("#enterpriseName").text(record.entCustName);
						$("#sysOpenDate").text(record.openDate);
						$("#tgqy_jfzhsb").text(comm.lang("infoManage").businessPermison[obj.PV_TO_HSB]);
						$("#tgqy_jftz").text(comm.lang("infoManage").businessPermison[obj.PV_INVEST]);
						$("#tgqy_dhhsb").text(comm.lang("infoManage").businessPermison[obj.BUY_HSB]);
						$("#tgqy_hsbzhb").text(comm.lang("infoManage").businessPermison[obj.HSB_TO_CASH]);
						$("#tgqy_hbzyh").text(comm.lang("infoManage").businessPermison[obj.CASH_TO_BANK]);
						
						$("#jfzhsb_new").text(comm.lang("infoManage").businessPermison[$("input[name='jfzhsb']:checked").val()]);
						$("#jftz_new").text(comm.lang("infoManage").businessPermison[$("input[name='jftz']:checked").val()]);
						$("#dhhsb_new").text(comm.lang("infoManage").businessPermison[$("input[name='dhhsb']:checked").val()]);
						$("#hsbzhb_new").text(comm.lang("infoManage").businessPermison[$("input[name='hsbzhb']:checked").val()]);
						$("#hbzyh_new").text(comm.lang("infoManage").businessPermison[$("input[name='hbzyh']:checked").val()]);
						
						$("#jfzhsb_new_info").text($("#jfzhsb_info").val());
						$("#jftz_new_info").text($("#jftz_info").val());
						$("#dhhsb_new_info").text($("#dhhsb_info").val());
						$("#hsbzhb_new_info").text($("#hsbzhb_info").val());
						$("#hbzyh_new_info").text($("#hbzyh_info").val());
						
					},
					"取消":function(){
						 $( "#sz_dlg" ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			
				
		}	
	};
	return self;
});