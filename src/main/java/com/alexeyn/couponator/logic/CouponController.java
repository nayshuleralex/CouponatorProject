package com.alexeyn.couponator.logic;

import java.util.List;

import com.alexeyn.couponator.entities.Coupon;
import com.alexeyn.couponator.dao.ICouponDao;
import com.alexeyn.couponator.enums.CouponsCategories;
import com.alexeyn.couponator.enums.ErrorTypes;
import com.alexeyn.couponator.exceptions.ApplicationException;
import com.alexeyn.couponator.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CouponController {

    @Autowired
    private ICouponDao couponDao;
    @Autowired
    private CompanyController companyController;

    public long createCoupon(Coupon coupon) throws ApplicationException {
    	validateCoupon(coupon);
    	validateCouponId(coupon.getCouponId(), false); // validate provided id is NULL
    	validateCouponDoesNotExist(couponDao.findCouponByTitle(coupon.getTitle()));
        return couponDao.save(coupon).getCouponId();
    }

    public Coupon getCoupon(long couponId) throws ApplicationException {
    	validateTable();
    	validateCouponExist(couponId);
        return couponDao.findById(couponId).get();
    }

    public List<Coupon> getAllCoupons() throws ApplicationException {
        return (List<Coupon>) couponDao.findAll();
    }

    public List<Coupon> getAllCoupons(long companyId) throws ApplicationException {
    	validateTable();
        return couponDao.findAllByCompanyId(companyId);
    }

    public void updateCoupon(Coupon coupon) throws ApplicationException {
    	validateTable();
    	validateCoupon(coupon);
    	validateCouponId(coupon.getCouponId(), true);
    	validateCouponExist(coupon.getCouponId());
    	validateUpdateCoupon(coupon);
        couponDao.save(coupon);
    }

    public void deleteCoupon(long couponId) throws ApplicationException {
    	validateTable();
    	validateCouponExist(couponId);
        couponDao.deleteById(couponId);
    }


    private void validateTable() throws ApplicationException {
        if (couponDao.findAll() == null) {
            throw new ApplicationException(ErrorTypes.EMPTY_TABLE,
                    DateUtils.getCurrentDateAndTime() + ": Coupon Table is empty");
        }
    }

    private void validateCouponId(Long couponId, boolean isRequired) throws ApplicationException {
        if (isRequired) {
            if (couponId == null) {
                throw new ApplicationException(ErrorTypes.NULL_DATA,
                        DateUtils.getCurrentDateAndTime() + ": CouponId is not supplied");
            }
        } else {
            if (couponId != null) {
                throw new ApplicationException(ErrorTypes.REDUNDANT_DATA,
                        DateUtils.getCurrentDateAndTime() + ": Id is redundant");
            }
        }
	}

    public void validateCouponExist(Long couponId) throws ApplicationException {
    	if (!couponDao.findById(couponId).isPresent()) {
    		throw new ApplicationException(ErrorTypes.COUPON_DOES_NOT_EXIST,
					DateUtils.getCurrentDateAndTime() + ": Coupon doesn't exist");
		}
    }

    private void validateCouponDoesNotExist(Coupon coupon) throws ApplicationException {
		if (coupon != null) {
			throw new ApplicationException(ErrorTypes.COUPON_ALREADY_EXIST,
					DateUtils.getCurrentDateAndTime() + ": Coupon already exist");
		}
    }

	private void validateUpdateCoupon(Coupon updatedCoupon) throws ApplicationException {
		Coupon currentCoupon = couponDao.findById(updatedCoupon.getCouponId()).get();
		if (currentCoupon.equals(updatedCoupon)) {
			throw new ApplicationException(ErrorTypes.UPDATE_FAILED,
					DateUtils.getCurrentDateAndTime() + ": No difference found between old and new data");
		}
	}

    private void validateCoupon(Coupon coupon) throws ApplicationException {
        if (coupon == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + ": Coupon is null");
        }
        if (coupon.getTitle() == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + ": Title is null");
        }
        if (coupon.getTitle().isEmpty()) {
            throw new ApplicationException(ErrorTypes.EMPTY_DATA,
                    DateUtils.getCurrentDateAndTime() + ": Title is empty");
        }
        if (coupon.getDescription() == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + ": Description is null");
        }
        if (coupon.getDescription().isEmpty()) {
            throw new ApplicationException(ErrorTypes.EMPTY_DATA,
                    DateUtils.getCurrentDateAndTime() + ": Description is empty");
        }
        if (coupon.getPrice() <= 0) {
            throw new ApplicationException(ErrorTypes.INVALID_PRICE,
                    DateUtils.getCurrentDateAndTime() + ": Invalid Price");
        }
        if (coupon.getAmount() <= 0) {
            throw new ApplicationException(ErrorTypes.INVALID_AMOUNT,
                    DateUtils.getCurrentDateAndTime() + ": Invalid amount");
        }
        if (coupon.getStartDate() == null || coupon.getEndDate() == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + ": Null date(s)");
        }
        if (coupon.getStartDate().after(coupon.getEndDate())) {
            throw new ApplicationException(ErrorTypes.INVALID_DATES,
                    DateUtils.getCurrentDateAndTime() + ": StartDate is after EndDate");
        }
        if (coupon.getType() == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + ": Null or Empty Type");
        }
        if (!CouponsCategories.contains(coupon.getType())) {
            throw new ApplicationException(ErrorTypes.COUPON_TYPE_DOES_NOT_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": Coupon type " + coupon.getType() + " doesn't exist");
        }
        if (coupon.getCompanyId() == null) {
            throw new ApplicationException(ErrorTypes.INVALID_ID,
                    DateUtils.getCurrentDateAndTime() + ": Invalid CompanyID");
        }
        if (!companyController.isCompanyExist(coupon.getCompanyId())) {
            throw new ApplicationException(ErrorTypes.COMPANY_DOES_NOT_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": Invalid CompanyID");
        }
    }

    boolean isCouponExist(Coupon coupon) throws ApplicationException {
        validateCouponId(coupon.getCouponId(), true);
        List<Coupon> coupons = (List<Coupon>) couponDao.findAll();
        return coupons.contains(coupon);
    }
}
