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

// Demo5: Add, list, and remove Audit instances

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
public class Demo6 {

    private final static Logger log = LoggerFactory.getLogger(Demo6.class);
    
    @Autowired
    private AuditRepository auditRepository;

    private final Audit audit = new Audit("test", "123", "safety");
    
    @BeforeEach
    private void setup() {
	auditRepository.saveAndFlush(audit);
    }
    
    @Test
    @Order(1)
    public void testSaveAudit() {
	Audit audit2 = auditRepository.findByAuditID("test");

	log.info(audit2.toString());
	
	assertNotNull(audit);
	assertEquals(audit2.getAuditID(), audit.getAuditID());
    }
    
    @Test
    @Order(2)
    public void testGetAudit() {
	Audit audit2 = auditRepository.findByAuditID("test");
	assertNotNull(audit);
	assertEquals(audit2.getAuditID(), audit.getAuditID());
    }

    @Test
    @Order(3)
    public void testDeleteAudit() {
	auditRepository.delete(audit);
	auditRepository.flush();
    }
    
    @Test
    @Order(4)
    public void testFindAllAudit() {
	assertNotNull(auditRepository.findAll());
    }
    
    @Test
    @Order(5)
    public void testDeletByAuditId() {
	Audit e = auditRepository.findByAuditID("test");
	auditRepository.deleteById(e.getId());
	auditRepository.flush();
    }

    @Test
    @Order(6)
    public void testJpqlFinder() {
	Audit e = auditRepository.findByAuditIDJpql("test");
	assertEquals(e.getAuditID(), audit.getAuditID());
    }

    @Test
    @Order(7)
    public void testSqlFinder() {
	Audit p = auditRepository.findByAuditIDSql("test");
	assertEquals(p.getAuditID(), audit.getAuditID());
    }

}
