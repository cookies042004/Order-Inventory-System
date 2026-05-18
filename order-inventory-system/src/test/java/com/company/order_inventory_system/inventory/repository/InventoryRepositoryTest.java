package com.company.order_inventory_system.inventory.repository;

import com.company.order_inventory_system.inventory.entity.Inventory;
import com.company.order_inventory_system.product.entity.Product;
import com.company.order_inventory_system.product.repository.ProductRepository;
import com.company.order_inventory_system.store.entity.Store;
import com.company.order_inventory_system.store.repository.StoreRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InventoryRepositoryTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Test Save Inventory")
    void testSaveInventory() {

        Store store = new Store();

        store.setStoreName("Chennai Store");
        store.setWebAddress("https://store.com");

        Store savedStore = storeRepository.save(store);

        Product product = new Product();

        product.setProductName("Laptop");
        product.setBrand("Nike");
        product.setColour("Black");
        product.setSize("M");
        product.setUnitPrice(2500.0);
        product.setRating(4);

        Product savedProduct = productRepository.save(product);

        Inventory inventory = new Inventory();

        inventory.setStore(savedStore);
        inventory.setProduct(savedProduct);
        inventory.setProductInventory(100);

        Inventory savedInventory = inventoryRepository.save(inventory);

        assertNotNull(savedInventory);

        assertNotNull(savedInventory.getInventoryId());

        assertEquals(100, savedInventory.getProductInventory());
    }

    @Test
    @DisplayName("Test Find Inventory By Id")
    void testFindInventoryById() {

        Store store = new Store();

        store.setStoreName("Chennai Store");
        store.setWebAddress("https://store.com");

        Store savedStore = storeRepository.save(store);

        Product product = new Product();

        product.setProductName("Laptop");
        product.setBrand("Nike");
        product.setColour("Black");
        product.setSize("M");
        product.setUnitPrice(2500.0);
        product.setRating(4);

        Product savedProduct = productRepository.save(product);

        Inventory inventory = new Inventory();

        inventory.setStore(savedStore);
        inventory.setProduct(savedProduct);
        inventory.setProductInventory(100);

        Inventory savedInventory = inventoryRepository.save(inventory);

        Optional<Inventory> foundInventory = inventoryRepository.findById(savedInventory.getInventoryId());

        assertTrue(foundInventory.isPresent());

        assertEquals(100, foundInventory.get().getProductInventory());
    }

    @Test
    @DisplayName("Test Exists By Store And Product")
    void testExistsByStoreAndProduct() {

        Store store = new Store();

        store.setStoreName("Chennai Store");
        store.setWebAddress("https://store.com");

        Store savedStore = storeRepository.save(store);

        Product product = new Product();

        product.setProductName("Laptop");
        product.setBrand("Nike");
        product.setColour("Black");
        product.setSize("M");
        product.setUnitPrice(2500.0);
        product.setRating(4);

        Product savedProduct = productRepository.save(product);

        Inventory inventory = new Inventory();

        inventory.setStore(savedStore);
        inventory.setProduct(savedProduct);
        inventory.setProductInventory(100);

        inventoryRepository.save(inventory);

        boolean exists = inventoryRepository.existsByStoreStoreIdAndProductProductId(
                                savedStore.getStoreId(),
                                savedProduct.getProductId());

        assertTrue(exists);
    }
}