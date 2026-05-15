package com.company.order_inventory_system.store.Repository;

import com.company.order_inventory_system.store.entity.Store;
import com.company.order_inventory_system.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StoreRepositoryTest {
    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("Test Store Save Successfully")
    void testSaveStore(){
        Store store = new Store();

        store.setStoreName("Chennai Store");
        store.setWebAddress("https://chennai-store.com");
        store.setLatitude(new BigDecimal("12.971600"));
        store.setLongitude(new BigDecimal("80.594600"));
        store.setLogoLastUpdated(LocalDate.now());

        Store savedStore = storeRepository.save(store);

        assertNotNull(savedStore);
        assertNotNull(savedStore.getStoreId());
        assertEquals("Chennai Store", savedStore.getStoreName());
    }

    @Test
    @DisplayName("Test Find By Store Name Successfully")
    void testExistsByStoreName(){
//        Store store = new Store();
//
//        store.setStoreName("Mumbai Store");
//        store.setWebAddress("https://mumbai-store.com");
//
//        storeRepository.save(store);

        boolean exists = storeRepository.existsByStoreName("Seattle");

        assertTrue(exists);
    }
}
