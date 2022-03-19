package com.fc.invoicing.controllers;

import com.fc.invoicing.dto.CompanyDto;
import com.fc.invoicing.services.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(path = "/companies", produces = {"application/json;charset=UTF-8"})
@RestController
@RequiredArgsConstructor
@Tag(name = "Controller for company services")
public class CompanyController implements CompanyApi {

    private final CompanyService companyService;

    @Override
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        log.debug("Getting list of all companies");
        return ResponseEntity.ok(companyService.getAll());
    }

    @Override
    public ResponseEntity<CompanyDto> getById(@PathVariable UUID id) {
        log.debug("Getting company with ID: " + id);
        return ResponseEntity.ok(companyService.getById(id));
    }

    @Override
    public ResponseEntity<CompanyDto> addCompany(@RequestBody CompanyDto company) {
        log.debug("Adding new company with ID: " + company.getCompanyId());
        return ResponseEntity.ok(companyService.add(company));
    }

    @Override
    public ResponseEntity deleteById(@PathVariable UUID id) {
        companyService.delete(id);
        log.debug("Deleting company with ID: " + id);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<CompanyDto> update(@PathVariable UUID id, @RequestBody CompanyDto updatedCompany) {
        log.debug("Updating company with ID: " + id);
        return ResponseEntity.ok(companyService.update(id, updatedCompany));
    }
}
