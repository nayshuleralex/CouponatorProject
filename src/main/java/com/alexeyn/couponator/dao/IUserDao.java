package com.alexeyn.couponator.dao;

import com.alexeyn.couponator.data.LoggedInUserData;
import com.alexeyn.couponator.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IUserDao extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.username = :username and u.password = :password")
    User login(@Param("username") String username, @Param("password") String password);

    @Query("SELECT COUNT(*) FROM User u")
    Long findTableSize();
}
