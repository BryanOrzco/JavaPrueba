package com.servicio.demo.service;

import com.servicio.demo.model.Inventory;
import com.servicio.demo.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory addInventory(Long productId, int quantity) {
        Inventory inventory = new Inventory(productId, quantity);
        return inventoryRepository.save(inventory);
    }

    public void deleteInventory(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        if (inventory != null) {
            inventoryRepository.delete(inventory);
        }
    }

    public Inventory getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId);
    }
}
