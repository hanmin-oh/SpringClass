package com.tjoeun.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tjoeun.springWEB_Transaction.HomeController;
import com.tjoeun.vo.CardVO;

public class TransactionDAO {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

//	Jdbc template 
	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	/*
	 * // 트랜잭션 처리를 위해 PlatformTransactionManager 선언 private
	 * PlatformTransactionManager transactionManager;
	 * 
	 * // 트랜잭션 처리를 위한 PlatformTransactionManager 초기화하기 위해 setter 메소드를 선언하고
	 * servlet-context.xml 파일에서 초기화 시킨다. public void
	 * setTransactionManager(PlatformTransactionManager transactionManager) {
	 * this.transactionManager = transactionManager; }
	 */

//	트랜잭션 처리를 위해 사용했던 PlatformTransactionManager 객체 대신 트랜잭션 템플릿을 사용하기 위해  TransactionTemplate 선언
	private TransactionTemplate transactionTemplate;

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void buyTicket(final CardVO cardVO) {

		/*
		 * TransactionDefinition definition = new DefaultTransactionDefinition();
		 * TransactionStatus status = transactionManager.getTransaction(definition);
		 * 
		 * try { String sql = "insert into card (consumerId, amount) values (?, ?)";
		 * template.update(sql, new PreparedStatementSetter() {
		 * 
		 * @Override public void setValues(PreparedStatement ps) throws SQLException {
		 * ps.setString(1, cardVO.getConsumerId()); ps.setString(2, cardVO.getAmount());
		 * } });
		 * 
		 * template.update(new PreparedStatementCreator() {
		 * 
		 * @Override public PreparedStatement createPreparedStatement(Connection con)
		 * throws SQLException { String sql =
		 * "insert into ticket (consumerId, countnum) values (?, ?)"; PreparedStatement
		 * ps = con.prepareStatement(sql); ps.setString(1, cardVO.getConsumerId());
		 * ps.setString(2, cardVO.getAmount()); return ps; } });
		 * 
		 * logger.info("트랜잭션이 정상적으로 처리되었으므로 commit 합니다.");
		 * transactionManager.commit(status); } catch (Exception e) {
		 * logger.info("트랜잭션이 정상적으로 처리되지 않았으므로 rollback 합니다.");
		 * transactionManager.rollback(status); }
		 */

//		트랜잭션 템플릿 코딩
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					String sql = "insert into card (consumerId, amount) values (?, ?)";
					template.update(sql, new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setString(1, cardVO.getConsumerId());
							ps.setString(2, cardVO.getAmount());
						}
					});
					
					template.update(new PreparedStatementCreator() {

						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							String sql = "insert into ticket (consumerId, countnum) values (?, ?)";
							PreparedStatement ps = con.prepareStatement(sql);
							ps.setString(1, cardVO.getConsumerId());
							ps.setString(2, cardVO.getAmount());
							return ps;
						}
					});
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
