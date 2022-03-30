package com.fc.invoicing.controllers;

import com.fc.invoicing.dto.CompanyDto;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
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
