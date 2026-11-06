package com.example.demo.repository.buyerRepo;

import com.example.demo.entity.buyerEntity.BuyerEntity;
import org.hibernate.internal.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<BuyerEntity, Long> {

    Optional<BuyerEntity> findByGstin(String gstin);

    boolean existsByGstin(String gstin);

}