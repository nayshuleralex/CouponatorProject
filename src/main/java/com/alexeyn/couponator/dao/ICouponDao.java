package com.alexeyn.couponator.dao;

import com.alexeyn.couponator.entities.Coupon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICouponDao extends CrudRepository<Coupon, Long> {

    @Query("SELECT c FROM Coupon c WHERE c.companyId =: companyId")
    List<Coupon> findAllByCompanyId(long companyId);

}
