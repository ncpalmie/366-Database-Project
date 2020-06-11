package csc366.jpademo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.StringJoiner;

@Entity  // indicates that this class maps to a database table
@Table(name = "Inventory",     // may be omitted for default naming
        // requires @Column(name=...)
        uniqueConstraints = @UniqueConstraint(columnNames={"invEntryId"})
)
public class Inventory {

    @Id
    @Column(name = "invEntryID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String invEntryID;

    @NotNull
    @Column(name="itemQuantity")
    private int itemQuantity;  // note: no annotation, still included in underlying table

    @NotNull
    @Column(name="storeID")
    private String storeID;

    @NotNull
    @Column(name = "itemID")
    private String itemID;

    public Inventory() { }

    public Inventory(int itemQuantity, String storeID, String itemID) {
        this.itemQuantity = itemQuantity;
        this.storeID = storeID;
        this.itemID = itemID;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("," , Order.class.getSimpleName() + "[" , "]");
        sj.add(invEntryID.toString()).add("" + itemQuantity).add(storeID.toString()).add(itemID.toString());
        return sj.toString();
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public String getInvEntryID() {
        return invEntryID;
    }

    public String getItemID() {
        return itemID;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setInvEntryID(String invEntryID) {
        this.invEntryID = invEntryID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }
}
