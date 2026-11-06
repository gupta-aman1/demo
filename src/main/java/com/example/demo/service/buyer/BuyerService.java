package com.example.demo.service.buyer;

import com.example.demo.dto.buyer.BuyerRequest;
import com.example.demo.dto.buyer.BuyerResponse;
import com.example.demo.entity.buyerEntity.BuyerEntity;
import com.example.demo.repository.buyerRepo.BuyerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BuyerService {

    private final BuyerRepository buyerRepository;

    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    // Create Buyer
    public BuyerResponse createBuyer(BuyerRequest request) {

        if(buyerRepository.existsByGstin(request.getGstin())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "GSTIN is already registered"
            );
        }

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

    // GET All Buyers
    @Transactional(readOnly = true)
    public List<BuyerResponse> getAllBuyers() {

        /*if(buyerRepository.count() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No Data Found"
            );
        }*/

        return buyerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET Buyer By ID
    @Transactional(readOnly = true)
    public BuyerResponse getBuyerById(Long id) {

        BuyerEntity buyer = buyerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Buyer not found with ID: " + id
                ));

        return mapToResponse(buyer);
    }

    @Transactional
    public BuyerResponse updateBuyer(Long id, BuyerRequest request) {

        // Find existing buyer
        BuyerEntity buyer = buyerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Buyer not found with ID: " + id
                ));

        // Check duplicate GSTIN
        if (request.getGstin() != null &&
                !request.getGstin().equals(buyer.getGstin()) &&
                buyerRepository.existsByGstin(request.getGstin())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "GSTIN is already registered"
            );
        }

        // Update buyer fields
        buyer.setBuyerName(request.getBuyerName());
        buyer.setMobile(request.getMobile());
        buyer.setGstin(request.getGstin());
        buyer.setEaddress(request.getEaddress());
        buyer.setState(request.getState());
        buyer.setStateCode(request.getStateCode());

        // Save updated buyer
        BuyerEntity updatedBuyer = buyerRepository.save(buyer);

        return mapToResponse(updatedBuyer);
    }
}