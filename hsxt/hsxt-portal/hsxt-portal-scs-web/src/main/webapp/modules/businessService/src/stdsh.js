define([
"text!jingyingTpl/stdsh/sh.html",
"text!jingyingTpl/stdsh/shbtg.html",
"text!jingyingTpl/stdsh/shqb.html",
"text!jingyingTpl/stdsh/shtg.html" 
],function(shTpl,shbtgTpl,shqbTpl,shtgTpl){
 
	var stdsh = {
		show:function(){
			//加载中间内容 
			$(".operationsArea").html(shTpl).append(shtgTpl).append(shbtgTpl).append(shqbTpl);
			
			$('#sel_sf').selectList({width:80,optionWidth:80,options:[{name:'广东省',value:'1'},{name:'江西省',value:'2'},{name:'湖南省',value:'3'}] });
			$('#sel_cs').selectList({width:80,optionWidth:80});
			$('#sel_qy').selectList({width:80,optionWidth:80});
			
			var cityObj ={
				1:[{name:'深圳市',value:1},{name:'广州市',value:2},{name:'东莞市',value:3},{name:'惠州市',value:4}],
				2:[{name:'南昌市',value:5},{name:'九江市',value:6},{name:'上饶市',value:7},{name:'赣州市',value:8}],
				3:[{name:'长沙市',value:9},{name:'株洲市',value:10},{name:'岳阳市',value:11},{name:'怀化市',value:12}]
			};
			
			var townObj= {
				1:[{name:'福田区',value:1},{name:'龙华区',value:2},{name:'罗湖区',value:3},{name:'南山区',value:4}],
				5:[{name:'东湖区',value:1},{name:'青云谱',value:1},{name:'洪城区',value:1},{name:'湾里区',value:1}],
				9:[{name:'长沙市1',value:1},{name:'株洲市2',value:1},{name:'岳阳市3',value:1},{name:'怀化市4',value:1}] 
			}
			
			
			$('#sel_sf').change(function(){
				var optionValue = $(this).attr('optionValue') ;
				$('#sel_cs').val('');
				$('#sel_qy').val('').selectList({options:[]});
				$('#sel_cs').selectList({options:cityObj[optionValue]});
				
			});			
			
			$('#sel_cs').change(function(){
				var optionValue = $(this).attr('optionValue') ;
				$('#sel_qy').val('');
				$('#sel_qy').selectList({options:townObj[optionValue]}) ;   
			});			 
			 
			$('#sel_sf2').selectList({width:80,optionWidth:80});
			$('#sel_cs2').selectList({width:80,optionWidth:80});
			$('#sel_qy2').selectList({width:80,optionWidth:80});
			
			$('#sel_sf3').selectList({width:80,optionWidth:80});
			$('#sel_cs3').selectList({width:80,optionWidth:80});
			$('#sel_qy3').selectList({width:80,optionWidth:80});
			
			$('#sel_sf4').selectList({width:80,optionWidth:80});
			$('#sel_cs4').selectList({width:80,optionWidth:80});
			$('#sel_qy4').selectList({width:80,optionWidth:80});
			
			 
			// 页面切换
			$('.tab01,.tab02,.tab03,.tab04').click(function(){
				var tabs=$('#sh,#shtg,#shbtg,#shqb');
				tabs.hide();
				var m=$(this).attr('class');				
				var n=m.charAt(m.length-1);
				tabs.eq(n-1).show();	
				});
			//弹出详情
			$( ".stdsh_qb" ).click(function() {
				$( "#stdsh_qb" ).dialog({
					show: true,
					modal: true,
					title:"实体店审核",
					width:950,
					buttons: {
					  保存: function() {$( this ).dialog( "close" );},
					  取消: function() {$( this ).dialog( "close" );}		
					}
					
					});				
			  });
			$(".wsscsh").click(function(){
				$("#subNav_2_06").trigger("click");	
			})
			$(".spsh").click(function(){
				$("#subNav_2_07").trigger("click");	
			})
			
		},
		
		
		
		hide:function(){
			//清空中间内容） 
			$(".operationsArea").empty();	
		}};
	
		
	return stdsh;
});