package com.tjoeun.springWEB_DBCP_board;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tjoeun.dao.MyBatisDAO;
import com.tjoeun.vo.MvcBoardList;
import com.tjoeun.vo.MvcBoardVO;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

//	servlet-context.xml 파일에서 생성한 mybatis bean(sqlSession)을 위해 SqlSession 객체 생성
	@Autowired // 생성한 mybatis bean을 자동으로 객체에 넣어주기 위해 @Autowired 어노테이션을 붙여준다.
	private SqlSession sqlSession;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) {
		return "redirect:list";
	}

	@RequestMapping("/insert")
	public String insert(HttpServletRequest request, Model model) {
		return "insert";
	}

//	글 입력 폼에 입력된 내용을 테이블에 저장하는 메소드
	@RequestMapping("/insertOK")
	public String insertOK(HttpServletRequest request, Model model, MvcBoardVO mvcBoardVO) {
		logger.info("컨트롤러의 insertOK() 메소드 실행 - 커맨드 객체 사용");
//		model.addAttribute("request" , request);
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
//		MvcBoardService service = ctx.getBean("insert" , InsertService.class);
//		service.execute(model);

//		mapper로 사용할 인터페이스 객체에 mybatis mapper를 넣어준다.
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);

//		메인글을 저장하는 insert sql 명령을 실행한다. 
		mapper.insert(mvcBoardVO);
		return "redirect:list";
	}

//	브라우저에 출력할 1페이지 분량의 글 목록을 얻어오고 1페이지 분량의 글 목록을 출력하는 페이지를 호출하는 메소드
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		logger.info("컨트롤러의 list() 메소드 실행");
//		model.addAttribute("request" , request);
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
//		MvcBoardService service = ctx.getBean("select" , SelectService.class);
//		service.execute(model);

		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);

//		1페이제 표시할 글의 개수, 브라우저에 표시할 페이지 번호, 전체 글의 개수를 저장한다. 
		int pageSize = 10;
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) {
		}

		int totalCount = mapper.selectCount();
//		logger.info("전체 글의 개수 : {}", totalCount);

//		1페이지 분량의 글 목록과 페이징 작업에 사용할 변수를 초기화시킨다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardList mvcBoardList = ctx.getBean("mvcBoardList", MvcBoardList.class);
		mvcBoardList.initMvcBoardList(pageSize, totalCount, currentPage);
//		logger.info("{}" , mvcBoardList);

//		1페이지 분량의 글 목록을 얻어온다.
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("startNo", mvcBoardList.getStartNo());
		hmap.put("endNo", mvcBoardList.getEndNo());
		mvcBoardList.setList(mapper.selectList(hmap));
		logger.info("{}", mvcBoardList.getList());

//		list.jsp로 넘겨줄 데이터를 Model 인터페이스 객체에 넣어준다.
		model.addAttribute("boardList", mvcBoardList);
		return "list";
	}

//	조회수를 증가시키는 메소드
	@RequestMapping("/increment")
	public String increment(HttpServletRequest request, Model model) {
		logger.info("컨트롤러의 increment() 메소드 실행");

//		model.addAttribute("request" , request);
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
//		MvcBoardService service = ctx.getBean("increment" , IncrementService.class);
//		service.execute(model);

		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		int idx = Integer.parseInt(request.getParameter("idx"));
		mapper.increment(idx);
//		조회수를 증가시킨 글번호와 작업 후 돌아갈 페이지 번호를 Model 인터페이스 객체에 넣어준다.
		model.addAttribute("idx", request.getParameter("idx"));
		model.addAttribute("currentPage", request.getParameter("currentPage"));

		return "redirect:contentView";
	}

//	조회수를 증가시킨 글 1건을 얻어오는 메소드
	@RequestMapping("/contentView")
	public String contentView(HttpServletRequest request, Model model) {

//		model.addAttribute("request" , request);
//		조회수를 증가시킨 글 1건을 얻어오는 메소드
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
//		MvcBoardService service = ctx.getBean("contentView" , ContentViewService.class);
//		service.execute(model);

		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
//		Model 인터페이스 객체에 저장되서 넘어온 HttpServletRequest 인터페이스 객체에서 조회수를 증가시킨 글번호를 받는다.
		int idx = Integer.parseInt(request.getParameter("idx"));

//		조회수를 증가시킨 글 1건을 얻어오는 메소드를 실행해서 얻어온 글을 MvcBoardVO 클래스 객체에 저장한다. 
		MvcBoardVO mvcBoardVO = mapper.selectByIdx(idx);
		logger.info("{}", mvcBoardVO);
//	   	contentView.jsp에 넘겨줄 글 1건, 작업 후 돌아갈 페이지 번호, 줄바꿈에 사용할 "\r\n"을 Model 인터페이스 객체에 넣어준다.
		model.addAttribute("vo", mvcBoardVO);
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		model.addAttribute("enter", "\r\n");

		return "contentView";
	}

//	글 1건을 수정하는 메소드
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Model model, MvcBoardVO mvcBoardVO) {

//		model.addAttribute("request" , request);
//		
//		글 1건을 수정하는 메소드를 실행한다. 
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
//		MvcBoardService service = ctx.getBean("update" , UpdateService.class);
//		service.execute(model);

		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
//		수정 후 현재 페이지로 돌아가기 위해 curretnPage를 넣어준다.
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		mapper.update(mvcBoardVO);

		return "redirect:list";
	}

//	글 1건을 삭제하는 메소드
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {

//		model.addAttribute("request" , request);
//		글 1건을 수정하는 메소드를 실행한다. 
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
//		MvcBoardService service = ctx.getBean("delete" , DeleteService.class);
//		service.execute(model);

		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
//		Model 인터페이스 객체에 저장되서 넘어온 HttpServletRequest 인터페이스 객체에서 수정할 글번호와 제목, 내용의 데이터를 받는다.
		int idx = Integer.parseInt(request.getParameter("idx"));

		mapper.delete(idx);

		return "redirect:list";
	}

//	답글을 입력하기 위해 브라우저에 출력할 메인글을 얻어오고 답글을 입력하는 페이지를 호출하는 메소드
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model) {

//		model.addAttribute("request" , request);
//		답변을 입력할 글 1건을 얻어오는 메소드를 실행한다. 
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
//		MvcBoardService service = ctx.getBean("contentView" , ContentViewService.class);
//		service.execute(model);
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
//		Model 인터페이스 객체에 저장되서 넘어온 HttpServletRequest 인터페이스 객체에서 조회수를 증가시킨 글번호를 받는다.
		int idx = Integer.parseInt(request.getParameter("idx"));

//		조회수를 증가시킨 글 1건을 얻어오는 메소드를 실행해서 얻어온 글을 MvcBoardVO 클래스 객체에 저장한다. 
		MvcBoardVO mvcBoardVO = mapper.selectByIdx(idx);

//	   	contentView.jsp에 넘겨줄 글 1건, 작업 후 돌아갈 페이지 번호, 줄바꿈에 사용할 "\r\n"을 Model 인터페이스 객체에 넣어준다.
		model.addAttribute("vo", mvcBoardVO);
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		model.addAttribute("enter", "\r\n");

		return "reply";
	}

//	답글을 입력하기 위해 브라우저에 출력할 메인글을 얻어오고 답글을 입력하는 페이지를 호출하는 메소드
	@RequestMapping("/replyInsert")
	public String replyInsert(HttpServletRequest request, Model model, MvcBoardVO mvcBoardVO) {

//		model.addAttribute("request" , request);
//		답변을 입력할 글 1건을 얻어오는 메소드를 실행한다. 
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
//		MvcBoardService service = ctx.getBean("reply" , ReplyService.class);
//		service.execute(model);

		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		// 답글은 질문글 바로 아래에 위치해야 하므로 lev와 seq를 1씩 증가시켜 넣어준다.
//		int lev = Integer.parseInt(request.getParameter("lev"));
//		int seq = Integer.parseInt(request.getParameter("seq"));
		mvcBoardVO.setLev(mvcBoardVO.getLev() + 1);
		mvcBoardVO.setSeq(mvcBoardVO.getSeq() + 1);
//		답글이 삽입될 위치를 정하기 위해 조건에 만족하는 seq를 1씩 증가시키는 메소드를 실행한다.
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("gup", mvcBoardVO.getGup());
		hmap.put("seq", mvcBoardVO.getSeq());
		mapper.replyIncrement(hmap);

//		답글을 저장하는 메소드를 실행한다.
		mapper.replyInsert(mvcBoardVO);

//		답글 저장 후 돌아갈 페이지 번호를 Model 인터페이스 객체에 넣어준다.
		model.addAttribute("currentPage", request.getParameter("currentPage"));

		return "redirect:list";
	}

}
