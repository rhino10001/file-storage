CREATE SCHEMA IF NOT EXISTS public;

CREATE SEQUENCE IF NOT EXISTS files_data_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS public.files_data
(
    id BIGINT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    dir VARCHAR(255) NOT NULL,
    CONSTRAINT pk_files_data_id PRIMARY KEY (id),
    CONSTRAINT u_files_data_file_name UNIQUE (file_name)
);
