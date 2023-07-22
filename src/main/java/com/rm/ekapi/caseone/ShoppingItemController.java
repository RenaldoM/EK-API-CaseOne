package com.rm.ekapi.caseone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shoppingItems")
public class ShoppingItemController {

    @Autowired
    private ShoppingItemService shoppingItemService;

    @PostMapping
    public ResponseEntity<ShoppingItem> createShoppingItem(@RequestBody ShoppingItem shoppingItem) {
        try {
            ShoppingItem createdItem = shoppingItemService.createShoppingItem(shoppingItem);
            return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ShoppingItem>> getAllShoppingItems() {
        List<ShoppingItem> shoppingItems = shoppingItemService.getAllShoppingItems();
        return new ResponseEntity<>(shoppingItems, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingItem> updateShoppingItem(@PathVariable Long id, @RequestBody ShoppingItem shoppingItem) {
        try {
            ShoppingItem updatedItem = shoppingItemService.updateShoppingItem(id, shoppingItem);
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingItem(@PathVariable Long id) {
        try {
            shoppingItemService.deleteShoppingItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
