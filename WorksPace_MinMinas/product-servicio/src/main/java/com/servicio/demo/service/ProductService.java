package com.servicio.demo.service;

import com.servicio.demo.model.Product;
import com.servicio.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
@Service // Agrega esta anotació
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String INVENTORY_SERVICE_URL = "http://localhost:8081/api/inventory"; // Cambia el puerto según sea necesario

    public Product addProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        
        // Llamada al servicio de inventario para agregar el producto
        restTemplate.postForObject(INVENTORY_SERVICE_URL, new InventoryRequest(savedProduct.getId(), 0), InventoryRequest.class);
        
        return savedProduct;
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
        
        // Llamada al servicio de inventario para eliminar el producto
        restTemplate.delete(INVENTORY_SERVICE_URL + "/" + productId);
    }

    // Método para obtener todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Método para obtener un producto por ID
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }
}