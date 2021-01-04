package com.koreait.fashionshop.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.koreait.fashionshop.common.FileManager;
import com.koreait.fashionshop.model.domain.Product;
import com.koreait.fashionshop.model.domain.SubCategory;
import com.koreait.fashionshop.model.product.service.ProductService;
import com.koreait.fashionshop.model.product.service.SubCategoryService;
import com.koreait.fashionshop.model.product.service.TopCategoryService;

//관리자 모드에서의 상품에 대한 요청 처리
@Controller
public class ProductController implements ServletContextAware{
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	@Qualifier("topCategoryServiceImpl")
	private TopCategoryService topCategoryService;
	@Autowired
	private SubCategoryService subCategoryService;
	@Autowired
	private ProductService productService;
	@Autowired
	private FileManager fileManager;
	
	//우리가 왜 ServletContext를 써야하는가? getRealPath() 사용하려구~!
	ServletContext servletContext;
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext; 
		//이 타이밍을 놓치지말고, 실제 물리적 경로를 FileManager에 대입하자!
		fileManager.setSaveBasicDir(servletContext.getRealPath(fileManager.getSaveBasicDir()));
		fileManager.setSaveAddOnDir(servletContext.getRealPath(fileManager.getSaveAddOnDir()));
		logger.debug(fileManager.getSaveBasicDir());
	}

	// 상위카테고리 가져오기
	@RequestMapping(value = "/admin/product/registform", method = RequestMethod.GET)
	public ModelAndView getTopList() {
		// 3단계 : 로직 객체에 일 시킨다.
		List topList = topCategoryService.selectAll();

		// 4단계 : 저장
		ModelAndView mav = new ModelAndView();
		mav.addObject("topList", topList);
		mav.setViewName("admin/product/regist_form");
		return mav;
	}

	// 하위카테고리 가져오기
	// application/json으로 넘기면 객체형태로 클라이언트가 받을 수 있따!!! 파싱할 필요도 없어짐
	/*
	 * @RequestMapping(value="/admin/product/sublist", method=RequestMethod.GET,
	 * produces = "application/json;charset=utf8")
	 * 
	 * @ResponseBody //jsp와 같은 뷰페이지가 아닌, 단순 데이터만 전송 시!! 개꿀이다 public String
	 * getSubList(int topcategory_id) { logger.debug("넘어온 topcategory_id : "
	 * +topcategory_id); List<SubCategory> subList =
	 * subCategoryService.selectAllById(topcategory_id);
	 * 
	 * //String으로 전송할 수 밖에 없으므로 리스트를 json으로 변형하여 보내줘야함.. StringBuilder sb = new
	 * StringBuilder(); sb.append("{"); sb.append("\"subList\":[") ; for(int
	 * i=0;i<subList.size();i++) { SubCategory subCategory = subList.get(i);
	 * sb.append("{"); sb.append(
	 * "\"subcategory_id\":"+subCategory.getSubcategory_id()+","); sb.append(
	 * "\"topcategory_id\":"+subCategory.getTopcategory_id()+","); sb.append(
	 * "\"name\": \""+subCategory.getName()+"\""); if(i<subList.size()-1) {
	 * sb.append("},"); }else { sb.append("}"); } } sb.append("]"); sb.append("}");
	 * 
	 * return sb.toString(); }
	 */

	// 하위카테고리 가져오기
	// 스프링에서는 지원하는 java객체와 json간의 변환을 자동으로 처리해주는 라이브러리 이용 
	@RequestMapping(value="/admin/product/sublist", method=RequestMethod.GET)
	@ResponseBody
	public List getSubList(int topcategory_id) {
		List<SubCategory> subList = subCategoryService.selectAllById(topcategory_id);
		return subList;
	}

	// 상품목록
	@RequestMapping(value = "/admin/product/list", method = RequestMethod.GET)
	public ModelAndView getProductList() {
		ModelAndView mav = new ModelAndView("admin/product/product_list");
		return mav;
	}

	// 상품등록 폼
	/*
	 * @RequestMapping(value="/admin/product/registform") public String registForm()
	 * { return "admin/product/regist_form"; }
	 */

	// 상품 상세

	// 상품 등록
	@RequestMapping(value="/admin/product/regist", method=RequestMethod.POST)
	@ResponseBody
	public String registProduct(Product product) {
		logger.debug("하위 카테고리 " + product.getSubcategory_id());
		logger.debug("상품명 " + product.getProduct_name());
		logger.debug("가격 " + product.getPrice());
		logger.debug("브랜드 " + product.getBrand());
		logger.debug("상세내용 " + product.getDetail());
//		logger.debug("업로드 이미지명 " + product.getRepImg().getOriginalFilename());
//		for(MultipartFile file:product.getAddImg()) {
//			logger.debug("추가이미지명 "+ file.getOriginalFilename());
//		}
//		for(int i=0;i<product.getFit().length;i++) {
//			logger.debug("지원 사이즈는 " + product.getFit()[i]);
//		}
		
		//logger.debug("insert하기 전 상품의 product_id "+product.getProduct_id());
		productService.regist(fileManager, product);//상품등록
		//logger.debug("방금 insert된 상품의 product_id "+product.getProduct_id());
		
		
		
		/*
		 * //파일명이 생성되었으면, 이제 저장을 해보자!! //실제 물리적 경로를 얻어오려면,ServletContext가 보유한
		 * getRealPath() 메서드가 필요하다,, try { //File.separator = OS환경에 맞게 알맞은 구분자를 넣어준다!!
		 * product.getRepImg().transferTo(new
		 * File(fileManager.getSaveBasicDir()+File.separator+basicImg));//저장 } catch
		 * (IllegalStateException e) { e.printStackTrace(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
		return "gg";
		/*
		//대표이미지 업로드(파일명 : 현재 날짜로 처리)
		//어떤 파일명으로, 어디에 저장할 지 결정
		long time = System.currentTimeMillis();
		
		//확장자 얻기
		String ext = fileManager.getExtend(product.getRepImg().getOriginalFilename());
		String filename = time+"."+ext;
		try {
			product.getRepImg().transferTo(new File(fileManager.getSaveDir()+"/"+filename));
			logger.debug(filename);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//db에 넣기
		productService.regist(product);*/
		
	}
	// 상품 수정

	// 상품 삭제
	
	//예외처리 : 위의 메서드 중에서 하나라도 예외가 발생하면, 
}
