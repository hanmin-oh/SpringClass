CREATE TABLE "MVCBOARD" (
    "IDX" NUMBER(*,0) NOT NULL, 
	"NAME" VARCHAR2(20 BYTE) NOT NULL, 
	"SUBJECT" VARCHAR2(200 BYTE) NOT NULL, 
	"CONTENT" VARCHAR2(2000 BYTE) NOT NULL, 
	"GUP" NUMBER(*,0), 
	"LEV" NUMBER(*,0), 
	"SEQ" NUMBER(*,0), 
	"HIT" NUMBER(*,0) DEFAULT 0, 
	"WRITEDATE" TIMESTAMP (6) DEFAULT sysdate, 
	CONSTRAINT "MVCBOARD_PK" PRIMARY KEY ("IDX")
);

insert into mvcboard (idx, name, subject, content)
values (mvcboard_idx_seq.nextval, '이순신' , '1등' , '명량해전');
insert into mvcboard (idx, name, subject, content)
values (mvcboard_idx_seq.nextval, '강감찬' , '2등' , '귀주대첩');
insert into mvcboard (idx, name, subject, content)
values (mvcboard_idx_seq.nextval, '을지문덕' , '3등' , '살수대첩');
insert into mvcboard (idx, name, subject, content)
values (mvcboard_idx_seq.nextval, '홍범도' , '4등' , '봉오동 전투');

delete from mvcboard;
drop sequence mvcboard_idx_seq;
create sequence mvcboard_idx_seq;
.

select * from mvcboard order by gup desc, seq asc;
select count(*) from mvcboard;