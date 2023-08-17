CREATE TABLE tb_authorities (
   tb_users_id  UUID            NOT NULL,
   authorities  VARCHAR(255)    NOT NULL
);

CREATE TABLE tb_hives (
   id                   UUID            NOT NULL,
   created_date         TIMESTAMP,
   last_modified_date   TIMESTAMP,
   created_by_user      VARCHAR(50),
   modified_by_user     VARCHAR(50),
   code                 VARCHAR(50),
   CONSTRAINT pk_tb_hives PRIMARY KEY (id)
);

CREATE TABLE tb_images (
   id                   UUID            NOT NULL,
   created_date         TIMESTAMP,
   last_modified_date   TIMESTAMP,
   created_by_user      VARCHAR(50),
   modified_by_user     VARCHAR(50),
   name                 VARCHAR(30),
   CONSTRAINT pk_tb_images PRIMARY KEY (id)
);

CREATE TABLE tb_mensurations (
   id                   UUID                NOT NULL,
   created_date         TIMESTAMP,
   last_modified_date   TIMESTAMP,
   created_by_user      VARCHAR(50),
   modified_by_user     VARCHAR(50),
   temperature          DOUBLE PRECISION    NOT NULL,
   humidity             DOUBLE PRECISION    NOT NULL,
   weight               DOUBLE PRECISION    NOT NULL,
   hive_id              UUID,
   CONSTRAINT pk_tb_mensurations PRIMARY KEY (id)
);

CREATE TABLE tb_users (
   id                       UUID                NOT NULL,
   created_date             TIMESTAMP,
   last_modified_date       TIMESTAMP,
   created_by_user          VARCHAR(50),
   modified_by_user         VARCHAR(50),
   name                     VARCHAR(100)        NOT NULL,
   username                 VARCHAR(50)         NOT NULL,
   password                 VARCHAR(255)        NOT NULL,
   enabled                  BOOLEAN             NOT NULL,
   photo_id                 UUID,
   CONSTRAINT pk_tb_users PRIMARY KEY (id)
);

ALTER TABLE tb_hives ADD CONSTRAINT uc_tb_hives_code UNIQUE (code);

ALTER TABLE tb_images ADD CONSTRAINT uc_tb_images_name UNIQUE (name);

ALTER TABLE tb_users ADD CONSTRAINT uc_tb_users_username UNIQUE (username);

ALTER TABLE tb_mensurations ADD CONSTRAINT FK_TB_MENSURATIONS_ON_HIVE FOREIGN KEY (hive_id) REFERENCES tb_hives (id);

ALTER TABLE tb_users ADD CONSTRAINT FK_TB_USERS_ON_PHOTO FOREIGN KEY (photo_id) REFERENCES tb_images (id);

ALTER TABLE tb_authorities ADD CONSTRAINT fk_tb_authorities_on_user FOREIGN KEY (tb_users_id) REFERENCES tb_users (id);