package org.dafe.tripTix.repository;

import org.dafe.tripTix.entity.Contactus;
import org.dafe.tripTix.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsRepository extends JpaRepository<Contactus, Integer> {
}
