
package com.awt.donation.repository;

import com.awt.donation.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findByDonorId(Long donorId);

    default double getTotalAmount() {
        return findAll()
                .stream()
                .mapToDouble(donation -> donation.getAmount() == null
                        ? 0.0
                        : donation.getAmount())
                .sum();
    }
}
