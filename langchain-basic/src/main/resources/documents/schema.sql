CREATE TABLE  `user` (
                         `id` bigint unsigned not null auto_increment,
                         `username` varchar(60) not null default '' comment '登录用户名',
                         `password` varchar(60) not null default '' comment '密码',
                         `phone` char(15) not null default '' comment '手机号',
                         `nickname` varchar(60) not null default '' comment '昵称',
                         `avatar` varchar(255) not null default '' comment '头像',
                         `role` varchar(30) not null default '用户' comment '身份标识（用户、员工、老板）',
                         `is_admin` tinyint(1) not null default 0 comment '是否为管理员(0-不是, 1-是)',
                         `related_store_id` bigint unsigned default 1395641928572928 comment '用户最近关联的门店id',
                         `create_time` datetime not null default current_timestamp comment '创建时间',
                         `update_time` datetime not null default current_timestamp comment '更新时间',
                         primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

CREATE TABLE  `user_address` (
                                 `id` bigint unsigned not null,
                                 `user_id` bigint not null comment '用户ID',
                                 `real_name` varchar(60) not null  comment '收货人姓名',
                                 `phone` varchar(15) not null  comment '收货人手机号',
                                 `province` varchar(30) not null  comment '省',
                                 `city` varchar(30) not null  comment '市',
                                 `district` varchar(30) not null  comment '区',
                                 `detail` varchar(180) not null  comment '详细地址',
                                 `defaulted` tinyint(1) not null default 0 comment '是否为默认地址',
                                 `create_time` datetime not null default current_timestamp comment '创建时间',
                                 `update_time` datetime not null default current_timestamp comment '更新时间',
                                 primary key (`id`),
                                 key (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户地址表';