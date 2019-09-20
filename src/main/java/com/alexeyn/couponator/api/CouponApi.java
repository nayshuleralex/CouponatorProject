package com.alexeyn.couponator.api;

import com.alexeyn.couponator.entities.Coupon;
import com.alexeyn.couponator.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alexeyn.couponator.logic.CouponController;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponApi {
	
	@Autowired
	private CouponController couponController;

	// method = POST	url = http://localhost:8080/coupons
	@PostMapping
	public long createCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		return this.couponController.createCoupon(coupon);
	}

	// method = GET		url = http://localhost:8080/coupons/42
	@GetMapping("/{couponId}")
	public Coupon getCoupon(@PathVariable("couponId") long id) throws ApplicationException {
		return this.couponController.getCoupon(id);
	}

	// method = GET		url = http://localhost:8080/coupons
	@GetMapping
	public List<Coupon> getAllCoupons() throws ApplicationException {
		return this.couponController.getAllCoupons();
	}

	// method = GET		url = http://localhost:8080/coupons/byCompanyId?companyId=42
	@GetMapping("/byCompanyId")
	public List<Coupon> getAllCoupons(@RequestParam("companyId") long companyId) throws ApplicationException {
		return this.couponController.getAllCoupons(companyId);
	}

	// method = PUT		url = http://localhost:8080/coupons
	@PutMapping
	public void updateCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		this.couponController.updateCoupon(coupon);
	}

	// method = DELETE	url = http://localhost:8080/coupons/42
	@DeleteMapping("/{couponId}")
	public void deleteCoupon(@PathVariable("couponId") long id) throws ApplicationException {
		this.couponController.deleteCoupon(id);
	}
}
