package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

    /*Order findByid(Long id);

    // JPQL query
    @Query("from `Order` o where o.id = :id")
    Order findByOrderIDJpql(@Param("id") Long id);

    @Query("select p from `Order` p join p.customers customer where p.id = :id")
    Order findByOrderIDWithAuditJpql(@Param("id") Long id);
    
    // Native SQL query
    @Query(value = "select * from `Order` as o where o.id = :id", nativeQuery = true)
    Order findByOrderIDSql(@Param("id") Long id);

    @Modifying
    @Query("update `Order` o set o.id = :newId where o.id = :oldId")
    void updateOrderID(@Param("oldId") Long oldId, @Param("newId") Long newId);*/

}