CREATE TABLE public.companies
(
company_id uuid NOT NULL,
tax_identification_number character varying (13),
name varchar(100) NOT NULL,
address varchar(200) NOT NULL,
healthy_insurance numeric(10,2) NOT NULL DEFAULT 0,
pension_insurance numeric(10,2) NOT NULL DEFAULT 0,
PRIMARY KEY (company_id)
);
