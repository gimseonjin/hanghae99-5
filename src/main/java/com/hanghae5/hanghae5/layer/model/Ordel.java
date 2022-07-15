package com.hanghae5.hanghae5.layer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Ordel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderQuantity> orderQuantityList;

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        //무한루프에 빠지지 않도록 체크
        if (!restaurant.getOrders().contains(this)) {
            restaurant.getOrders().add(this);
        }
    }

    public void addQuantity(OrderQuantity orderQuantity) {
        this.orderQuantityList.add(orderQuantity);
        //무한루프에 빠지지 않도록 체크
        if (orderQuantity.getOrder() != this) {
            orderQuantity.setOrder(this);
        }
    }

    @Override
    public String toString() {
        return "Ordel{" +
                "id=" + id +
                ", restaurant=" + restaurant +
                '}';
    }
}
