CREATE TABLE User
(id int unsigned NOT NULL AUTO_INCREMENT
,username varchar(1024) NOT NULL
,passwordHash varchar(1024) NOT NULL
,email varchar(1024) NOT NULL
,role varchar(1024) NOT NULL
,PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Game
(id int unsigned NOT NULL AUTO_INCREMENT
,title varchar(255) NOT NULL
,PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Image
(id int unsigned NOT NULL AUTO_INCREMENT
,gameId int unsigned NOT NULL
,userId int unsigned NOT NULL
,name varchar(1024) DEFAULT NULL
,uploadDate varchar(1024) NOT NULL
,url varchar(1024) NOT NULL
,PRIMARY KEY (id)
,KEY fk_gameId (gameId)
,CONSTRAINT fk_gameId FOREIGN KEY (gameId) REFERENCES game (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;