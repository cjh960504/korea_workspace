<%@page import="com.koreait.fashionshop.model.domain.Product"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
	List<Product> list = (List<Product>)request.getAttribute("productList");
%>
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
<style>
 img{
 width:100px;
 }
</style>
<h3>상품목록</h3>
<p>
	<table>
		<tr>
			<th>No</th>
			<th>이미지</th>
			<th>상품명</th>
			<th>카테고리명</th>
			<th>브랜드</th>
			<th>가격</th>
		</tr>
		<%for(Product product:list){ %>
			<tr>
				<td>1</td>
				<td><img alt="" src="/resources/data/basic/<%=product.getProduct_id() %>.<%=product.getFilename()%>"></td>
				<td><%=product.getProduct_name() %></td>
				<td><%=product.getSubCategory().getName() %></td>
				<td><%=product.getBrand() %></td>
				<td><%=product.getPrice() %></td>
			</tr>
		<%} %>
		<tr>
			<td colspan="6">
				<button type="button">상품등록</button>
			</td>
		</tr>
	</table>
</p>
</body>
</html>