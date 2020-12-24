<%@page import="com.model2.domain.Board"%>
<%@page import="common.board.Pager"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	//포워딩을 통해 넘겨받은 request 객체에 담겨진 데이터 꺼내기
	List<Board> boardList = (List)request.getAttribute("boardList");
	out.print("게시물 수는 "+boardList.size());
	Pager pager = new Pager();
	pager.init(request, boardList);
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
table {
	border-collapse: collapse;
	border-spacing: 0;
	width: 100%;
	border: 1px solid #ddd;
}
th, td {
	text-align: left;
	padding: 16px;
}
tr:nth-child(even) {
	background-color: #f2f2f2;
}
</style>
</head>
<body>

	<table>
		<tr>
			<th>No</th>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일</th>
			<th>조회수</th>
		</tr>
		<%
			// ++ 혹은 -- 할 대상은 변수로 받아놓고 처리해야 편하다
			int curPos=pager.getCurPos();
			int num = pager.getNum();
		%>
		<%for(int i=1;i<=pager.getPageSize();i++){%>
		<%if(num<1)break; %>
		<%Board board = boardList.get(curPos++);%>
		<tr>
			<td><%=num-- %></td>
			<td><a href="/board/detail?board_id=<%=board.getBoard_id()%>"><%=board.getTitle() %></a>[<%=board.getCnt() %>]</td>
			<td><%=board.getWriter() %></td>
			<td><%=board.getRegdate().substring(0,10) %></td>
			<td><%=board.getHit() %></td>
		</tr>
		<%}%>
		<tr>
			<td colspan="5" style="text-align:center">
				[1][2][3]
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<button onClick="location.href='/board/regist_form.jsp'"> 글등록</button>
			</td>
		</tr>
	</table>

</body>
</html>