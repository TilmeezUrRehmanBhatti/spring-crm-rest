package com.tilmeez.rest;

import com.tilmeez.entity.Customer;
import com.tilmeez.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

    // auto wire the CustomerService
    @Autowired
    private CustomerService customerService;

    // add mapping for GET / customer
    @GetMapping("/customers")
    public List<Customer> getCustomers(int theSortField) {

        return customerService.getCustomers(theSortField);
    }
}
