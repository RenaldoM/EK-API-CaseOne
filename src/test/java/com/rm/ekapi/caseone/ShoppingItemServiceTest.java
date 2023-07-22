package com.rm.ekapi.caseone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShoppingItemServiceTest {

    @InjectMocks
    private ShoppingItemService shoppingItemService;

    @Mock
    private FileStorageService fileStorageService;

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

        when(fileStorageService.readFromFile()).thenReturn(new ArrayList<>());

        ShoppingItem createdItem = shoppingItemService.createShoppingItem(shoppingItem);

        assertEquals(shoppingItem.getItemName(), createdItem.getItemName());
        assertEquals(shoppingItem.getQuantity(), createdItem.getQuantity());
        assertEquals(shoppingItem.getCreatorName(), createdItem.getCreatorName());
        assertEquals(shoppingItem.getDueDate(), createdItem.getDueDate());
        assertNotNull(createdItem.getCreationDate());

        verify(fileStorageService, times(1)).readFromFile();
        verify(fileStorageService, times(1)).saveToFile(anyList());
    }

    @Test
    public void testGetAllShoppingItems() {
        ShoppingItem shoppingItem1 = new ShoppingItem();
        shoppingItem1.setId(1L);
        ShoppingItem shoppingItem2 = new ShoppingItem();
        shoppingItem2.setId(2L);

        when(fileStorageService.readFromFile()).thenReturn(Arrays.asList(shoppingItem1, shoppingItem2));

        List<ShoppingItem> shoppingItems = shoppingItemService.getAllShoppingItems();

        assertEquals(2, shoppingItems.size());
        assertTrue(shoppingItems.stream().anyMatch(item -> item.getId().equals(1L)));
        assertTrue(shoppingItems.stream().anyMatch(item -> item.getId().equals(2L)));

        verify(fileStorageService, times(1)).readFromFile();
    }

    @Test
    public void testUpdateShoppingItem() {
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setId(1L);
        shoppingItem.setItemName("Test Item");
        shoppingItem.setQuantity(10);
        shoppingItem.setCreatorName("Test Creator");
        shoppingItem.setDueDate(LocalDate.now());

        when(fileStorageService.readFromFile()).thenReturn(Arrays.asList(shoppingItem));

        ShoppingItem updatedItem = new ShoppingItem();
        updatedItem.setItemName("Updated Item");
        updatedItem.setQuantity(20);
        updatedItem.setCreatorName("Updated Creator");
        updatedItem.setDueDate(LocalDate.now().plusDays(1));

        ShoppingItem result = shoppingItemService.updateShoppingItem(1L, updatedItem);

        assertEquals(updatedItem.getItemName(), result.getItemName());
        assertEquals(updatedItem.getQuantity(), result.getQuantity());
        assertEquals(updatedItem.getCreatorName(), result.getCreatorName());
        assertEquals(updatedItem.getDueDate(), result.getDueDate());

        verify(fileStorageService, times(1)).readFromFile();
        verify(fileStorageService, times(1)).saveToFile(anyList());
    }

    @Test
    public void testDeleteShoppingItem() {
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setId(1L);

        when(fileStorageService.readFromFile()).thenReturn(Arrays.asList(shoppingItem));

        shoppingItemService.deleteShoppingItem(1L);

        verify(fileStorageService, times(1)).readFromFile();
        verify(fileStorageService, times(1)).saveToFile(anyList());
    }
}
