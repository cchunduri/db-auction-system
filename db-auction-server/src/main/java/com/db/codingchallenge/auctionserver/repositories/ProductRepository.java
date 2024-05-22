package com.db.codingchallenge.auctionserver.repositories;

import com.db.codingchallenge.auctionserver.entities.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}