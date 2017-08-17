define(['text!workflowTpl/rcap/rcap.html',
        'text!workflowTpl/rcap/rcap_dialog.html',
        'zrender', 
        'zrender/shape/Rectangle' ,
        'zrender/shape/Line',
        'zrender/shape/Text',
        "workflowDat/workflow",
        "workflowSrc/rcap/schedule",
        'workflowLan'], 
function(rcapTpl,rcap_dialogTpl,zrender, RectangleShape,LineShape,TextSharp,workflow,scheduleComm){
	var rcapFun = {	
		groupOneCheif:null,						//获取值班组中的一个值班主任
		currOptIsCheif:false,					//当前用户值班主任状态， true:是， false:否
		currGroupCheifNum:null,					//当前值班组值班主任个数
		scheduleId: null,						//值班计划编号
		scheduleStatus:null,					//值班计划状态 2：启动、3：暂停
		currYear  : new Date().getFullYear(),
		currMonth : new Date().getMonth()+1,
		dayCount  : null,
		personCount:null,						//值班组组员总数
		groupNames : null,						//值班组数组集合
		clerkNames : null ,						//值班组组员数组集合
		membersSchedule: null ,					//值班组所有组员值班集合
		schedules : null ,	
		scheduleClass : [] ,	
		scheduleEdit : [] ,
		serialNoArr: [] ,
		nameArr   : [] ,
		restArr   :[]  ,
		restDays : []  ,
		canvasHeight:null,
		offset:0.5,
		zr:null,
		selectedClassRect: null ,
		selectedEditRect: null ,
		rectangle : {} ,	
		editable  : true ,	 
		
		showPage : function(){
			$('#busibox').html(_.template(rcapTpl+rcap_dialogTpl)); 
			var self = this ; 
			document.oncontextmenu = function (){
				return false ;
			}
		    //加载分组
		    self.getGroup();
		},
		initTab : function(){
			$('#rcap_tab').empty();
			$('#rcap_tab').unbind();
			if(this.groupNames.length>0){
				this.groupNames.forEach(function(el,key){
					$('#rcap_tab').append('<li data-id="'+(el.id)+'" class="'+(key==0?'cur':'')+'" >'+el.name+'</li>') ;
				}) ;
			}
			$('#rcap_tab').append('<li data-id="'+( $('#rcap_tab >li').length +1 )+'"  >+</li>') ;
			
			//加载值班组滚动条
			comm.tab_scroll($('#rcap_tab'));
			
		} ,
		renderTable : function(){
			this.zr.clear();					 
		 	this.createRectangle();			   
			this.setYearMonth() ;
			 
			this.setTableDate(); 
			this.initScheduleClass();	 
						
			this.createGrid();	
			this.createSerial();		
			this.createName();
			this.createTexts();	
			this.createRest();
			this.zr.render();		
		} ,
		
		buttonBind : function(){
				var self=this;
				$("#rcap_tab").off("click", "li");
				//值班组标签切换
				$('#rcap_tab').on('click' , 'li' , function(){
					var liValue = $(this).data('value') ;
					$('#rcap_zzzmc,#txtJobNumber_add').val('');
					if ($(this).text()=='+'){
						//是否管理员
						var adminBool = scheduleComm.currOptIsAdmin();
						
						//当前操作是否值班主任
						workflow.isChief(function(rsp){
							//非值班主任或管理员提示
							if(!rsp.data && !adminBool){
								comm.alert({imgClass: 'tips_i' ,content: comm.lang("workflow")[33340]});		
								return false;
							}
						
							//加载值班员工列表，先清空已存在的
							$("#rcap_zby").html("");
							self.getAttendant();
							
							//显示创建对话框
							$( "#rcap_xzgzz" ).dialog({
								title: comm.langConfig["workflow"].TS0026 ,		 
								width:"650" ,
								buttons:{ 
									"创建":function(){ 
										var dself=this;
										
										//值班组新增
										scheduleComm.addGroup(function(){
											$(dself).dialog("destroy");
											$("#040500000000_subNav_040502000000").click();//重新加载工作组
										});
									},
									"取消":function(){
										 $(this).dialog( "destroy" );
									}
								}		
							  }) ;
							
						});
					} else {
						//切换标签
						$(this).addClass('cur');
						$(this).siblings().removeClass('cur');
						
						//获取值班组组员
						self.getMembers($("#rcap_tab [class='cur']").attr("data-id"));
					}	 
				}) ; 
				
				//开启
				 var self = this ;
				 $('#zr_stop').unbind("click").bind("click",function(e){
					//功能权限验证
					if(!self.operChk()){return false;}
					
		        	if ($(this).attr('src') == 'resources/img/rcap/stop2.png') {	        	 
		        		//开启值班组
		        		/*scheduleComm.udpateGroupStatus(1,function(){
		        			self.controlButton(1);
							comm.alert({imgClass: 'tips_i' ,content: comm.langConfig["workflow"].TS0030 });
		        		});*/
		        	} else {	 
		        		//停用值班组
		        		scheduleComm.udpateGroupStatus(0,function(){
		        			self.controlButton(0);
							comm.alert({imgClass: 'tips_i' ,content: comm.langConfig["workflow"].TS0031 });
		        		});
		        	}
		        }); 
				
				//保存临时值班计划
				$('#zr_save').unbind("click").bind("click",function(e){	
					//功能权限验证
					if(!self.operChk()){return false;}
					
					//值班员排班计划
					var memberShedule=self.getShedule();
					
					//保存值班计划
					scheduleComm.saveSchedule(memberShedule,function(rsp){
						self.scheduleId=rsp.data.scheduleId;
						comm.alert({imgClass: 'tips_i' ,content: comm.langConfig["workflow"].TS0017 });		
			        	$('#zr_edit').attr('src' , 'resources/img/rcap/edit.png');
					});
					
		        }); 
				
				//暂停
				$('#zr_pause').unbind("click").bind("click",function(e){
					//功能权限验证
					if(!self.operChk()){return false;}
					
		        	if ($(this).attr('src') == 'resources/img/rcap/pause2.png'  ) {	        	 
		        		comm.alert({imgClass: 'tips_i' ,content: comm.langConfig["workflow"].TS0019 });			
		        	} else {
		        		//值班组编号
		        		var $groupId=$("#rcap_tab [class='cur']").attr("data-id");
		        		
		        		//暂停值班计划
		        		scheduleComm.pauseSchedule($groupId,function(rsp){
		        			$('#zr_pause').attr('src' , 'resources/img/rcap/pause2.png') ;
	        				$('#zr_start').attr('src' , 'resources/img/rcap/start.png') ;
	        				$('#zr_stop').attr('src' , 'resources/img/rcap/stop.png') ;
							comm.alert({imgClass: 'tips_i' ,content: comm.langConfig["workflow"].TS0032 });
		        		});
		        	}
		        }); 
		        
		        //执行值班计划
				$('#zr_start').unbind("click").bind("click",function(e){
					//功能权限验证
					if(!self.operChk()){return false;}
					
					//获取值班计划
					var menbers=self.getShedule();
					
					//值班计划编号
					var psParam={"scheduleId":self.scheduleId,"groupId":$("#rcap_tab [class='cur']").attr("data-id"),
							"year":self.currYear,"month":self.currMonth, "scheduleOptsJson":encodeURIComponent(JSON.stringify(menbers))
							};
					
					//执行值班计划
					scheduleComm.executeSchedule(psParam,function(rsp){
						//重新绑定值班计划编号
						self.scheduleId=rsp.data.scheduleId;
    				    $('#zr_start').attr('src' , 'resources/img/rcap/start2.png');
		        		$('#zr_pause').attr('src' , 'resources/img/rcap/pause.png'); 
		        		$('#zr_stop').attr('src' , 'resources/img/rcap/stop.png');
		        		comm.alert({imgClass: 'tips_i' ,content: comm.langConfig["workflow"].TS0033 });
		        		
		        		//重新加载值班组
		        		$("#rcap_tab [class='cur']").click();
					});
	        	
		        }); 
		       
				//下载
				$('#zr_down').unbind("click").bind("click",function(e){
					var $group=$("#rcap_tab [class='cur']");
					var parm={"groupName":$group.text(),"groupId":$group.attr("data-id"),
							"year":self.currYear,"month":self.currMonth};
					
					window.open(workflow.exportSchedule(parm),'_blank') 
				});
				
				//编辑组信息
		        $('#zr_edit').unbind("click").bind("click",function(e){
		        	//功能权限验证
					if(!self.operChk()){return false;}
					
					$('#txtJobNumber_update').val('');								//清空文本值
		        	var $groupId=$('#rcap_tab li[class="cur"]').attr("data-id");	//工作组编号
	        		
		        	//加载值班组	
		        	scheduleComm.loadGroupInfo($groupId);	
	        		
	        	    //显示编辑对话框 
					$("#rcap_bjgzz").dialog({
						title:  comm.langConfig["workflow"].TS0027  ,		 
						width:"650" ,
						buttons:{ 
							"保存":function(){
								var dialogSelf=this;
							
								//值班组修改
								scheduleComm.updateGroup(function(){
									$(dialogSelf).dialog("destroy");
								});
								
							},
							"取消":function(){
								 $(this).dialog( "destroy" );
							}
						}
				
					  });
		        }); 
		        
		        // 查看上个月值班计划
		        $('#zr_prev').unbind("click").bind("click",function(e){
					self.currMonth -= 1;
					if (self.currMonth <1){
						self.currMonth = 12;
						self.currYear -=1;
					}		
					self.setYearMonth() ;
					self.setTableDate();
					
					//获取上月组员日程安排
					self.getMembers($("#rcap_tab [class='cur']").attr("data-id"));
			}) ;
		        
		    // 查看下个月值班计划
			$('#zr_next').unbind("click").bind("click",function(e){
				
				//查询不能大于3个月
				if ( comm.monthMinus(new Date(),new Date(self.currYear +'/'+self.currMonth+ '/01'),'/')>=3){
					comm.alert({imgClass: 'tips_i' ,content: comm.langConfig["workflow"].TS0022 });			
					return ;
				}
				self.currMonth += 1;		
				if (self.currMonth>12){
					self.currMonth =1;
					self.currYear +=1;
				}
				
				//获取下个月值班组员日程安排
				self.setYearMonth() ;
				self.setTableDate();
				self.getMembers($("#rcap_tab [class='cur']").attr("data-id"));
			}) ;	 
		    
			//
		    $('#ul_rcap > li').unbind("click").bind("click",function(e){
		    	e.preventDefault();	 
				if ( !self.selectedEditRect ) { return; };
				var target  = $(this) ,
					  value = target.data('value') ; 
			    if ( self.rectangle.selectCount ){	 
			    	for ( var i = 1 ; i<= self.personCount ; i++ ){
						for (var j = 0 ; j <  31 ; j++){	
								//选择的日期是否在当前日期之前	
								if(rcapFun.isCurrDataBefore(j+1)){continue};
							
								 if (  self.scheduleClass[i-1][j].selected  ){
								 	var textColor = null ;
								 	switch (value){
										case '早': 
											textColor = "black" ;
											break;
										case '午':
											textColor = "black" ;
											break;
										case '休':
											textColor = "red"  ;
											break;
										case '旷':
											textColor = "red"  ;
											break;
										default:
											textColor = "black";
											break;				
									} 							 
									$.extend(self.scheduleEdit[i-1][j].style,{
										text :  value == '消'?'':value ,
										textColor :  textColor  ,
										strokeColor : '#fff'
									}); 
								}  
							}
					} 			    	
			    } else {
			    	var textColor = null ;
			    	switch (value){
						case '早': 
							textColor = "black" ;
							break;
						case '午':
							textColor = "black" ;
							break;
						case '休':
							textColor = "red"  ;
							break;
						case '旷':
							textColor = "red"  ;
							break;
						default:
							textColor = "black";
							break;				
					}
			    	var currClassValue =  self.scheduleClass[self.selectedEditRect.rowIndex][self.selectedEditRect.colIndex].style.text ;
			    	self.selectedEditRect.style.textColor = textColor;
				    if ( value != currClassValue  ){
				    	self.selectedEditRect.style.text = value == '消'?'':value ;	
				    } else {
				    	self.selectedEditRect.style.text = '' ;	
				    }
			    } ;
				self.zr.render();				 
				$(this).parent().addClass('none');
				
			}); 
			
			/******新增值班组 begin*****/
			//值班员选择框列表
			$('#rcap_yglb,#rcap_zby').on('click','li',function(e){
				var theLi = $(this) ;
				theLi.siblings().removeClass('bg_ddd');
				theLi.addClass('bg_ddd');				
			});
			
			//添加值班员双击选择框列表
			$('#rcap_yglb,#rcap_zby').off('dblclick','li');
			$('#rcap_yglb').on('dblclick','li',function(e){
				$("#rcap_rightArrow").click();	
			});
			//删除值班员双击选择框列表
			$('#rcap_zby').on('dblclick','li',function(e){
				$("#rcap_leftArrow").click();	
			});
			//左键按钮
		 	$('#rcap_leftArrow').click(function(){
		 		if (!$('#rcap_zby >li[class="bg_ddd"]').length){
		 			comm.alert({imgClass:'tips_i',content: comm.langConfig["workflow"].TS0025  });
		 			return;
		 		}	 	
		 		var theLi = $('#rcap_zby >li[class="bg_ddd"]').clone() ;
		 		theLi.removeClass('bg_ddd');
		 		theLi.children('span').eq(0).css('display', 'inline-block'); 
				theLi.children('span').eq(2).css('display', 'none'); 
		 		$('#rcap_yglb').append(theLi); 
		 		$('#rcap_zby >li[class="bg_ddd"]').remove(); 
		 		
		 	});
		 	//右键按钮
		 	$('#rcap_rightArrow').unbind("click").bind("click",function(){	
		 		if (!$('#rcap_yglb >li[class="bg_ddd"]').length){ 
		 			comm.alert({imgClass:'tips_i',content: comm.langConfig["workflow"].TS0024  }) ;
		 			return;
		 		}	 		 
		 		var theLi = $('#rcap_yglb >li[class="bg_ddd"]').clone() ;
		 		theLi.removeClass('bg_ddd') ;
		 		theLi.children('span').eq(0).css('display', 'none');
				theLi.children('span').eq(2).css('display', 'inline-block'); 
		 		$('#rcap_zby').append(theLi) ; 
		 		$('#rcap_yglb >li[class="bg_ddd"]').remove() ; 
		 		
		 	});
			/******新增值班组end*****/
			
			
			/******编辑值班组 begin*****/
			//值班员选择框列表
			$('#rcap_yglb2,#rcap_zby2').on('click','li',function(e){
				var theLi = $(this) ;
				theLi.siblings().removeClass('bg_ddd');
				theLi.addClass('bg_ddd');				
			});
			//添加值班员双击选择框列表
			$('#rcap_yglb2,#rcap_zby2').off('dblclick','li');
			$('#rcap_yglb2').on('dblclick','li',function(e){
				$("#rcap_rightArrow2").click();	
			});
			//删除值班员双击选择框列表
			$('#rcap_zby2').on('dblclick','li',function(e){
				$("#rcap_leftArrow2").click();	
			});
			//左键按钮
		 	$('#rcap_leftArrow2').unbind("click").bind("click",function(){
		 		if (!$('#rcap_zby2 >li[class="bg_ddd"]').length){
		 			comm.alert({imgClass:'tips_i',content: comm.langConfig["workflow"].TS0025 });
		 			return;
		 		}	 	
		 		var theLi = $('#rcap_zby2 >li[class="bg_ddd"]').clone() ;
		 		theLi.removeClass('bg_ddd');
		 		theLi.children('span').eq(0).css('display', 'inline-block'); 
				theLi.children('span').eq(2).css('display', 'none'); 
		 		$('#rcap_yglb2').append(theLi); 
		 		$('#rcap_zby2 >li[class="bg_ddd"]').remove(); 
		 	});
		 	//右键按钮
		 	$('#rcap_rightArrow2').unbind("click").bind("click",function(){	
		 		if (!$('#rcap_yglb2 >li[class="bg_ddd"]').length){ 
		 			comm.alert({imgClass:'tips_i',content: comm.langConfig["workflow"].TS0024   }) ;
		 			return;
		 		}	 		 
		 		var theLi = $('#rcap_yglb2 >li[class="bg_ddd"]').clone() ;
		 		theLi.removeClass('bg_ddd') ;
		 		theLi.children('span').eq(0).css('display', 'none');
				theLi.children('span').eq(2).css('display', 'inline-block'); 
		 		$('#rcap_zby2').append(theLi) ; 
		 		$('#rcap_yglb2 >li[class="bg_ddd"]').remove() ; 
		 	});
		 	
		 	/** by wangjg 2016-3-12 作用：增加值班员搜索功能  start*/
		 	$("#txtJobNumber_update,#txtJobNumber_add").keyup(function(){
		 		var $txtId=$(this).attr("id"); //获取文本控件id
		 		var $txtVal=comm.trim($(this).val()); //获取输入文本值
		 		var $defaultId="rcap_yglb li";  //员工列表控件id
		 		
		 		//若为修改值班组时，控制隐藏控制权转换
		 		if($txtId=="txtJobNumber_update"){$defaultId="rcap_yglb2 li";}
		 		
		 		//为空时，显示所有工作人员
		 		if($txtVal=="")
		 		{
		 			$("#"+$defaultId).show();
		 		}else{
		 			$("#"+$defaultId+"[workno*='"+$txtVal+"']").show();
			 		$("#"+$defaultId).not($("#"+$defaultId+"[workno*='"+$txtVal+"']")).hide();
		 		}
		 	});
		 	/** by wangjg 2016-3-12 end */
		 	
			/******编辑值班组 end*****/
			
			//值班员移除业务节点
		 	$('#rcap_bjzby_leftArrow').unbind("click").bind("click",function(){
		 		//功能权限验证
				if(!self.operChk()){return false;}
				
				//移除业务节点
				scheduleComm.deleteBizType();
		 	});
			//值班员移除业务节点双击
		 	$('#selExitsBizAuth').unbind("dblclick").bind("dblclick",function(){	
		 		if(comm.isNotEmpty($(this).val())){
		 			$('#rcap_bjzby_leftArrow').click();
		 		}
		 	});
		 	
		 	//值班员新增业务节点
		 	$('#rcap_bjzby_rightArrow').unbind("click").bind("click",function(){	
		 		//功能权限验证
				if(!self.operChk()){return false;}
				
				//增加业务权限
				scheduleComm.addBizType();
		 	});
		 	//值班员新增业务节点双击
		 	$('#selBizAuth').unbind("dblclick").bind("dblclick",function(){	
		 		if(comm.isNotEmpty($(this).val())){
		 			$('#rcap_bjzby_rightArrow').click();
		 		}
		 	});
		 	
		} ,
		
		createRectangle : function(){
			//创建选择框
			var self = this;
			self.rectangle.rect =new RectangleShape({
		            zlevel:100 ,
		            style : {
		                x : 0 ,
		                y : 0 ,
		                width : 0,
		                height :0,
		                brushType : 'stroke',  
		                strokeColor : 'blue' ,    
		                lineWidth : 1                
		            },
		       		hoverable:false  		       		
	            }) ;	  	     
				self.zr.addShape(self.rectangle.rect);  
			 
			 	$('#zr_rcap canvas:eq(0)').mousedown(function(e){
			 		//如果已选择，且鼠标在所选的某一矩形内	
			 		var currPos = {x : e.pageX-122 , y : e.pageY-373 } ; 
			 			for(var i =0 ; i< self.personCount ; i++){
		       				for (var j = 0 ; j < 31 ; j++){ 
		       						 
		       						 classP1 = { x : self.scheduleClass[i][j].style.x , y : self.scheduleClass[i][j].style.y  } ,
		       						 classP2 = { x : self.scheduleClass[i][j].style.x +self.scheduleClass[i][j].style.width , y : self.scheduleClass[i][j].style.y  } ,
		       						 
		       						 classP3  = { x : self.scheduleClass[i][j].style.x  +self.scheduleClass[i][j].style.width  ,y : self.scheduleClass[i][j].style.y  +self.scheduleClass[i][j].style.height } ,
		       						 classP4 = { x : self.scheduleClass[i][j].style. x , y : self.scheduleClass[i][j].style.y+self.scheduleClass[i][j].style.height  } ,
		       						 
		       						 oldColor  = 	self.scheduleClass[i][j].style.color  ;	 
		       						 
			       				    if (self.pointInPoly( currPos , [classP1,classP2,classP3,classP4 ] )  ) {	
			       				    	 		       				     
			       				    	if ( self.scheduleClass[i][j].selected ){
			       				    		//可拖动
			       				    		self.rectangle.moveFlag= 1   ;	 
			       				    	} else {
			       				    		//可框选
			       				    		self.rectangle.moveFlag= 2   ;
			       				    	}			       				    	
			       						self.selectedClassRect =  self.scheduleClass[i][j]  ;
			       						self.scheduleClass[i][j].style.strokeColor = "#00a0e9" ;			       						 
			       						$.extend(self.rectangle.rect.style ,{ 
						       				x: self.scheduleClass[i][j].style.x ,
								            y: self.scheduleClass[i][j].style.y ,
								            width: self.scheduleClass[i][j].style.width  ,
								            height: self.scheduleClass[i][j].style.height,
								            opacity : 1	 		       				
						       			}) ;
			       						
			       					} else {	
			       						if (e.button!=2){
			       							self.scheduleClass[i][j].style.strokeColor  = '#fff' ;
			       							self.scheduleClass[i][j].selected = false ;
			       						}		       						 
			       							
			       					}	       				      						
		       				}         				
		       			}  	
		       			self.rectangle.x = e.pageX-122  ;
						self.rectangle.y = e.pageY-373  ;	
						if (e.button!=2){ 
			       			self.rectangle.selectCount = 0; 
			       		}
			 			self.zr.render(); 
			 	  
			 	}) ;
			 	
			 	$('#zr_rcap canvas:eq(0)').mousemove(function(e,o){
		 			if (!self.rectangle.moveFlag || e.button == 2){ return; }; 
			 		var posX =e.pageX-122 , posY = e.pageY-373 ,
			 			 deltaX =posX -  self.rectangle.x, deltaY =posY - self.rectangle.y ;				 		
			 		if (self.rectangle.moveFlag == 1 ){
			 			//拖动某一个
			 			$.extend(self.rectangle.rect.style,{ 
		       				x: self.rectangle.rect.style.x +deltaX ,
				            y: self.rectangle.rect.style.y +deltaY 		
		       			});		
		       			self.rectangle.x = posX ;
		       			self.rectangle.y = posY ;
		       			//console.log(posX) ;   
			 			
			 		} else  if (self.rectangle.moveFlag == 2 ){
			 			//框选
			 			$.extend(self.rectangle.rect.style,{ 
		       				x: self.rectangle.x,
				            y: self.rectangle.y,
				            width: posX - self.rectangle.x  ,
				            height: posY - 	self.rectangle.y				       				
		       			});		       			
		       			//框选，改变选中的背景色
		       			for(var i =0 ; i< self.personCount ; i++){
		       				for (var j = 0 ; j < 31 ; j++){		       					 
		       					var rectangleStart = { x: self.rectangle.rect.style.x  ,y:self.rectangle.rect.style.y    } ,
		       						 rectangleEnd   = { x: self.rectangle.rect.style.x + self.rectangle.rect.style.width ,y:self.rectangle.rect.style.y+ self.rectangle.rect.style.height } ,
		       						 classStart = { x : self.scheduleClass[i][j].style.x ,y : self.scheduleClass[i][j].style.y  } ,
		       						 classEnd  = { x : self.scheduleClass[i][j].style.x  +self.scheduleClass[i][j].style.width  ,y : self.scheduleClass[i][j].style.y  +self.scheduleClass[i][j].style.height } ,
		       						 oldColor = 	self.scheduleClass[i][j].style.color;	
		       						
		       					var rectStyle =  self.scheduleClass[i][j].style   ;		       					
		       					//var firstRect = { x : self.rectangle.rect.style.x  , y: self.rectangle.rect.style.y  ,width :  self.rectangle.rect.style.width , heigth : self.rectangle.rect.style.height } ;
		       					//var secondRect = {  x : self.scheduleClass[i][j].style.x ,y: self.scheduleClass[i][j].style.y, width:self.scheduleClass[i][j].style.width ,height : self.rectangle.rect.style.height    } ;
		       					
		       				   if (self.isOverLap( rectangleStart , rectangleEnd , classStart ,  classEnd)  &&   j<self.dayCount  ) {	       				 	 
		       						//console.log(classStart.x + '  ,  ' + classStart.y  + '   ---   '   +  classEnd.x + '    ,    '+classEnd.y  ) ;
		       						self.scheduleClass[i][j].style.strokeColor = "#00a0e9";
		       						self.scheduleClass[i][j].selected = true;		       						 
		       					} else {
		       						self.scheduleClass[i][j].style.strokeColor  = '#fff' ;
		       						self.scheduleClass[i][j].selected = false ;
		       					}	       				      						
		       				}	       				
		       			}   
			 			
			 		}			 		
			 		self.zr.render();	
			 	});
			 	$('#zr_rcap canvas:eq(0)').mouseup(function(e){	
			 	 	
			 	 	var currPos = {x : e.pageX-122 , y : e.pageY-373 } ,
			 	 		  classP1 ,classP2 ,classP3, classP4	; 
			 	 		 
			 	 		//console.log(self.rectangle.moveFlag);
		 
			 	 		for(var i =0 ; i< self.personCount ; i++){
		       				for (var j = 0 ; j < 31 ; j++){ 		       						 
		       						 classP1 =  { x : self.scheduleClass[i][j].style.x , y : self.scheduleClass[i][j].style.y  } ,
		       						 classP2 =  { x : self.scheduleClass[i][j].style.x +self.scheduleClass[i][j].style.width , y : self.scheduleClass[i][j].style.y  } ,		       						 
		       						 classP3  = { x : self.scheduleClass[i][j].style.x +self.scheduleClass[i][j].style.width  ,y : self.scheduleClass[i][j].style.y  +self.scheduleClass[i][j].style.height } ,
		       						 classP4  = { x : self.scheduleClass[i][j].style.x , y : self.scheduleClass[i][j].style.y+self.scheduleClass[i][j].style.height  }  ;
		       						//判断选中的数量
		       						if ( self.scheduleClass[i][j].selected  ){
		       							self.rectangle.selectCount += 1 ;
		       						}
		       						 
			       				    if ( self.pointInPoly( currPos , [classP1,classP2,classP3,classP4 ] )  ) {	 
			       						var dd  = self.selectedClassRect ==  self.scheduleClass[i][j]  ;
			       						if ( self.rectangle.moveFlag == 2 ){
			       							if (  self.selectedClassRect == self.scheduleClass[i][j]  ) {
			       								//可框选标志
				       							self.scheduleClass[i][j].selected = true ;			       							
				       						} ; 
			       						} else {
			       							//对调两个rect对象的排班信息			       	 
			       							//如果对调的对象文本都为空，则判断下面编辑框是否有文本，有则直接对调 
		       								
		       								if ( !self.selectedClassRect.style.text && ! self.scheduleClass[i][j].style.text ) {		       									
		       									//所选排班对象未设置文本，则判断下面编辑框是否存在文本
		       									if ( self.scheduleEdit[i][j].style.text ||  self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.text  ){
			       									 var temp = { textColor : self.scheduleEdit[i][j].style.textColor , text: self.scheduleEdit[i][j].style.text }  ;
			       									 self.scheduleEdit[i][j].style.text = self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.text ;
			       									 self.scheduleEdit[i][j].style.textColor = self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.textColor ;
			       									 self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.text = temp.text ;
			       									 self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.textColor = temp.textColor ; 
			       								} 			       								
			       							} else if (  self.selectedClassRect.style.text &&  self.scheduleClass[i][j].style.text )  {
			       								//1.若编辑框有信息，且不一样，则交换编辑框信息
			       								//2.编辑框无信息，用排班框信息不一样，则把排班信息交换写入编辑框
			       								if (  self.scheduleEdit[i][j].style.text ||  self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.text  ){
			       									 
			       									  var temp = { textColor : self.scheduleEdit[i][j].style.textColor , text: self.scheduleEdit[i][j].style.text }  ;
			       									  self.scheduleEdit[i][j].style.text =  self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.text ;
			       									  self.scheduleEdit[i][j].style.textColor =  self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.textColor ;
			       									  self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.text = temp.text ;
			       									  self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.textColor = temp.textColor ; 
			       								} else if (self.selectedClassRect.style.text != self.scheduleClass[i][j].style.text ) {			       									 	
			       									  var temp = { textColor : self.scheduleClass[i][j].style.textColor , text: self.scheduleClass[i][j].style.text }  ;
			       									  self.scheduleEdit[i][j].style.text = self.selectedClassRect.style.text ;
			       									  self.scheduleEdit[i][j].style.textColor = self.selectedClassRect.style.textColor ;
			       									  self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.text = temp.text ;
			       									  self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.textColor = temp.textColor ;  
			       								}	 
			       							} else  { 
			       								
			       								if ( self.scheduleEdit[i][j].style.text ||  self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.text ){
			       									var tempObj = { textColor : self.scheduleEdit[i][j].style.textColor , text: self.scheduleEdit[i][j].style.text }  ;
					       							self.scheduleEdit[i][j].style.textColor = self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.textColor ;
					       							self.scheduleEdit[i][j].style.text = self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.text  ;
					       							self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.textColor = tempObj.textColor ;
					       							self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.text  = tempObj.text ; 	
			       								} else {
			       									//源
			       									 var temp = { textColor : self.scheduleClass[i][j].style.textColor , text: self.scheduleClass[i][j].style.text }  ;
			       									  self.scheduleEdit[i][j].style.text = self.selectedClassRect.style.text ;
			       									  self.scheduleEdit[i][j].style.textColor = self.selectedClassRect.style.textColor ;
			       									  self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.text = temp.text ;
			       									  self.scheduleEdit[ self.selectedClassRect.rowIndex ][ self.selectedClassRect.colIndex ].style.textColor = temp.textColor ;  
			       								}   
			       							}  
			       							self.zr.render();	
			       						}	        						
			       					} else {
			       						//self.scheduleClass[i][j].selected = undefined ;
			       					}       				      						
		       				}         				
		       			}
  
			 		self.rectangle.moveFlag = 0 ;
			 		$.extend(self.rectangle.rect.style,{
	       				x: 0,
			            y: 0,
			            width: 0  ,
			            height: 0	,
			            opacity:0			       				
	       			});
	       				 
	       			self.zr.render();	       			 
			 	});
		} ,
		
		createGrid : function(){ 
				 //创建表格
				  //rows
				  for (var i= 1 ; i<= this.personCount*2 ; i++){						  	  	  
					  this.zr.addShape(new LineShape({
					      style: {
					         	xStart : ( i%2==0?0:127  )+this.offset ,  //奇行从27*4开发
					  			yStart : 21*i +this.offset  ,
					  			xEnd  : (i%2==0? 1026+this.offset : 1026-63+this.offset  )  ,
					  			yEnd  : 21*i +this.offset  ,
					  			lineType: (i%2==0?'solid':'dashed'),
					  			strokeColor: '#6baf58' ,
					            lineWidth: 1  
					      }
					  }));	
				  } 
				  //cols
				  for (var i = 1; i <=35 ; i++){
				  		var xStart ,  lineType = 'solid';
				  			 xStart = (i-1)*27+this.offset ; 
				  		if (i ==1 || i == 2 ){
				  			xStart = (i-1)*30 +this.offset;
				  		} else if(i ==3){
				  			 xStart = 127+this.offset ;
				  		} else if (i==4){
				  			 xStart = 154+this.offset  ;	 
				  		}  else if (i==34){
				  			 xStart  = 1024-60+this.offset ;
				  		} else if (i==35){
				  			 xStart  = 1027+this.offset ;
				  		} else {
				  			 xStart = 154 + (i-4)*27+this.offset
				  		}
				  		
				  		if (i>3 && i<34){
				  			lineType = 'dashed' ;
				  		} 
				  		this.zr.addShape(new LineShape({
					      style: {
					         	xStart :  xStart ,  //奇行从27*4开发
					  			yStart :  this.offset  ,
					  			xEnd  :  xStart ,
					  			yEnd  : this.canvasHeight+this.offset ,
					  			lineType:  lineType,
					  			strokeColor: '#6baf58' ,
					            lineWidth : 1  
					      }
					  }));					  	
				  } 
			} ,
		
		getCountDays : function(year,month){
		 	   /* 返回当月的天数 */
		 	   var curYear    = year || this.currYear ;
		 	   var curMonth = month || this.currMonth ;	
		       var curDate = new Date( curYear+'/'+curMonth+'/1' ) ;
		       curDate.setMonth( curMonth ); 
		       curDate.setDate(0);	
		      // debugger
		       this.dayCount = curDate.getDate() ;		       
		       return  this.dayCount;
		},		
	    setYearMonth : function(year,month){
	    	$('#zr_rq').text( (year||this.currYear)+ ' 年 '+ (month||this.currMonth) + ' 月' );
	    	this.getCountDays();
	    } ,
	    setTableDate : function(){
	    	 
	    	//重载日期
	    	var rcap_tr2 = $('#table_rcap tr:eq(1)') ;	    	 
	    	//rcap_tr2.append('<td rowspan="2" colspan="4">姓名 \ 日期</td>') ;
	    	$('#table_rcap tr:eq(1) td').removeClass('red');
	    	$('#table_rcap tr:eq(2) td').removeClass('red');
	    	 
	    	for(var i = 1 ; i <= 31 ;i++ ){
	    		var weekIndex  = new Date(this.currYear +'/'+ this.currMonth +'/'+ i ).getDay() ,
	    			  currDay = new Date().getDate();
	     	
	    		if ( i <= this.dayCount ){
	    			if (weekIndex ==0 || weekIndex ==6){
	    				$('#table_rcap tr:eq(1) td').eq(i).addClass('red');
	    				$('#table_rcap tr:eq(1) td').eq(i).text(i);
	    				//rcap_tr2.append('<td class="red">'+ i +'</td>');
	    			}  else {
	    				
	    				$('#table_rcap tr:eq(1) td').eq(i).text(i);
	    			}	
	    			if (i==currDay){
	    				$('#table_rcap tr:eq(1) td').eq(i).addClass('bg_blue');
	    			} else {
	    				$('#table_rcap tr:eq(1) td').eq(i).removeClass('bg_blue');
	    			} 
	    			    			
	    		} else {
	    			$('#table_rcap tr:eq(1) td').eq(i).text('').removeClass('bg_blue');
	    		}   		
	    	}	    	
	    	
	    	//rcap_tr2.append('<td rowspan="2" colspan="2">调休天数</td>') ;
	    	//重载星期
	    	var rcap_tr3 = $('#table_rcap tr:eq(2)') ;
	    	 
	    	for(var i = 1 ; i <= 31 ; i++ ){
	    		var weekIndex  = new Date(this.currYear +'/'+ this.currMonth +'/'+ i ).getDay() , week=null ;
	    		switch (weekIndex){
	    			case 0:
	    				week = '天';
	    				break;
	    			case 1:
	    				week = '一';
	    				break;
	    			case 2:
	    				week = '二';
	    				break;
	    			case 3:
	    				week = '三';
	    				break;
	    			case 4:
	    				week = '四';
	    				break;
	    			case 5:
	    				week = '五';
	    				break;
	    			case 6:
	    				week = '六';
	    				break;	
	    		}
	    		
	    		if ( i <= this.dayCount ){
	    			if ( weekIndex ==0 || weekIndex ==6 ){
	    				//rcap_tr2.append('<td class="red">'+ week +'</td>') ;
	    				$('#table_rcap tr:eq(2) td').eq(i-1).addClass('red');
	    				$('#table_rcap tr:eq(2) td').eq(i-1).text(week) ;
	    			}  else {
	    				//rcap_tr2.append('<td>'+week +'</td>') ;
	    				$('#table_rcap tr:eq(2) td').eq(i-1).text(week) ;
	    			}	
	    		} else {
	    			$('#table_rcap tr:eq(2) td').eq(i-1).text('') ;
	    		}  	
	    	}	 
	    } ,
	    initScheduleClass : function(){
	    	var self = this ;
	    	for ( var i = 1 ; i<= this.personCount ; i++ ){
	    		//编辑区域
				var rectArr2 = [];
				for ( var j = 0 ; j <  31 ; j++ ){						
						var rect2 = new RectangleShape({
	                            //zlevel:0,
	                            style : {
	                                x : 127+j*27+1 ,
	                                y : (i-1)*2*21 +1+21 ,
	                                width : 26,
	                                height : 20,
	                                //brushType : 'both',
	                                color : j< self.dayCount ?'#ddd':'#fff' ,
	                                //strokeColor : '#fff'
	                                text : '' ,
	                                textFont: '12px 宋体',
	                                textPosition:'inside',
	                                textAlign: 'center',
	                                textBaseline: 'middle',
	                                textColor: '#000' 
	                               
	                            },
					       		hoverable:false, 
					       		rowIndex : i -1 ,
		                    	colIndex  : j  					       						       		 			       		
	                      });
	                    rectArr2.push(rect2); 
						this.zr.addShape( rect2); 
					}
					this.scheduleEdit[i-1] = rectArr2;
	    		
		    	//当前班次
		    	var rectArr = [];
		    	for (var j = 0 ; j <  31 ; j++){		    		
		    		var rect = new RectangleShape({
		                    //zlevel:0,
		                    style : {
		                        x : 127+j*27+1,
		                        y : (i-1)*2*21 +1 ,
		                        width : 26,
		                        height : 20,		                
		                        color : '#fff',
		            			//text : this.schedules[j],
		                        text : '',
		                        textFont: '12px 宋体',
		                        textPosition:'inside',
		                        textAlign: 'center',
		                        textBaseline: 'middle',
		                        textColor: '#000'
		                    },
		                    rowIndex : i -1 ,
		                    colIndex  : j  ,
				       		hoverable:false,					       	 	 				       		
				       		onmouseup : function(e) {
				       			//鼠标右键控制
				       			if (e.event.button !=2){return;};		
				       			
				       			//不能编辑当日之前的值班计划
				       			if(rcapFun.isCurrDataBefore((e.target.colIndex+1))){return;}
				       			
				       			$('#ul_rcap').removeClass('none');	  
				       			$('#ul_rcap').css({ left:e.target.style.x ,top:e.target.style.y }) ;		 
				       		} ,
				       		onmouseout : function(e) { 				       		  
				       			if (!$('#ul_rcap').hasClass('none')  ){
				       				$('#ul_rcap').addClass('none')  ;
				       			} ;   
				       		} ,	 
				       		onmouseover :function(e){	
				       			//编辑回写对象				       			
				       			self.selectedEditRect = self.scheduleEdit[e.target.rowIndex][e.target.colIndex] ;   
				       		}  
				       			       		
	                }) ;	   
	                rectArr.push(rect);
	                
					//this.scheduleClass.push(rect);	
					this.zr.addShape(rect); 
				}
				this.scheduleClass[i-1] = rectArr ; 		
			}
			
	    } ,
	   	//建序号
	   	createSerial : function(i){	
	   		this.serialNoArr.length = 0 ;
	   		for ( var i = 1 ; i<= this.personCount ; i++ ){ 
					var serialNo = new TextSharp({
					        style: {
					            text: i ,
					            x: 15 ,
					            y: 21*i +21*(i-1) ,
					            textFont: '14px Arial' ,
					            textAlign: 'center' 
					        },
					        hoverable:false
					 }) ;					  	 
				  	 this.serialNoArr[i] = serialNo ;
				  	 this.zr.addShape(serialNo); 
	   		}  
	   	} ,
	   	//建姓名
	   	createName : function(){
	   		var self=this;
	   		 this.nameArr.length  =0 ;
	   		 for ( var index= 1 ;index<= this.personCount ; index++ ){ 
	   			//值班员信息
		   		var optInfo = this.clerkNames[index-1];	
				var name = new TextSharp({
					optCustId:(comm.isNotEmpty(optInfo) ? optInfo.id : ""), /** 增加组员操作号 wjg */
					/*optCustName:optInfo.name,*/
			        style: {
			            text:(comm.isNotEmpty(optInfo) ? comm.subString(optInfo.name,7):""), 
			            x: 77 ,
			            y: 21*index +21*(index-1),
			            textFont: 'bold 10px Arial',
			            textAlign: 'center'
			        },
			        clickable:true,
			       /*  onmousemove:function(){
			        	console.log(JSON.stringify(this.optCustName));
			        },
			        onmouseout:function(){
			        	console.log(this.optCustName+"=======");
			        },*/
			        onclick:function(){	
			        	//功能权限验证
						if(!self.operChk()){return false;}
						
			        	var optCustId=this.optCustId; 								//获取值班组组员编号
			        	var $groupId=$("#rcap_tab [class='cur']").attr("data-id");	//获取值班组编号
			        	if(comm.navNull(optCustId)!=""){
			        		//加载组员明细
			        		var getParam={"groupId":$("#rcap_tab [class='cur']").attr("data-id"),"optCustId":optCustId,"scheduleId":self.scheduleId};
			        		scheduleComm.loadAttendantInfo(getParam);
			        	}    
			        	
			        	$("#rcap_zbyxx").dialog({
								title:"值班员值班信息",		 
								width:"650" ,
								height:"500",
								buttons:{ 
									"移除":function(){
										var rmthis=this;
										
										comm.confirm({
											imgFlag : true,
											imgClass : 'tips_i',
											content : '确定移除该值班员？',
											callOk : function(){
												
												//当前组值班主任少于2个时，无法移除  
												if(self.currGroupCheifNum<2){
													var currCustid = comm.getCookie("custId");		//当前操作员客户号
													var currUserName = comm.getCookie("userName");	//当前操作员登录帐号
													if(currCustid == optCustId || (currUserName == "0000" && optCustId==self.groupOneCheif)){
														comm.alert({imgClass: 'tips_i' ,content: comm.lang("workflow").remove_cust_prompt});	
														return false;
													}
												}
												
												//移除值班员
												 var rmParam={"groupId":$groupId,"optCustId":optCustId};
												 scheduleComm.removeOperator(self.clerkNames,rmParam,function(rsp){
													 $("#rcap_tab [class='cur']").click();
													 $(rmthis).dialog("destroy");
												 });
											}	
										});
									},
									"关闭":function(){
										 $(this).dialog("destroy");
									}
								}		
						  }) ;
			        }
			     });	
			  	this.zr.addShape(name);
			 	this.nameArr[index] = name ;
			 }	
	   	} ,
	   	//建调休
	   	createRest : function(){
	   		 this.restArr.length = 0 ;
	   		  for ( var index= 1 ;index<= this.personCount ; index++ ){ 
				var rest = new TextSharp({
			        style: {
			            text:this.restDays[index-1] || ''  ,
			            x: 1027-30 ,
			            y: 21*index +21*(index-1),
			            textFont: '14px Arial',
			            textAlign: 'center'
			        }
			   });
			   this.restArr[index] = rest ;
			   this.zr.addShape(rest);					   
			 }
	   	}  ,
	    
		
		isOverLap : function(p1, p2, q1, q2) {
			var pMaxX = Math.max(p1.x, p2.x);
			var pMaxY = Math.max(p1.y, p2.y);
			var pMinX = Math.min(p1.x, p2.x);
			var pMinY = Math.min(p1.y, p2.y);
			var qMinX = Math.min(q1.x, q2.x);
			var qMinY = Math.min(q1.y, q2.y);
			var qMaxX = Math.max(q1.x, q2.x);
			var qMaxY = Math.max(q1.y, q2.y);
			return !(pMaxX<qMinX || pMaxY<qMinY || qMaxX<pMinX || qMaxY<pMinY);
			//如果矩形1中最大的X轴点都小于矩形2的最小的X轴，那肯定两个矩形不重叠，后同...
		},
		
		pointInPoly : function(pt, poly) {
		    for (var c = false, i = -1, l = poly.length, j = l - 1; ++i < l; j = i)
		        ((poly[i].y <= pt.y && pt.y < poly[j].y) || (poly[j].y <= pt.y && pt.y < poly[i].y))
		        && (pt.x < (poly[j].x - poly[i].x) * (pt.y - poly[i].y) / (poly[j].y - poly[i].y) + poly[i].x)
		        && (c = !c);
		    return c;
		},
		 
	    createTexts : function(){ 
	    		var self = this;
				for ( var i = 1 ; i<= this.personCount ; i++ ){
					//序号					
					//self.getOrCreateSerial(i);				
					
					//姓名
					//self.getOrCreateName(i); 
					
					//组员月排班
					var schedule=self.membersSchedule[i-1];
					 
					//排班详情
					for (var j = 0 ; j <  31 ; j++){										 
						if ( j<this.dayCount ){	
							
							//是否在当前日期之前
							var cdb = rcapFun.isCurrDataBefore(j+1);
							
							//TODO...
							var weekIndex  = new Date(this.currYear +'/'+ this.currMonth +'/'+ (j+1) ).getDay() , week=null ;
							//this.scheduleClass[i-1][j].style.text =this.schedules[j];
							this.scheduleClass[i-1][j].style.text = '';
							this.scheduleClass[i-1][j].style.brushType="both";
							this.scheduleClass[i-1][j].style.strokeColor = '#ffffff' ; 
							this.scheduleClass[i-1][j].style.lineWidth=2 ;
							//this.scheduleClass[i-1][j].clickable= self.editable ;
							if ( weekIndex ==0 || weekIndex ==6 ){	
								this.scheduleClass[i-1][j].style.text =(!schedule || !schedule.monthODT || !comm.isNotEmpty(schedule.monthODT[j]) ? "":schedule.monthODT[j]);
								this.scheduleClass[i-1][j].style.textColor='red';								
								if(!cdb){this.scheduleClass[i-1][j].style.color = '#ffff00' ;} 							
							} else {
								this.scheduleClass[i-1][j].style.text =(!schedule || !schedule.monthODT || !comm.isNotEmpty(schedule.monthODT[j]) ? "": schedule.monthODT[j]);
								this.scheduleClass[i-1][j].style.textColor='black';
								if(!cdb){this.scheduleClass[i-1][j].style.color = '#ffffff'; }	
							}
						} else {
							this.scheduleClass[i-1][j].clickable= false ;
							this.scheduleClass[i-1][j].style.text ='';
							this.scheduleClass[i-1][j].style.textColor='';
							if(!cdb){this.scheduleClass[i-1][j].style.color = '#ffffff'; }	
						}	
						
						//图像忽略
						//this.scheduleClass[i-1][j].ignore=true;
						
						if(cdb){
							//灰色背景
							this.scheduleClass[i-1][j].style.color = '#ddd'; 	
						}
						//鼠标移动背景
						//this.scheduleClass[i-1][j].hoverable=true;
						
						
						//this.scheduleClass[i-1][j].clickable = false ;
					}
					
					//编辑区域
					for (var j = 0 ; j <  31 ; j++){
						 if ( j<this.dayCount ){							
							//this.scheduleClass[i-1][j]
							//TODO...
							var weekIndex  = new Date(this.currYear +'/'+ this.currMonth +'/'+ (j+1) ).getDay() , week=null ;
							//this.scheduleEdit[i-1][j].clickable= self.editable ;
							//this.scheduleClass[i-1][j].style.text =this.schedules[j];			
							//this.scheduleClass[i-1][j].style.text ='';	
							
							//console.log("打印排班类型："+JSON.stringify(schedule));
							
							this.scheduleEdit[i-1][j].clickable= self.editable ;
						
							//获取值班类型名称
							var sheduleText="";
							if(schedule && schedule.tempMonthODT && comm.isNotEmpty(schedule.tempMonthODT[j])){
								sheduleText=schedule.tempMonthODT[j];
							}
							
							this.scheduleEdit[i-1][j].style.text =sheduleText;		
							
							//增加颜色标识
							if(sheduleText=="休" || sheduleText=="旷"){
								this.scheduleEdit[i-1][j].style.textColor='red';								
								//this.scheduleEdit[i-1][j].style.color = '#ffff00' ; 
							}
							
						} else {			 
							//this.scheduleEdit[i-1][j].clickable = false; 
							this.scheduleEdit[i-1][j].style.text ='';
							this.scheduleEdit[i-1][j].style.textColor='';
						}
					} 					
					//休假天数				   
					//this.getOrCreateRest(i);  
				}
								
			} ,
			
			setClassEdit : function(editable) {
				for ( var i = 1 ; i<= this.personCount ; i++ ){
					for (var j = 0 ; j <  31 ; j++){
							 if ( j<this.dayCount ){
								this.scheduleEdit[i-1][j].clickable= editable; 
							}  
						}
				} 
			}  ,
			//判断矩形是否已选中
			getClassByCurrPos : function(currPos){
				var currClass  ,  classP1 ,classP2,classP3,classP4 ;
				for(var i =0 ; i< self.personCount ; i++){
       				for (var j = 0 ; j < 31 ; j++){
       						 classP1 = { x : self.scheduleClass[i][j].style.x , y : self.scheduleClass[i][j].style.y  } ,
       						 classP2 = { x : self.scheduleClass[i][j].style.x +self.scheduleClass[i][j].style.width , y : self.scheduleClass[i][j].style.y  } ,       						 
       						 classP3  = { x : self.scheduleClass[i][j].style.x  +self.scheduleClass[i][j].style.width  ,y : self.scheduleClass[i][j].style.y  +self.scheduleClass[i][j].style.height } ,
       						 classP4 = { x : self.scheduleClass[i][j].style. x , y : self.scheduleClass[i][j].style.y+self.scheduleClass[i][j].style.height  }   ;	 
	       					 
	       				    if (self.pointInPoly( currPos , [classP1,classP2,classP3,classP4 ] )  ) {	
	       				    	currClass = self.scheduleClass[i][j] ;
	       				    	break;
	       				    	/*
	       				    	if (self.scheduleClass[i][j].selected){
	       				    		return true;
	       				    	} 
	       				    	 
	       						self.selectedClassRect =  self.scheduleClass[i][j]  ;
	       						self.scheduleClass[i][j].style.strokeColor = "#00a0e9" ;
	       						self.scheduleClass[i][j].selected = true ; 
	       						return false;
	       						 */
	       						//console.log(i);
	       						 
	       					} /* else {
	       						self.scheduleClass[i][j].style.strokeColor  = '#fff' ;
	       						self.scheduleClass[i][j].selected = false ;
	       					}	*/       				      						
       				}         				
       			}
       			return currClass;
       			
			},
			/** 获取组 */
			getGroup:function(){
				var self=this;
				//值班组
				workflow.getGroupList({},function(rsp){
					var defaultGroupId="0";
					self.groupNames=rsp.data;
					
					if(rsp.data.length>0){
						defaultGroupId=self.groupNames[0].id;
					}
					
					//获取组员
					self.getMembers(defaultGroupId,true);
					
					//加载值班组
					self.initTab();
				});
			},
			
			/** 获取组员 */
			getMembers:function(groupId,initGroup){
				var self=this;
				
				//获取组员日程安排
				workflow.getMembersSchedule({"groupId":groupId,"year":self.currYear,"month":self.currMonth},function(rsp){
					var pc=rsp.data.operationList.length;
					self.scheduleId=rsp.data.scheduleId;
					self.personCount = (pc == 0 ? 2 : pc);
					self.clerkNames = rsp.data.operationList;
					self.membersSchedule = rsp.data.onDutyTypeList;
					self.restDays = rsp.data.off;
					self.canvasHeight = self.personCount*2*21 ;	
					self.scheduleStatus=rsp.data.scheduleStatus;
					
					zrender.dispose(self.zr);
					
					//初始化值班组
					$('#zr_rcap').css({'height': self.canvasHeight+100,'min-height':self.canvasHeight+100}) ; 
					self.zr = zrender.init(document.getElementById('zr_rcap') ) ;  
					self.buttonBind();
					
					//加载组员、值班计划
					self.renderTable(); 
					
					//控件展示
					self.controlButton();
					
					//获取值班组下值班主任信息 
					var cheifInfo = scheduleComm.getMembersCheifInfo(self.clerkNames,self.getCurrentGroup());
					self.currGroupCheifNum=cheifInfo.chiefNum;
					self.currOptIsCheif=cheifInfo.currCustChief;
					self.groupOneCheif=cheifInfo.groupOneCheif;
					
				});
			},
			/** 获取值班员列表 */
			getAttendant:function(){
				workflow.getEntOperList({},function(rsp){ 
					$("#rcap_yglb,#rcap_yglb2").html("");
					$(rsp.data).each(function(i,v){
						$("#rcap_yglb, #rcap_yglb2").append("<li workNo=\""+v.workNo+"\" data-value=\""+v.optCustId+"\"><span>"+v.workNo+"</span><span>"+ comm.navNull(v.operatorName)+"</span><span style=\"display:none;\"><input type=\"checkbox\"></span></li>");
					});
				});
			},
			/** 获取值班员值班计划 */
			getShedule:function(addHead){
				var self=this;
				var dutyType;	//值班类型
				var monthDay=self.getCountDays(self.currYear, self.currMonth); //月天数
				var shedules=new Array(); //值班计划
				var memberShedule=[]; //员工值班计划
				for ( var i = 1 ; i<= this.personCount ; i++ ){
					for ( var j = 0 ; j <  monthDay ; j++ ){	
						dutyType= this.scheduleEdit[i-1][j].style.text;
						if (comm.isNotEmpty(dutyType) && !rcapFun.isCurrDataBefore(j+1)){
							memberShedule.push({"optCustId":this.clerkNames[i-1].id,"planDate":this.currYear+"-"+this.currMonth+"-"+(parseInt(j)+1),"inputWorkName":dutyType});
						}
    				}	
				}
				
				//是否存在值班员排班计划
				/*if(memberShedule.length>0){*/
				shedules.push({"scheduleId":this.scheduleId,"groupId":$('#rcap_tab li[class="cur"]').attr("data-id"),"planYear":this.currYear,"planMonth":this.currMonth,"scheduleOptList":memberShedule});
				/*}*/
				return shedules;
			},
			/** 控制按钮 */
			controlButton:function(updateStatus){
				var group=this.getCurrentGroup(updateStatus);
				if(group){
					//根据值班组状态显示按钮状态
					if(group.opened==0) {
						 $('#zr_stop').attr('src' , 'resources/img/rcap/stop2.png');
						 $('#zr_pause').attr('src' , 'resources/img/rcap/pause.png'); 
						 $('#zr_start').attr('src' , 'resources/img/rcap/start.png');
					}else{
						$('#zr_pause').attr('src' , 'resources/img/rcap/pause.png'); 
						$('#zr_start').attr('src' , 'resources/img/rcap/start.png');
						$('#zr_stop').attr('src' , 'resources/img/rcap/stop.png');
						
						 //根据值班计划状态控制按钮显示
						if (this.scheduleStatus==3) {
							 $('#zr_pause').attr('src' , 'resources/img/rcap/pause2.png'); 
						}
					}
				}
			},
			/** 获取当前值班组 */
			getCurrentGroup:function(updateStatus){
				var self=this;
				var groupJson;
				
				//获取当前显示的值班组编号
				var $groupId=$("#rcap_tab [class='cur']").attr("data-id");
				
				//遍历值班组，获取值班组内容JSON
				for(var i=0;i<self.groupNames.length;i++){
					if(self.groupNames[i].id==$groupId){
						//更新状态
						if(updateStatus!=undefined){
							self.groupNames[i].opened=updateStatus;
						}
						groupJson=self.groupNames[i];
						break;
					}
				}
				return groupJson;
			},
			/**
			 * 操作验证(值班组非启用状态、非值班主任不能操作)
			 */
			operChk:function(){
				return (this.controlClick() && this.noCheifDisables());
			},
			/** 根据值班组状态控制点击事件 */
			controlClick:function(){
				var group=this.getCurrentGroup();//获取当前值班组

				//禁止非开启状态下操作
				if(group.opened!=1){
					comm.alert({imgClass: 'tips_i' ,content: comm.lang("workflow").TS0034});		
					return false;
				}
				
				return true;
			},
			/**
			 * 非值班主任不能操作，只允许查看
			 */
			noCheifDisables:function(){
				//是否管理员
				var adminBool = scheduleComm.currOptIsAdmin();	
				//非管理员、非值班主任
				if(!this.currOptIsCheif && !adminBool){
					comm.alert({imgClass: 'tips_i' ,content: comm.lang("workflow")[42164]});		
				}
				return (this.currOptIsCheif || adminBool);
			},
			/**
			 * 选择的日期否是在当前日期之前
			 */
			isCurrDataBefore:function(selectDay){
				var newDate=new Date();
				var newYear=newDate.getFullYear();		//年
				var newMoth=newDate.getMonth()+1;		//月
				var newDay=newDate.getDate();			//日
				
				var currDateTime = new Date(newYear + "-" + newMoth + "-" + newDay);
				var selctDateTime = new Date(rcapFun.currYear + "-" + rcapFun.currMonth + "-" + selectDay);
				
				//与选中的日期进行比较，选择的日期小于当前日期不显示右键菜单
				if(currDateTime-selctDateTime>0){
					return true;
				}	
				
				return false;
			}
	}	
	
	return rcapFun;
});