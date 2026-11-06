package com.example.demo.controller.buyer;

import com.example.demo.common.ApiResponse;
import com.example.demo.dto.buyer.BuyerRequest;
import com.example.demo.dto.buyer.BuyerResponse;
import com.example.demo.service.buyer.BuyerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Buyer Management",
        description = "APIs for managing buyers"
)
@RestController
@RequestMapping("/api/buyers")
@SecurityRequirement(name = "bearerAuth")
public class BuyerController {

    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    // Create Buyer
    @PostMapping
    public ResponseEntity<ApiResponse<BuyerResponse>> createBuyer(
            @Valid @RequestBody BuyerRequest request) {

        BuyerResponse response = buyerService.createBuyer(request);

        ApiResponse<BuyerResponse> apiResponse =
                ApiResponse.success(
                        HttpStatus.CREATED.value(),
                        "Buyer created successfully",
                        response
                );

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    // GET All Buyers
    @GetMapping
    public ResponseEntity<ApiResponse<List<BuyerResponse>>> getAllBuyers() {

        List<BuyerResponse> buyers = buyerService.getAllBuyers();

        ApiResponse<List<BuyerResponse>> response =
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Buyers fetched successfully",
                        buyers
                );

        return ResponseEntity.ok(response);
    }


    // GET Buyer By ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<BuyerResponse>>> getBuyerById(@PathVariable Long id) {

        BuyerResponse buyer = buyerService.getBuyerById(id);

        List<BuyerResponse> buyers = List.of(buyer);

        ApiResponse<List<BuyerResponse>> response =
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Buyer fetched successfully",
                        buyers
                );

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BuyerResponse>> updateBuyer(
            @PathVariable Long id,
            @Valid @RequestBody BuyerRequest request) {

        BuyerResponse buyer = buyerService.updateBuyer(id, request);

        ApiResponse<BuyerResponse> response =
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Buyer updated successfully",
                        buyer
                );

        return ResponseEntity.ok(response);
    }


}