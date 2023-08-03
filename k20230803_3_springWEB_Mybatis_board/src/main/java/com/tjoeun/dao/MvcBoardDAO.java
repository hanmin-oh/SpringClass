package com.tjoeun.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.tjoeun.springWEB_DBCP_board.Constant;
import com.tjoeun.vo.MvcBoardVO;

public class MvcBoardDAO {

	private static final Logger logger = LoggerFactory.getLogger(MvcBoardDAO.class);
//  JdbcTemplate설정
	private JdbcTemplate template;
	
//	 - DAO 클래스의 bean이 기본 생성자로 생성되는 순간 servlet-context.xml 파일에서 생성되서 컨트롤러가 전달받아 Constant 클래스의 JdbcTemplate
//	클래스 타입의 static 객체에 저장한 bean으로 초기화한다. 
	public MvcBoardDAO() {
		template = Constant.template;
	}
	
	public void insert(final MvcBoardVO mvcBoardVO) {
		String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq)"
				+ "values (mvcboard_idx_seq.nextval, ? , ? , ? , mvcboard_idx_seq.currval, 0 , 0)";
		
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
		String sql = "select count(*) from mvcboard";
		return template.queryForObject(sql, Integer.class);
		
	}

//	 - SelectService 클래스에서 호출되는 브라우저에 표시할 1페이지 분량의 글 목록을 얻어오기 위해서 시작 인덱스, 끝 인덱스가 저장된 HashMap 
//	객체를 넘겨받고 1페이지 분량의 글 목록을 얻어오는 select sql 명령을 실행하는 메소드
	public ArrayList<MvcBoardVO> selectList(HashMap<String, Integer> hmap) {
		
		String sql = "select * from (" 
					+ "select rownum rnum, AA.* from("
						+ "select * from mvcboard order by gup desc, seq asc" 
					+ ") AA where rownum <=  " + hmap.get("endNo")
				+ ") where rnum >= " + hmap.get("startNo");
		
		return (ArrayList<MvcBoardVO>) template.query(sql, new BeanPropertyRowMapper(MvcBoardVO.class));
		
	}

	public void increment(final int idx) {
		String sql = "update mvcboard set hit = hit + 1 where idx = " + idx;
		template.update(sql);
	}

	public MvcBoardVO selectByIdx(int idx) {
		String sql = "select * from mvcboard where idx =" + idx; 
		return template.queryForObject(sql, new BeanPropertyRowMapper(MvcBoardVO.class));
	}

	// 메소드 1
	public void update(final int idx, final String subject, final String content) {
		String sql =  String.format("update mvcboard set subject = '%s', content = '%s' where idx = %d", subject, content, idx);
		template.update(sql);
	}
	
	// 메소드 2
	public void update(final MvcBoardVO mvcBoardVO) {
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
		String sql = "delete mvcboard where idx = " + idx;
		template.update(sql);
	}

	
	public void replyIncrement(final HashMap<String, Integer>hmap) {
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
