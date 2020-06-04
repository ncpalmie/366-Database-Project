package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long>{

    Owner findByFirstName(String firstName);

    Owner findByLastName(String lastName);

    // JPQL query
    @Query("from Owner o where o.firstName = :name or o.lastName = :name")
    Owner findByNameJpql(@Param("name") String name);
    
    // Native SQL query
    @Query(value = "select * from Owner as o where o.first_name = :name or o.last_name = :name", nativeQuery = true)
    Owner findByNameSql(@Param("name") String name);

    @Modifying
    @Query("update Owner o set o.firstName = :newName where o.firstName = :oldName")
    void updateName(@Param("oldName") String oldName, @Param("newName") String newName);

    @Modifying
    @Query("update Owner o set o.email = :newEmail where o.email = :oldEmail")
    void updateEmail(@Param("oldEmail") String oldEmail, @Param("newEmail") String newEmail);

}
