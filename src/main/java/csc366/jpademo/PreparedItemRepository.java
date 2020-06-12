package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface PreparedItemRepository extends JpaRepository<PreparedItem, Long>{

    PreparedItem findByItemID(int itemID);
    PreparedItem findByItemName(String itemName);

    // JPQL query
    @Query("from PreparedItem o where o.itemName = :itemName")
    PreparedItem findByItemNameJpql(@Param("itemName") String itemName);

    @Modifying
    @Query("update PreparedItem o set o.prepTimeMin = :newPrepTime where o.itemID = :givenItemID")
    void updatePrepTimeMin(@Param("givenItemID") int itemID, @Param("newPrepTime") int newPrepTime);

}
