--this script initiates db for h2 db (used in test profile)
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'john@domain.com', 'John', 'Steward')
insert into user (account_status, email, first_name) values ('NEW', 'brian@domain.com', 'Brian')
insert into user (account_status, email, first_name) values ('REMOVED', 'test@domain.com', 'Removed')
insert into user (account_status, email) values ('CONFIRMED', 'aaa@domain.com')
insert into user (account_status, email) values ('CONFIRMED', 'bbb@domain.com')

insert into blog_post values (null, 'test', 4)
insert into blog_post values (null, 'for test two likes', 4)
insert into blog_post values (null, 'no likes', 5)
insert into blog_post values (null, 'post for user with id 1', 1)
insert into blog_post values (null, 'post for removed user', 3)

