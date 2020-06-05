package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface RegulatorRepository extends JpaRepository<Regulator, Long>{

    Regulator findByFirstName(String firstName);

    Regulator findByLastName(String lastName);

    // JPQL query
    @Query("from Regulator o where o.firstName = :name or o.lastName = :name")
    Regulator findByNameJpql(@Param("name") String name);

    @Query("select p from Regulator p join p.audits audit where p.firstName = :name or p.lastName = :name")
    Regulator findByNameWithAuditJpql(@Param("name") String name);
    
    // Native SQL query
    @Query(value = "select * from Regulator as o where o.first_name = :name or o.last_name = :name", nativeQuery = true)
    Regulator findByNameSql(@Param("name") String name);

    @Modifying
    @Query("update Regulator o set o.firstName = :newName where o.firstName = :oldName")
    void updateName(@Param("oldName") String oldName, @Param("newName") String newName);

}
