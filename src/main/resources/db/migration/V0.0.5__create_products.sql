CREATE SEQUENCE IF NOT EXISTS seq_products START WITH 1 INCREMENT BY 1;

CREATE TABLE product
(
    id          BIGINT                      NOT NULL DEFAULT nextval('seq_products'),
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(255)                NOT NULL,
    modified_by VARCHAR(255),
    name        VARCHAR(128)                NOT NULL,
    description VARCHAR(500),
    cost_price  DECIMAL(10, 2)              NOT NULL,
    sale_price  DECIMAL(10, 2)              NOT NULL,
    imei        VARCHAR(255)                NULL,
    quantity    BIGINT                      NOT NULL,
    model_id    BIGINT                      NOT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

ALTER TABLE product
    ADD CONSTRAINT uc_products_name UNIQUE (name);

ALTER TABLE product
    ADD CONSTRAINT fk_products_on_model FOREIGN KEY (model_id) REFERENCES model (id);
