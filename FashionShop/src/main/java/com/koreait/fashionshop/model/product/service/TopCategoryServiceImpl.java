package com.koreait.fashionshop.model.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.koreait.fashionshop.model.domain.TopCategory;
import com.koreait.fashionshop.model.product.repository.TopCategoryDAO;

@Service
public class TopCategoryServiceImpl implements TopCategoryService {
	@Autowired
	@Qualifier("mybatisTopCategoryDAO")
	private TopCategoryDAO topCategoryDAO;

	@Override
	public List selectAll() {
		return topCategoryDAO.selectAll();
	}

	@Override
	public TopCategory select(int topcategory_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(TopCategory topCategory) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(TopCategory topCategory) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int topcategory_id) {
		// TODO Auto-generated method stub

	}

}
