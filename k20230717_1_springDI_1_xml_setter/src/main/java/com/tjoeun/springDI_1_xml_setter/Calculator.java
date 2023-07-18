package com.tjoeun.springDI_1_xml_setter;

// MyCalculator 클래스에서 호출되는 firstNum, secondNum을 인수로 넘겨받아 사칙연사을 실행하는 메소드를 모아놓은 클래스
public class Calculator {
	
	public void add(int firstNum, int secondNum) {
	      System.out.println("add()");
	      int result = firstNum + secondNum;
	      System.out.println(firstNum + " + " + secondNum + " = " + result);
	   }
	   
	   public void sub(int firstNum, int secondNum) {
	      System.out.println("sub()");
	      int result = firstNum - secondNum;
	      System.out.println(firstNum + " - " + secondNum + " = " + result);
	   }
	   
	   public void mul(int firstNum, int secondNum) {
	      System.out.println("mul()");
	      int result = firstNum * secondNum;
	      System.out.println(firstNum + " * " + secondNum + " = " + result);
	   }
	   
	   public void div(int firstNum, int secondNum) {
	      System.out.println("div()");
	      int result = firstNum / secondNum;
	      System.out.println(firstNum + " / " + secondNum + " = " + result);
	   }
	

}
