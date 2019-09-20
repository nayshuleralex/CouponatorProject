package com.alexeyn.couponator.dao;

import com.alexeyn.couponator.entities.Purchase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPurchaseDao extends CrudRepository<Purchase, Long> {

    @Query("SELECT p FROM Purchase p WHERE p.couponId =: couponId")
    List<Purchase> findAllByCouponId(@Param("couponId") long couponId);

    @Query("SELECT p FROM Purchase p WHERE p.customerId =: customerId")
    List<Purchase> findAllByCustomerId(@Param("customerId") long customerId);
}