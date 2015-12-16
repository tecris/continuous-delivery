CREATE TABLE Planet (
  id numeric(20) NOT NULL,
  Galaxy varchar(255) DEFAULT NULL,
  Name varchar(255) DEFAULT NULL,
  version integer DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE TABLE_GENERATOR
(
  gen_key   varchar(255) not null,
  gen_value integer,
  PRIMARY KEY (gen_key)
);
INSERT INTO TABLE_GENERATOR (Gen_Key,GEN_VALUE) VALUES ('PLANETS.GENERATOR',0);
