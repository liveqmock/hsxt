define(["workflowDat/workflow","workflowLan"], function(workflow){
	var scheduleFun = {
		/**
		 * 新增值班组
		 */
		addGroup : function(callBack){
			var validObj = {
					left:100,   
				    top:5 ,    
				    formID:'#fm_rcap_xzgzz',    
				    rules:{
				            rcap_zzzmc : {
				                required : true ,
				                maxlength :  10 
				            } 
				    },
				    messages:{
				            rcap_zzzmc : {
				                required : comm.langConfig["workflow"].TS0023,
				                maxlength : comm.langConfig["workflow"].TS0001
				            } 
				    } 
			} ; 
			
			//新增数据格式验证
			if (comm.valid(validObj)  && scheduleFun.checkGroup($("#rcap_zby li"))){
				
				//获取值班组组员
				var menbers=scheduleFun.getSelectMember($("#rcap_zby li"));
				
				//拼接参数
				var addParam={
						"memberJson":encodeURIComponent(JSON.stringify(menbers)),
						"groupName":$("#rcap_zzzmc").val(),"groupType":$("input[name='RgroupType']:checked").val()
				};
			
				//工作组新增
				workflow.groupAdd(addParam,function(rsp){
					callBack();
				});
			}  
		
		},
		/**
		 * 值班组修改
		 */
		updateGroup:function(callBack){
			var $groupId=$('#rcap_tab li[class="cur"]').attr("data-id");	//工作组编号
			var   validObj = {
					left:100,   
				    top:5 ,    
				    formID:'#fm_rcap_bjgzz_update',    
				    rules:{
				    		rcap_bjzmc:{
				                required : true ,
				                maxlength :  10 
				            } 
				    },
				    messages:{
				    		rcap_bjzmc : {
				                required : comm.langConfig["workflow"].TS0023 ,
				                maxlength : comm.langConfig["workflow"].TS0001
				            } 
				    }
			} ; 
			
			//修改数据格式验证
			if (comm.valid(validObj) && scheduleFun.checkGroup($("#rcap_zby2 li"))){
				var menbers=scheduleFun.getSelectMember($("#rcap_zby2 li"));	//获取值班组组员
				
				//拼接参数
				var updateParam={
						"memberJson":encodeURIComponent(JSON.stringify(menbers)),
						"groupId":$groupId,"scheduleId":self.scheduleId,
						"groupName":$("#rcap_bjzmc").val(),"groupType":$("input[name='updateRgroupType']:checked").val()
				};
			
				//值班组修改
				workflow.groupUpdate(updateParam,function(rsp){
					$("#rcap_tab [class='cur']").text($("#rcap_bjzmc").val());	//重新加载更新后的值班组名
					$("#rcap_tab [class='cur']").click();						//重新加载值班组
					callBack();
				});
			}
		},
		/**
		 * 移除值班员业务节点
		 */
		deleteBizType:function(callBack){
			var $rigthBizType=$("#selExitsBizAuth option:checked");
	 		if (!$rigthBizType.length){
	 			comm.alert({imgClass:'tips_i',content: comm.langConfig["workflow"].TS0039 });
	 			return;
	 		}	
	 		
	 		var $optCustId=$("#hidOptCustId").val();											//删除操作员对象
	 		var bizType=scheduleFun.getMemberOperatorBizTypeArray($rigthBizType,$optCustId);	//业务权限节点
	 		var deleteParam={"bizTypeJson":encodeURIComponent(JSON.stringify(bizType))};		//业务权限对象转换成字符串
	 		
	 		//删除业务节点
	 		workflow.deleteBizType(deleteParam,function(rsp){
	 			
	 			//将删除的业务节点添加到业务清单列表框
	 			$rigthBizType.each(function(){
	 				if($("#selBizAuth option[value='"+$(this).val()+"']").length==0)
	 					{$('#selBizAuth').append($(this).clone());}
	 				$(this).remove() ; 
	 			});
	 		});
		},
		/**
		 * 新增值班员业务节点
		 */
		addBizType : function(callBack){
			var $leftBizType=$("#selBizAuth option:checked");	//获取待新增业务节点
	 		if (!$leftBizType.length){ 
	 			comm.alert({imgClass:'tips_i',content: comm.langConfig["workflow"].TS0038   }) ;
	 			return;
	 		}	 
	 		
	 		var $optCustId=$("#hidOptCustId").val();															//新增业务权限操作员对象
	 		var bizType=scheduleFun.getMemberOperatorBizTypeArray($("#selBizAuth option:checked"),$optCustId);	//待新增业务权限json对象
	 		var addParam={"bizTypeJson":encodeURIComponent(JSON.stringify(bizType))};							//json对象转字符串
	 		
	 		//增加业务权限
	 		workflow.addBizType(addParam,function(rsp){
	 			//循环添加业务节点
	 			$leftBizType.each(function(){
	 				if($("#selExitsBizAuth option[value='"+$(this).val()+"']").length==0)
	 					{$('#selExitsBizAuth').append($(this).clone());}
	 				$(this).remove() ; 
	 			});
	 		});
		},
		/**
		 * 移除值班员
		 * @param clerkNames 值班员集合
		 */
		removeOperator : function(clerkNames,rmParam,callBack){
			//保留最后一个值班员
			if(clerkNames.length==1){
				comm.alert({imgClass: 'tips_i' ,content: comm.lang("workflow")["TS0036"]});		
				return false;
			}
			//移除	
			 workflow.removeOperator(rmParam,function(rsp){
				 callBack(rsp);
			 });
		}
		,
		/**
		 * 值班组启停状态修改
		 * @param status 1:开启、0:停用
		 */
		udpateGroupStatus:function(status,callBack){
			var confirmObj = { 
					content: (status==1 ? comm.langConfig["workflow"].TS0028 : comm.langConfig["workflow"].TS0015), 
	 			   	imgFlag:true,
	 			   	ok :'确定',      
					cancel:'取消',   
					imgClass: 'tips_i' ,
					callOk : function(){
						var $groupId=$("#rcap_tab [class='cur']").attr("data-id");	//值班组编号
						var param={"groupId":$groupId,"opened":status};				//请求参数
						
						//修改值班组状态
						workflow.udpateGroupStatus(param,function(rsp){
							callBack();
						});
					} 
				} 
	       comm.confirm(confirmObj);
		},
		/**
		 * 保存临时值班计划
		 * @param memberShedule 排班计划
		 */
		saveSchedule : function(memberShedule,callBack){
			//空数据验证
			if(memberShedule==null || memberShedule.length==0){
				comm.alert({imgClass: 'tips_i' ,content: comm.langConfig["workflow"].TS0029 });
				return false;
			}
			
			//保存值班计划
			var saveParam={"scheduleJson":encodeURIComponent(JSON.stringify(memberShedule))};
			workflow.saveSchedule(saveParam,function(rsp){
				callBack(rsp);
			});
		},
		/**
		 * 暂停值班计划
		 * @param scheduleId 值班计划编号
		 */
		pauseSchedule : function(groupId,callBack){
    		var confirmObj = { 
			   content: comm.langConfig["workflow"].TS0018  , 
 			   imgFlag:true,
				ok :'确定',      
				cancel:'取消',   
				imgClass: 'tips_i' ,
				callOk : function(){
	    			//暂停值班计划	
					workflow.pauseSchedule({"groupId":groupId},function(rsp){
						callBack(rsp);
					});
				} 
			 } 
    		comm.confirm(confirmObj );
		},
		/**
		 * 执行值班计划
		 */
		executeSchedule : function(param,callBack){
			var confirmObj = { 
        			content: comm.langConfig["workflow"].TS0020,
				   	imgFlag:true,
					ok :'确定',      
					cancel:'取消',   
					imgClass: 'tips_i' ,
					callOk : function(){
						//执行
						workflow.executeSchedule(param,function(rsp){
							callBack(rsp);
		    			});
					} 
				 } 
        		comm.confirm(confirmObj);
		},
		/**
		 * 验证值班组(新增、修改)数据
		 */
		checkGroup:function(menmberList){
			
			//获取已选择的员工总和
			var $menberNum=menmberList.length; 
			if($menberNum==0){
				comm.alert({imgClass: 'tips_i' ,content: comm.lang("workflow")[33326] });		
				return false;
			}
			
			//获取员工为主任总和
			var $dutyOfficer= menmberList.find("input[type='checkbox']:checked").length;
			if($dutyOfficer==0){
				comm.alert({imgClass: 'tips_i' ,content: comm.lang("workflow")[33327] });		
				return false;
			}
			return true;
		},
		/**
		 * 获取已选择的值班员=== 针对值班组新增和修改
		 */
		getSelectMember:function(selectMenber){
			var menbers=new Array();
			
			//循环获取已选择的值班员
			selectMenber.each(function(){
				var $optCustId=$(this).attr("data-value");
				var $chief=($(this).find("input[type='checkbox']:checked").val()==undefined?false:true);
				menbers.push({"optCustId":$optCustId,"operatorName":$(this).find("span:eq(1)").text(),"optWorkNo":$(this).attr("workNo"),"chief":$chief});
			});
			
			return menbers;
		},
		/**
		 * 获取值班员(新增、移除)业务数组
		 */
		getMemberOperatorBizTypeArray:function(mbt,optCustId){
			var bta=new Array();
			
			mbt.each(function(){
				bta.push({"bizType":$(this).val(),"optCustId":optCustId});
			});
			return bta;
		},
		/**
		 * 加载值班组组员明细
		 */
		loadAttendantInfo:function(param){
			//清空值班员信息
			$("#fm_rcap_bjzby td span").text("");
			$("#hidOptCustId").text("");
			$("#selExitsBizAuth").html("");
		
			workflow.getAttendantInfo(param,function(rsp){
				//判断数据存在
				if(rsp.data.operatorInfo.length==0){
					comm.alert({imgClass: 'tips_i' ,content: comm.lang("workflow")[23801] });		
					return;
				}
				
				//加载值班组组员明细
				scheduleFun.loadAttendantHtml(rsp.data,param.optCustId);
			});
		},
		/**
		 * 工作组组员HTML赋值
		 */
		loadAttendantHtml:function(data,optCustId){
			//获取值班员
			var operator=data.operatorInfo[0];
			$("#hidOptCustId").val(optCustId);
			$("#rcap_clerkName").html(operator.operatorName);
			$("#rcap_groupName").html(operator.groupName);
			$("#sOnDuty").html(data.workType==null || data.workType=="null"?"":data.workType);
			$("#sOffNum").html(data.restNum);
			$("#sWorkNo").html(operator.optWorkNo);
			$("#rcap_groupType").html(comm.lang("workflow").groupTypeEnum[operator.groupType]);
			
			// 加载组员拥有的业务类型
			var authList=data.authList;
			$(authList).each(function(i,v){
				$("#selExitsBizAuth").append("<option value=\""+v.bizType+"\">"+v.bizTypeName+"</option>");
			});
			
			//加载业务类型
			scheduleFun.loadBizAuthList(optCustId);
		},
		/**
		 * 加载值班员对应业务权限
		 * @param 操作号
		 */
		loadBizAuthList:function(optCustId){
			workflow.getBizTypeList({"optCustId":optCustId},function(rsp){
				var bizTypeList=rsp.data;
				//清空值
				$("#selBizAuth").html("");
				$.each(bizTypeList,function(i,v){
					//过滤值班员已存在的业务权限
					if($("#selExitsBizAuth option[value='"+v.bizType+"']").length==0){
						$("#selBizAuth").append("<option value=\""+v.bizType+"\">"+v.bizTypeName+"</option>");
					}
				});
			});
		},
		/**
		 * 加载值班组详情
		 * @param groupId 值班组编号
		 */
		loadGroupInfo:function(groupId){
			workflow.getGroupInfo({"groupId":groupId},function(rsp){
				//判断数据存在
				if(rsp.data.length==0){
					comm.alert({imgClass: 'tips_i' ,content: comm.lang("workflow")[23801] });		
					return;
				}
				//加载值班组
				scheduleFun.loadGroupMember(rsp.data[0]);
			});
		},
		/**
		 * 加载值班组显示内容
		 */
		loadGroupMember:function(group){
			$("#rcap_bjzmc").val(group.groupName);
			$("input[name='updateRgroupType'][value='"+group.groupType+"']").attr("checked",true); 
			$("#rcap_zby2,#rcap_yglb2").html("");
			
			//加载值班员工列表
			workflow.getEntOperList({},function(rsp){ 
				$("#rcap_yglb2").html("");
				$(rsp.data).each(function(i,v){
					$("#rcap_yglb2").append("<li workNo=\""+v.workNo+"\" data-value=\""+v.optCustId+"\"><span>"+v.workNo+"</span><span>"+ comm.navNull(v.operatorName)+"</span><span style=\"display:none;\"><input type=\"checkbox\"></span></li>");
					
				});
				
				//值班组组员组员
				$(group.operators).each(function(i,v){
					$("#rcap_zby2").append("<li workno=\""+v.optWorkNo+"\" data-value=\""+v.optCustId+"\" class=\"\"><span style=\"display: none;\">"+v.optWorkNo+"</span><span>"+comm.navNull(v.operatorName)+"</span><span style=\"display: inline-block;\"><input "+(v.chief==true?"checked=\"checked\"":"")+"  type=\"checkbox\"></span></li>");
					$("#rcap_yglb2 li[workno=\""+v.optWorkNo+"\"]").remove();
				});
			});
			
		},
		/**
		 * 获取值班组下值班主任信息
		 */
		getMembersCheifInfo:function(operationList, currGroup){
			var chiefNum=0;								//值班主任总数
			var currCustChief=false;					//当前操作员是否值班主任
			var groupOneCheif;							//获取值班组中的一个值班主任
			var currCustId = comm.getCookie("custId");	//当作操作员客户号
			
			//遍历值班组员
			$.each(operationList,function(i,v){
				if(v.chief){
					chiefNum++;
					groupOneCheif=v.id;
					if(currCustId==v.id && currGroup.opened!=0)
						currCustChief=true;
				}
			});
			
			return {"chiefNum":chiefNum,"currCustChief":currCustChief,"groupOneCheif":groupOneCheif };
		},
		/**
		 * 当前操作员是否管理员
		 */
		currOptIsAdmin:function(){
			var user = comm.navNull(comm.getCookie("userName"));	//获取管理员帐号
			return user=="0000";
		}
	}	
	
	return scheduleFun;
});
