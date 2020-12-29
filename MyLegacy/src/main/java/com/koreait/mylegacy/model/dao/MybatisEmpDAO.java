package com.koreait.mylegacy.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.koreait.mylegacy.exception.RegistException;
import com.koreait.mylegacy.model.domain.Emp;

@Repository
public class MybatisEmpDAO {
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	//목록가져오기
	public List selectAll() {
		List list = null;
		list = sqlSession.selectList("Emp.selectAll");
		return list;
	}
	
	//1건등록 
	//예외를 반환하는 메서드로 정의해보자!
	//어떤 예외를 만들어야될까..?
	public int insert(Emp emp) throws RegistException{
		int result=0;
		result = sqlSession.insert("Emp.insert",emp);//emp안에 dept가 포함!!
		result=0;
		if(result==0) {
			throw new RegistException("사원등록에 실패하였습니다. ");
		}
		return result;
	}
}
