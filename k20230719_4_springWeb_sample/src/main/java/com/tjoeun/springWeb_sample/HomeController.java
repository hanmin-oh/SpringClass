package com.tjoeun.springWeb_sample;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// @Controller 어노테이션이 붙여진 클래스가 컨트롤러로 사용된다.
// @Controller 어노테이션이 붙어있다 하더라도 모두 컨트롤러로 사용되는 것이 아니고 servlet-context.xml 파일에 프로젝트를 생성할 때 만들어준
// base-package로 지정된 패키지의 @Controller 어노테이션이 붙여진 클래스가 컨트롤러로 사용된다. 

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	주소 창으로 요청이 들어오면 컨트롤러의 @RequestMapping 어노테이션의 인수로 지정된 value 속성(생략가능)에 지정된 내용과
//	주소창의 컨텍스트 패스 뒤의 요청이 같으면 @RequestMapping 어노테이션이 붙어있는 메소드가 자동으로 실행된다.
//	method 속성 값으로 RequestMethod.GET이 지정되면 get 방식의 요청만 받아서 처리할 수 있고 RequestMethod.POST가 지정되면 post 방식의
//	요청만 받아서 처리할 수 있다. => 요청 방식이 일치하지 않으면 405 에러가 발생된다.
//	@RequestMapping(value = "/", method = RequestMethod.GET)
	
//	@RequestMapping 어노테이션에서 method 속성을 생략하면 get 방식과 post 방식을 구분하지 않고 모든 요청을 받아서 처리할 수 있다.
//	method 속성을 생략하면 value 속성만 남게 되고 이 경우 value 속성도 생략하고 요청만 적으면된다. 
	@RequestMapping("/")
//	@RequestMapping 어노테이션이 붙어있는 메소드는 필요한 작업을 실행하고 view 페이지(jsp파일)의 이름을 리턴한다.
//	view 페이지 이름은 문자열이므로 메소드의 리턴 타입은 특별한 경우를 제외하고 String으로 지정한다. 
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		// view 페이지 이름 앞에 위치하는 경로명과 뒤에 위치하는 확장명은 servlet-context.xml 파일에 설정되어 있다. 
		return "home";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
}
