package com.rm.ekapi.caseone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShoppingItemControllerTest {

    @InjectMocks
    private ShoppingItemController shoppingItemController;

    @Mock
    private ShoppingItemService shoppingItemService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateShoppingItem() {
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setItemName("Test Item");
        shoppingItem.setQuantity(10);
        shoppingItem.setCreatorName("Test Creator");
        shoppingItem.setDueDate(LocalDate.now());

        when(shoppingItemService.createShoppingItem(any(ShoppingItem.class))).thenReturn(shoppingItem);

        ResponseEntity<ShoppingItem> response = shoppingItemController.createShoppingItem(shoppingItem);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(shoppingItem.getItemName(), response.getBody().getItemName());

        verify(shoppingItemService, times(1)).createShoppingItem(any(ShoppingItem.class));
    }

    @Test
    public void testGetAllShoppingItems() {
        ShoppingItem shoppingItem1 = new ShoppingItem();
        shoppingItem1.setId(1L);
        ShoppingItem shoppingItem2 = new ShoppingItem();
        shoppingItem2.setId(2L);

        when(shoppingItemService.getAllShoppingItems()).thenReturn(Arrays.asList(shoppingItem1, shoppingItem2));

        ResponseEntity<List<ShoppingItem>> response = shoppingItemController.getAllShoppingItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().stream().anyMatch(item -> item.getId().equals(1L)));
        assertTrue(response.getBody().stream().anyMatch(item -> item.getId().equals(2L)));

        verify(shoppingItemService, times(1)).getAllShoppingItems();
    }

    @Test
    public void testUpdateShoppingItem() {
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setId(1L);
        shoppingItem.setItemName("Test Item");
        shoppingItem.setQuantity(10);
        shoppingItem.setCreatorName("Test Creator");
        shoppingItem.setDueDate(LocalDate.now());

        when(shoppingItemService.updateShoppingItem(anyLong(), any(ShoppingItem.class))).thenReturn(shoppingItem);

        ResponseEntity<ShoppingItem> response = shoppingItemController.updateShoppingItem(1L, shoppingItem);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(shoppingItem.getItemName(), response.getBody().getItemName());

        verify(shoppingItemService, times(1)).updateShoppingItem(anyLong(), any(ShoppingItem.class));
    }

    @Test
    public void testDeleteShoppingItem() {
        doNothing().when(shoppingItemService).deleteShoppingItem(anyLong());

        ResponseEntity<Void> response = shoppingItemController.deleteShoppingItem(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(shoppingItemService, times(1)).deleteShoppingItem(anyLong());
    }

    @Test
    public void testCreateShoppingItemWithInvalidData() {
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setItemName(null);
        shoppingItem.setQuantity(10);
        shoppingItem.setCreatorName("Test Creator");
        shoppingItem.setDueDate(LocalDate.now());

        when(shoppingItemService.createShoppingItem(any(ShoppingItem.class))).thenThrow(IllegalArgumentException.class);

        ResponseEntity<ShoppingItem> response = shoppingItemController.createShoppingItem(shoppingItem);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(shoppingItemService, times(1)).createShoppingItem(any(ShoppingItem.class));
    }

    @Test
    public void testUpdateShoppingItemWithNonExistingId() {
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setId(1L);
        shoppingItem.setItemName("Test Item");
        shoppingItem.setQuantity(10);
        shoppingItem.setCreatorName("Test Creator");
        shoppingItem.setDueDate(LocalDate.now());

        when(shoppingItemService.updateShoppingItem(anyLong(), any(ShoppingItem.class))).thenThrow(IllegalArgumentException.class);

        ResponseEntity<ShoppingItem> response = shoppingItemController.updateShoppingItem(1L, shoppingItem);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(shoppingItemService, times(1)).updateShoppingItem(anyLong(), any(ShoppingItem.class));
    }

    @Test
    public void testDeleteShoppingItemWithNonExistingId() {
        doThrow(IllegalArgumentException.class).when(shoppingItemService).deleteShoppingItem(anyLong());

        ResponseEntity<Void> response = shoppingItemController.deleteShoppingItem(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(shoppingItemService, times(1)).deleteShoppingItem(anyLong());
    }
}

