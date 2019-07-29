CREATE TABLE `users` (
  `id` int(11) NOT NULL auto_increment,
  `userName` varchar(32) UNIQUE NOT NULL COMMENT '用户名称',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `registerTime` datetime NOT NULL COMMENT '注册时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;