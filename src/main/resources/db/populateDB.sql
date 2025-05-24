DELETE FROM user_role;
DELETE FROM real_estate;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

INSERT INTO real_estate (address, square, user_id)
VALUES ('Address1', 10, 100000),
       ('Address2', 20, 100000),
       ('Address3', 30, 100000),
       ('Address4', 40, 100000),
       ('Address5', 50, 100000),
       ('Address6', 60, 100000),
       ('Address7', 70, 100000),
       ('Address10', 110, 100001),
       ('Address11', 111, 100001);
