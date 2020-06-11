package csc366.jpademo;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.text.SimpleDateFormat;

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
@Table(name = "supplyContract",
       uniqueConstraints = @UniqueConstraint(columnNames={"contractID"})
)

public class SupplyContract {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    //private List<SupplyContract> supplyContracts = new ArrayList<>();

    @NotNull
    @Column(name="contractID")
    private String contractID;

    @Column(name="deliveryFrequency", unique=false)
    private String deliveryFrequency;

    @Column(name="startDate", unique=false)
    private Date startDate;

    @Column(name="endDate", unique=false)
    private Date endDate;

    @Column(name="supplierID", unique=true)
    private String supplierID;

    @Column(name="rawItemID", unique=true)
    private String rawItemID;

    @Column(name="packagedItemID", unique=true)
    private String packagedItemID;

    public SupplyContract() { }
    
    public SupplyContract(String contractID, String deliveryFrequency, Date startDate, Date endDate, String supplierID, String rawItemID, String packagedItemID) {
	    this.contractID = contractID;
        this.deliveryFrequency = deliveryFrequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.supplierID = supplierID;
	    this.rawItemID = rawItemID;
	    this.packagedItemID = packagedItemID;
    }
    
    public Long getId() {
	    return id;
    }
    public void setId(Long id) {
	    this.id = id;
    }

    public String getContractID() {
        return contractID;
    }
    public void setContractID(String contractID) {
        this.contractID = contractID;
    }
    
    public String getRawItemID() {
	    return rawItemID;
    }
    public void setRawItemID(String rawItemID) {
	    this.rawItemID = rawItemID;
    }

    public String getPackagedItemID() {
	    return packagedItemID;
    }
    public void setPackagedItemID(String packagedItemID) {
	    this.packagedItemID = packagedItemID;
    }

    public String getSupplierID() {
	    return supplierID;
    }
    public void setSupplierID(String supplierID) {
	    this.supplierID = supplierID;
    }

    public Date getStartDate() {
	    return startDate;
    }
    public void setStartDate(Date startDate) {
	    this.startDate = startDate;
    }

    public Date getEndDate() {
	    return endDate;
    }
    public void setEndDate(Date endDate) {
	    this.endDate = endDate;
    }

    public String getDeliveryFrequency() {
	    return deliveryFrequency;
    }
    public void setDeliveryFrequency(String deliveryFrequency) {
	    this.deliveryFrequency = deliveryFrequency;
    }
    
    @Override
    public String toString() {
	    StringJoiner sj = new StringJoiner("," , SupplyContract.class.getSimpleName() + "[" , "]");
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String startDateStr = df.format(startDate);
        String endDateStr = df.format(endDate);
	    sj.add(contractID).add(deliveryFrequency).add(startDateStr).add(endDateStr).add(supplierID).add(rawItemID).add(packagedItemID);
	    return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof SupplyContract) return id != null && id.equals(((SupplyContract) o).getId());
        return false;
    }

    @Override
    public int hashCode() {
        return 366;
    }

}
