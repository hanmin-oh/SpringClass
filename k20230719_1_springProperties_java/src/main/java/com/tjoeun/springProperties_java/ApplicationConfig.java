package com.tjoeun.springProperties_java;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class ApplicationConfig {
	
//	properties 파일에서 읽어온 데이터를 저장할 필드를 선언한다.
//	properties 파일의 내용이 저장되서 리턴되는 리턴 타입이 PropertySourcesPlaceholderConfigurer인 메소드가 
//	리턴하는 내용 중에서 필드에 저장한 데이터의 key를 @Value 어노테이션에 ${admin.id}와 같이 사용해서 @Val
//	ue 어노테이션이 설정된 필드에 저장한다. 
	@Value("${admin.id}")
	private String adminId; 
	@Value("${admin.pw}")
	private String adminPw;
	@Value("${sub_admin.id}")
	private String sub_adminId; 
	@Value("${sub_admin.pw}")
	private String sub_adminPw;
	
	//AdminConnection 클래스의 bean을 만든다.
	@Bean
	public AdminConnection adminConnection() {
		
		AdminConnection adminConnection = new AdminConnection();
		adminConnection.setAdminId(adminId);
		adminConnection.setAdminPw(adminPw);
		adminConnection.setSub_adminId(sub_adminId);
		adminConnection.setSub_adminPw(sub_adminPw);
		
		return adminConnection;
	}
	
//	@Configuration 어노테이션을 붙여서 만든 클래스에서 @Bean 어노테이션을 붙여 bean을 생성하는 메소드가 실행될 때 자동으로
//	실행되는 메소드로 properties 파일의 데이터를 읽어서 필드를 초기화 할 준비를 하는 메소드 => 반드시 정적(static) 메소드
	@Bean
	public static PropertySourcesPlaceholderConfigurer configurer() {
		
		System.out.println("리턴 타입이 PropertySourcesPlaceholderConfigurer인 메소드 자동 실행");
		PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		
		// 읽어야 할 properties 파일이 1개일 경우 => 변수
		// Resource 인터페이스 타입의 변수에 ClassPathResource 클래스의 생성자로 읽어들일 properties 파일의
		// 이름을 넘겨서 properties 파일의 내용을 읽는다.
		//Resource resource = new ClassPathResource("admin.properties");
		// Resource 인터페이스 타입의 변수로 읽어들인 properties 파일의 내용을 setLocation 메소드로 
		// placeholderConfigurer 클래스 타입의 객체에 저장해서 리턴한다.
		// placeholderConfigurer.setLocation(resource);
		
		// 읽어야 할 properties 파일이 2개 이상일 경우 => 배열 => 배열은 properties 파일의 개수만큼 만든다.
		Resource[] resources = new Resource[2];
		resources[0] = new ClassPathResource("admin.properties");
		resources[1] = new ClassPathResource("sub_admin.properties");
		placeholderConfigurer.setLocations(resources);
		return placeholderConfigurer;
	}
	
	
	
}
