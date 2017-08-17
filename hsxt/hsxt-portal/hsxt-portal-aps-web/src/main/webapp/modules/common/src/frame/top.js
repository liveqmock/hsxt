define(function(){
	var top={
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
	
				//获取今天开始和结束
				getTodaySE:function(){
					var d1=new Date();
					return [d1.format(),d1.format()];
				},
			
				//获取最近一周开始和结束
				getWeekSE:function (){
					var d1=new Date();
					var e=d1.format();
					d1.setDate(d1.getDate()-6);//上一周
					var s=d1.format();
					return [s,e];
				},
			
				//获取最近一月开始和结束
				getMonthSE:function (){
					var d1=new Date();
					var e=d1.format();
					d1.setMonth(d1.getMonth()-1);//上一个月
					var s=d1.format();
					return [s,e];
				},
			
				//获取最近三年开始和结束
				get3YearsSE:function (){
					var d1=new Date();
					var e=d1.format();
					d1.setFullYear(d1.getFullYear()-3);//上三年
					var s=d1.format();
					return [s,e];
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
							comm.alert('请求数据出错！');
						}
					});	
				},
				
				//读取用户数据
				getLoginUser:function (callback){
					comm.Request('getLoginUser',{
						type:'get',
						dataType:'json',
						async : true,
						success:function(response){
							callback(response);	
						},
						error: function(){
							comm.alert('请求数据出错！');
						}
					});	
				},
				/**
				 * 转换银行名称
				 * @param bankNo
				 * @returns {String}
				 */
				getBankName : function(bankNo){
					var bankName="";
					$.each(comm.getCache("customer","cache").bankList,function(i,o){
						if(o.bankCode==bankNo)bankName= o.bankName;
					});
					return bankName;
				},
				/**
				 * 转换地区名称
				 */
				getArea:function(areaNo,type){
					var area=null;
					
					if(type=="0"){//国家
						$.each(comm.getCache("customer","cache").countryList,function(i,o){
							if(o.areaNo==areaNo){	
								area= o;
							}
						});

					}
					
					if(type=="1"){//省份
						$.each(comm.getCache("customer","cache").countryList,function(i,o){
							
							$.each(o.childs,function(j,o2){//alert(o2.areaNo+":"+areaNo);
								if(o2.areaNo==areaNo){//alert(o2.areaName);
									area= o2;
								}
							});
							
						});

					}
					
					if(type=="2"){//城市
						$.each(comm.getCache("customer","cache").countryList,function(i,o){
							
							$.each(o.childs,function(j,o2){
								$.each(o2.childs,function(k,o3){
									if(o3.areaNo==areaNo){
										area= o3;
									}
								});
							});
							
						});

					}
					
					return area;
				},
				getCityCode:function(cityName){
					var cityN = null ;
					$.each(comm.getCache("customer","cache").countryList,function(i,o){
						$.each(o.childs,function(j,o2){
							$.each(o2.childs,function(k,o3){
								if(o3.areaName==cityName){
									cityN= o3.areaNo;
								}
							});
						});
						
					});
					return cityN;
				},
				getQueryString : function (name) {
					var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
					var r = window.location.search.substr(1).match(reg);
					if (r != null) return unescape(r[2]); return null;
				},
				

				//jquery.validate扩展验证
				addMethod_validator : function (){
					//验证表单自定义方法,用于对比两个日期，第一个日期不能大于第二个日期（用于对比结束日期）
					$.validator.addMethod("endDate",function(value,element,params){ 
						 var startDate = value;
				         var endDate = $(params).val();//参数是结束日期的ID       
				         if(startDate==""||endDate=="")return true;      
				         var reg = new RegExp('-','g');
				         startDate=new Date(Date.parse(startDate.replace(reg, "/")));
				         endDate=new Date(Date.parse(endDate.replace(reg, "/")));
				         return startDate<=endDate;        	
					},"开始日期必须小于结束日期");
					
					
					//验证表单自定义方法,用于对比两个日期，第一个日期不能大于第二个日期（用于对比开始日期）
					$.validator.addMethod("beginDate",function(value,element,params){ 
						 var startDate = $(params).val();//参数是开始日期的ID    
				         var endDate = value;
				         if(startDate==""||endDate=="")return true;
				         var reg = new RegExp('-','g');
				         startDate=new Date(Date.parse(startDate.replace(reg, "/")));
				         endDate=new Date(Date.parse(endDate.replace(reg, "/")));
				         return startDate<=endDate;        	
					},"结束日期必须大于开始日期");
					
					//验证表单自定义方法,用于对比对比文本框的值不能大于某span或div里的值
					$.validator.addMethod("greater",function(value,element,params){
						 var val = $(params).text();//某span或div里的值    
						 if(val=="")val=$(params).val();
				         if(value==""||val==""||isNaN(value)||isNaN(val))return true;      
				         return parseInt(value)<=parseInt(val);        	
					},"数值不能大于指定范围");
					
					//验证表单自定义方法,用于对比对比文本框的值不能小于某span或div里的值
					$.validator.addMethod("less",function(value,element,params){
						 var val = $(params).text();//某span或div里的值    
						 if(val=="")val=$(params).val();
				         if(value==""||val==""||isNaN(value)||isNaN(val))return true;      
				         return parseInt(value)>=parseInt(val);        	
					},"数值不能小于指定范围");
					
					//100的整数验证
					$.validator.addMethod("isNumTimes", function(value,element) {
					      if(value%100==0){
					    	  return true;
					      }else{
					    	  return false;
					      }
					}, "请填写100的倍数");
					
					//验证表单自定义方法,用于对比对比文本框的值是否合法的货币格式(小数点左面最多9位，小数点右面最多2位)
					$.validator.addMethod("huobi",function(value,element,params){
						 if(params){
							 var reg=/^(([1-9]{1}\d{0,8})|0)(\.\d{1,2})?$/;
							 return reg.test(value);
						 }	 
						 return true;      	
					},"请输入正确的货币格式");
					
					// 手机号码验证
					$.validator.addMethod("mobile", function(value, element) {
					    var length = value.length;
					    var mobile =  /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
					    return this.optional(element) || (length == 11 && mobile.test(value));
					}, "手机号码格式错误");
					
				},
				
				
				
				//加载银行下拉框
				add_bankList:function(id,data){
					$(id).empty();//清空银行列表
					$(id).append('<option value="">-请选择-</option>');
					if(!data){
						$.each(comm.getCache("customer","cache").bankList,function(i,o){
							$(id).append('<option value="'+o.bankCode+'" '
									+'>'+o.bankName+'</option>');
						});
					}else{
						$.each(data,function(i,o){
							$(id).append('<option value="'+o.bankCode+'" '
									+'>'+o.bankName+'</option>');
						});
					}
					$(id).combobox();
					$(".ui-autocomplete").css({
						"max-height":"120px",
						"overflow-y":"auto",
						"overflow-x":"hidden",
						"height":"120px",
						"border":"1px solid #CCC"
					});
					$(".combobox2_style").find("a").attr("title","显示所有选项");
//					$(id).selectbox({width:190});由于需要输入框筛选，改用combobox
				},

				
				//加载省份下拉框
				add_areaListForCity:function(id1,id2){
					var country=this.getArea("156","0");
					if(country!=null){
						$(id1).empty();//清空列表
						$(id1).append('<option value="">-请选择-</option>');
						$.each(country.childs,function(i,o){
							$(id1).append('<option value="'+o.areaNo+'" '
									+'>'+o.areaName+'</option>');
						});
						$(id2).selectbox({width:90}).selectRefresh();
						
						$(id1).selectbox({width:90}).bind("change",function(){
							var areaNo=$(id1).val();
							var province=top.getArea(areaNo,"1");
							if(province!=null){
								$(id2).empty();//清空列表
								$(id2).append('<option value="">-请选择-</option>');
								$.each(province.childs,function(i,o){
									$(id2).append('<option value="'+o.areaNo+'" '
											+'>'+o.areaName+'</option>');
								});
								$(id2).selectbox({width:90}).selectRefresh();
							}else{
								$(id2).empty();//清空列表
								$(id2).append('<option value="">-请选择-</option>');
								$(id2).selectbox({width:90}).selectRefresh();
							}	
							$(id2).selectIndex(0);//默认选第一个
						});
						
						$(id1).selectIndex(0);//默认选第一个
					}	
				},
				
				/**
				 * 解析资源号类型
				 * @param inc 资源号
				 * @returns {String} 资源类型,M:管理公司、S:服务公司、T:托管企业、B:成员企业
				 */
				resolveInc:function(inc){
					if(inc.substr(2) == "000000000"){
						return "M";
					}else{
						if(inc.substr(5) == "000000"){
							return "S";
						}else{
							if(inc.substr(5,2) == "00"){
								return "B";
							}else{
								return "T";
							}
						}
					}
				},
				
				
				//上传组件
				ajaxFileUpload:function(fileElementId, realPath,picName,uploadImageId){
					top.ipro_alert("文件上传中...");
					$.ajaxFileUpload({
						// 处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
						url : comm.domainList.server+'chk/file/fileUploadNew?type=default&fileType=image',
						secureuri : false, // 是否启用安全提交,默认为false
						fileElementId : fileElementId, // 文件选择框的id属性
						dataType : 'json', // 服务器返回的格式,可以是json或xml等
						type : "POST",
						success : function(data) { // 服务器响应成功时的处理函数
							if(data.respCode=='success'){
								if(data.result[0].success){
									$("img[id=" + uploadImageId + "]").attr("src",comm.imgFtpServer+data.result[0].finalAbsFileName);
									$('#' + realPath).val(data.result[0].finalAbsFileName);									
									top.i_close();
								}else{
									top.i_close();
									if(data.result[0].state=='205'){
										top.i_alert("上传文件超过限定大小！");
									}else{
										top.i_alert("图片上传失败:"+data.result[0].state);
									}
								}
							}else {
								top.i_close();
								top.error_alert("图片上传失败");
							}
						},
						error : function(data, status, e) { // 服务器响应失败时的处理函数
							top.i_close();
							top.error_alert("图片上传不成功！！");
						}
					});
				},
				
				//刷新验证码
				refresh:function(obj){
					obj.src =comm.domainList.server+"validateCode?" + Math.random();
				},
				
				i_alert:function(text,width){
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
				},
				
				ipro_alert:function(text){
					$("#i_content").text(text);
					$("#alert_i").dialog({ title:"提示信息",width:"400"});
				},
				i_close:function(){
					$("#alert_i").dialog( "destroy" );
				},
				error_alert:function(text,width){
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
	};

	top.addDate_format();
	top.addMethod_validator();
	return top;
});

