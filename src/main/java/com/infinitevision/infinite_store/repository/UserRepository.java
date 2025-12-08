package com.infinitevision.infinite_store.repository;

import com.infinitevision.infinite_store.domain.model.enums.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);
}
