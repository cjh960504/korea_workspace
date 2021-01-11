package com.koreait.fashionshop.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.koreait.fashionshop.exception.LoginRequiredException;

/*
 * 앞으로 로그인이 필요한 서비스 여부를 처리하기 위한 코드는, 컨트롤러에 두지 않고,
 * 지금 이 객체로 공통화시켜 AOP를 적용하겠다.
*/
public class MemberSessionCheckAspect {
	private static final Logger logger = LoggerFactory.getLogger(MemberSessionCheckAspect.class);
	public Object sessionCheck(ProceedingJoinPoint joinPoint) throws Throwable{
		Object target = joinPoint.getTarget();//원래 호출하려했던 객체
		logger.debug("원래 호출하려했던 객체는 " + target);
		logger.debug("원래 호출하려했던 메서드는 "+joinPoint.getSignature());
		Object[] args = joinPoint.getArgs();//원래 호출하려했던 메서드의 매개변수
	
		//현재의 요청이 세션을 가지고 있는지를 판단하여, 적절한 제어...
		
		//세션을 얻기 위해서는 HttpServletRequest필요! target매개변수로 넣어주면 요청자는 
		//메서드를 실행하기 위해 request객체를 매개변수로 넣어줄것임!
		HttpServletRequest request = null;
		for(Object arg:args) {
			logger.debug("매개변수명은 "+ arg);
			//왼쪽의 있는 변수가 오른쪽 자료형이니?
			if(arg instanceof HttpServletRequest) {//request 객체라면..
				request = (HttpServletRequest)arg;
			}
		}
		//1. 세션이 없다면?? 예외를 발생시킬 것임--> ExceptionHandler를 거쳐서 클라이언트에게 적절한 응답처리
		
		///2. 세션이 있다면? 그대로 원래 호출하려했던 메서드 진행시켜주자!
		HttpSession session = null;
		session = request.getSession();
		Object result = null;
		if(session.getAttribute("member")==null) {
			throw new LoginRequiredException("로그인이 필요한 서비스입니다.");
		}else {
			//원래 요청의 흐름을 그대로 진행...
			//원래 호출하려했던 메서드를 대신 호출
			//MainController를 수행 후 ModelAndView를 반환
			result = joinPoint.proceed(); 
		}
		
		return result;
	}
}
