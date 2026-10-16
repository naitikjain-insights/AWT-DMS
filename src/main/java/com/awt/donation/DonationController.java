
package com.awt.donation;

import com.awt.donation.model.Donor;
import com.awt.donation.repository.DonorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DonorController {

    private final DonorRepository donorRepository;

    public DonorController(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    @GetMapping("/donors")
    public String showDonors(Model model) {
        model.addAttribute("donors", donorRepository.findAll());
        model.addAttribute("donor", new Donor());
        return "donors";
    }

    @PostMapping("/donors/save")
    public String saveDonor(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String address,
            RedirectAttributes redirectAttributes) {

        if (name == null || name.isBlank()
                || email == null || email.isBlank()) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Name and email are required.");
            return "redirect:/donors";
        }

        Donor donor = new Donor();
        donor.setName(name.trim());
        donor.setEmail(email.trim());
        donor.setPhone(phone == null ? "" : phone.trim());
        donor.setAddress(address == null ? "" : address.trim());

        donorRepository.save(donor);

        redirectAttributes.addFlashAttribute(
                "successMessage", "Donor registered successfully!");

        return "redirect:/donors";
    }
}
