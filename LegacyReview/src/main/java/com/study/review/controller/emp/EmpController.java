package com.study.review.controller.emp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.study.review.model.domain.Dept;
import com.study.review.model.domain.Emp;
import com.study.review.model.service.EmpService;

//controller는 단지 일을 시키는 역할만 수행한다
@Controller
public class EmpController {
	@Autowired
	EmpService empService;
	
	@RequestMapping("/emp/list")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		List list = empService.selectAll();
		mav.addObject("empList", list);
		mav.setViewName("emp/list");
		return mav;
	}
	
	@RequestMapping("/emp/registform")
	public String registForm() {
		return "emp/regist_form";
	}
	
	@RequestMapping(value="/emp/regist", method = RequestMethod.POST)
	public String regist(Dept dept, Emp emp) {
		emp.setDept(dept);
		int result = empService.regist(emp);
		return "redirect:/emp/list";
	}
}
