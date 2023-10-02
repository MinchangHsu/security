CREATE SCHEMA IF NOT EXISTS local_test;
USE
local_test;

DROP TABLE if exists user_action_log;
DROP TABLE if exists user_roles;
DROP TABLE if exists user;
DROP TABLE if exists role_permission;
DROP TABLE if exists permission_api;
DROP TABLE if exists api_url;
DROP TABLE if exists permission;
DROP TABLE if exists function_info;
DROP TABLE if exists roles;
DROP TABLE if exists system_config;
DROP TABLE if exists shedlock;


create table user
(
    ID                     int auto_increment comment '識別ID, 自動遞增' primary key,
    NAME                   varchar(50)          not null comment '姓名或暱稱',
    DESCRIPTION            varchar(100) null comment '使用者描述',
    LOGIN_ID               varchar(20) null comment '登入帳號',
    PWD                    varchar(200)         not null comment '登入密碼',
    EMAIL                  varchar(100) null comment '通訊email',
    BIRTHDAY               datetime null comment '出生日期(西元)',
    GENDER                 char     default '0' not null comment '性別: 0:不明, 1:男性, 2:女性',
    USER_TYPE              char     default '1' not null comment '類型: 1:系統管理者, 2:高級管理者, 3:普通管理者, 4.客服人員, 5:會員',
    STATUS                 char     default '1' not null comment '帳號的狀態, 0:停用, 1:啟用',
    GOOGLE_AUTH_SECRET_KEY varchar(50) null comment 'google authenticator secret',
    ICON_PATH              varchar(50) null comment '使用者個人icon',
    USER_GROUP             char null comment '帳號所屬的權限群組',
    LOGIN_IP_ADDRESS       varchar(30) null comment '紀錄最後登入的ip',
    LAST_ACCESS_DATE       datetime null comment '最後操作系統時間',
    REFRESH_TOKEN          varchar(1000) null comment 'refreshToken',
    CREATE_DATE            datetime default CURRENT_TIMESTAMP null comment '建立日期',
    UPDATE_DATE            datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '最後修改日期',
    constraint uk_user_login_id
        unique index (LOGIN_ID)
) comment '系統使用者資訊';

create table api_url
(
    ID              int auto_increment comment 'id'
        primary key,
    API_URL         varchar(500)     not null comment 'RestUrl',
    API_HTTP_METHOD varchar(100)     null comment 'GET,HEAD,POST,PUT,PATCH,DELETE,OPTIONS,TRACE(可多個，以,分隔null代表無限制)',
    API_DESCRIPTION varchar(300)     null comment 'Api功能描述',
    STATUS          char default '1' not null comment '狀態; 0:停用, 1:啟用',
    constraint uk_api_url_API_URL
        unique (API_URL, API_HTTP_METHOD)
)
    comment '系統RestApi';

create table function_info
(
    ID            int auto_increment primary key,
    FUNCTION_NAME varchar(150) null comment '功能名稱',
    constraint uk_function_name
        unique (FUNCTION_NAME)
) comment '功能表';

create table permission
(
    ID                     int auto_increment primary key,
    PERMISSION             varchar(200) not null comment '權限名稱',
    PERMISSION_DESCRIPTION varchar(300) null comment '描述',
    FUNCTION_ID            int null comment '群組',
    constraint permission_function_ID_fk
        foreign key (FUNCTION_ID) references function_info (ID)
            on update cascade on delete cascade,
    constraint uk_permission_PERMISSION
        unique (PERMISSION)
) comment '權限表';

create table permission_api
(
    PERMISSION_ID int not null,
    API_ID        int not null,
    primary key (PERMISSION_ID, API_ID),
    constraint permission_api_api_url_ID_fk
        foreign key (API_ID) references api_url (ID)
            on update cascade on delete cascade,
    constraint permission_api_permission_ID_fk
        foreign key (PERMISSION_ID) references permission (ID)
            on update cascade on delete cascade
) comment '權限Api對應表';


create table roles
(
    ID               int auto_increment primary key,
    NAME             varchar(50)      not null comment '權限群組',
    ROLE_DESCRIPTION varchar(500) null comment '描述',
    USER_TYPE        char default '1' not null,
    constraint uk_roles_name
        unique (NAME)
) comment '使用者角色';



create table user_roles
(
    USER_ID int not null,
    ROLE_ID int not null,
    primary key (USER_ID, ROLE_ID),
    constraint fk_user_roles_role_id
        foreign key (ROLE_ID) references roles (ID)
            on update cascade on delete cascade,
    constraint fk_user_roles_user_id
        foreign key (USER_ID) references user (ID)
            on update cascade on delete cascade
);


create table role_permission
(
    ROLE_ID       int not null,
    PERMISSION_ID int not null,
    primary key (ROLE_ID, PERMISSION_ID),
    constraint role_permission_permission_ID_fk
        foreign key (PERMISSION_ID) references permission (ID)
            on update cascade on delete cascade,
    constraint role_permission_roles_ID_fk
        foreign key (ROLE_ID) references roles (ID)
            on update cascade on delete cascade
) comment '角色權限對應表';

-- 使用者登入操作日誌
create table IF NOT EXISTS local_test.user_action_log
(
    ID          bigint auto_increment comment '識別ID, 自動遞增'
        primary key,
    API_URL_ID  int                                not null comment 'api url id',
    API_URL     varchar(30)                        not null comment 'Api url',
    USER_ID     int                                not null comment '使用者id',
    PARAMS      varchar(5000)                      null comment '請求參數',
    OLD_INFO    varchar(5000)                      null comment '異動前資訊',
    NEW_INFO    varchar(5000)                      null comment '異動後資訊',
    LOGIN_IP    varchar(100)                       not null comment '使用者登入ip',
    CREATE_DATE datetime default CURRENT_TIMESTAMP null comment '操作建立日期',
    REMARK      varchar(500)                       null comment '備註',
    constraint user_action_log_api_url_id_fk
        foreign key (API_URL_ID) references local_test.api_url (ID)
);




create table system_config
(
    ID          int auto_increment primary key,
    TYPE        varchar(50)   not null,
    KEY_NAME    varchar(50) charset utf8mb3 not null,
    VALUE       varchar(2000) not null,
    DESCRIPTION varchar(200) null comment '關於此設定的描述',
    SORT_VALUE  int      default 1 null comment '排序值',
    CREATE_DATE datetime default CURRENT_TIMESTAMP null comment '建立日期',
    UPDATE_DATE datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '最後修改日期',
    constraint system_config_unique
        unique (TYPE, KEY_NAME)
) comment '系統設定表';


DROP TABLE IF EXISTS batch_process_timeout_log;
DROP TABLE IF EXISTS batch_process_center;

CREATE TABLE IF NOT EXISTS batch_process_timeout_log
(
    ID
    INT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    IDENTIFIER_CODE
    VARCHAR(50) NOT NULL comment '執行識別碼',
    ACTION CHAR(2) NOT NULL COMMENT '執行動作',
    EXECUTE_DATE DATETIME NULL COMMENT '最近一次執行時間',
    EXECUTE_COUNT INT DEFAULT 0 NULL COMMENT '累積執行次數',
    MAX_EXECUTE_COUNT INT DEFAULT 10 NULL COMMENT '最大執行次數'
    ) COMMENT '存放執行程式日誌';

CREATE TABLE IF NOT EXISTS batch_process_center
(
    ID
    INT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    IDENTIFIER_CODE
    VARCHAR(50) NOT NULL comment '執行識別碼',
    DATA VARCHAR(1000) default '' NOT NULL comment '執行識別碼',
    ACTION CHAR(2) NOT NULL COMMENT '執行動作',
    NEXT_EXECUTE_DATE DATETIME NULL COMMENT '下一次執行的時間',
    EXECUTE_DATE DATETIME NULL COMMENT '最近一次執行時間',
    EXECUTE_COUNT INT DEFAULT 0 NULL COMMENT '累積執行次數',
    MAX_EXECUTE_COUNT INT DEFAULT 10 NULL COMMENT '最大執行次數',
    CONSTRAINT batch_process_center_IDENTIFIER_CODE_uindex
    UNIQUE(IDENTIFIER_CODE)
) COMMENT '存放需要執行程式資訊';

create table if not exists shedlock
(
    NAME VARCHAR(64),
    LOCK_UNTIL TIMESTAMP(3) null,
    LOCKED_AT TIMESTAMP(3) null,
    LOCKED_BY VARCHAR(255),
    primary key(NAME)
);



drop table if exists user_type_permission_map;

create table user_type_permission_map
(
    USER_TYPE     char not null,
    PERMISSION_ID int  not null,
    primary key (USER_TYPE, PERMISSION_ID),
    constraint user_map_t_permission_ID_fk
        foreign key (PERMISSION_ID) references permission (ID)
            on update cascade on delete cascade
) comment '使用者類型權限對應表';

DROP TABLE if exists numTable;
CREATE TABLE numTable
(
    id int(11) NOT NULL auto_increment,
    PRIMARY KEY (id)
);

-- 增加一支可偵測table欄位是否存在的store procedure.
DROP PROCEDURE IF EXISTS `DropColumnIfExists`;
DROP PROCEDURE IF EXISTS `DropConstraintIfExists`;
DROP PROCEDURE IF EXISTS `DropConstraintFKIfExists`;
DROP PROCEDURE IF EXISTS `doWhile`;

DELIMITER
$$

CREATE PROCEDURE `DropColumnIfExists`(`@TABLE` VARCHAR (100), `@COLUMN` VARCHAR (100)) `DropColumnIfExists`:
BEGIN
    DECLARE
`@EXISTS` INT UNSIGNED DEFAULT 0;

SELECT COUNT(*)
INTO `@EXISTS`
FROM `information_schema`.`columns`
WHERE (
                  `TABLE_SCHEMA` = DATABASE()
              AND `TABLE_NAME` = `@TABLE`
              AND `COLUMN_NAME` = `@COLUMN`
          );

IF
(`@EXISTS` > 0) THEN
        SET @SQL = CONCAT('ALTER TABLE `', `@TABLE`, '` DROP COLUMN `', `@COLUMN`, '`;');

PREPARE query FROM @SQL;
EXECUTE query;
END IF;
END $$

-- 增加一支可偵測TABLE Constraint是否存在的store procedure.  (PRIMARY KEY, FOREIGN KEY,UNIQUE)
CREATE PROCEDURE `DropConstraintIfExists`(`@TABLE` VARCHAR (100), `@CONSTRAINT_NAME` VARCHAR (100),
                                          `@CONSTRAINT_TYPE` VARCHAR (100)) `DropConstraintIfExists`:
BEGIN
    DECLARE
`@EXISTS` INT UNSIGNED DEFAULT 0;

SELECT COUNT(*)
INTO `@EXISTS`
FROM `information_schema`.`TABLE_CONSTRAINTS`
WHERE (
                  CONSTRAINT_SCHEMA = DATABASE()
              AND `CONSTRAINT_NAME` = `@CONSTRAINT_NAME`
              AND `CONSTRAINT_TYPE` = `@CONSTRAINT_TYPE`
          );

IF
(`@EXISTS` > 0) THEN
        SET @SQL = CONCAT('ALTER TABLE `', `@TABLE`, '` DROP INDEX `', `@CONSTRAINT_NAME`, '`;');
PREPARE query FROM @SQL;
EXECUTE query;
END IF;
END $$

-- 增加一支可偵測TABLE Constraint是否存在的store procedure.  (FOREIGN KEY)
CREATE PROCEDURE `DropConstraintFKIfExists`(`@TABLE` VARCHAR (100), `@CONSTRAINT_NAME` VARCHAR (100),
                                            `@CONSTRAINT_TYPE` VARCHAR (100)) `DropConstraintIfExists`:
BEGIN
    DECLARE
`@EXISTS` INT UNSIGNED DEFAULT 0;

SELECT COUNT(*)
INTO `@EXISTS`
FROM `information_schema`.`TABLE_CONSTRAINTS`
WHERE (
                  CONSTRAINT_SCHEMA = DATABASE()
              AND `CONSTRAINT_NAME` = `@CONSTRAINT_NAME`
              AND `CONSTRAINT_TYPE` = `@CONSTRAINT_TYPE`
          );

IF
(`@EXISTS` > 0) THEN
        SET @SQL = CONCAT('ALTER TABLE `', `@TABLE`, '` DROP FOREIGN KEY `', `@CONSTRAINT_NAME`, '`;');
PREPARE query FROM @SQL;
EXECUTE query;
END IF;
END $$

-- 增加一支可產生1-n 的數字資料(table name: numTable)
CREATE PROCEDURE `doWhile`(tableLimit int)
BEGIN
    DECLARE
pointer INT DEFAULT tableLimit;
    WHILE
pointer > 0
        DO
            INSERT numTable VALUES (NULL);
            SET
pointer = pointer - 1;
END WHILE;
END $$
DELIMITER ;