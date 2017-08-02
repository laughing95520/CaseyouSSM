# Host: localhost  (Version: 5.5.20)
# Date: 2017-05-15 22:40:12
# Generator: MySQL-Front 5.3  (Build 4.89)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "user"
#

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(32) NOT NULL DEFAULT '',
  `email` varchar(50) NOT NULL,
  `sex` tinyint(1) DEFAULT NULL COMMENT '0-男 1- 女',
  `place` varchar(100) DEFAULT NULL,
  `birthday` varchar(20) DEFAULT NULL,
  `headimg` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

#
# Data for table "user"
#

INSERT INTO `user` VALUES (5,'laughing','fcea920f7412b5da7be0cf42b8c93759','1000086@qq.com',0,'重庆','1999-09-01','http://localhost:8080/newcaseyou/image/5b3bcdee-a3fe-4991-9b51-45739dbd6a6b.jpg'),(6,'www','ed2b1f468c5f915f3f1cf75d7068baae','1069700162@qq.com',0,'长寿','2003-03-03','http://localhost:8080/caseyou/image/f7d69ea5-bf12-43b4-8d13-f2bb69c9ecd9.jpg'),(7,'msh','781e5e245d69b566979b86e28d23f2c7','123@qq.com',1,'彭水','1996-01-11',NULL);

#
# Structure for table "article"
#

CREATE TABLE `article` (
  `article_id` varchar(32) NOT NULL DEFAULT '',
  `article_name` varchar(50) NOT NULL,
  `article_writer` varchar(20) DEFAULT NULL,
  `article_text` text,
  `article_from_flag` tinyint(1) DEFAULT '0' COMMENT '1-转载 0- 自创',
  `article_from` varchar(50) DEFAULT NULL,
  `article_date` datetime DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`article_id`),
  KEY `user_writer` (`article_writer`),
  CONSTRAINT `user_writer` FOREIGN KEY (`article_writer`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "article"
#

INSERT INTO `article` VALUES ('05d7f98396c541b89940c6efc45fc259','StringBuffer','laughing','<p>请输入内容=====你好</p>',0,'','2017-03-22 20:05:58'),('0f609cb939464476a05e56093588b814','Test-SSM','msh','<p>测试下改用Spring MVC 和Mybatis 框架后正常与否？？？<img src=\"/newcaseyou/image/ed9fb3a6-4274-49ae-9fd3-f7c02be867dc.jpg\" alt=\"oss_58fb5ac644ed2\" style=\"max-width: 100%; width: 342px; height: 212px;\" class=\"\"></p><p><br></p>',0,'','2017-05-10 20:45:46'),('17a182d963064daeac6880bd186bf7c8','StringMethod','www','<p>句看i刻录机iiiiiiiiiiiiiiiiiiiiiiiiiiii及迥异玉腿哦怕</p>',0,'','2017-03-22 20:00:35'),('355e680b174b4f918e9c57fed0042f6d','StringT','laughing','<p>测试事务</p><p>测试事务自己冷二楼房间哦我网易哈了既了解诶你付了款格列一个来得及港口等各环节外观来看路口回顾看偶尔跟hi哦啊看到囧了看得见覅偶就 来看待世界废物多少个那个的高哈尔提高多少分阿发发到付额打十分昂贵大话非道弘人案发后如何融入方哈儿黄日华再热雨后购房合同合同热哈grey爱如风染发膏然而寡人感觉哈尔yagberhaharfbfhr mysql中分页查询有两种方式, 一种是使用COUNT(*)的方式,具体代码如下\n\n1\n2\n3\nSELECT COUNT(*) FROM foo WHERE b = 1;\n \nSELECT a FROM foo WHERE b = 1 LIMIT 100,10;\n1\n  \n另外一种是使用SQL_CALC_FOUND_ROWS\n\n1\n2\nSELECT SQL_CALC_FOUND_ROWS a FROM foo WHERE b = 1 LIMIT 100, 10;\nSELECT FOUND_ROWS();\n \n\n第二种方式调用SQL_CALC_FOUND_ROWS之后会将WHERE语句查询的行数放在FOUND_ROWS()之中，第二次只需要查询FOUND_ROWS()就可以查出有多少行了。\n\n \n\n讨论这两种方法的优缺点：\n首先原子性讲，第二种肯定比第一种好。第二种能保证查询语句的原子性，第一种当两个请求之间有额外的操作修改了表的时候，结果就自然是不准确的了。而第二种则不会。但是非常可惜，一般页面需要进行分页显示的时候，往往并不要求分页的结果非常准确。即分页返回的total总数大1或者小1都是无所谓的。所以其实原子性不是我们分页关注的重点。\n\n \n\n下面看效率。这个非常重要，分页操作在每个网站上的使用都是非常大的，查询量自然也很大。由于无论哪种，分页操作必然会有两次sql查询，于是就有很多很多关于两种查询性能的比较：\n\nSQL_CALC_FOUND_ROWS真的很慢么？\n\nhttp://hi.baidu.com/thinkinginlamp/item/b122fdaea5ba23f614329b14\n\nTo SQL_CALC_FOUND_ROWS or not to SQL_CALC_FOUND_ROWS?\n\nhttp://www.mysqlperformanceblog.com/2007/08/28/to-sql_calc_found_rows-or-not-to-sql_calc_found_rows/\n\n老王这篇文章里面有提到一个covering index的概念，简单来说就是怎样才能只让查询根据索引返回结果，而不进行表查询\n\n具体看他的另外一篇文章：\n\nMySQL之Covering Index\n\nhttp://hi.baidu.com/thinkinginlamp/item/1b9aaf09014acce0f45ba6d3\n\n \n\n实验\n结合这几篇文章，做的实验：\n\n表：\n\n1\n2\n3\n4\n5\n6\n7\nCREATE TABLE IF NOT EXISTS `foo` (\n`a` int(10) unsigned NOT NULL AUTO_INCREMENT,\n`b` int(10) unsigned NOT NULL,\n`c` varchar(100) NOT NULL,\nPRIMARY KEY (`a`),\nKEY `bar` (`b`,`a`)\n) ENGINE=MyISAM;\n \n\n \n\n注意下这里是使用b,a做了一个索引，所以查询select * 的时候是不会用到covering index的，select a才会使用到covering index\n\n1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20\n21\n22\n23\n24\n25\n26\n27\n28\n29\n30\n31\n32\n33\n34\n35\n36\n37\n38\n39\n40\n41\n42\n43\n44\n45\n46\n47\n48\n49\n50\n<?php\n \n$host = \'192.168.100.166\';\n$dbName = \'test\';\n$user = \'root\';\n$password = \'\';\n \n$db = mysql_connect($host, $user, $password) or die(\'DB connect failed\');\nmysql_select_db($dbName, $db);\n \n \necho \'==========================================\' . \"\\r\\n\";\n \n$start = microtime(true);\nfor ($i =0; $i<1000; $i++) {\n    mysql_query(\"SELECT SQL_NO_CACHE COUNT(*) FROM foo WHERE b = 1\");\n    mysql_query(\"SELECT SQL_NO_CACHE a FROM foo WHERE b = 1 LIMIT 100,10\");\n}\n$end = microtime(true);\necho $end - $start . \"\\r\\n\";\n \necho \'==========================================\' . \"\\r\\n\";\n \n$start = microtime(true);\nfor ($i =0; $i<1000; $i++) {\n    mysql_query(\"SELECT SQL_NO_CACHE SQL_CALC_FOUND_ROWS a FROM foo WHERE b = 1 LIMIT 100, 10\");\n    mysql_query(\"SELECT FOUND_ROWS()\");\n}\n$end = microtime(true);\necho $end - $start . \"\\r\\n\";\n \necho \'==========================================\' . \"\\r\\n\";\n \n$start = microtime(true);\nfor ($i =0; $i<1000; $i++) {\n    mysql_query(\"SELECT SQL_NO_CACHE COUNT(*) FROM foo WHERE b = 1\");\n    mysql_query(\"SELECT SQL_NO_CACHE * FROM foo WHERE b = 1 LIMIT 100,10\");\n}\n$end = microtime(true);\necho $end - $start . \"\\r\\n\";\n \necho \'==========================================\' . \"\\r\\n\";\n \n$start = microtime(true);\nfor ($i =0; $i<1000; $i++) {\n    mysql_query(\"SELECT SQL_NO_CACHE SQL_CALC_FOUND_ROWS * FROM foo WHERE b = 1 LIMIT 100, 10\");\n    mysql_query(\"SELECT FOUND_ROWS()\");\n}\n$end = microtime(true);\necho $end - $start . \"\\r\\n\";\n返回的结果：\n\nclip_image001\n\n和老王里面文章说的是一样的。第四次查询SQL_CALC_FOUND_ROWS由于不仅是没有使用到covering index，也需要进行全表查询，而第三次查询COUNT(*)，且select * 有使用到index，并没进行全表查询，所以有这么大的差别。\n\n \n\n总结\nPS： 另外提醒下，这里是使用MyISAM会出现三和四的查询差别这么大，但是如果是使用InnoDB的话，就不会有这么大差别了。\n\n所以我得出的结论是如果数据库是InnoDB的话，我还是倾向于使用SQL_CALC_FOUND_ROWS\n\n \n\n结论：SQL_CALC_FOUND_ROWS和COUNT(*)的性能在都使用covering index的情况下前者高，在没使用covering index情况下后者性能高。所以使用的时候要注意这个。\n\nCreative Commons License\n专注Web开发50年。请加群：Thinking in Web\n轩脉刃的技术日记，请关注同名公众号：{轩脉刃的刀光剑影}，记录日常技术讨论和思考\n本文基于署名-非商业性使用 3.0许可协议发布，欢迎转载，演绎，但是必须保留本文的署名叶剑峰（包含链接http://www.cnblogs.com/yjf512/），且不得用于商业目的。如您有任何疑问或者授权方面的协商，请与我联系。</p>',0,'','2017-03-22 20:11:17'),('49adb2bad3b340b18b6c530f998e42ce','Mybatis','laughing','<p>Mybatis 原理实现</p><p><br></p><pre style=\"max-width:100%;overflow-x:auto;\"><code class=\"java hljs\" codemark=\"1\"><span class=\"hljs-keyword\">package</span> test;\r\n\r\n<span class=\"hljs-keyword\">import</span> java.io.InputStream;\r\n<span class=\"hljs-keyword\">import</span> java.util.Map;\r\n\r\n<span class=\"hljs-keyword\">import</span> com.duobang.io.Resource;\r\n<span class=\"hljs-keyword\">import</span> com.duobang.session.SqlSession;\r\n<span class=\"hljs-keyword\">import</span> com.duobang.session.SqlSessionFactory;\r\n<span class=\"hljs-keyword\">import</span> com.duobang.session.SqlSessionFactoryBuilder;\r\n\r\n<span class=\"hljs-keyword\">public</span> <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">Test</span> </span>{\r\n\r\n\t<span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">static</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">main</span><span class=\"hljs-params\">(String[] args)</span> </span>{\r\n\r\n\t\t<span class=\"hljs-keyword\">try</span> {\r\n\r\n\t\t\tString fileName = <span class=\"hljs-string\">\"daoMapper.xml\"</span>;\r\n\r\n\t\t\tInputStream stream = Resource.getResourceAsStream(fileName);\r\n\t\t\tSqlSessionFactory factory = <span class=\"hljs-keyword\">new</span> SqlSessionFactoryBuilder()\r\n\t\t\t\t\t.build(stream);\r\n\t\t\tSqlSession sqlSession = factory.openSqlSession();\r\n\t\t\t\r\n\t\t\t\r\n\t\t\tIDao dao = sqlSession.mapper(IDao.class);\r\n\t\t\t\r\n\t\t\t\r\n\t\t\tUserBean userBean = <span class=\"hljs-keyword\">new</span> UserBean(<span class=\"hljs-string\">\"1111\"</span>, <span class=\"hljs-string\">\"2\"</span>, <span class=\"hljs-string\">\"3\"</span>);\r\n\t\t\t\r\n\t\t\t<span class=\"hljs-comment\">// int v = dao.insertUser(userBean);</span>\r\n\t\t\t<span class=\"hljs-comment\">// System.out.println(v);</span>\r\n\t\t\tMap&lt;String, UserBean&gt; queryList = dao.queryMap();\r\n\t\t\t<span class=\"hljs-comment\">// List&lt;UserBean&gt; queryList = dao.queryList();</span>\r\n\t\t\t<span class=\"hljs-comment\">// UserBean queryList = dao.queryUserBean(userBean);</span>\r\n\t\t\tSystem.out.println(queryList);\r\n\r\n\t\t} <span class=\"hljs-keyword\">catch</span> (Exception e) {\r\n\t\t\te.printStackTrace();\r\n\t\t}\r\n\t}\r\n\r\n}\r\n</code></pre><p><br></p>',0,'','2017-05-08 15:44:15'),('5cd3a89c02cc4c24b062262d93b49f6d','java webT','laughing','<p>马双红，，，，</p>',0,'','2017-04-06 21:06:15'),('72e520e27461442eb76d6e21c88f8cb9','StringT','laughing','<p>你好你好看看就锻炼腹肌锻炼司法鉴定所了看到房价的肺部看到了发结果呢但是快乐到家给的空间佛欸快乐记得是发的就是给扩大解放立刻即可查看就考虑的经费赶快决定是否的康复街路口女男刀锋</p>',0,'','2017-03-22 20:20:38'),('8cb288c88eb5417795587da2e23072c5','test File','laughing','<p>&nbsp; &nbsp; 测试发布文章，上传是否正确????</p><p><br></p>',0,'','2017-04-09 13:54:58'),('8d1a01be617e4ed1aac250fa7774d900','是的发生T','www','<p>丰东股份幸福地方</p>',0,'','2017-03-29 17:51:36'),('98e6582b38ea4472a421f248f86fc951','test no file','laughing','<p>&nbsp; &nbsp; 发布文章，不上传文件，看附件表中是否有值？？？？</p><p><br></p>',0,'','2017-04-09 14:10:32'),('c0f76f14977f41f1828e4cb8c4d1cc35','附件测试T','laughing','<p>有脾气，厉害了。为了见佛阿道夫的徕卡健身房</p>',0,'','2017-03-22 22:08:43'),('de16d8dadf5c49959a62e52a75e89b08','Test 转载格式','www','<p>这是测试转载时，格式是怎么样的，显示效果如何？？？</p>',1,'www.baidu.com','2017-04-10 22:03:36'),('f30b89292a154fbeb5548d19ed2a28a9','是的发生TTT','www','<p>丰东股份幸福地方</p>',0,'','2017-03-29 17:52:36'),('f449b8ca753643d1b07a1f160e054623','TestPblish','msh','<p>测试一下发布文章，两篇的情况能不能正常查询？？？？</p>',0,'','2017-05-10 21:17:48');

#
# Structure for table "article_view"
#

CREATE TABLE `article_view` (
  `article_id` varchar(32) NOT NULL,
  `article_view_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`article_id`),
  KEY `article_view` (`article_id`),
  CONSTRAINT `article_view_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "article_view"
#

INSERT INTO `article_view` VALUES ('05d7f98396c541b89940c6efc45fc259',46),('17a182d963064daeac6880bd186bf7c8',11),('355e680b174b4f918e9c57fed0042f6d',78),('5cd3a89c02cc4c24b062262d93b49f6d',27),('72e520e27461442eb76d6e21c88f8cb9',130),('8cb288c88eb5417795587da2e23072c5',12),('8d1a01be617e4ed1aac250fa7774d900',43),('98e6582b38ea4472a421f248f86fc951',39),('c0f76f14977f41f1828e4cb8c4d1cc35',117),('de16d8dadf5c49959a62e52a75e89b08',11),('f30b89292a154fbeb5548d19ed2a28a9',30),('f449b8ca753643d1b07a1f160e054623',11);

#
# Structure for table "article_like"
#

CREATE TABLE `article_like` (
  `article_id` varchar(32) NOT NULL,
  `username` varchar(20) NOT NULL,
  PRIMARY KEY (`article_id`,`username`),
  KEY `article_like` (`username`),
  CONSTRAINT `article_like_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`),
  CONSTRAINT `article_like_ibfk_2` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "article_like"
#

INSERT INTO `article_like` VALUES ('05d7f98396c541b89940c6efc45fc259','laughing'),('0f609cb939464476a05e56093588b814','laughing'),('17a182d963064daeac6880bd186bf7c8','laughing'),('355e680b174b4f918e9c57fed0042f6d','laughing'),('49adb2bad3b340b18b6c530f998e42ce','laughing'),('5cd3a89c02cc4c24b062262d93b49f6d','laughing'),('72e520e27461442eb76d6e21c88f8cb9','laughing'),('72e520e27461442eb76d6e21c88f8cb9','www'),('8cb288c88eb5417795587da2e23072c5','laughing'),('98e6582b38ea4472a421f248f86fc951','laughing'),('c0f76f14977f41f1828e4cb8c4d1cc35','laughing'),('f449b8ca753643d1b07a1f160e054623','laughing');

#
# Structure for table "article_file"
#

CREATE TABLE `article_file` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` varchar(32) NOT NULL DEFAULT '0',
  `file_path` varchar(255) DEFAULT NULL,
  `file_size` int(11) DEFAULT NULL,
  `file_count` int(11) DEFAULT '0',
  PRIMARY KEY (`file_id`),
  KEY `id` (`article_id`),
  CONSTRAINT `id` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

#
# Data for table "article_file"
#

INSERT INTO `article_file` VALUES (3,'355e680b174b4f918e9c57fed0042f6d','E:\\download\\tomcat\\apache-tomcat-9.0.0.M11\\wtpwebapps\\newcaseyou\\WEB-INF\\upload/2017/03\\2017-03-22caseyou.war',3,6),(4,'72e520e27461442eb76d6e21c88f8cb9','E:\\download\\tomcat\\apache-tomcat-9.0.0.M11\\wtpwebapps\\newcaseyou\\WEB-INF\\upload/2017/03\\2017-03-22com.duobang.orm.jar',1,2),(5,'c0f76f14977f41f1828e4cb8c4d1cc35','E:\\download\\tomcat\\apache-tomcat-9.0.0.M11\\wtpwebapps\\newcaseyou\\WEB-INF\\upload/2017/03\\2017-03-22com.duobang.orm.jar',1,1),(16,'8cb288c88eb5417795587da2e23072c5','E:\\download\\tomcat\\apache-tomcat-9.0.0.M11\\wtpwebapps\\newcaseyou\\WEB-INF\\upload\\2017/04\\2017-04-09应聘java_web实习生_汪義航_西安邮电大学_15667023869.pdf',1,1),(17,'49adb2bad3b340b18b6c530f998e42ce','E:\\download\\tomcat\\apache-tomcat-9.0.0.M11\\wtpwebapps\\newcaseyou\\WEB-INF\\upload\\2017/05\\2017-05-08caseyou项目案列.docx',1,1),(18,'0f609cb939464476a05e56093588b814','E:\\download\\tomcat\\apache-tomcat-9.0.0.M11\\wtpwebapps\\newcaseyou\\WEB-INF\\upload\\2017/05\\2017-05-10kms8.log',1,0);

CREATE VIEW `articleinfo` AS 
  select `caseyou`.`article`.`article_id` AS `article_id`,`caseyou`.`article`.`article_name` AS `article_name`,`caseyou`.`article`.`article_writer` AS `article_writer`,`caseyou`.`article`.`article_text` AS `article_text`,`caseyou`.`article`.`article_from_flag` AS `article_from_flag`,`caseyou`.`article`.`article_from` AS `article_from`,`caseyou`.`article`.`article_date` AS `article_date`,`caseyou`.`article_file`.`file_path` AS `file_path`,`caseyou`.`article_file`.`file_size` AS `file_size`,`caseyou`.`article_file`.`file_count` AS `file_count`,`caseyou`.`article_view`.`article_view_count` AS `article_view_count`,count(`caseyou`.`article_like`.`article_id`) AS `article_like_count` from (((`caseyou`.`article` left join `caseyou`.`article_file` on((`caseyou`.`article`.`article_id` = `caseyou`.`article_file`.`article_id`))) left join `caseyou`.`article_view` on((`caseyou`.`article`.`article_id` = `caseyou`.`article_view`.`article_id`))) left join `caseyou`.`article_like` on((`caseyou`.`article`.`article_id` = `caseyou`.`article_like`.`article_id`))) group by `caseyou`.`article`.`article_id`;
