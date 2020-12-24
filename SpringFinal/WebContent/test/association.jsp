<%@page import="com.study.springfinal.domain.Emp"%>
<%@page import="com.study.springfinal.domain.Dept"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.study.springfinal.mybatis.config.MybatisConfigManager"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
	//멤버변수 영역
	MybatisConfigManager manager = MybatisConfigManager.getInstance();
%>
<%
	//service메서드 영역
	SqlSession sqlSession = manager.getSqlSession();
	List<Emp> list = sqlSession.selectList("Emp.selectAll");
	out.print("검색된 총 사원수는 "+list.size());	
%>
