package com.springmvc.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model2.board.model.BoardDAO;

public class DeleteController implements Controller{
	private BoardDAO boardDAO;
	
	
	public void setBoardDAO(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}


	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//3단계 일 시키기
		String board_id = request.getParameter("board_id");
		int result = boardDAO.delete(Integer.parseInt(board_id));
		
		//4단계 저장
		ModelAndView mav = new ModelAndView();
		if(result==0) {
			mav.addObject("msg", "삭제 실패!");
			mav.setViewName("error/result"); //실패한 경우 url
		}else {
			mav.setViewName("redirect:/board/list");//성공한 경우 url
		}
		return mav;
	}
}
