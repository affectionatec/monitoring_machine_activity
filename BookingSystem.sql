DROP SCHEMA EquipmentBooking;

CREATE SCHEMA EquipmentBooking;

USE EquipmentBooking;

DROP TABLE IF EXISTS  OperationData;
DROP TABLE IF EXISTS EquipmentOperationData;
DROP TABLE IF EXISTS OperationCheckItems;
DROP TABLE IF EXISTS ServicingSchedule;
DROP TABLE IF EXISTS UserEquipmentTrained;
DROP TABLE IF EXISTS Booking;
DROP TABLE IF EXISTS ProjectCodes;
DROP TABLE IF EXISTS Projects;
DROP TABLE IF EXISTS Equipment;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS chemical;
DROP TABLE IF EXISTS chemical_used;
DROP TABLE IF EXISTS Boots;
DROP TABLE IF EXISTS Suit;
DROP TABLE IF EXISTS Hood;
DROP TABLE IF EXISTS Laundry;

CREATE TABLE IF NOT EXISTS User (
	username VARCHAR(255) NOT NULL,
	firstName VARCHAR(255) NOT NULL,
	lastName VARCHAR(255) NOT NULL,
	birth VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	type VARCHAR(255) NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (username)
);

INSERT INTO User (username, firstName, lastName, birth, email, type) VALUES ("caitlin", "Caitlin", "Yih", "07/06/98", "caitlin@yih.com", "USER");
INSERT INTO User (username, firstName, lastName, birth, email, type) VALUES ("admin", "admin", "admin", "07/06/98", "admin@admin.com", "ADMIN");
INSERT INTO User (username, firstName, lastName, birth, email, type) VALUES ("zheliu", "zhe", "liu", "07/06/98", "zhe@liu.com", "USER");
INSERT INTO User (username, firstName, lastName, birth, email, type) VALUES ("superadmin", "superadmin", "superadmin", "07/06/98", "superadmin@superadmin.com", "SUPERADMIN");
INSERT INTO User (username, firstName, lastName, birth, email, type) VALUES ("junningchen", "junning", "chen", "07/06/98", "junning@chen.com", "USER");
INSERT INTO User (username, firstName, lastName, birth, email, type) VALUES ("junweili", "junwei", "li", "07/06/98", "junwei@li.com", "USER");

CREATE TABLE IF NOT EXISTS Equipment (
	 id INT AUTO_INCREMENT NOT NULL,
	 name VARCHAR(255) NOT NULL,
	 inventory INT NOT NULL,
	 charge_rate FLOAT,
	 CONSTRAINT equip_pk PRIMARY KEY (id)
);

INSERT INTO Equipment (name, inventory, charge_rate) VALUES ("EVAP1", 1, 10);
INSERT INTO Equipment (name, inventory, charge_rate) VALUES ("EVAP2", 1, 2);
INSERT INTO Equipment (name, inventory, charge_rate) VALUES ("EVAP3", 1, 5);
INSERT INTO Equipment (name, inventory, charge_rate) VALUES ("ICP1", 1, 11);
INSERT INTO Equipment (name, inventory, charge_rate) VALUES ("ICP2", 1, 1);
INSERT INTO Equipment (name, inventory, charge_rate) VALUES ("ICP3", 1, 100);

CREATE TABLE Projects (
	code VARCHAR(20) NOT NULL,
    name VARCHAR(64),
    CONSTRAINT code_pk PRIMARY KEY (code)
);

INSERT INTO Projects VALUES ("H4B9H4B9H4B9", "Project H");
INSERT INTO Projects VALUES ("K6Q3K6Q3K6Q3", "Project K");
INSERT INTO Projects VALUES ("O9LMO9LMO9LM", "Project O");
INSERT INTO Projects VALUES ("X2G8X2G8X2G8", "Project X");

CREATE TABLE ProjectCodes (
	username VARCHAR(255) NOT NULL,
    project_code VARCHAR(20) NOT NULL,
    CONSTRAINT `fk_project_code_ProjectCodes` FOREIGN KEY (project_code) REFERENCES Projects(code) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_username` FOREIGN KEY (username) REFERENCES User(username) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO ProjectCodes VALUES ("caitlin", "H4B9H4B9H4B9");
INSERT INTO ProjectCodes VALUES ("caitlin", "K6Q3K6Q3K6Q3");
INSERT INTO ProjectCodes VALUES ("caitlin", "O9LMO9LMO9LM");
INSERT INTO ProjectCodes VALUES ("caitlin", "X2G8X2G8X2G8");
INSERT INTO ProjectCodes VALUES ("zheliu", "H4B9H4B9H4B9");
INSERT INTO ProjectCodes VALUES ("zheliu", "K6Q3K6Q3K6Q3");
INSERT INTO ProjectCodes VALUES ("zheliu", "O9LMO9LMO9LM");
INSERT INTO ProjectCodes VALUES ("zheliu", "X2G8X2G8X2G8");

-- Users can book equipments with permissions
CREATE TABLE IF NOT EXISTS Booking (
	id INT AUTO_INCREMENT NOT NULL,
	equipmentID INTEGER NOT NULL,
	username VARCHAR(255) NOT NULL,
	date DATE,
    time INT,
    used BOOLEAN,
    project_code VARCHAR(20),
	CONSTRAINT equip_user_pk PRIMARY KEY (id),
	CONSTRAINT `fk_book_uid` FOREIGN KEY (username) REFERENCES User(username) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `fk_book_eid` FOREIGN KEY (equipmentID) REFERENCES Equipment(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `fk_project_code_Booking` FOREIGN KEY (project_code) REFERENCES Projects(code) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Users are trained to use equipment1 instead of equipment2, they just can book equipment1 and not book equipment2
CREATE TABLE IF NOT EXISTS UserEquipmentTrained(
	 id INT AUTO_INCREMENT NOT NULL,
	 equipmentID INT NOT NULL,
	 username VARCHAR(255) NOT NULL,
	 date_trained VARCHAR(255),
	 CONSTRAINT equip_user_pk PRIMARY KEY (id),
	 CONSTRAINT `fk_uid` FOREIGN KEY (username) REFERENCES User(username) ON DELETE CASCADE ON UPDATE CASCADE,
	 CONSTRAINT `fk_eid` FOREIGN KEY (equipmentID) REFERENCES Equipment(id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO UserEquipmentTrained (equipmentID, username, date_trained) VALUES (1, "caitlin", "2021-08-19");
INSERT INTO UserEquipmentTrained (equipmentID, username, date_trained) VALUES (3, "caitlin", "2021-08-19");

-- Because the equipment being serviced will also affect the booked condition
CREATE TABLE IF NOT EXISTS ServicingSchedule (
	 id INT AUTO_INCREMENT,
	 equipmentID INTEGER NOT NULL,
	 date_serviced VARCHAR(255),
	 date_next_service VARCHAR(255),
	 CONSTRAINT ser_schedule_pk PRIMARY KEY (id),
	 CONSTRAINT `fk_service_eid` FOREIGN KEY (equipmentID) REFERENCES Equipment(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Store operation data that user input
CREATE TABLE IF NOT EXISTS EquipmentOperationData (
                                                      id INT AUTO_INCREMENT,
                                                      equipmentID INTEGER NOT NULL,
                                                      date DATE,
                                                      CONSTRAINT ser_operation_pk PRIMARY KEY (id),
                                                      CONSTRAINT `fk_operation_id` FOREIGN KEY (equipmentID) REFERENCES Equipment(id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS TrainedRequestInformation(
    id INT AUTO_INCREMENT NOT NULL primary key,
    equipmentID INT NOT NULL,
    username VARCHAR(255) NOT NULL,
    date_trained VARCHAR(255),
    CONSTRAINT FK_TrainedRequestInformation_User FOREIGN KEY (username) REFERENCES User(username) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_TrainedRequestInformation_Equipment FOREIGN KEY (equipmentID) REFERENCES Equipment(id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `OperationCheckItems`(
                                      id INT NOT NULL AUTO_INCREMENT,
                                      name CHAR(50) NOT NULL,
                                      mode CHAR(50) NOT NULL,
                                      value CHAR(50) NOT NULL,
                                      CONSTRAINT item_pk PRIMARY KEY (id)) ;

CREATE TABLE IF NOT EXISTS OperationData (
                                             id INT AUTO_INCREMENT,
                                             operationId INT NOT NULL,
                                             itemID INT NOT NULL,
                                             value VARCHAR(255),
                                             CONSTRAINT operateData_pk PRIMARY KEY (id),
                                             CONSTRAINT `operate_data_fk` FOREIGN KEY (operationId) REFERENCES EquipmentOperationData(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                             CONSTRAINT `item_id_fk` FOREIGN KEY (itemID) REFERENCES OperationCheckItems(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Laundry (
                                       id INT AUTO_INCREMENT,
                                       barcode  VARCHAR(255),
                                       status VARCHAR(255),
                                       comment VARCHAR(255),
                                       CONSTRAINT laundry_pk PRIMARY KEY (id)

);

CREATE TABLE IF NOT EXISTS Suit (
                                    id INT AUTO_INCREMENT,
                                    size  VARCHAR(255),
                                    status VARCHAR(255),
                                    laundryId INT NOT NULL,
                                    CONSTRAINT suit_pk PRIMARY KEY (id),
                                    CONSTRAINT `suit_id_fk` FOREIGN KEY (laundryId) REFERENCES Laundry(id) ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE TABLE IF NOT EXISTS Hood (
                                    id INT AUTO_INCREMENT,
                                    size  VARCHAR(255),
                                    status VARCHAR(255),
                                    laundryId INT NOT NULL,
                                    CONSTRAINT suit_pk PRIMARY KEY (id),
                                    CONSTRAINT `hoods_id_fk` FOREIGN KEY (laundryId) REFERENCES Laundry(id) ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE TABLE IF NOT EXISTS Boots (
                                     id INT AUTO_INCREMENT,
                                     size  VARCHAR(255),
                                     status VARCHAR(255),
                                     laundryId INT NOT NULL,
                                     CONSTRAINT suit_pk PRIMARY KEY (id),
                                     CONSTRAINT `boots_id_fk` FOREIGN KEY (laundryId) REFERENCES Laundry(id) ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE SCHEMA `ChemicalDetail`;

USE `ChemicalDetail`;

DROP TABLE IF EXISTS chemical;
DROP TABLE IF EXISTS chemical_delivery;

CREATE TABLE `ChemicalDetail`.`chemical`(
                                            `id` INT NOT NULL AUTO_INCREMENT,
                                            `chemical_name` VARCHAR(50) NOT NULL,
                                            `level` VARCHAR(50) NOT NULL,
                                            `location` VARCHAR(50),
                                            `storage` DECIMAL(10,1) ZEROFILL,
                                            `unit` VARCHAR(50) NOT NULL,
                                            `bottle_num` INT,
                                            PRIMARY KEY (`id`) );

CREATE TABLE `ChemicalDetail`.`chemical_delivery`(
                                                     `id` INT NOT NULL AUTO_INCREMENT,
                                                     `chemicalID` INT NOT NULL,
                                                     `expiry` VARCHAR(50) NOT NULL,
                                                     `delivery_date` VARCHAR(50) NOT NULL,
                                                     `weight` DECIMAL(10,1) NOT NULL, PRIMARY KEY (`id`),
                                                     `unit` VARCHAR(50) NOT NULL,
                                                     CONSTRAINT `chemicalID` FOREIGN KEY (`chemicalID`) REFERENCES `ChemicalDetail`.`chemical`(`id`) );




