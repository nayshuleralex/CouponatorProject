package com.alexeyn.couponator.api;

import com.alexeyn.couponator.entities.Customer;
import com.alexeyn.couponator.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alexeyn.couponator.logic.CustomerController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerApi {

    @Autowired
    private CustomerController customerController;

    // method = POST	url = http://localhost:8080/customers
    @PostMapping
    public long createCustomer(@RequestBody Customer customer) throws ApplicationException {
        return this.customerController.createCustomer(customer);
    }

    // method = GET 	url = http://localhost:8080/customers/42
    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable("customerId") long id) throws ApplicationException {
        return this.customerController.getCustomer(id);
    }

    // method = GET 	url = http://localhost:8080/customers
    @GetMapping
    public List<Customer> getAllCustomers() throws ApplicationException {
        return this.customerController.getAllCustomers();
    }

    // method = PUT 	url = http://localhost:8080/customers
    @PutMapping
    public void updateCustomer(@RequestBody Customer customer) throws ApplicationException {
        this.customerController.updateCustomer(customer);
    }

    // method = DELETE	url = http://localhost:8080/customers/42
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") long id) throws ApplicationException {
        this.customerController.deleteCustomer(id);
    }
}
