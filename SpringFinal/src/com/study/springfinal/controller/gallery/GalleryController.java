package com.study.springfinal.controller.gallery;

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

import com.study.springfinal.common.FileManager;
import com.study.springfinal.domain.Gallery;
import com.study.springfinal.model.dao.GalleryDAO;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	//ǥ�ø� ���״�, ���⿡ �־��ּ��� �Ф�
	@Autowired
	private GalleryDAO galleryDAO;

	public GalleryDAO getGalleryDAO() {
		return galleryDAO;
	}

	public void setGalleryDAO(GalleryDAO galleryDAO) {
		this.galleryDAO = galleryDAO;
	}

	//������ �����ӿ��� ���ε� ������Ʈ��, apache fileupload�� �����
	//��������̶� ���� �ְ� ���� �Ķ���� �־��!! ���
	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public String regist(Gallery gallery, HttpServletRequest request) {
		//������ ����
		MultipartFile photo = gallery.getPhoto();
		System.out.println("getName is "+photo.getName());
		System.out.println("original is "+photo.getOriginalFilename());
		System.out.println("contentType is "+photo.getContentType());
		System.out.println("size is "+photo.getSize());
		
		//���ϸ� ���� �����, �����ϱ�
		String newName = Long.toString(System.currentTimeMillis());
		String ext = FileManager.getExtend(photo.getOriginalFilename());
		System.out.println(ext);
		String filename = newName+"."+ext;//�������ϸ�
		gallery.setFilename(filename);//�������ϸ� VO�� ����
		ServletContext context = request.getServletContext();
		String realPath = context.getRealPath("/data");
		//��Ŭ���� ���� ��Ĺ�� ���, ���� ��οʹ� �ٸ� ��ο� ����.. 
		System.out.println(realPath);
		try {
			photo.transferTo(new File(realPath+"/"+filename));//����������
			int result = galleryDAO.insert(gallery);
			System.out.println("��ϰ���� "+result);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/gallery/list";
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView selectAll() {
		//3�ܰ�
		List galleryList = galleryDAO.selectAll();
		
		//4�ܰ�
		ModelAndView mav = new ModelAndView();
		mav.addObject("galleryList", galleryList);
		mav.setViewName("gallery/list");
		return mav;
	}
	
	//�󼼺����ûó��
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	public ModelAndView select(int gallery_id) {
		System.out.println("gallery_id : "+gallery_id);
		Gallery gallery = galleryDAO.select(gallery_id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("gallery", gallery);
		mav.setViewName("gallery/detail");
		return mav;
	}
	
	//����ó��
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String edit(Gallery gallery) {
		int result = galleryDAO.update(gallery);
		//4�ܰ�� ������ ���� ����. ��?? ��û�� ���� detail�� ���� �����ҰŴϱ�
		return "redirect:/gallery/detail?gallery_id="+gallery.getGallery_id();
	}
	
	//����ó��
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(int gallery_id) {
		int result = galleryDAO.delete(gallery_id);
		return "redirect:/gallery/list";
	}
}
