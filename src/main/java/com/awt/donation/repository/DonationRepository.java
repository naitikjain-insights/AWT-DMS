
package com.awt.donation.repository;

import com.awt.donation.model.Donation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DonationRepository {

    private final List<Donation> donations = new ArrayList<>();
    private long nextId = 1;

    public Donation save(Donation donation) {
        if (donation.getId() == null) {
            donation.setId(nextId++);
            donations.add(donation);
            return donation;
        }

        for (int i = 0; i < donations.size(); i++) {
            if (donations.get(i).getId().equals(donation.getId())) {
                donations.set(i, donation);
                return donation;
            }
        }

        donations.add(donation);
        return donation;
    }

    public List<Donation> findAll() {
        return new ArrayList<>(donations);
    }

    public Optional<Donation> findById(Long id) {
        return donations.stream()
                .filter(donation -> donation.getId().equals(id))
                .findFirst();
    }

    public List<Donation> findByDonorId(Long donorId) {
        return donations.stream()
                .filter(donation ->
                        donation.getDonorId() != null
                        && donation.getDonorId().equals(donorId))
                .toList();
    }

    public void deleteById(Long id) {
        donations.removeIf(donation -> donation.getId().equals(id));
    }

    public long count() {
        return donations.size();
    }

    public double getTotalAmount() {
        return donations.stream()
                .mapToDouble(Donation::getAmount)
                .sum();
    }
}
