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
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
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
@Table(name = "ItemsOrdered")

public class ItemsOrdered {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="orderId", nullable = false)
    private Order order;

    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="itemId", nullable = false)
    private MenuItem menuItem;
    
    public ItemsOrdered() { }

    public ItemsOrdered(Order order, MenuItem menuItem) {
        this.order = order;
        this.menuItem = menuItem;
    }

    public Long getId() {
        return this.id;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    
    public MenuItem getMenuItem() {
	    return this.menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("," , Order.class.getSimpleName() + "[" , "]");
        sj.add(id.toString()).add("order=" + order.toString()).add("menuItem=" + menuItem.toString());
        return sj.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ItemsOrdered) {
            return this.id != null
             && (this.id.equals(((ItemsOrdered) obj).getId()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 366;
    }
}