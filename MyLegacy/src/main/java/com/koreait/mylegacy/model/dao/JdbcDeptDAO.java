package com.koreait.mylegacy.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.koreait.mylegacy.model.domain.Dept;
import com.koreait.mylegacy.model.pool.PoolManager;

//Dept 테이블에 대한 CRUD를 수행하되, jdbc기반으로 코드를 작성
//Repository = DAO를 말하는 것
@Repository
public class JdbcDeptDAO {
	private Connection con;

	public void setCon(Connection con) {
		this.con = con;
	}
	
	public List selectAll() {
		List list = null;
		return list;
	}
	
	public Dept select(int deptno) {
		Dept dept=null;
		return dept;
	}
	
	//전가(throws)가 필요한 이유!!!!!!!!!
	public int regist(Dept dept){
		int result=0;
		PreparedStatement pstmt = null;
//		System.out.println("풀매니저는 "+poolManager);
		String sql = "insert into dept(deptno, dname, loc) values(?, ? ,?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dept.getDeptno());
			pstmt.setString(2, dept.getDname());
			pstmt.setString(3, dept.getLoc());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	public int update(Dept dept) {
		int result=0;
		return result;
	}
	
	public int delete(int deptno) {
		int result=0;
		return result;
	}
}
