package com.fc.invoicing.db;

import com.fc.invoicing.model.Company;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyRepository extends PagingAndSortingRepository<Company, UUID> {
}
