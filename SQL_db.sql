delete from user where id >=1;
select * from user;
update user set role='ADMIN' where id=1 ;
select
        book0_.id as id1_0_,
        book0_.author as author2_0_,
        book0_.available as availabl3_0_,
        book0_.title as title4_0_ 
    from
        book book0_;

delete from users where id =40;
select * from users;
update users set role='ADMIN' where id=35 ;
select * from books;
delete from books where id >=1;
select
        book0_.id as id1_0_,
        book0_.author as author2_0_,
        book0_.available as availabl3_0_,
        book0_.title as title4_0_ 
    from
        Books book0_;
        
select * from rents;
delete from rents where id >=1;
drop table rents;

select * from resetpasswordtoken;
drop table resetpasswordtoken;