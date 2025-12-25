package com.infinitevision.infinite_store.repository;

import com.infinitevision.infinite_store.domain.model.enums.Address;
import com.infinitevision.infinite_store.domain.model.enums.User;
import com.infinitevision.infinite_store.domain.model.enums.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    // Check if user has any address
    boolean existsByUser(User user);

    // Check if user already has HOME / WORK
    boolean existsByUserAndAddressType(User user, AddressType addressType);

    // Get all addresses of a user
    List<Address> findByUser(User user);

    // Find specific address (used in OrderService)
    Optional<Address> findByUserAndAddressLine1AndAddressLine2AndCityAndStateAndPincodeAndAddressType(
            User user,
            String addressLine1,
            String addressLine2,
            String city,
            String state,
            String pincode,
            AddressType addressType
    );
}
