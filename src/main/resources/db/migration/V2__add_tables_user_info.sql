CREATE TABLE "address" (
    id bigserial NOT NULL,
    geo_id bigint,
    street character varying(255),
    suite character varying(255),
    city character varying(255),
    zipcode character varying(255),
    CONSTRAINT address_pkey PRIMARY KEY (id)
);

ALTER TABLE "address" OWNER to postgres;

CREATE TABLE "geo" (
    id bigserial NOT NULL,
    latitude character varying(20),
    longitude character varying(20),
    CONSTRAINT geo_pkey PRIMARY KEY (id)
);

ALTER TABLE "geo" OWNER to postgres;

CREATE TABLE "company" (
    id bigserial NOT NULL,
    name character varying(255),
    catch_phrase character varying(255),
    bs character varying(255),
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

ALTER TABLE "company" OWNER to postgres;

-- ----------------------------
-- Add Columns
-- ----------------------------
ALTER TABLE "users" ADD COLUMN phone character varying(100);
ALTER TABLE "users" ADD COLUMN website character varying(100);
ALTER TABLE "users" ADD COLUMN address_id bigint;
ALTER TABLE "users" ADD COLUMN company_id bigint;

-- ----------------------------
-- Foreign Keys
-- ----------------------------
ALTER TABLE "address" ADD CONSTRAINT fk_geo FOREIGN KEY (geo_id) REFERENCES "geo" (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "users" ADD CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES "address" (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "users" ADD CONSTRAINT fk_company FOREIGN KEY (company_id) REFERENCES "company" (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

