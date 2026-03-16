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

@SpringBootTest // Кажемо Spring підняти контекст (і базу даних H2) для тесту
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    // @BeforeEach означає, що цей код виконається ПЕРЕД запуском самого тесту
    @BeforeEach
    void setUp() {
        // Вимога 2: Створити у БД тестовий сет на 30 елементів
        List<Item> itemsToSave = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            itemsToSave.add(new Item("Тестовий елемент " + i));
        }
        itemService.saveAllItems(itemsToSave);
    }

    // @AfterEach виконується ПІСЛЯ тесту, щоб очистити базу даних
    @AfterEach
    void tearDown() {
        itemService.deleteAllItems();
    }

    // @Test - це безпосередньо наш тест
    @Test
    void testDatabaseHasExactlyThirtyElements() {
        // Отримуємо фактичну кількість елементів з бази
        long count = itemService.getItemsCount();

        // Вимога 3: Створити один тест на кількість елементів у базі
        // Перевіряємо, чи очікуване значення (30) збігається з фактичним (count)
        Assertions.assertEquals(30, count, "Кількість елементів у базі має бути рівно 30");
    }
}