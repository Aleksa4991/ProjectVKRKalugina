-- Удаление схемы, если она существует
DROP SCHEMA IF EXISTS usercervice CASCADE;

-- Создание схемы с кодировкой UTF-8
CREATE SCHEMA IF NOT EXISTS usercervice;

-- Установка кодировки для схемы
SET search_path TO usercervice;

-- Удаление таблицы, если она существует
DROP TABLE IF EXISTS userservice.last_visit CASCADE;
CREATE TABLE userservice.last_visit (
                                        id BYTEA NOT NULL,
                                        in INT DEFAULT NULL,
                                        out INT DEFAULT NULL,
                                        PRIMARY KEY (id)
);

-- Удаление таблицы, если она существует
DROP TABLE IF EXISTS userservice.roles CASCADE;
CREATE TABLE userservice.roles (
                                   id BYTEA NOT NULL,
                                   name VARCHAR(45) DEFAULT NULL,
                                   description VARCHAR(45) DEFAULT NULL,
                                   PRIMARY KEY (id)
);

-- Удаление таблицы, если она существует
DROP TABLE IF EXISTS userservice.users CASCADE;
CREATE TABLE userservice.users (
                                   id BYTEA NOT NULL,
                                   name VARCHAR(45) NOT NULL,
                                   email VARCHAR(45) NOT NULL,
                                   password VARCHAR(45) NOT NULL,
                                   created INT NOT NULL,
                                   last_visit_id BYTEA DEFAULT NULL,
                                   PRIMARY KEY (id),
                                   UNIQUE (email),
                                   CONSTRAINT fk_users_last_visit FOREIGN KEY (last_visit_id) REFERENC






                                       ES usercervice.last_visit (id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Удаление таблицы, если она существует
DROP TABLE IF EXISTS userservice.users_has_roles CASCADE;
CREATE TABLE userservice.users_has_roles (
                                             users_id BYTEA NOT NULL,
                                             roles_id BYTEA NOT NULL,
                                             PRIMARY KEY (users_id, roles_id),
                                             CONSTRAINT fk_users_has_roles_users FOREIGN KEY (users_id) REFERENCES usercervice.users (id) ON DELETE CASCADE ON UPDATE CASCADE,
                                             CONSTRAINT fk_users_has_roles_roles FOREIGN KEY (roles_id) REFERENCES usercervice.roles (id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Вставка данных в таблицу last_visit
INSERT INTO userservice.last_visit (id, in, out) VALUES
                                                     ('\x306DCF05D3D64B438E066B6FFE2331FC', 1604249194, 1604249224),
                                                     ('\x3C37571B0F494FED875845BFAE428B29', 1604249181, 1604249209),
                                                     ('\x7AC613D5538745E58C9F9F49A0F271E1', 1604249171, 1604249203),
                                                     ('\x8A0BE660EBC64C47AF813F7123977993', 1604249188, 1604249217),
                                                     ('\xBE76648F49F24FBE9BC434BD8C23C70E', 1604130098, 1604130106);

-- Вставка данных в таблицу roles
INSERT INTO userservice.roles (id, name, description) VALUES
                                                          ('\x43DCF1D63AC0429099FD44F9B9F9BECF', 'ADMIN', 'Administrative roles for the system'),
                                                          ('\xB479B3577E2547FA8DBABFDAEECC6C2C', 'SUBSCRIBER', 'Ad content consumer'),
                                                          ('\xEB932DBB7005422FA6497190AF39E984', 'PRODUCER', 'Ad content producer');

-- Вставка данных в таблицу users
INSERT INTO userservice.users (id, name, email, password, created, last_visit_id) VALUES
                                                                                      ('\x0DD03A597DBC4D0081073271B3345434', 'Aleksandra Aleksandrova', 'aleksa4991@mail.ru', 'password', 1504249224, '\x8A0BE660EBC64C47AF813F7123977993'),
                                                                                      ('\x1CD89E11602A4186AFBFE0149B59EB08', 'Elena Drugova', 'elena@mail.ru', 'password', 1504249224, '\xBE76648F49F24FBE9BC434BD8C23C70E'),
                                                                                      ('\x6E27EA06A7164C89AF88813749A8BD48', 'Ylia Drokina', 'drokina@mail.ru', 'password', 1604129987, '\xBE76648F49F24FBE9BC434BD8C23C70E'),
                                                                                      ('\x70A64B5443C34C18BBEC64590FF7E0CC', 'Gennadiy Sharov', 'sharov@mail.ru', 'password', 1504249224, '\x3C37571B0F494FED875845BFAE428B29'),
                                                                                      ('\xABB04B9F5D1040DD9076EB27CA76891A', 'Vladimir Petrov', 'petrov@mail.ru', 'password', 1504249224, '\x7AC613D5538745E58C9F9F49A0F271E1');

-- Вставка данных в таблицу users_has_roles
INSERT INTO userservice.users_has_roles (users_id, roles_id) VALUES
                                                                 ('\x0DD03A597DBC4D0081073271B3345434', '\xB479B3577E2547FA8DBABFDAEECC6C2C'),
                                                                 ('\x1CD89E11602A4186AFBFE0149B59EB08', '\xB479B3577E2547FA8DBABFDAEECC6C2C'),
                                                                 ('\x1CD89E11602A4186AFBFE0149B59EB08', '\xEB932DBB7005422FA6497190AF39E984'),
                                                                 ('\x6E27EA06A7164C89AF88813749A8BD48', '\xB479B3577E2547FA8DBABFDAEECC6C2C'),
                                                                 ('\x6E27EA06A7164C89AF88813749A8BD48', '\xEB932DBB7005422FA6497190AF39E984'),
                                                                 ('\x70A64B5443C34C18BBEC64590FF7E0CC', '\xB479B3577E2547FA8DBABFDAEECC6C2C'),
                                                                 ('\xABB04B9F5D1040DD9076EB27CA76891A', '\xB479B3577E2547FA8DBABFDAEECC6C2C');