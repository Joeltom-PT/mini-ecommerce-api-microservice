package com.camcorderio.userservice.repository;

import com.camcorderio.userservice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findByUserId(Long userId);
}
