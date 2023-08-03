package com.tjoeun.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tjoeun.DAO.TransactionDAO;
import com.tjoeun.springWEB_Transaction.HomeController;
import com.tjoeun.vo.CardVO;

public class TicketInsert implements TransactionService {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private TransactionDAO dao;

	public void setDao(TransactionDAO dao) {
		this.dao = dao;
	}

//	외부 트랜잭션 
	private TransactionTemplate transactionTemplate2;

	public void setTransactionTemplate2(TransactionTemplate transactionTemplate2) {
		this.transactionTemplate2 = transactionTemplate2;
	}

	@Override
	public void execute(final CardVO cardVO) {

//		ticket.jsp의 입력과 상관없이 외부 트랜잭션이 있을 때와 없을 때를 구분해서 트랜잭션을 실행한다.

//		REQUIRED(기본값, 0) <=> REQUIRES_NEW(3)
//		 - REQUIRED
//		 1. 외부 트랜잭션이 존재한다면 외부 트랜잭션으로 합류하고 외부 트랜잭션이 없다면 새로운 트랜잭션을 생성한다.
//		 2. 중간에 롤백이 발생한다면 모두 하나의 트랜잭션이기 때문에 진행 상황이 모두 롤백된다. 
//		 - REQUIRES_NEW(3)
//		무조건 새로운 트랜잭션을 생성하고 각각의 트랜잭션이 롤백 되더라도 서로 영향을 주지 않는다.
//		 
//		SUPPORTS(1) <=> NOT_SUPPORTED(4)
//		 - SUPPORTS
//		1. 외부 트랜잭션이 있다면 합류하고 진행 중인 외부 트랜잭션이 없다면 트랜잭션을 생성하지 않는다.
//		 - NOT_SUPPORTED(4)
//		1. 외부 트랜잭션이 있다면 보류시키고 진행 중인 외부 트랜잭션이 없다면 트랜잭션을 생성하지 않는다.
//		
//		MANDATORY(2) <=> NEVER(5)
//		 - MANDATORY(2)
//		1. 외부 트랜잭션에 합류한다. 만약 외부 트랜잭션이 없다면 예외를 발생시킨다.
//		 - NEVER(5)
//		1. 트랜잭션을 생성하지 않고 외부 트랜잭션이 존재한다면 예외를 발생시킨다.

//		propagationBehavior 속성 지정에 따라 트랜잭션 처리가 달라진다.
//		 1. 외부 트랜잭션(transactionTemplate2) 0, 내부 트랜잭션(transactionTemplate) 0이면 모두 적용된다. 
//		 2. 외부 트랜잭션이 없고, 내부 트랜잭션이 0이면 내부 트랜잭션이 정상 처리된다. 
//		 3. 외부 트랜잭션이 있고, 내부 트랜잭션이 1이면 모두 적용된다. 
//		 4. 외부 트랜잭션이 없고, 내부 트랜잭션이 1이면 모두 적용되지 않는다.
//		 5. 외부 트랜잭션이 있고, 내부 트랜잭션이 2이면 모두 적용된다.
//		 6. 외부 트랜잭션이 없고, 내부 트랜잭션이 2이면 예외가 발생된다.

//		외부 트랜잭션이 없는 경우 => dao 바로 실행

		cardVO.setAmount("1");
		dao.buyTicket(cardVO);
		cardVO.setAmount("5");
		dao.buyTicket(cardVO);

//		외부 트랜잭션이 있는 경우 
		logger.info("외부 트랜잭션 실행");
		try {
			transactionTemplate2.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					cardVO.setAmount("1");
					dao.buyTicket(cardVO);
					cardVO.setAmount("5");
					dao.buyTicket(cardVO);
				}
			});
		} catch (Exception e) {

		}

	}

}
