insert into tb_user (nickname, email, password) values (
    'brunocp',
    'brunocp@gmail.com',
    '$2a$10$hom3/vwQkOAOi4iCVsLfEeTPm1jStxHpctjuiDXLe0ULHJ5//xPBa'
);

insert into tb_user (nickname, email, password) values (
    'bruno',
    'bruno@gmail.com',
    '$2a$10$hom3/vwQkOAOi4iCVsLfEeTPm1jStxHpctjuiDXLe0ULHJ5//xPBa'
);

insert into tb_role (authority) values ('ROLE_USER');
insert into tb_role (authority) values ('ROLE_ADMIN');

insert into tb_user_role (user_id, role_id) values (1, 1);
insert into tb_user_role (user_id, role_id) values (1, 2);
insert into tb_user_role (user_id, role_id) values (2, 1);