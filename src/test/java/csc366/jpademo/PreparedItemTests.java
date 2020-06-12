package csc366.jpademo;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

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
public class PreparedItemTests {

    private final static Logger log = LoggerFactory.getLogger(PreparedItemTests.class);
    
    @Autowired
    private PreparedItemRepository preparedItemRepository;

    @Autowired
    private RawItemRepository rawItemRepository;

    private final RawItem rawItemNoodles = new RawItem("rawitem0", "Noodles", 1.10, "One pound of raw macaroni noodles");

    private final RawItem rawItemCheese = new RawItem("rawitem1", "Cheddar Cheese", 3.50, "One half pound of cheddar cheese");
    
    private final List<RawItem> ingredients = new ArrayList<>(Arrays.asList(rawItemNoodles, rawItemCheese));

    private final PreparedItem macAndCheese = new PreparedItem(0, "Mac and Cheese", 2.50, "A bowl of Mac and Cheese", 5, ingredients);

    @BeforeEach
    private void setup() {
        rawItemRepository.saveAndFlush(rawItemNoodles);
        rawItemRepository.saveAndFlush(rawItemCheese);
	    preparedItemRepository.saveAndFlush(macAndCheese);
    }
    
    @Test
    @Order(1)
    public void testSavePreparedItemAndGetByName() {
	    PreparedItem preparedItem2 = preparedItemRepository.findByItemName("Mac and Cheese");

	    log.info(preparedItem2.toString());
	
	    assertNotNull(macAndCheese);
	    assertEquals(preparedItem2.getItemID(), macAndCheese.getItemID());
    }
    
    @Test
    @Order(2)
    public void testGetIngredientsByItemName() {
    	PreparedItem preparedItem2 = preparedItemRepository.findByItemName("Mac and Cheese");
    	assertNotNull(macAndCheese);
    	assertEquals(preparedItem2.getRawItems(), macAndCheese.getRawItems());
    }

    @Test
    @Order(3)
    public void testDeletePreparedItem() {
    	preparedItemRepository.delete(macAndCheese);
    	preparedItemRepository.flush();
    }
    
    @Test
    @Order(4)
    public void testFindAllPreparedItems() {
    	assertNotNull(preparedItemRepository.findAll());
    }

    @Test
    @Order(5)
    public void testPreparedItemModifiers() {
        RawItem rawTestItem = new RawItem("T", "T", 0, "T");
        PreparedItem preparedItem2 = preparedItemRepository.findByItemName("Mac and Cheese");
        preparedItem2.setItemName("T");
        preparedItem2.setItemCost(-1);
        preparedItem2.setItemDescription("T");
        preparedItem2.setPrepTimeMin(-1);
        rawItemRepository.save(rawTestItem);
        preparedItem2.addRawItem(rawTestItem);
        preparedItemRepository.save(preparedItem2);

        
        preparedItem2 = preparedItemRepository.findByItemName("T");
        assertEquals(preparedItem2.getItemName(), "T");
        assertEquals(preparedItem2.getItemCost(), -1);
        assertEquals(preparedItem2.getItemDescription(), "T");
        assertEquals(preparedItem2.getPrepTimeMin(), -1);
        assertEquals(preparedItem2.removeRawItem(rawTestItem), true);
    }

    @Test
    @Order(6)
    public void testFindUsingJpql() {
        PreparedItem preparedItem2 = preparedItemRepository.findByItemNameJpql("Mac and Cheese");

        assertNotNull(preparedItem2);
        assertNotNull(macAndCheese);
    	assertEquals(preparedItem2.getItemID(), macAndCheese.getItemID());
    }
}
