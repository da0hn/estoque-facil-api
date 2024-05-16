CREATE SEQUENCE IF NOT EXISTS seq_brands START WITH 1 INCREMENT BY 1;

CREATE TABLE brand
(
    id BIGINT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    modified_by VARCHAR(255),
    name VARCHAR(128) NOT NULL,
    description VARCHAR(500),
    category_id BIGINT NOT NULL,
    CONSTRAINT pk_brand PRIMARY KEY (id)
);

ALTER TABLE brand
    ADD CONSTRAINT uc_brand_name UNIQUE (name);

ALTER TABLE brand
    ADD CONSTRAINT fk_brand_on_category FOREIGN KEY (category_id)
        REFERENCES category (id);
