package com.fc.invoicing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "invoice_entry")
public class InvoiceEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Schema(name = "Position number", example = "1", required = true)
    private int entryId;
    @Schema(name = "Name of product/service", example = "Gasoline", required = true)
    private String description;
    @Schema(name = "Net price of product/service", example = "115.10", required = true)
    private BigDecimal price;
    @Schema(name = "Number of items", example = "11", required = true)
    private BigDecimal quantity;
    @Schema(name = "Total price of products/services", example = "1100.98", required = true)
    private BigDecimal totalPrice;
    @Schema(name = "Product/service tax value", example = "11.39", required = true)
    private BigDecimal vatValue;
    @Column(columnDefinition = "numeric(3, 2)")
    @Schema(name = "Tax rate", required = true)
    private Vat vatRate;
    @Schema(name = "Product/service gross price", example = "11.39", required = true)
    private BigDecimal totalGrossPrice;
    @Column(name = "car_registration_number")
    @Schema(name = "Car registration numbers", example = "WR 90090")
    private String carRegNo;
    @Column(name = "personal_use_car")
    @Schema(name = "If entry refers to personal-use car", example = "true")
    private boolean personalUseCar;

    public InvoiceEntry(String description, BigDecimal price, Vat vatRate) {
        this.description = description;
        this.price = price;
        this.vatRate = vatRate;
        this.vatValue = price.multiply(new BigDecimal(Float.toString(vatRate.getRate()))).setScale(2, RoundingMode.HALF_UP);
        this.carRegNo = "";
        this.personalUseCar = false;
    }

    public InvoiceEntry(int entryId, String description, BigDecimal price, Vat vatRate, String carRegNo, Boolean personalUseCar) {
        this.entryId = entryId;
        this.description = description;
        this.price = price;
        this.vatRate = vatRate;
        this.vatValue = price.multiply(new BigDecimal(Float.toString(vatRate.getRate()))).setScale(2, RoundingMode.HALF_UP);
        this.carRegNo = carRegNo;
        this.personalUseCar = personalUseCar;
    }

    @PrePersist
    public void calculateTotalPriceAndVatValue() {
        this.totalPrice = price.multiply(quantity).setScale(2, RoundingMode.HALF_UP);
        this.vatValue = totalPrice.multiply(new BigDecimal(Float.toString(vatRate.getRate()))).setScale(2, RoundingMode.HALF_UP);
        this.totalGrossPrice = totalPrice.add(vatValue).setScale(2, RoundingMode.HALF_UP);
    }
}
