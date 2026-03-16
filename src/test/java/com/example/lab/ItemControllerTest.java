package com.example.lab;

import com.example.lab.model.Item;
import com.example.lab.repository.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    private Item savedItem;

    @BeforeEach
    void setUp() {
        savedItem = itemRepository.save(new Item("Тестовий предмет"));
    }

    @AfterEach
    void tearDown() {
        itemRepository.deleteAll();
    }

    // 5 ТЕСТІВ

    @Test
    void test1_shouldReturnStatusOkWhenGetAllItems() throws Exception {
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk());
    }

    @Test
    void test2_shouldReturnJsonWithItems() throws Exception {
        mockMvc.perform(get("/api/items"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Тестовий предмет"));
    }

    @Test
    void test3_shouldReturnStatusOkWhenGetExistingItemById() throws Exception {
        mockMvc.perform(get("/api/items/" + savedItem.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Тестовий предмет"));
    }

    @Test
    void test4_shouldReturnStatusNotFoundWhenItemDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/items/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void test5_shouldReturnStatusCreatedWhenPostNewItem() throws Exception {
        String newItemJson = "{\"name\":\"Новий предмет через POST\"}";

        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newItemJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Новий предмет через POST"));
    }

    // ДОДАТКОВІ ТЕСТИ

    @Test
    void test6_bonus_shouldReturnStatusBadRequestWhenPostEmptyName() throws Exception {
        String invalidItemJson = "{\"name\":\"\"}";

        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidItemJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void test7_bonus_shouldReturnStatusOkWhenDeleteItem() throws Exception {
        mockMvc.perform(delete("/api/items/" + savedItem.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/items/" + savedItem.getId()))
                .andExpect(status().isNotFound());
    }
}