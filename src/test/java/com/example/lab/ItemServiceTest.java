package com.example.lab;

import com.example.lab.model.Item;
import com.example.lab.service.ItemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @BeforeEach
    void setUp() {

        List<Item> itemsToSave = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            itemsToSave.add(new Item("Тестовий елемент " + i));
        }
        itemService.saveAllItems(itemsToSave);
    }


    @AfterEach
    void tearDown() {
        itemService.deleteAllItems();
    }

    @Test
    void testDatabaseHasExactlyThirtyElements() {
        long count = itemService.getItemsCount();

        Assertions.assertEquals(30, count, "Кількість елементів у базі має бути рівно 30");
    }

}