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
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "inventoryEntry",
       uniqueConstraints = @UniqueConstraint(columnNames={"inventoryEntryID"})
)

public class InventoryEntry {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory")
    private Inventory inventory;

    @NotNull
    @Column(name="inventoryEntryID")
    private String inventoryEntryID;
    
    @Column(name="itemQuantity")
    private int itemQuantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="rawItem", referencedColumnName = "itemID", nullable = true)
    private RawItem rawItem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="packagedItem", referencedColumnName = "itemID", nullable = true)
    private PackagedItem packagedItem;

    public InventoryEntry() { }
    
    public InventoryEntry(String inventoryEntryID, Inventory inventory, int itemQuantity, RawItem rawItem, PackagedItem packagedItem) {
	    this.inventoryEntryID = inventoryEntryID;
        this.inventory = inventory;
        this.itemQuantity = itemQuantity;
        this.rawItem = rawItem;
        this.packagedItem = packagedItem;
    }
    
    public Long getId() {
	    return id;
    }
    public void setId(Long id) {
	    this.id = id;
    }

    public String getInventoryEntryID() {
        return inventoryEntryID;
    }
    public void setInventoryEntryID(String inventoryEntryID) {
        this.inventoryEntryID = inventoryEntryID;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
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

    public Inventory getInventory() {
    	return inventory;
    }

    public void setInventory(Inventory inventory) {
    	this.inventory = inventory;
    }
    
    @Override
    public String toString() {
	    StringJoiner sj = new StringJoiner("," , InventoryEntry.class.getSimpleName() + "[" , "]");
        if (packagedItem == null)
	        sj.add(inventoryEntryID).add(Integer.toString(inventory.getInventoryID())).add(Integer.toString(itemQuantity)).add(rawItem.getItemName());
        else if (rawItem == null)
	        sj.add(inventoryEntryID).add(Integer.toString(inventory.getInventoryID())).add(Integer.toString(itemQuantity)).add(packagedItem.getItemName());
	    return sj.toString();
	    return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof InventoryEntry) return id != null && id.equals(((InventoryEntry) o).getId());
        return false;
    }

    @Override
    public int hashCode() {
        return 366;
    }

}
