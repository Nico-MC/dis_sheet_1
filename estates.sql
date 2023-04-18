-- extensions
CREATE EXTENSION pgcrypto;

-- tables
CREATE TABLE estate_agent (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255) NOT NULL,
    address varchar(255) NOT NULL,
    login text NOT NULL UNIQUE,
    password text NOT NULL
);

CREATE TABLE estate (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    estate_id SERIAL UNIQUE,
    city varchar(255) NOT NULL,
    postal_code int NOT NULL,
    street varchar(255) NOT NULL,
    street_number int NOT NULL,
    square_area decimal(6,2) NOT NULL
);

CREATE TABLE apartment (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    floor int NOT NULL,
    rent decimal(6,2) NOT NULL,
    rooms int NOT NULL,
    balcony boolean NOT NULL,
    builtin_kitchen boolean NOT NULL
);

CREATE TABLE house (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    floors int NOT NULL,
    price decimal(9,2) NOT NULL,
    garden boolean NOT NULL
);

CREATE TABLE contract (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    contract_no uuid UNIQUE DEFAULT gen_random_uuid (),
    date varchar(255) NOT NULL,
    place varchar(255) NOT NULL
);

CREATE TABLE tenancy_contract (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    start_date varchar(255) NOT NULL,
    duration varchar(255) NOT NULL,
    additional_costs decimal(6,2) NOT NULL
);

CREATE TABLE purchase_contract (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    no_of_installments varchar(255) NOT NULL,
    intrest_rate decimal(4,2) NOT NULL
);

CREATE TABLE person (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    address varchar(255) NOT NULL
);

-- dummy data
INSERT INTO estate_agent (name, address, login, password)
VALUES
  ('Max', 'Berlin, 10715 , Schoelerpark, 39', 'max@estate.com', crypt('max123', gen_salt('bf'))),
  ('Nico', 'Hamburg, 20457, Alter Wandrahm, 4', 'nico@estate.com', crypt('nico123', gen_salt('bf')));
  
INSERT INTO apartment (floor, rent, rooms, balcony, builtin_kitchen)
VALUES
  (1, 775.00, 2, true, false),
  (3, 1200.00, 3, false, true);

INSERT INTO house (floors, price, garden)
VALUES
  (1, 1200000.00, false),
  (3, 3400000.00, true);
  
INSERT INTO estate (city, postal_code, street, street_number, square_area)
VALUES
  ('Berlin', 10115, 'Ligusterweg', 4, 96.85),
  ('Hamburg', 22415, 'Luemmelweg', 42, 45.00);

/*-- insert estates (houses and apartments)
WITH new_house AS (
    INSERT INTO house (floors, price, garden)
    VALUES (1, 1200000.00, false), (3, 3400000.00, true)
    RETURNING id
),
new_apartment AS (
    INSERT INTO apartment (floor, rent, rooms, balcony, builtin_kitchen)
    VALUES (1, 775.00, 2, true, false), (3, 1200.00, 3, false, true)
    RETURNING id
)

INSERT INTO estate (estate_id, city, postal_code, street, street_number, square_area)
VALUES 
    ((SELECT id FROM new_house OFFSET 0 LIMIT 1), 'Berlin', 10115, 'Ligusterweg', 4, 96.85),
	((SELECT id FROM new_apartment OFFSET 0 LIMIT 1), 'Bremen', 28355, 'Mackensenweg', 21, 45.68),
    ((SELECT id FROM new_house OFFSET 1 LIMIT 1), 'Hamburg', 22415, 'Luemmelweg', 42, 45.00),
	((SELECT id FROM new_apartment OFFSET 1 LIMIT 1), 'Bremen', 28755, 'Tannenstraße', 38, 56.70);*/

INSERT INTO person (first_name, name, address)
VALUES
  ('Daniel', 'Trello', 'Hamburg, 20099, Lange Reihe, 17'),
  ('Vino', 'Lesch', 'Berlin, 14193, Schildhorn, 8');

INSERT INTO tenancy_contract (start_date, duration, additional_costs)
VALUES
  ('2023-05-15', '2 years', 150.00),
  ('2023-07-01', '1 years', 120.00);

INSERT INTO purchase_contract (no_of_installments, intrest_rate)
VALUES
  ('120 months', 6.24),
  ('180 months', 7.12);

INSERT INTO contract (date, place)
VALUES
  ('2023-07-12', 'Berlin, 10719, Kurfürstendamm, 42'),
  ('2023-06-08', 'Hamburg, 20457 , Großer Grasbrook, 9');