package com.tjoeun.springDI_xml_in_java;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//	java 파일에서 xml 파일의 bean 설정 정보를 읽어오려면 @ImportResource 어노테이션으로 읽어들일 xml 파일을 java 파일에 포함시킨다. 

@ImportResource("classpath:applicationCTX.xml")

@Configuration  
public class ApplicationConfig {
	
	@Bean
	public Student student2() {
		Student student = new Student();
		student.setName("을지문덕");
		student.setAge(46);
		ArrayList<String> subject = new ArrayList<String>();
		subject.add("Java");
		subject.add("JSP");
		subject.add("spring");
		student.setSubject(subject);
		student.setHeight(177);
		student.setWeight(69);
		
		return student; 
	}
	
	
	
	
}
