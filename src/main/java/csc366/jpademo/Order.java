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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "Order")

public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="custEmail", nullable = true)
    private Customer customer;
    
    @NotNull
    @Column(name="timeOrdered")
    private Date timeOrdered;

    @NotNull
    @Column(name="totalCost")
    private Double totalCost;

    @NotNull
    @OneToMany(mappedBy = "order",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY)
    private List<ItemsOrdered> itemsOrdered = new ArrayList<>();
    
    public Order() { }

    // Constructor without registered customer email
    public Order(Double totalCost) {
        this.customer = null;
        this.totalCost = totalCost;
        this.timeOrdered = new Date();
    }

    // Cunstructor with registered customer email
    public Order(Double totalCost, Customer customer) {
        this.customer = customer;
        this.totalCost = totalCost;
        this.timeOrdered = new Date();
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Long getId() {
	    return this.id;
    }
    public Date getTimeOrdered(String firstName) {
	    return this.timeOrdered;
    }

    public Double getTotalCost() {
	    return this.totalCost;
    }

    public void setTotalCost(Double cost) {
	    this.totalCost = cost;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("," , Order.class.getSimpleName() + "[" , "]");
        sj.add(id.toString()).add("itemsOrdered=" + itemsOrdered.toString());
        return sj.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Order) {
            return (this.id != null) && (this.id.equals(((Order) obj).getId()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 366;
    }
}