<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(function(){
		$('#checkName').click(function(){
			var userName = $('#userName').val();
			var $obj = $(this);
			$.ajax({
				url:"checkUserName",
				data:{
					name:userName
				},
				success:function(data){
					if(data == 'ok'){
						$obj.next().html('可以使用').css("color",'green');
					}else {
						$obj.next().html('重复').css("color",'red');
					}
				}
			});
		});
		
		$('#showNews').click(function(){
			$.ajax({
				url:"selectAllNews2",
				success:function(newsList){
					var $tb = $('table>tbody');
					$tb.html('');
					$.each(newsList,function(index,news){
						var $tr=$("<tr><td>nid</td><td>ntitle</td></tr>");
						$.each(news,function(key,val){
							$tr.find('td:contains('+key+')').html(val);
						});
						$tb.append($tr);
					});
				}
			});
		});
	});
</script>
</head>
<body>
	<form action="uploadFile" method="post" enctype="multipart/form-data">
		<p>
		<label>用户名：</label>
		<input id="userName" type="text" name="userName">
		<input id="checkName" type="button" value="检查">
		<span></span>
		</p>
		<p>
			<label>文件：</label>
			<input type="file" name="myFile">
			<span id="fileMessage"></span>
		</p>
		<p>
			<input type="submit" value="保存">
		</p>
	</form>
	<p>
		<a href="static/upload/a341e079-b152-438e-b8ac-9125abecc415.mp4">美丽的岳阳.mp4</a>
	</p>
	<input id="showNews" type="button" value="显示新闻">
	<table border="1" style="width: 80%">
		<thead>
			<tr>
				<th>序号</th>
				<th>标题</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<c:if test="${message != null}">
		<script type="text/javascript">
			$("#fileMessage").html('${message}');
		</script>
	</c:if>
</body>
</html>