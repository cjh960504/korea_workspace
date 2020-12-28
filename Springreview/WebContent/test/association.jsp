<%@page import="com.review.domain.Emp"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.review.mybatis.config.MybatisConfigManager"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
	MybatisConfigManager manager = MybatisConfigManager.getInstance();
%>
<%
	SqlSession sqlSession = manager.getSession();
 	List<Emp> list= sqlSession.selectList("Emp.selectAll");
 	out.print(list.size());
%>