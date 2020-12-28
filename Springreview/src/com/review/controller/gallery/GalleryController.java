package com.review.controller.gallery;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.review.common.FileManager;
import com.review.domain.Gallery;
import com.review.model.dao.GalleryDAO;

@Controller
@RequestMapping(value="/gallery")
public class GalleryController{
	@Autowired
	private GalleryDAO dao;
	public void setDao(GalleryDAO dao) {
		this.dao = dao;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		List list = dao.selectAll();
		mav.addObject("galleryList", list);
		mav.setViewName("gallery/list");
		return mav;
	}
	
	//로드 존슨아저씨가 매개변수로 파라미터를 받을 수 있게 맏늘어줌
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	public ModelAndView detail(int gallery_id) {
		Gallery gallery = dao.select(gallery_id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("gallery", gallery);
		mav.setViewName("gallery/detail");
		return mav;
	}
	
	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public String regist(Gallery gallery, HttpServletRequest req) {
		//MultiPartFile형식으로 받은 파일을 물리적으로 저장해야함
		//MultiPartFile으로 받을 수 있게 Gallery의 변수로 저장
		MultipartFile photo = gallery.getPhoto();
		String time = Long.toString(System.currentTimeMillis());
		String ext = FileManager.getExtend(photo.getOriginalFilename());
		String newName = time+"."+ext;
		ServletContext context = req.getServletContext();
		String realPath=context.getRealPath("/data");
		try {
			photo.transferTo(new File(realPath+"/"+newName));
			gallery.setFilename(newName);
			int result = dao.insert(gallery);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/gallery/list";
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public ModelAndView edit(Gallery gallery) {
		ModelAndView mav = new ModelAndView();
		int result = dao.update(gallery);
		if(result==0) {
			mav.addObject("msg", "수정실패!");
			mav.setViewName("error/result");
		}else {
			mav.setViewName("redirect:/gallery/detail?gallery_id="+gallery.getGallery_id());
		}
		return mav;
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public ModelAndView delete(int gallery_id) {
		ModelAndView mav = new ModelAndView();
		int result = dao.delete(gallery_id);
		if(result==0) {
			mav.addObject("msg", "삭제실패!");
			mav.setViewName("error/result");
		}else {
			mav.setViewName("redirect:/gallery/list");
		}
		return mav;
	}
	
	
}