CREATE TABLE public.invoices
(
    invoice_id      uuid            NOT NULL,
    invoice_number  varchar(255)    NOT NULL UNIQUE,
    date_of_issue   date            NOT NULL,
    issuer_id       uuid            NOT NULL,
    receiver_id     uuid            NOT NULL,
PRIMARY KEY (invoice_id)
);

ALTER TABLE public.invoices
    ADD CONSTRAINT issuer_fk FOREIGN KEY (issuer_id)
        REFERENCES public.companies (company_id);

ALTER TABLE public.invoices
    ADD CONSTRAINT receiver_fk FOREIGN KEY (receiver_id)
        REFERENCES public.companies (company_id);
