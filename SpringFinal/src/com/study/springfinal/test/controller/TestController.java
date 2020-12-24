package com.study.springfinal.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/*������, �ý��ۿ� ���������� �ʴ� POJO�� ��ǥ
 * 	Plain Old Java Object : ������ �ڹٸ� �߱�
 * */
@Controller
public class TestController {
	//� url�� �������� �� � ��û�� ó���� ���� ����
	
	@RequestMapping(value="/hi", method=RequestMethod.GET)
	public ModelAndView test() {
		ModelAndView mav = new ModelAndView("test/result"); //setViewName�� ����
		return mav;
	}
}