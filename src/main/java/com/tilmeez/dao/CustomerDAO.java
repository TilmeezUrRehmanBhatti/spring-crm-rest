package com.tilmeez.dao;

import com.tilmeez.entity.Customer;

import java.util.List;

public interface CustomerDAO {

    public List<Customer> getCustomers(int theSortField);

    public void saveCustomer(Customer theCustomer);

    public Customer getCustomer(int theId);

    public void deleteCustomer(int theId);

    public List<Customer> searchCustomers(String theSearchName);

    public List<Customer> getCustomers();
}
