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

    @Column(name="name")
    private String name;
    
    @NotNull
    @Column(name="cost")
    private float cost;

    @NotNull
    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "menuItem",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY)
    private List<ItemsOrdered> itemsOrdered = new ArrayList<>();
    
    public MenuItem() { }

    public MenuItem(String name, float cost, String description) {
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
	    this.name = name;
    }

    public float getCost() {
	    return this.cost;
    }

    public void setCost(float cost) {
	    this.cost = cost;
    }

    public String getDescription() {
	    return this.description;
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