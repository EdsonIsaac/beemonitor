CREATE TABLE tb_hives
(
    id                 UUID NOT NULL,
    created_date       TIMESTAMP,
    last_modified_date TIMESTAMP,
    created_by_user    VARCHAR(255),
    modified_by_user   VARCHAR(255),
    code               VARCHAR(255),
    CONSTRAINT pk_tb_hives PRIMARY KEY (id)
);

CREATE TABLE tb_images
(
    id                 UUID NOT NULL,
    created_date       TIMESTAMP,
    last_modified_date TIMESTAMP,
    created_by_user    VARCHAR(255),
    modified_by_user   VARCHAR(255),
    name               VARCHAR(255),
    CONSTRAINT pk_tb_images PRIMARY KEY (id)
);

CREATE TABLE tb_mensurations
(
    id                 UUID             NOT NULL,
    created_date       TIMESTAMP,
    last_modified_date TIMESTAMP,
    created_by_user    VARCHAR(255),
    modified_by_user   VARCHAR(255),
    temperature        DOUBLE PRECISION NOT NULL,
    humidity           DOUBLE PRECISION NOT NULL,
    weight             DOUBLE PRECISION NOT NULL,
    hive_id            UUID,
    CONSTRAINT pk_tb_mensurations PRIMARY KEY (id)
);

CREATE TABLE tb_users
(
    id                 UUID         NOT NULL,
    created_date       TIMESTAMP,
    last_modified_date TIMESTAMP,
    created_by_user    VARCHAR(255),
    modified_by_user   VARCHAR(255),
    name               VARCHAR(255),
    username           VARCHAR(255),
    password           VARCHAR(255),
    enabled            BOOLEAN      NOT NULL,
    department         VARCHAR(255) NOT NULL,
    photo_id           UUID,
    CONSTRAINT pk_tb_users PRIMARY KEY (id)
);

ALTER TABLE tb_mensurations
    ADD CONSTRAINT FK_TB_MENSURATIONS_ON_HIVE FOREIGN KEY (hive_id) REFERENCES tb_hives (id);

ALTER TABLE tb_users
    ADD CONSTRAINT FK_TB_USERS_ON_PHOTO FOREIGN KEY (photo_id) REFERENCES tb_images (id);