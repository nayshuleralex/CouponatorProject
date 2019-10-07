package com.alexeyn.couponator.api;

import java.util.List;

import com.alexeyn.couponator.data.LoginResponseDataObject;
import com.alexeyn.couponator.data.UserLoginDetailsDataObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alexeyn.couponator.entities.User;
import com.alexeyn.couponator.exceptions.ApplicationException;
import com.alexeyn.couponator.logic.UserController;

@RestController
@RequestMapping("/users")
public class UserApi {
	
	@Autowired
	private UserController userController;

	// method = POST	url = http://localhost:8080/users
	@PostMapping
	public long createUser(@RequestBody User user) throws ApplicationException {
		return this.userController.createUser(user);
	}

	// method = GET		url = http://localhost:8080/users/2?token=1348440385
	@GetMapping("/{userId}")
	public User getUser(@PathVariable("userId") long id) throws ApplicationException {
		return this.userController.getUser(id);
	}

	// method = GET		url = http://localhost:8080/users
	@GetMapping
	public List<User> getAllUsers() throws ApplicationException {
		return this.userController.getAllUsers();
	}

	// method = GET		url = http://localhost:8080/purchases/byUserName?username=user
	@GetMapping("/byUserName")
	public User getUser(@RequestParam("username") String username) throws ApplicationException {
		return this.userController.getUser(username);
	}

	// method = PUT		url = http://localhost:8080/users
	@PutMapping
	public void updateUser(@RequestBody User user) throws ApplicationException {
		this.userController.updateUser(user);
	}

	// method = DELETE		url = http://localhost:8080/users
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") long id) throws ApplicationException {
		this.userController.deleteUser(id);
	}

	@PostMapping("/login") // url = http://localhost:8080/users/login?token=????
	public LoginResponseDataObject login(@RequestBody UserLoginDetailsDataObject userLoginDetails) throws ApplicationException {
		return this.userController.login(userLoginDetails.getUsername(), userLoginDetails.getPassword());
	}

	/*public boolean isUserExist(String userName) throws ApplicationException {
		return this.userController.isUserExistsByName(userName);
	}
	
	public boolean isUserExist(long id) throws ApplicationException {
		return this.isUserExist(id);
	}*/
}
