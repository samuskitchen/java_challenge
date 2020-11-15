CREATE TABLE "users" (
    id bigserial NOT NULL,
    endpoint_id bigint,
    email character varying(255),
    name character varying(255),
    username character varying(255),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

ALTER TABLE "users" OWNER to postgres;

CREATE TABLE "album" (
    id bigserial NOT NULL,
    owner_endpoint_id bigint,
    endpoint_id bigint,
    title character varying(255),
    CONSTRAINT album_pkey PRIMARY KEY (id)
);

ALTER TABLE "album" OWNER to postgres;

CREATE TABLE "post" (
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    endpoint_id bigint,
    body character varying(255),
    title character varying(255),
    CONSTRAINT post_pkey PRIMARY KEY (id)
);

ALTER TABLE "post" OWNER to postgres;

CREATE TABLE "photo" (
    id bigserial NOT NULL,
    album_id bigint NOT NULL,
    endpoint_id bigint,
    title character varying(255),
    url character varying(255),
    thumbnail_url character varying(255),
    CONSTRAINT photo_pkey PRIMARY KEY (id)
);

ALTER TABLE "photo" OWNER to postgres;

CREATE TABLE "comment"(
    id bigserial NOT NULL,
    post_id bigint NOT NULL,
    endpoint_id bigint,
    body character varying(255),
    email character varying(255),
    name character varying(255),
    CONSTRAINT comment_pkey PRIMARY KEY (id)
);

ALTER TABLE "comment" OWNER to postgres;

CREATE TABLE "album_user" (
    id bigserial NOT NULL,
    album_id bigint NOT NULL,
    user_id bigint NOT NULL,
    access_type character varying(20),
    CONSTRAINT album_user_pkey PRIMARY KEY (id)
);

ALTER TABLE "album_user" OWNER to postgres;

-- ----------------------------
-- Foreign Keys
-- ----------------------------
ALTER TABLE "post" ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "users" (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "photo" ADD CONSTRAINT fk_album FOREIGN KEY (album_id) REFERENCES "album" (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "comment" ADD CONSTRAINT fk_post FOREIGN KEY (post_id) REFERENCES "post" (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "album_user" ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "users" (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "album_user" ADD CONSTRAINT fk_album FOREIGN KEY (album_id) REFERENCES "album" (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;