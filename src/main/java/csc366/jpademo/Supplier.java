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
@Table(name = "supplier",
       uniqueConstraints = @UniqueConstraint(columnNames={"supplierID"})
)

public class Supplier {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SupplyContract> supplyContracts = new ArrayList<>();

    @NotNull
    @Column(name="supplierID")
    private String supplierID;

    @Column(name="supplierName", unique=false)
    private String supplierName;

    @Column(name="supplierType", unique=false)
    private String supplierType;

    @Column(name="email", unique=true)
    private String email;

    @Column(name="phone", unique=true)
    private String phone;

    public Supplier() { }
    
    public Supplier(String supplierID, String supplierName, String supplierType, String email, String phone) {
	    this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.supplierType = supplierType;
        this.email = email;
	    this.phone = phone;
    }
    
    public Long getId() {
	    return id;
    }
    public void setId(Long id) {
	    this.id = id;
    }

    public String getSupplierID() {
        return supplierID;
    }
    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }
    
    public String getPhone() {
	    return phone;
    }
    public void setPhone(String phone) {
	    this.phone = phone;
    }

    public String getEmail() {
	    return email;
    }
    public void setEmail(String email) {
	    this.email = email;
    }

    public String getSupplierType() {
	    return supplierType;
    }
    public void setSupplierType(String supplierType) {
	    this.supplierType = supplierType;
    }

    public String getSupplierName() {
	    return supplierName;
    }
    public void setSupplierName(String supplierName) {
	    this.supplierName = supplierName;
    }

    public void addContract(SupplyContract c) {
	    supplyContracts.add(c);
	    c.setSupplier(this);
    }
    public void removeContract(SupplyContract c) {
	    supplyContracts.remove(c);
	    c.setSupplier(null);
    }
    public List<SupplyContract> getSupplyContracts() {
	    return this.supplyContracts;
    }
    
    @Override
    public String toString() {
	StringJoiner sj = new StringJoiner("," , Supplier.class.getSimpleName() + "[" , "]");
	sj.add(supplierID).add(supplierName).add(supplierType).add(phone).add(email);
	return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Supplier) return id != null && id.equals(((Supplier) o).getId());
        return false;
    }

    @Override
    public int hashCode() {
        return 366;
    }

}
