
package com.awt.donation.repository;

import com.awt.donation.model.Donor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DonorRepository {

    private final List<Donor> donors = new ArrayList<>();
    private long nextId = 1;

    public Donor save(Donor donor) {
        if (donor.getId() == null) {
            donor.setId(nextId++);
            donors.add(donor);
            return donor;
        }

        for (int i = 0; i < donors.size(); i++) {
            if (donors.get(i).getId().equals(donor.getId())) {
                donors.set(i, donor);
                return donor;
            }
        }

        donors.add(donor);
        return donor;
    }

    public List<Donor> findAll() {
        return new ArrayList<>(donors);
    }

    public Optional<Donor> findById(Long id) {
        return donors.stream()
                .filter(donor -> donor.getId().equals(id))
                .findFirst();
    }

    public void deleteById(Long id) {
        donors.removeIf(donor -> donor.getId().equals(id));
    }

    public long count() {
        return donors.size();
    }
}
