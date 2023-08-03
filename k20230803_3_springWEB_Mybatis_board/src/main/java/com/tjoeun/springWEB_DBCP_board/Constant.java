package com.tjoeun.springWEB_DBCP_board;

import org.springframework.jdbc.core.JdbcTemplate;

public class Constant {
	
//	 - JdbcTemplate 클래스 객체를 public static으로 선언한 이유는 public으로 선언해야 현재 클래스 외부에서 자유롭게 접근할 수 있고
//	static으로 선언해야 클래스 객체를 선언하지 않고 클래스 이름에 "."을 찍어서 접근할 수 있기 때문에다. 
//	 - 컨트롤러에서 프로젝트가 시작될 때 초기화된 JdbcTemplate 객체를 MvcBoardDAO 클래스에서 사용할 수 있도록 하기 위함이다.
	public static JdbcTemplate template;
	
	public Constant() {
		// TODO Auto-generated constructor stub
	}
	
	
}
