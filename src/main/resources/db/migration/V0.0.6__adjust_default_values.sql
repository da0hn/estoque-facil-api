alter table category
    alter column id set default nextval('seq_categories');

alter table category
    alter column updated_at set default now();

alter table category
    alter column created_at set default now();

alter table brand
    alter column id set default nextval('seq_brands');

alter table brand
    alter column updated_at set default now();

alter table brand
    alter column created_at set default now();

alter table model
    alter column id set default nextval('seq_models');

alter table model
    alter column updated_at set default now();

alter table model
    alter column created_at set default now();

alter table "user"
    alter column updated_at set default now();

