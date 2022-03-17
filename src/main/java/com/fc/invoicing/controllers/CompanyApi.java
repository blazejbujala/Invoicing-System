package com.fc.invoicing.controllers;

import com.fc.invoicing.dto.CompanyDto;
import com.fc.invoicing.model.Company;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CompanyApi {

    @GetMapping
    @Operation(summary = "List of companies with full details", description = "Get list of all companies")
    ResponseEntity<List<CompanyDto>> getAllCompanies();

    @GetMapping("/{id}")
    @Operation(summary = "Get company", description = "Get company by id")
    ResponseEntity<CompanyDto> getById(@PathVariable UUID id);

    @PostMapping
    @Operation(summary = "Add company", description = "Add new company")
    ResponseEntity<CompanyDto> addCompany(@RequestBody CompanyDto company);

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete company", description = "Delete one company by id")
    ResponseEntity deleteById(@PathVariable UUID id);

    @PatchMapping("/{id}")
    @Operation(summary = "Update company", description = "Update company by id")
    ResponseEntity<CompanyDto> update(@PathVariable UUID id, @RequestBody CompanyDto updatedCompany);
}
