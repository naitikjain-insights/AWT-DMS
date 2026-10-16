
package com.awt.donation;

import com.awt.donation.model.Donation;
import com.awt.donation.model.Donor;
import com.awt.donation.repository.DonationRepository;
import com.awt.donation.repository.DonorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReceiptController {

    private final DonationRepository donationRepository;
    private final DonorRepository donorRepository;

    public ReceiptController(
            DonationRepository donationRepository,
            DonorRepository donorRepository) {
        this.donationRepository = donationRepository;
        this.donorRepository = donorRepository;
    }

    @GetMapping("/receipts/{donationId}")
    public String receipt(
            @PathVariable Long donationId,
            Model model) {

        Donation donation = donationRepository
                .findById(donationId)
                .orElse(null);

        if (donation == null) {
            return "receipt-not-found";
        }

        Donor donor = donorRepository
                .findById(donation.getDonorId())
                .orElse(null);

        if (donor == null) {
            return "receipt-not-found";
        }

        model.addAttribute("donation", donation);
        model.addAttribute("donor", donor);

        return "receipt";
    }
}
