package com.fc.invoicing.controllers;

import com.fc.invoicing.dto.InvoiceListDto;
import com.fc.invoicing.model.Invoice;
import com.fc.invoicing.services.InvoiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RequestMapping(path = "/invoices", produces = {"application/json;charset=UTF-8"})
@RestController
@RequiredArgsConstructor
@Tag(name = "Controller for accounting services")
public class InvoiceController implements InvoiceApi {

    private final InvoiceService invoiceService;

    @Override
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        log.debug("Getting list of all invoices");
        return ResponseEntity.ok(invoiceService.getAll());
    }

    public ResponseEntity<List<InvoiceListDto>> getList() {
        log.debug("Getting list of all invoices");
        return ResponseEntity.ok()
            .body(invoiceService.getList());
    }

    @Override
    public ResponseEntity<Invoice> getById(@PathVariable UUID id) {
        log.debug("Getting invoice with ID: " + id);
        return ResponseEntity.ok(invoiceService.getById(id));
    }

    @Override
    public ResponseEntity<Invoice> addInvoice(@RequestBody Invoice invoice) {
        log.debug("Adding new invoice with ID: " + invoice.getInvoiceId());
        return ResponseEntity.ok(invoiceService.add(invoice));
    }

    @Override
    public ResponseEntity deleteById(@PathVariable UUID id) {
        invoiceService.delete(id);
        log.debug("Deleting invoice with ID: " + id);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Invoice> update(@PathVariable UUID id, @RequestBody Invoice updatedInvoice) {
        log.debug("Updating invoice with ID: " + id);
        return ResponseEntity.ok(invoiceService.update(id, updatedInvoice));
    }
}
