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

// OwnerTests: Add, list, and remove Owner instances

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
public class OwnerTests {

    private final static Logger log = LoggerFactory.getLogger(OwnerTests.class);
    
    @Autowired
    private OwnerRepository ownerRepository;

    private final Owner owner = new Owner("test", "test", "test@calpoly.edu", "123-45-6789", "02/02/77", "123-456-7890");
    
    @BeforeEach
    private void setup() {
	ownerRepository.saveAndFlush(owner);
    }
    
    @Test
    @Order(1)
    public void testSaveOwner() {
	Owner owner2 = ownerRepository.findByFirstName("test");

	log.info(owner2.toString());
	
	assertNotNull(owner);
	assertEquals(owner2.getFirstName(), owner.getFirstName());
	assertEquals(owner2.getLastName(), owner.getLastName());
    }
    
    @Test
    @Order(2)
    public void testGetOwner() {
	Owner owner2 = ownerRepository.findByFirstName("test");
	assertNotNull(owner);
	assertEquals(owner2.getFirstName(), owner.getFirstName());
	assertEquals(owner2.getLastName(), owner.getLastName());
    }

    @Test
    @Order(3)
    public void testDeleteOwner() {
	ownerRepository.delete(owner);
	ownerRepository.flush();
    }
    
    @Test
    @Order(4)
    public void testFindAllOwners() {
	assertNotNull(ownerRepository.findAll());
    }
    
    @Test
    @Order(5)
    public void testDeletByOwnerId() {
	Owner e = ownerRepository.findByFirstName("test");
	ownerRepository.deleteById(e.getId());
	ownerRepository.flush();
    }

    @Test
    @Order(6)
    public void testJpqlFinder() {
	Owner e = ownerRepository.findByNameJpql("test");
	assertEquals(e.getFirstName(), owner.getFirstName());
    }

    @Test
    @Order(7)
    public void testSqlFinder() {
	Owner p = ownerRepository.findByNameSql("test");
	assertEquals(p.getFirstName(), owner.getFirstName());
    }

}
