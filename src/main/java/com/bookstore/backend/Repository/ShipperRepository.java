package com.bookstore.backend.Repository;

import com.bookstore.backend.Entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShipperRepository extends JpaRepository<Shipper, Integer> {
}
