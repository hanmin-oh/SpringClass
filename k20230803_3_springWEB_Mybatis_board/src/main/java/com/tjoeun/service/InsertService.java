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

public class InsertService implements MvcBoardService {

	private static final Logger logger = LoggerFactory.getLogger(InsertService.class);

	//다형성 구현 부모와 자식 똑같은 메소드, 부모 클래스에서 반드시 자식 클래스의 메소드 구현, 부모를 이용해 자식을 실행
	@Override
	public void execute(MvcBoardVO mvcBoardVO) {
		logger.info("InsertService 클래스의 execute() 메소드 실행 - VO 사용");
//		logger.info("{}" , mvcBoardVO);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardDAO mvcBoardDAO = ctx.getBean("mvcBoardDAO" , MvcBoardDAO.class);
		
//		메인글을 테이블에 저장하는 메소드를 호출한다. 
		mvcBoardDAO.insert(mvcBoardVO);
		
		
	}
	@Override
	public void execute(Model model) {
//		1. 컨트롤러에서 Model 인터페이스 객체에 저장해서 넘겨준 HttpServletRequest 객체에서 insert.jsp에서 입력한 데이터를 읽어낸다.
//		2. Model 인터페이스 객체는 key와 value로 구성된 데이터 구조를 가지므로 asMap() 메소드로 Map<String , Object> 타입으로 변환해서 사용한다. 
		Map<String, Object> map = model.asMap();
		
//		Model 인터페이스 객체가 Map<String, Object> 타입으로 변환되서 저장된 객체에서 key가 "request"인 value(insert.jsp에서 넘어온 데이터)를 얻어온다.
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
//		Model 인터페이스 객체에 저장되서 넘어온 HttpServletRequest 인터페이스 객체에서 insert.jsp에서 넘어온 데이터를 받는다.
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
//		MvcBoardVO 클래스의 bean을 얻어온다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardVO mvcBoardVO = ctx.getBean("mvcBoardVO" , MvcBoardVO.class);
		
//		MvcBoardVO 클래스 객체에 insert.jsp에서 HttpServletRequest 인터페이스 객체로 넘어온 데이터를 저장한다. 
		mvcBoardVO.setName(name);
		mvcBoardVO.setSubject(subject);
		mvcBoardVO.setContent(content);
		
		MvcBoardDAO mvcBoardDAO = ctx.getBean("mvcBoardDAO" , MvcBoardDAO.class);
		mvcBoardDAO.insert(mvcBoardVO);
	}

}
