USE master;
GO

DROP DATABASE IF EXISTS SKS; 
GO

CREATE DATABASE SKS;
GO

USE SKS;
GO

CREATE TABLE Customers(
CustomerID int IDENTITY(1,1) PRIMARY KEY NOT NULL,
FirstName varchar(50) NOT NULL,
LastName varchar(50) NOT NULL,
HomeAddress varchar(100) NOT NULL,
City varchar(50) NOT NULL
);
GO

CREATE TABLE Branches(
OfficeID int IDENTITY(1,1) PRIMARY KEY NOT NULL,
DivisionType varchar(50) NOT NULL,
OfficeName varchar(50) NOT NULL,
OfficeAddress varchar(100) NOT NULL,
OfficeCity varchar(50) NOT NULL
);
GO

CREATE TABLE Accounts(
AccountID int IDENTITY(1,1) PRIMARY KEY NOT NULL,
AccountNumber varchar(50) NOT NULL,
AccountType varchar(50) NOT NULL,
OfficeID int REFERENCES Branches(OfficeID) NOT NULL,
Balance money DEFAULT 0,
AccessedDate smalldatetime NOT NULL,
InterestRate float NULL
);
GO


CREATE TABLE Overdrafts(
OverdraftID int IDENTITY(1,1) PRIMARY KEY NOT NULL,
AccountID int REFERENCES Accounts(AccountID) NOT NULL,
ChequeNumber varchar(50) NOT NULL,
OverdraftAmount money NULL,
OverdraftDate smalldatetime NULL
);
GO


CREATE TABLE Loans(
LoanID int IDENTITY(1,1) PRIMARY KEY NOT NULL,
OfficeID int REFERENCES Branches(OfficeID) NOT NULL,
LoanAmount money NOT NULL
);
GO


CREATE TABLE Payments(
PaymentID int IDENTITY(1,1) PRIMARY KEY NOT NULL,
LoanID int REFERENCES Loans(LoanID) NOT NULL,
PaymentAmount money NOT NULL,
PaymentDate smalldatetime NOT NULL
);
GO


CREATE TABLE BankEmployee(
EmployeeID int IDENTITY(1,1) PRIMARY KEY NOT NULL,
FirstName varchar(50) NOT NULL,
LastName varchar(50) NOT NULL,
EmployeeRole varchar(50)  NULL,
Manager varchar(50)  NULL,
HomeAddress varchar(100) NOT NULL,
StartDate smalldatetime NOT NULL,
City varchar(50) NOT NULL
);
GO

CREATE TABLE ConnEmpBranch(
EmployeeID int REFERENCES BankEmployee(EmployeeID) NOT NULL,
OfficeID int REFERENCES Branches(OfficeID) NOT NULL,
PRIMARY KEY(OfficeID, EmployeeID)
);
GO


CREATE TABLE ConnCustEmp(
CustomerID int REFERENCES Customers(CustomerID) NOT NULL,
EmployeeID int REFERENCES BankEmployee(EmployeeID) NOT NULL,
PRIMARY KEY(CustomerID, EmployeeID)
);
GO


CREATE TABLE ConnCustLoan(
CustomerID int REFERENCES Customers(CustomerID) NOT NULL,
LoanID int REFERENCES Loans(LoanID) NOT NULL,
PRIMARY KEY(CustomerID, LoanID)
);
GO


CREATE TABLE ConnCustAccount(
CustomerID int REFERENCES Customers(CustomerID) NOT NULL,
AccountID int REFERENCES Accounts(AccountID) NOT NULL,
PRIMARY KEY(CustomerID, AccountID)
);
GO
