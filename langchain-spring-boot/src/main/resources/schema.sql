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


CREATE TABLE  `user_channel` (
                                 `id` bigint unsigned not null,
                                 `user_id` bigint not null comment '用户ID',
                                 `channel` tinyint(4) not null  comment '用户渠道(0: 网页 1: 微信小程序)',
                                 `open_id` varchar(60) not null comment '渠道对应ID',
                                 primary key (`id`),
                                 unique (user_id, channel)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户渠道表';

CREATE TABLE  `boss_examine` (
                                 `id` bigint unsigned not null auto_increment,
                                 `user_id` bigint unsigned not null,
                                 `company_name` varchar(60) not null default '' comment '公司名称',
                                 `company_industry` varchar(60) not null default '' comment '所属行业',
                                 `business_license` varchar(255) not null default '' comment '营业执照',
                                 `store_info` json comment '门店信息（暂存）',
                                 `status` tinyint(4) not null default 0 comment '审核状态 0:审核中 1:审核通过 2:审核驳回',
                                 `paid` tinyint(1) not null default 0 comment '是否支付 0 未支付 1已支付',
                                 `reject_reason` varchar(255) default null comment '驳回理由',
                                 `create_time` datetime not null default current_timestamp comment '创建时间',
                                 `update_time` datetime not null default current_timestamp comment '更新时间',
                                 primary key (`id`),
                                 key (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='老板信息审核表';


CREATE TABLE  `boss_seat` (
                              `id` bigint unsigned not null auto_increment,
                              `store_id` bigint not null comment '门店名称',
                              `boss_id` bigint not null comment '门店名称',
                              primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='老板坐席门店关联表';

# EMPLOYEE DOMAIN;
DROP TABLE IF EXISTS `employee`;

CREATE TABLE  `employee` (
                             `id` bigint unsigned not null auto_increment,
                             `create_time` datetime not null default current_timestamp comment '创建时间',
                             `update_time` datetime not null default current_timestamp comment '更新时间',

                             `store_id` bigint unsigned not null comment '绑定门店ID',
                             `paid` tinyint(1) unsigned not null default 0 comment '是否支付',
                             `authentication_status` tinyint(4) unsigned not null default 0 comment '员工坐席状态(0未支付 1未认证 2已认证)',
                             `remark` varchar(255)not null default '' comment '备注',
                             `info` json comment '员工资料',

                             primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工表';


CREATE TABLE  `employee_examine` (
                                     `id` bigint unsigned not null auto_increment,
                                     `create_time` datetime not null default current_timestamp comment '创建时间',
                                     `update_time` datetime not null default current_timestamp comment '更新时间',
                                     `store_id` bigint unsigned not null comment '所属门店ID',
                                     `info` json not null comment '员工信息',
                                     `status` tinyint(4) not null default 0 comment '审核状态 0:审核中 1:已通过 2:已驳回',
                                     `reject_reason` varchar(255) default null comment '驳回理由',
                                     primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='老板信息审核表';

# STORE DOMAIN;
DROP TABLE IF EXISTS `store`;

CREATE TABLE  `store` (
                          `id` bigint unsigned not null auto_increment,
                          `boss_id` bigint unsigned not null comment '所属老板ID',
                          `name` varchar(60) not null default '' comment '门店名称',
                          `banners` json comment '门店banner轮播图数组',
                          `share_title` varchar(60) comment '分享标题',
                          `share_img` varchar(512) comment '分享图片地址',
                          `qr_code_img` varchar(512) comment '老板二维码图片地址',
                          `location` json comment '经纬度坐标',
                          `phone` varchar(15) not null default '' comment '联系方式',
                          `create_time` datetime not null default current_timestamp comment '创建时间',
                          `update_time` datetime not null default current_timestamp comment '更新时间',
                          primary key (`id`),
                          key (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='门店表';



# CATEGORY DOMAIN;
DROP TABLE IF EXISTS `category`;
CREATE TABLE  `category` (
                             `id` bigint unsigned not null auto_increment,
                             `name` varchar(60) not null default '' comment '分类名称',
                             `url` varchar(255) not null default '' comment '分类Logo',
                             `displayed` tinyint(1) not null default 1 comment '是否显示(0: 不显示, 1: 显示)',
                             `sequence` int(4) not null default 0 comment '展示顺序',
                             `create_time` datetime not null default current_timestamp comment '创建时间',
                             `update_time` datetime not null default current_timestamp comment '更新时间',
                             primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类表';


# PRODUCT DOMAIN;
DROP TABLE IF EXISTS `product`;
CREATE TABLE  `product` (
                            `id` bigint unsigned not null auto_increment,
                            `store_id` bigint unsigned not null comment '所属门店星系',
                            `category_id` bigint unsigned not null ,
                            `title` varchar(60) not null default '' comment '标题',
                            `subtitle` varchar(150) not null default '' comment '副标题',
                            `state` tinyint(1) not null default 0 comment '状态(0:草稿中, 1:下架, 2:上架)',
                            `logo` varchar(255) not null default '' comment '图标',
                            `hot_flag` tinyint(4) not null default 0 comment '是否为热销商品',
                            `total_sale` bigint not null default 0 comment '总销量',
                            `weight` bigint not null default 0 comment '重量(单位kg)',
                            `freight` decimal(10,2) not null default 0 comment '运费(单位元)',
                            `total_stock` bigint not null default 0 comment '总库存',
                            `show_price` decimal(10, 2) not null default 0  comment '展示价格, 取自第一个价格的sku',
                            `banners` json comment '轮播图地址数组',
                            `images` json comment '详情图地址数组',
                            `employee_rates` json comment '员工倍率数组',
                            `create_time` datetime not null default current_timestamp comment '创建时间',
                            `update_time` datetime not null default current_timestamp comment '更新时间',
                            primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表';

CREATE TABLE  `product_attr` (
                                 `id` bigint unsigned not null auto_increment,
                                 `product_id` bigint unsigned not null ,
                                 `name` varchar(60) not null default '' comment '规格名称',
                                 primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品规格表';

CREATE TABLE  `product_attr_val` (
                                     `id` bigint unsigned not null auto_increment,
                                     `attr_id` bigint unsigned not null ,
                                     `product_id` bigint unsigned not null ,
                                     `name` varchar(60) not null default '' comment '规格值',
                                     primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品规格值表';

CREATE TABLE  `product_sku` (
                                `id` bigint unsigned not null auto_increment,
                                `product_id` bigint unsigned not null comment '所属商品',
                                `code` varchar(255) not null comment 'SKU编码',
                                `price` decimal(10, 2) not null default 0  comment '价格',
                                `stock` bigint not null default 0 comment '库存',
                                `attributes` json  comment 'sku所对应的规格',
                                `create_time` datetime not null default current_timestamp comment '创建时间',
                                `update_time` datetime not null default current_timestamp comment '更新时间',
                                primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品sku表';

CREATE TABLE  `offer` (
                          `id` bigint unsigned not null auto_increment,
                          `create_time` datetime not null default current_timestamp comment '创建时间',
                          `update_time` datetime not null default current_timestamp comment '更新时间',
                          `user_id` bigint not null comment '报价单所属用户ID',
                          `related_employee_id` bigint default null comment '报价关联销售ID',
                          `status` tinyint(4) unsigned not null default 0 comment '报价单状态(0已报价 1已付款 2制作中 3已发货 4已完成)',
                          `place_time` datetime not null comment '下单时间，实际数据应该与创建时间不同，但是属于业务字段，应该由应用来初始化',
                          `order_no` varchar(100) not null comment '报价单编号',
                          `total_price` decimal(10, 2) not null comment '总价',
                          `total_weight` bigint unsigned not null default 0 comment '报价单中的总重量',
                          `store` json comment '报价单中的门店信息',
                          `product` json comment '报价单中的商品信息',
                          `address` json comment '报价单中的地址信息',
                          primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='报价单表';


DROP TABLE IF EXISTS  `customer`;

CREATE TABLE  `customer` (
                             `id` bigint unsigned not null auto_increment,
                             `create_time` datetime not null default current_timestamp comment '创建时间',
                             `update_time` datetime not null default current_timestamp comment '更新时间',
                             `employee_id` bigint unsigned not null comment '所属员工ID',
                             `employee_name` varchar(90) not null default ''comment '所属员工姓名（冗余字段）',

                             `user_id` bigint unsigned not null comment '客户的ID',
                             `avatar` varchar(255) not null default '' comment '客户头像',
                             `store_id` bigint unsigned not null comment '门店ID',
                             `name` varchar(90) not null default '' comment '姓名',
                             `phone` varchar(15) not null default '' comment '联系方式',
                             `gender` char(4) not null default 0 comment '性别(男 女)',
                             `remark` varchar(255) not null default '' comment '备注',
                             primary key (`id`),
                             key (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='门店客户表';


CREATE TABLE  `order` (
                          `id` bigint unsigned not null auto_increment,
                          `create_time` datetime not null default current_timestamp comment '创建时间',
                          `update_time` datetime not null default current_timestamp comment '更新时间',

                          `user_id` bigint unsigned not null comment '订单所属ID',
                          `order_no` varchar(50) not null default '' comment '订单号',
                          `price` decimal(10,2) not null default 0.00 comment '订单价格',
                          `paid` tinyint(1) not null default 0 comment '是否支付',
                          `type` tinyint(4) not null default 0 comment '订单类型: 0-老板坐席 1-员工坐席',
                          `place_time` datetime default null comment '下单时间',
                          `paid_time` datetime default null comment '支付时间',
                          `related_seat_id` bigint unsigned null comment '关联坐席ID',
                          primary key (`id`),
                          key (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表';

CREATE TABLE `boss_seat` (
                             `id` bigint unsigned not null auto_increment,
                             `create_time` datetime not null default current_timestamp comment '创建时间',
                             `update_time` datetime not null default current_timestamp comment '更新时间',

                             `boss_id` bigint unsigned not null comment '老板ID',
                             `store_id` bigint unsigned not null comment '门店ID',
                             primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='老板坐席表';