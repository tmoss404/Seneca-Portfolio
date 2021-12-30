Assignment Submission Form
================================================================================
I/we declare that the attached assignment is my/our own work in accordance with Seneca Academic Policy. No
part of this assignment has been copied manually or electronically from any other source (including web sites) or
distributed to other students.

Name(s)         | Tanner Moss
                |            
Student ID(s)   |  017896150 

--------------------------------------------------------------------------
-------------------------------------------------------------------------- 

Project Description: 
This assignments purpose was to create a series of classes to bridge a connection between a java application and an SQL database.
We had to create a class to handle the connection to the server, dedicating a single module to the management of connection information 
and establishing the connection itself.
A class was created to handle the manipulation of data from the SQL database, offering a range of operations to create, read, update, 
and delete information within the database.
A javaBeans style class was created to model, store, and manage locally the type of data we would be expecting from the databse. In this
case it was a class for an "Employee" object, since the table we were working with was a table of Employees in a business.
A final test class to demonstrate the operation of the Database access/ management methods was created.



Notes:

The getEmployeesByDepartmentID method is not really applicable anymore 
due to the fact that our table is missing a DEPARTMENT_ID value.
I did, however, repurpose it to select for MANAGER_ID instead of DEPARTMENT_ID.
I did change the name of the method, and the name of the args to accommodate for this.

see: getEmployeesByManagerID(int manId)

-------------------

Having the properties file in the root folder of the project was causing issues
so I decided to simply move it into the src directory so I could find the file based
on the classpath pointing to src by default.
This way you shouldn't need to change anything.
Simply add a database.properties file to src and all should work as intended.

-------------------

I made some helper methods to consolidate some functionality I found to be a little bulky and I
ended up using in more than one place. See the bottom of the DBAccessHelper.java if you would like.
I only hope that wasn't against the rules, they really are harmless so all should be well.

