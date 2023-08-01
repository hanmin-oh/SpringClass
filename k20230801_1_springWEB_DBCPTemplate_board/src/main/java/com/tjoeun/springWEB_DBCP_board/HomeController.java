package com.tjoeun.springWEB_DBCP_board;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tjoeun.service.ContentViewService;
import com.tjoeun.service.DeleteService;
import com.tjoeun.service.IncrementService;
import com.tjoeun.service.InsertService;
import com.tjoeun.service.MvcBoardService;
import com.tjoeun.service.ReplyService;
import com.tjoeun.service.SelectService;
import com.tjoeun.service.UpdateService;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	JdbcTemplate를 사용하려면 servlet-context.xml 파일에서 프로젝트가 시작될 때 DriverManagerDataSource 클래스의 bean(DB 연결 정보)을 참조해서
//	생성한 JdbcTemplate 클래스의 bean을 컨트롤러에서 JdbcTemplate 클래스 타입의 객체를 생성하고 넣어줘야 한다. ,,,,,,,,,
	private JdbcTemplate template;
	
//	JdbcTemplate 클래스 타입 객체의 getter, setter를 만든다. => 사실....getter는 안만들어도 된다. 
	public JdbcTemplate getTemplate() {
		return template;
	}

//	 - 프로젝트를 실행하면 스프링 환경 설정 파일인 servlet-context.xml 파일이 읽혀진 다음 JdbcTemplate 클래스 객체의 setter 메소드가
//	자동으로 실행되게 하기 위해서 @Autowired 어노테이션을 붙여준다. 
//	 - @AutoWired를 붙여준 메소드는 서버가 구동되는 단계에서 자동으로 실행되며 setter 메소드의 인수 template으로 스프링이 알아서
//	servlet-context.xml 파일에서 생성한 JdbcTemplate 클래스의 bena을 자동으로 전달한다.
//	 - 자동으로 실행된 setter 메소드는 자도으로 전달받은 JdbcTemplate 클래스의 bean으로 JdbcTemplate 클래스 객체를 초기화시킨다.
	@Autowired // @Atuowired 어노테이션을 부텽서 선언한 메소드는 서버 구동 단계에서 자동으로 실행된다. 
	public void setTemplate(JdbcTemplate template) {
		logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~!");
		this.template = template;
		
//		 - 여기까지 정상적으로 실행되면 컨트롤러에서 JdbcTemplate를 사용할 수 있게 된다. 
//		 - 데이터베이스 연결은 주로 DAO 클래스에서 하므로 컨트롤러 이외의 클래스에서 JdbcTemplate을 사용할 수 있게 하기 위해서 적당한
//		이름의 패키지(base-package)에 적당한 이름의 클래스(Constant)를 만들고 적당한 이름으로 작성한 클래스의 정적 필드에 servlet-con
//		text.xml 파일에서 생성된 컨트롤러의 JdbcTemplate 클래스 객체에 저장된 bean을 넣어준다.
		
//		적당한 이름으로 클래스를 만들고 JdbcTemplate 객체를 static으로 선언한 후 코딩한다.
		Constant.template = this.template;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request , Model model) {
		return "redirect:list";
	}
	
	@RequestMapping("/insert")
	public String insert (HttpServletRequest request , Model model) {
		return "insert";
	}
	
//	글 입력 폼에 입력된 내용을 테이블에 저장하는 메소드
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
		MvcBoardService service = ctx.getBean("reply" , ReplyService.class);
		service.execute(model);
		
		return "redirect:list";
	}
	
	
	
}
