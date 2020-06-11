package csc366.jpademo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.StringJoiner;

@Entity  // indicates that this class maps to a database table
@Table(name = "Inventory",     // may be omitted for default naming
        // requires @Column(name=...)
        uniqueConstraints = @UniqueConstraint(columnNames={"inventoryId"})
)
public class Inventory {

    @Id
    @Column(name = "inventoryID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int inventoryID;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InventoryEntry> inventoryEntries = new ArrayList<>();

    @NotNull
    @Column(name="storeID")
    private String storeID;

    public Inventory() { }

    public Inventory(int inventoryID, String storeID) {
        this.inventoryID = inventoryID;
        this.storeID = storeID;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("," , Inventory.class.getSimpleName() + "[" , "]");
        sj.add(Integer.toString(inventoryID)).add(storeID).add(inventoryEntries.toString());
        return sj.toString();
    }

    public void addInventoryEntry(InventoryEntry e) {
	    inventoryEntries.add(e);
	    e.setInventory(this);
    }
    public void removeInventoryEntry(InventoryEntry e) {
	    inventoryEntries.remove(e);
	    e.setInventory(null);
    }
    public List<InventoryEntry> getInventoryEntries() {
	    return this.inventoryEntries;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }
}
