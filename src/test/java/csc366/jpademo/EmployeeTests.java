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

// EmployeeTests: Add, list, and remove Employee instances

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

public class EmployeeTests {

    private final static Logger log = LoggerFactory.getLogger(EmployeeTests.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    private final Employee employee = new Employee("jack@daniels.com", "Jack", "Daniels");

    @BeforeEach
    private void setup() {
        employeeRepository.saveAndFlush(employee);
    }

    @Test
    @Order(1)
    public void testSaveEmployee() {
        Employee employee2 = employeeRepository.findByEmployeeEmailJpql("jack@daniels.com");

        log.info(employee.toString());

        assertNotNull(employee);
        assertEquals(employee2.getEmail(), employee.getEmail());
    }

    @Test
    @Order(2)
    public void testGetEmployee() {
        Employee employee2 = employeeRepository.findByEmployeeEmailJpql("jack@daniels.com");
        assertNotNull(employee);
        assertEquals(employee2.getEmail(), employee.getEmail());
    }

    @Test
    @Order(3)
    public void testDeleteEmployee() {
        employeeRepository.delete(employee);
        employeeRepository.flush();
    }

    @Test
    @Order(4)
    public void testFindAllEmployees() {
        assertNotNull(employeeRepository.findAll());
    }

    @Test
    @Order(5)
    public void testDeleteEmployeeById() {
        Employee e = employeeRepository.findByEmployeeEmailJpql("jack@daniels.com");
        employeeRepository.deleteById(e.getId());
        employeeRepository.flush();
    }
    
}