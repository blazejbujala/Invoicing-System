package com.fc.invoicing.dto.mappers;

import com.fc.invoicing.dto.InvoiceDto;
import com.fc.invoicing.model.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    InvoiceDto toDto(Invoice invoice);

    Invoice toEntity(InvoiceDto invoice);
}
