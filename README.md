# Expense Reimbursement System (ERS) - Java
## Project Description
The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and past history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

## Technologies Used
* Java
* JDBC
* HTML
* CSS
* JavaScript
* Project Lombok - version 1.18.22
* JUnit - version 4.13.2
* Postgresql - version 42.3.3
* Jackson Databind - version 2.13.2.2
* Javax Servlet API - version 4.0.1
* Mockito JUnit Jupiter - version 4.2.0
* Spring Test Framework - version 5.3.19

## Features
List of features ready and TODOs for future development
* Single login page that handles two types of users: Employee, and Finance Manager.
* An Employee can submit reimbursements.
* An Employee can view all of the reimbursements they submitted, and check their status.
* A Finance Manager can view reimbursements by status.
* Through Radio buttons on a table, they can select pending reimbursements.
* With a reimbursement selected, a Finance Manager may deny or approve it.
* Input validation through JavaScript when submissions, approvals, or denial is attempted.

## To-do list:
* Add full automated testing using JUnit.
* Add H2 database dependency for local testing purposes.

## Getting Started
You will need the database credentials, which are stored in my local environment variables. Please request and then set them for the database functionality to work.

## Usage
* Login with preset credentials, how these credentials are passed to you is up to your manager.
* Employee: The page will start by loading all reimbursements submitted by you. To submit a request, select a type, amount in gold, optionally add a description, and then submit.
* Finance Manager: The page will start by loading reimbursements by "Pending" status. Select a status and click "filter by status" to show only reimbursements with that status type. 
* Finance Manager: To deny or approve a reimbursement, select a pending reimbursement via the selector in the table, then click "Deny" or "Approve" buttons respectively.

## License
This project uses the following license: MIT license.
Copyright (c) 2022 Frederick Doell. All Rights Reserved.
