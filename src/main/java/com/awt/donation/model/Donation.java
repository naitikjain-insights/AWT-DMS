
package com.awt.donation.model;

import java.time.LocalDateTime;

public class Donation {

    private Long id;
    private Long donorId;
    private double amount;
    private String paymentMode;
    private String purpose;
    private String transactionId;
    private LocalDateTime donationDate;

    public Donation() {
    }

    public Donation(
            Long id,
            Long donorId,
            double amount,
            String paymentMode,
            String purpose,
            String transactionId,
            LocalDateTime donationDate) {

        this.id = id;
        this.donorId = donorId;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.purpose = purpose;
        this.transactionId = transactionId;
        this.donationDate = donationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDonorId() {
        return donorId;
    }

    public void setDonorId(Long donorId) {
        this.donorId = donorId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDateTime donationDate) {
        this.donationDate = donationDate;
    }
}
