package com.model2.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model2.board.model.BoardDAO;
import com.model2.domain.Board;

public class ListController implements Controller{
	private BoardDAO dao;
	
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Board> list = dao.selectAll();
		ModelAndView mav = new ModelAndView("board/list");
		mav.addObject("boardList", list);
		return mav;
	}
}
