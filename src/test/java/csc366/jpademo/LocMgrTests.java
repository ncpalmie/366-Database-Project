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

// locMgrTests: Add, list, and remove LocMgr instances

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
public class LocMgrTests {

    private final static Logger log = LoggerFactory.getLogger(LocMgrTests.class);
    
    @Autowired
    private LocMgrRepository locMgrRepository;

    @Autowired
    private StoreRepository storeRepository;

    private final LocMgr locMgr = new LocMgr("test", "test", "test@calpoly.edu", "123-45-6789", "02/02/77", "123-456-7890");
    
    private final Store randomStore = new Store("store0", "860-345-1920", "Glendale", "Large"); 

    @BeforeEach
    private void setup() {
    storeRepository.saveAndFlush(randomStore);
    locMgr.setStore(randomStore);
	locMgrRepository.saveAndFlush(locMgr);
    }
    
    @Test
    @Order(1)
    public void testSaveLocMgr() {
	LocMgr locMgr2 = locMgrRepository.findByFirstName("test");

	log.info(locMgr2.toString());
	
	assertNotNull(locMgr);
	assertEquals(locMgr2.getFirstName(), locMgr.getFirstName());
	assertEquals(locMgr2.getLastName(), locMgr.getLastName());
    }
    
    @Test
    @Order(2)
    public void testGetLocMgr() {
	LocMgr locMgr2 = locMgrRepository.findByFirstName("test");
	assertNotNull(locMgr);
	assertEquals(locMgr2.getFirstName(), locMgr.getFirstName());
	assertEquals(locMgr2.getLastName(), locMgr.getLastName());
    }

    @Test
    @Order(3)
    public void testDeleteLocMgr() {
	locMgrRepository.delete(locMgr);
	locMgrRepository.flush();
    }
    
    @Test
    @Order(4)
    public void testFindAllLocMgrs() {
	assertNotNull(locMgrRepository.findAll());
    }
    
    @Test
    @Order(5)
    public void testDeleteByLocMgrId() {
	LocMgr e = locMgrRepository.findByFirstName("test");
	locMgrRepository.deleteById(e.getId());
	locMgrRepository.flush();
    }

    @Test
    @Order(6)
    public void testJpqlFinder() {
	LocMgr e = locMgrRepository.findByNameJpql("test");
	assertEquals(e.getFirstName(), locMgr.getFirstName());
    }

}
