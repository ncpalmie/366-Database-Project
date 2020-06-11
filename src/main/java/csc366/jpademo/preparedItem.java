package csc366.jpademo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.DecimalFormat;
import java.util.StringJoiner;

@Entity  // indicates that this class maps to a database table
@Table(name = "preparedItem",     // may be omitted for default naming
        // requires @Column(name=...)
        uniqueConstraints = @UniqueConstraint(columnNames={"itemID", "rawItemID"})
)
public class preparedItem {

    @NotNull
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

    @NotNull
    @Column(name = "rawItemID")
    private String rawItemID;

    public preparedItem() { }

    public preparedItem(int itemID, String itemName, double itemCost, String itemDescription, int prepTimeMin,String rawItemID) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemDescription = itemDescription;
        this.prepTimeMin = prepTimeMin;
        this.rawItemID = rawItemID;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        StringJoiner sj = new StringJoiner("," , Order.class.getSimpleName() + "[" , "]");
        sj.add(itemID + "").add(itemName)
                .add(df.format(itemCost)).add(itemDescription)
                .add(prepTimeMin + "").add(rawItemID);
        return sj.toString();
    }
}
