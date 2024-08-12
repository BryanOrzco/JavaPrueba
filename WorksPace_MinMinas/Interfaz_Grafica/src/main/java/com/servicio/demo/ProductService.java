package com.servicio.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final String BASE_URL = "http://localhost:8080/api/products"; // Cambia esto según tu API

    private final RestTemplate restTemplate;

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> getAllProducts() {
        Product[] products = restTemplate.getForObject(BASE_URL, Product[].class);
        return Arrays.asList(products);
    }

    public Product getProductById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, Product.class);
    }

    public Product saveProduct(Product product) {
        return restTemplate.postForObject(BASE_URL, product, Product.class);
    }

    public void deleteProduct(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
