<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="../inc/header.jsp" %>
</head>
<body>
<%@ include file="../inc/main_navi.jsp" %>
<script>
$(function() {
	$("button").click(function() {
		location.href="/admin/product/registform";//글쓰기 폼 요청
	})
});
</script>
<h3>상품목록</h3>
<p>
	<table>
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Points</th>
		</tr>
		<tr>
			<td>Jill</td>
			<td>Smith</td>
			<td>50</td>
		</tr>
		<tr>
			<td>Eve</td>
			<td>Jackson</td>
			<td>94</td>
		</tr>
		<tr>
			<td>Adam</td>
			<td>Johnson</td>
			<td>67</td>
		</tr>
		<tr>
			<td colspan="5">
				<button type="button">상품등록</button>
			</td>
		</tr>
	</table>
</p>
</body>
</html>