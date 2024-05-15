CREATE SEQUENCE IF NOT EXISTS seq_categories START WITH 1 INCREMENT BY 1;

CREATE TABLE category
(
    id BIGINT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    modified_by VARCHAR(255),
    name VARCHAR(128) NOT NULL,
    description VARCHAR(500),
    CONSTRAINT pk_category PRIMARY KEY (id)
);

ALTER TABLE category
    ADD CONSTRAINT uc_category_name UNIQUE (name);
