package com.koreait.fashionshop.model.product.service;

import java.util.List;

import com.koreait.fashionshop.model.domain.TopCategory;

public interface TopCategoryService {
	//CRUD
	public List selectAll();
	public TopCategory select(int topcategory_id);
	public void insert(TopCategory topCategory);
	public void update(TopCategory topCategory);
	public void delete(int topcategory_id);
}
