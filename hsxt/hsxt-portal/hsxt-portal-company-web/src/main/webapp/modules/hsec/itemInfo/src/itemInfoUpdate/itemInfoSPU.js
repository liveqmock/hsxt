define([ "hsec_tablePointSrc/tablePoint" ], function(tablePoint) {
	var imgDeletearray = new Array();
	/**
	 * 价格快速输入事件
	 */
	function priceInput(obj){
				var type = $(obj).attr("type");
				var index = $(obj).parents('td').index();
				var inputnum = $.trim($(obj).parents('td').find("input").val());
				var numAll;
				//应用到全部文本框
				if (type == 0) {
					$("#tablesku tbody:eq(1) tr").each(function(numIndex) {
								numAll = $(this).children("td:eq(" + index + ")").find("input");
								if (numAll.hasClass("price1")) {
									$(this).children("td").find("input").val('');
								}
								numAll.val(inputnum);
								if (numAll.hasClass("jifen1")) {
										jifen1($(".jifen1:eq(" + numIndex + ")"), 0);//积分触发事件
								}

								if (numAll.hasClass("jifen2")) {
										jifen2($(".jifen2:eq(" + numIndex + ")"), 0);//积分百分比触发事件
								}
					})
				}
				//应用到未填写文本框
				else if (type == 1) {
					$("#tablesku tbody:eq(1) tr").each(function(numIndex) {
								numAll = $(this).children("td:eq(" + index + ")").find("input");
								if ('' == $.trim(numAll.val())) {
									if (numAll.hasClass("price1")) {
										$(this).children("td").find("input").val('');
									}
									numAll.val(inputnum);
									if (numAll.hasClass("jifen1")) {
											jifen1($(".jifen1:eq(" + numIndex + ")"), 0); //积分触发事件
									}
									if (numAll.hasClass("jifen2")) {
											jifen2($(".jifen2:eq(" + numIndex + ")"), 0); //积分百分比触发事件
									}
									
								}
							})
				}
			};
			//	价格触发文本清空
	function priceUpdate(obj){
		var x = $(obj).parents('tr').index();
		var y = $(obj).parents('td').index();
		var tbody = $("#tablesku tbody:eq(1)");
		$(obj).on('keyup',function(){
					for ( var i = 1; i < 5; i++) {
						tbody.children("tr:eq(" + x + ")").children("td:eq(" + (y + i) + ")").find("input").val('');
					}
				})
	}

	 function jifen1(obj, state){
		var x = $(obj).parents('tr').index();
		var y = $(obj).parents('td').index();
		var price = 0;
		var tbody = $("#tablesku tbody:eq(1)").children("tr:eq(" + x + ")");
		price = $.trim(tbody.children("td:eq(" + (y - 1) + ")").find("input").val());
		if(price == null || price == 0){
			tablePoint.tablePoint("请先填写价格!");
			return false;
		}
				if(state == 0){
					var jifen = $(obj).val();
					var baifenbi = (jifen / price * 100).toFixed(2);
					tbody.children("td:eq(" + (y + 1) + ")").find("input").val(baifenbi * 2);
					tbody.children("td:eq(" + (y + 2) + ")").find("input").val(jifen * 2);
					tbody.children("td:eq(" + (y + 3) + ")").find("input").val(jifen);
				}else{
					$(obj).on('keyup',function(){
						var jifen = $(this).val();
						var baifenbi = (jifen / price * 100).toFixed(2);
						tbody.children("td:eq(" + (y + 1) + ")").find("input").val(baifenbi * 2);
						tbody.children("td:eq(" + (y + 2) + ")").find("input").val(jifen * 2);
						tbody.children("td:eq(" + (y + 3) + ")").find("input").val(jifen);
					})
				}
	}
	

	function jifen2(obj, state) {
		var x = $(obj).parents('tr').index();
		var y = $(obj).parents('td').index();
		var price = 0;
		var tbody = $("#tablesku tbody:eq(1)").children("tr:eq(" + x + ")");
		price = $.trim(tbody.children("td:eq(" + (y - 2) + ")").find("input").val());
		if(price == null || price == 0){
			tablePoint.tablePoint("请先填写价格!");
			return false;
		}
				if(state == 0){
					var baifenbi = $(obj).val();
					var jifenzhi = (price * (baifenbi / 100)).toFixed(2);
					tbody.children("td:eq(" + (y - 1) + ")").find("input").val(jifenzhi / 2);
					tbody.children("td:eq(" + (y + 1) + ")").find("input").val(jifenzhi);
					tbody.children("td:eq(" + (y + 2) + ")").find("input").val(jifenzhi / 2);
				}else{
					$(obj).on('keyup',function(){
						var baifenbi = $(this).val();
						var jifenzhi = (price * (baifenbi / 100)).toFixed(2);
						tbody.children("td:eq(" + (y - 1) + ")").find("input").val(jifenzhi / 2);
						tbody.children("td:eq(" + (y + 1) + ")").find("input").val(jifenzhi);
						tbody.children("td:eq(" + (y + 2) + ")").find("input").val(jifenzhi / 2);
					})
				}
	}
	
	
	
	//生成SKU页面
	function skuInput(ajax,obj){
		 step.Creat_Table();	
		 $(obj).each(function(){
					//图片脚本加载
					var isColor	= $(this).attr("isColor");
					if(isColor == 1 && $(this).prop("checked")){
						var color = $(this).val().split("&");
						ImgLoad(color,isColor);
					}else{
						var color = $(this).val().split("&");
						$.each($("#imgLoad").find("li"),function(index,value){
							if($(this).attr("colorId") == color[0]){
								$(this).remove();
								var colorid = $(this).attr("colorid");
								
								if($(this).find(".deleteImgsku").attr("imgid") != null){
									var param = {};
									param["id"] = "";
									param["colorId"] = colorid;
									imgDeletearray.push(param);
								}
								for(var i = (itemskuimg.length-1);i >= 0;i--){
									if(itemskuimg[i].colorId == colorid){
										itemskuimg.remove(i);
									}
								}
								return;
							}
						})
					 }
			});
	
		//图片上传
		$(".fileimgsku").unbind().on('change',function(){
			imgSkuUpload(ajax,this);
		})
	};
	var itemskuimg = new Array();//保存sku生成的图片集合 
		
	var count = 0;
	function ImgLoad(color,isColor) {
		$("#imgLoad").append("<li colorId='"+color[0]+"'><div class=\"upload_hd\" style='width:80px'><span>"+color[1]+"</span></div>"
                          +"<b id='imgskushow"+count+"' ></b>"
                          +"<span class=\"action\" style='position:relative;'>"
                          +"<input type='file' style='float :left' name='imgsku"+(count)+"' class='fileimgsku inputFile' id='imgsku"+(count++)+"'>"
                          +"<input type='button' class='loadpic' value='上传图片'>"
                          +"</span>"
                        +"</li>");
	}
	/**SKU图片上传*/
	var imgcount = 0;
	function imgSkuUpload(ajax,obj){
		var skuImgCheck = $(obj).parents("li");
		if(skuImgCheck.length > 0){
			var bool = false;
			$.each($(skuImgCheck).find("b"),function(k,v){
				if($(v).find("img").length > 9){
					tablePoint.tablePoint("请上传正确格式!如:JPG,JPEG,PNG,GIF,BMP,不超过1024KB,不超过5张!建议图片大于700X700!");
					bool = true;
					return false;
				}
			})
			if(bool){
				return false;
			}
		}else{
			tablePoint.tablePoint("请上传正确格式!如:JPG,JPEG,PNG,GIF,BMP,不超过1024KB,不超过5张!建议图片大于700X700!");
			return false;
		}
		
		if(itemskuimg.length > 55){
			tablePoint.tablePoint("SKU图片上传总数不能超过55张,请重新选择上传图片!");
			return false;
		}
		
        var file = $(obj).val();  
        var htmlUpload = $(obj).html();
        var fileType = file.substring(file.lastIndexOf(".")+1);  
        var size = 0;
        if (document.all){
        	
        }else{
        	size = obj.files[0].size;
        	size = tablePoint.formatFileSize(size);
        }
        if(("JPG,JPEG,PNG,GIF,BMP").indexOf(fileType.toUpperCase()) == -1 || size > 1024){  
        	tablePoint.tablePoint("请上传正确格式!如:JPG,JPEG,PNG,GIF,BMP,不超过1024KB,不超过5张!建议图片大于700X700!");
            return false;  
        }else{  
        	var id = $(obj).attr("id");
        	var colorid = $(obj).parents("li").attr("colorid");
        	var param = {};
			        	var imgobj = $(obj).parents("li");
			        	tablePoint.bindItemSKUJiazai(imgobj,true);
    				ajax.upLoadFile(id, function(response) {
    					tablePoint.bindItemSKUJiazai(imgobj,false);
						param["colorId"] = colorid;
						param["response"] = response;
						imgcount++;
						param["index"] = imgcount;
						itemskuimg.push(param);
						var urlimg,imgName;
						   $.each(eval(response), function(i, items) {
							   urlimg = items.httpUrl;
							   $.each(items.imageNails, function(j, item) {
								   if(item.height=="68" && item.width=="68"){
									   imgName = item.imageName;
								   }
					        });
					});
						   
						   $("#imgskushow"+id.split("imgsku")[1]).prepend("<b style='position: relative;display: inline-block;'><img src="+urlimg+imgName+"><img src='resources/img/delete.png' width='10px' height='10px' style='position: absolute;top: 0px;right: 0px;' class='deleteImgsku' imgIndex='"+imgcount+"'></b>");   
						   step.bindTableClick();
    				});
        
        $("#itemPhoto").replaceWith(htmlUpload);
    	//图片上传
		$(".fileimgsku").unbind().on('change',function(){
			imgSkuUpload(ajax,this);
		})
     } 	
};
	/**
	 * 页面注意事项： 1、 .div_contentlist 这个类变化这里的js单击事件类名也要改 2、 .Father_Title
	 * 这个类作用是取到所有标题的值，赋给表格，如有改变JS也应相应改动 3、 .Father_Item
	 * 这个类作用是取类型组数，有多少类型就添加相应的类名：如: Father_Item1、Father_Item2、Father_Item3 ...
	 */
	// SKU信息
var inputData  = new Array() ;
	var step = {
		InitSpuSku : function(ajax,sku) {
			if(count != null){
				count = 0;
			}
			if(imgcount != null){
				imgcount = 0;
			}
			if(itemskuimg != null){
				itemskuimg.length = 0;
			}
			if(imgDeletearray != null){
				imgDeletearray.length = 0;
			}
			$("#skuMain").on("click",".skuName",function(e){
				var isCheckOk = $(this).find(".sku");
				var skuIdValue = $(isCheckOk).val().split("&");
				if(skuIdValue.length > 1){
					if($.trim(skuIdValue[0]) == null || $.trim(skuIdValue[0]) == ""){
						$(this).parent().remove();
						return false;
					}
					if($.trim(skuIdValue[1]) == null || $.trim(skuIdValue[1]) == ""){
						tablePoint.tablePoint("请输入属性值,属性值不能为空!");
						return false;
					}
				}else{
					if($.trim(skuIdValue[0]) == null || $.trim(skuIdValue[0]) == ""){
						$(this).parent().remove();
						return false;
					}else{
						tablePoint.tablePoint("请输入属性值,属性值不能为空!");
						return false;
					}
				}
				if(e.target != isCheckOk[0]){
					if($(isCheckOk).is(":checked")){
						$(isCheckOk).attr("checked",false);
					}else{
						$(isCheckOk).attr("checked",true);
					}
				}
				skuInput(ajax,isCheckOk);
			});
			
			 skuInput(ajax,$(".sku")); 
			 /**商品信息销售属性设置(SKU)*/
			 $("#createTable").on('click','.sale_set',function(){
				var $popup=$(this).children(".popup_set");
				$popup.is(":visible")?$popup.hide():$popup.show()
				});
			// 积分百分比修改事件
			$("#createTable").on('focus','.jifen2',function(){
				jifen2(this, 1);
			});
			// 积分值修改事件
			$("#createTable").on('focus','.jifen1',function(){
				jifen1(this, 1);
			});
			// 价格触发文本框清空事件
			$("#createTable").on('focus','.price1',function(){
				priceUpdate(this);
			});
			//价格快速输入
			$("#createTable").on('click','.insertnum',function(){
				priceInput($(this));
			});
			
			//spu输入验证
			$("#createTable").on('focus','.inputCheck',function(){
				$(this).on('keyup',function(){
					var num = $(this).val();
					var p1=/\.\d{3,}/;
					if(!isNaN(num) && p1.test(num)){
						$(this).val((new Number(num)).toFixed(2));
					}
					if(isNaN(num)){
						$(this).val("");
					}
				})
			});
			//sku修改初始化
		$('#tablesku tr:gt(0)').each(function(key2,trObj){
		 $(sku.lstSku).each(function(key1,value){
			 	var skus = value.sku;
				var bool = $(trObj).attr("bool");
				if(bool == skus){
					$(trObj).attr("skuid",value.skuId);
					$(trObj).find(".price1").val(value.price);
					$(trObj).find(".jifen1").val(value.auction);
					
					
					var jifen = value.auction;
					var baifenbi = (jifen / value.price * 100).toFixed(2);
					var length = $(trObj).children("td").length-1;
					$(trObj).children("td:eq("+(length-2)+")").find("input").val(baifenbi * 2);
					$(trObj).children("td:eq("+(length-1)+")").find("input").val(jifen * 2);
					$(trObj).children("td:eq("+length+")").find("input").val(jifen);
					sku.lstSku.splice(key1, 1);
					return false;
				}
			});
		 });
	
		 var li = $("#imgLoad").find("li");
		 $(li).each(function(key,value){
			var obj = $(this);
			var colorid = $(this).attr("colorid");
			var imgurl = '';
			$(sku.lstGroupByPic).each(function(index,el){
				
				if(colorid == el.colorId){
					$(eval('('+el.PIC+')')).each(function(count,picel){
						var url = picel.picUrl;
						var img = picel.webPicUrl;
						imgurl = url+eval('('+img+')')["68x68"];
						$(obj).find("b:first").prepend("<b style='position: relative;display: inline-block;'><img src="+imgurl+" imgid='"+picel.id+"'><img src='resources/img/delete.png' width='10px' height='10px' style='position: absolute;top: 0px;right: 0px;' class='deleteImgsku' imgid='"+picel.tfsFileName+"'></b>");  
					})
					sku.lstGroupByPic.splice(index, 1);
					return false;
				}
				
			})
			
		 })
		 step.bindSKUTool();	
		 step.bindTableClick();
		}, 
		// SKU信息组合
		Creat_Table : function() {
			step.hebingFunction();
			var SKUObj = $(".skuDl");
			// var skuCount = SKUObj.length;//
			var arrayTile = new Array();// 标题组数
			var arrayInfor = new Array();// 盛放每组选中的CheckBox值的对象
			var arrayColumn = new Array();// 指定列，用来合并哪些列
			var bCheck = true;// 是否全选
			var columnIndex = 0;
			$.each(SKUObj, function(i, item) {
				arrayColumn.push(columnIndex);
				columnIndex++;
				arrayTile.push(SKUObj.find("dt").eq(i).attr("items")+"+"+SKUObj.find("dt").eq(i).attr("isColor"));
				var itemName = "Father_Item" + i;
				// 选中的CHeckBox取值
				var order = new Array();
				$("." + itemName + " input[type=checkbox]:checked").each(function() {
							order.push($(this).val());
						});
				arrayInfor.push(order);
				if (order.join() == "") {
					bCheck = false;
				}
				// var skuValue = SKUObj.find("li").eq(index).html();
			});
			// 开始创建Table表
			if (bCheck == true) {
		$('#tablesku tr:gt(0)').each(function(key,trObj){
					
					inputData[key] = new Array() ;
					var bool = $(trObj).attr("bool");
					var skuid = $(trObj).attr("skuid");
					$(trObj).find("input").each(function(colKey,input){
						inputData[key][colKey] = $(input).val()+"#"+bool+"#"+skuid;
					}) 
					
					
				});
				
				
				var RowsCount = 0;
				$("#createTable").html("");
				var table = $("<table style=\"word-break: break-all; word-wrap: break-word;\" id=\"tablesku\"  class=\"tc bg_lvse\" width=\"100%\" border=\"1\" bordercolor=\"white\" cellspacing=\"0\" cellpadding=\"0\"></table>");
				table.appendTo($("#createTable"));
				var trHead = $("<tr class=\"tc bg_grey\" height=\"34\"></tr>");
				trHead.appendTo(table);
				// 创建表头
				$.each(arrayTile, function(index, item) {
					var strcolor = item.split("+");
					var td = $("<td width='60px' class=\"bd_r\" items='" + strcolor[0] + "' isColor='" + strcolor[1] + "'>"
							+ strcolor[0].split("&")[1] + "</td>");
					td.appendTo(trHead);
				});
				var itemColumHead = $("<td class='bd_r'><img src='resources/img/price.png' width='20' style='vertical-align:middle;'>价格"
						+ "<input type='text' maxlength='12' class='inputCheck'/>"
						+ "<span class='set sale_set' id='sale_set'>"
						+ "<div class='popup_set' id='popup_set'>" + "<ul>"
						+ "<li class='cur insertnum' type='0'>应用到全部SKU</li>"
						+ " <li class='insertnum' type='1'>应用到未填写SKU</li>"
						+ "</ul>" + " </div>" + "</span>" + "</td>"
						+ "  <td class='bd_r'><img src='resources/img/pv.png' width='25' style='vertical-align:middle;'>积分值" + "<input type='text' maxlength='12' class='inputCheck'/>"
						+ " <span class='set sale_set'>"
						+ " <div class='popup_set'>" + "<ul>"
						+ "  <li class='cur insertnum' type='0'>应用到全部SKU</li>"
						+ "   <li class='insertnum' type='1'>应用到未填写SKU</li>"
						+ " </ul>" + " </div></span>" + " </td>"
						+ " <td class='bd_r'>积分比例" + "<input type='text' maxlength='12' class='inputCheck'/>%"
						+ " <span class='set sale_set'>"
						+ " <div class='popup_set'>" + " <ul>"
						+ "  <li class='cur insertnum' type='0'>应用到全部SKU</li>"
						+ "  <li class='insertnum' type='1'>应用到未填写SKU</li>"
						+ "</ul>" + "</div></span></td>"
						+ " <td class='bd_r'><img src='resources/img/pv.png' width='25' style='vertical-align:middle;'/>企业扣除积分金额</td>"
						+ " <td><img src='resources/img/pv.png' width='25' style='vertical-align:middle;'/>持卡人可得积分</td>");
				itemColumHead.appendTo(trHead);
				var tbody = $("<tbody></tbody>");
				tbody.appendTo(table);

				// //生成组合
				var zuheDate = step.doExchange(arrayInfor);
				if (zuheDate.length > 0) {
					
					
					
					// 创建行
					$.each(zuheDate,function(index, item) {
						
						var inputVal0 = '' ,inputVal1 = '',inputVal2 = '',inputVal3 = '',inputVal4 = '',skutr = '';
						if (inputData[0] && inputData[0][0]){
							
							for(var i = 0 ;i<inputData.length;i++){
							if(item == inputData[i][0].split("#")[1]){
								inputVal0 = inputData[i][0].split("#")[0];
								inputVal1 = inputData[i][1].split("#")[0];
								inputVal2 = inputData[i][2].split("#")[0];
								inputVal3 = inputData[i][3].split("#")[0];
								inputVal4 = inputData[i][4].split("#")[0];
								skutr = inputData[i][0].split("#")[2];
							}
						}
					}
						
						
						var td_array = item.split(",");
						var tr = $("<tr  height='32' bool='"+item+"' skuid='"+skutr+"'></tr>");
						tr.appendTo(tbody);
						$.each(td_array, function(i, values) {
							var td = $("<td items='" + values
									+ "'>"
									+ values.split("&")[1]
									+ "</td>");
							td.appendTo(tr);
						});
						
						
						
						var td1 = $("<td ><input type=\"text\" maxlength='12' style=\"width:70px\" value='"+ inputVal0 +"' class='price1 inputCheck'></td>");
						td1.appendTo(tr);
						var td2 = $("<td ><input type=\"text\" maxlength='12' style=\"width:60px\" value='"+inputVal1 +"' class='jifen1 inputCheck'></td>");
						td2.appendTo(tr);
						var td3 = $("<td ><input type=\"text\" maxlength='12' style=\"width:35px\" value='"+ inputVal2 +"' class='jifen2 inputCheck'>%</td>");
						td3.appendTo(tr);
						var td4 = $("<td ><input type=\"text\" value='"+ inputVal3 +"' disabled=\"disabled\" style=\"border:0px;width:62px\"></td>");
						td4.appendTo(tr);
						var td5 = $("<td ><input type=\"text\" value='"+ inputVal4 +"' disabled=\"disabled\" style=\"border:0px;width:62px\"></td>");
						td5.appendTo(tr);
					});
				}
				// 结束创建Table表
				arrayColumn.pop();// 删除数组中最后一项
				// 合并单元格
				$(table).mergeCell({
					// 目前只有cols这么一个配置项, 用数组表示列的索引,从0开始
					cols : arrayColumn
				});
				inputData.length = 0;
			} else {
				// 未全选中,清除表格
				$('#createTable').html("");
			}
		},// 合并行
		hebingFunction : function() {
			$.fn.mergeCell = function(options) {
				return this.each(function() {
					var cols = options.cols;
					for ( var i = cols.length - 1; cols[i] != undefined; i--) {
						// fixbug console调试
						// console.debug(cols[i]);
						mergeCell($(this), cols[i]);
					}
					dispose($(this));
				});
			};
			function mergeCell($table, colIndex) {
				$table.data('col-content', ''); // 存放单元格内容
				$table.data('col-rowspan', 1); // 存放计算的rowspan值 默认为1
				$table.data('col-td', $()); // 存放发现的第一个与前一行比较结果不同td(jQuery封装过的),
											// 默认一个"空"的jquery对象
				$table.data('trNum', $('tbody tr', $table).length); // 要处理表格的总行数,
																	// 用于最后一行做特殊处理时进行判断之用
				// 进行"扫面"处理 关键是定位col-td, 和其对应的rowspan
				$('tbody tr', $table).each(function(index) {
									// td:eq中的colIndex即列索引
									var $td = $('td:eq(' + colIndex + ')', this);
									// 取出单元格的当前内容
									var currentContent = $td.html();
									// 第一次时走此分支
									if ($table.data('col-content') == '') {
										$table.data('col-content',currentContent);
										$table.data('col-td', $td);
									} else {
										// 上一行与当前行内容相同
										if ($table.data('col-content') == currentContent) {
											// 上一行与当前行内容相同则col-rowspan累加, 保存新值
											var rowspan = $table.data('col-rowspan') + 1;
											$table.data('col-rowspan', rowspan);
											// 值得注意的是
											// 如果用了$td.remove()就会对其他列的处理造成影响
											$td.hide();
											// 最后一行的情况比较特殊一点
											// 比如最后2行 td中的内容是一样的,
											// 那么到最后一行就应该把此时的col-td里保存的td设置rowspan
											if (++index == $table.data('trNum'))
												$table.data('col-td').attr('rowspan',$table.data('col-rowspan'));
										} else { // 上一行与当前行内容不同
											// col-rowspan默认为1,
											// 如果统计出的col-rowspan没有变化, 不处理
											if ($table.data('col-rowspan') != 1) {
												$table.data('col-td').attr('rowspan',$table.data('col-rowspan'));
											}
											// 保存第一次出现不同内容的td, 和其内容,
											// 重置col-rowspan
											$table.data('col-td', $td);
											$table.data('col-content', $td.html());
											$table.data('col-rowspan', 1);
										}
									}
								});
			}
			// 同样是个private函数 清理内存之用
			function dispose($table) {
				$table.removeData();
			}
		},
		// 组合数组
		doExchange : function(doubleArrays) {
			var len = doubleArrays.length;
			if (len >= 2) {
				var arr1 = doubleArrays[0];
				var arr2 = doubleArrays[1];
				var len1 = doubleArrays[0].length;
				var len2 = doubleArrays[1].length;
				var newlen = len1 * len2;
				var temp = new Array(newlen);
				var index = 0;
				for ( var i = 0; i < len1; i++) {
					for ( var j = 0; j < len2; j++) {
						temp[index] = arr1[i] + "," + arr2[j];
						index++;
					}
				}
				var newArray = new Array(len - 1);
				newArray[0] = temp;
				if (len > 2) {
					var _count = 1;
					for ( var i = 2; i < len; i++) {
						newArray[_count] = doubleArrays[i];
						_count++;
					}
				}
				// console.log(newArray);
				return step.doExchange(newArray);
			} else {
				return doubleArrays[0];
			}
		},
		skuImg : function(){
		    return itemskuimg;
		},
		skuImgdelete : function(){
			return imgDeletearray;
		},
		bindTableClick : function(){
			//删除图片
			$(".deleteImgsku").unbind().on('click',function(){
				var objdelete = $(this);
				 $( "#dlg1" ).dialog({
				      modal: true,
					  close: function() { 
					        $(this).dialog('destroy');
					  },
				      buttons: {
				        确定: function() {
				        	//删除
				        	var param = {};
							var imgid = $(objdelete).attr("imgid");
							if(imgid != null){
								param["imgName"] = imgid;
								param["colorId"] = $(objdelete).parent().parent().parent().attr("colorid");
								imgDeletearray.push(param);
							}else{
								var deleteId = $(objdelete).attr("imgIndex");
								for(var i = 0; i<itemskuimg.length;i++){
									
									if(itemskuimg[i].index == deleteId){
										itemskuimg.remove(i);
									}
								}	
							}
							$(objdelete).parent().remove();
				            $( this ).dialog( "destroy" );
				        }
				      }
				  });
			});
		},
		bindSKUTool : function(){
			$("#skuMain").on("blur",".skuNameNew",function(){
				var skuValue = $.trim($(this).val());
				var skuId = $(this).prev().find(".sku").val().split("&")[0];
				if(skuId != null && skuId != ""){
					$(this).prev().find(".sku").val(skuId+"&"+skuValue);
					step.Creat_Table();
					$.each($("#imgLoad li"),function(k,v){
						if($(v).attr("colorid") == skuId){
							$(v).find("div").find("span").html(skuValue);
							return false;
						} 
					})
				}else{
					$(this).parent().remove();
					step.Creat_Table();
				}
			})
			$("#skuMain").on("click",".delSkuNode",function(){
				var img = $(this).parents("dl").find("dt").find("img");
				if($(img).is(":hidden")){
					$(img).show();
				}
					var sku = $(this).parents("span").eq(0).find(".sku");
				if($(sku).attr("iscolor") == 1){
					var skuId =	$(sku).val().split("&")[0];
					$.each($("#imgLoad").find("li"),function(k,v){
						if($(v).attr("colorid") == skuId){
							$(v).remove();
							return false;
						}
					})
				}
				$(this).parent().remove();
				step.Creat_Table();
			})
			
			$("#skuMain").on("click",".addSkuNode",function(){
				var thisCount = $(this).attr("maxCount");
				var thisDlLength = $(this).parents("dl").find("dd").find("span").length;
				if(thisCount > thisDlLength){
					var thisColor = $(this).parent().attr("iscolor");
					var systemTime = showLeftTime();
					var skuNode = "<span style='line-height: 20px'><a href='javascript:;' style='margin: 0;display: inline;' class='skuName'><input type='checkbox' class='sku' newSku='1' value='"+systemTime+"&amp;' iscolor='"+thisColor+"'>" +
							"</a><input type='text' class='inpt skuNameNew' maxlength='12' style='width:60px'/><img src='resources/img/shan.png' class='delSkuNode' style='cursor: pointer;vertical-align:middle;'></span>";
					var thisDl = $(this).parents("dl");
					$(thisDl).find("dd").append(skuNode);
					if(thisCount <= (thisDlLength +1)){
						$(this).hide();
					}
				}else{
					var thisColor = $(this).parent().attr("iscolor");
					var systemTime = showLeftTime();
					var skuNode = "<span style='line-height: 20px'><a href='javascript:;' style='margin: 0;display: inline;' class='skuName'><input type='checkbox' class='sku' newSku='1' value='"+systemTime+"&amp;' iscolor='"+thisColor+"'>" +
							"</a><input type='text' class='inpt skuNameNew' maxlength='12' style='width:60px'/><img src='resources/img/shan.png' class='delSkuNode' style='cursor: pointer;vertical-align:middle;'></span>";
					var thisDl = $(this).parents("dl");
					$(thisDl).find("dd").append(skuNode);
					$(this).hide();
				}
			})
			
			function showLeftTime()
			{
			var now=new Date();
			var year=now.getFullYear();
			var month=now.getMonth()+1;
			var day=now.getDate();
			var hours=now.getHours();
			var minutes=now.getMinutes();
			var seconds=now.getSeconds();
			var milliseconds = now.getMilliseconds(); 
			var xitongtime ="-"+year+""+month+""+day+""+hours+""+minutes+""+seconds+""+milliseconds;
			//一秒刷新一次显示时间
			return xitongtime;
			}
		}
	}
	return step;
});