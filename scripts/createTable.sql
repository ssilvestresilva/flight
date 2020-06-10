-- Database: db_flight

-- DROP DATABASE db_flight;

--CREATE DATABASE db_flight
--    WITH 
--    OWNER = flight
--    ENCODING = 'UTF8'
--    LC_COLLATE = 'Portuguese_Brazil.1252'
--    LC_CTYPE = 'Portuguese_Brazil.1252'
--    TABLESPACE = pg_default
--    CONNECTION LIMIT = -1;

-- DROP TABLE public.flight;

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
)