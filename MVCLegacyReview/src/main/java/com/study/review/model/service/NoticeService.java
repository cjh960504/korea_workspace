package com.study.review.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.review.model.dao.NoticeDAO;

@Service
public class NoticeService {
	private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);
	@Autowired
	private NoticeDAO noticeDAO;
	
	public void test() {
		logger.info("서비스 성공~");
	}
}
