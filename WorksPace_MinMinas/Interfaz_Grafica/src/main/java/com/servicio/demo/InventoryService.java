package com.servicio.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryService {

    private final String INVENTORY_URL = "http://localhost:8081/api/inventory"; // Cambia el puerto según sea necesario

    private final RestTemplate restTemplate;

    public InventoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void updateInventory(Long productId, int quantity) {
        InventoryRequest inventoryRequest = new InventoryRequest(productId, quantity);
        restTemplate.postForObject(INVENTORY_URL, inventoryRequest, InventoryRequest.class);
    }

    public void deleteInventory(Long productId) {
        restTemplate.delete(INVENTORY_URL + "/" + productId);
    }

    public int getInventoryByProductId(Long productId) {
        Inventory inventory = restTemplate.getForObject(INVENTORY_URL + "/" + productId, Inventory.class);
        return inventory != null ? inventory.getQuantity() : 0;
    }
}
