INSERT INTO Customers
VALUES 
('Kasey', 'Denver', '3010 Cindel Drive', 'Libery'),
('Robert', 'Denirro', 'PO Box 2169', 'Washington'),
('Dan', 'Dicaprio', '1990 Westwood Blvd', 'New York'),
('Bob', 'Luna', '1230 MacLeod Trail', 'Shawnee'),
('William', 'Shekspire', '3230 Sierra Vista', 'Washington');
GO


INSERT INTO Branches
VALUES 
('Office', 'Downtown Main', '3245 S Drive', 'Kansas'),
('Branch', 'New Plaza', '342 Valderas', 'New York'),
('Office', 'Chinook Mall', '52 Ave', 'Pittsburg'),
('Branch', 'SE Corner', '43 N Street', 'Kansas'),
('Office', 'Manhattan Plaza', ' 7A Manhattan Drive', 'New York');
GO


INSERT INTO BankEmployee
VALUES 
('Kevin', 'Waters', 'loan officer', 'Scott Pit', '23 S Street', '2015-11-01 08:00:00', 'Kansas'),
('Shrek', 'Charming', 'banker', 'Peter Nova', 'Big Ave', '2014-08-15 08:00:00', 'New York'),
('Kate', 'Freedom', 'banker', 'Mark Twen', 'Forest Blvd', '2017-07-01 08:00:00', 'Pittsburg'),
('Richard', 'Mongol', 'banker', 'Tony Chilly', '1 South Ave', '2018-05-15 08:00:00', 'Kansas'),
('Shanon', 'Shine', 'loan officer', 'Karl Engels', 'Promenada Way', '2010-10-01 08:00:00', 'New York');
GO


INSERT INTO Accounts
VALUES 
('989320', 'saving', 1, 10000.00, '2016-07-28 08:00:00', 0.15),
('350200', 'checking', 2, 8500.00, '2018-11-01 14:25:00', NULL),
('255100', 'checking', 3, 6300.00, '2018-10-15 06:45:00', NULL),
('623351', 'saving', 4, 5000.00, '2017-01-01 11:30:00', 0.1),
('200200', 'saving', 5, 15000.00, '2017-08-12 12:35:00', 0.2);
GO


INSERT INTO ConnCustAccount
VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(2, 1);
GO


INSERT INTO Overdrafts
VALUES 
(2, '255', 1000.00, '2018-10-13 08:00:00'),
(3, '354', 500.00, '2018-11-24 14:25:00'),
(2, '259', 1250.00, '2009-12-13 09:06:31'),
(3, '364', 580.00, '2009-11-24 11:15:09'),
(2, '254', 120.00, '2009-12-15 09:06:31'),
(2, '269', 10.00, '2009-12-18 09:06:31'),
(3, '294', 155.00, '2009-12-15 13:07:45');
GO


INSERT INTO ConnEmpBranch
VALUES 
(1, 4),
(2, 5),
(3, 3),
(4, 1),
(5, 2),
(2, 3);
GO


INSERT INTO ConnCustEmp
VALUES 
(1, 4),
(3, 5),
(5, 3),
(4, 1),
(2, 2),
(5, 2);
GO

INSERT INTO Loans
VALUES 
(1, 125000.00),
(2, 225000.00),
(3, 50000.00),
(4, 175000.00),
(5, 75000.00),
(5, 50000.00),
(5, 75000.00);
GO

INSERT INTO ConnCustLoan
VALUES 
(1, 1),
(3, 2),
(5, 3),
(4, 4),
(2, 5),
(2, 6),
(2, 7),
(4, 5);
GO

INSERT INTO Payments
VALUES 
(5, 5000.00, '2018-11-12 11:00:00'),
(4, 2800.00, '2017-07-05 08:00:00'),
(3, 4000.00, '2016-04-28 22:35:00'),
(2, 3750.00, '2017-12-14 15:15:00'),
(1, 2500.00, '2018-06-30 06:00:00');
GO