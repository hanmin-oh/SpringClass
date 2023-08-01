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
		// 답글 정보를 MvcBoardVO 클래스 객체에 저장한다. 이 때, lev와 seq는 1씩 증가시켜 저장한다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardVO vo = ctx.getBean("mvcBoardVO", MvcBoardVO.class);
		vo.setIdx(idx);
		vo.setGup(gup);
		vo.setLev(lev + 1);
		vo.setSeq(seq + 1);
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);

		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("gup", vo.getGup());
		hmap.put("seq", vo.getSeq());
		MvcBoardDAO mvcBoardDAO = ctx.getBean("mvcBoardDAO", MvcBoardDAO.class);
		mvcBoardDAO.incrementSeq();

		// 답글을 저장하는 메소드를 실행한다.
		mvcBoardDAO.replyInsert(vo);
	}

}
