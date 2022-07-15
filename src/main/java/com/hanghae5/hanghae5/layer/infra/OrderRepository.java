package com.hanghae5.hanghae5.layer.infra;

import com.hanghae5.hanghae5.layer.model.Ordel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Ordel, Long> {
}
