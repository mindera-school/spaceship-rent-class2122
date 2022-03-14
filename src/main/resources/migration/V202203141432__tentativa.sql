
CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL UNIQUE,
    name VARCHAR(256) NOT NULL,
    age INTEGER NOT NULL,
    license_number VARCHAR(256) NOT NULL,
    ssn BIGINT NOT NULL,
    planet VARCHAR(256) NOT NULL,
    email VARCHAR(256) NOT NULL,
    password VARCHAR(256) NOT NULL,
    user_type VARCHAR(256) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS users_id_generator
AS BIGINT
INCREMENT 1
MINVALUE 1
START 1;


CREATE TABLE IF NOT EXISTS spaceships (
  id BIGINT NOT NULL UNIQUE,
   name VARCHAR(255) NOT NULL,
   brand VARCHAR(255) NOT NULL,
   model VARCHAR(255) NOT NULL,
   register_number INTEGER NOT NULL,
   price_day FLOAT NOT NULL,
   CONSTRAINT pk_spaceships PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS spaceship_id_generator
AS BIGINT
INCREMENT 1
MINVALUE 1
START 1;


CREATE TABLE IF NOT EXISTS rents (
   id BIGINT NOT NULL UNIQUE,
    user_id BIGINT REFERENCES users(id) NOT NULL,
    spaceship_id BIGINT REFERENCES spaceships(id) NOT NULL,
    expected_pickup_date DATE  NOT NULL,
    expected_return_date DATE NOT NULL,
    pickup_date DATE,
    return_date DATE,
    price_per_day FLOAT NOT NULL,
    discount FLOAT NOT NULL,
    CONSTRAINT pk_rents PRIMARY KEY (id)


);

CREATE SEQUENCE IF NOT EXISTS rents_id_generator
AS BIGINT
INCREMENT 1
MINVALUE 1
START 1;

