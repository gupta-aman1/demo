package com.example.demo.repository.buyerRepo;

import com.example.demo.entity.buyerEntity.BuyerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<BuyerEntity, Long> {

}