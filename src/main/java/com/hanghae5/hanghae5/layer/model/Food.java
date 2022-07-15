package com.hanghae5.hanghae5.layer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints =
        {@UniqueConstraint(name = "UniqueRestaurantFood", columnNames = { "restaurant_id", "name"})})
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private int price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "food")
    private List<OrderQuantity> orderQuantityList;

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        //무한루프에 빠지지 않도록 체크
        if (!restaurant.getMenu().contains(this)) {
            restaurant.getMenu().add(this);
        }
    }

    public void addQuantity(OrderQuantity orderQuantity) {
        this.orderQuantityList.add(orderQuantity);
        //무한루프에 빠지지 않도록 체크
        if (orderQuantity.getFood() != this) {
            orderQuantity.setFood(this);
        }
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", restaurant=" + restaurant +
                '}';
    }
}
