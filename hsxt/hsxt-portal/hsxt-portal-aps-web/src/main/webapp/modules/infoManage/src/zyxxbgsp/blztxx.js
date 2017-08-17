define(['text!infoManageTpl/zyxxbgsp/blztxx.html' ,
		'text!infoManageTpl/zyxxbgsp/blztxx_ck_dialog.html'], 
		function(blztxxTpl, blztxx_ck_dialog){
	var blztxxPage = {
		obj : null,
		showPage : function(_obj){
			
			obj = _obj;
			
			$('#infobox').html(_.template(blztxxTpl,obj));
			
			//点击【取消】【返回】按钮返回列表页面
			$('#xfz_blztxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#xfzzyxxbgsp'),'');
			});
			$('#tgqy_blztxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#tgqyzyxxbgsp'),'');
			});
			$('#cyqy_blztxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#cyqyzyxxbgsp'),'');
			});
			$('#fwgs_blztxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#fwgszyxxbgsp'),'');
			});
			$('#xfzspcx_blztxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#xfzzyxxbgspcx'),'');
			});
			$('#qyspcx_blztxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#qyzyxxbgspcx'),'');
			});
			
			var gridObj;
			
			if(obj.num == "xfz" || obj.num == "xfzspcx")
			{
				gridObj = comm.getCommBsGrid("searchTable4blztxx","perimportantinfochange_status_list",{applyId : obj.applyId},comm.lang("infoManage"),blztxxPage.detail);
			}
			else
			{
				gridObj = comm.getCommBsGrid("searchTable4blztxx","entimportantinfochange_status_list",{applyId : obj.applyId},comm.lang("infoManage"),blztxxPage.detail);
			}
			
		},
		detail : function(record, rowIndex, colIndex, options){
			
			if(colIndex == 0){
				return comm.lang("infoManage").changeInfoBizAction[record.bizAction];
			}else if(colIndex == 4 ){
				
				var link1 = $('<a>'+comm.lang("infoManage").query+'</a>').click(function(e) {
					
					blztxxPage.chaKan(record);
					
				}.bind(this) ) ;
				
				return link1;
			}
		},
		chaKan : function(record){
			
			record.num = obj.num;
			
			$('#confirm_dialogBox > div').html(_.template(blztxx_ck_dialog, record));
			
			if(obj.num == "xfz" || obj.num == "xfzspcx")
			{
				cacheUtil.findCacheContryAll(function(contryList){
					$('#confirm_dialogBox > div').html(_.template(blztxx_ck_dialog, record));
					//加载变更项
					var change = record.changeContent;
					var trs = "";
					var tus = "";
					if(change == null || change == "")
					{
						trs = "<tr><td  class='view_item'></td><td class='view_text' colspan='2'></td></tr>";
					}
					else
					{
						var changContent = JSON.parse(change);
						for(key in changContent){
							//证件类型
							if(key == 'creTypeNew'){
								trs = trs + "<tr><td class='view_item'>" + comm.lang("infoManage").zyxxPersonItem[key] +"</td>" +
								"<td class='view_text'>" + comm.removeNull(comm.lang("infoManage").realNameCreType[changContent[key].old]) + "</td><td class='view_text'>" + comm.removeNull(comm.lang("infoManage").legalCreType[comm.removeNull(changContent[key]["new"])]) + "</td></tr>";
							}
							//文件
							else if(key == 'creFacePicNew' || key == 'creBackPicNew' || key == 'creHoldPicNew' || key == 'residenceAddrPic')
							{
								tus = tus + "<tr><td class='view_item'>" + comm.lang("infoManage").zyxxPersonItem[key] + "</td><td class='view_text'>" +
								"<span class='fl ml5 mt5 sample_img' style='margin-bottom:2px'><img  src='"+comm.getFsServerUrl(changContent[key].old)+"' width='81' height='52' /></span></td>" +
								"<td class='view_text'>" + 
								"<span class='fl ml5 mt5 sample_img' style='margin-bottom:2px'><img  src='"+comm.getFsServerUrl(changContent[key]["new"])+"' width='81' height='52' /></span></td></tr>";
							}//性别
							else if(key == 'sexNew'){
								trs = trs + "<tr><td class='view_item'>" + comm.lang("infoManage").zyxxPersonItem[key] +"</td>" +
								"<td class='view_text'>" + comm.removeNull(comm.lang("infoManage").personSex[changContent[key].old]) + "</td><td class='view_text'>" + comm.removeNull(comm.lang("infoManage").personSex[comm.removeNull(changContent[key]["new"])]) + "</td></tr>";
							}//国籍
							else if(key == 'nationalityNew'){
									trs = trs + "<tr><td class='view_item'>" + comm.lang("infoManage").zyxxPersonItem[key] +"</td>" +
									"<td class='view_text'>" + comm.getCountryName(contryList, changContent[key].old) + "</td><td class='view_text'>" + comm.getCountryName(contryList, comm.removeNull(changContent[key]["new"])) + "</td></tr>";
							}//其他
							else
							{
								if(obj.per.creTypeNew == 2 && (key == 'registorAddressNew' || key == 'creIssueOrgNew')){
									trs = trs + "<tr><td class='view_item'>" + comm.lang("infoManage").zyxxPersonItem[(key+"2")] +"</td>" +
									"<td class='view_text' style='word-wrap:break-word;word-break:break-all;'>" + changContent[key].old + "</td><td class='view_text' style='word-wrap:break-word;word-break:break-all;'>" + comm.removeNull(changContent[key]["new"]) + "</td></tr>";
								}else{
									trs = trs + "<tr><td class='view_item'>" + comm.lang("infoManage").zyxxPersonItem[key] +"</td>" +
									"<td class='view_text' style='word-wrap:break-word;word-break:break-all;'>" + changContent[key].old + "</td><td class='view_text' style='word-wrap:break-word;word-break:break-all;'>" + comm.removeNull(changContent[key]["new"]) + "</td></tr>";
								}
							}
						}
					}
					
					trs = trs + tus;
					
					//为了页面美观，至少要2个tr
					var n = trs.split("<tr>").length;
					if(n < 3){
						for(var i = 0; i < 3-n ; i++){
							trs = trs + "<tr><td class='view_item'></td><td class='view_text' colspan='2'></td></tr>";
						}
					}
					
					//将修改项加到table中
					$('#personTab tr:eq(1)').before(trs);
				
					//设置证件照url
					comm.initPicPreview("#cerpica", obj.per.creFacePicNew);
					comm.initPicPreview("#cerpicb", obj.per.creBackPicNew);
					comm.initPicPreview("#cerpich", obj.per.creHoldPicNew);
					comm.initPicPreview("#registorAddressPicOld", obj.per.residenceAddrPic);
					if(obj.per.creTypeNew == 2 || obj.per.creTypeNew == 3){
						$("#cerpicb").hide();
						$("#cerpicb").next().remove()
						$("#registorAddressPicOld").hide();
					}
				});
			}
			else
			{
				$('#confirm_dialogBox > div').html(_.template(blztxx_ck_dialog, record));
				//加载变更项
				var change = record.changeContent;
				var trs = "";
				var tu_trs = "";
				if(change == null || change == "")
				{
					trs = "<tr><td  class='view_item'></td><td class='view_text' colspan='2'></td></tr>";
				}
				else
				{
					var changContent = JSON.parse(change);
					for(key in changContent){
						if(key == 'bizLicenseCrePicNew'|| key == 'linkAuthorizePicNew' || key=='imptappPic' )
						{
							if(comm.isNotEmpty(changContent[key]["new"])){
								tu_trs = tu_trs + "<tr><td class='view_item'>" + comm.lang("infoManage").zyxxEntItem[key] + "</td><td class='view_text'>" +
								"<span class='fl ml5 mt5 sample_img' style='margin-bottom:2px'><img  src='"+comm.getFsServerUrl(changContent[key].old)+"' width='81' height='52' /></span></td>" +
								"<td class='view_text'>" + 
								"<span class='fl ml5 mt5 sample_img' style='margin-bottom:2px'><img  src='"+comm.getFsServerUrl(changContent[key]["new"])+"' width='81' height='52' /></span></td></tr>";
							}
						}
						else
						{
							trs = trs + "<tr><td class='view_item'>" + comm.lang("infoManage").zyxxEntItem[key] +"</td>" +
							"<td class='view_text' style='word-wrap:break-word;word-break:break-all;'>" + comm.removeNull(changContent[key].old) + "</td><td class='view_text' style='word-wrap:break-word;word-break:break-all;'>" + comm.removeNull(changContent[key]["new"]) + "</td></tr>";
						}
					}
				}
				trs = trs + tu_trs;
				
				
				//将修改项加到table中
				$('#entTab tr:eq(1)').before(trs);
				
				//设置证件照url
				var bizLicenseCrePicNew = comm.removeNull(obj.ent.bizLicenseCrePicNew);
				var linkAuthorizePicNew = comm.removeNull(obj.ent.linkAuthorizePicNew);
				var imptappPic = comm.removeNull(obj.ent.imptappPic);
				
				//营业执照
				comm.isEmpty(bizLicenseCrePicNew)?$("#bizLicenseCrePicNew").hide():comm.initPicPreview("#bizLicenseCrePicNew", bizLicenseCrePicNew);
				//联系人授权委托书
				comm.initPicPreview("#linkAuthorizePicNew", linkAuthorizePicNew);
				//重要信息变更业务办理申请书
				comm.initPicPreview("#imptappPic", imptappPic);
				
				//隐藏没有上传的附件
				if(comm.isEmpty(obj.ent.bizLicenseCrePicNew)){
					$("#bizLicenseCrePicNew").hide();
					$("#bizLicenseCrePicNew").next().remove();
				}
				if(comm.isEmpty(obj.ent.linkAuthorizePicNew)){
					$("#linkAuthorizePicNew").hide();
					$("#linkAuthorizePicNew").next().remove();
				}
				if(comm.isEmpty(obj.ent.imptappPic)){
					$("#imptappPic").hide();
					$("#imptappPic").next().remove();
				}
				
			}
			
			/*弹出框*/
			$( "#confirm_dialogBox" ).dialog({
				title:"重要信息变更申请提交",
				modal:true,
				width:"900",
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					},
					"取消":function(){
						$(this).dialog("destroy");
					}
				}
		  });	
		  /*end*/
				  	
		}	
	};
	return blztxxPage
});