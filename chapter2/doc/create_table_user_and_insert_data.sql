CREATE TABLE `user` (
`username`  varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '用户id' ,
`password`  varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '密码' ,
PRIMARY KEY (`username`)
)
;

INSERT INTO `user` VALUES ('aaa', '111');
