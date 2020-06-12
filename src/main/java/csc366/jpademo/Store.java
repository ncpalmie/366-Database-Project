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
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "store",
       uniqueConstraints = @UniqueConstraint(columnNames={"storeID"})
)

public class Store {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Audit> audits = new ArrayList<>();

    @NotNull
    @Column(name="storeID")
    private String storeID;

    @Column(name="phone", unique=true)
    private String phone;

    @Column(name="location", unique=true)
    private String location;

    @Column(unique=false)
    private String storeSize;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="owner", nullable = true)
    private Owner owner;

//    //MANAGER
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="location_mng", nullable = true)
//    private LocationManager manager;

//    //Employee Table
//    @OneToMany(mappedBy = "storeE", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<Employee> employees = new ArrayList<>();


//    //**ADD to empolyee table for foreign key
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="storeID", nullable = true)
//    private Store storeE;

//    //Inventory Table
//    @OneToMany(mappedBy = "storeI", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<Inventory> inventory = new ArrayList<>();


//    //**ADD to inventory table for foreign key
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="storeID", nullable = true)
//    private Store store;

    public Store() { }
    
    public Store(String storeID, String phone, String location, String storeSize) {
	this.storeID = storeID;
	this.phone = phone;
	this.location = location;
	this.storeSize = storeSize;
    }
    
    public Long getId() {
	return id;
    }
    public void setId(Long id) {
	this.id = id;
    }

    public String getStoreID() {
        return storeID;
    }
    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }
    
    public String getPhone() {
	return phone;
    }
    public void setPhone(String phone) {
	this.phone = phone;
    }

    public String getLocation() {
	return location;
    }
    public void setLocation(String location) {
	this.location = location;
    }

    public String getStoreSize() {
	return storeSize;
    }
    public void setStoreSize(String storeSize) {
	this.storeSize = storeSize;
    }

    public Owner getOwner() {
        return owner;
    }
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void addAudit(Audit a) {
        audits.add(a);
        a.setStore(this);
    }
    public void removeAudit(Audit a) {
        audits.remove(a);
        a.setStore(null);
    }

    public List<Audit> getAudits() {
        return this.audits;
    }
//
//    public void addInventory(Inventory i) {
//        inventory.add(i);
//        i.setStore(this);
//    }
//    public void removeInventory(Inventory i) {
//        inventory.remove(i);
//        i.setStore(null);
//    }
//
//    public List<Inventory> getInventory() {
//        return this.inventory;
//    }
//
//    public void addEmployee(Employee e) {
//        employees.add(e);
//        e.setStore(this);
//    }
//    public void removeEmployee(Employee e) {
//        employees.remove(e);
//        e.setStore(null);
//    }
//
//    public List<Employee> getEmployees() {
//        return this.employees;
//    }
    
    @Override
    public String toString() {
	StringJoiner sj = new StringJoiner("," , Store.class.getSimpleName() + "[" , "]");
	sj.add(storeID).add(phone).add(location).add(storeSize).add("audits="+audits.toString());
	return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Store) return id != null && id.equals(((Store) o).getId());
        return false;
    }

    @Override
    public int hashCode() {
        return 366;
    }

}
