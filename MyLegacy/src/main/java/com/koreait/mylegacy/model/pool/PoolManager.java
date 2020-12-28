package com.koreait.mylegacy.model.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

//이 클래스를 통해서,  db작업을 하고 싶은 객체가 Connection객체를 빌려갈 수 있도록 제공하기 위함
@Component
public class PoolManager {
	private static PoolManager instance;
	private InitialContext context;
	private DataSource ds;

	private PoolManager() {
		// JNDI를 이용해서 DataSource가져오기
		try {
			context = new InitialContext();
			ds = (DataSource) context.lookup("java:comp/env/jdbc/myoracle");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static PoolManager getInstance() {
		if (instance == null) {
			instance = new PoolManager();
		}
		return instance;
	}

	//커넥션 얻는 메서드
	public Connection getConnection() {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	// 커넥션 및 리소스 반납 (오버로딩 3가지형으로)
	public void freeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void freeConnection(Connection con, PreparedStatement pstmt) {
		if (con != null) {
			try {
				con.close();
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

	public void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs) {
		if (con != null) {
			try {
				con.close();
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
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
