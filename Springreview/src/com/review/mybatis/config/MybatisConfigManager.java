package com.review.mybatis.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MybatisConfigManager {
	@Autowired
	private static MybatisConfigManager instance;
	String resource = "com/review/mybatis/config/config.xml";
	InputStream inputStream;
	SqlSessionFactory sqlSessionFactory;
	
	private MybatisConfigManager() {
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static MybatisConfigManager getInstance() {
		if(instance==null) {
			instance=new MybatisConfigManager();
		}
		return instance;
	}
	
	public SqlSession getSession() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		return sqlSession; 
	}
	
	public void close(SqlSession sqlSession) {
		if(sqlSession!=null) {
			sqlSession.close();
		}
	}
}
