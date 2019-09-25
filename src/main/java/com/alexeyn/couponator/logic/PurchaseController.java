package com.alexeyn.couponator.logic;

import java.util.List;

import com.alexeyn.couponator.entities.Coupon;
import com.alexeyn.couponator.entities.Customer;
import com.alexeyn.couponator.entities.Purchase;
import com.alexeyn.couponator.dao.IPurchaseDao;
import com.alexeyn.couponator.enums.ErrorTypes;
import com.alexeyn.couponator.exceptions.ApplicationException;
import com.alexeyn.couponator.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PurchaseController {
    @Autowired
    private IPurchaseDao purchaseDao;
    @Autowired
    private CouponController couponController;
    @Autowired
    private CustomerController customerController;


    public long createPurchase(Purchase purchase) throws ApplicationException {
        validatePurchase(purchase);
        validatePurchaseId(purchase.getPurchaseId(), false);
        return purchaseDao.save(purchase).getPurchaseId();
    }

    public Purchase getPurchase(long purchaseId) throws ApplicationException {
        validateTable();
        validatePurchaseExist(purchaseId);
        return purchaseDao.findById(purchaseId).get();
    }

    public List<Purchase> getAllPurchases() throws ApplicationException {
        return (List<Purchase>) purchaseDao.findAll();
    }

    public List<Purchase> getAllCouponPurchases(Coupon coupon) throws ApplicationException {
        validateCouponExist(coupon);
        return purchaseDao.findAllByCouponId(coupon.getCouponId());
    }

   public List<Purchase> getAllCustomerPurchases(Customer customer) throws ApplicationException {
        validateCustomerExist(customer);
        return purchaseDao.findAllByCustomerId(customer.getCustomerId());
    }

    public void updatePurchase(Purchase purchase) throws ApplicationException {
        validateTable();
        validatePurchaseId(purchase.getPurchaseId(), true);
        validatePurchaseExist(purchase.getPurchaseId());
        validateUpdatePurchase(purchase);
        purchaseDao.save(purchase);
    }

    public void deletePurchase(long purchaseId) throws ApplicationException {
        validateTable();
        purchaseDao.deleteById(purchaseId);
    }

    private void validateTable() throws ApplicationException {
        if (purchaseDao.findAll() == null) {
            throw new ApplicationException(ErrorTypes.EMPTY_TABLE,
                    DateUtils.getCurrentDateAndTime() + ": Purchase Table is empty");
        }
    }

    private void validatePurchaseId(Long purchaseId, boolean isRequired) throws ApplicationException {
        if (isRequired) {
            if (purchaseId == null) {
                throw new ApplicationException(ErrorTypes.NULL_DATA,
                        DateUtils.getCurrentDateAndTime() + ": purchaseId is not supplied");
            }
        } else {
            if (purchaseId != null) {
                throw new ApplicationException(ErrorTypes.REDUNDANT_DATA,
                        DateUtils.getCurrentDateAndTime() + ": Id is redundant");
            }
        }
    }

    private void validatePurchaseExist(Long purchaseId) throws ApplicationException {
        if (!purchaseDao.findById(purchaseId).isPresent()) {
            throw new ApplicationException(ErrorTypes.PURCHASE_DOES_NOT_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": Purchase doesn't exist");
        }
    }

    private void validatePurchaseDoesNotExist(Long purchaseId) throws ApplicationException {
        if (purchaseDao.findById(purchaseId).isPresent()) {
            throw new ApplicationException(ErrorTypes.PURCHASE_ALREADY_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": Purchase already exist");
        }
    }

    private void validateUpdatePurchase(Purchase updatedPurchase) throws ApplicationException {
        Purchase currentPurchase = purchaseDao.findById(updatedPurchase.getPurchaseId()).get();
        if (currentPurchase.equals(updatedPurchase)) {
            throw new ApplicationException(ErrorTypes.UPDATE_FAILED,
                    DateUtils.getCurrentDateAndTime() + ": No difference found between old and new data");
        }
    }

    private void validatePurchase(Purchase purchase) throws ApplicationException {
        if (purchase == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + ":Purchase is null");
        }
        if (purchase.getAmount() <= 0) {
            throw new ApplicationException(ErrorTypes.INVALID_AMOUNT,
                    DateUtils.getCurrentDateAndTime() + ": Purchase has invalid Amount");
        }
        if (purchase.getCouponId() < 0) {
            throw new ApplicationException(ErrorTypes.INVALID_ID,
                    DateUtils.getCurrentDateAndTime() + ": Purchase has invalid CouponID");
        }
        if (!couponController.isCouponExist(couponController.getCoupon(purchase.getCouponId()))) {
            throw new ApplicationException(ErrorTypes.ID_DOES_NOT_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": CouponID doesn't exist");
        }
        if (purchase.getCustomerId() < 0) {
            throw new ApplicationException(ErrorTypes.INVALID_ID,
                    DateUtils.getCurrentDateAndTime() + ": Purchase has invalid CustomerID");
        }
        if (!customerController.isCustomerExist(customerController.getCustomer(purchase.getCustomerId()))) {
            throw new ApplicationException(ErrorTypes.ID_DOES_NOT_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": CustomerID doesn't exist");
        }
    }

    private void validateCouponExist(Coupon coupon) throws ApplicationException {
        if (!couponController.isCouponExist(coupon)) {
            throw new ApplicationException(ErrorTypes.COUPON_DOES_NOT_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": Coupon doesn't exist");
        }
    }

    private void validateCustomerExist(Customer customer) throws ApplicationException {
        if (!customerController.isCustomerExist(customer)) {
            throw new ApplicationException(ErrorTypes.CUSTOMER_DOES_NOT_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": Customer doesn't exist");
        }
    }
}
