package com.alexeyn.couponator.logic;

import java.util.List;

import com.alexeyn.couponator.entities.Coupon;
import com.alexeyn.couponator.dao.ICouponDao;
import com.alexeyn.couponator.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CouponController {

	@Autowired
	private ICouponDao couponDao;

	public long createCoupon(Coupon coupon) throws ApplicationException {
		Coupon c = couponDao.save(coupon);
		return c.getCouponId();
	}

	public Coupon getCoupon(long couponId) throws ApplicationException {
		return couponDao.findById(couponId).get();
	}

	public List<Coupon> getAllCoupons() throws ApplicationException {
		return (List<Coupon>) couponDao.findAll();
	}

	public List<Coupon> getAllCoupons(long companyId) throws ApplicationException {
		return (List<Coupon>) couponDao.findAllByCompanyId(companyId);
	}



	public void updateCoupon(Coupon coupon) throws ApplicationException {
		couponDao.save(coupon);
	}

	public void deleteCoupon(long couponId) throws ApplicationException {
		couponDao.deleteById(couponId);
	}

	/*public List<Coupon> getAllCoupons(long companyId) throws ApplicationException {
		return (List<Coupon>) couponDao.findAllById(companyId);
	}*/

	/*private boolean isCouponTableExist() throws ApplicationException {
		if (!this.couponDao.isCouponTableExist()) {
			return false;
		}
		return true;
	}

	public boolean isCouponExist(long couponId) throws ApplicationException {
		if (this.couponDao.isCouponExist(couponId)) {
			return true;
		}
		return false;
	}

	public boolean isCouponPerCompanyExist(long companyId) throws ApplicationException {
		if (this.couponDao.isCouponPerCompanyExist(companyId)) {
			return true;
		}
		return false;
	}

	// Validating Coupon function
	private void ValidateCoupon(Coupon coupon) throws ApplicationException {
		if (coupon == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA, "Coupon is null");
		}
		if (coupon.getTitle() == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA, "Title is null");
		}
		if (coupon.getTitle().isEmpty()) {
			throw new ApplicationException(ErrorTypes.EMPTY_DATA, "Title is empty");
		}
		if (coupon.getDescription() == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA, "Description is null");
		}
		if (coupon.getDescription().isEmpty()) {
			throw new ApplicationException(ErrorTypes.EMPTY_DATA, "Description is empty");
		}
		if (coupon.getPrice() <= 0) {
			throw new ApplicationException(ErrorTypes.INVALID_PRICE, "Invalid Price");
		}
		if (coupon.getAmount() <= 0) {
			throw new ApplicationException(ErrorTypes.INVALID_AMOUNT, "Invalid amount");
		}
		if (coupon.getStartDate() == null || coupon.getEndDate() == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA, "Null dates");
		}
		if (coupon.getStartDate().after(coupon.getEndDate())) {
			throw new ApplicationException(ErrorTypes.INVALID_DATES, "StartDate is after EndDate");
		}
		if (coupon.getType() == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA, "Null or Empty Type");
		}
		if (coupon.getCompanyId() < 0) {
			throw new ApplicationException(ErrorTypes.INVALID_ID, "Invalid CompanyID");
		}
		if (coupon.getId() < 0) {
			throw new ApplicationException(ErrorTypes.INVALID_ID, "Invalid CouponID");
		}
	}*/
}
