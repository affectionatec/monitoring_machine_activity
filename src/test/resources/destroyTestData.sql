DELETE FROM User WHERE username='TEST_USER';

DELETE FROM Equipment WHERE name='TEST_EQUIPMENT';

DELETE FROM Projects WHERE code IN ('TEST_CODE_123','VALID_TEST_CODE');

DELETE FROM ProjectCodes WHERE username='TEST_USER';

DELETE FROM Booking WHERE username='TEST_USER';

DELETE FROM ServicingSchedule WHERE date_next_service='2222-01-01';

DELETE FROM GasUse.gas WHERE name='Ne';

delete  from OperationCheckItems where name like 'test%';
delete  from Laundry where status like 'Test%';