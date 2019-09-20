package com.alexeyn.couponator.logic;

import java.util.List;

import com.alexeyn.couponator.entities.User;
import com.alexeyn.couponator.dao.IUserDao;
import com.alexeyn.couponator.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    @Autowired
    private IUserDao userDao;


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
        return (List<User>) userDao.findAll();
    }

    public void updateUser(User user) throws ApplicationException {
        userDao.save(user);
    }

    public void deleteUser(long userId) throws ApplicationException {
        userDao.deleteById(userId);
    }

/*    public boolean isUserExists(long userId) throws ApplicationException {
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
