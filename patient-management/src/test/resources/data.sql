create table patient
(
    id           uuid        not null,
    name         varchar(60) not null,
    gender       char(2)     not null,
    cpf          varchar(11) not null,
    sus_number   varchar(15) not null,
    birth_date   date        not null,
    phone_number varchar(15) not null,
    mail         varchar(100),
    street       varchar(60) not null,
    number       varchar(10) not null,
    complement   varchar(60),
    district     varchar(30) not null,
    city         varchar(30) not null,
    state        char(2)     not null,
    postal_code  char(8)     not null,
    latitude     numeric(9, 6),
    longitude    numeric(9, 6),
    constraint patient_pk primary key (id),
    constraint patient_cpf_uq unique (cpf),
    constraint patient_sus_number_uq unique (sus_number)
);
