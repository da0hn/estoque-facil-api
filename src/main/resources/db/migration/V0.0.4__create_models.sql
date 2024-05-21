CREATE SEQUENCE IF NOT EXISTS seq_models START WITH 1 INCREMENT BY 1;

CREATE TABLE model
(
    id BIGINT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    modified_by VARCHAR(255),
    name VARCHAR(128) NOT NULL,
    description VARCHAR(500),
    brand_id BIGINT NOT NULL,
    CONSTRAINT pk_model PRIMARY KEY (id)
);

ALTER TABLE model
    ADD CONSTRAINT uc_model_name UNIQUE (name);

ALTER TABLE model
    ADD CONSTRAINT fk_model_on_brand FOREIGN KEY (brand_id)
        REFERENCES brand (id);
