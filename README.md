## Spring REST - API Design Best Practices


**REST API Design**

+ For real-time projects, who will use your API?
+ Also, how will they use your API?
+ Design the API based on requirements


**API Design Process**

1. Review API requirements
2. Identify main resource/entity
3. Use HTTP methods to assign action on resource


_Step 1:Review API Requirements_
+ Create REST API for the
    + Custom Relation Management (CRM) system

+ REST clients should be able to
    + Get a list of customers
    + Get a single customer by id
    + Add a new customer
    + Update a customer
    + Delete a customer

_Step 2:Identify main resource / entity_

+ To identify main resource / entity, look for the most prominent "noun"
+ For our project, it is "customer"
+ Convention is to use plural form of resource / entity**customer**
    + /api/customers

_Step 3:Use HTTP methods to assign action on resource_

| HTTP Method | CRUD Action                              |
| ----------- | ---------------------------------------- |
| POST        | Create a new entity                      |
| GET         | Read a list of entities or single entity |
| PUT         | Update an existing entity                |
| DELETE      | Delete an existing entity                |


**CRUD Endpoint Examples**

| HTTP Method | Endpoint                    | CRUD Action                  |
| ----------- | --------------------------- | ---------------------------- |
| POST        | /api/customers              | Create a new customers          |
| GET         | /api/customers              | Read a list of customers     |
| GET         | /api/customers/{customerId} | Read a list of single customer |
| PUT         | /api/customers              | Update an existing customers    |
| DELETE      | /api/customers/{customerId} | Delete an existing customers    |


+ For POST and PUT, we will send customer data as JSON in request message body


**Anti-Patterns**

+ DO NOT DO THIS ... these are REST anti-patterns, bad practice
    + /api/customersList
    + /api/deleteCustomers
    + /api/addcCustomers
    + /api/updateCustomers

+ Instead, use HTTP methods to assign actions

## Spring REST - CRUD Database Real-Time Project

**Application Architecture**

<img src="https://user-images.githubusercontent.com/80107049/192119878-557d40d4-aba3-47a2-be96-24f2e4696191.png" width="500" />


**Development Process**

1. Get customers
2. Get single customer by ID
3. Add a new customer
4. Update an existing customer
5. Delete an existing customer

