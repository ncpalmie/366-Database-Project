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
public class MenuItemTests {

    private final static Logger log = LoggerFactory.getLogger(MenuItemTests.class);
    
    @Autowired
    private MenuItemRepository menuItemRepository;
 
    @Autowired
    private PackagedItemRepository packagedItemRepository;

    private final PackagedItem packagedItemChips = new PackagedItem("packageditem0", "Chips", 1.50, "One small bag of plain chips");

    private final MenuItem menuItem = new MenuItem(packagedItemChips.getItemID(), "Plain Chips", 2.10, "One small bag of plain chips", null, packagedItemChips); 
    
    @BeforeEach
    private void setup() {
        packagedItemRepository.saveAndFlush(packagedItemChips);
	    menuItemRepository.saveAndFlush(menuItem);
    }
    
    @Test
    @Order(1)
    public void testSaveMenuItemAndGetByID() {
	    MenuItem menuItem2 = menuItemRepository.findByMenuItemID("packageditem0");

	    log.info(menuItem2.toString());
	
	    assertNotNull(menuItem);
	    assertEquals(menuItem2.getMenuItemID(), menuItem.getMenuItemID());
    }
    
}
