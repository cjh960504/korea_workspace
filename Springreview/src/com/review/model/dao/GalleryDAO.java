package com.review.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.review.domain.Gallery;
import com.review.mybatis.config.MybatisConfigManager;

@Repository
public class GalleryDAO {
	@Autowired
	private MybatisConfigManager manager;
	
	public void setManager(MybatisConfigManager manager) {
		this.manager = manager;
	}
	
	public Gallery select(int gallery_id) {
		Gallery gallery=null;
		SqlSession sqlSession = manager.getSession();
		gallery = sqlSession.selectOne("Gallery.select", gallery_id);
		manager.close(sqlSession);
		return gallery;
	}
	
	public List selectAll() {
		List list = null;
		SqlSession sqlSession = manager.getSession();
		list = sqlSession.selectList("Gallery.selectAll");
		manager.close(sqlSession);
		return list;
	}
	
	public int insert(Gallery gallery) {
		int result=0;
		SqlSession sqlSession = manager.getSession();
		result = sqlSession.insert("Gallery.insert", gallery);
		sqlSession.commit();
		manager.close(sqlSession);
		return result;
	}
	
	public int update(Gallery gallery) {
		int result=0;
		SqlSession sqlSession = manager.getSession();
		result = sqlSession.update("Gallery.update", gallery);
		sqlSession.commit();
		manager.close(sqlSession);
		return result;
	}
	
	public int delete(int gallery_id) {
		int result=0;
		SqlSession sqlSession = manager.getSession();
		result = sqlSession.delete("Gallery.delete", gallery_id);
		sqlSession.commit();
		manager.close(sqlSession);
		return result;
	}
}
