CREATE SEQUENCE customer_id_seq START 1;

ALTER TABLE customer ALTER COLUMN id SET DEFAULT nextval('customer_id_seq');