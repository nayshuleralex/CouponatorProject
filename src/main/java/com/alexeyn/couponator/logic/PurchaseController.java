package com.alexeyn.couponator.logic;

import java.util.List;

import com.alexeyn.couponator.entities.Coupon;
import com.alexeyn.couponator.entities.Customer;
import com.alexeyn.couponator.entities.Purchase;
import com.alexeyn.couponator.dao.IPurchaseDao;
import com.alexeyn.couponator.exceptions.ApplicationException;
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
        Purchase p = purchaseDao.save(purchase);
        return p.getPurchaseId();
    }

    public Purchase getPurchase(long purchaseId) throws ApplicationException {
        return purchaseDao.findById(purchaseId).get();
    }

    public List<Purchase> getAllPurchases() throws ApplicationException {
        return (List<Purchase>) purchaseDao.findAll();
    }

    public List<Purchase> getAllCouponPurchases(Coupon coupon) throws ApplicationException {
        return purchaseDao.findAllByCouponId(coupon.getCouponId());
    }

   public List<Purchase> getAllCustomerPurchases(Customer customer) throws ApplicationException {
        return (List<Purchase>) purchaseDao.findAllByCustomerId(customer.getId());
    }

    public void updatePurchase(Purchase purchase) throws ApplicationException {
        purchaseDao.save(purchase);
    }

    public void deletePurchase(long purchaseId) throws ApplicationException {
        purchaseDao.deleteById(purchaseId);
    }

    /*public boolean isPurchaseExists(long purchaseId) throws ApplicationException {
        if (this.purchaseDao.isPurchaseExist(purchaseId)) {
            return true;
        }
        return false;
    }

    public boolean isPurchaseTableExist() throws ApplicationException {
        if (this.purchaseDao.isPurchaseTableExist()) {
            return true;
        }
        return false;
    }

    private void ValidatePurchase(Purchase purchase) throws ApplicationException {
        if (purchase.getId() < 0) {
            throw new ApplicationException(ErrorTypes.INVALID_ID, "Purchase has invalid ID");
        }
        if (purchase.getAmount() <= 0) {
            throw new ApplicationException(ErrorTypes.INVALID_AMOUNT, "Purchase has invalid Amount");
        }
        if (purchase.getCouponId() < 0) {
            throw new ApplicationException(ErrorTypes.INVALID_ID, "Purchase has invalid CouponID");
        }
        if (!couponController.isCouponExist(purchase.getCouponId())) {
            throw new ApplicationException(ErrorTypes.ID_DONT_EXIST, "CouponID doesn't exist");
        }
        if (purchase.getCustomerId() < 0) {
            throw new ApplicationException(ErrorTypes.INVALID_ID, "Purchase has invalid CustomerID");
        }
        if (!customerController.isCustomerExist(purchase.getCustomerId())) {
            throw new ApplicationException(ErrorTypes.ID_DONT_EXIST, "CustomerID doesn't exist");
        }
    }*/
}
