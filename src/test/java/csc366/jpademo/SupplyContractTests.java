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

    private final Supplier dairySupplier = new Supplier("supplier0", "Joshua Farms", "dairy", "jfarms991@gmail.com",
                                                        "203-897-1033");

    private final Store randomStore = new Store("store0", "860-345-1920", "Glendale", "Large"); 

    private final SupplyContract milkContract = new SupplyContract("contract0", "weekly", 20, startDate, endDate, dairySupplier, rawItemMilk, null, randomStore);
    
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
        assertNotNull(contract2);
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

    @Test
    @Order(5)
    public void testSupplyContractModifiers() {
        SupplyContract contract2 = supplyContractRepository.findByContractID("contract0");
        Date testStart = stringToDate("0000-00-00");
        Date testEnd = stringToDate("1111-11-11");
        contract2.setDeliveryFrequency("test frequency");
        contract2.setDeliveryAmount(9999);
        contract2.setStartDate(testStart);
        contract2.setEndDate(testEnd);
        contract2.setSupplier(new Supplier("T", "T", "T", "T", "T"));
        contract2.setRawItem(new RawItem("T", "T", 0, "T"));
        contract2.setPackagedItem(new PackagedItem("T", "T", 0, "T"));
        contract2.setStoreSupplied(new Store("T", "T", "T", "T")); 
        supplyContractRepository.save(contract2);

        
        contract2 = supplyContractRepository.findByContractID("contract0");
        assertEquals(contract2.getDeliveryFrequency(), "test frequency");
        assertEquals(contract2.getDeliveryAmount(), 9999);
        assertEquals(contract2.getStartDate(), testStart);
        assertEquals(contract2.getEndDate(), testEnd);
        assertEquals(contract2.getSupplier().getSupplierName(), "T");
        assertEquals(contract2.getRawItem().getItemName(), "T");
        assertEquals(contract2.getPackagedItem().getItemName(), "T");
        assertEquals(contract2.getStoreSupplied().getPhone(), "T");
    }

    @Test
    @Order(6)
    public void testFindUsingJpql() {
        SupplyContract contract2 = supplyContractRepository.findByContractIDJpql("contract0");

        assertNotNull(contract2);
        assertNotNull(milkContract);
    	assertEquals(contract2.getContractID(), milkContract.getContractID());
    }

}
