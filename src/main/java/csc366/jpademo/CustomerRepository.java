package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    
    Customer findByEmail(String email);

    Customer findByFirstName(String firstName);

    Customer findByLastName(String lastName);

    // JPQL query
    @Query("from Customer o where o.firstName = :name or o.lastName = :name")
    Customer findByNameJpql(@Param("name") String name);

    /*@Query("select o from Customer o join o.orders order where o.firstName = :name or o.lastName = :name")
    Customer findByNameWithOrderJpql(@Param("name") String name);
    
    // Native SQL query
    @Query(value = "select * from Customer as o where o.firstName = :name or o.lastName = :name", nativeQuery = true)
    Customer findByNameSql(@Param("name") String name);

    @Modifying
    @Query("update Customer o set o.firstName = :newName where o.firstName = :oldName")
    void updateName(@Param("oldName") String oldName, @Param("newName") String newName);

    @Modifying
    @Query("update Customer o set o.email = :newEmail where o.email = :oldEmail")
    void updateEmail(@Param("oldEmail") String oldEmail, @Param("newEmail") String newEmail);*/
}