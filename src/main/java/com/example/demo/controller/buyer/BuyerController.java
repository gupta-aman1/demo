package com.example.demo.controller.buyer;

import com.example.demo.dto.buyer.BuyerRequest;
import com.example.demo.dto.buyer.BuyerResponse;
import com.example.demo.service.buyer.BuyerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyers")
public class BuyerController {

    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    // Create Buyer
    @PostMapping
    public ResponseEntity<BuyerResponse> createBuyer(
            @Valid @RequestBody BuyerRequest request) {

        BuyerResponse response = buyerService.createBuyer(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}