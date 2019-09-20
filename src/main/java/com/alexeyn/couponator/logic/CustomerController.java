package com.alexeyn.couponator.logic;

import java.util.List;

import com.alexeyn.couponator.entities.Customer;
import com.alexeyn.couponator.entities.User;
import com.alexeyn.couponator.dao.ICustomerDao;
import com.alexeyn.couponator.enums.ErrorTypes;
import com.alexeyn.couponator.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {
	@Autowired
	private ICustomerDao customerDao;
	@Autowired
	private UserController userController;

	public long createCustomer(Customer customer) throws ApplicationException {
		long userId = userController.createUser(customer.getUser());
		customer.setId(userId);
		Customer c = customerDao.save(customer);
		return c.getId();
	}

	public Customer getCustomer(long customerId) throws ApplicationException {
		return customerDao.findById(customerId).get();
	}

	public List<Customer> getAllCustomers() throws ApplicationException {
		return (List<Customer>) customerDao.findAll();
	}

	public void updateCustomer(Customer customer) throws ApplicationException {
		customerDao.save(customer);
	}

	public void deleteCustomer(long customerId) throws ApplicationException {
		customerDao.deleteById(customerId);
	}

	/*public boolean isCustomerExist(String email) throws ApplicationException {
		if (this.customerDao.isCustomerExist(email)) {
			return true;
		}
		return false;
	}

	public boolean isCustomerExist(long customerId) throws ApplicationException {
		if (this.customerDao.isCustomerExist(customerId)) {
			return true;
		}
		return false;
	}

	public boolean isCustomerTableExist() throws ApplicationException {
		if (this.customerDao.isCustomerTableExist()) {
			return true;
		}
		return false;
	}

	private void ValidateCustomer(Customer customer) throws ApplicationException {
		if (customer.getFirstName() == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA, "Null user");
		}
		if (customer.getFirstName().isEmpty()) {
			throw new ApplicationException(ErrorTypes.EMPTY_DATA, "Empty user");
		}
		if (customer.getEmail() == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA, "Null email");
		}
		if (customer.getEmail().isEmpty()) {
			throw new ApplicationException(ErrorTypes.EMPTY_DATA, "Empty email");
		}
	}*/
}
