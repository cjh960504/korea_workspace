package com.study.review.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.review.model.domain.Dept;
import com.study.review.model.domain.Emp;
import com.study.review.model.pool.PoolManager;

@Repository
public class DeptDAO {
	private Connection con;

	public void setCon(Connection con) {
		this.con = con;
	}

	public int insert(Dept dept) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "insert into dept(deptno, dname, loc) values(?, ?, ?)";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dept.getDeptno());
			pstmt.setString(2, dept.getDname());
			pstmt.setString(3, dept.getLoc());
			result = pstmt.executeUpdate();
		}  finally {
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
