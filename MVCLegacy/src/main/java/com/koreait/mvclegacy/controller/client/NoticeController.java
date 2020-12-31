package com.koreait.mvclegacy.controller.client;

import java.util.List;

import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.koreait.mvclegacy.exception.DMLException;
import com.koreait.mvclegacy.model.domain.Notice;
import com.koreait.mvclegacy.model.notice.NoticeService;

@Controller
public class NoticeController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	@Autowired
	private NoticeService noticeService;
	
	//글쓰기 폼 요청
	@RequestMapping("/notice/registform")
	public String getRegistForm() {
		return "notice/regist_form";
	}
	
	//글쓰기 요청
	@RequestMapping(value="/notice/regist", method=RequestMethod.POST)
	public String regst(Notice notice) { 
		noticeService.insert(notice);
		return "redirect:/client/notice/list";
	}
	
	//모든 글 가져오기 요청
	@RequestMapping(value="/notice/list", method=RequestMethod.GET)
	public ModelAndView selectAll() {
		logger.debug("글 목록 메서드 호출~");
		ModelAndView mav = new ModelAndView();
		List noticeList = noticeService.selectAll();
		mav.addObject("noticeList",noticeList);
		mav.setViewName("notice/list");
		return mav;
	}
	
	//한건 가져오기
	@RequestMapping("/notice/detail")
	public ModelAndView select(int notice_id) {
		ModelAndView mav = new ModelAndView("notice/detail");
		Notice notice = noticeService.select(notice_id);
		mav.addObject("notice", notice);
		return mav;
	}
	
	//글 수정
	@RequestMapping(value="/notice/edit", method=RequestMethod.POST)
	public String edit(Notice notice) {
		noticeService.update(notice);
		return "redirect:/client/notice/detail?notice_id="+notice.getNotice_id();
	}
	
	//글 수정
	@RequestMapping(value="/notice/delete", method=RequestMethod.POST)
	public String del(int notice_id) {
		noticeService.delete(notice_id);
		return "redirect:/client/notice/list";
	}
	
	//스프링의 컨트롤러에 요청처리 메서드중 어느 하나라도 예외가 발생하면, 그 예외를 처리할 수 있는
	//별도의 메서드가 지원된다.. 어노테이션의 명시한 예외만 감자하여 처리..
	@ExceptionHandler(DMLException.class)
	public ModelAndView handleException(DMLException e) {
		ModelAndView mav = new ModelAndView();
		//어떤 내용을 담을 지
		mav.addObject("msg", e.getMessage());
		//어느 페이즈를 보여줄지
		mav.setViewName("message/result");
		return mav;
	}
}
