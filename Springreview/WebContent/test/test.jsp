<%@page import="com.review.domain.Dept"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="org.apache.catalina.Manager"%>
<%@page import="com.review.mybatis.config.MybatisConfigManager"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
	MybatisConfigManager manager = MybatisConfigManager.getInstance();
%>
<%
	SqlSession sqlSeesion = manager.getSession();
	List<Dept> list =  sqlSeesion.selectList("Dept.selectJoin");
	out.print("쿼리의 결과의 길이는 " + list.size());
	
%>