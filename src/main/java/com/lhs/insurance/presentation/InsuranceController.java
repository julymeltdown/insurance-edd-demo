package com.lhs.insurance.presentation;

import com.lhs.insurance.application.InsuranceService;
import com.lhs.insurance.presentation.request.InsuranceAcceptRequestDto;
import com.lhs.insurance.presentation.request.InsuranceCreateRequestDto;
import com.lhs.insurance.presentation.response.InsuranceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @PostMapping
    public ResponseEntity<InsuranceResponseDto> createInsuranceOffer(
            @RequestBody InsuranceCreateRequestDto requestDto
    ) {
        InsuranceResponseDto responseDto = insuranceService.createInsuranceOffer(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/accept") // 승인 요청
    public ResponseEntity<InsuranceResponseDto> acceptInsuranceOffer(
            @RequestBody InsuranceAcceptRequestDto requestDto
    ) {
        InsuranceResponseDto responseDto = insuranceService.acceptInsuranceOffer(requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{insuranceOfferId}")
    public ResponseEntity<InsuranceResponseDto> retrieveInsuranceOffer(
            @PathVariable(name = "insuranceOfferId") Long insuranceOfferId
    ) {
        InsuranceResponseDto responseDto = insuranceService.retrieveInsuranceOffer(insuranceOfferId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
