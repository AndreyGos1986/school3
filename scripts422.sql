CREATE TABLE Drivers
(
    Id              SERIAL PRIMARY KEY UNIQUE,
    Driver_name     VARCHAR NOT NULL,
    Driver_surname  VARCHAR NOT NULL,
    Driver_age      INTEGER CHECK ( Driver_age >= 18),
    Drivers_license BOOLEAN,
    Car_id          BIGINT
);

CREATE TABLE Cars
(
    Id    SERIAL PRIMARY KEY ,
    Brand VARCHAR NOT NULL,
    Model VARCHAR NOT NULL,
    Price INTEGER CHECK ( Price > 0 ),
    Owner INTEGER REFERENCES  Drivers (id)
);