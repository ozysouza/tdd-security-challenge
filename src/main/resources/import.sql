INSERT INTO tb_city(name) VALUES ('Toronto');
INSERT INTO tb_city(name) VALUES ('Vancouver');
INSERT INTO tb_city(name) VALUES ('Montreal');
INSERT INTO tb_city(name) VALUES ('Calgary');
INSERT INTO tb_city(name) VALUES ('Ottawa');
INSERT INTO tb_city(name) VALUES ('Edmonton');
INSERT INTO tb_city(name) VALUES ('Winnipeg');
INSERT INTO tb_city(name) VALUES ('Halifax');
INSERT INTO tb_city(name) VALUES ('Quebec City');
INSERT INTO tb_city(name) VALUES ('Victoria');
INSERT INTO tb_city(name) VALUES ('Hamilton');

INSERT INTO tb_event(name, date, url, city_id) VALUES ('Software Fair', '2021-05-16', 'https://softwarefair.ca', 1);
INSERT INTO tb_event(name, date, url, city_id) VALUES ('Canadian Comic Expo', '2021-04-13', 'https://cancomicexpo.ca', 1);
INSERT INTO tb_event(name, date, url, city_id) VALUES ('Linux Conference Canada', '2021-05-23', 'https://linuxconf.ca', 2);
INSERT INTO tb_event(name, date, url, city_id) VALUES ('Spring React Week', '2021-05-03', 'https://devsuperior.ca', 3);

INSERT INTO tb_user (email, password) VALUES ('ana@gmail.com', '$2a$10$hekAig9V0rG/0/Svd5m6..7sl3hQmVhBwqaNmbf9zfEsj6brViBfC');
INSERT INTO tb_user (email, password) VALUES ('bob@gmail.com', '$2a$10$hekAig9V0rG/0/Svd5m6..7sl3hQmVhBwqaNmbf9zfEsj6brViBfC');

INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
