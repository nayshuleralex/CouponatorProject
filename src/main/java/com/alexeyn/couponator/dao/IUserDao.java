package com.alexeyn.couponator.dao;

import com.alexeyn.couponator.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IUserDao extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username =: username")
    User findByUsername(@Param("username") String username);
}
