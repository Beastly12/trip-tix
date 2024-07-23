package org.dafe.tripTix.repository;

import org.dafe.tripTix.entity.User;
import org.dafe.tripTix.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String emailAddress);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = TRUE, u.email_verified_at = CURRENT_TIMESTAMP WHERE u.id = ?1")
    int enableUserEmail(int userId);

    List<User> findByAppUserRole(UserRole userRole);
}
