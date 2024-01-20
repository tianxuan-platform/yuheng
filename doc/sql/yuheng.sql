create database yuheng;


-- auto-generated definition
create table yuheng.t_user
(
    id           varchar(128)                               not null
        primary key,
    username     varchar(128)     default ''                not null comment '用户名称',
    password     varchar(128)     default ''                not null comment '密码',
    slat         varchar(128)     default ''                not null comment '用户混合加密的盐',
    nickname     varchar(128)     default ''                not null comment '用户昵称',
    remark       varchar(500)     default ''                not null comment '备注',
    face_url     varchar(255)     default ''                not null comment '头像',
    gmt_create   datetime         default CURRENT_TIMESTAMP not null comment '创建时间',
    create_id    varchar(128)     default '-1'                not null comment '创建用户id',
    gmt_modified datetime         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    update_id    varchar(128)     default '-1'              not null comment '更新用户id',
    del_flag     tinyint unsigned default '0'               not null comment '逻辑删除字段 0未删除 1已删除',
    del_id       varchar(128)     default '-1'              not null comment '逻辑删除id',
    version      bigint           default 1                 not null comment '乐观锁版本标识',
    constraint idx_username
        unique (username, del_id)
)
    comment '用户表' collate = utf8mb4_general_ci
                     row_format = DYNAMIC;


INSERT INTO yuheng.t_user (id, username, password, slat, nickname, remark, face_url, gmt_create, create_id, gmt_modified, update_id, del_flag, del_id, version)
VALUES ('1702676846703136770', 'admin', '8d0df03c2ab1b9bcce717de8741f15a5', '777918', '管理员', '这个是管理员账号备注信息', 'http://www.wuyiccc.com/imgs/avatar2.jpg', '2023-09-15 21:32:49', '1702671830822948866', '2024-01-19 00:32:52', '-1', 0, '-1', 1)
