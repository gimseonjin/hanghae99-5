package com.hanghae5.hanghae5.layer.infra;

import com.hanghae5.hanghae5.layer.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
