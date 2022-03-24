package com.fc.invoicing.dto.mappers;

import com.fc.invoicing.dto.InvoiceListDto;
import com.fc.invoicing.model.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceListMapper {

    InvoiceListDto invoiceListToDto(Invoice invoice);
}
