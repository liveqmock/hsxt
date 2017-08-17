define(['text!infoManageTpl/glqyxxgl/glqyxxwh.html',
        'infoManageDat/infoManage'], function(glqyxxwhTpl,infoManage){
	var self = null;
	return {
		showPage : function(){
			self = this;
			$('#busibox').html(_.template(glqyxxwhTpl));
			$("#qry_qy_btn").click(function(){
				//查询参数
				var params={
								search_entResNo:comm.getSysCookie('entResNo'),//企业互生号
								search_belongEntResNo:$.trim($('#search_belongEntResNo').val()),	
								search_belongEntName:$.trim($('#search_belongEntName').val())	
							};
				gridObj = comm.getCommBsGrid("searchTable","queryRelateCompanyList",params,comm.lang("infoManage"),self.update,self.detail);
			});
			
			$("#qry_jl_btn").click(function(){
				self.xiuGaijl();
			});
		},
		
		detail : function(record, rowIndex, colIndex, options){
			
			if(colIndex == 6){//查看
				 var link1 = $('<a>'+comm.lang("infoManage").updateRecordTitle+'</a>').click(function(e){
					 
					 	$("#belongEntCustId").val(record.entCustId);
					 	
						self.xiuGaijl();
						
						$('#glqyxxwh_jl').dialog({
						 	width:900,
						 	closeIcon:true,
						 	title: comm.lang("infoManage").updateRecordTitle,
						 	buttons:{
						 		'确定':function(){
						 			$('#glqyxxwh_jl').dialog('destroy');
//						 			$("#searchTable_pt_refreshPage").click();
						 		}
						 	}
						});
					}.bind(this));
				 return link1 ;
			}
		},
		update : function(record, rowIndex, colIndex, options){
			if(colIndex == 6){//查看
				 var link1 = $('<a>'+comm.lang("infoManage").updateTitle+'</a>').click(function(e) {
						var obj = gridObj.getRecord(rowIndex);
						comm.liActive_add($('#xgqyxx'));
						require(['infoManageSrc/glqyxxgl/sub_tab_2'], function(subTab2){
							subTab2.showPage(obj);
						});
					}.bind(this) ) ;
				 return link1 ;
			}
		},
		chaKan : function(obj){
			require(['infoManageSrc/zyxxbgsp/sub_tab'], function(tab){
				tab.showPage(obj);
			});
		},
		xiuGaijl : function(){
			 
			 var params = {};
			 params.search_belongEntCustId = $("#belongEntCustId").val();//企业客户ID
			 params.search_updateFieldName = $("#updateFieldName").val();
			 //初始化修改记录表格
			 gridObjs = comm.getCommBsGrid("xgjl_table","queryEntInfoBak",params,comm.lang("infoManage"),function(record, rowIndex, colIndex, options){
				 if(colIndex == 2){
					 var obj = gridObjs.getRecord(rowIndex);
					 var fileId = obj.updateField;
					 var oldValue = obj.updateValueOld;
					 return self.initDataType(fileId,oldValue,colIndex,rowIndex);
				 }else if(colIndex == 3){
					 var obj = gridObjs.getRecord(rowIndex);
					 var fileId = obj.updateField;
					 var newValue = obj.updateValueNew;
					 return self.initDataType(fileId,newValue,colIndex,rowIndex);
				 }
			 });
			 
		},
		initDataType : function(fileId,value,colIndex,rowIndex){
			var link;
			//图片查看链接
			if("busiLicenseImg" == fileId || "orgCodeImg" == fileId || "taxRegImg" == fileId || "legalPersonPicFront" == fileId
					|| "legalPersonPicBack" == fileId || "contactProxy" == fileId || "helpAgreement" == fileId){
				if(value){
					link = $('<a id="search_'+rowIndex+colIndex+'">查看图片</a>').click(function(e){
						comm.initPicPreview("#search_"+rowIndex+colIndex, value, "");
					}.bind(this));
				} 
			}else{
				link = "<span title="+comm.removeNull(value)+">"+comm.removeNull(value)+"</span>";
			}
			return link;
		}
	}	
});