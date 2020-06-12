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

// StoreTests: Add, list, and remove Store instances

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
public class StoreTests {

    private final static Logger log = LoggerFactory.getLogger(StoreTests.class);
    
    @Autowired
    private StoreRepository storeRepository;

    private final Store store = new Store("test", "1234567890", "Center", "200 ft");
    
    @BeforeEach
    private void setup() {
	storeRepository.saveAndFlush(store);
    }
    
    @Test
    @Order(1)
    public void testSaveStore() {
	Store store2 = storeRepository.findByStoreID("test");

	log.info(store2.toString());
	
	assertNotNull(store);
	assertEquals(store2.getStoreID(), store.getStoreID());
    }
    
    @Test
    @Order(2)
    public void testGetStore() {
	Store store2 = storeRepository.findByStoreID("test");
	assertNotNull(store);
	assertEquals(store2.getStoreID(), store.getStoreID());
    }

    @Test
    @Order(3)
    public void testDeleteStore() {
	storeRepository.delete(store);
	storeRepository.flush();
    }
    
    @Test
    @Order(4)
    public void testFindAllStore() {
	assertNotNull(storeRepository.findAll());
    }
    
    @Test
    @Order(5)
    public void testDeletByStoreId() {
	Store e = storeRepository.findByStoreID("test");
	storeRepository.deleteById(e.getId());
	storeRepository.flush();
    }

    @Test
    @Order(6)
    public void testJpqlFinder() {
	Store e = storeRepository.findByStoreIDJpql("test");
	assertEquals(e.getStoreID(), store.getStoreID());
    }

    @Test
    @Order(7)
    public void testSqlFinder() {
	Store p = storeRepository.findByStoreIDSql("test");
	assertEquals(p.getStoreID(), store.getStoreID());
    }

}
