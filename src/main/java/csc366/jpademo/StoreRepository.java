package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>{

    Store findByStoreID(String storeID);

    // JPQL query
    @Query("from Store o where o.storeID = :storeID")
    Store findByStoreIDJpql(@Param("storeID") String storeID);
    
    // Native SQL query
    @Query(value = "select * from Store as o where o.storeID = :storeID", nativeQuery = true)
    Store findByStoreIDSql(@Param("storeID") String storeID);

    @Modifying
    @Query("update Store o set o.storeID = :newStoreID where o.storeID = :oldStoreID")
    void updateStoreID(@Param("oldStoreID") String oldStoreID, @Param("newStoreID") String newStoreID);

}
