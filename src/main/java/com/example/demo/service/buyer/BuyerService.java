package com.example.demo.service.buyer;

import com.example.demo.dto.buyer.BuyerRequest;
import com.example.demo.dto.buyer.BuyerResponse;
import com.example.demo.entity.buyerEntity.BuyerEntity;
import com.example.demo.repository.buyerRepo.BuyerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BuyerService {

    private final BuyerRepository buyerRepository;

    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    // Create Buyer
    public BuyerResponse createBuyer(BuyerRequest request) {

        BuyerEntity buyer = new BuyerEntity();

        buyer.setBuyerName(request.getBuyerName());
        buyer.setMobile(request.getMobile());
        buyer.setGstin(request.getGstin());
        buyer.setEaddress(request.getEaddress());
        buyer.setState(request.getState());
        buyer.setStateCode(request.getStateCode());

        BuyerEntity savedBuyer = buyerRepository.save(buyer);

        return mapToResponse(savedBuyer);
    }

    // Entity to Response DTO mapping
    private BuyerResponse mapToResponse(BuyerEntity buyer) {

        BuyerResponse response = new BuyerResponse();

        response.setId(buyer.getId());
        response.setBuyerName(buyer.getBuyerName());
        response.setMobile(buyer.getMobile());
        response.setGstin(buyer.getGstin());
        response.setEaddress(buyer.getEaddress());
        response.setState(buyer.getState());
        response.setStateCode(buyer.getStateCode());
        response.setCreatedAt(buyer.getCreatedAt());
        response.setUpdatedAt(buyer.getUpdatedAt());

        return response;
    }
}