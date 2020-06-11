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
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "LocMgr",
       uniqueConstraints = @UniqueConstraint(columnNames={"last_name", "first_name"})
)
public class LocMgr {

    @Id
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

    @OneToOne(mappedBy = "manager",
               orphanRemoval = true,
               fetch = FetchType.LAZY)
    private Store store = new Store();

    @OneToMany(mappedBy = "manager",
               cascade = CascadeType.ALL,
               orphanRemoval = true,
               fetch = FetchType.LAZY)
    private List<Owner> owners = new ArrayList<>();
    
    public LocMgr() { }
    
    public LocMgr(String firstName, String lastName, String email, String ssn, String dob, String phone) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.ssn = ssn;
	this.dob = dob;
	this.phone = phone;
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

    
    @Override
    public String toString() {
	StringJoiner sj = new StringJoiner("," , LocMgr.class.getSimpleName() + "[" , "]");
	sj.add(firstName).add(lastName).add(email).add(ssn).add(dob).add(phone).add("store="+store.toString());
	return sj.toString();
    }

}
