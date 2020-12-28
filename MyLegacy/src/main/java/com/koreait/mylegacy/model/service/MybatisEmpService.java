package com.koreait.mylegacy.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.mylegacy.model.dao.MybatisDeptDAO;
import com.koreait.mylegacy.model.dao.MybatisEmpDAO;
import com.koreait.mylegacy.model.domain.Emp;
import com.koreait.mylegacy.mybatis.config.MyBatisConfigManager;

@Service
public class MybatisEmpService {
	@Autowired
	MyBatisConfigManager manager;
	@Autowired
	MybatisEmpDAO empDAO;
	
	@Autowired
	MybatisDeptDAO deptDAO;
	
	//모든 레코드 가져오기
	public List selectAll() {
		SqlSession sqlSession = manager.getSqlSession();
		empDAO.setSqlSession(sqlSession);
		List list = empDAO.selectAll();
		manager.freeSqlSession(sqlSession);
		return list;
	}
	
	//사원등록(부서등록+사원등록=2개의 업무로 구성된 트랜잭션 상황)
	public int regist(Emp emp) {
		int result = 0;
		SqlSession sqlSession = manager.getSqlSession();//default AutoCommit = false
		deptDAO.setSqlSession(sqlSession);
		empDAO.setSqlSession(sqlSession);
		
		deptDAO.insert(emp.getDept());
		empDAO.insert(emp);
		manager.freeSqlSession(sqlSession);
		return result;
	}
}
