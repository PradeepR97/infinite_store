package com.infinitevision.infinite_store.repository;

import com.infinitevision.infinite_store.domain.model.enums.Address;
import com.infinitevision.infinite_store.domain.model.enums.AddressType;
import com.infinitevision.infinite_store.domain.model.enums.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    boolean existsByUser(User user);

    boolean existsByUserAndAddressType(User user, AddressType addressType);

    List<Address> findByUser(User user);
}

