define(['text!eshopInfoTpl/gzsc/gzsc.html'], function(tpl){
	return {
		showPage : function(){
			var self = this;
			
			//分类数据，从后台获取
			var response = {
				totalSum: 500,
				data : {
					"2" : {
						name: "女装",
						value: "2",
						sum: 15,
						subdata: [
							{name:"连衣裙", value:"2-1"},
							{name:"外套", value:"2-2"},
							{name:"牛仔裤", value:"2-3"},
							{name:"毛衣", value:"2-4"}
						]
					},
					"3" : {
						name: "珠宝",
						value: "3",
						sum: 3,
						subdata: [
							{name:"翡翠", value:"3-1"},
							{name:"和田玉", value:"3-2"}
						]
					},
					"4" : {
						name: "配饰",
						value: "4",
						sum: 25,
						subdata: [
							{name:"配饰1", value:"4-1"},
							{name:"配饰2", value:"4-2"}
						]
					},
					"5" : {
						name: "手机",
						value: "5",
						sum: 15,
						subdata: [
							{name:"手机1", value:"5-1"},
							{name:"手机2", value:"5-2"}
						]
					},
					"6" : {
						name: "其它",
						value: "6",
						sum: 20,
						subdata: [
							{name:"其它1", value:"6-1"},
							{name:"其它2", value:"6-2"}
						]
					}
				}
			};
			
			$('#busibox').html(_.template(tpl, response));
			
			var searchTable = $.fn.bsgrid.init('gzsc_table', {
				//url: '',
				localData: [{spzp:'resources/img/product01.jpg',spmc:'2014冬季韩版新女显瘦学院风复古',spys:'蓝色',spcm:'XXL 170/175',fl:'连衣裙',scrs:20,zjscrq:'2015-01-15'},{spzp:'resources/img/product02.jpg',spmc:'2014冬季韩版新女显瘦学院风复古',spys:'粉色',spcm:'XXL 170/175',fl:'连衣裙',scrs:30,zjscrq:'2015-01-14'},{spzp:'resources/img/product03.jpg',spmc:'2014冬季韩版新女显瘦学院风复古',spys:'白色',spcm:'XXL 170/175',fl:'连衣裙',scrs:15,zjscrq:'2015-01-13'},{spzp:'resources/img/product01.jpg',spmc:'2014冬季韩版新女显瘦学院风复古',spys:'蓝色',spcm:'XXL 170/175',fl:'连衣裙',scrs:25,zjscrq:'2015-01-12'}],
				otherParames: {
					gzsc_zt: ''
				},
				//不显示空白行
				displayBlankRows: false,
				//不显示无页可翻的提示
				pageIncorrectTurnAlert: false,
				//隔行变色
				stripeRows: true,
				//不显示选中行背景色
				rowSelectedColor: false,
				pageSize: 10,
				operate: {
					add: function(record, rowIndex, colIndex, options){
						var sTpl = '<dl class="imgWord"><dt><img src="<%=obj.zp%>" width="71" height="71"></dt><dd><%=obj.mc%></dd><dd><span class="tit">颜色：</span><%=obj.ys%></dd><dd><span class="tit">尺码：</span><%=obj.cm%></dd></dl>';
						return _.template(sTpl, {
							zp: searchTable.getRecordIndexValue(record, 'spzp'),
							mc: searchTable.getRecordIndexValue(record, 'spmc'),
							ys: searchTable.getRecordIndexValue(record, 'spys'),
							cm: searchTable.getRecordIndexValue(record, 'spcm')
						});
					}
				}
			});
			
			$('#classify').css('cursor','text').find('.classtext').text('分类').next().hide();
			
			//状态页签切换
			$('#gzsc_zt_tab > li').click(function(){
				var o = $(this),
				sVal = o.data('value');
				o.addClass('cur').siblings().removeClass('cur');
				
				if (sVal != ""){
					var sTpl = '<ul><li data-value="<%=obj.value%>">全部</li><%$.each(obj.subdata, function(k, v){%><li data-value="<%=v.value%>"><%=v.name%></li><%})%></ul>';
					var data = response.data[sVal];
					$('#droplist').toggleClass('h180', data.subdata.length > 9).html(_.template(sTpl, data));
					$('#classify').css('cursor','pointer').find('.classtext').text('全部').next().show();
					//绑定事件
					$("#classify").click(function () {
						$("#droplist").show();
					}).parent().hover(function () {
						$("#droplist").show();
					}, function () {
						$("#droplist").hide();
					});
					$('#droplist li').click(function () {
						var o = $(this);
						$('#classify .classtext').text(o.text())
						$("#droplist").hide();
						searchTable.search({'gzsc_zt': o.data('value')});
					});
				} else {
					$('#droplist').removeClass('h180').html('');
					$('#classify').css('cursor','text').find('.classtext').text('分类').next().hide();
					$("#classify").unbind().parent().unbind();
				}
				
				searchTable.search({'gzsc_zt': sVal});
			});
		}
	};
});