package com.rm.ekapi.caseone;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ShoppingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private int quantity;
    private String creatorName;
    private LocalDate creationDate;
    private LocalDate dueDate;

    // getters and setters
}