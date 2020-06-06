package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long>{

    Audit findByAuditID(String auditID);

    // JPQL query
    @Query("from Audit o where o.auditID = :auditID")
    Audit findByAuditIDJpql(@Param("auditID") String auditID);

    @Query("select p from Audit p join p.store store where p.auditID = :id")
    Audit findByAuditIDWithStoreJpql(@Param("id") String id);

    @Query("select p from Audit p join p.regulator regulator where p.auditID = :id")
    Audit findByAuditIDWithRegulatorJpql(@Param("id") String id);
    
    // Native SQL query
    @Query(value = "select * from Audit as o where o.auditID = :auditID", nativeQuery = true)
    Audit findByAuditIDSql(@Param("auditID") String auditID);

    @Modifying
    @Query("update Audit o set o.auditID = :newAuditID where o.auditID = :oldAuditID")
    void updateID(@Param("oldAuditID") String oldAuditID, @Param("newAuditID") String newAuditID);

}
