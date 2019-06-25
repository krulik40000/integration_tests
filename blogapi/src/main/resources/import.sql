--this script initiates db for h2 db (used in test profile)
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'john@domain.com', 'John', 'Steward')
insert into user (account_status, email, first_name) values ('NEW', 'brian@domain.com', 'Brian')

insert into user (account_status, email, first_name) values ('CONFIRMED', 'test@domain.com', 'test')
insert into user (account_status, email, first_name) values ('REMOVED', 'test2@domain.com', 'test2')

insert into blog_post values (null, 'Test post ', 1)
insert into blog_post values (null, 'Test post ', 3)
insert into blog_post values (null, 'Test post removed user ', 4)
