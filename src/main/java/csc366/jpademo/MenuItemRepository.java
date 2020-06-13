package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    
    MenuItem findByid(Long id);
    MenuItem findByMenuItemID(String menuItemID);

    @Query("from MenuItem c where c.id = :id")
    MenuItem findByMenuItemIdJpql(@Param("id") Long id);

    @Query("from MenuItem c where c.name = :name")
    MenuItem findByMenuItemCustEmailJpql(@Param("name") String name);

    @Query("from MenuItem c where c.cost = :cost")
    MenuItem findByMenuItemTotalCostJpql(@Param("cost") float cost);

}
