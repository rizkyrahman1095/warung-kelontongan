package com.enigma.tokonyadia.repository;

import com.enigma.tokonyadia.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, String> {
}
