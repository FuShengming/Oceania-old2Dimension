DROP SCHEMA IF EXISTS oceania;
CREATE SCHEMA oceania;
USE oceania;
CREATE TABLE user
(
    id   INT AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL ,
    pwd  VARCHAR(16) NOT NULL ,
    CONSTRAINT user_pk
        PRIMARY KEY (id)
);