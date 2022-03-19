package com.fc.invoicing.dto;

import com.fc.invoicing.model.Company;
import com.fc.invoicing.model.InvoiceEntry;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvoiceDto {

    @Schema(name = "invoiceId", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
    private UUID invoiceId;

    @Schema(name = "invoiceNumber", example = "2020/02/02/00001", required = true)
    private String invoiceNumber;

    @Schema(name = "dateOfIssue", example = "2022-01-01", required = true)
    private LocalDate dateOfIssue;

    @Schema(name = "issuer", required = true)
    private Company issuer;

    @Schema(name = "receiver", required = true)
    private Company receiver;

    @Schema(name = "entries", required = true)
    private List<InvoiceEntry> entries;

}
