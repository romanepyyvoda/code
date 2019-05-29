/* What are the names of every branch in Kansas? */

SELECT DivisionType, OfficeName, OfficeCity
FROM Branches
WHERE OfficeCity = 'Kansas';



/* Which customers have a savings account in Kansas, but don’t live in Kansas? */

SELECT c.FirstName, c.LastName, c.City AS CustomerCity, 
		a.AccountType, a.OfficeID, b.OfficeCity, b.DivisionType
FROM Customers AS c
JOIN ConnCustAccount AS cca
	ON c.CustomerID = cca.CustomerID
JOIN Accounts AS a
	ON cca.AccountID = a.AccountID
JOIN Branches AS b
	ON b.OfficeID = a.OfficeID
WHERE a.AccountType = 'saving' AND
		b.OfficeCity = 'Kansas' AND c.City != 'Kansas';


		

/* Find all loans (loan ID and branch) and customer names where the loan is held by a single customer. */

SELECT SortedLoans.LoanID, b.OfficeName, b.OfficeCity,
		(c.FirstName +' '+ c.LastName) AS CustomerName
FROM (SELECT LoanID, COUNT(LoanID) AS CountOfCustomers
		FROM ConnCustLoan
		GROUP BY LoanID
		HAVING COUNT(LoanID) = 1) AS SortedLoans
JOIN Loans AS l
	ON SortedLoans.LoanID = l.LoanID
JOIN Branches AS b
	ON l.OfficeID = b.OfficeID
JOIN ConnCustLoan AS ccl
	ON ccl.LoanID = l.LoanID
JOIN Customers AS c
	ON ccl.CustomerID = c.CustomerID;
	
	
	


/* Find all employees (employee ID and name) who are a personal banker for at least one customer and who do not work at any branch. */

SELECT DISTINCT SortedEmployees.EmployeeID, (be.FirstName+' '+ be.LastName) AS EmployeeName, be.EmployeeRole,
		b.DivisionType, SortedEmployees.CountOfCustomersPerEmployee
FROM (SELECT DISTINCT EmployeeID, COUNT(EmployeeID) AS CountOfCustomersPerEmployee
	FROM ConnCustEmp
	GROUP BY EmployeeID
	HAVING COUNT(EmployeeID) > 0) AS SortedEmployees
JOIN BankEmployee AS be
	ON be.EmployeeID = SortedEmployees.EmployeeID
JOIN ConnEmpBranch AS ceb
	ON ceb.EmployeeID = be.EmployeeID
JOIN Branches AS b
	ON b.OfficeID = ceb.OfficeID
WHERE b.DivisionType = 'Office' AND be.EmployeeRole = 'banker';





/* Which customers have had more than three overdrafts in December 2009 and have loans totaling more than $100,000? */

SELECT CustomersLoansTotal.CustomerID,
		(c.FirstName+' '+c.LastName) AS CustomerName, 
		CustomersByOverdrafts.QtyOfOverdrafts, 
		CustomersLoansTotal.LoansTotal
FROM (SELECT AccountID, COUNT (AccountID) AS QtyOfOverdrafts
	FROM Overdrafts
	WHERE OverdraftDate BETWEEN '2009-12-01' AND '2009-12-31'
	GROUP BY AccountID
	HAVING COUNT(AccountID) = 3) AS CustomersByOverdrafts
JOIN ConnCustAccount AS cca
	ON CustomersByOverdrafts.AccountID = cca.AccountID
JOIN Customers AS c
	ON c.CustomerID = cca.CustomerID
JOIN (SELECT CustomerID, SUM(Loans) AS LoansTotal
	FROM (SELECT  CustomerID,
		(SELECT LoanAmount FROM Loans WHERE Loans.LoanID = ConnCustLoan.LoanID) AS Loans
		FROM ConnCustLoan) AS LoansPerCustomer
	GROUP BY CustomerID
	HAVING SUM(Loans) > 100000) AS CustomersLoansTotal
	ON CustomersLoansTotal.CustomerID = c.CustomerID;

