--this script initiates db for h2 db (used in test profile)
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'john@domain.com', 'John', 'Steward')
insert into user (account_status, email, first_name) values ('NEW', 'brian@domain.com', 'Brian')
insert into user (account_status, email, first_name) values ('REMOVED', 'braindead@domain.com', 'poorhim')
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'peter@domain.com', 'Peter', 'Pen')

insert into blog_post values (null, 'Test post by confirmed user', 1)
insert into blog_post values (null, 'Test post by another confirmed user', 4)

insert into like_post values (null, 2, 1)