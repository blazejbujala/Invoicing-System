package com.fc.invoicing.controllers;

import com.fc.invoicing.model.Company;
import com.fc.invoicing.model.CompanyTaxInformation;
import com.fc.invoicing.services.TaxCalculatorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RequestMapping(path = "/tax", produces = {"application/json;charset=UTF-8"})
@RestController
@RequiredArgsConstructor
@Tag(name = "Controller for company tax information")
public class TaxCalculatorController implements TaxCalculatorApi {

    private final TaxCalculatorService taxCalculatorService;

    @Override
    public ResponseEntity<CompanyTaxInformation> companyTaxInformation(@RequestBody Company company) {
        log.debug("Getting full tax information about company");
        return ResponseEntity.ok().body(taxCalculatorService.companyTaxInformation(company));
    }
}
