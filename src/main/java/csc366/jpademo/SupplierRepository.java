package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>{

    Supplier findBySupplierID(String supplierID);
    Supplier findBySupplierName(String supplierName);

    // JPQL query
    @Query("from Supplier o where o.supplierID = :supplierID")
    Supplier findBySupplierIDJpql(@Param("supplierID") String supplierID);

    // Native SQL query
    @Query(value = "select * from Supplier as o where o.supplierID = :supplierID", nativeQuery = true)
    Supplier findBySupplierIDSql(@Param("supplierID") String supplierID);

    @Modifying
    @Query("update Supplier o set o.phone = :newPhone where o.supplierID = :givenSupplierID")
    void updateSupplierPhone(@Param("givenSupplierID") String givenSupplierID, @Param("newPhone") String newPhone);

}
