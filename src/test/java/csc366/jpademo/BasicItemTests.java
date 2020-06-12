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
public class BasicItemTests {

    private final static Logger log = LoggerFactory.getLogger(BasicItemTests.class);
    
    @Autowired
    private RawItemRepository rawItemRepository;
    @Autowired
    private PackagedItemRepository packagedItemRepository;

    private final RawItem rawItemMilk = new RawItem("rawitem0", "Milk", 8.50, "One gallon of whole milk");
    
    private final PackagedItem packagedItemChips = new PackagedItem("packageditem0", "Chips", 1.50, "One small bag of plain chips");
    
    @BeforeEach
    private void setup() {
        log.info(rawItemMilk.getItemName());
        log.info(packagedItemChips.getItemName());
	    rawItemRepository.saveAndFlush(rawItemMilk);
	    packagedItemRepository.saveAndFlush(packagedItemChips);
    }
    
    @Test
    @Order(1)
    public void testSaveRawItemAndGetByID() {
	    RawItem rawItem2 = rawItemRepository.findByItemID("rawitem0");

	    log.info(rawItem2.toString());
	
	    assertNotNull(rawItemMilk);
	    assertEquals(rawItem2.getItemID(), rawItemMilk.getItemID());
    }
    
    @Test
    @Order(2)
    public void testGetRawItemByID() {
    	RawItem rawItem2 = rawItemRepository.findByItemID("rawitem0");

    	assertNotNull(rawItemMilk);
    	assertEquals(rawItem2.getItemName(), rawItemMilk.getItemName());
    }

    @Test
    @Order(3)
    public void testDeleteRawItem() {
    	rawItemRepository.delete(rawItemMilk);
    	rawItemRepository.flush();
    }
    
    @Test
    @Order(4)
    public void testFindAllRawItems() {
    	assertNotNull(rawItemRepository.findAll());
    }

    @Test
    @Order(5)
    public void testRawItemModifiers() {
        RawItem rawItem2 = rawItemRepository.findByItemID("rawitem0");
        rawItem2.setItemName("T");
        rawItem2.setItemCost(-1);
        rawItem2.setItemDescription("T");
        rawItemRepository.save(rawItem2);

        
        rawItem2 = rawItemRepository.findByItemID("rawitem0");
        assertEquals(rawItem2.getItemName(), "T");
        assertEquals(rawItem2.getItemCost(), -1);
        assertEquals(rawItem2.getItemDescription(), "T");
    }

    @Test
    @Order(6)
    public void testFindRawUsingJpql() {
        RawItem rawItem2 = rawItemRepository.findByItemIDJpql("rawitem0");

        assertNotNull(rawItem2);
        assertNotNull(rawItemMilk);
    	assertEquals(rawItem2.getItemName(), rawItemMilk.getItemName());
    }

    @Test
    @Order(7)
    public void testSavePackagedItemAndGetByID() {
	    PackagedItem packagedItem2 = packagedItemRepository.findByItemID("packageditem0");

	    log.info(packagedItem2.toString());
	
	    assertNotNull(packagedItemChips);
	    assertEquals(packagedItem2.getItemID(), packagedItemChips.getItemID());
    }
    
    @Test
    @Order(8)
    public void testGetPackagedItemByID() {
    	PackagedItem packagedItem2 = packagedItemRepository.findByItemID("packageditem0");

    	assertNotNull(packagedItemChips);
    	assertEquals(packagedItem2.getItemName(), packagedItemChips.getItemName());
    }

    @Test
    @Order(9)
    public void testDeletePackagedItem() {
    	packagedItemRepository.delete(packagedItemChips);
    	packagedItemRepository.flush();
    }
    
    @Test
    @Order(10)
    public void testFindAllPackagedItems() {
    	assertNotNull(packagedItemRepository.findAll());
    }

    @Test
    @Order(11)
    public void testPackagedItemModifiers() {
        PackagedItem packagedItem2 = packagedItemRepository.findByItemID("packageditem0");
        packagedItem2.setItemName("T");
        packagedItem2.setItemCost(-1);
        packagedItem2.setItemDescription("T");
        packagedItemRepository.save(packagedItem2);

        
        packagedItem2 = packagedItemRepository.findByItemID("packageditem0");
        assertEquals(packagedItem2.getItemName(), "T");
        assertEquals(packagedItem2.getItemCost(), -1);
        assertEquals(packagedItem2.getItemDescription(), "T");
    }

    @Test
    @Order(12)
    public void testFindPackagedUsingJpql() {
        PackagedItem packagedItem2 = packagedItemRepository.findByItemIDJpql("packageditem0");

        assertNotNull(packagedItem2);
        assertNotNull(packagedItemChips);
    	assertEquals(packagedItem2.getItemName(), packagedItemChips.getItemName());
    }
}
