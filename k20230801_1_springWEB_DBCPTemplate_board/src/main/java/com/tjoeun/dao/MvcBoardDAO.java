package com.tjoeun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.tjoeun.springWEB_DBCP_board.Constant;
import com.tjoeun.vo.MvcBoardVO;

public class MvcBoardDAO {

	private static final Logger logger = LoggerFactory.getLogger(MvcBoardDAO.class);
// JdbcTemplate설정
//	DAO 클래스에서 JdbcTemplate을 사용하기 위해 JdbcTemplate 클래스 타입의 객체를 선언한다. 
	private JdbcTemplate template;
	
//	DAO 클래스의 bean이 기본 생성자로 생성되는 순간 servlet-context.xml 파일에서 생성되서 컨트롤러가 전달받아 Constant 클래스의 JdbcTemplate
//	클래스 타입의 static 객체에 저장한 bean으로 초기화한다. 
	public MvcBoardDAO() {
		template = Constant.template;
	}
	
	
//	DBCP에 사용할 DataSource 인터페이스 객체를 선언한다.
	/*
	 * private DataSource dataSource;
	 * 
	 * // MvcBoadrDAO 클래스의 bean이 생성될 떄 오라클과 연결한다. public MvcBoardDAO() { try {
	 * template = Constant.template; Context context = new InitialContext();
	 * dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle"); }
	 * catch (NamingException e) { // e.printStackTrace(); logger.info("연결실패!!"); }
	 * }
	 */
// 여기까지는 DBCP 방식을 사용하는 객체를 초기화하는 부분이므로 JdbcTemplate으로 코드 변환이 완료되면 모두 주석으로 처리
	
//	 - insert, delete, update sql 명령을 실행하는 메소드의 인수로 넘어온 데이터가 중간에 값이 변경되면 안되기 때문에 JdbcTemplate에서는
//	반드시 final을 붙여서 인수로 넘어온 데이터를 수정할 수 없도록 선언해야 한다.
	public void insert(final MvcBoardVO mvcBoardVO) {
		/*
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq)"
					+ "values (mvcboard_idx_seq.nextval, ? , ? , ? , mvcboard_idx_seq.currval, 0 , 0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mvcBoardVO.getName());
			pstmt.setString(2, mvcBoardVO.getSubject());
			pstmt.setString(3, mvcBoardVO.getContent());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {	if (conn != null) {	try {conn.close();	} catch (SQLException e) {	e.printStackTrace();}}}
		*/
		String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq)"
				+ "values (mvcboard_idx_seq.nextval, ? , ? , ? , mvcboard_idx_seq.currval, 0 , 0)";
		
//		update() : 테이블의 내용이 갱신되는 sql 명령 => insert, delete, update
//		query() : 테이블의 내용이 갱신되지 않는 sql 명령 => select => 결과가 여러건일 경우
//		queryForInt() : 테이블의 내용이 갱신되지 않는 sql 명령 => select => 결과가 정수일 경우
//		queryForObject() : 테이블의 내용이 갱신되지 않는 sql 명령 => select => 결과가 1건일 경우
		
//		 - update(sql 명령 , "?"를 채운다)
//		 - update() 메소드의 2번째 인수로 PreparedStatementSetter 인터페이스 객체를 익명으로 구현해서 @Override된 setValues() 메소드에서 실행할
//		sql 명령의 "?"를 채운다.
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, mvcBoardVO.getName());
				pstmt.setString(2, mvcBoardVO.getSubject());
				pstmt.setString(3, mvcBoardVO.getContent());
			}
		});
	}

// SelectService 클래스에서 호출되는 테이블에 저장된 전체 글의 개수를 얻어오는 select sql 명령을 실행하는 메소드
	public int selectCount() {
		/*
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "select count(*) from mvcboard";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			// 테이블에 저장된 글의 개수는 null이 나올리가 없으므로 조건 비교는 필요없다.
			rs.next();
			result = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {	if (conn != null) {	try {conn.close();	} catch (SQLException e) {	e.printStackTrace();}}}
		
		return result;
		*/
		String sql = "select count(*) from mvcboard";
//		return template.queryForInt(sql);
		return template.queryForObject(sql, Integer.class);
		
	}

//	SelectService 클래스에서 호출되는 브라우저에 표시할 1페이지 분량의 글 목록을 얻어오기 위해서 시작 인덱스, 끝 인덱스가 저장된 HashMap 
//	객체를 넘겨받고 1페이지 분량의 글 목록을 얻어오는 select sql 명령을 실행하는 메소드
	public ArrayList<MvcBoardVO> selectList(HashMap<String, Integer> hmap) {
		/*
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MvcBoardVO> list = null; // 1페이지 분량의 글 목록을 저장해 리턴시킬 ArryList

		try {
			conn = dataSource.getConnection();
			String sql = "select * from (" 
									+ "select rownum rnum, AA.* from("
										+ "select * from mvcboard order by gup desc, seq asc" 
									+ ") AA where rownum <= ?"
							+ ") where rnum >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, hmap.get("endNo"));
			pstmt.setInt(2, hmap.get("startNo"));
			rs = pstmt.executeQuery();

//			ResultSet 객체에 저장된 1페이지 분량의 글 목록을 저장할 ArrayList 객체 생성
			list = new ArrayList<MvcBoardVO>();
//			ResultSet 객체에 저장된 글의 개수만큼 반복하며 ArrayList에 저장한다. 
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				MvcBoardVO mvcBoardVO = ctx.getBean("mvcBoardVO", MvcBoardVO.class);
				mvcBoardVO.setIdx(rs.getInt("idx"));
				mvcBoardVO.setName(rs.getString("name"));
				mvcBoardVO.setSubject(rs.getString("subject"));
				mvcBoardVO.setContent(rs.getString("content"));
				mvcBoardVO.setGup(rs.getInt("gup"));
				mvcBoardVO.setLev(rs.getInt("lev"));
				mvcBoardVO.setSeq(rs.getInt("seq"));
				mvcBoardVO.setHit(rs.getInt("hit"));
				mvcBoardVO.setWriteDate(rs.getTimestamp("writeDate"));
				list.add(mvcBoardVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {	if (conn != null) {	try {conn.close();	} catch (SQLException e) {	e.printStackTrace();}}}
		
		return list;
		*/
		
//		JdbcTemplate을 사용하는 경우 select sql 명령에는 "?"를 사용할 수 없다. 데이터가 저장된 변수를 직접 사용한다. 
		String sql = "select * from (" 
					+ "select rownum rnum, AA.* from("
						+ "select * from mvcboard order by gup desc, seq asc" 
					+ ") AA where rownum <=  " + hmap.get("endNo")
				+ ") where rnum >= " + hmap.get("startNo");
		
//		 - 필드 이름과 .class 의 필드 이름이 같을 때 넣어준다.  --- 테이블 이름과 vo클래스의 필드 이름과 form의 name 속성값 동일하게!!
//		 - select sql 명령 실행결과를 BeanPropertyRowMapper 클래스 생성자의 인수인 MvcBoardVO 클래스로 넘겨서 sql 명령 실행 결과를 저장시켜
//		리턴한다 리턴은 무슨 유턴이야? 왜 돌아?. 
//		 - query() 메소드 실행 결과는 List 인터페이스 타입으로 리턴되기 때문에 메소드의 리턴 타입으로 형변환시켜야 한다.  
//		 - 데이터베이스 테이블의 필드 이름과 BeanPropertyRowMapper 클래스 생성자의 인수로 전달되는 클래스의 필드 이름과 같아야 정상적으로
//		처리된다고 생각했겠지 그건 경기도 오산이라고. 
		return (ArrayList<MvcBoardVO>) template.query(sql, new BeanPropertyRowMapper(MvcBoardVO.class));
		
	}

	public void increment(final int idx) {
		/*
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard set hit = hit + 1 where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {	if (conn != null) {	try {conn.close();	} catch (SQLException e) {	e.printStackTrace();}}}
		*/
		String sql = "update mvcboard set hit = hit + 1 where idx = " + idx;
		template.update(sql);
	}

	public MvcBoardVO selectByIdx(int idx) {
		/*
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MvcBoardVO mvcBoardVO = null; // 조회수를 증가시킨 글 1건을 얻어와 저장시켜 리턴할 객체 선언

		try {
			conn = dataSource.getConnection();
			String sql = "select * from mvcboard where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();

			// ResultSet 객체에 저장된 글 1건을 리턴시키기 위해서 MvcBoardVO 클래스 객체에 저장한다.
			if (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				mvcBoardVO = ctx.getBean("mvcBoardVO", MvcBoardVO.class);
				mvcBoardVO.setIdx(rs.getInt("idx"));
				mvcBoardVO.setName(rs.getString("name"));
				mvcBoardVO.setSubject(rs.getString("subject"));
				mvcBoardVO.setContent(rs.getString("content"));
				mvcBoardVO.setGup(rs.getInt("gup"));
				mvcBoardVO.setLev(rs.getInt("lev"));
				mvcBoardVO.setSeq(rs.getInt("seq"));
				mvcBoardVO.setHit(rs.getInt("hit"));
				mvcBoardVO.setWriteDate(rs.getTimestamp("writeDate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {	if (conn != null) {	try {conn.close();	} catch (SQLException e) {	e.printStackTrace();}}}

		return mvcBoardVO;
		*/
		String sql = "select * from mvcboard where idx =" + idx; 
		return template.queryForObject(sql, new BeanPropertyRowMapper(MvcBoardVO.class));
	}

	// 메소드 1
	public void update(final int idx, final String subject, final String content) {
		/*
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setInt(3, idx);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {	if (conn != null) {	try {conn.close();	} catch (SQLException e) {	e.printStackTrace();}}}
		*/
		
		String sql =  String.format("update mvcboard set subject = '%s', content = '%s' where idx = %d", subject, content, idx);
		template.update(sql);
	}
	
	
	// 메소드 2
	public void update(final MvcBoardVO mvcBoardVO) {
		/*
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mvcBoardVO.getSubject());
			pstmt.setString(2, mvcBoardVO.getContent());
			pstmt.setInt(3, mvcBoardVO.getIdx());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {	if (conn != null) {	try {conn.close();	} catch (SQLException e) {	e.printStackTrace();}}}
	}
 	*/
		String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, mvcBoardVO.getSubject());
				pstmt.setString(2, mvcBoardVO.getContent());
				pstmt.setInt(3, mvcBoardVO.getIdx());
			}
		});
	}
	
	public void delete(final int idx) {
		/*
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "delete mvcboard where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {	if (conn != null) {	try {conn.close();	} catch (SQLException e) {	e.printStackTrace();}}}
		*/
		
		String sql = "delete mvcboard where idx = " + idx;
		template.update(sql);
	}

	
	public void replyIncrement(final HashMap<String, Integer>hmap) {
		/*

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard set seq = seq + 1 where gup = ? and seq >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, hmap.get("gup"));
			pstmt.setInt(2, hmap.get("seq"));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {	if (conn != null) {	try {conn.close();	} catch (SQLException e) {	e.printStackTrace();}}}
		*/
		String sql = "update mvcboard set seq = seq + 1 where gup = ? and seq >= ?";
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setInt(1, hmap.get("gup"));
				pstmt.setInt(2, hmap.get("seq"));
			}
		});
		
	}

	public void replyInsert(final MvcBoardVO mvcBoardVO) {
		
		/*
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "insert into mvcboard (idx, name, subject, content, gup, lev ,seq) " + 
						 "values (mvcboard_idx_seq.nextval, ? , ? , ? , ? , ? , ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mvcBoardVO.getName());
			pstmt.setString(2, mvcBoardVO.getSubject());
			pstmt.setString(3, mvcBoardVO.getContent());
			pstmt.setInt(4, mvcBoardVO.getGup());
			pstmt.setInt(5, mvcBoardVO.getLev());
			pstmt.setInt(6, mvcBoardVO.getSeq());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {	if (conn != null) {	try {conn.close();	} catch (SQLException e) {	e.printStackTrace();}}}
		*/
		String sql =  "insert into mvcboard (idx, name, subject, content, gup, lev ,seq) " + 
				 "values (mvcboard_idx_seq.nextval, ? , ? , ? , ? , ? , ?)";
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, mvcBoardVO.getName());
				pstmt.setString(2, mvcBoardVO.getSubject());
				pstmt.setString(3, mvcBoardVO.getContent());
				pstmt.setInt(4, mvcBoardVO.getGup());
				pstmt.setInt(5, mvcBoardVO.getLev());
				pstmt.setInt(6, mvcBoardVO.getSeq());
			}
		});
		
		
	}

}
