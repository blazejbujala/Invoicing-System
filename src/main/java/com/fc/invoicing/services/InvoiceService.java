package com.fc.invoicing.services;

import com.fc.invoicing.db.Database;
import com.fc.invoicing.db.JpaInvoiceRepository;
import com.fc.invoicing.dto.InvoiceListDto;
import com.fc.invoicing.dto.mappers.InvoiceListMapper;
import com.fc.invoicing.exeptions.handlers.ItemNotFoundExemption;
import com.fc.invoicing.model.Invoice;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class InvoiceService implements Database<Invoice> {

    private final JpaInvoiceRepository jpaInvoiceRepository;
    private final InvoiceListMapper invoiceListMapper;

    @Override
    public Invoice add(Invoice invoice) {
        invoice.setInvoiceId(UUID.randomUUID());
        return jpaInvoiceRepository.save(invoice);
    }

    @Override
    public Invoice getById(UUID id) {
        if (jpaInvoiceRepository.findById(id).isPresent()) {
            return jpaInvoiceRepository.findById(id).get();
        } else {
            throw new ItemNotFoundExemption("No invoice with id: " + id);
        }
    }

    @Override
    public List<Invoice> getAll() {
        return StreamSupport
            .stream(jpaInvoiceRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    public List<InvoiceListDto> getList() {
        return getAll().stream().map(invoiceListMapper::invoiceListToDto).collect(Collectors.toList());
    }

    @Override
    public Invoice update(UUID id, Invoice updatedInvoice) {
        if (jpaInvoiceRepository.findById(id).isPresent()) {
            updatedInvoice.setInvoiceId(id);
            return jpaInvoiceRepository.save(updatedInvoice);
        } else {
            throw new ItemNotFoundExemption("No invoice with id: " + id + " please add new invoice");
        }
    }

    @Override
    public boolean delete(UUID id) {
        if (jpaInvoiceRepository.findById(id).isPresent()) {
            jpaInvoiceRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

        @Override
        public void clear () {
            jpaInvoiceRepository.deleteAll();
        }
    }
