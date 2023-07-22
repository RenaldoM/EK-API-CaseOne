package com.rm.ekapi.caseone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingItemService {

    @Autowired
    private FileStorageService fileStorageService;

    public ShoppingItem createShoppingItem(ShoppingItem shoppingItem) {
        if (shoppingItem.getQuantity() < 1 || shoppingItem.getQuantity() > 100) {
            throw new IllegalArgumentException("Quantity must be between 1 and 100");
        }
        if (shoppingItem.getItemName() == null || shoppingItem.getCreatorName() == null || shoppingItem.getDueDate() == null) {
            throw new IllegalArgumentException("Item name, creator name and due date must not be null");
        }
        List<ShoppingItem> shoppingItems = fileStorageService.readFromFile();
        Optional<ShoppingItem> existingItem = shoppingItems.stream()
                .filter(item -> item.getItemName().equalsIgnoreCase(shoppingItem.getItemName()))
                .findFirst();
        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + shoppingItem.getQuantity());
        } else {
            shoppingItem.setCreationDate(LocalDate.now());
            shoppingItems.add(shoppingItem);
        }
        fileStorageService.saveToFile(shoppingItems);
        return shoppingItem;
    }

    public List<ShoppingItem> getAllShoppingItems() {
        return fileStorageService.readFromFile();
    }

    public ShoppingItem updateShoppingItem(Long id, ShoppingItem shoppingItem) {
        if (shoppingItem.getQuantity() == 0) {
            throw new IllegalArgumentException("Quantity must not be 0");
        }
        List<ShoppingItem> shoppingItems = fileStorageService.readFromFile();
        Optional<ShoppingItem> existingItem = shoppingItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
        if (existingItem.isPresent()) {
            existingItem.get().setItemName(shoppingItem.getItemName());
            existingItem.get().setQuantity(shoppingItem.getQuantity());
            existingItem.get().setCreatorName(shoppingItem.getCreatorName());
            existingItem.get().setDueDate(shoppingItem.getDueDate());
            fileStorageService.saveToFile(shoppingItems);
            return existingItem.get();
        } else {
            throw new IllegalArgumentException("Item with id " + id + " not found");
        }
    }

    public void deleteShoppingItem(Long id) {
        List<ShoppingItem> shoppingItems = fileStorageService.readFromFile();
        if (shoppingItems.removeIf(item -> item.getId().equals(id))) {
            fileStorageService.saveToFile(shoppingItems);
        } else {
            throw new IllegalArgumentException("Item with id " + id + " not found");
        }
    }
}
