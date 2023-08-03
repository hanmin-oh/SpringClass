package com.tjoeun.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.MvcBoardDAO;
import com.tjoeun.vo.MvcBoardVO;

public class ReplyService implements MvcBoardService {

	@Override
	public void execute(MvcBoardVO mvcBoardVO) {
	}

	@Override
	public void execute(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		// request 객체로 넘어오는 답글 정보를 받는다.
		int idx = Integer.parseInt(request.getParameter("idx"));
		int gup = Integer.parseInt(request.getParameter("gup"));
		int lev = Integer.parseInt(request.getParameter("lev"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardDAO mvcBoardDAO = ctx.getBean("mvcBoardDAO", MvcBoardDAO.class);
		
//		HttpServletRequest 인터페이스 객체에서 받은 데이터를 MvcBoardVo 클래스의 bean에 넣어준다.
		MvcBoardVO mvcBoardVO = ctx.getBean("mvcBoardVO", MvcBoardVO.class);
		mvcBoardVO.setIdx(idx);
		mvcBoardVO.setName(name);
		mvcBoardVO.setSubject(subject);
		mvcBoardVO.setContent(content);
		mvcBoardVO.setGup(gup);
//		답글은 질문글 바로 아래에 위치해야 하므로 lev와 seq를 1씩 증가시켜 넣어준다.
		mvcBoardVO.setLev(lev + 1);
		mvcBoardVO.setSeq(seq + 1);
		
//		답글이 삽입될 위치를 정하기 위해 조건에 만족하는 seq를 1씩 증가시키는 메소드를 실행한다.
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("gup", mvcBoardVO.getGup());
		hmap.put("seq", mvcBoardVO.getSeq());
		mvcBoardDAO.replyIncrement(hmap);

//		답글을 저장하는 메소드를 실행한다.
		mvcBoardDAO.replyInsert(mvcBoardVO);
		
//		답글 저장 후 돌아갈 페이지 번호를 Model 인터페이스 객체에 넣어준다.
	    model.addAttribute("currentPage" , request.getParameter("currentPage"));

	}

}
