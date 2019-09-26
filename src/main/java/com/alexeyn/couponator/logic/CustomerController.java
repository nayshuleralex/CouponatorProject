package com.alexeyn.couponator.logic;

import java.util.List;

import com.alexeyn.couponator.entities.Customer;
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
		validateCustomer(customer);
		validateCustomerId(customer.getCustomerId(), false);
		validateCustomerDoesNotExist(customerDao.findCustomerByEmail(customer.getEmail()));
		long userId = userController.createUser(customer.getUser());
		customer.setCustomerId(userId);
		return customerDao.save(customer).getCustomerId();
	}

	public Customer getCustomer(long customerId) throws ApplicationException {
		validateTable();
		validateCustomerExist(customerId);
		return customerDao.findById(customerId).get();
	}

	public List<Customer> getAllCustomers() throws ApplicationException {
		return (List<Customer>) customerDao.findAll();
	}

	public void updateCustomer(Customer customer) throws ApplicationException {
		validateTable();
		validateCustomer(customer);
		validateCustomerId(customer.getCustomerId(), true);
		validateCustomerExist(customer.getCustomerId());
		validateUpdateCustomer(customer);
		customerDao.save(customer);
	}

	public void deleteCustomer(long customerId) throws ApplicationException {
		validateTable();
		validateCustomerExist(customerId);
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

	private void validateCustomerDoesNotExist(Customer customer) throws ApplicationException {
		if (customer == null) {
			throw new ApplicationException(ErrorTypes.CUSTOMER_ALREADY_EXIST,
					DateUtils.getCurrentDateAndTime() + ": Customer already exist");
		}
	}

	private void validateUpdateCustomer(Customer updatedCustomer) throws ApplicationException {
		Customer currentCustomer = customerDao.findById(updatedCustomer.getCustomerId()).get();
		if (currentCustomer.equals(updatedCustomer)) {
			throw new ApplicationException(ErrorTypes.UPDATE_FAILED,
					DateUtils.getCurrentDateAndTime() + ": No difference found between old and new data");
		}
	}

	private void validateCustomer(Customer customer) throws ApplicationException {
		if (customer == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA,
					DateUtils.getCurrentDateAndTime() + ": Customer is null");
		}
		if (customer.getFirstName() == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA,
					DateUtils.getCurrentDateAndTime() + ": Null first name");
		}
		if (customer.getFirstName().isEmpty()) {
			throw new ApplicationException(ErrorTypes.EMPTY_DATA,
					DateUtils.getCurrentDateAndTime() + ": Empty first name");
		}
		if (customer.getLastName() == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA,
					DateUtils.getCurrentDateAndTime() + ": Null last name");
		}
		if (customer.getLastName().isEmpty()) {
			throw new ApplicationException(ErrorTypes.EMPTY_DATA,
					DateUtils.getCurrentDateAndTime() + ": Empty last name");
		}
		if (customer.getEmail() == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA,
					DateUtils.getCurrentDateAndTime() + ": Null email");
		}
		if (customer.getEmail().isEmpty()) {
			throw new ApplicationException(ErrorTypes.EMPTY_DATA,
					DateUtils.getCurrentDateAndTime() + ": Empty email");
		}
	}

	boolean isCustomerExist(Customer customer) throws ApplicationException {
		validateCustomerId(customer.getCustomerId(), true);
		return customerDao.findById(customer.getCustomerId()).isPresent();
	}
}
