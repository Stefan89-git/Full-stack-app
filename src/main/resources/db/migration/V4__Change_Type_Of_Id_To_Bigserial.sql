CREATE TABLE temp_table(
    id    BIGSERIAL PRIMARY KEY,
    name  TEXT NOT NULL,
    email TEXT NOT NULL,
    age   INT  NOT NULL
);

INSERT INTO temp_table (id, name, email, age)
SELECT id, name, email, age FROM customer;

DROP TABLE customer;

ALTER TABLE temp_table
    RENAME TO customer;