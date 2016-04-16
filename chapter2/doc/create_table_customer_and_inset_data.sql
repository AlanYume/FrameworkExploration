--create table customer
CREATE TABLE `customer` (
`id`  bigint NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`name`  varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '客户名称' ,
`contact`  varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '联系人' ,
`telephone`  varchar(255) CHARACTER SET utf8 NULL COMMENT '电话号码' ,
`email`  varchar(255) CHARACTER SET utf8 NULL COMMENT '电子邮箱' ,
`remark`  text CHARACTER SET utf8 NULL COMMENT '备注' ,
PRIMARY KEY (`id`)
) ENGINE=INNODB
;

-- innser data to customer
INSERT INTO `customer` VALUES ('1', 'customer1', 'Jack', '13512345678', 'jack@gmail.com', NULL);
INSERT INTO `customer` VALUES ('2', 'customer2', 'Rose', '13623456778', 'rose@gmail.com', NULL);