package com.fc.invoicing.controllers;

import com.fc.invoicing.model.Company;
import com.fc.invoicing.model.CompanyTaxInformation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface TaxCalculatorApi {

    @PostMapping
    @Operation(summary = "Get tax information", description = "Get full company's tax information")
    ResponseEntity<CompanyTaxInformation> companyTaxInformation(@RequestBody Company company);
}
