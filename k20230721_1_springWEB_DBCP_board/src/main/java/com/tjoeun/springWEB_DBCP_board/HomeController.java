package com.tjoeun.springWEB_DBCP_board;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tjoeun.service.ContentViewService;
import com.tjoeun.service.DeleteService;
import com.tjoeun.service.IncrementService;
import com.tjoeun.service.InsertService;
import com.tjoeun.service.MvcBoardService;
import com.tjoeun.service.SelectService;
import com.tjoeun.service.UpdateService;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	 1. 프로젝트가 실행되면 최초의 요청 "/"을 받아 대문 페이지를 요청한다.
//	 2. 게시판의 대문을 list.jsp로 지정해서 글 목록을 보여준다.
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request , Model model) {
		return "redirect:list";
	}
	
	@RequestMapping("/insert")
	public String insert (HttpServletRequest request , Model model) {
		return "insert";
	}
	
	/*
	@RequestMapping("/insertOK")
	public String insertOK (HttpServletRequest request , Model model) {
		
		// insert.jsp에서 넘어오는 데이터를 받는다. 
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
//		MvcBoardVO 클래스의 bean을 얻어온다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardVO mvcBoardVO = ctx.getBean("mvcBoardVO" , MvcBoardVO.class);
		
//		MvcBoardVO 클래스 객체에 insert.jsp에서 넘어온 데이터를 저장한다. 
		mvcBoardVO.setName(name);
		mvcBoardVO.setSubject(subject);
		mvcBoardVO.setContent(content);
		
//		테이블에 메인글을 저장하는 메소드 호출
		MvcBoardService service = ctx.getBean("insert" , InsertService.class);
		service.execute(mvcBoardVO);
		
		
		return "insert";
	}
	*/
	
	/*
//	글 입력 폼에 입력된 내용을 테이블에 저장하는 메소드 => 커맨드 객체 사용
	@RequestMapping("/insertOK")
	public String insertOK (HttpServletRequest request , Model model, MvcBoardVO mvcBoardVO) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("insert" , InsertService.class);
		service.execute(mvcBoardVO);
		return "insert";
	}
	*/
	
//	글 입력 폼에 입력된 내용을 테이블에 저장하는 메소드 => Model 인터페이스 객체 사용
	@RequestMapping("/insertOK")
	public String insertOK (HttpServletRequest request , Model model) {
		// insert.jsp에서 입력한 데이터가 저장된 HttpServletRequest 인터페이스 객체를 Model 인터페이스 객체에 저장한다.
		model.addAttribute("request" , request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("insert" , InsertService.class);
		service.execute(model);
		return "redirect:list"; //@RequestMapping("/list") 어노테이션이 지정된 메소드를 실행한다. 
	}
	
//	브라우저에 출력할 1페이지 분량의 글 목록을 얻어오고 1페이지 분량의 글 목록을 출력하는 페이지를 호출하는 메소드
	@RequestMapping("/list")
	public String list (HttpServletRequest request , Model model) {
		logger.info("컨트롤러의 list() 메소드 실행");
		
//		컨트롤러에 list로 요청하는 페이지에서 넘어오는 브라우저에 표시할 페이지 번호가 저장된 HttpServletRequest 인터페이스 객체를 Model 인터페이스 객체에 저장
		model.addAttribute("request" , request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("select" , SelectService.class);
		service.execute(model);
		
		return "list";
	}
	
//	조회수를 증가시키는 메소드
	@RequestMapping("/increment")
	public String increment(HttpServletRequest request, Model model) {
		logger.info("컨트롤러의 increment() 메소드 실행");
		
		model.addAttribute("request" , request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("increment" , IncrementService.class);
		service.execute(model);
		
		
//		조회수를 증가시킨 글번호와 작업 후 돌아갈 페이지 번호를 Model 인터페이스 객체에 넣어준다.
		model.addAttribute("idx" , request.getParameter("idx"));
		model.addAttribute("currentPage" , request.getParameter("currentPage"));
		
		return "redirect:contentView";
	}

//	조회수를 증가시킨 글 1건을 얻어오는 메소드
	@RequestMapping("/contentView")
	public String contentView(HttpServletRequest request, Model model) {
//		컨트롤러에서 요청하는 페이지에서 넘어오는 조회수를 증가한 글의 글번호와 그 글이 있는 페이지 번호가 저장된 HttpServletRequest 객체를
//		Model 객체에 저장한다. 
		model.addAttribute("request" , request);
		
//		조회수를 증가시킨 글 1건을 얻어오는 메소드
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("contentView" , ContentViewService.class);
		service.execute(model);
		
		return "contentView";
	}
	
//	글 1건을 수정하는 메소드
	@RequestMapping("/update")
	public String update(HttpServletRequest request , Model model) {
		
		model.addAttribute("request" , request);
		
//		글 1건을 수정하는 메소드를 실행한다. 
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("update" , UpdateService.class);
		service.execute(model);
		
		return "redirect:list";
	}
	
//	글 1건을 삭제하는 메소드
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request , Model model) {
		
		model.addAttribute("request" , request);
		
//		글 1건을 수정하는 메소드를 실행한다. 
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("delete" , DeleteService.class);
		service.execute(model);
		
		return "redirect:list";
	}
	
//	답글을 입력하기 위해 브라우저에 출력할 메인글을 얻어오고 답글을 입력하는 페이지를 호출하는 메소드
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request , Model model) {
		
		model.addAttribute("request" , request);
		
//		답변을 입력할 글 1건을 얻어오는 메소드를 실행한다. 
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("contentView" , ContentViewService.class);
		service.execute(model);
		
		return "reply";
	}
//	답글을 입력하기 위해 브라우저에 출력할 메인글을 얻어오고 답글을 입력하는 페이지를 호출하는 메소드
	@RequestMapping("/replyInsert")
	public String replyInsert(HttpServletRequest request , Model model) {
		
		model.addAttribute("request" , request);
		
//		답변을 입력할 글 1건을 얻어오는 메소드를 실행한다. 
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("contentView" , ContentViewService.class);
		service.execute(model);
		
		return "redirect:reply";
	}
	
	
	
}
