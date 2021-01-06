package com.koreait.fashionshop.model.product.service;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.koreait.fashionshop.common.FileManager;
import com.koreait.fashionshop.exception.ProductRegistException;
import com.koreait.fashionshop.model.domain.Color;
import com.koreait.fashionshop.model.domain.Image;
import com.koreait.fashionshop.model.domain.Product;
import com.koreait.fashionshop.model.domain.Psize;
import com.koreait.fashionshop.model.product.repository.ColorDAO;
import com.koreait.fashionshop.model.product.repository.ImageDAO;
import com.koreait.fashionshop.model.product.repository.ProductDAO;
import com.koreait.fashionshop.model.product.repository.PsizeDAO;

@Service
public class ProductServiceImpl implements ProductService{
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private PsizeDAO psizeDAO;
	@Autowired
	private ColorDAO colorDAO;
	
	@Override
	public List selectAll() {
		return productDAO.selectAll();
	}

	@Override
	public List selectById(int subcategory_id) {
		return productDAO.selectById(subcategory_id);
	}

	@Override
	public Product select(int product_id) {
		return productDAO.select(product_id);
	}

	@Override
	public void regist(FileManager fileManager, Product product) throws ProductRegistException{
		//서비스의 존재가 필요한 이유가 나옴 
		//파일을 업로드를 하고 디비에 저장을 하는 과정 중 업로드 과정을 누가 처리를 해야될까? 
		//DAO? - X : 영역침범, 재사용성떨어짐
		
		String ext = fileManager.getExtend(product.getRepImg().getOriginalFilename());
		product.setFilename(ext);
		//db에 넣는 일은 DAO에게 시키고
		productDAO.insert(product);
		
		//파일 업로드는 FileManager!!! (서비스의 존재 이유 중 하나!)
		//대표 이미지 업로드
		//상품의 product_id를 이용하여, 대표이미지명을 결정짓자!!
		String basicImg = product.getProduct_id()+ "."+fileManager.getExtend(product.getRepImg().getOriginalFilename());
		fileManager.saveFile(fileManager.getSaveBasicDir()+File.separator+basicImg, product.getRepImg());
		
		//추가이미지 업로드 (FileManager에게 추가이미지 갯수만큼 업로드 업무를 시키면 된다!!)
		for(int i=0;i<product.getAddImg().length;i++) {
			MultipartFile file = product.getAddImg()[i];
			ext = fileManager.getExtend(file.getOriginalFilename());
			
			//image table에 넣기!!
			Image image = new Image();
			image.setProduct_id(product.getProduct_id());
			image.setFilename(ext);
			imageDAO.insert(image);
			
			String addImage = image.getImage_id()+"."+ext;
			fileManager.saveFile(fileManager.getSaveAddOnDir()+File.separator+addImage, file);
		}
		
		//기타 옵션 중, 색상 사이즈 넣기(반복문으로..)
		for(Psize psize: product.getPsize()) {
			logger.debug("넘겨받은 psize"+psize.getFit());
			psize.setProduct_id(product.getProduct_id());
			psizeDAO.insert(psize);
		}
		
		for(Color color:product.getColor()) {
			logger.debug("넘겨받은 color"+color.getPicker());
			color.setProduct_id(product.getProduct_id());
			colorDAO.insert(color);
		}
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
