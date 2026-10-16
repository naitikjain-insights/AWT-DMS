
package com.awt.donation;

import com.awt.donation.model.Donor;
import com.awt.donation.repository.DonorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DonorController {

    private final DonorRepository donorRepository;

    public DonorController(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    @GetMapping("/donors")
    public String donors(Model model) {
        model.addAttribute("donors", donorRepository.findAll());
        model.addAttribute("donor", new Donor());
        return "donors";
    }

    @PostMapping("/donors/save")
    public String saveDonor(
            @ModelAttribute Donor donor,
            RedirectAttributes redirectAttributes) {

        if (donor.getName() == null || donor.getName().isBlank()
                || donor.getEmail() == null || donor.getEmail().isBlank()
                || donor.getPhone() == null || donor.getPhone().isBlank()) {

            redirectAttributes.addFlashAttribute(
                    "error", "Please fill in all required fields.");
            return "redirect:/donors";
        }

        donorRepository.save(donor);

        redirectAttributes.addFlashAttribute(
                "success", "Donor added successfully!");

        return "redirect:/donors";
    }
}
