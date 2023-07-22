package com.rm.ekapi.caseone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {
    ShoppingItem findByItemNameIgnoreCase(String itemName);
}