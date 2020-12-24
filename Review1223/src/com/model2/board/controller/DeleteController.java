package com.model2.board.controller;

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
		String board_id = request.getParameter("board_id");
		int result = boardDAO.delete(Integer.parseInt(board_id));
		ModelAndView mav = new ModelAndView();
		if(result==0) {
			mav.setViewName("error/result");
			mav.addObject("msg", "��������!");
		}else {
			mav.setViewName("redirect:/board/list");
		}
		return mav;
	}
}
