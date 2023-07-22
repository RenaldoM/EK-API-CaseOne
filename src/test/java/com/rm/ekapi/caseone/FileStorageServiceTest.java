package com.rm.ekapi.caseone;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileStorageServiceTest {

    private FileStorageService fileStorageService;
    private File file;

    @BeforeEach
    public void init() {
        fileStorageService = new FileStorageService();
        file = new File("shoppingItems.txt");
    }

    @AfterEach
    public void cleanup() {
        file.delete();
    }

    @Test
    public void testSaveAndReadFromFile() {
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setItemName("Test Item");
        shoppingItem.setQuantity(10);
        shoppingItem.setCreatorName("Test Creator");
        shoppingItem.setCreationDate(LocalDate.now());
        shoppingItem.setDueDate(LocalDate.now());

        fileStorageService.saveToFile(List.of(shoppingItem));

        List<ShoppingItem> shoppingItems = fileStorageService.readFromFile();

        assertEquals(1, shoppingItems.size());
        assertEquals(shoppingItem.getItemName(), shoppingItems.get(0).getItemName());
        assertEquals(shoppingItem.getQuantity(), shoppingItems.get(0).getQuantity());
        assertEquals(shoppingItem.getCreatorName(), shoppingItems.get(0).getCreatorName());
        assertEquals(shoppingItem.getCreationDate(), shoppingItems.get(0).getCreationDate());
        assertEquals(shoppingItem.getDueDate(), shoppingItems.get(0).getDueDate());
    }
}
