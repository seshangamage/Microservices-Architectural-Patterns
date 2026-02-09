package com.example.inventory.controller;

import com.example.inventory.model.InventoryItem;
import com.example.inventory.repository.InventoryRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping
    public List<InventoryItem> list() {
        return inventoryRepository.findAll();
    }

    @PostMapping
    public InventoryItem create(@RequestBody InventoryItem item) {
        return inventoryRepository.save(item);
    }
}
