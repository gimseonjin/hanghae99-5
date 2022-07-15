package com.hanghae5.hanghae5.layer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
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
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private int minOrderPrice;
    @NotNull
    private int deliveryFee;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Food> menu;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Ordel> orders;

    public void addMenu(Food food) {
        this.menu.add(food);
        //무한루프에 빠지지 않도록 체크
        if (food.getRestaurant() != this) {
            food.setRestaurant(this);
        }
    }

    public void addOrders(Ordel order) {
        this.orders.add(order);
        //무한루프에 빠지지 않도록 체크
        if (order.getRestaurant() != this) {
            order.setRestaurant(this);
        }
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", minOrderPrice=" + minOrderPrice +
                ", deliveryFee=" + deliveryFee +
                '}';
    }
}
