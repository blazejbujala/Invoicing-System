package com.fc.invoicing.db;

import com.fc.invoicing.model.Invoice;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaInvoiceRepository extends PagingAndSortingRepository<Invoice, UUID> {
}
