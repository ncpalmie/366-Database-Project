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

// Demo0: Add, list, and remove Owner & Store instances

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
public class Demo5 {

    private final static Logger log = LoggerFactory.getLogger(Demo5.class);
    
    @Autowired
	private OwnerRepository ownerRepository;

    private final Owner owner = new Owner("Jane", "Doe", "test@calpoly.edu", "123-45-6789", "02/02/77", "123-456-7890");
	private final Store store = new Store("Store1", "1234567890", "123 Center Dr", "200 sqft");
    
    @BeforeEach
    private void setup() {
	ownerRepository.saveAndFlush(owner);
	owner.addStore(store);
	ownerRepository.saveAndFlush(owner);
    }
    
    @Test
    @Order(1)
    public void testOwnerAndStore() {
	Owner o2 = ownerRepository.findByFirstName("Jane");

	log.info(o2.toString());

	assertNotNull(owner);

	assertEquals(o2.getStores().size(), 1);
    }
    
    @Test
    @Order(2)
    public void testOwnerAndStoreQuery() {
	Owner o2 = ownerRepository.findByFirstName("Jane");
	assertNotNull(owner);
	assertEquals(o2.getFirstName(), owner.getFirstName());
	assertEquals(o2.getLastName(), owner.getLastName());
    }


    @Test
    @Order(3)
    public void testRemoveStore() {
	Owner o = ownerRepository.findByFirstName("Jane");
        Store s = new ArrayList<Store>(o.getStores()).get(0);
	o.removeStore(s);
	ownerRepository.save(o);
        log.info(o.toString());
    }

    @Test
    @Order(4)
    public void testRemoveStoreAndFlush() {
	Owner o = ownerRepository.findByFirstName("Jane");
        Store s = new ArrayList<Store>(o.getStores()).get(0);
	o.removeStore(s);
	ownerRepository.saveAndFlush(o);
        log.info(o.toString());
    }

    @Test
    @Order(5)
    public void testJpqlJoin() {
	Owner o = ownerRepository.findByNameWithStoreJpql("Jane");
	log.info(o.toString());

	o.addStore(new Store("Store2", "0987654321", "123 Outer Dr", "100 sqft"));
	ownerRepository.saveAndFlush(o);

	o = ownerRepository.findByNameWithStoreJpql("Jane");
	log.info(o.toString());
    }

}
