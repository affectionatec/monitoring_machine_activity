CREATE SCHEMA GasUse;
USE GasUse;

-- Create gas table
CREATE TABLE IF NOT EXISTS gas (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    storage int DEFAULT 0,
    unit varchar(50) NOT NULL,
    bottleNum int NOT NULL,
    coshh varchar(255) NOT NULL,
    location varchar(50) NOT NULL,
    hazardLevel varchar(50) NOT NULL,
    comments text,
    CONSTRAINT gas_PK PRIMARY KEY (id)
);
-- Insert data to gas table
INSERT INTO gas (name, unit, bottleNum, coshh, location, hazardLevel)
VALUES ('CO','litres',4,'Under adequate surveillance.','First floor','Flammable');
INSERT INTO gas (name, unit, bottleNum, coshh, location, hazardLevel)
VALUES ('CO2','litres',5,'Deal with accidents and emergencies.','Second floor','Non-flammable');
INSERT INTO gas (name, unit, bottleNum, coshh, location, hazardLevel)
VALUES ('N2','litres',4,'Check employees are carrying out tasks.','Third floor','Compressed');
INSERT INTO gas (name, unit, bottleNum, coshh, location, hazardLevel)
VALUES ('O2','litres',6,'Prevent exposure to hazardous substances.','First floor','Compressed');
INSERT INTO gas (name, unit, bottleNum, coshh, location, hazardLevel, comments)
VALUES ('H2','litres',4,'Deal with accidents and emergencies.','First floor','Flammable','New Gas');
INSERT INTO gas (name, unit, bottleNum, coshh, location, hazardLevel)
VALUES ('CI2','litres',3,'Under adequate surveillance.','Third floor','Toxic');
INSERT INTO gas (name, unit, bottleNum, coshh, location, hazardLevel)
VALUES ('CH4','litres',4,'Carry out COSHH risk assessments.','Second floor','Oxidizing');

-- Create gasDelivery table
CREATE TABLE IF NOT EXISTS gasDelivery (
    deliveryID int NOT NULL AUTO_INCREMENT,
    gasID int NOT NULL,
    deliveryDate DATE NOT NULL,
    expiryDate DATE NOT NULL,
    distributionWeight int NOT NULL,
    deliveryStaff varchar(50) NOT NULL,
    CONSTRAINT gasDelivery_PK PRIMARY KEY (deliveryID),
    CONSTRAINT FK_gas FOREIGN KEY (gasID) REFERENCES gas(id)
);
-- Insert data to gasDelivery table
INSERT INTO gasDelivery (gasID, deliveryDate, expiryDate, distributionWeight, deliveryStaff)
VALUES (1,'2020-07-27','2030-07-27',1,'George');
INSERT INTO gasDelivery (gasID, deliveryDate, expiryDate, distributionWeight, deliveryStaff)
VALUES (1,'2020-08-27','2030-08-27',2,'Hill');
INSERT INTO gasDelivery (gasID, deliveryDate, expiryDate, distributionWeight, deliveryStaff)
VALUES (1,'2020-09-27','2030-09-27',3,'John');
INSERT INTO gasDelivery (gasID, deliveryDate, expiryDate, distributionWeight, deliveryStaff)
VALUES (1,'2021-08-13','2031-08-13',4,'Johnson');
INSERT INTO gasDelivery (gasID, deliveryDate, expiryDate, distributionWeight, deliveryStaff)
VALUES (2,'2021-07-31','2031-07-31',7,'George');
INSERT INTO gasDelivery (gasID, deliveryDate, expiryDate, distributionWeight, deliveryStaff)
VALUES (2,'2021-08-15','2031-08-15',5,'John');
INSERT INTO gasDelivery (gasID, deliveryDate, expiryDate, distributionWeight, deliveryStaff)
VALUES (3,'2021-07-31','2031-07-31',3,'Johnson');
INSERT INTO gasDelivery (gasID, deliveryDate, expiryDate, distributionWeight, deliveryStaff)
VALUES (3,'2021-08-17','2031-08-17',11,'Hill');
INSERT INTO gasDelivery (gasID, deliveryDate, expiryDate, distributionWeight, deliveryStaff)
VALUES (3,'2021-08-18','2031-08-18',1,'Lavine');