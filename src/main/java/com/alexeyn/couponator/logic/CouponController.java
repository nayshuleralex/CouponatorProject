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

    public long createCoupon(Coupon coupon) throws ApplicationException {
    	validateCoupon(coupon);
    	validateCouponId(coupon.getCouponId(), false); // validate provided id is NULL
    	validateCouponDoesNotExist(couponDao.findCouponId(coupon.getTitle()));
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
        return (List<Coupon>) couponDao.findAllByCompanyId(companyId);
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

    private void validateCouponExist(Long couponId) throws ApplicationException {
    	if (!couponDao.findById(couponId).isPresent()) {
    		throw new ApplicationException(ErrorTypes.COUPON_DOES_NOT_EXIST,
					DateUtils.getCurrentDateAndTime() + ": Coupon doesn't exist");
		}
    }

    private void validateCouponDoesNotExist(Long couponId) throws ApplicationException {
		if (couponDao.findById(couponId).isPresent()) {
			throw new ApplicationException(ErrorTypes.COUPON_ALREADY_EXIST,
					DateUtils.getCurrentDateAndTime() + ": Coupon already exist");
		}
    }

	private void validateUpdateCoupon(Coupon updatedCoupon) throws ApplicationException {
		Coupon currentCoupon = couponDao.findById(updatedCoupon.getCompanyId()).get();
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
