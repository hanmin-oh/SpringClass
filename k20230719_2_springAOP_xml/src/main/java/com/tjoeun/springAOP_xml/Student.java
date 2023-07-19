package com.tjoeun.springAOP_xml;

public class Student {
	private String name;
	private int age;
	private int gradeNum;
	private int classNum;
	
	public Student() {
		System.out.println("Student 클래스의 기본생성자로 bean을 만든다.");
	}
	
	public Student(String name, int age, int gradeNum, int classNum) {
		System.out.println("데이터를 전달받아 초기화시키는 생성자 실행");
		this.name = name;
		this.age = age;
		this.gradeNum = gradeNum;
		this.classNum = classNum;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getGradeNum() {
		return gradeNum;
	}
	public void setGradeNum(int gradeNum) {
		this.gradeNum = gradeNum;
	}
	public int getClassNum() {
		return classNum;
	}
	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", gradeNum=" + gradeNum + ", classNum=" + classNum + "]";
	}
	
//	student 클래스의 핵심 기능
	public void getStudentInfo() {
		System.out.println("Student 클래스의 핵심 기능");
		System.out.println("이름 : " + name);
		System.out.println("나이 : " + age);
		System.out.println("학년 : " + gradeNum);
		System.out.println("반 : " + classNum);
	}
 	
	public void test() {
		System.out.println("맛점!~");
		int i = 5 + 15;
		System.out.println(i);
	}
 	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
