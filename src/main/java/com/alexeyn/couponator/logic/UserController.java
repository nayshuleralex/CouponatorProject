package com.alexeyn.couponator.logic;

import java.util.List;
import java.util.Random;

import com.alexeyn.couponator.cache.ICacheController;
import com.alexeyn.couponator.data.LoggedInUserData;
import com.alexeyn.couponator.data.LoginResponseDataObject;
import com.alexeyn.couponator.entities.User;
import com.alexeyn.couponator.dao.IUserDao;
import com.alexeyn.couponator.enums.ErrorTypes;
import com.alexeyn.couponator.enums.UserType;
import com.alexeyn.couponator.exceptions.ApplicationException;
import com.alexeyn.couponator.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    @Autowired
    private IUserDao userDao;

    @Autowired
    private ICacheController cacheController;


    public long createUser(User user) throws ApplicationException {
        User u = userDao.save(user);
        return u.getUserId();
    }

    public User getUser(long userId) throws ApplicationException {
        return userDao.findById(userId).get();
    }

    public User getUser(String username) throws ApplicationException {
        return userDao.findByUsername(username);
    }

    public List<User> getAllUsers() throws ApplicationException {
        validateUserTable();
        return (List<User>) userDao.findAll();
    }

    public void updateUser(User user) throws ApplicationException {
        userDao.save(user);
    }

    public void deleteUser(long userId) throws ApplicationException {
        userDao.deleteById(userId);
    }

    public LoginResponseDataObject login(String userName, String password) throws ApplicationException {
        LoggedInUserData loggedInUserData = userDao.login(userName, password);
        if (loggedInUserData == null) {
            throw new ApplicationException(ErrorTypes.LOGIN_FAILED, "Failed to login user: " + userName);
        }
        int token = generateToken(userName, loggedInUserData);

        // Save login user data in cache
        cacheController.put(token, loggedInUserData);

        return new LoginResponseDataObject(token, loggedInUserData.getUserType());
    }

    private int generateToken(String userName, LoggedInUserData loggedInUserData) {
        Random rnd = new Random();
        String salt = "#####";
        return (userName + rnd.nextInt(9999999) + salt + loggedInUserData.getUserId()).hashCode();
    }

    private void validateUserTable() throws ApplicationException {
        List<User> userList = (List<User>) userDao.findAll();
        if (userList.isEmpty()) {
            throw new ApplicationException(ErrorTypes.EMPTY_TABLE, DateUtils.getCurrentDateAndTime() +
                    ": User table is empty.");
        }
    }

    private void validateUser(User user) throws ApplicationException {
        validateUserTable();
        List<User> userList = (List<User>) userDao.findAll();
        if (user == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + ": user is null");
        }
        if (user.getUserId() != null) {
            throw new ApplicationException(ErrorTypes.REDUNDANT_DATA,
                    DateUtils.getCurrentDateAndTime() + "Id is redundant");
        }
        if (user.getUsername() == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + ": username is null");
        }
        if (user.getUsername().isEmpty()) {
            throw new ApplicationException(ErrorTypes.EMPTY_DATA,
                    DateUtils.getCurrentDateAndTime() + ": username is empty");
        }
        if (user.getPassword() == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + ": password is null");
        }
        if (user.getPassword().isEmpty()) {
            throw new ApplicationException(ErrorTypes.EMPTY_DATA,
                    DateUtils.getCurrentDateAndTime() + ": password is empty");
        }
        if (isStrongPassword(user.getPassword())) {
            throw new ApplicationException(ErrorTypes.INCORRECT_PASSWORD,
                    DateUtils.getCurrentDateAndTime() + ": Password " + user.getUsername() + " is not strong");
        }
        if (user.getType() == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + ": user type is null");
        }
        if (!UserType.contains(user.getType())) {
            throw new ApplicationException(ErrorTypes.USER_TYPE_DOES_NOT_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": user type " + user.getType() + " doesn't exist");
        }
        if (userList.contains(user)) {
            throw new ApplicationException(ErrorTypes.USER_ALREADY_EXIST,
                    DateUtils.getCurrentDateAndTime() + "User " + user.getUsername() + " already exist");
        }

    }

    private boolean isStrongPassword(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if (password.length() < 6) {
            return false;
        }
        if (!password.matches(pattern)) {
            return false;
        }
        return true;
    }



    /*public boolean isUserExists(long userId) throws ApplicationException {
        if (this.userDao.isUserExist(userId)) {
            return true;
        }
        return false;
    }

    public boolean isUserExistsByName(String username) throws ApplicationException {
        if (this.userDao.isUserExist(username)) {
            return true;
        }
        return false;
    }

    private void ValidateUser(User user) throws ApplicationException {
        if (user == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA, "Null user");
        }
        if (user.getUsername() == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA, "Null username");
        }
        if (user.getUsername().isEmpty()) {
            throw new ApplicationException(ErrorTypes.EMPTY_DATA, "Empty username");
        }
        if (user.getPassword() == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA, "Null password");
        }
        if (user.getPassword().isEmpty()) {
            throw new ApplicationException(ErrorTypes.EMPTY_DATA, "Empty password");
        }
        if (user.getType() == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA, "Null User Type");
        }
        if (user.getId() < 0) {
            throw new ApplicationException(ErrorTypes.INVALID_ID, "Invalid ID");
        }
    }*/

}
