package com.alexeyn.couponator.dao;

import com.alexeyn.couponator.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface ICustomerDao extends CrudRepository<Customer, Long> {

    /*public void createCustomer(Customer customer);

    public Customer getCustomer(long customerId);

    public void updateCustomer(Customer customer);

    public void deleteCustomer(long customerId);

    public boolean isCustomerExistsByEmail(String email);

    public boolean isCustomerExistsById(long customerId);*/
}
