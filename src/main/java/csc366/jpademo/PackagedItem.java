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
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "packagedItem",
       uniqueConstraints = @UniqueConstraint(columnNames={"itemID"})
)

public class PackagedItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="itemID")
    private String itemID;

    @Column(name="itemName", unique=true)
    private String itemName;

    @Column(name="itemCost", unique=false)
    private double itemCost;

    @Column(name="itemDescription", unique=false)
    private String itemDescription;

    @OneToOne(mappedBy = "packagedItem")
    private SupplyContract supplyContract;

    @OneToOne(mappedBy = "packagedItem")
    private InventoryEntry inventoryEntry;

    @OneToOne(mappedBy = "packagedItem")
    private MenuItem menuItem;

    public PackagedItem() { }
    
    public PackagedItem(String itemID, String itemName, double itemCost, String itemDescription) {
	    this.itemID = itemID;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemDescription = itemDescription;
    }
    
    public Long getId() {
	    return id;
    }
    public void setId(Long id) {
	    this.id = id;
    }

    public String getItemID() {
        return itemID;
    }
    public void setItemID(String ItemID) {
        this.itemID = itemID;
    }
    
    public String getItemName() {
	    return itemName;
    }
    public void setItemName(String itemName) {
	    this.itemName = itemName;
    }

    public double getItemCost() {
	    return itemCost;
    }
    public void setItemCost(double itemCost) {
	    this.itemCost = itemCost;
    }

    public String getItemDescription() {
	    return itemDescription;
    }
    public void setItemDescription(String itemDescription) {
	    this.itemDescription = itemDescription;
    }

    @Override
    public String toString() {
	    StringJoiner sj = new StringJoiner("," , Supplier.class.getSimpleName() + "[" , "]");
	    sj.add(itemID).add(itemName).add(Double.toString(itemCost)).add(itemDescription);
	    return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof PackagedItem) return id != null && id.equals(((PackagedItem) o).getId());
        return false;
    }

    @Override
    public int hashCode() {
        return 366;
    }

}
