package com.tjoeun.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.MvcBoardDAO;
import com.tjoeun.vo.MvcBoardVO;

public class IncrementService implements MvcBoardService {
	private static final Logger logger = LoggerFactory.getLogger(IncrementService.class);
	
	
	@Override
	public void execute(MvcBoardVO mvcBoardVO) {
	
	}

	@Override
	public void execute(Model model) {
		logger.info("IncrementService 클래스의 execute() 메소드 실행");
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
//		logger.info("조회수를 증가시킬 글번호: {}" , request.getParameter("idx"));
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		
//		조회수를 증가시키는 메소드를 실행한다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    MvcBoardDAO mvcBoardDAO = ctx.getBean("mvcBoardDAO", MvcBoardDAO.class);
        mvcBoardDAO.increment(idx);


	} 

}
