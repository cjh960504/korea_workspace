package test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController{
	private Student student;
	
	public void setStudent(Student student) {
		this.student = student;
	}
	
	@RequestMapping(value="/test")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		student.first();
		student.second();
		student.third();
		return null;
	}
}
