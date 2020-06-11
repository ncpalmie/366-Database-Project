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

// Demo0: Add, list, and remove Regulator & Audit instances

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
public class Demo3 {

    private final static Logger log = LoggerFactory.getLogger(Demo3.class);
    
    @Autowired
	private RegulatorRepository regulatorRepository;

    private final Audit audit = new Audit("audit1", "123", "safety");
	private final Regulator regulator = new Regulator("Jane", "Doe", "IRS", "Agent1", "Inspector");
	private final Store store = new Store("Store1", "1234567890", "123 Center Dr", "200 sqft");
    
    @BeforeEach
    private void setup() {
	regulatorRepository.saveAndFlush(regulator);
	regulator.addAudit(audit);
	regulatorRepository.saveAndFlush(regulator);
    }
    
    @Test
    @Order(1)
    public void testRegulatorAndAudit() {
	Regulator reg2 = regulatorRepository.findByFirstName("Jane");

	log.info(reg2.toString());

	assertNotNull(regulator);

	assertEquals(reg2.getAudits().size(), 1);
    }
    
    @Test
    @Order(2)
    public void testRegulatorAuditQuery() {
	Regulator reg2 = regulatorRepository.findByFirstName("Jane");
	assertNotNull(regulator);
	assertEquals(reg2.getFirstName(), regulator.getFirstName());
	assertEquals(reg2.getLastName(), regulator.getLastName());
    }


    @Test
    @Order(3)
    public void testRemoveAudit() {
	Regulator r = regulatorRepository.findByFirstName("Jane");
        Audit a = new ArrayList<Audit>(r.getAudits()).get(0);
	r.removeAudit(a);
	regulatorRepository.save(r);
        log.info(r.toString());
    }

    @Test
    @Order(4)
    public void testRemoveAuditAndFlush() {
	Regulator r = regulatorRepository.findByFirstName("Jane");
        Audit a = new ArrayList<Audit>(r.getAudits()).get(0);
	r.removeAudit(a);
	regulatorRepository.saveAndFlush(r);
        log.info(r.toString());
    }

    @Test
    @Order(5)
    public void testJpqlJoin() {
	Regulator r = regulatorRepository.findByNameWithAuditJpql("Jane");
	log.info(r.toString());

	r.addAudit(new Audit("audit2", "321", "Quality"));
	regulatorRepository.saveAndFlush(r);

	r = regulatorRepository.findByNameWithAuditJpql("Jane");
	log.info(r.toString());
    }

}
