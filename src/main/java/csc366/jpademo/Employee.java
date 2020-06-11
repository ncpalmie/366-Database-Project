package csc366.jpademo;

import java.util.List;
import java.sql.Date;
import java.util.ArrayList;
import java.util.StringJoiner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OrderColumn;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "employee", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))

/*
create table Employee (
    employeeID int NOT NULL AUTO_INCREMENT
    email VARCHAR(255),
firstName VARCHAR(100),
lastName VARCHAR(100),
dateOfBirth date,
ssn VARCHAR(15),
phone VARCHAR(15),
storeID VARCHAR(100),
startDate date,
endDate date,
PRIMARY KEY (emp_id),
FOREIGN KEY (store_id) REFERENCES store storeID
)
*/

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email", unique = true)
    private String email;

    @Column(unique = false)
    private String firstName;

    @Column(unique = false)
    private String lastName;

    @Column(unique = false, nullable = true)
    private Date dateOfBirth;

    @Column(unique = false, nullable = true)
    private String ssn;

    @Column(unique = false, nullable = true)
    private String phone;

    @Column(unique = false, nullable = true)
    private Date startDate;

    @Column(unique = false, nullable = true)
    private Date endDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="storeID", nullable = true)
    private Store store;

    public Employee() { }

    public Employee(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return this.id;
    }

    public String getEmail(){
        return this.email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getSSN() {
        return this.ssn;
    }

    public String getPhone() {
        return this.phone;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public Store getStore() {
        return this.store;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSSN(String ssn) {
        this.ssn = ssn;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStore(Store store) {
        this.store = store;
    }

}

