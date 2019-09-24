package com.alexeyn.couponator.logic;

import java.util.List;

import com.alexeyn.couponator.entities.Customer;
import com.alexeyn.couponator.entities.User;
import com.alexeyn.couponator.dao.ICustomerDao;
import com.alexeyn.couponator.enums.ErrorTypes;
import com.alexeyn.couponator.exceptions.ApplicationException;
import com.alexeyn.couponator.utils.DateUtils;
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
		return customerDao.save(customer).getId();
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

	private void validateTable() throws ApplicationException {
		if (customerDao.findAll() == null) {
			throw new ApplicationException(ErrorTypes.EMPTY_TABLE,
					DateUtils.getCurrentDateAndTime() + ": Customer Table is empty");
		}
	}

	private void validateCustomerId(Long customerId, boolean isRequired) throws ApplicationException {
		if (isRequired) {
			if (customerId == null) {
				throw new ApplicationException(ErrorTypes.NULL_DATA,
						DateUtils.getCurrentDateAndTime() + ": customerId is not supplied");
			}
		} else {
			if (customerId != null) {
				throw new ApplicationException(ErrorTypes.REDUNDANT_DATA,
						DateUtils.getCurrentDateAndTime() + ": customerId is redundant");
			}
		}
	}

	private void validateCustomerExist(Long customerId) throws ApplicationException {
		if (!customerDao.findById(customerId).isPresent()) {
			throw new ApplicationException(ErrorTypes.CUSTOMER_DOES_NOT_EXIST,
					DateUtils.getCurrentDateAndTime() + ": customer doesn't exist");
		}
	}

	private void validateCustomerDoesNotExist(Long couponId) throws ApplicationException {
		if (customerDao.findById(couponId).isPresent()) {
			throw new ApplicationException(ErrorTypes.CUSTOMER_ALREADY_EXIST,
					DateUtils.getCurrentDateAndTime() + ": Customer already exist");
		}
	}

	private void validateUpdateCustomer(Customer updatedCustomer) throws ApplicationException {
		Customer currentCustomer = customerDao.findById(updatedCustomer.getId()).get();
		if (currentCustomer.equals(updatedCustomer)) {
			throw new ApplicationException(ErrorTypes.UPDATE_FAILED,
					DateUtils.getCurrentDateAndTime() + ": No difference found between old and new data");
		}
	}

	private void validateCustomer(Customer customer) throws ApplicationException {
		if (customer == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA,
					DateUtils.getCurrentDateAndTime() + ": Coupon is null");
		}
		if (customer.getFirstName() == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA, "Null first name");
		}
		if (customer.getFirstName().isEmpty()) {
			throw new ApplicationException(ErrorTypes.EMPTY_DATA, "Empty first name");
		}
		if (customer.getLastName() == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA, "Null last name");
		}
		if (customer.getLastName().isEmpty()) {
			throw new ApplicationException(ErrorTypes.EMPTY_DATA, "Empty last name");
		}
		if (customer.getEmail() == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA, "Null email");
		}
		if (customer.getEmail().isEmpty()) {
			throw new ApplicationException(ErrorTypes.EMPTY_DATA, "Empty email");
		}
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
