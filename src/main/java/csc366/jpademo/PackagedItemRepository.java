package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface PackagedItemRepository extends JpaRepository<PackagedItem, Long>{

    PackagedItem findByItemID(String itemID);
    PackagedItem findByItemName(String itemName);

    // JPQL query
    @Query("from PackagedItem o where o.itemID = :itemID")
    PackagedItem findByItemIDJpql(@Param("itemID") String itemID);

    @Modifying
    @Query("update PackagedItem o set o.itemCost = :newItemCost where o.itemID = :givenItemID")
    void updateItemCost(@Param("givenItemID") String givenitemID, @Param("newItemCost") double newItemCost);

}
