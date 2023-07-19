package com.tjoeun.springAOP_xml;

import org.aspectj.lang.ProceedingJoinPoint;

// 공통 기능 메소드를 모아놓은 클래스
public class LogAOP {
	
//	before 
	public void before() {
		System.out.println("LogAOP 클래스의 before() 메소드가 실행됨");
	}
	
//	after-returning
	public void afterReturning() {
		System.out.println("LogAop 클래스의 afterReturning() 메소드가 실행됨");
	}
	
//	after-thworing
	public void afterThworing() {
		System.out.println("LogAop 클래스의 afterThworing() 메소드가 실행됨");
	}
	
//	after
	public void after() {
		System.out.println("LogAop 클래스의 after() 메소드가 실행됨");
	}
	
//	around
//	1. around AOP 메소드는 핵심 기능이 실행되고 난 후 리턴되는 데이터 타입을 예측할 수 없으므로 모든 데이터 타입을 포함할 수 있는
//		자바의 최 상위 클래스인 Object로 지정한다. 
//	2. around AOP 메소드의 인수인 ProceedingJoinPoint 인터페이스 타입의 객체로 스프링이 자동으로 실행할 핵심 기능(메소드)을 넘겨준다. 
//		=> 반드시 인수로 ProceedingJoinPoint 인터페이스 타입의 객체를 사용한다.
//		=> pom.xml 파일의 aspectjweaver dependency에 <scope>runtime</scope>이 있으면 PropceedingJoinPoin 인터페이스를 사용하 할 수 
//			없으므로 삭제하거나 주석을 처리한다.
//	3. around AOP 메소드는 try ~ 
	public Object around(ProceedingJoinPoint joinPoint) {
		
		return null;
	}
}
