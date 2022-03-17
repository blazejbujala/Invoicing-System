package com.fc.invoicing.db;

import com.fc.invoicing.model.Company;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCompanyRepository extends PagingAndSortingRepository<Company, UUID> {
}
