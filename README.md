# Simple CRUD application for Employee Performance Management

## Setup

1. Resolve meven dependencies
2. `mvn clean install`
3. data.sql file is src/main/resources folder will create the test data on server-startup
4. POST employee/getEmployeeWithFilters with correct Payload will return the filtered EmployeeData
5. GET employee/getEMployeeWithId?id=1, will return the full Employee information when passed correct ID
