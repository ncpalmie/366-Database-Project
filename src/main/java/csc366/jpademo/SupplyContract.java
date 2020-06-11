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
import javax.persistence.OneToOne;
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier", nullable = true)
    private Supplier supplier;

    @NotNull
    @Column(name="contractID")
    private String contractID;

    @Column(name="deliveryFrequency", unique=false)
    private String deliveryFrequency;

    @Column(name="deliveryAmount", unique=false)
    private int deliveryAmount;

    @Column(name="startDate", unique=false)
    private Date startDate;

    @Column(name="endDate", unique=false)
    private Date endDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="rawItem", referencedColumnName = "itemID")
    private RawItem rawItem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="packagedItem", referencedColumnName = "itemID")
    private PackagedItem packagedItem;

    public SupplyContract() { }
    
    public SupplyContract(String contractID, String deliveryFrequency, int deliveryAmount, Date startDate, Date endDate, Supplier supplier, RawItem rawItem, PackagedItem packagedItem) {
	    this.contractID = contractID;
        this.deliveryFrequency = deliveryFrequency;
        this.deliveryAmount = deliveryAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.supplier = supplier;
        this.rawItem = rawItem;
	    this.packagedItem = packagedItem;
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

    public int getDeliveryAmount() {
        return deliveryAmount;
    }
    public void setDeliveryAmount(int deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }
    
    public RawItem getRawItem() {
        return rawItem;
    }

    public void setRawItem(RawItem rawItem) {
        this.rawItem = rawItem;
    }

    public PackagedItem getPackagedItem() {
        return packagedItem;
    }

    public void setPackagedItem(PackagedItem packagedItem) {
        this.packagedItem = packagedItem;
    }

    public String getSupplierID() {
	    return supplier.getSupplierID();
    }
    public void setSupplierID(String supplierID) {
	    this.supplier.setSupplierID(supplierID);
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

    public Supplier getSupplier() {
    	return supplier;
    }

    public void setSupplier(Supplier supplier) {
    	this.supplier = supplier;
    }
    
    @Override
    public String toString() {
	    StringJoiner sj = new StringJoiner("," , SupplyContract.class.getSimpleName() + "[" , "]");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = df.format(startDate);
        String endDateStr = df.format(endDate);
	    sj.add(contractID).add(deliveryFrequency).add(Integer.toString(deliveryAmount)).add(startDateStr).add(endDateStr).add(rawItem.getItemName()).add(packagedItem.getItemName());
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
