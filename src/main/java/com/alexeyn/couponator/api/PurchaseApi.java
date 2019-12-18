package com.alexeyn.couponator.api;

import com.alexeyn.couponator.consts.Constants;
import com.alexeyn.couponator.data.LoggedInUserData;
import com.alexeyn.couponator.data.PurchaseDataObject;
import com.alexeyn.couponator.entities.Customer;
import com.alexeyn.couponator.entities.Purchase;
import com.alexeyn.couponator.entities.Coupon;
import com.alexeyn.couponator.exceptions.ApplicationException;
import com.alexeyn.couponator.logic.CouponController;
import com.alexeyn.couponator.logic.CustomerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alexeyn.couponator.logic.PurchaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseApi {

    @Autowired
    private PurchaseController purchaseController;
	@Autowired
    private CustomerController customerController;
	@Autowired
    private CouponController couponController;

	// method = POST	url = http://localhost:8080/purchases
    @PostMapping
    public void createPurchase(@RequestBody PurchaseDataObject purchase, HttpServletRequest request) throws ApplicationException {
		LoggedInUserData userData = (LoggedInUserData) request.getAttribute(Constants.USER_DATA_KEY);
        this.purchaseController.createPurchase(purchase, userData);
    }

	// method = GET		url = http://localhost:8080/purchases
    @GetMapping("/{purchaseId}")
    public Purchase getPurchase(@PathVariable("purchaseId") long id) throws ApplicationException {
    	return this.purchaseController.getPurchase(id);
	}

	// method = GET		url = http://localhost:8080/purchases/byCustomerId?customerId=42
	/*@GetMapping("/byCustomerId")
	public List<Purchase> getAllCustomerPurchases(@RequestParam("customerId") long id) throws ApplicationException {
		Customer customer = customerController.getCustomer(id);
		return this.purchaseController.getAllCustomerPurchases(customer);
	}*/

	// method = GET		url = http://localhost:8080/purchases/byCustomerId?customerId=42
	/*@GetMapping("/byCouponId")
	public List<Purchase> getAllCouponPurchases(@RequestParam("couponId") long id) throws ApplicationException {
		Coupon coupon = couponController.getCoupon(id);
		return this.purchaseController.getAllCouponPurchases(coupon);
	}*/

	// method = PUT		url = http://localhost:8080/purchases
	@PutMapping
	public void updatePurchase(@RequestBody Purchase purchase) throws ApplicationException {
    	this.purchaseController.updatePurchase(purchase);
	}

	// method = DELETE	url = http://localhost:8080/purchases/42
	@DeleteMapping("/{purchaseId}")
	public void deletePurchase(@PathVariable("purchaseId") long id) throws ApplicationException {
    	this.purchaseController.deletePurchase(id);
	}


}
