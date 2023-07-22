package com.rm.ekapi.caseone;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {

    private final String fileName = "shoppingItems.txt";

    public void saveToFile(List<ShoppingItem> shoppingItems) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(shoppingItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ShoppingItem> readFromFile() {
        List<ShoppingItem> shoppingItems = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            shoppingItems = (List<ShoppingItem>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return shoppingItems;
    }
}
