<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="../js/laypage/laypage.js" type="text/javascript"></script>
<link rel="stylesheet" href="../js/laypage/skin/laypage.css">
<tr style="background-color: #fff">
	<td colspan="10" style="text-align: left;">
		<div class="pagination" style="border: 0px;float: left; line-height:27px; font-size: 13px; width:130px">
			总记录数：<span id="counts"></span>
		</div>
		<div class="pagination" style="border: 0px;float: left; font-size: 13px; width:130px">
			每页显示：<select style="width:52px;" class="next" id="pagesizeID">
				<option value="5">5</option>
				<option selected="selected" value="10">10</option>
				<option value="15">15</option>
				<option value="20">20</option>
			</select>条
		</div>
		<div id="pagination" class="pagination" style="float: right;">
			<a style="height: 25px; font-size: 21px; width: 30px;" href="#"
				class="first" data-action="first">&laquo;</a> <a
				style="height: 25px; width: 30px; font-size: 21px;" href="#"
				class="previous" data-action="previous">&lsaquo;</a> <input
				style="height: 25px;line-height: 25px;" type="text" id="data-max-page"
				data-max-page="0" readonly="readonly" /> <a
				style="height: 25px; width: 30px; font-size: 21px;" href="#"
				class="next" data-action="next">&rsaquo;</a> <a href="#"
				style="height: 25px; width: 30px; font-size: 21px;" class="last"
				data-action="last">&raquo;</a>
		</div>
	</td>
</tr>
<script type="text/javascript">
	var page = {};
	page.GetPage = function(pageindex) {
		//分页
		$('#pagination').jqPagination({
			link_string : '/?page={page_number}',
			max_page : pagecount,
			paged : function(page) {
				pageNo = page;
				obj.load(function() {
				});
			}
		});
	}
	//每页显示多少条切换
	$('#pagesizeID').on("change", function() {
		pagesize = $('#pagesizeID').val();
		pageNo = 1;
		obj.load(function() {
			page.GetPage();
		});
		//window.location = '?pagesizeID=' + $('#pagesizeID').val();
	});
</script>