package csc366.jpademo;

import java.util.Date;
import java.text.SimpleDateFormat;

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

// Demo0: Add, list, and remove Person instances

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
public class SupplyContractTests {

    private final static Logger log = LoggerFactory.getLogger(SupplyContractTests.class);
    
    @Autowired
    private SupplyContractRepository supplyContractRepository;

    private Date startDate = stringToDate("2019-04-13");
    private Date endDate = stringToDate("2020-04-14");
    
    private final RawItem rawItemMilk = new RawItem("rawitem0", "Milk", 8.50, "One gallon of whole milk");

    private final SupplyContract milkContract = new SupplyContract("contract0", "weekly", 20, startDate, endDate, rawItemMilk, null);
    
    public static Date stringToDate(String dateStr) {
        Date dateObj = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateObj = df.parse(dateStr);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        return dateObj;
    }

    @BeforeEach
    private void setup() {
	    supplyContractRepository.saveAndFlush(milkContract);
    }
    
    @Test
    @Order(1)
    public void testSaveSupplyContract() {
	    SupplyContract contract2 = supplyContractRepository.findByContractID("contract0");

	    log.info(contract2.toString());
	
	    assertNotNull(milkContract);
	    assertEquals(contract2.getContractID(), milkContract.getContractID());
    }
    
    @Test
    @Order(2)
    public void testGetSupplyContract() {
	    SupplyContract contract2 = supplyContractRepository.findByContractID("contract0");
	    assertNotNull(milkContract);
	    assertEquals(contract2.getContractID(), milkContract.getContractID());
    }

    @Test
    @Order(3)
    public void testDeleteSupplyContract() {
    	supplyContractRepository.delete(milkContract);
    	supplyContractRepository.flush();
    }
    
    @Test
    @Order(4)
    public void testFindAllSupplyContracts() {
    	assertNotNull(supplyContractRepository.findAll());
    }

}
