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
        validateTable();
        validateUserId(user.getUserId(), false);
        validateUser(user);
        validateUserDoesNotExist(userDao.findUserByUsername(user.getUsername()));
        return userDao.save(user).getUserId();
    }

    public User getUser(Long userId) throws ApplicationException {
        validateTable();
        validateUserExist(userId);
        return userDao.findById(userId).get();
    }

    public User getUser(String username) throws ApplicationException {
        validateTable();
        validateUserExist(userDao.findUserByUsername(username).getUserId());
        return userDao.findUserByUsername(username);
    }

    public List<User> getAllUsers() throws ApplicationException {
        validateTable();
        return (List<User>) userDao.findAll();
    }

    public void updateUser(User user) throws ApplicationException {
//        validateTable();
        validateUser(user);
        validateUserId(user.getUserId(), true);
        validateUserExist(user.getUserId());
        validateUpdateUser(user);
        userDao.save(user);
    }

    public void deleteUser(long userId) throws ApplicationException {
        validateTable();
        validateUserExist(userId);
        userDao.deleteById(userId);
    }

    public LoginResponseDataObject login(String username, String password) throws ApplicationException {
        User user = userDao.login(username, password);
        LoggedInUserData loggedInUserData = new LoggedInUserData(user.getType(), user.getCompanyId(), user.getUserId());
        if (loggedInUserData.getUserId() == null) {
            throw new ApplicationException(ErrorTypes.LOGIN_FAILED, "Failed to login user: " + username);
        }
        int token = generateToken(username, loggedInUserData);

        loggedInUserData.setToken(token);
        // Save login user data in cache
        cacheController.put(token, loggedInUserData);

        return new LoginResponseDataObject(token, loggedInUserData.getUserType());
    }

    private int generateToken(String username, LoggedInUserData loggedInUserData) {
        Random rnd = new Random();
        String salt = "#####";
        return (username + rnd.nextInt(9999999) + salt + loggedInUserData.getUserId()).hashCode();
    }

    private void validateTable() throws ApplicationException {
        if (userDao.findAll() == null) {
            throw new ApplicationException(ErrorTypes.EMPTY_TABLE,
                    DateUtils.getCurrentDateAndTime() + ": User table is empty.");
        }
    }

    private void validateUserId(Long userId, boolean isRequired) throws ApplicationException {
        if (isRequired) {
            if (userId == null) {
                throw new ApplicationException(ErrorTypes.NULL_DATA,
                        DateUtils.getCurrentDateAndTime() + ": UserId is not supplied");
            }
        } else {
            if (userId != null) {
                throw new ApplicationException(ErrorTypes.REDUNDANT_DATA,
                        DateUtils.getCurrentDateAndTime() + "Id is redundant");
            }
        }
    }

    private void validateUserExist(Long userId) throws ApplicationException {
        if (!userDao.findById(userId).isPresent()) {
            throw new ApplicationException(ErrorTypes.USER_DOES_NOT_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": User doesn't exist");
        }
    }

    private void validateUserDoesNotExist(User user) throws ApplicationException {
        if (user != null) {
            throw new ApplicationException(ErrorTypes.USER_ALREADY_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": User already exist");
        }
    }

    private void validateUpdateUser(User updatedUser) throws ApplicationException {
        User currentUser = userDao.findById(updatedUser.getUserId()).get();
        if (currentUser.equals(updatedUser)) {
            throw new ApplicationException(ErrorTypes.UPDATE_FAILED,
                    DateUtils.getCurrentDateAndTime() + ": No difference found between old and new data");
        }
    }

    private void validateUser(User user) throws ApplicationException {
        validateTable();
        List<User> userList = (List<User>) userDao.findAll();
        if (user == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + ": user is null");
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
        if (!isStrongPassword(user.getPassword())) {
            throw new ApplicationException(ErrorTypes.INCORRECT_PASSWORD,
                    DateUtils.getCurrentDateAndTime() + ": Password " + user.getPassword() + " is not strong");
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
        return password.matches(pattern);
    }
}
