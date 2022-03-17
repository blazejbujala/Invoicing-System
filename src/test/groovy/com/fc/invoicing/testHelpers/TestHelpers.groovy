package com.fc.invoicing.testHelpers

import com.fc.invoicing.model.Company
import com.fc.invoicing.model.Invoice
import com.fc.invoicing.model.InvoiceEntry
import com.fc.invoicing.model.Vat

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
                vatRate: Vat.VAT_23
        )
    }

    static invoice(int id) {
        new Invoice(LocalDate.now()
                , company(id)
                , company(id + 1)
                , List.of(product(id)
                , product(id + 1)
                , product(id + 2)))
    }
}
