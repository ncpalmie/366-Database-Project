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

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Demo0: Add, list, and remove Store & Audit instances

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
	"spring.main.banner-mode=off",
	"logging.level.root=ERROR",
	"logging.level.csc366=DEBUG",

	"spring.jpa.hibernate.ddl-auto=update",
	"spring.datasource.url=jdbc:mysql://mysql.labthreesixsix.com/csc366",
	"spring.datasource.username=jpa",
	"spring.datasource.password=demo",
	
	"logging.level.org.hibernate.SQL=DEBUG",
	"logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE", // display prepared statement parameters
	"spring.jpa.properties.hibernate.format_sql=true",
	"spring.jpa.show-sql=false",   // prevent duplicate logging
	"spring.jpa.properties.hibernate.show_sql=false",	
	
	"logging.pattern.console= %d{yyyy-MM-dd HH:mm:ss} - %msg%n"
})
@TestMethodOrder(OrderAnnotation.class)
public class Demo9 {

    private final static Logger log = LoggerFactory.getLogger(Demo9.class);
    
    @Autowired
	private StoreRepository storeRepository;

    private final Audit audit = new Audit("audit1", "123", "safety");
	private final Store store = new Store("Store1", "1234567890", "123 Center Dr", "200 sqft");
    
    @BeforeEach
    private void setup() {
	storeRepository.saveAndFlush(store);
	store.addAudit(audit);
	storeRepository.saveAndFlush(store);
    }
    
    @Test
    @Order(1)
    public void testStoreAndAudit() {
	Store store2 = storeRepository.findByStoreID("Store1");

	log.info(store2.toString());

	assertNotNull(store);

	assertEquals(store2.getAudits().size(), 1);
    }
    
    @Test
    @Order(2)
    public void testStoreAuditQuery() {
	Store store2 = storeRepository.findByStoreID("Store1");
	assertNotNull(store);
	assertEquals(store2.getStoreID(), store.getStoreID());
	assertEquals(store2.getStoreSize(), store.getStoreSize());
    }


    @Test
    @Order(3)
    public void testStoreAudit() {
	Store s = storeRepository.findByStoreID("Store1");
        Audit a = new ArrayList<Audit>(s.getAudits()).get(0);
	s.removeAudit(a);
	storeRepository.save(s);
        log.info(s.toString());
    }

    @Test
    @Order(4)
    public void testRemoveAuditAndFlush() {
	Store s = storeRepository.findByStoreID("Store1");
	Audit a = new ArrayList<Audit>(s.getAudits()).get(0);
	s.removeAudit(a);
	storeRepository.saveAndFlush(s);
        log.info(s.toString());
    }

    @Test
    @Order(5)
    public void testJpqlJoin() {
	Store s = storeRepository.findByStoreIDWithAuditJpql("Store1");
	log.info(s.toString());

	s.addAudit(new Audit("audit2", "321", "Quality"));
	storeRepository.saveAndFlush(s);

	s = storeRepository.findByStoreIDWithAuditJpql("Store1");
	log.info(s.toString());
    }

}
