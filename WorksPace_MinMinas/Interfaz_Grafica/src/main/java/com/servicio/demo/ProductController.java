package com.servicio.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Long> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, String> descriptionColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TextArea productDetailsArea;

    private final ProductService productService;
    private final InventoryService inventoryService;

    public ProductController(ProductService productService, InventoryService inventoryService) {
        this.productService = productService;
        this.inventoryService = inventoryService;
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadProducts();
    }

    private void loadProducts() {
        List<Product> products = productService.getAllProducts();
        productTable.getItems().setAll(products);
    }

    @FXML
    public void addProduct() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        double price = Double.parseDouble(priceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());

        Product newProduct = new Product(null, name, description, price);
        Product savedProduct = productService.saveProduct(newProduct);

        // Actualizar el inventario con la cantidad
        inventoryService.updateInventory(savedProduct.getId(), quantity);

        loadProducts();
        clearFields();
    }

    @FXML
    public void deleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            productService.deleteProduct(selectedProduct.getId());
            inventoryService.deleteInventory(selectedProduct.getId());
            loadProducts();
        }
    }

    @FXML
    public void viewProductDetails() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Product product = productService.getProductById(selectedProduct.getId());
            int quantity = inventoryService.getInventoryByProductId(selectedProduct.getId());

            productDetailsArea.setText(product.toString() + ", Quantity: " + quantity);
        }
    }

    private void clearFields() {
        nameField.clear();
        descriptionField.clear();
        priceField.clear();
        quantityField.clear();
    }
}
