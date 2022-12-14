package com.tilmeez.service;

import com.tilmeez.dao.CustomerDAO;
import com.tilmeez.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    // need to injection customer DAO
    @Autowired
    private CustomerDAO customerDAO;

    @Override
    @Transactional
    public List<Customer> getCustomers(int theSortField) {
        return customerDAO.getCustomers(theSortField); // Delegate calls to DAO
    }

    @Override
    @Transactional
    public void saveCustomer(Customer theCustomer) {

        customerDAO.saveCustomer(theCustomer);
    }

    @Override
    @Transactional
    public Customer getCustomer(int theId) {

        return customerDAO.getCustomer(theId);
    }

    @Override
    @Transactional
    public void deleteCustomer(int theId) {

        customerDAO.deleteCustomer(theId);
    }

    @Override
    @Transactional
    public List<Customer> searchCustomer(String theSearchName) {

        return customerDAO.searchCustomers(theSearchName);
    }

    @Override
    @Transactional
    public List<Customer> getCustomers() {
         return customerDAO.getCustomers();
    }

}













