--this script initiates db for h2 db (used in test profile)
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'john@domain.com', 'John', 'Steward')
insert into user (account_status, email, first_name) values ('NEW', 'brian@domain.com', 'Brian')
insert into user (account_status, email, first_name, last_name) values ('NEW','tester@domain.com', 'Tohn', 'Tester') 
insert into user (account_status, email, first_name, last_name) values ('REMOVED', 'removed@domain.com', 'Rohn', 'Removed') 
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'sohn@domain.com', 'Bohn', 'jteward')
insert into blog_post ( values (null, 'test post', 1)