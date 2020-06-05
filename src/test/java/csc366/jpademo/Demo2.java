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

// Demo4: Add, list, and remove Regulator instances

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
public class Demo2 {

    private final static Logger log = LoggerFactory.getLogger(Demo2.class);
    
    @Autowired
    private RegulatorRepository regulatorRepository;

    private final Regulator regulator = new Regulator("test", "test", "test@", "123", "boss");  // "reference" person
    
    @BeforeEach
    private void setup() {
	regulatorRepository.saveAndFlush(regulator);
    }
    
    @Test
    @Order(1)
    public void testSaveRegulator() {
	Regulator regulator2 = regulatorRepository.findByFirstName("test");

	log.info(regulator2.toString());
	
	assertNotNull(regulator);
	assertEquals(regulator2.getFirstName(), regulator.getFirstName());
	assertEquals(regulator2.getLastName(), regulator.getLastName());
    }
    
    @Test
    @Order(2)
    public void testGetRegulator() {
	Regulator regulator2 = regulatorRepository.findByFirstName("test");
	assertNotNull(regulator);
	assertEquals(regulator2.getFirstName(), regulator.getFirstName());
	assertEquals(regulator2.getLastName(), regulator.getLastName());
    }

    @Test
    @Order(3)
    public void testDeleteRegulator() {
	regulatorRepository.delete(regulator);
	regulatorRepository.flush();
    }
    
    @Test
    @Order(4)
    public void testFindAllRegulator() {
	assertNotNull(regulatorRepository.findAll());
    }
    
    @Test
    @Order(5)
    public void testDeletByRegulatorId() {
	Regulator e = regulatorRepository.findByFirstName("test");
	regulatorRepository.deleteById(e.getId());
	regulatorRepository.flush();
    }

    @Test
    @Order(6)
    public void testJpqlFinder() {
	Regulator e = regulatorRepository.findByNameJpql("test");
	assertEquals(e.getFirstName(), regulator.getFirstName());
    }

    @Test
    @Order(7)
    public void testSqlFinder() {
	Regulator p = regulatorRepository.findByNameSql("test");
	assertEquals(p.getFirstName(), regulator.getFirstName());
    }

}
