ALTER TABLE customer ALTER COLUMN id SET DEFAULT nextval('customer_id_seq');

UPDATE customer SET id = nextval('customer_id_seq');