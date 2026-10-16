
package com.awt.donation;

import com.awt.donation.model.Donation;
import com.awt.donation.repository.DonationRepository;
import com.awt.donation.repository.DonorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

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
    public String donations(Model model) {
        model.addAttribute("donations", donationRepository.findAll());
        model.addAttribute("donors", donorRepository.findAll());
        model.addAttribute("donation", new Donation());
        model.addAttribute(
                "totalAmount", donationRepository.getTotalAmount());
        return "donations";
    }

    @PostMapping("/donations/save")
    public String saveDonation(
            @ModelAttribute Donation donation,
            RedirectAttributes redirectAttributes) {

        if (donation.getDonorId() == null
                || donation.getAmount() <= 0
                || donation.getPaymentMode() == null
                || donation.getPaymentMode().isBlank()) {

            redirectAttributes.addFlashAttribute(
                    "error", "Please enter valid donation details.");
            return "redirect:/donations";
        }

        if (donorRepository.findById(donation.getDonorId()).isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "error", "Please select a valid donor first.");
            return "redirect:/donations";
        }

        donation.setDonationDate(LocalDateTime.now());
        donationRepository.save(donation);

        redirectAttributes.addFlashAttribute(
                "success", "Donation recorded successfully!");

        return "redirect:/donations";
    }
}
