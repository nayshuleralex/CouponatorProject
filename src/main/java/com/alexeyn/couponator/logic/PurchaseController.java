package com.alexeyn.couponator.logic;

import java.util.List;

import com.alexeyn.couponator.data.LoggedInUserData;
import com.alexeyn.couponator.data.PurchaseDataObject;
import com.alexeyn.couponator.entities.Coupon;
import com.alexeyn.couponator.entities.Customer;
import com.alexeyn.couponator.entities.Purchase;
import com.alexeyn.couponator.dao.IPurchaseDao;
import com.alexeyn.couponator.entities.User;
import com.alexeyn.couponator.enums.ErrorTypes;
import com.alexeyn.couponator.enums.UserType;
import com.alexeyn.couponator.exceptions.ApplicationException;
import com.alexeyn.couponator.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;

@Controller
public class PurchaseController {
    @Autowired
    private IPurchaseDao purchaseDao;
    @Autowired
    private CouponController couponController;
    @Autowired
    private CustomerController customerController;


    @Transactional
    public void createPurchase(PurchaseDataObject purchaseData, LoggedInUserData loggedInUserData) throws ApplicationException {
        validatePurchase(purchaseData);
        validateLoggedInUserData(loggedInUserData);
//        validatePurchaseId(purchase.getPurchaseId(), false);
        Purchase purchase = createPurchaseFromPurchaseDataObject(purchaseData, loggedInUserData);
        purchaseDao.save(purchase);
    }

    public Purchase getPurchase(long purchaseId) throws ApplicationException {
        validateTable();
        validatePurchaseExist(purchaseId);
        return purchaseDao.findById(purchaseId).get();
    }

    public List<Purchase> getAllPurchases() throws ApplicationException {
        return (List<Purchase>) purchaseDao.findAll();
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

    @Transactional
    private Purchase createPurchaseFromPurchaseDataObject(PurchaseDataObject purchaseData, LoggedInUserData userData) throws ApplicationException {
        Coupon coupon = couponController.getCoupon(purchaseData.getCouponId());
        long couponId = coupon.getCouponId();
        int currentAmountOfCoupons = coupon.getAmount();
        int amountOfCouponsToBuy = purchaseData.getAmount();
        int updatedCouponAmount = (currentAmountOfCoupons - amountOfCouponsToBuy);
        couponController.updateAmountOfCoupons(couponId, updatedCouponAmount);

        Customer customer = new Customer();
        User user = new User();
        user.setUserId(userData.getUserId());
        customer = this.customerController.getCustomer(user.getUserId());
        return new Purchase(amountOfCouponsToBuy, coupon, customer);
    }

    private void validateTable() throws ApplicationException {
        if (purchaseDao.findTableSize() == 0) {
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

    private void validateLoggedInUserData(LoggedInUserData loggedInUserData) throws ApplicationException {
        if (loggedInUserData == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + "loggedInUserData is empty");
        }
        if (loggedInUserData.getUserId() == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + "userId is Null");
        }
        if (loggedInUserData.getType() == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + "userType is Null");
        }
        if (!loggedInUserData.getType().equals(UserType.CUSTOMER)) {
            throw new ApplicationException(ErrorTypes.INVALID_USER_TYPE,
                    DateUtils.getCurrentDateAndTime() + "UserType: " + loggedInUserData.getType() + " not allowed to purchase");
        }
        if (loggedInUserData.getToken() == null) {
            // Or hacking attempt?
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + "token is null");
        }
        if (loggedInUserData.getToken().isEmpty()) {
            // Or hacking attempt?
            throw new ApplicationException(ErrorTypes.EMPTY_DATA,
                    DateUtils.getCurrentDateAndTime() + "token is empty");
        }
        if (loggedInUserData.getCompanyId() != null) {
            throw new ApplicationException(ErrorTypes.REDUNDANT_DATA,
                    DateUtils.getCurrentDateAndTime() + "User has companyId");
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

    private void validatePurchase(PurchaseDataObject purchase) throws ApplicationException {
        if (purchase == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + ":Purchase is null");
        }
        if (purchase.getAmount() <= 0) {
            throw new ApplicationException(ErrorTypes.INVALID_AMOUNT,
                    DateUtils.getCurrentDateAndTime() + ": Purchase has invalid Amount");
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
