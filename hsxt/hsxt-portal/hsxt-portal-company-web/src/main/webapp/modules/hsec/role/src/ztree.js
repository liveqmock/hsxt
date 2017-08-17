define(function(){
	var tree = {
			bindParam : function(id,ajaxRequest,type,callback){
				var booltree = false;
				ajaxRequest.getRoleSysAuth("id="+id,function(response){
					var zNodes = new Array();
					var bool,styleClass,typeClick;
					if(type == "look"){
						typeClick = "return false";
						styleClass = "";
					}else{
						typeClick = "";
						styleClass = "roleCheck";
					}
					$.each(response.data,function(k,v){
						var arr = {};
						arr["id"] = v.id;
						arr["pId"] = v.parentId;
						if(v.related == true){
							booltree = true;
							bool = "checked";
						}else{
							bool = "";
						}
						arr["name"] = "<input type='checkbox' name='authCheckBox' class='"+styleClass+"' value='"+v.id+"' "+bool+" onclick='"+typeClick+"'/>&&"+v.name;
						zNodes.push(arr);
					})
					tree.bindStart(zNodes);
					if(type == "add"){
						callback(booltree);
					}
				})
			},
			bindStart : function(zNodes){
				var setting = {
					data: {
						simpleData: {
							enable: true
						}
					}
				};
		
				$(document).ready(function(){
					$.fn.zTree.init($("#treeDemo"), setting, zNodes);
					$("#dlg33").on("click",".roleCheck",function(){
						var count = $(this).parents("li").attr("class").split("level")[1];
						if($(this).prop('checked')){
							for(var i = (count-1); -1 < i; i--){
								var parentObj = $(this).parents(".level"+i);
								$(parentObj).children("a").find(".roleCheck").prop('checked',true);
							}
						}else{
							$(this).parents(".level"+count).find(".roleCheck").prop('checked',false);
							$("#allTree").prop('checked',false);
						}
					})
				});
			}
	}
	return tree;
	})


