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
    create_id    varchar(128)     default ''                not null comment '创建用户id',
    gmt_modified datetime         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    update_id    varchar(128)     default '-1'              not null comment '更新用户id',
    del_flag     tinyint unsigned default '0'               not null comment '逻辑删除字段 0未删除 1已删除',
    del_id       varchar(128)     default '-1'              not null comment '逻辑删除id',
    version      bigint           default 1                 null,
    constraint idx_username
        unique (username, del_id)
)
    comment '用户表' collate = utf8mb4_general_ci
                     row_format = DYNAMIC;

