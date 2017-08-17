define(['text!toolmanageTpl/ckkcgl/ckxx.html',
		'text!toolmanageTpl/ckkcgl/ckxx_tj_dialog.html',
		'text!toolmanageTpl/ckkcgl/ckxx_ck_dialog.html',
		'text!toolmanageTpl/ckkcgl/ckxx_xg_dialog.html',
		'toolmanageDat/ckkcgl/ckxx',
        'toolmanageLan'
		], function(ckxxTpl, ckxx_tj_dialogTpl, ckxx_ck_dialogTpl, ckxx_xg_dialogTpl, dataModoule){
	return {
		self_ckxx : null,
		showPage : function(){
			self_ckxx = this;
			self_ckxx.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#busibox').html(_.template(ckxxTpl));
			
			$('#queryBtn').click(function(){
				self_ckxx.initData();
			});
			
			//添加仓库
			$('#tjck_btn').click(function(){
				$('#dialogBox_tj').html(_.template(ckxx_tj_dialogTpl));
				/*弹出框*/
				$( "#dialogBox_tj" ).dialog({
					title:"添加仓库",
					width:"600",
			 		height: 450 ,
					buttons:{ 
						"确定":function(){
							self_ckxx.saveData("#tj_wh_form", "#dialogBox_tj", "","");
						},
						"取消":function(){
							$(this).dialog("destroy");
							$(this).html("");
						}
					}
				});
				comm.initProvincePlugin("#cityPlugin", null, 280, 150, false);
				 $(".city-uncheck.bottom-line").css("display","none");
				if(self_ckxx.roleList){
					comm.initToolSelect("#whRoleId", self_ckxx.roleList, 280, null, null, 150, "roleId", "roleName");
				}else{
					dataModoule.findListRole(null, function(res){
						self_ckxx.roleList = res.data;
						comm.initToolSelect("#whRoleId", res.data, 280, null, null, 150, "roleId", "roleName");
					});
				}
				$(".selectCity-box").css("z-index", 9);
				$(".selectList-span")[0].click();
			});
			
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			cacheUtil.findProvCity();
			var params = {};
			params.search_whName = $("#search_whName").val().trim();
			if(self_ckxx.roleList){
				dataModoule.findWarehouseList(params, self_ckxx.detail, self_ckxx.update);
			}else{
				dataModoule.findListRole(null, function(res){
					self_ckxx.roleList = res.data;
					dataModoule.findWarehouseList(params, self_ckxx.detail, self_ckxx.update);
				});
			}
			
			 $("#whRoleId").focusin(function() {
			        if($(this).val() =="请输入"){  
			            $(this).val("");
			        }
			    });
			    $("#whRoleId").focusout(function() {
			        if($(this).val() ==""){ 
			            $(this).val("请输入");
			        }
			    });

			
		},
		/**
		 * 查看动作
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 1){
				return comm.getProvinceNames(record.whArea);
			}else if(colIndex == 3){
				return comm.getNameByEnumId(record['isDefault'], comm.lang("toolmanage").isDefaultEnum);
			}else if(colIndex == 4){
				return self_ckxx.getRoleName(record.whRoleId);
			}
			return $('<a>查看</a>').click(function(e) {
				self_ckxx.chaKan(record.whId, record.whArea);
			}.bind(this));
		},
		/**
		 * 获取角色名称
		 */
		getRoleName : function(roleId){
			var roleList = self_ckxx.roleList;
			if(!roleList || !roleId){
				return "";
			}
			for(var key in roleList){
				if(roleList[key].roleId == roleId){
					return roleList[key].roleName;
				}
			}
			return "";
		},
		/**
		 * 修改动作
		 */
		update : function(record, rowIndex, colIndex, options){
			if(colIndex == 1 || colIndex == 3 || colIndex == 4){
				return null;
			}
			return $('<a>修改</a>').click(function(e) {
				self_ckxx.xiuGai(record.whId);
			}.bind(this));
		},
		/**
		 * 查看
		 */
		chaKan : function(whId, whArea){
			dataModoule.findWarehouseById({whId:whId}, function(res){
				var obj = res.data;
				obj.provinceNos = comm.getProvinceNames(whArea);
				obj.roleName = self_ckxx.getRoleName(obj.whRoleId);
				obj.isDefault = comm.getNameByEnumId(obj['isDefault'], comm.lang("toolmanage").isDefaultEnum);
				$('#dialogBox_ck').html(_.template(ckxx_ck_dialogTpl, obj));
				$( "#dialogBox_ck").dialog({
					title:"仓库详情",
					width:"600",
					modal:true,
					buttons:{ 
						"确定":function(){
							$(this).dialog("destroy");
						},
						"取消":function(){
							$(this).dialog("destroy");
							$(this).html("");
						}
					}
				});
			});
		},
		/**
		 * 表单校验
		 */
		validateData : function(formId){
			 return $(formId).validate({
				rules : {
					whName : {
						required : true,
						rangelength:[1, 20]
					},
					whAddr : {
						required : true,
						rangelength:[1, 128]
					},
					whRoleId : {
						required : true
					},
					remark : {
						maxlength:300
					},
					mobile : {
						required:true,
						mobileNo : true
					},
					phone : {
						rangelength:[0, 300]
					},
					cityPlugin:{
						required:true
					}
				},
				messages : {
					whName : {
						required : comm.lang("toolmanage")[36217],
						rangelength:comm.lang("toolmanage")[36221]
					},
					whAddr : {
						required : comm.lang("toolmanage")[36220],
						rangelength:comm.lang("toolmanage")[36222]
					},
					whRoleId : {
						required : comm.lang("toolmanage")[36218]
					},
					remark : {
						maxlength:comm.lang("toolmanage")[36223]
					},
					mobile : {
						required:comm.lang("toolmanage")[36244],
						mobileNo : comm.lang("toolmanage")[36018]
					},
					phone : {
						rangelength:comm.lang("toolmanage")[36249]
					},
					cityPlugin:{
						required:comm.lang("toolmanage")[36275]
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		},
		saveData : function(formId, dialogId, whId,isProvinceList){
			if(!self_ckxx.validateData(formId).form()){
				return;
			}
			cacheUtil.findCacheSystemInfo(function(sysRes){
				var params = {};
				params.whId = whId;
				params.whName = $("#whName").val();
				params.whRoleId = $("#whRoleId").attr('optionValue');
				params.whAddr = $("#whAddr").val();
				params.isDefault = $("#isDefault_1").attr("checked") == "checked";
				params.remark = $("#remark").val();
				params.provinceNos = $('#cityPlugin').attr('optionvalue');;
				params.mobile = $("#mobile").val();
				params.phone = $("#phone").val();
				params.countryNo = sysRes.countryNo;
				params.isProvinceList=isProvinceList;
				dataModoule.saveWarehouse(params, function(res){
					comm.alert({content:comm.lang("toolmanage")[22000], callOk:function(){
						self_ckxx.initData();
						$(dialogId).dialog("destroy");
						$(formId).html("");		
					}});
				});
			});
		},
		xiuGai : function(whId){
			dataModoule.findWarehouseById({whId:whId}, function(res){
				var obj = res.data;
				$('#dialogBox_xg').html(_.template(ckxx_xg_dialogTpl , obj));
				if(obj.isDefault){
					$("#isDefault_1").attr("checked", true);
				}else{
					$("#isDefault_0").attr("checked", true);
				}
				 var isProvinceList = "";
			      if(obj.whArea){
						for(var key in obj.whArea){
							isProvinceList+=obj.whArea[key].provinceNo+",";
						}
				 }
			     if(isProvinceList!=""&&isProvinceList!="null"&&isProvinceList!=null){
			    		 isProvinceList=isProvinceList.substring(0,isProvinceList.length-1);
			     }
				/*弹出框*/
				$( "#dialogBox_xg" ).dialog({
					title:"修改仓库",
					width:600,
					height: 450,
					modal:true,
					buttons:{ 
						"确定":function(){
							self_ckxx.saveData("#xg_wh_form", '#dialogBox_xg', obj.whId,isProvinceList);
						/*	comm.initProvincePlugin("#cityPlugin", obj.whArea, 280, 150, false);
							$(".city-uncheck.bottom-line").css("display","none");*/
						},
						"取消":function(){
							$(this).dialog("destroy");
							$(this).html("");
						}
					}
				});
				comm.initProvincePlugin("#cityPlugin", obj.whArea, 280, 150, false);
				$(".city-uncheck.bottom-line").css("display","none");
				if(self_ckxx.roleList){
					comm.initToolSelect("#whRoleId", self_ckxx.roleList, 305, comm.removeNull(obj.whRoleId), null, 150, "roleId", "roleName");
				}else{
					dataModoule.findListRole(null, function(res){
						self_ckxx.roleList = res.data;
						comm.initToolSelect("#whRoleId", res.data, 305, comm.removeNull(obj.whRoleId), null, 150, "roleId", "roleName");
					});
				}
				$(".selectCity-box").css("z-index", 9);
				$(".selectList-span")[0].click();
			});
		}	
	}	
});
