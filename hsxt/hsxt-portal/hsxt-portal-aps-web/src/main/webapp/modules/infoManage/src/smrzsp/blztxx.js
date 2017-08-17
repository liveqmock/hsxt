define(['text!infoManageTpl/smrzsp/blztxx.html' ,
		'text!infoManageTpl/smrzsp/blztxx_ck_dialog.html'], 
		function(blztxxTpl, blztxx_ck_dialog){
	var blztxxPage = {
			
		obj : null,
		showPage : function(_obj){
			
			obj = _obj;
			
			$('#infobox').html(_.template(blztxxTpl,obj));
			
			$('#xfz_blztxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#xfzsmrzsp'),'');
			});
			$('#tgqy_blztxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#tgqysmrzsp'),'');
			});
			$('#cyqy_blztxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#cyqysmrzsp'),'');
			});
			$('#fwgs_blztxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#fwgssmrzsp'),'');
			});
			$('#xfzspcx_blztxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#xfzsmrzspcx'),'');
			});
			$('#qyspcx_blztxx_cancel').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#qysmrzspcx'),'');
			});
			
			var gridObj;
			
			if(obj.num == "xfz" ||obj.num == "xfzspcx" )
			{
				gridObj = comm.getCommBsGrid("searchTable4blztxx","perrealnameidentific_status_list",{applyId : obj.applyId},comm.lang("infoManage"),blztxxPage.detail);
			}
			else
			{
				gridObj = comm.getCommBsGrid("searchTable4blztxx","entrealnameidentific_status_list",{applyId : obj.applyId},comm.lang("infoManage"),blztxxPage.detail);
			}
			
			
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 0){
				return comm.lang("infoManage").realNameAuthBizAction[record.bizAction];
			}else if(colIndex == 4){
				
				var link1 = $('<a>'+comm.lang("infoManage").query+'</a>').click(function(e) {
					
					blztxxPage.chaKan(record);
					
				}.bind(this) ) ;
				
				return link1;
			}
		},
		chaKan : function(record){
			
			record.num = obj.num;
			if(obj.num == "xfz"||obj.num == "xfzspcx")
			{
				
				$('#confirm_dialogBox > div').html(_.template(blztxx_ck_dialog, record));
				//加载变更项
				var change = record.changeContent;
				var trs = "";
				if(change == null || change == "" || (record.bizAction != 2 && record.bizAction != 5))
				{
					trs = "";
				}
				else
				{
					var changContent = JSON.parse(change);
					for(key in changContent){
						if(key == 'cerpica' || key == 'cerpicb' || key == 'cerpich' )
						{
							trs = trs + "<tr><td class='view_item'>" + comm.lang("infoManage").smrzPersonItem[key] + "</td><td class='view_text'>" +
							"<span class='fl ml5 mt5 sample_img' style='margin-bottom:2px'><img  src='"+comm.getFsServerUrl(changContent[key].old)+"' width='81' height='52' /></span></td>" +
							"<td class='view_text'>" + 
							"<span class='fl ml5 mt5 sample_img' style='margin-bottom:2px'><img  src='"+comm.getFsServerUrl(changContent[key]["new"])+"' width='81' height='52' /></span></td></tr>";
						}
						else if(key == 'birthAddr' || key == 'licenceIssuing')
						{
							if(obj.per.certype == '2'){
								trs = trs + "<tr><td class='view_item'>" + comm.removeNull(comm.lang("infoManage").smrzPersonItem[key + "2"]) +"</td>" +
								"<td class='view_text'>" + changContent[key].old + "</td><td class='view_text'>" + changContent[key]["new"] + "</td></tr>";
							}else{
								trs = trs + "<tr><td class='view_item'>" + comm.removeNull(comm.lang("infoManage").smrzPersonItem[key]) +"</td>" +
								"<td class='view_text'>" + changContent[key].old + "</td><td class='view_text'>" + changContent[key]["new"] + "</td></tr>";
							}
						}
						else
						{
							trs = trs + "<tr><td class='view_item'>" + comm.removeNull(comm.lang("infoManage").smrzPersonItem[key]) +"</td>" +
							"<td class='view_text'>" + changContent[key].old + "</td><td class='view_text'>" + changContent[key]["new"] + "</td></tr>";
						}
					}
				}
				
				//为了页面美观，至少要3个tr
				var n = trs.split("<tr>").length;
				if(n < 3){
					if(trs == ""){
						trs = trs + "<tr ><td class='view_text' rowspan='"+(3-n)+"' colspan='3' style='text-align: center'>"+comm.lang("infoManage").blztxxNoUpdate+"</td></tr>";
					}else{
						trs = trs + "<tr ><td class='view_text' rowspan='"+(3-n)+"' colspan='3' style='text-align: center'><hr style='width:30%;height:1px;border:none;border-top:1px dashed #000000;'></td></tr>";
					}
					for(var i = 0; i < 2-n ; i++){
						trs = trs + "<tr><td class='view_text' colspan='3'></td></tr>";
					}
				}
				
				//将修改项加到table中
				$('#personTab tr:eq(1)').before(trs);
				
				//设置证件照url
				comm.initPicPreview("#cerpica", obj.per.cerpica);
				comm.initPicPreview("#cerpicb", obj.per.cerpicb);
				comm.initPicPreview("#cerpich", obj.per.cerpich);
				
				//只有身份证才有证件反面附件
				if(obj.per.certype != '1'){
					$("#cerpicb").next().remove();
					$("#cerpicb").remove();
				}
			}
			else
			{
				$('#confirm_dialogBox > div').html(_.template(blztxx_ck_dialog, record));
				//加载变更项
				var change = record.changeContent;
				var trs = "";
				if(change == null || change == "" || (record.bizAction != 2 && record.bizAction != 5))
				{
//					trs = "<tr><td  class='view_item'></td><td class='view_text' colspan='2'></td></tr>";
				}
				else
				{
					var changContent = JSON.parse(change);
					for(key in changContent){
						if(key == 'lrcFacePic' || key == 'lrcBackPic' || key == 'licensePic'|| key == 'orgPic' || key == 'taxPic' || key=='certificatePic' )
						{
							trs = trs + "<tr><td class='view_item'>" + comm.lang("infoManage").smrzEntItem[key] + "</td><td class='view_text'>" +
							"<span class='fl ml5 mt5 sample_img' style='margin-bottom:2px'><img  src='"+comm.getFsServerUrl(changContent[key].old)+"' width='81' height='52' /></span></td>" +
							"<td class='view_text'>" + 
							"<span class='fl ml5 mt5 sample_img' style='margin-bottom:2px'><img  src='"+comm.getFsServerUrl(changContent[key]["new"])+"' width='81' height='52' /></span></td></tr>";
						}
						else
						{
							trs = trs + "<tr><td class='view_item'>" + comm.lang("infoManage").smrzEntItem[key] +"</td>" +
							"<td class='view_text' style='word-wrap:break-word;word-break:break-all;'>" + changContent[key].old + "</td><td class='view_text'>" + changContent[key]["new"] + "</td></tr>";
						}
					}
				}
				
				//将修改项加到table中
				$('#entTab tr:eq(1)').before(trs);
				
				comm.initPicPreview("#dialog_licensePic", obj.ent.licensePic);
			}
			
			/*弹出框*/
			$( "#confirm_dialogBox" ).dialog({
				title:comm.lang("infoManage").statusInfo,
				modal:true,
				width:"800",
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