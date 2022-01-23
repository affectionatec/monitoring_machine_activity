INSERT INTO User (username, firstName, lastName, birth, email, type) VALUES ('TEST_USER', 'TEST', 'TEST', '01/01/01', 'TEST@TEST.TEST', 'USER');

INSERT INTO Equipment (name, inventory, charge_rate) VALUES ('TEST_EQUIPMENT', 1, 10);

INSERT INTO Projects VALUES ('TEST_CODE_123', 'TEST_PROJECT');

INSERT INTO ProjectCodes VALUES ('TEST_USER', 'TEST_CODE_123');

-- EquipmentBookingControllerTests
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 1, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 2, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 3, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 4, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 5, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 6, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 7, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 8, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 9, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 10, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 11, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 12, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 13, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 14, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 15, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 16, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 17, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 18, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 19, 0, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-02-02', 20, 0, NULL);

INSERT INTO ServicingSchedule (equipmentID, date_serviced, date_next_service) VALUES (1, '2021-07-01', '2222-01-01');

# EquipmentUsageTests
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2021-08-28', 1, 1, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2021-08-29', 2, 1, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2021-08-20', 3, 1, NULL);

# Laundry
INSERT INTO Laundry (barcode, status, comment) VALUES ('9999', 'Test', 'good');
INSERT INTO Laundry (barcode, status, comment) VALUES ('9998', 'Test', 'good');

-- InvoiceControllerTests
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-03-03', 1, 1, NULL);
INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (1, 'TEST_USER', '2222-03-03', 2, 1, NULL);

-- -- ZHE
-- INSERT INTO Task (id, createDate, deadLine,description,done,title) VALUES (1L, '2021-09-06 17:40:53', '2021-10-06 17:40:53','description',false ,'EquipmentName_1');
-- INSERT INTO Task (id, createDate, deadLine,description,done,title) VALUES (2L, '2021-09-06 17:40:53', '2021-10-06 17:40:53','description',false ,'EquipmentName_2');
-- INSERT INTO Task (id, createDate, deadLine,description,done,title) VALUES (3L, '2021-09-06 17:40:53', '2021-10-06 17:40:53','description',false ,'EquipmentName_3');
-- INSERT INTO Task (id, createDate, deadLine,description,done,title) VALUES (4L, '2021-09-06 17:40:53', '2021-10-06 17:40:53','description',false ,'EquipmentName_4');
-- INSERT INTO Task (id, createDate, deadLine,description,done,title) VALUES (5L, '2021-09-06 17:40:53', '2021-10-06 17:40:53','description',false ,'EquipmentName_5');

INSERT INTO GasUse.gas (id, name, storage, unit, bottleNum, coshh, location, hazardLevel, comments)
VALUES (1,'CO',50,'litres',4,'Under adequate surveillance.','First floor','Flammable','New Gas');
VALUES (2,'CO2',40,'kg',5,'Deal with accidents and emergencies.','Second floor','Non-flammable','New Gas');
INSERT INTO GasUse.gasDelivery (deliveryID, gasID, deliveryDate, expiryDate, distributionWeight, deliveryStaff)
VALUES (1,1,'2020-07-27','2030-07-27',50,'George');



