package csc366.jpademo;

import java.util.List;
import java.util.ArrayList;

import java.util.StringJoiner;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OrderColumn;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Customer",
       uniqueConstraints = @UniqueConstraint(columnNames={"lastName", "firstName"})
)

public class Customer {
    @Id
    @Column(name="email")
    private String email;

    @NotNull
    @Column(name="firstName")
    private String firstName;
    
    @NotNull
    @Column(name="lastName")
    private String lastName;

    @NotNull
    @Column(unique=false)
    private String dob;

    @Column(unique=false)
    private String phone;

    @Column(unique=false)
    private Integer rewardsPoints;

    @OneToMany(mappedBy = "customer",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public Customer() { }
    
    // Constructor without phone number
    public Customer(String email, String firstName, String lastName, String dob) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.phone = "";
        this.rewardsPoints = 0;
    }

    // Cunstructor with phone number
    public Customer(String email, String firstName, String lastName, String dob, String phone) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.phone = phone;
        this.rewardsPoints = 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFirstName() {
	    return firstName;
    }
    public void setFirstName(String firstName) {
	    this.firstName = firstName;
    }

    public String getLastName() {
	    return lastName;
    }

    public void setLastName(String lastName) {
	    this.lastName = lastName;
    }

    public String getDOB() {
        return dob;
    }

    public void setDOB(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRewardsPoints() {
        return this.rewardsPoints;
    }

    public void setRewardsPoints(Integer points) {
        this.rewardsPoints = points;
    }

    public void addOrder(Order o) {
        orders.add(o);
        o.setCustomer(this);
    }
    public void removeOrder(Order o) {
        orders.remove(o);
        o.setCustomer(null);
    }

    public List<Order> getOrders() {
        return this.orders;
    }
    
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("," , Customer.class.getSimpleName() + "[" , "]");
        sj.add(email).add(firstName).add(lastName).add(dob).add(phone).add("orders=" + orders.toString());
        return sj.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Customer) {
            return (this.email != null) && (this.email.equals(((Customer) obj).getEmail()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 366;
    }
}