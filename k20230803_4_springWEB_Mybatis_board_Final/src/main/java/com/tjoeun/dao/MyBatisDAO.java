package com.tjoeun.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.tjoeun.vo.MvcBoardVO;

// mapper와 연결에 사용할 인터페이스
// - 이 인터페이스의 풀 패키지와 이름을 mvcboard.xml 파일의 namespace에 정확히 적어야 한다.
public interface MyBatisDAO {

	
//	 - mapper로 사용되는 인터페이스의 추상 메소드 형식은 resultType id(parameterType)과 같은 형식으로 만든다.
//	 - MyBatisDAO 인터페이스의 추상 메소드 이름이 xml 파일의 sql 명령을 식별하는 id로 사용되고 추상 메소드의 인수로 전될된 데이터가 xml 파일의 sql
//	명령으로 전달된다. 
	void insert(MvcBoardVO mvcBoardVO);
	int selectCount();
	ArrayList<MvcBoardVO> selectList(HashMap<String, Integer> hmap);
	void increment(int idx);
	MvcBoardVO selectByIdx(int idx);
	void update(MvcBoardVO mvcBoardVO);
	void delete(int idx);
	void replyIncrement(HashMap<String, Integer> hmap);
	void replyInsert(MvcBoardVO mvcBoardVO);
	
}
