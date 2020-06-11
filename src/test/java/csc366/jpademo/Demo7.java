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

// Demo0: Add, list, and remove Audit, Regualtor, & Store instances

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
public class Demo7 {

    private final static Logger log = LoggerFactory.getLogger(Demo7.class);
    
    @Autowired
	private AuditRepository auditRepository;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private RegulatorRepository regulatorRepository;

    private final Audit audit = new Audit("audit1", "123", "safety");
	private final Regulator regulator = new Regulator("Jane", "Doe", "IRS", "Agent1", "Inspector");
	private final Store store = new Store("Store1", "1234567890", "123 Center Dr", "200 sqft");
    
    @BeforeEach
    private void setup() {
	auditRepository.saveAndFlush(audit);
    }
    
    @Test
    @Order(1)
    public void testAuditRegulatorAndStore() {
	Audit a = auditRepository.findByAuditID("audit1");
	store.addAudit(a);
	a = auditRepository.save(a);
	regulator.addAudit(a);
	a = auditRepository.save(a);

	log.info(a.getStore().toString());
	log.info(a.getRegulator().toString());

	assertEquals(a.getStore(), store);
	assertEquals(a.getRegulator(), regulator);
    }

    @Test
    @Order(2)
    public void testAuditStoreQuery() {
	Audit a2 = auditRepository.findByAuditID("audit1");
	assertNotNull(a2);
	assertEquals(a2.getAuditID(), audit.getAuditID());
	assertEquals(a2.getDate(), audit.getDate());
    }

    @Test
    @Order(3)
    public void testJpqlJoin() {
	Audit a = auditRepository.findByAuditID("audit1");
	store.addAudit(a);
	storeRepository.saveAndFlush(store);
	a = auditRepository.save(a);
	a = auditRepository.findByAuditIDWithStoreJpql("audit1");
	log.info(a.toString());
    }

	@Test
	@Order(4)
	public void testJpqlJoin2() {
	Audit a = auditRepository.findByAuditID("audit1");
	regulator.addAudit(a);
	regulatorRepository.saveAndFlush(regulator);
	a = auditRepository.save(a);
	a = auditRepository.findByAuditIDWithRegulatorJpql("audit1");
	log.info(a.toString());
	}

}
