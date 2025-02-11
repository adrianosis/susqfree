CREATE TABLE health_unit (
  id                SERIAL NOT NULL,
  name              varchar(60) NOT NULL UNIQUE,
  type              varchar(20) NOT NULL,
  phone             varchar(16),
  street            varchar(60) NOT NULL,
  number            varchar(10) NOT NULL,
  complement        varchar(30),
  zipcode           varchar(9) NOT NULL,
  city              varchar(30) NOT NULL,
  state             varchar(2) NOT NULL,
  latitude          numeric(9, 6),
  longitude         numeric(9, 6),
  PRIMARY KEY (id));
