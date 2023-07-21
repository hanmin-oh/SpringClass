package com.tjoeun.springWeb_controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// 클라이언트로부터 서비스 요청이 들어왔을 때 컨트롤러로 진입하고 컨트롤러는 요청에 따른 작업을 실행한 후 뷰 페이지로 데이터를 전달한다.

//	컨트롤러 제작 순서
//		1. base-package로 지정된 패키지에 적당한 이름으로 클래스를 만들고 @Controller 어노테이션을 붙여서 컨트롤러로 사용할 클래스임을 
//		스프링에게 알려준다.
//		2. @RequestMapping 어노테이션의 인수로 클라이언트에서 넘어오는 요청을 지정한다. 
//		3. 요청을 처리할 적당한 이름의 메소드를 만든다.

@Controller
public class MyController {
	
	private static final Logger logger = LoggerFactory.getLogger(MyController.class);
	
	@RequestMapping("/view") //클라이언트에서 서비스 요청을 받는다.
	public String view() {
//		System.out.println("MyController의 view() 메소드 실행");
		logger.info("MyController의 view() 메소드 실행");
		return "view";
	}
	
	@RequestMapping("/model")
//	@RequestMapping 어노테이션이 설정된 메소드는 Model 인터페이스 객체를 인수로 가질 수 있다.
//	Model 인터페이스 객체는 컨트롤러에서 뷰 페이지로 넘겨줄 데이터를 저장한다.
	public String model(Model model) {
		
		// addAttribute(key, value) : Model 인터페이스 객체에 key에 따른 value를 넣어준다.
		model.addAttribute("id" , "hs");
		model.addAttribute("pw" , "0515");
		model.addAttribute("name" , "이순신");
		return "model/model";
	}
	
	@RequestMapping("/modelAndView")
	// ModelAndView 클래스 객체는 뷰 페이지의 이름과 뷰 페이지로 넘겨줄 데이터를 저장한다.
	// 뷰 페이지 이름과 뷰 페이지로 넘겨줄 데이터를 ModelAndView 클래스 객체에 저장해서 리턴시켜야 한다. 
	// 메소드의 리턴입에 ModelAndView를 지정한다. 
	public ModelAndView modelAndView() {
		logger.info("MyController의 modelAndView() 메소드 실행");
		
		// 뷰 페이지로 넘겨줄 데이터와 뷰 페이지 이름을 저장할 ModelAndView 클래스 객체를 선언한다. 
		ModelAndView modelAndView = new ModelAndView();
		//setViewName() 메소드로 ModelAndView 클래스 객체에 뷰 페이지 이름을 넣어준다. 
		modelAndView.setViewName("modelAndView/modelAndView");
		// addObject(key, value) : ModelAndView 클래스 객체에 key에 따른 value를 넣어준다.
		modelAndView.addObject("id" , "ujmd");
		modelAndView.addObject("pw" , "0515");
		modelAndView.addObject("name" , "을지문덕");
		return modelAndView;
	}
	
	
	
}
