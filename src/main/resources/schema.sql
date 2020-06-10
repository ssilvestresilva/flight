--DROP DATABASE IF EXISTS db_flight;

--CREATE DATABASE db_flight;
CREATE USER flight WITH PASSWORD 'flight';
GRANT ALL PRIVILEGES ON DATABASE db_flight TO flight;

\connect app

CREATE TABLE public.flight
(
    id 					bigint 												NOT NULL DEFAULT nextval('flight_id_seq'::regclass),
    departure 			character varying(20) COLLATE pg_catalog."default" 	NOT NULL,
    arrival 			character varying(20) COLLATE pg_catalog."default" 	NOT NULL,
    currency 			character varying(20) COLLATE pg_catalog."default" 	NOT NULL,
    price_avarage 		bigint,
    bags_price 			character varying COLLATE pg_catalog."default",
    date_from 			timestamp with time zone,
    date_to 			timestamp with time zone,
    CONSTRAINT flight_pkey 
    PRIMARY KEY (id)
);

ALTER TABLE public.flight OWNER to flight;