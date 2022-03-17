package com.fc.invoicing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvoiceListDto {

    @Schema(name = "invoiceNumber", example = "2020/02/02/00001", required = true)
    private String invoiceNumber;

    @Schema(name = "dateOfIssue", example = "2022-01-01", required = true)
    private LocalDate dateOfIssue;

}
