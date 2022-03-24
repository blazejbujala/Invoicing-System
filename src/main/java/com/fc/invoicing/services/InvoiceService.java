package com.fc.invoicing.services;

import com.fc.invoicing.db.InvoiceRepository;
import com.fc.invoicing.dto.InvoiceListDto;
import com.fc.invoicing.dto.mappers.InvoiceListMapper;
import com.fc.invoicing.exeptions.handlers.ItemNotFoundExemption;
import com.fc.invoicing.model.Invoice;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceListMapper invoiceListMapper;

    public Invoice add(Invoice invoice) {
        invoice.setInvoiceId(UUID.randomUUID());
        return invoiceRepository.save(invoice);
    }

    public Invoice getById(UUID id) {
        return invoiceRepository.findById(id).orElseThrow(() -> new ItemNotFoundExemption("No invoice with id: " + id));
    }

    public List<Invoice> getAll() {
        return StreamSupport
            .stream(invoiceRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    public List<InvoiceListDto> getList() {
        return getAll().stream().map(invoiceListMapper::invoiceListToDto).collect(Collectors.toList());
    }

    public Invoice update(UUID id, Invoice updatedInvoice) {
        if (invoiceRepository.findById(id).isPresent()) {
            updatedInvoice.setInvoiceId(id);
            return invoiceRepository.save(updatedInvoice);
        } else {
            throw new ItemNotFoundExemption("No invoice with id: " + id + " please add new invoice");
        }
    }

    public boolean delete(UUID id) {
        try {
            invoiceRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundExemption("No invoice with id: " + id);
        }
        return true;
    }

    public void clear() {
        invoiceRepository.deleteAll();
    }
}
