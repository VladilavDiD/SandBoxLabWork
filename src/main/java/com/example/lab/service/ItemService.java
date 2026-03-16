package com.example.lab.service;

import com.example.lab.model.Item;
import com.example.lab.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void saveAllItems(List<Item> items) {
        itemRepository.saveAll(items);
    }

    public long getItemsCount() {
        return itemRepository.count();
    }

    public void deleteAllItems() {
        itemRepository.deleteAll();
    }
}