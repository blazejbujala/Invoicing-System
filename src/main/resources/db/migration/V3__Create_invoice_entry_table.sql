CREATE TABLE public.invoice_entry
(
    entry_id                 integer             NOT NULL,
    description              varchar(255)        NOT NULL,
    price                    numeric(10, 2)      NOT NULL,
    vat_value                numeric(10, 2)      NOT NULL,
    vat_rate                 numeric(3, 2)       NOT NULL,
    quantity                 numeric(10, 2)      NOT NULL   DEFAULT 0,
    total_price              numeric(10, 2)      NOT NULL,
    car_registration_number  varchar(20),
    personal_use_car         boolean             NOT NULL DEFAULT false,
    invoice_id               uuid                NOT NULL,
    PRIMARY KEY(entry_id),
    CONSTRAINT invoice_fk FOREIGN KEY(invoice_id) REFERENCES public.invoices(invoice_id)
);



