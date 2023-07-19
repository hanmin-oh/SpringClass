package com.tjoeun.springAOP_java;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

// java 파일을 이용해서 AOP 설정을 하려면 AOP를 설정할 클래스에 @Aspect 어노테이션을 붙여준다.
@Aspect
public class LogAOP {
	
//	pointcut을 지정하는 1번째 방법 => 빈 메소드를 이용해서 pointcut 메소드를 만든다.
//	적당한 이름으로 빈 메소드를 만든 후 @Pointcut 어노테이션을 붙여서 인수에 pointcut을 지정한다. 
//	@Pointcut("within() 또는 execution()을 이용한 pointcut")
//	public void 적당한이름() {
//		메소드가 실행할 내용이 없는 빈 메소드
//	}
//	pointcut 1개를 만들어서 여러 메소드에 적용시킬 수 있다.
	
	@Pointcut("within(com.tjoeun.springAOP_java.Student)")
	public void pointcutMethod() {	}
	
//	pointcut을 AOP 메소드에 적용하려면 AOP 어노테이션(@Before, AfterReturning, AfterThrowing, After, Around)의 인수로
//	pointcut을 설정한 빈 메소드 이름을 넣어주면 된다. 
	
	@Before("pointcutMethod()")
	public void before() {
		System.out.println("LogAOP 클래스의 before() 메소드가 실행됨");
	}
	
	@AfterReturning("pointcutMethod()")
	public void afterReturning() {
		System.out.println("LogAop 클래스의 afterReturning() 메소드가 실행됨");
	}
	
	@AfterThrowing("pointcutMethod()")
	public void afterThworing() {
		System.out.println("LogAop 클래스의 afterThworing() 메소드가 실행됨");
	}
	
	@After("pointcutMethod()")
	public void after() {
		System.out.println("LogAop 클래스의 after() 메소드가 실행됨");
	}
	
//	pointcut을 지정하는 2번째 방법 => AOP 어노테이션의 인수에 바로 pointcut을 지정할 수 있다.
	@Around("execution(* com.tjoeun.springAOP_java.W*.*())")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("LogAop 클래스의 around() 메소드가 실행됨 - 핵심 기능 실행 전");
		long start = System.currentTimeMillis(); // 시작 시간
		try {
			System.out.println("LogAop 클래스의 around() 메소드가 실행됨 - 핵심 기능 실행 중");
			Thread.sleep(2000);
			Object object = joinPoint.proceed(); // 핵심 기능을 실행한다.
			return object;
		} finally {
			System.out.println("LogAop 클래스의 around() 메소드가 실행됨 - 핵심 기능 실행 후");
			long end = System.currentTimeMillis(); // 종료 시간
			System.out.println("핵심 기능이 실행되는데 경과된 시간: " + (end - start) / 1000. + "초");
		}

	}
}
