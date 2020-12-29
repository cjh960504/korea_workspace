package com.study.review.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.review.model.dao.DeptDAO;
import com.study.review.model.dao.EmpDAO;
import com.study.review.model.dao.MybaitsEmpDAO;
import com.study.review.model.domain.Emp;
import com.study.review.model.pool.PoolManager;
import com.study.review.mybatis.config.MybatisConfigManager;

@Service
public class EmpService {
	@Autowired
	private PoolManager manager;
	@Autowired
	private EmpDAO empDAO;
	@Autowired 
	private DeptDAO deptDAO;
	@Autowired
	private MybatisConfigManager configManager;
	@Autowired
	private MybaitsEmpDAO myEmpDAO;
	
	public List selectAll() {
		List list = null;
//		Connection con = manager.getConnection();
//		empDAO.setCon(con);
//		list = empDAO.selectAll();
//		manager.freeConnection(con);
		myEmpDAO.setSqlSession(configManager.getSession());
		list = myEmpDAO.selectAll();
		return list;
	}
	
	public int regist(Emp emp) {
		int result=0;
		Connection con = manager.getConnection();

		try {
			con.setAutoCommit(false);
			empDAO.setCon(con);
			deptDAO.setCon(con);
			
			empDAO.insert(emp);
			deptDAO.insert(emp.getDept());
			result = 1;
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				con.setAutoCommit(true);
				manager.freeConnection(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
	
}
