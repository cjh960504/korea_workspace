<%@page import="com.model2.domain.Board"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%
	request.setCharacterEncoding("utf-8");
	int result = (Integer)request.getAttribute("result"); //수정결과값
	Board board = (Board)request.getAttribute("board");
	
	StringBuilder sb = new StringBuilder();
	
	sb.append("<script>");	
	if(result==0){
		sb.append("alert('수정실패');");
		sb.append("history.back();");
	}else{
		sb.append("alert('수정성공');");
		sb.append("location.href='/board/detail?board_id="+board.getBoard_id()+"';");
	}
	sb.append("</script>");
	
	out.print(sb.toString());
%>