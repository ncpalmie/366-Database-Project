package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface RawItemRepository extends JpaRepository<RawItem, Long>{

    RawItem findByItemID(String itemID);
    RawItem findByItemName(String itemName);

    // JPQL query
    @Query("from RawItem o where o.itemID = :itemID")
    RawItem findByItemIDJpql(@Param("itemID") String itemID);

    @Modifying
    @Query("update RawItem o set o.itemCost = :newItemCost where o.itemID = :givenItemID")
    void updateItemCost(@Param("givenItemID") String givenitemID, @Param("newItemCost") double newItemCost);

}
