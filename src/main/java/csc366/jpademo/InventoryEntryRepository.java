package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface InventoryEntryRepository extends JpaRepository<InventoryEntry, Long>{

    InventoryEntry findByInventoryEntryID(String inventoryEntryID);

    // JPQL query
    @Query("from InventoryEntry o where o.inventoryEntryID = :entryID")
    InventoryEntry findByInventoryEntryIDJpql(@Param("entryID") String entryID);

    @Modifying
    @Query("update InventoryEntry o set o.itemQuantity = :newQuantity where o.inventoryEntryID = :givenEntryID")
    void updateItemQuantity(@Param("givenEntryID") String givenEntryID, @Param("newQuantity") int newQuantity);

}
