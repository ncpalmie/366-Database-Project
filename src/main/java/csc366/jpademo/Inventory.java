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

    @Column(name = "inventoryName")
    private String inventoryName;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InventoryEntry> inventoryEntries = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="store", referencedColumnName = "storeID")
    private Store store;

    public Inventory() { }

    public Inventory(int inventoryID, String inventoryName, Store store) {
        this.inventoryID = inventoryID;
        this.inventoryName = inventoryName;
        this.store = store;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("," , Inventory.class.getSimpleName() + "[" , "]");
        sj.add(Integer.toString(inventoryID)).add(store.getStoreID()).add(inventoryEntries.toString());
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

    public Store getStore() {
        return store;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
