DELETE
FROM orders;
DELETE
FROM masters;
DELETE
FROM users;

INSERT INTO BeautySalon.users (`id`, `email`, `name`, `surname`, `password`, `role`)
VALUES (1, 'admin@gmail.com', 'Garri', 'Spacer', 'Splos2142132', 'ADMIN');
INSERT INTO BeautySalon.users (`id`, `email`, `name`, `surname`, `password`, `role`)
VALUES (2, 'user2@gmail.com', 'Salazar', 'Slizerin', 'Slizer12345', 'DEFAULT');
INSERT INTO BeautySalon.users (`id`, `email`, `name`, `surname`, `password`, `role`)
VALUES (3, 'user3@gmail.com', 'Larry', 'Goodman', 'goodDayLarry', 'DEFAULT');
INSERT INTO BeautySalon.users (`id`, `email`, `name`, `surname`, `password`, `role`)
VALUES (4, 'user4@gmail.com', 'Illidan', 'DemonHunter', 'DemonDestr555', 'DEFAULT');
INSERT INTO BeautySalon.users (`id`, `email`, `name`, `surname`, `password`, `role`)
VALUES (5, 'user5@gmail.com', 'Kaelthas', 'Sunstrider', 'ovaCeltalas24212', 'DEFAULT');
INSERT INTO BeautySalon.users (`id`, `email`, `name`, `surname`, `password`, `role`)
VALUES (6, 'user6@gmail.com', 'Thrall', 'Ogrimar', 'LocktarOGar241569', 'DEFAULT');

INSERT INTO BeautySalon.users (`id`, `email`, `name`, `surname`, `password`, `role`)
VALUES (7, 'master1@gmail.com', 'Garri', 'Spacer', 'Splos2142132', 'MASTER');
INSERT INTO BeautySalon.users (`id`, `email`, `name`, `surname`, `password`, `role`)
VALUES (8, 'master2@gmail.com', 'Marry', 'Blood', 'bMarryBok42352', 'MASTER');
INSERT INTO BeautySalon.users (`id`, `email`, `name`, `surname`, `password`, `role`)
VALUES (9, 'master3@gmail.com', 'Melina', 'Kartter', 'kartier5jso1', 'MASTER');
INSERT INTO BeautySalon.users (`id`, `email`, `name`, `surname`, `password`, `role`)
VALUES (10, 'master4@gmail.com', 'Katty', 'Bracer', 'ironBraceKatty', 'MASTER');
INSERT INTO BeautySalon.users (`id`, `email`, `name`, `surname`, `password`, `role`)
VALUES (11, 'master5@gmail.com', 'Elizabeth', 'Svonce', 'el1zabethSvonce', 'MASTER');
INSERT INTO BeautySalon.users (`id`, `email`, `name`, `surname`, `password`, `role`)
VALUES (12, 'master6@gmail.com', 'Betty', 'Cooper', 'CooperBetts5552', 'MASTER');
INSERT INTO BeautySalon.users (`id`, `email`, `name`, `surname`, `password`, `role`)
VALUES (13, 'master7@gmail.com', 'Nika', 'Fox', 'whatDoesTheFoxSay', 'MASTER');

INSERT INTO BeautySalon.masters (`id`, `speciality`, `rate`)
VALUES (7, 'HAIRDRESSER', 4.7);
INSERT INTO BeautySalon.masters (`id`, `speciality`, `rate`)
VALUES (8, 'HAIRDRESSER', 2.2);
INSERT INTO BeautySalon.masters (`id`, `speciality`, `rate`)
VALUES (9, 'MANICURIST', 2.5);
INSERT INTO BeautySalon.masters (`id`, `speciality`, `rate`)
VALUES (10, 'BEAUTICIAN', 3.5);
INSERT INTO BeautySalon.masters (`id`, `speciality`, `rate`)
VALUES (11, 'MANICURIST', 3.5);
INSERT INTO BeautySalon.masters (`id`, `speciality`, `rate`)
VALUES (12, 'HAIRDRESSER', 3.2);
INSERT INTO BeautySalon.masters (`id`, `speciality`, `rate`)
VALUES (13, 'BEAUTICIAN', 1.2);

DELETE
FROM favors;
INSERT INTO BeautySalon.favors (`id`, `name`, `price`, `speciality`)
VALUES (1, 'Манікюр класичний', 500, 'MANICURIST');
INSERT INTO BeautySalon.favors (`id`, `name`, `price`, `speciality`)
VALUES (2, 'Манікюр з нарощенням', 675, 'MANICURIST');
INSERT INTO BeautySalon.favors (`id`, `name`, `price`, `speciality`)
VALUES (3, 'Стрижка', 250, 'HAIRDRESSER');
INSERT INTO BeautySalon.favors (`id`, `name`, `price`, `speciality`)
VALUES (4, 'Макіяж', 400, 'BEAUTICIAN');
INSERT INTO BeautySalon.favors (`id`, `name`, `price`, `speciality`)
VALUES (5, 'Фарбування волосся', 300, 'HAIRDRESSER');
INSERT INTO BeautySalon.favors (`id`, `name`, `price`, `speciality`)
VALUES (7, 'Макіяж святковий', 333, 'BEAUTICIAN');