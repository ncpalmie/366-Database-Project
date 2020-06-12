package csc366.jpademo;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.DecimalFormat;
import java.util.StringJoiner;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;

@Entity  // indicates that this class maps to a database table
@Table(name = "PreparedItem",     // may be omitted for default naming
        // requires @Column(name=...)
        uniqueConstraints = @UniqueConstraint(columnNames={"itemID"})
)
public class PreparedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="itemID")
    private int itemID;

    @NotNull
    @Column(name="itemName")
    private String itemName;

    @NotNull
    @Column(name = "itemCost")
    private double itemCost;

    @NotNull
    @Column(name = "itemDescription")
    private String itemDescription;

    @NotNull
    @Column(name = "prepTimeMin")
    private int prepTimeMin;

    @ManyToMany
    @JoinTable(
        name = "recipes",
        joinColumns = @JoinColumn(name = "prepareditem_id"),
        inverseJoinColumns = @JoinColumn(name = "rawitem_id"))
    private List<RawItem> rawItems = new ArrayList<>();

    public PreparedItem() { }

    public PreparedItem(int itemID, String itemName, double itemCost, String itemDescription, int prepTimeMin, List<RawItem> rawItems) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemDescription = itemDescription;
        this.prepTimeMin = prepTimeMin;
        this.rawItems = rawItems;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
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

    public int getPrepTimeMin() {
        return prepTimeMin;
    }

    public void setPrepTimeMin(int prepTimeMin) {
        this.prepTimeMin = prepTimeMin;
    }

    public void addRawItem(RawItem i) {
        rawItems.add(i);
        i.getPreparedItems().add(this);
    }
    public boolean removeRawItem(RawItem i) {
        rawItems.remove(i);
        return i.getPreparedItems().remove(this);
    }

    public List<RawItem> getRawItems() {
        return this.rawItems;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        StringJoiner sj = new StringJoiner("," , PreparedItem.class.getSimpleName() + "[" , "]");
        sj.add(itemID + "").add(itemName)
                .add(df.format(itemCost)).add(itemDescription)
                .add(prepTimeMin + "").add(rawItems.toString());
        return sj.toString();
    }
}
