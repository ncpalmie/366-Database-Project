package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{

    Inventory findByInventoryID(int inventoryID);
    Inventory findByInventoryName(String inventoryName);

    // JPQL query
    @Query("from Inventory o where o.inventoryName = :inventoryName")
    Inventory findByInventoryNameJpql(@Param("inventoryName") String inventoryName);

    @Modifying
    @Query("update Inventory o set o.store = :newStore where o.inventoryID = :givenInventoryID")
    void updateInventoryStore(@Param("givenInventoryID") int inventoryID, @Param("newStore") Store newStore);

}
