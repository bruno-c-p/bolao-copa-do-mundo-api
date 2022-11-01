create table tb_role (
    id bigint primary key not null,
    authority varchar(30) not null
);

create table tb_user (
    id bigint primary key not null,
    nickname varchar(30) not null,
    points int default 0,
    email varchar(100) not null,
    password varchar(255) not null,
    is_first_login boolean default true
);

create table tb_user_role (
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    foreign key (user_id) references tb_user(id),
    foreign key (role_id) references tb_role(id)
);

create table tb_log (
    id bigint primary key not null,
    user_id bigint not null,
    date timestamp not null,
    action varchar(30) not null,
    foreign key (user_id) references tb_user(id)
);

create table tb_group (
    id bigint primary key not null,
    name char not null
);

create table tb_team (
    id bigint primary key not null,
    name varchar(30) not null,
    acronym varchar(3) not null,
    group_id bigint not null,
    points int default 0,
    flag_url varchar(255) not null,
    fifa_ranking_position int
);

create table tb_match (
    id bigint primary key not null,
    date timestamp not null,
    result varchar(20),
    team_1 bigint not null,
    team_2 bigint not null,
    foreign key (team_1) references tb_team(id),
    foreign key (team_2) references tb_team(id)
);

create table tb_tip (
    id bigint primary key not null,
    user_id bigint not null,
    match_id bigint not null,
    result varchar(20) not null,
    foreign key (user_id) references tb_user(id),
    foreign key (match_id) references tb_match(id)
);
