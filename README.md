# midcloud-sso

表结构

create schema if not exists sso collate utf8mb4_0900_ai_ci;

create table if not exists sso_permission
(
	id varchar(32) not null
		primary key,
	name varchar(32) null,
	parent_id varchar(32) null comment '父权限id',
	route varchar(32) null comment '路由',
	code varchar(32) null comment '标记',
	summary varchar(64) null comment '总结',
	cte_tm datetime null,
	upt_tm datetime null
);

create table if not exists sso_role
(
	id varchar(32) not null
		primary key,
	name varchar(32) null,
	cte_tm datetime null,
	upt_tm datetime null
)
comment '角色表';

create table if not exists sso_role_permission
(
	id varchar(32) not null
		primary key,
	role_id varchar(32) null,
	permission_id varchar(32) null
)
comment '角色权限表';

create table if not exists sso_role_system
(
	id varchar(32) null,
	role_id varchar(32) null,
	system_id varchar(32) null
)
comment '角色系统表';

create table if not exists sso_system
(
	id varchar(32) not null
		primary key,
	name varchar(32) null comment '系统名称',
	url varchar(128) null comment '根地址',
	cte_tm datetime null,
	upt_tm datetime null
)
comment '系统表';

create table if not exists sso_user
(
	id varchar(32) not null comment '主键
'
		primary key,
	username varchar(32) not null comment '用户名',
	password varchar(64) not null comment '密码',
	cte_tm datetime null comment '创建时间',
	upt_tm datetime null comment '更新时间'
)
comment '用户表';

create table if not exists sso_user_role
(
	id varchar(32) not null
		primary key,
	user_id varchar(32) null,
	role_id varchar(32) null
)
comment '用户权限表';

