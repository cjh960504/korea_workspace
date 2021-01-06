package com.koreait.fashionshop.controller.member;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.koreait.fashionshop.exception.MailSendException;
import com.koreait.fashionshop.exception.MemberRegistException;
import com.koreait.fashionshop.model.domain.Member;
import com.koreait.fashionshop.model.domain.TopCategory;
import com.koreait.fashionshop.model.member.service.MemberSerivce;
import com.koreait.fashionshop.model.product.service.TopCategoryService;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberSerivce memberService;
	
	//회원가입폼 요청
	@RequestMapping(value="/shop/member/registForm", method=RequestMethod.GET)
	public String getRegistForm() {
		return "shop/member/signup";
	}
	
	@RequestMapping(value="/shop/member/regist", method=RequestMethod.POST, produces="text/html;charset=utf-8")
	@ResponseBody
	public String regist(Member member) {
		logger.debug("아이디 : "+member.getUser_id());
		logger.debug("이름 : "+member.getName());
		logger.debug("비번: "+member.getPassword());
		logger.debug("이메일id : "+member.getEmail_id());
		logger.debug("이메일서버 : "+member.getEmail_server());
		logger.debug("우편번호 : "+member.getZipcode());
		logger.debug("주소 : "+member.getAddr());
		memberService.regist(member);
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"result\":1,");
		sb.append("\"msg\":\"회원가입 성공\"");
		sb.append("}");
		return sb.toString();
	}
	
	@ExceptionHandler(MemberRegistException.class)
	@ResponseBody
	public String handleException(MemberRegistException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"result\":0,");
		sb.append("\"msg\":\""+e.getMessage()+"\"");
		sb.append("}");
		return sb.toString();
	}
	
	@ExceptionHandler(MailSendException.class)
	public ModelAndView handleException(MailSendException e) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/shop/error/result");
		mav.addObject("msg", e.getMessage()); //사용자가 보게 될 에러 메시지
		//해당 기능의 오류는 관리자가 알아야하지 사용자가 알 필요는 없다.
		//시스템 관리자들에게 알려야함
		return mav;
	}
}
