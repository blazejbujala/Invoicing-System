package com.fc.invoicing.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue
    @Schema(name = "invoiceId", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
    private UUID invoiceId;
    @Column(name = "invoice_number")
    @Schema(name = "invoiceNumber", example = "2020/02/02/00001", required = true)
    private String invoiceNumber;
    @Schema(name = "dateOfIssue", example = "2022-01-01", required = true)
    private LocalDate dateOfIssue;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "issuerId")
    @Schema(name = "issuer", required = true)
    private Company issuer;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "receiverId")
    @Schema(name = "receiver", required = true)
    private Company receiver;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "invoiceId")
    @Schema(name = "entries", required = true)
    private List<InvoiceEntry> entries;

    public Invoice(LocalDate dateOfIssue, Company issuer, Company receiver, List<InvoiceEntry> entries) {
        this.dateOfIssue = dateOfIssue;
        this.issuer = issuer;
        this.receiver = receiver;
        this.entries = entries;
    }

    public Invoice(UUID invoiceId, LocalDate dateOfIssue, Company issuer, Company receiver, List<InvoiceEntry> entries) {
        this.invoiceId = invoiceId;
        this.dateOfIssue = dateOfIssue;
        this.issuer = issuer;
        this.receiver = receiver;
        this.entries = entries;
    }
}
