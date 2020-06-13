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

// CustomerOrderTests: Add, list, and remove Customer & Order instances

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
public class CustomerAndOrderTests {

    private final static Logger log = LoggerFactory.getLogger(CustomerAndOrderTests.class);
    
    @Autowired
	private CustomerRepository customerRepository;

    private final Customer customer = new Customer("marti@backtothefuture.com", "Marti", "McFly", "06/04/1968", "999-999-9999");
	private final csc366.jpademo.Order order = new csc366.jpademo.Order(55.0);
    
    @BeforeEach
    private void setup() {
        customerRepository.saveAndFlush(customer);
        customer.addOrder(order);
        customerRepository.saveAndFlush(customer);
    }
    
    @Test
    @Order(1)
    public void testCustomerAndOrder() {
        Customer o2 = customerRepository.findByFirstName("Marti");

        log.info(o2.toString());

        assertNotNull(customer);

        assertEquals(o2.getOrders().size(), 1);
    }
    
    @Test
    @Order(2)
    public void testCustomerAndOrderQuery() {
	Customer o2 = customerRepository.findByFirstName("Marti");
	assertNotNull(customer);
	assertEquals(o2.getFirstName(), customer.getFirstName());
	assertEquals(o2.getLastName(), customer.getLastName());
    }


    @Test
    @Order(3)
    public void testRemoveOrder() {
	Customer o = customerRepository.findByFirstName("Marti");
    csc366.jpademo.Order s = new ArrayList<csc366.jpademo.Order>(o.getOrders()).get(0);
	o.removeOrder(s);
	customerRepository.save(o);
        log.info(o.toString());
    }

    @Test
    @Order(4)
    public void testRemoveOrderAndFlush() {
	Customer o = customerRepository.findByFirstName("Marti");
    csc366.jpademo.Order s = new ArrayList<csc366.jpademo.Order>(o.getOrders()).get(0);
	o.removeOrder(s);
	customerRepository.saveAndFlush(o);
        log.info(o.toString());
    }

}
