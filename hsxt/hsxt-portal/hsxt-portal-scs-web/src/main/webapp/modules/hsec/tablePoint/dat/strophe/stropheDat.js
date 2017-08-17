define(function(){
	var ajaxRequest = {
			
			//扩展日期格式化方法format()
			addDate_format:function(){
				Date.prototype.format=function(){
					var month="0";
					if(this.getMonth()+1>=10)month=this.getMonth()+1;
					else month+=this.getMonth()+1;
					var day="0";
					if(this.getDate()>=10)day=this.getDate();
					else day+=this.getDate();
					var str=this.getFullYear()+"-"+month+"-"+day;	
					return str;
				};
				
				Date.prototype.formatTime=function(){
					var month="0";
					if(this.getMonth()+1>=10)month=this.getMonth()+1;
					else month+=this.getMonth()+1;
					var day="0";
					if(this.getDate()>=10)day=this.getDate();
					else day+=this.getDate();
					var hour="0";
					if(this.getHours()>=10)hour=this.getHours();
					else hour+=this.getHours();
					var mins="0";
					if(this.getMinutes()>=10)mins=this.getMinutes();
					else mins+=this.getMinutes();
					var str=this.getFullYear()+"-"+month+"-"+day+" "+hour+":"+mins;	
					return str;
				};
			},
			
			//读取缓存基础数据
			loadCache:function (callback){
				comm.Request('loadCache',{
					type:'get',
					dataType:'json',
					async : false,
					success:function(response){
						callback(response);	
					},
					error: function(){
						$("#error_content").text(_dat.result);
						$("#alert_error").dialog({
							title:"提示信息",
							width:"400",/*此处根据文字内容多少进行调整！*/
							modal:true,
							buttons:{ 
							"确定":function(){
								$("#alert_error").dialog( "destroy" );
							}
							}
						});
					}
				});	
			},
				
			//读取用户数据
			getLoginUser:function (callback){
				comm.Request('getLoginUser',{
					type:'get',
					dataType:'json',
					async : false,
					success:function(response){
						callback(response);	
					},
					error: function(){
						$("#error_content").text(_dat.result);
						$("#alert_error").dialog({
							title:"提示信息",
							width:"400",/*此处根据文字内容多少进行调整！*/
							modal:true,
							buttons:{ 
							"确定":function(){
								$("#alert_error").dialog( "destroy" );
							}
							}
						});
					}
				});	
			}	
			
			,i_alert:function(text,width){
				if(!width)width="400";
				$("#i_content").text(text);
				$("#alert_i").dialog({
					title:"提示信息",
					width:width,/*此处根据文字内容多少进行调整！*/
					modal:true,
					buttons:{ 
					"确定":function(){
						$("#alert_i").dialog( "destroy" );
					}
					}
				});
			}
			
			,error_alert:function(text,width){
				if(!width)width="400";
				$("#error_content").text(text);
				$("#alert_error").dialog({
					title:"提示信息",
					width:width,/*此处根据文字内容多少进行调整！*/
					modal:true,
					buttons:{ 
					"确定":function(){
						$("#alert_error").dialog( "destroy" );
					}
					}
				});
			}
			
			//服务公司通讯录接口
			,findSerComUserList:function(callback,param){
				if(!param)param={};
				comm.Request({url:'/SreviceCompany/findSerComUserList',domain:'scs'},{
					type:'POST',
					data:param,
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
					});	
			}
			
	};	
	ajaxRequest.addDate_format();
	return ajaxRequest;
});