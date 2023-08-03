
  CREATE TABLE "CARD"  (	
     "CONSUMERID" VARCHAR2(200 BYTE), 
	"AMOUNT" VARCHAR2(200 BYTE)
   ) ;

  CREATE TABLE "ticket"  (	
     "CONSUMERID" VARCHAR2(200 BYTE), 
	"COUNTNUM" VARCHAR2(200 BYTE)
    CONSTRAINT "CK_TICKET_COUNTNUM" CHECK (countnum < 5) ENABLE
   ) ;

delete from card;
delete from ticket;

insert into ticket(consumerid , countnum) values ('1111' , '4');
insert into ticket(consumerid , countnum) values ('2222' , '5');
insert into card (consumerId, amount) values ('han', '2');
select * from ticket;
select * from card;

