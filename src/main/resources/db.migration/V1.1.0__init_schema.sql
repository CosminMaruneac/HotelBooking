CREATE TABLE consumer
(
    id            uuid,
    first_name    varchar,
    last_name     varchar,
    phone_no      varchar,
    password      varchar,
    email         varchar unique,
    date_created  timestamp without time zone DEFAULT now(),
    date_updated  timestamp without time zone DEFAULT now(),
    is_deleted    boolean NOT NULL DEFAULT FALSE,
    url_signature varchar,
    PRIMARY KEY (id)
);
