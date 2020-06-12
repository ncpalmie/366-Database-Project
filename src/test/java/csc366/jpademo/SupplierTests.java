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
public class SupplierTests {

    private final static Logger log = LoggerFactory.getLogger(SupplierTests.class);
    
    @Autowired
    private SupplierRepository supplierRepository;

    private final Supplier dairySupplier = new Supplier("supplier0", "Joshua Farms", "dairy", "jfarms991@gmail.com",
                                                        "203-897-1033");
    
    @BeforeEach
    private void setup() {
	    supplierRepository.saveAndFlush(dairySupplier);
    }
    
    @Test
    @Order(1)
    public void testSaveSupplierAndGetByName() {
	    Supplier supplier2 = supplierRepository.findBySupplierName("Joshua Farms");

	    log.info(supplier2.toString());
	
	    assertNotNull(dairySupplier);
	    assertEquals(supplier2.getSupplierID(), dairySupplier.getSupplierID());
    }
    
    @Test
    @Order(2)
    public void testGetSupplierByID() {
    	Supplier supplier2 = supplierRepository.findBySupplierID("supplier0");
    	assertNotNull(dairySupplier);
    	assertEquals(supplier2.getSupplierName(), dairySupplier.getSupplierName());
    }

    @Test
    @Order(3)
    public void testDeleteSupplier() {
    	supplierRepository.delete(dairySupplier);
    	supplierRepository.flush();
    }
    
    @Test
    @Order(4)
    public void testFindAllSuppliers() {
    	assertNotNull(supplierRepository.findAll());
    }

    @Test
    @Order(5)
    public void testSupplierModifiers() {
        Supplier supplier2 = supplierRepository.findBySupplierID("supplier0");
        supplier2.setSupplierPhone("999-888-7777");
        supplier2.setSupplierEmail("test@gmail.com");
        supplier2.setSupplierName("test supplier");
        supplier2.setSupplierType("test type");
        supplierRepository.save(supplier2);

        
        supplier2 = supplierRepository.findBySupplierID("supplier0");
        assertEquals(supplier2.getSupplierPhone(), "999-888-7777");
        assertEquals(supplier2.getSupplierEmail(), "test@gmail.com");
        assertEquals(supplier2.getSupplierName(), "test supplier");
        assertEquals(supplier2.getSupplierType(), "test type");
    }

    @Test
    @Order(6)
    public void testFindUsingJpql() {
        Supplier supplier2 = supplierRepository.findBySupplierIDJpql("supplier0");

        assertNotNull(supplier2);
        assertNotNull(dairySupplier);
    	assertEquals(supplier2.getSupplierID(), dairySupplier.getSupplierID());
    }
}
