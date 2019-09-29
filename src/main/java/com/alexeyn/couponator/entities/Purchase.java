package com.alexeyn.couponator.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "purchases")
public class Purchase implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "purchaseId")
    private Long purchaseId;

    @Column(name = "customerId", nullable = false)
    private Long customerId;

    @Column(name = "couponId", nullable = false)
    private Long couponId;

    @Column(name = "amount", nullable = false)
    private int amount;

    @ManyToOne
    private Coupon coupon;

    @ManyToOne
    private Customer customer;

    // Empty constructor
    public Purchase() {
    }

    // Full constructor without id
    public Purchase(Long customerId, Long couponId, int amount) {
        this.customerId = customerId;
        this.couponId = couponId;
        this.amount = amount;
    }

    // Full constructor with id
    public Purchase(Long purchaseId, Long customerId, Long couponId, int amount) {
        this(customerId, couponId, amount);
        this.purchaseId = purchaseId;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "Purchase [" +
                "id=" + purchaseId +
                ", customerId=" + customerId +
                ", couponId=" + couponId +
                ", amount=" + amount +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return purchaseId.equals(purchase.purchaseId) &&
                customerId.equals(purchase.customerId) &&
                couponId.equals(purchase.couponId) &&
                amount == purchase.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseId, customerId, couponId, amount);
    }
}
