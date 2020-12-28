package com.koreait.mylegacy.mybatis.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyBatisConfigManager {
	 private static MyBatisConfigManager instance;
	 String resource = "com/koreait/mylegacy/mybatis/config/config.xml";
	 InputStream inputStream;
	 SqlSessionFactory sqlSessionFactory;
	 private MyBatisConfigManager() {
		 try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	 public static MyBatisConfigManager getInstance() {
		 if(instance!=null) {
			 instance = new MyBatisConfigManager();
		 }
		 return instance;
	}
	 
	 public SqlSession getSqlSession() {
		 SqlSession sqlSession = sqlSessionFactory.openSession();
		 return sqlSession;
	 }
	 
	 public void freeSqlSession(SqlSession sqlSession) {
		 if(sqlSession!=null) {
			 sqlSession.close();
		 }
	 }
}
