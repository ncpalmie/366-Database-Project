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
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "audit",
       uniqueConstraints = @UniqueConstraint(columnNames={"auditID"})
)

//create table Audit(
//        auditID int NOTNULL,
//        agentID varchar(50) NOTNULL,
//        storeID int NOTNULL,
//        date Date NOTNULL,
//        audit_type varchar(50),
//        primary key (auditID),
//        foreign key (agentID) references Regulator(AgentID),
//        foreighn key (storeID) references Store(storeID)
//        );

public class Audit {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique=true, name="auditID")
    private String auditID;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="regulator_id", nullable = true)
    private Regulator regulator;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="store_id", nullable = true)
    private Store store;

    @Column(unique=false)
    private String date;

    @Column(unique=false)
    private String auditType;
    
    public Audit() { }
    
    public Audit(String auditID, String date, String auditType) {
	this.auditID = auditID;
	this.date = date;
	this.auditType = auditType;
    }
    
    public Long getId() {
	return id;
    }
    public void setId(Long id) {
	this.id = id;
    }
    
    public String getAuditID() {
	return auditID;
    }
    public void setAuditID(String auditID) {
	this.auditID = auditID;
    }

    public String getDate() {
	return date;
    }
    public void setDate(String date) {
	this.date = date;
    }

    public void setRegulator(Regulator regulator) { this.regulator = regulator; }
    public Regulator getRegulator() {
	return regulator;
    }

    public void setStore(Store store) { this.store = store; }
    public Store getStore() {
        return store;
    }
    
    @Override
    public String toString() {
	StringJoiner sj = new StringJoiner("," , Audit.class.getSimpleName() + "[" , "]");
	sj.add(id.toString()).add(auditID).add(date).add(auditType);
	return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Store) return id != null && id.equals(((Store) o).getId());
        if (o instanceof Regulator) return id != null && id.equals(((Regulator) o).getId());
        return false;
    }

    @Override
    public int hashCode() {
        return 366;
    }

}
