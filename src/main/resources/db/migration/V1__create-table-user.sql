create table tb_role (
    id bigint primary key not null,
    authority varchar(30) not null
);

create table tb_user_role (
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    foreign key (user_id) references tb_user(id),
    foreign key (role_id) references tb_role(id)
);

create table tb_user (
    id bigint primary key not null,
    nickname varchar(30) not null,
    points int default 0,
    email varchar(100) not null,
    password varchar(255) not null,
    is_first_login boolean default true
)