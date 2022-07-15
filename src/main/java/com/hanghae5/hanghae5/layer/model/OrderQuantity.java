package com.hanghae5.hanghae5.layer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_quantity_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Ordel order;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    private int quantity;

    public void setOrder(Ordel order) {
        this.order = order;
        //무한루프에 빠지지 않도록 체크
        if (!order.getOrderQuantityList().contains(this)) {
            order.getOrderQuantityList().add(this);
        }
    }

    public void setFood(Food food) {
        this.food = food;
        //무한루프에 빠지지 않도록 체크
        if (!food.getOrderQuantityList().contains(this)) {
            food.getOrderQuantityList().add(this);
        }
    }

    public int getPrice(){
        return this.food.getPrice() * this.quantity;
    }

}
