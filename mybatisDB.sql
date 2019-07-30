CREATE TABLE `users` (
  `userName` varchar(32) NOT NULL COMMENT '用户名称',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `registerTime` datetime NOT NULL COMMENT '注册时间',
  PRIMARY KEY  (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;