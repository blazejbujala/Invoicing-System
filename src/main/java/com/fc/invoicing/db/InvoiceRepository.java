package com.fc.invoicing.db;

import com.fc.invoicing.model.Invoice;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, UUID> {
}
