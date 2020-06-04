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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "regulator",
       uniqueConstraints = @UniqueConstraint(columnNames={"last_name", "first_name"})
)

//create table Regulator(
//        agency varchar(50) NOTNULL,
//        agentID varchar(50) NOTNULL,
//        first_name varchar(50) NOTNULL,
//        last_name varchar(50) NOTNULL,
//        position varchar(50),
//        primary key (agentID)
//        );

public class Regulator {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "regulator",  orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Audit> audits = new ArrayList<>();

    @Column(unique=true)
    private String agency;

    @Column(name="first_name")
    private String firstName;
    
    @NotNull
    @Column(name="last_name")
    private String lastName;

    @Column(name="regualatorID", unique=true)
    private String regulatorID;

    @Column(unique=false)
    private String position;
    
    public Regulator() { }
    
    public Regulator(String firstName, String lastName, String agency, String agentID, String position) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.agency = agency;
	this.regulatorID = regulatorID;
	this.position = position;
    }
    
    public Long getId() {
	return id;
    }
    public void setId(Long id) {
	this.id = id;
    }

    public String getAgency() {
        return agency;
    }
    public void setAgency(String agency) {
        this.agency = agency;
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

    public String getRegulatorID() {
	return regulatorID;
    }
    public void setRegulatorID(String regulatorID) {
	this.regulatorID = regulatorID;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public void addAudit(Audit a) {
        audits.add(a);
        a.setRegulator(this);
    }
    public void removeAudit(Audit a) {
        audits.remove(a);
        a.setRegulator(null);
    }

    public List<Audit> getAudits() {
        return this.audits;
    }
    
    @Override
    public String toString() {
	StringJoiner sj = new StringJoiner("," , Regulator.class.getSimpleName() + "[" , "]");
	sj.add(firstName).add(lastName).add(agency).add(regulatorID).add(position).add("audits="+audits.toString());
	return sj.toString();
    }

}
