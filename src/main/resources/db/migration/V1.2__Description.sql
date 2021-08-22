-- bash exec
-- mvn flyway:migrate
create table myh2
(
	id int auto_increment,
	account_id varchar(100),
	name varchar(50),
	token char default 36,
	gmt_create bigint,
	gmt_modified bigint,
	constraint myh2_pk
		primary key (id)
);

