package com.study.review.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.study.review.model.domain.Dept;
import com.study.review.model.domain.Emp;

@Repository
public class EmpDAO {
	Logger logger = LoggerFactory.getLogger(EmpDAO.class);
	private Connection con;

	public void setCon(Connection con) {
		this.con = con;
	}

	public List selectAll() {
		List list = new ArrayList<Emp>();
		logger.info(con.toString());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from dept d, emp e where d.deptno=e.deptno";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Dept dept = new Dept();
				Emp emp = new Emp();
				dept.setDeptno(rs.getInt("deptno"));
				dept.setDname(rs.getString("dname"));
				dept.setLoc(rs.getString("loc"));
				emp.setEmpno(rs.getInt("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setSal(rs.getString("sal"));
				emp.setDept(dept);
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public int insert(Emp emp) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "insert into emp(empno, ename, sal, deptno) values(?, ?, ?, ?)";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, emp.getEmpno());
			pstmt.setString(2, emp.getEname());
			pstmt.setString(3, emp.getSal());
			pstmt.setInt(4, emp.getDept().getDeptno());
			result = pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
