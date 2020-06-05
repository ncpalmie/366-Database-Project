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
@Table(name = "owner",
       uniqueConstraints = @UniqueConstraint(columnNames={"last_name", "first_name"})
)
public class Owner {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String email;

    @Column(name="first_name")
    private String firstName;
    
    @NotNull
    @Column(name="last_name")
    private String lastName;

    @Column(unique=true)
    private String ssn;

    @Column(unique=false)
    private String dob;

    @Column(unique=false)
    private String phone;

    @OneToMany(mappedBy = "owner",
               cascade = CascadeType.ALL,
               orphanRemoval = true,
               fetch = FetchType.LAZY)
    private List<Store> stores = new ArrayList<>();
    
    public Owner() { }
    
    public Owner(String firstName, String lastName, String email, String ssn, String dob, String phone) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.ssn = ssn;
	this.dob = dob;
	this.phone = phone;
    }
    
    public Long getId() {
	return id;
    }
    public void setId(Long id) {
	this.id = id;
    }

    public String getSSN() {
        return ssn;
    }
    public void setSSN(String ssn) {
        this.ssn = ssn;
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

    public String getEmail() {
	return email;
    }
    public void setEmail(String email) {
	this.email = email;
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

    public void addStore(Store s) {
        stores.add(s);
        s.setOwner(this);
    }
    public void removeStore(Store s) {
        stores.remove(s);
        s.setOwner(null);
    }

    public List<Store> getStores() {
        return this.stores;
    }
    
    @Override
    public String toString() {
	StringJoiner sj = new StringJoiner("," , Owner.class.getSimpleName() + "[" , "]");
	sj.add(firstName).add(lastName).add(email).add(ssn).add(dob).add(phone).add("stores="+stores.toString());
	return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Owner) return id != null && id.equals(((Owner) o).getId());
        return false;
    }

    @Override
    public int hashCode() {
        return 366;
    }

}
