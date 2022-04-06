package com.fc.invoicing.testHelpers

import com.fc.invoicing.model.Company
import com.fc.invoicing.model.Invoice
import com.fc.invoicing.model.InvoiceEntry
import com.fc.invoicing.model.Vat

import java.math.RoundingMode
import java.time.LocalDate

class TestHelpers {

    static company(int id) {
        new Company(UUID.randomUUID(),
                "111-11-11-11$id"
                , "$id Company"
                , "Ul. Kocia 1/$id, 12-312, Gdansk"
                , BigDecimal.valueOf(70 + id)
                , BigDecimal.valueOf(10 + id)
        )
    }

    static product(int id) {
        new InvoiceEntry(entryId: id,
                description: "Product $id",
                price: BigDecimal.valueOf(100 * id),
                quantity: BigDecimal.valueOf(id),
                totalPrice: (BigDecimal.valueOf(100 * id) * BigDecimal.valueOf(id)),
                vatValue: (BigDecimal.valueOf(100 * id) * BigDecimal.valueOf(id) * BigDecimal.valueOf(0.23)).setScale(2, RoundingMode.HALF_UP),
                vatRate: Vat.VAT_23,
                totalGrossPrice: (BigDecimal.valueOf(100 * id) * BigDecimal.valueOf(id) * BigDecimal.valueOf(0.23))
                        .setScale(2, RoundingMode.HALF_UP) + (BigDecimal.valueOf(100 * id) * BigDecimal.valueOf(id)),
                carRegNo: null,
                personalUseCar: false,
        )
    }

    static gasoline(int id) {
        new InvoiceEntry(entryId: id,
                description: "Gasoline $id",
                price: BigDecimal.valueOf(100 * id),
                quantity: BigDecimal.valueOf(id),
                totalPrice: (BigDecimal.valueOf(100 * id) * BigDecimal.valueOf(id)),
                vatValue: (BigDecimal.valueOf(100 * id) * BigDecimal.valueOf(id) * BigDecimal.valueOf(0.23)).setScale(2, RoundingMode.HALF_UP),
                vatRate: Vat.VAT_23,
                totalGrossPrice: (BigDecimal.valueOf(100 * id) * BigDecimal.valueOf(id) * BigDecimal.valueOf(0.23))
                        .setScale(2, RoundingMode.HALF_UP) + (BigDecimal.valueOf(100 * id) * BigDecimal.valueOf(id)),
                carRegNo: "GO 1232$id",
                personalUseCar: true,
        )
    }

    static invoice(int id) {
        new Invoice(UUID.randomUUID()
                , "12/12/$id"
                , LocalDate.now()
                , company(id)
                , company(id + 1)
                , List.of(product(id)
                , product(id + 1)
                , product(id + 2)))
    }

    static invoiceWithGasoline(int id) {
        new Invoice(UUID.randomUUID()
                , "12/12/$id"
                , LocalDate.now()
                , company(id)
                , company(id + 1)
                , List.of(product(id)
                , gasoline(id + 1)
                , product(id + 2)))
    }
}
