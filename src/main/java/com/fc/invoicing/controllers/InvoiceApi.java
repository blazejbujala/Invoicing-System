package com.fc.invoicing.controllers;

import com.fc.invoicing.dto.InvoiceListDto;
import com.fc.invoicing.model.Invoice;
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

public interface InvoiceApi {

    @GetMapping
    @Operation(summary = "List of full invoices", description = "Get list of full invoices")
    ResponseEntity<List<Invoice>> getAllInvoices();

    @GetMapping(path = "/list")
    @Operation(summary = "List of invoices", description = "Get list of all invoices")
    ResponseEntity<List<InvoiceListDto>> getList();

    @GetMapping("/{id}")
    @Operation(summary = "Get invoice", description = "Get one invoice by id")
    ResponseEntity<Invoice> getById(@PathVariable UUID id);

    @PostMapping
    @Operation(summary = "Add invoice", description = "Add new invoice")
    ResponseEntity<Invoice> addInvoice(@RequestBody Invoice invoice);

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete invoice", description = "Delete one invoice by id")
    ResponseEntity deleteById(@PathVariable UUID id);

    @PatchMapping("/{id}")
    @Operation(summary = "Update invoice", description = "Update invoice by id")
    ResponseEntity<Invoice> update(@PathVariable UUID id, @RequestBody Invoice updatedInvoice);
}
