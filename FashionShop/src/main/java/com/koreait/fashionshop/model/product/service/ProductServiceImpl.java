package com.koreait.fashionshop.model.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.fashionshop.model.domain.Product;
import com.koreait.fashionshop.model.product.repository.ProductDAO;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDAO productDAO;
	
	@Override
	public List selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List selectById(int subcategory_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product select(int product_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void regist(Product product) {
		//서비스의 존재가 필요한 이유가 나옴 
		//파일을 업로드를 하고 디비에 저장을 하는 과정 중 업로드 과정을 누가 처리를 해야될까? 
		//DAO? - X : 영역침범, 재사용성떨어짐
		productDAO.insert(product);
		
		//파일 업로드!!! (서비스의 존재 이유 중 하나!)
	}

	@Override
	public void update(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int product_id) {
		// TODO Auto-generated method stub
		
	}
	
}
