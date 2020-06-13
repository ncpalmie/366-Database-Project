package csc366.jpademo;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OrderColumn;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.UniqueConstraint;
import javax.print.attribute.standard.DateTimeAtCreation;
import javax.print.attribute.standard.DateTimeAtProcessing;
import javax.validation.constraints.NotNull;
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.query.criteria.internal.expression.function.CurrentTimestampFunction;
import org.springframework.data.auditing.CurrentDateTimeProvider;

@Entity
@Table(name = "MenuItem",
       uniqueConstraints = @UniqueConstraint(columnNames={"name"})
)

public class MenuItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="menuItemID")
    private String menuItemID;

    @Column(name="name")
    private String name;
    
    @NotNull
    @Column(name="cost")
    private double cost;

    @NotNull
    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "menuItem",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY)
    private List<ItemsOrdered> itemsOrdered = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="rawItem", referencedColumnName = "itemID")
    private RawItem rawItem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="packagedItem", referencedColumnName = "itemID")
    private PackagedItem packagedItem;
    
    public MenuItem() { }

    public MenuItem(String menuItemID, String name, double cost, String description, RawItem rawItem, PackagedItem packagedItem) {
        this.menuItemID = menuItemID;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.rawItem = rawItem;
        this.packagedItem = packagedItem;
    }

    public Long getId() {
        return this.id;
    }

    public String getMenuItemID() {
        return menuItemID;
    }

    public void setMenuItemID(String menuItemID) {
        this.menuItemID = menuItemID;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
	    this.name = name;
    }

    public double getCost() {
	    return this.cost;
    }

    public void setCost(double cost) {
	    this.cost = cost;
    }

    public String getDescription() {
	    return this.description;
    }

    public void addItemsOrdered(ItemsOrdered o) {
        itemsOrdered.add(o);
        o.setMenuItem(this);
    }

    public List<ItemsOrdered> getItemsOrdered() {
        return this.itemsOrdered;
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

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("," , MenuItem.class.getSimpleName() + "[" , "]");
        sj.add(name).add(description).add("itemsOrdered=" + itemsOrdered.toString());
        return sj.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MenuItem) {
            return (this.id != null) && (this.id.equals(((MenuItem) obj).getId()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 366;
    }
}
