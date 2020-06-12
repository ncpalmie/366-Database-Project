package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    
    Customer findByEmail(String email);

    @Query("from Customer c where c.email = :email")
    Customer findByCustomerEmailJpql(@Param("email") String email);

    @Query("from Customer c where c.phone = :phone")
    Customer findByCustomerPhoneJpql(@Param("phone") String phone);

    @Modifying
    @Query("update Customer c set c.phone = :newPhone where c.email = :email")
    void updateCustomerPhone(
        @Param("newPhone") String newPhone, 
        @Param("email") String email
    );

    @Modifying
    @Query("update Customer c set c.rewardsPoints = :newPoints where c.email = :email")
    void updateCustomerPhone(
        @Param("newPoints") Integer newPoints, 
        @Param("email") String email
    );
}