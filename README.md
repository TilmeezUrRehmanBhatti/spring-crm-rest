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

### Get Customer

**Development Process Customer REST Controller**
1. Create Customer REST Controller
2. Autowire CustomerService
3. Add mapping for GET / customers

_Step 1:Create Customer REST Controller_

File:CustomerRestController.java
```JAVA
@RestController
@RequestMapping("/api")
public class CustomerRestController {
  
}
```

_Step 2:Autowire CustomerService_

File:CustomerRestController.java
```JAVA
@RestController
@RequestMapping("/api")
public class CustomerRestController {
  
  // autowire the CustomerService
  @Autowired
  private CustomerService customerService;
  
}
```

_Step 3:Add mapping for GET / customers_
File:CustomerRestController.java
```JAVA
@RestController
@RequestMapping("/api")
public class CustomerRestController {
  
  // autowire the CustomerService
  @Autowired
  private CustomerService customerService;
  
  // add mapping for GET / customers
  @GetMapping("/customers")
  public List<Customer> getCustomers() {
    
    return customerService.getCustomers();
  }
  
}
```

**Exception Handling **


<img src="https://user-images.githubusercontent.com/80107049/192348123-1db63d2a-b385-4937-b644-d94ea819e579.png" width="500" />


**Development Process**
1. Create a customer error response class
2. Create a customer exception class
3. Update REST service to throw exception if customer not found
4. Add an exception handler methods using @ExceptionHandler


**Add Customer - Overview**

**Access Request Body**
+ Jackson will convert request body from JSON to POJO
+ @RequestBody annotation binds the POJO to a method parameter

```JAVA 
@PostMapping("/customers")
public Customer addCustomer(@RequestBody Customer theCustomer) {
 ... 
}
```
+ `@RequestBody Customer theCustomer` we can access the request body as a POJO

_Add Customer_
File:CustomerRestController.java
```JAVA
@RestController
@RequestMapping("/api")
public class CUstomerRestController {
  
 ...
   
  // add mapping for POST/customers - add new customer
  @PostMapping("/customers")
  public Customer addCustomer(@RequestBody Customer theCustomer) {
    
    theCustomer.setId(0);
    
    customerService.saveCustomer(theCustomer);
    
    return theCustomer;
  }
}
```

+ `customerService.saveCustomer(theCustomer);` Delegate call to customer service
+ `return theCustomer;` Return updated customer object

**With `theCustomer.setId(0);`**
+ In REST controller, we explicitly set the customer id to 0
+ Because our backend DAO code uses Hibernate method
  + session.saveOrUpdate(...)
    + Id(primary key / id) empty then INSERT new customer else UPDATE exiting customer
      + Here: "empty" means null or 0

**Adding customer with HTTP POST**

+ If REST is sending a request to "add", using HTTP POST
+ Then we ignore any id sent in the request
+ We overwrite the id with 0. to effectively set it to null/empty
+ Then our backend DAO code will "INSERT" new customer




**Sending JSON to Spring REST Controllers**

+ When sending JSON data to Spring REST Controller
+ For controller to process JSON data, need to set a HTTP request header
  + Content-type: application/json

+ Need to configure REST client to send the correct HTTP request header



**Update Customer - Overview**

_Update Customer_
File:CustomerRestController.java
```JAVA
@RestController
@RequestMapping("/api")
public class CUstomerRestController {
  
 ...
   
  // add mapping for PUT/customers - update existing customer
  @PutMapping("/customers")
  public Customer updateCustomer(@RequestBody Customer theCustomer) {
    
    customerService.saveCustomer(theCustomer);
    
    return theCustomer;
  }
}
```


**Delete Customer - Overview**

_Delete Customer_
File:CustomerRestController.java
```JAVA
@RestController
@RequestMapping("/api")
public class CUstomerRestController {
  
 ...
   
  // add mapping for DELETE/customers - delete customer
  @DeleteMapping("/customers/{customerId}")
  public Customer updateCustomer(@PathVariable int customerId) {
   
   Customer tempCustomer = customerService.getCustomer(customerId);
   
   // throw exception if null
   if (tempCustomer == null) {
     throw new CustomerNotFoundException("Customer id not found - " + customerId);
   }
    customerService.deleteCustomer(customerId);
    
    return "Delete customer id - " + customerId;
  }
}
```