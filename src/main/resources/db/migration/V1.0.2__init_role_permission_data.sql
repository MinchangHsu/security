use local_test;

DELETE FROM function_info WHERE FUNCTION_NAME = '使用者管理';
DELETE FROM permission WHERE PERMISSION in ('USER_ADD', 'USER_UPDATE', 'USER_QUERY', 'USER_DELETE');
DELETE FROM permission WHERE PERMISSION in ('AUTH_ADD', 'AUTH_UPDATE', 'AUTH_QUERY', 'AUTH_DELETE');
DELETE FROM api_url WHERE API_URL in ('/user', '/user/{userId}', '/user/info', '/user/list', '/user/{userId}');
DELETE FROM api_url WHERE API_URL in ('/auth/role', '/auth/role/{roleId}', '/auth/role/{roleId}/detail', '/auth/role/list');
DELETE FROM roles WHERE NAME = 'ROLE_SYSTEM_ADMIN';
DELETE FROM user WHERE NAME in ('caster');


-- 增加功能
INSERT INTO function_info (FUNCTION_NAME) VALUES ('使用者管理');

INSERT INTO permission (PERMISSION, PERMISSION_DESCRIPTION, FUNCTION_ID) VALUES ('USER_ADD', '新增帳號', (SELECT ID FROM function_info WHERE FUNCTION_NAME = '使用者管理'));
INSERT INTO permission (PERMISSION, PERMISSION_DESCRIPTION, FUNCTION_ID) VALUES ('USER_UPDATE', '修改帳號', (SELECT ID FROM function_info WHERE FUNCTION_NAME = '使用者管理'));
INSERT INTO permission (PERMISSION, PERMISSION_DESCRIPTION, FUNCTION_ID) VALUES ('USER_QUERY', '查詢帳號', (SELECT ID FROM function_info WHERE FUNCTION_NAME = '使用者管理'));
INSERT INTO permission (PERMISSION, PERMISSION_DESCRIPTION, FUNCTION_ID) VALUES ('USER_DELETE', '刪除帳號', (SELECT ID FROM function_info WHERE FUNCTION_NAME = '使用者管理'));
INSERT INTO permission (PERMISSION, PERMISSION_DESCRIPTION, FUNCTION_ID) VALUES ('AUTH_ADD', '新增權限', (SELECT ID FROM function_info WHERE FUNCTION_NAME = '使用者管理'));
INSERT INTO permission (PERMISSION, PERMISSION_DESCRIPTION, FUNCTION_ID) VALUES ('AUTH_UPDATE', '修改權限', (SELECT ID FROM function_info WHERE FUNCTION_NAME = '使用者管理'));
INSERT INTO permission (PERMISSION, PERMISSION_DESCRIPTION, FUNCTION_ID) VALUES ('AUTH_QUERY', '查詢權限', (SELECT ID FROM function_info WHERE FUNCTION_NAME = '使用者管理'));
INSERT INTO permission (PERMISSION, PERMISSION_DESCRIPTION, FUNCTION_ID) VALUES ('AUTH_DELETE', '刪除權限', (SELECT ID FROM function_info WHERE FUNCTION_NAME = '使用者管理'));

-- 使用者 api url
INSERT INTO api_url (API_URL, API_HTTP_METHOD, API_DESCRIPTION, STATUS) VALUES ('/user', 'POST', '新增帳號', '1');
INSERT INTO api_url (API_URL, API_HTTP_METHOD, API_DESCRIPTION, STATUS) VALUES ('/user/{userId}', 'PUT', '更新帳號', '1');
INSERT INTO api_url (API_URL, API_HTTP_METHOD, API_DESCRIPTION, STATUS) VALUES ('/user/list', 'GET', '取得所有帳號列表', '1');
INSERT INTO api_url (API_URL, API_HTTP_METHOD, API_DESCRIPTION, STATUS) VALUES ('/user/{userId}', 'GET', '取得單一帳號細節', '1');
INSERT INTO api_url (API_URL, API_HTTP_METHOD, API_DESCRIPTION, STATUS) VALUES ('/user/info', 'GET', '取得當前帳號細節', '1');
INSERT INTO api_url (API_URL, API_HTTP_METHOD, API_DESCRIPTION, STATUS) VALUES ('/user/{userId}', 'DELETE', '刪除帳號', '1');
INSERT INTO api_url (API_URL, API_HTTP_METHOD, API_DESCRIPTION, STATUS) VALUES ('/auth/role', 'POST', '新增角色', '1');
INSERT INTO api_url (API_URL, API_HTTP_METHOD, API_DESCRIPTION, STATUS) VALUES ('/auth/role/{roleId}', 'PUT', '更新角色', '1');
INSERT INTO api_url (API_URL, API_HTTP_METHOD, API_DESCRIPTION, STATUS) VALUES ('/auth/role/{roleId}/detail', 'GET', '單一角色細節', '1');
INSERT INTO api_url (API_URL, API_HTTP_METHOD, API_DESCRIPTION, STATUS) VALUES ('/auth/role/list', 'GET', '取得所有角色清單及角色對應的權限', '1');
INSERT INTO api_url (API_URL, API_HTTP_METHOD, API_DESCRIPTION, STATUS) VALUES ('/auth/role/{roleId}', 'DELETE', '刪除角色', '1');

-- 外部url須對應到的內部功能權限
INSERT INTO permission_api (PERMISSION_ID, API_ID)
VALUES ((SELECT ID FROM permission WHERE PERMISSION = 'USER_ADD'), (SELECT ID FROM api_url WHERE API_URL = '/user'));
INSERT INTO permission_api (PERMISSION_ID, API_ID)
VALUES ((SELECT ID FROM permission WHERE PERMISSION = 'USER_UPDATE'),
        (SELECT ID FROM api_url WHERE API_URL = '/user/{userId}' and API_HTTP_METHOD = 'PUT'));
INSERT INTO permission_api (PERMISSION_ID, API_ID)
VALUES ((SELECT ID FROM permission WHERE PERMISSION = 'USER_QUERY'),
        (SELECT ID FROM api_url WHERE API_URL = '/user/list'));
INSERT INTO permission_api (PERMISSION_ID, API_ID)
VALUES ((SELECT ID FROM permission WHERE PERMISSION = 'USER_QUERY'),
        (SELECT ID FROM api_url WHERE API_URL = '/user/{userId}' and API_HTTP_METHOD = 'GET'));
INSERT INTO permission_api (PERMISSION_ID, API_ID)
VALUES ((SELECT ID FROM permission WHERE PERMISSION = 'USER_QUERY'),
        (SELECT ID FROM api_url WHERE API_URL = '/user/info' and API_HTTP_METHOD = 'GET'));
INSERT INTO permission_api (PERMISSION_ID, API_ID)
VALUES ((SELECT ID FROM permission WHERE PERMISSION = 'USER_DELETE'),
        (SELECT ID FROM api_url WHERE API_URL = '/user/{userId}' and API_HTTP_METHOD = 'DELETE'));
INSERT INTO permission_api (PERMISSION_ID, API_ID)
VALUES ((SELECT ID FROM permission WHERE PERMISSION = 'AUTH_ADD'),
        (SELECT ID FROM api_url WHERE API_URL = '/auth/role'));
INSERT INTO permission_api (PERMISSION_ID, API_ID)
VALUES ((SELECT ID FROM permission WHERE PERMISSION = 'AUTH_UPDATE'),
        (SELECT ID FROM api_url WHERE API_URL = '/auth/role/{roleId}' and API_HTTP_METHOD = 'PUT'));
INSERT INTO permission_api (PERMISSION_ID, API_ID)
VALUES ((SELECT ID FROM permission WHERE PERMISSION = 'AUTH_QUERY'),
        (SELECT ID FROM api_url WHERE API_URL = '/auth/role/{roleId}/detail'));
INSERT INTO permission_api (PERMISSION_ID, API_ID)
VALUES ((SELECT ID FROM permission WHERE PERMISSION = 'AUTH_QUERY'),
        (SELECT ID FROM api_url WHERE API_URL = '/auth/role/list'));
INSERT INTO permission_api (PERMISSION_ID, API_ID)
VALUES ((SELECT ID FROM permission WHERE PERMISSION = 'AUTH_DELETE'),
        (SELECT ID FROM api_url WHERE API_URL = '/auth/role/{roleId}' and API_HTTP_METHOD = 'DELETE'));

-- 系統定義的三個角色
INSERT INTO roles (NAME, ROLE_DESCRIPTION, USER_TYPE) VALUES ('ROLE_SYSTEM_ADMIN', '系統管理角色', '1');

-- 初始系統管理者、銀商管理者權限
INSERT INTO role_permission (ROLE_ID, PERMISSION_ID) VALUES ((SELECT ID FROM roles WHERE NAME = 'ROLE_SYSTEM_ADMIN'),
                                                             (SELECT ID FROM permission WHERE PERMISSION = 'USER_ADD'));
INSERT INTO role_permission (ROLE_ID, PERMISSION_ID) VALUES ((SELECT ID FROM roles WHERE NAME = 'ROLE_SYSTEM_ADMIN'),
                                                             (SELECT ID FROM permission WHERE PERMISSION = 'USER_UPDATE'));
INSERT INTO role_permission (ROLE_ID, PERMISSION_ID) VALUES ((SELECT ID FROM roles WHERE NAME = 'ROLE_SYSTEM_ADMIN'),
                                                             (SELECT ID FROM permission WHERE PERMISSION = 'USER_QUERY'));
INSERT INTO role_permission (ROLE_ID, PERMISSION_ID) VALUES ((SELECT ID FROM roles WHERE NAME = 'ROLE_SYSTEM_ADMIN'),
                                                             (SELECT ID FROM permission WHERE PERMISSION = 'USER_DELETE'));
INSERT INTO role_permission (ROLE_ID, PERMISSION_ID) VALUES ((SELECT ID FROM roles WHERE NAME = 'ROLE_SYSTEM_ADMIN'),
                                                             (SELECT ID FROM permission WHERE PERMISSION = 'AUTH_ADD'));
INSERT INTO role_permission (ROLE_ID, PERMISSION_ID) VALUES ((SELECT ID FROM roles WHERE NAME = 'ROLE_SYSTEM_ADMIN'),
                                                             (SELECT ID FROM permission WHERE PERMISSION = 'AUTH_UPDATE'));
INSERT INTO role_permission (ROLE_ID, PERMISSION_ID) VALUES ((SELECT ID FROM roles WHERE NAME = 'ROLE_SYSTEM_ADMIN'),
                                                             (SELECT ID FROM permission WHERE PERMISSION = 'AUTH_QUERY'));
INSERT INTO role_permission (ROLE_ID, PERMISSION_ID) VALUES ((SELECT ID FROM roles WHERE NAME = 'ROLE_SYSTEM_ADMIN'),
                                                             (SELECT ID FROM permission WHERE PERMISSION = 'AUTH_DELETE'));

-- 初始化使用者
INSERT INTO user (NAME, LOGIN_ID, PWD) VALUES ('admin', 'admin', '$2a$10$iRagKWBrl1xdKerRAUePCuGiSRK3U/2lmHdbylUFn24iaxC.50KZW');

-- 初始化使用者與role的關係
INSERT INTO user_roles (USER_ID, ROLE_ID) VALUES ((SELECT ID FROM user WHERE NAME = 'admin'), (SELECT ID FROM roles WHERE NAME = 'ROLE_SYSTEM_ADMIN'));
