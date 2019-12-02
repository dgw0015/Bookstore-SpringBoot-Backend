package com.bookstore.backend.Repository;

import com.bookstore.backend.Entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
