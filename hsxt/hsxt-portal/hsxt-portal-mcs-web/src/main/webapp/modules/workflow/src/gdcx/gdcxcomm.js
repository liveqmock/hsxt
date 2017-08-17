define(["workflowDat/workflow","commSrc/cacheUtil"], function(workflow){
	return {
		/** 获取业务类型 */
		getBizAuthList:function(callback){
			cacheUtil.getBizAuthList(function(data){
				var options = [];
				$(data).each(function(index,item){
					options.push({name:item.bizTypeName, value:item.bizType});
				});
				callback(options);
			});
		},
		/**加载业务类型 */
		initBizAuthList : function(){
			this.getBizAuthList(function(options){
				/*加载业务类型*/
				$("#service_type").selectList({
					options : options,
					optionWidth: 200,
					borderWidth: 1,
					borderColor: '#ccc'	
				});
			});
		},
		/** 获取业务类型名称 */
		getBizAuthName:function(bizType){
			var bizAuthName;
			cacheUtil.getBizAuthList(function(data){
				$(data).each(function(index,item){
					if(item.bizType==bizType){
						bizAuthName=item.bizTypeName;
						return;
					}
				});
			});	
			return bizAuthName;
		},
		/** 获取转入待办业务类型 */
		getBizTypeList:function(dataObj){
			var bizType=[];
		    for(var i=0;i<dataObj.length;i++){
		    	bizType.push(dataObj[i].bizType);
		    }
		    return bizType;
		},
		/** 获取值班员列表 */
		getAttendant:function(dataObj){
			var self=this;
			var param={"bizType":self.getBizTypeList(dataObj)};
			
			workflow.getAttendantList(param,function(rsp){
				$("#leftSelect").html("");
				$(rsp.data).each(function(i,v){
					$("#leftSelect").append("<li workno=\""+v.optWorkNo+"\" data-value=\""+v.optCustId+"\"><span>"+v.optWorkNo+"</span><span>"+v.operatorName+"</span></li>");			 
				});
			});
		},
		/** 获取已选择的值班员 */
		getSelectOper:function(){
			var oper=[];
			$("#rightSelect li").each(function(){
				oper.push($(this).attr("data-value"));
			});
			return oper;
		},
		/** 快捷日期控制 */
		quickDateChange:function(type){
			// 默认近一日
			var qd = comm.getTodaySE();
			
			if (type == "2") {
				// 近一周
				qd = comm.getWeekSE();
			}else if (type == "3") {
				// 近一月
				qd = comm.getMonthSE();
			}
			if (type == "4") { 
				// 近三月
				qd = comm.get3MonthSE();
			}

			$("#search_startDate").val(qd[0]);
			$("#search_endDate").val(qd[1]);
		},
		/** 
		 * 超出指定时间范围 
		 * @param time 与当前比较时间
		 * @param hour 超出时间(按小时)
		 */
		isOutTimeRange:function(time,outHour){
			var currentTime=new Date(); //当前时间
			var compareTime=new Date(Date.parse(time.replace(/-/g, '/'))); //比较时间
			
			//获取两个时间的小时数
			var hour=(new Date()-new Date(time))/3600000;
			if(hour>outHour){
				return true;
			}
			return false;
		},
		/** bsgrid行颜色控制 */
		bsgridRowControl:function(){
			var self=this;
			//根据紧急状态控制行背景
			$.fn.bsgrid.defaults.additionalAfterRenderGrid = function (parseSuccess, gridData, options) {
	            var tableId = options.gridId; //分页列表id
	            var assignedTime;  //催办时间
	           
	            //只针对办理中列表做控制
	            if(tableId=="searchTable_blz"){
	            	
		            $.each(gridData.data, function(name, value){
		            	var assignedTime=value.assignedTime; //获取派单办时间
		            	var bagColor=""; //背景颜色
		            	
		                if(value.redirectFlag==1){ //已转派显示蓝色
		                	bagColor="trbg_blue";
		                }
		                	
	                	//紧急工单
	                	if(value.priority==1){
	                		//派单时间2小时内=绿色
	            			if(!self.isOutTimeRange(assignedTime, 2)){
	            				bagColor="trbg_green";
	            			}else{//派单时间超过2小时=黄色
	            				bagColor="trbg_yellow";
	            			}
	                	}
	                	
	                	//普通工单
	                	if(value.priority==0){
	                		//派单超过24小时=红色
	                		if(self.isOutTimeRange(assignedTime, 24)){
	            				bagColor="trbg_red";
	            			}
	                	}
		                
		            	//添加行背景色
		            	if(bagColor!=""){
		            	 $("#searchTable_blz").children('tbody').children('tr').eq(name).addClass(bagColor);
		            	}
		            });
	            }
			}
		},
		/** 转入待办工单公共方法*/
		workFlowToDo:function(dataObj,WFBsgrid){
			var self=this;
			/*表格*/
			$.fn.bsgrid.init('zrdbTable', {				 
				pageAll : true ,
				stripeRows: true,
				localData : dataObj
			});
			
			//值班员选择框列表
			$('#leftSelect,#rightSelect').off('click','li');
			$('#leftSelect,#rightSelect').on('click','li',function(e){
				var theLi = $(this) ;
				theLi.siblings().removeClass('bg_ddd');
				theLi.addClass('bg_ddd');				
			});
			//双击选择值班员
			$('#leftSelect').on('dblclick','li',function(e){
				$("#toRight").click();	
			});
			//双击删除已选择值班员
			$('#rightSelect').on('dblclick','li',function(e){
				$("#toLeft").click();	
			});
			
			//左键按钮
		 	$('#toLeft').unbind("click").bind("click",function(){
		 		var theLi = $('#rightSelect >li[class="bg_ddd"]').clone() ;
		 		theLi.removeClass('bg_ddd');
		 		theLi.children('span').eq(0).css('display', 'inline-block'); 
				theLi.children('span').eq(2).css('display', 'none'); 
		 		$('#leftSelect').append(theLi); 
		 		$('#rightSelect >li[class="bg_ddd"]').remove(); 
		 	});
		 	//右键按钮
		 	$('#toRight').unbind("click").bind("click",function(){	
		 		var theLi = $('#leftSelect >li[class="bg_ddd"]').clone() ;
		 		theLi.removeClass('bg_ddd') ;
		 		theLi.children('span').eq(0).css('display', 'none');
				theLi.children('span').eq(2).css('display', 'inline-block'); 
		 		$('#rightSelect').append(theLi) ; 
		 		$('#leftSelect >li[class="bg_ddd"]').remove() ; 
		 	});
			
			/*派单模式操作*/
			$('#modeForm input[name="mode"]:radio').unbind().bind("change",function(){
				var val = $(this).val();	
				if(val == 2){
					$('#appoint_zby').removeClass('none');	
					//加载值班人员
					self.getAttendant(dataObj);
				}
				else{
					$('#appoint_zby').addClass('none');		
				}
			});
			
			/** 增加值班员搜索功能*/
		 	$("#txtOperSearch").keyup(function(){
		 		var $txtVal=comm.trim($(this).val()); //获取输入文本值
		 		var $defaultId="leftSelect li";  //员工列表控件id
		 		
		 		//为空时，显示所有工作人员
		 		if($txtVal=="")
		 		{
		 			$("#"+$defaultId).show();
		 		}else{
		 			$("#"+$defaultId+"[workno*='"+$txtVal+"']").show();
			 		$("#"+$defaultId).not($("#"+$defaultId+"[workno*='"+$txtVal+"']")).hide();
		 		}
		 	});
		 	
			/*弹出框*/
			$("#gd_dialog").dialog({
				title : "转入待办 ",
				width : "750",
				height : 'auto',
				modal : true,
				closeIcon : true,
				buttons : { 
					"派单":function(){
						var zrdbself=this;
						var taskIds=[];
						var operS=self.getSelectOper();
						var $type=$("#modeForm input[name='mode']:checked").val();	//派单类型
						
						//派单类型为“指定值班员”时判断是否指定值班员
						if($type=="2" && operS.length==0){
							comm.alert({imgClass: 'tips_i' ,content: comm.langConfig["workflow"].notSelectOperError });
							return false;
						}
						
						//获取转派工单
					    for(var i=0;i<dataObj.length;i++){
					    	taskIds.push(dataObj[i].taskId);
					    }
					    
						var param={
								"taskIdStr":encodeURIComponent(JSON.stringify(taskIds)),
								"assignedMode":$("input[name='mode']:checked").val(),
								"optCustIdStr": ($type=="2" ? encodeURIComponent(JSON.stringify(operS)) : "")
								};
						workflow.workOrderToDo(param,function(){
							comm.alert({imgClass: 'tips_yes' ,content:comm.lang("workflow")[22000],callOk:function(){
								$(zrdbself).dialog("destroy");
								WFBsgrid.refreshPage();
							}});
						});
						
					},
					"取消":function(){
						 $(this).dialog( "destroy" );
					}
				}
			});
			
		},
		/** 加载验证元素 */
		loadCheckWay:function(){
			/*验证方式*/
			comm.initSelect("#verificationMode",comm.lang("workflow").checkTypeEnum);
			$("#verificationMode").change(function(e){
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
					case '':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').addClass('none');
						break;
				}
			});
		},
		/** 表单提交验证 */
		fromVerification:function(){
			return comm.valid({
				formID : '#fTermination',
				rules : {
					txtReason : {
						required : true,
						maxlength :  300
					},
					verificationMode : {
						required : true,
					},
					txtCheckAccount : {
						required : function(){
							if($("#verificationMode").attr("optionvalue")=="1"){
								return true;
							}
							return false;
						}
					},
					txtCheckPwd : {
						required : function(){
							if($("#verificationMode").attr("optionvalue")=="1"){
								return true;
							}
							return false;
						}
					}
				},
				messages : {
					txtReason : {
						required :  comm.lang("workflow")[36048],
						maxlength : comm.lang("workflow").inp_len_err
					},
					verificationMode : {
						required : comm.lang("workflow").sel_chk_tp_err
					},
					txtCheckAccount : {
						required :  comm.lang("workflow")[34025]
					},
					txtCheckPwd : {
						required : comm.lang("workflow")[34026]
					}
				}
			});
		} 
	}	
});
