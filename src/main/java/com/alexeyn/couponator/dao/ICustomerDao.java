package com.alexeyn.couponator.dao;

import com.alexeyn.couponator.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ICustomerDao extends CrudRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Customer findCustomerByEmail(@Param("email") String email);

    /*public void createCustomer(Customer customer);

    public Customer getCustomer(long customerId);

    public void updateCustomer(Customer customer);

    public void deleteCustomer(long customerId);

    public boolean isCustomerExistsByEmail(String email);

    public boolean isCustomerExistsById(long customerId);*/
}
