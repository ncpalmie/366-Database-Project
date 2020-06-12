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
public class InventoryEntryTests {

    private final static Logger log = LoggerFactory.getLogger(InventoryEntryTests.class);
    
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private RawItemRepository rawItemRepository;
    
    @Autowired
    private PackagedItemRepository packagedItemRepository;

    @Autowired
    private InventoryEntryRepository inventoryEntryRepository;

    private final Store randomStore = new Store("store0", "860-345-1920", "Glendale", "Large"); 

    private final Inventory storeInventory = new Inventory(0, "GlendaleStore0", randomStore);

    private final RawItem rawItemMilk = new RawItem("rawitem0", "Milk", 8.50, "One gallon of whole milk");
    
    private final PackagedItem packagedItemChips = new PackagedItem("packageditem0", "Chips", 1.50, "One small bag of plain chips");
    
    private final InventoryEntry milkEntry = new InventoryEntry("invEntry0", storeInventory, 10, rawItemMilk, null); 

    private final InventoryEntry chipsEntry = new InventoryEntry("invEntry1", storeInventory, 8, null, packagedItemChips); 

    @BeforeEach
    private void setup() {
	    inventoryRepository.saveAndFlush(storeInventory);
	    rawItemRepository.saveAndFlush(rawItemMilk);
	    packagedItemRepository.saveAndFlush(packagedItemChips);
	    inventoryEntryRepository.saveAndFlush(milkEntry);
	    inventoryEntryRepository.saveAndFlush(chipsEntry);
    }
    
    @Test
    @Order(1)
    public void testSaveInventoryEntryAndGetByID() {
	    InventoryEntry inventoryEntry2 = inventoryEntryRepository.findByInventoryEntryID("invEntry0");

	    log.info(inventoryEntry2.toString());
	
	    assertNotNull(milkEntry);
	    assertEquals(inventoryEntry2.getInventoryEntryID(), milkEntry.getInventoryEntryID());
    }
    
    @Test
    @Order(2)
    public void testGetInventoryEntryByID() {
    	InventoryEntry inventoryEntry2 = inventoryEntryRepository.findByInventoryEntryID("invEntry0");

    	assertNotNull(inventoryEntry2);
    	assertNotNull(milkEntry);
    	assertEquals(inventoryEntry2.getInventoryEntryID(), milkEntry.getInventoryEntryID());
    }

    @Test
    @Order(3)
    public void testDeleteInventoryEntry() {
    	inventoryEntryRepository.delete(milkEntry);
    	inventoryRepository.flush();
    }
    
    @Test
    @Order(4)
    public void testFindAllInventoryEntries() {
    	assertNotNull(inventoryEntryRepository.findAll());
    }

    @Test
    @Order(5)
    public void testInventoryEntryModifiers() {
        Store testStore = new Store("T", "T", "T", "T"); 
        Inventory storeInventory = new Inventory(0, "T", testStore);
        RawItem rawTestItem = new RawItem("T", "T", -1, "T");
        PackagedItem packagedTestItem = new PackagedItem("T", "T", -1, "T");
        inventoryRepository.saveAndFlush(storeInventory);
        rawItemRepository.saveAndFlush(rawTestItem);
        packagedItemRepository.saveAndFlush(packagedTestItem);
        InventoryEntry inventoryEntry2 = inventoryEntryRepository.findByInventoryEntryID("invEntry0");
        inventoryEntry2.setInventory(storeInventory);
        inventoryEntry2.setItemQuantity(-1);
        inventoryEntry2.setRawItem(rawTestItem);
        inventoryEntry2.setPackagedItem(packagedTestItem);
        inventoryEntryRepository.save(inventoryEntry2);

        
        inventoryEntry2 = inventoryEntryRepository.findByInventoryEntryID("invEntry0");
        assertEquals(inventoryEntry2.getInventory().getStore().getPhone(), "T");
        assertEquals(inventoryEntry2.getItemQuantity(), -1);
        assertEquals(inventoryEntry2.getRawItem(), rawTestItem);
        assertEquals(inventoryEntry2.getPackagedItem(), packagedTestItem);
    }

    @Test
    @Order(6)
    public void testFindInventoryEntryUsingJpql() {
        InventoryEntry inventoryEntry2 = inventoryEntryRepository.findByInventoryEntryIDJpql("invEntry1");

        assertNotNull(inventoryEntry2);
        assertNotNull(chipsEntry);
    	assertEquals(inventoryEntry2.getInventoryEntryID(), chipsEntry.getInventoryEntryID());
    }
}
