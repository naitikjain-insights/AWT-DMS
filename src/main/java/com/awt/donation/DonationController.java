
package com.awt.donation;

import com.awt.donation.model.Donation;
import com.awt.donation.model.Donor;
import com.awt.donation.repository.DonationRepository;
import com.awt.donation.repository.DonorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class DonationController {

    private final DonationRepository donationRepository;
    private final DonorRepository donorRepository;

    public DonationController(
            DonationRepository donationRepository,
            DonorRepository donorRepository) {
        this.donationRepository = donationRepository;
        this.donorRepository = donorRepository;
    }

    @GetMapping("/donations")
    public String showDonations(Model model) {
        model.addAttribute("donations", donationRepository.findAll());
        model.addAttribute("donors", donorRepository.findAll());
        model.addAttribute("totalAmount", donationRepository.getTotalAmount());
        model.addAttribute("donation", new Donation());
        
        return "donations";
    }

    @PostMapping("/donations/save")
    public String saveDonation(
            @RequestParam Long donorId,
            @RequestParam Double amount,
            @RequestParam String paymentMode,
            @RequestParam(required = false) String purpose,
            RedirectAttributes redirectAttributes) {

        if (amount == null || amount <= 0) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Donation amount must be greater than zero.");
            return "redirect:/donations";
        }

        if (paymentMode == null || paymentMode.isBlank()) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Please select a payment method.");
            return "redirect:/donations";
        }

        Donor donor = donorRepository.findById(donorId).orElse(null);

        if (donor == null) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Please select a valid donor.");
            return "redirect:/donations";
        }

        Donation donation = new Donation();
        donation.setDonorId(donor.getId());
        donation.setAmount(amount);
        donation.setPaymentMode(paymentMode.trim());
        donation.setPurpose(purpose == null ? "" : purpose.trim());
        donation.setDonationDate(LocalDate.now());

        donationRepository.save(donation);

        redirectAttributes.addFlashAttribute(
                "successMessage", "Donation saved successfully!");

        return "redirect:/donations";
    }
}
