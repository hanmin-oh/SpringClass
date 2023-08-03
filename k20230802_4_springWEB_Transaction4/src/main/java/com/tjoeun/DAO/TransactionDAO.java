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

//	내부 트랜잭션
	private TransactionTemplate transactionTemplate;
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void buyTicket(final CardVO cardVO) {
		logger.info("내부 트랜잭션 실행");
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
			//e.printStackTrace();
		}
	}
}
