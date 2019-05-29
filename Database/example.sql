CREATE DATABASE SKS;
GO

USE SKS;
GO

CREATE TABLE Customers(
CustomerID int IDENTITY(1,1) PRIMARY KEY NOT NULL,
FirstName varchar(50) NOT NULL,
LastName varchar(50) NOT NULL,
AddressLine varchar(100) NOT NULL,
City varchar(50) NOT NULL,
EmployeeID int REFERENCES BankEmployee(EmployeeID) NOT NULL,
DefaultLoanID int REFERENCES Loans(LoanID) NOT NULL
);
GO


CREATE TABLE Accounts(
AccountID int IDENTITY(1,1) PRIMARY KEY NOT NULL,
CustomerID int REFERENCES Customers(CustomerID) NOT NULL,
AccountType varchar(50) NOT NULL,
Balance money DEFAULT 0,
DepositAmount money  DEFAULT 0,
WithdrawalAmount money  DEFAULT 0,
AccessedDate smalldatetime NULL,
InterestRate float NOT NULL,
OverdraftAmount money NOT NULL,
OverdraftQty int NOT NULL
);
GO

CREATE TABLE Loans(
LoanID int IDENTITY(1,1) PRIMARY KEY NOT NULL,
LoanNumber varchar(50) NOT NULL,
LoanDate smalldatetime NOT NULL,
LoanAmountTotal money NOT NULL,
PaymentNumber varchar(50) NOT NULL,
PaymentDate smalldatetime NULL,
PaymentTotal money  NOT NULL,
CreditTotal money  NOT NULL
);
GO


CREATE TABLE Branches(
OfficeID int IDENTITY(1,1) PRIMARY KEY NOT NULL,
LoanID int REFERENCES Loans(LoanID) NOT NULL,
AccountID int REFERENCES Accounts(AccountID) NOT NULL,
DivisionName varchar(50) NOT NULL,
OfficeName varchar(50) NOT NULL,
OfficeAddress varchar(100) NOT NULL,
OfficeCity varchar(50) NOT NULL
);
GO


CREATE TABLE BankEmployee(
EmployeeID int IDENTITY(1,1) PRIMARY KEY NOT NULL,
OfficeID int REFERENCES Branches(OfficeID) NOT NULL,
FirstName varchar(50) NOT NULL,
LastName varchar(50) NOT NULL,
EmployeeRole varchar(50)  NULL,
Manager varchar(50)  NULL,
HomeAddress varchar(100) NOT NULL,
StartDate smalldatetime NULL
);
GO

