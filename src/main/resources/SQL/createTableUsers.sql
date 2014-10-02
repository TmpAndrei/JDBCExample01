drop table if exists `Users`;

CREATE TABLE `Users` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `login` varchar(128) NOT NULL UNIQUE,
  `email` varchar(128) NOT NULL UNIQUE,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into Users (login, email) values ('Mike', 'mike@gmail.com');
insert into Users (login, email) values ('Admin', 'admin@site.com');
insert into Users (login, email) values ('Bill', 'bill@yahoo.com');