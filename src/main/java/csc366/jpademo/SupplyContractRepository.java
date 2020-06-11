package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface SupplyContractRepository extends JpaRepository<SupplyContract, Long>{

    SupplyContract findByContractID(String contractID);

    // JPQL query
    @Query("from SupplyContract o where o.contractID = :contractID")
    SupplyContract findByContractIDJpql(@Param("contractID") String contractID);

    @Modifying
    @Query("update SupplyContract o set o.deliveryFrequency = :newFrequency where o.contractID = :givenContractID")
    void updateContractFrequency(@Param("givenContractID") String givenContractID, @Param("newFrequency") String newFrequency);

}
