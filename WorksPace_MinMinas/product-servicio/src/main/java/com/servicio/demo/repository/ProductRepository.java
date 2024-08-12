package com.servicio.demo.repository;

import com.servicio.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Puedes agregar métodos personalizados aquí si es necesario
}
