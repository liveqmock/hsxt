define(['text!userpasswordTpl/jymmcz/qyjymmczshlist.html',
		'text!userpasswordTpl/jymmcz/qyjymmczsh_dialog.html',
		'text!userpasswordTpl/jymmcz/qyjymmczsh_ck.html',
		"userpasswordDat/dlmmcz/userpwd",
		'systemManageDat/systemManage'
		], function(qyjymmczshTpl, qyjymmczsh_dialogTpl,qyjymmczsh_ckTpl,userpwd,systemManage){
	return {
		showPage : function(){
			$('#busibox').html(_.template(qyjymmczshTpl));	
			var self = this; 
			//首次加载
			   self.pageQuery(false);
			   //查询
			   $("#btnQuery").click(function(){
				   self.pageQuery(true);
			   });
		},
		/** 分页查询 */ 
		pageQuery:function(autoLoad){
			var self = this; 
			var entResNo=$("#entResNo").val();
			var entCustName=$("#entCustName").val();
			if(entResNo==null||entResNo=="undefined"){
				entResNo="";
			}
			if(entCustName==null||entCustName=="undefined"){
				entCustName="";
			}
			var param = {"search_entResNo":entResNo,"search_entCustName":entCustName};
			var gridObj=userpwd.listentjylist_pwd(autoLoad,param,function(record, rowIndex, colIndex, options){
				
				var link1 = null;
				if(colIndex == 1){
					link1 = $('<a>' + gridObj.getColumnValue(rowIndex, 'entResNo') + '</a>').click(function(e) {
							var obj = gridObj.getRecord(rowIndex);
							self.chaKan1(obj);
						}.bind(this) ) ;
				}
				if(colIndex == 5){
					record['statusStr'] = comm.getNameByEnumId(record['status'], {0:'待审核',1:'通过',2:'驳回'});
					return comm.getNameByEnumId(record['status'], {0:'待审核',1:'通过',2:'驳回'});
				} 
				
				if(colIndex == 6){
					if(record['status']==0){
						return comm.formatDate(record['applyDate'],'yyyy-MM-dd hh:mm:ss ');
					}else{
						return comm.formatDate(record['updatedDate'],'yyyy-MM-dd hh:mm:ss ');
					}
				} 
				
				
				if(colIndex == 7){
					    link1 = $('<a>查看</a>').click(function(e) {
					   var obj = gridObj.getRecord(rowIndex);
						self.chaKan(obj);
					}.bind(this) ) ;
				}
				return link1;
				
				
				
			});
		},
		
		chaKan1 : function(obj){
			comm.liActive_add($('#ckqyxxxx'));
			$('#busibox').addClass('none');
			$('#ent_detail').removeClass('none');
			$("#ent_ResNo").val(obj.entResNo);
			$('#xtzcxx').click();
		},
		
		chaKan : function(obj){
			
			var params={
					resNo:obj.entResNo	
			}
			userpwd.findAllByResNo(params,function(res){
				
				var allInfor=res.data;
				var statusInfo=allInfor.statusInfo;
				var baseInfor=allInfor.baseInfo;
				var objdata=allInfor.mainInfo;
				obj.openDate=comm.formatDate(statusInfo.openDate,'yyyy-MM-dd hh:mm:ss');
				obj.applyDate=comm.formatDate(obj.applyDate,'yyyy-MM-dd');
				
				//获取操作员
				cacheUtil.searchOperByCustId(obj.createdby, function(res1){
					
					obj.createdbyDetail = res1.userName + "(" + res1.realName + ")";
					$('#qyjymmczsh_ck').html(_.template(qyjymmczsh_ckTpl, obj));
					$("#downloadId").attr("href", comm.getFsServerUrl(obj.applyPath)+"&useSavedFileName=true&fileName=交易密码重置申请函");
				});
			});	
			
			
			
			//obj.applyDate=comm.formatDate(obj.applyDate,'yyyy-MM-dd hh:mm:ss ');
			
		
			/*弹出框*/	
			$( "#qyjymmczsh_ck").dialog({
				title:"企业交易密码重置审核查看",
				modal:true,
				width:"700",
				closeIcon : true,
				buttons:{ 
					"关闭":function(){
						$( this ).dialog( "destroy" );
		           }
				}
		   });
		},
		shenHe : function(obj){
			$('#dialogBox').html(_.template(qyjymmczsh_dialogTpl, obj));	
			
			/*弹出框*/	
			$( "#dialogBox").dialog({
				title:"企业交易密码重置审核",
				modal:true,
				width:"700",
				height:"560	",
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
						
						var confirmObj = {	
							imgFlag:true  ,             //显示图片
							content : '交易密码重置授权码已发送成功！',			 
							callOk :function(){
													
							}
						}
						comm.confirm(confirmObj);
		
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
						 
						 var confirmObj = {	
							imgFlag:true  ,             //显示图片
							content : '此次申请失效！',			 
							callOk :function(){
													
							}
						}
						comm.confirm(confirmObj);
					}
				}
			
			});
			/*end*/
			
			/*下拉列表*/
			$("#verificationMode_qy").selectList({
				width : 180,
				optionWidth : 185,
				options:[
					{name:'密码验证',value:'1'},
					{name:'指纹验证',value:'2'},
					{name:'刷卡验证',value:'3'}
				]
			}).change(function(e){
				var val = $(this).attr('optionValue');
				switch(val){
					case '1':
						$('#fhy_userName_qy, #fhy_password_qy').removeClass('none');
						$('#verificationMode_prompt_qy').addClass('none');
						break;	
						
					case '2':
						$('#fhy_userName_qy, #fhy_password_qy').addClass('none');
						$('#verificationMode_prompt_qy').removeClass('none');
						break;
						
					case '3':
						$('#fhy_userName_qy, #fhy_password_qy').addClass('none');
						$('#verificationMode_prompt_qy').removeClass('none');
						break;
				}
			});
			/*end*/		
		}
	}	
});