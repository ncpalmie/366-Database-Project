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
public class RawItemTests {

    private final static Logger log = LoggerFactory.getLogger(RawItemTests.class);
    
    @Autowired
    private RawItemRepository rawItemRepository;

    private final RawItem rawItemMilk = new RawItem("rawitem0", "Milk", 8.50, "One gallon of whole milk");
    
    @BeforeEach
    private void setup() {
	    rawItemRepository.saveAndFlush(rawItemMilk);
    }
    
    @Test
    @Order(1)
    public void testSaveRawItem() {
	    RawItem rawItem2 = rawItemRepository.findByItemName("Milk");

	    log.info(rawItem2.toString());
	
	    assertNotNull(rawItemMilk);
	    assertEquals(rawItem2.getItemID(), rawItemMilk.getItemID());
    }
    
    @Test
    @Order(2)
    public void testGetFromItemID() {
    	RawItem rawItem2 = rawItemRepository.findByItemID("rawitem0");
	    assertNotNull(rawItemMilk);
	    assertEquals(rawItem2.getItemID(), rawItemMilk.getItemID());
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

}
