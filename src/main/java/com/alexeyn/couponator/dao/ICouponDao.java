package com.alexeyn.couponator.dao;

import com.alexeyn.couponator.entities.Coupon;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ICouponDao extends CrudRepository<Coupon, Long> {

//    @Query("SELECT c FROM Coupon c WHERE c.companyId = :companyId")
//    List<Coupon> findAllByCompanyId(@Param("companyId") long companyId);

    @Query("SELECT c FROM Coupon c WHERE c.title = :title")
    Coupon findCouponByTitle(@Param("title") String title);

    @Query("SELECT COUNT(*) FROM Coupon c")
    Long findTableSize();

    @Query("DELETE FROM Coupon c WHERE c.endDate <= :date")
    void deleteAllExpiredCoupons(@Param("date") Date date);

    @Modifying
    @Query("UPDATE Coupon c SET c.amount=:amount WHERE c.couponId=:couponId")
    void updateAmountOfCoupons(@Param("couponId") long couponId, @Param("amount") int amount);
}
