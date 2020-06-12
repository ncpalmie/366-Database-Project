package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    Order findByid(Long id);

    @Query("from Order o where o.id = :id")
    Order findByOrderIdJpql(@Param("id") Long id);

    @Query("from Order o where o.totalCost = :cost")
    Order findByOrderTotalCostJpql(@Param("cost") float cost);

}