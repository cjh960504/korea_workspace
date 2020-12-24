//이 클래스의 목적
//쿼리문 수행에 필요한 SqlSession을 보다 쉽게 얻어갈 수 있도록 재사용성을 고려하여
//정의한 객체, 특히 이 객체의 인스턴스는 어플리케이션내에서 1개만 둬야하므로 SingleTon
//패턴으로 정의한다!!
package com.model2.mybatis.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisConfigManager {
	//2)성성자를 묶었으므로, 현재 클래스에서 인스턴스를 제공하지 않으면 사용할 방법이 없다.
	//따라서 현재 클래스에서 getter를 제공해주자!
	private static MybatisConfigManager instance;
	InputStream inputStream;
	SqlSessionFactory sqlSessionFactory;
	//1)생성자를 묶어서, 아무나 new 못하게 만들자!!
	private MybatisConfigManager() {
		String resource = "com/model2/mybatis/config/config.xml";
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(inputStream!=null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//3) getter는 제공했으나, 역시 인스턴스 메서드이므로 new한 다음에나 호출할 수 있기
	//때문에 그 어떤 객체로 이 메서드를 호출할 수 없다...(new를 막아놓았으니..)
	//해결책? new하지 않고도 아래의 메서드를 호출할 수 있도록 static메서드로 정의하자!
	public static MybatisConfigManager getInstance() {
		//4) 이 메서드 호출 시 instance변수가 null이라면 여기서 인스턴스를 생성해서
		//가져가도록 처리
		if(instance==null) {
			instance = new MybatisConfigManager();
		}
		return instance;
	}
	
	//SqlSession 얻기 및 닫기
	public SqlSession getSqlSession() {
		SqlSession sqlSession = null;
		sqlSession = sqlSessionFactory.openSession();
		return sqlSession;
	}
	
	public void close(SqlSession sqlSession) {
		if(sqlSession!=null) {
			sqlSession.close();
		}
	}
	
	
}
