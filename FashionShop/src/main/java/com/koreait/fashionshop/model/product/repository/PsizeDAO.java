package com.koreait.fashionshop.model.product.repository;

import java.util.List;

import com.koreait.fashionshop.model.domain.Psize;

public interface PsizeDAO {
	public List selectAll();
	public List selectById(int product_id);
	public Psize select(int psize_id);
	public void insert(Psize psize);
	public void update(Psize psize);
	public void delete(Psize psize);
}
