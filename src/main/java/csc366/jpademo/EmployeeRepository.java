package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    Employee findByEmployeeID(Long id);

    @Query("from Employee e where e.id = :id")
    Employee findByEmployeeIDJpql(@Param("id") Long id);

    @Query("from Employee e where e.email = :email")
    Employee findByEmployeeEmailJpql(@Param("email") String email);

    @Query("from Employee e where e.ssn = :ssn")
    Employee findByEmployeeSSNJpql(@Param("ssn") String ssn);

    @Query("from Employee e where e.phone = :phone")
    Employee findByEmployeePhoneJpql(@Param("phone") String phone);

    @Modifying
    @Query("update Employee e set e.firstName = :newFirstName where e.id = :id")
    void updateFirstName(
        @Param("newFirstName") String newFirstName, 
        @Param("id") Long id
    );
}