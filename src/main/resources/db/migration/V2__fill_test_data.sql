insert into messages (created_time, text) values (current_time, "test text");
insert into messages (created_time, text) values (current_time, "test text");
insert into messages (created_time, text) values (current_time, "test text");

insert into users (email, password, user_type, is_blocked)
    values ("admin@mail.ru", "$2a$12$N7BKpBv1xcrRaDWvYN1FKekNs4c80HzMGDO2Xn9hUFtKIJhpmTb/G", "ADMIN", 0);
insert into users (email, password, user_type, is_blocked)
    values ("user@mail.ru",  "$2a$12$yhBmcPsRGiUjO4KOgVP1zO7eS/7.COPTbQMv0K4Ucfik9CUiJjYyq", "USER", 0);
insert into users (email, password, user_type, is_blocked)
    values ("blockeduser@mail.ru",  "$2a$12$yhBmcPsRGiUjO4KOgVP1zO7eS/7.COPTbQMv0K4Ucfik9CUiJjYyq", "USER", 1);