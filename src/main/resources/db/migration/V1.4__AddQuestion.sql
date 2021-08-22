create table question
(
    id            int auto_increment,
    title         varchar default 50,
    description   text,
    gmt_create    bigint,
    modified      bigint,
    creator       int,
    comment_count int     default 0,
    view_count    int     default 0,
    like_count    int     default 0,
    tag           varchar default 256,
    constraint question_pk
		primary key (id)
);

