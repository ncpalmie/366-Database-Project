package csc366.jpademo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.TestPropertySource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
	"spring.main.banner-mode=off",
	"spring.jpa.hibernate.ddl-auto=update",
	"logging.level.root=ERROR",
	"logging.level.csc366=DEBUG",

	"logging.level.org.hibernate.SQL=DEBUG",
	"logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE", // display prepared statement parameters
	"spring.jpa.properties.hibernate.format_sql=true",
	"spring.jpa.show-sql=false",   // prevent duplicate logging
	"spring.jpa.properties.hibernate.show_sql=false",	
	
	"logging.pattern.console= %d{yyyy-MM-dd HH:mm:ss} - %msg%n"
})
@TestMethodOrder(OrderAnnotation.class)
public class InventoryTests {

    private final static Logger log = LoggerFactory.getLogger(InventoryTests.class);
    
    @Autowired
    private InventoryRepository inventoryRepository;

    private final Store randomStore = new Store("store0", "860-345-1920", "Glendale", "Large"); 

    private final Inventory storeInventory = new Inventory(0, "GlendaleStore0", randomStore);
    
    @BeforeEach
    private void setup() {
	    inventoryRepository.saveAndFlush(storeInventory);
    }
    
    @Test
    @Order(1)
    public void testSaveInventoryAndGetByID() {
	    Inventory inventory2 = inventoryRepository.findByInventoryName("GlendaleStore0");

	    log.info(inventory2.toString());
	
	    assertNotNull(storeInventory);
	    assertEquals(inventory2.getInventoryID(), storeInventory.getInventoryID());
    }
    
    @Test
    @Order(2)
    public void testGetInventoryByID() {
    	Inventory inventory2 = inventoryRepository.findByInventoryName("GlendaleStore0");

    	assertNotNull(storeInventory);
    	assertEquals(inventory2.getInventoryID(), storeInventory.getInventoryID());
    }

    @Test
    @Order(3)
    public void testDeleteInventory() {
    	inventoryRepository.delete(storeInventory);
    	inventoryRepository.flush();
    }
    
    @Test
    @Order(4)
    public void testFindAllInventories() {
    	assertNotNull(inventoryRepository.findAll());
    }

    @Test
    @Order(5)
    public void testInventoryModifiers() {
        Store testStore = new Store("T", "T", "T", "T"); 
        Inventory inventory2 = inventoryRepository.findByInventoryName("GlendaleStore0");
        inventory2.setInventoryName("T");
        inventory2.setStore(testStore);
        inventoryRepository.save(inventory2);

        
        inventory2 = inventoryRepository.findByInventoryName("T");
        assertEquals(inventory2.getStore().getPhone(), "T");
        assertEquals(inventory2.getInventoryName(), "T");
    }

    @Test
    @Order(6)
    public void testFindInventoryUsingJpql() {
        Inventory inventory2 = inventoryRepository.findByInventoryNameJpql("GlendaleStore0");

        assertNotNull(inventory2);
        assertNotNull(storeInventory);
    	assertEquals(inventory2.getInventoryID(), storeInventory.getInventoryID());
    }
}
