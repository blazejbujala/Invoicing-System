const getData = async () => {
    const response = await fetch('http://localhost:7777/companies');
    const companies = await response.json();

    companies.forEach(company => {
        console.log(company)
    })

    const table = document.getElementById('company-table');
    companies.forEach(company => {
        const row = table.insertRow(-1);
        const taxIdCell = row.insertCell(0);
        taxIdCell.innerText = company.taxIdentificationNumber;

        const addressCell = row.insertCell(1);
        addressCell.innerText = company.address;

        const nameCell = row.insertCell(2);
        nameCell.innerText = company.name;

        const pensionInsuranceCell = row.insertCell(3);
        pensionInsuranceCell.innerText = company.pensionInsurance;

        const healthInsuranceCell = row.insertCell(4);
        healthInsuranceCell.innerText = company.healthInsurance;
    })
}

const serializeFormToJson = form => JSON.stringify(
    Array.from(new FormData(form).entries())
        .reduce((m, [key, value]) =>
            Object.assign(m, {[key]: value}), {})
);

function submitForm() {
    const form = $("#Company-Form");
    form.on('submit', function (e) {
        e.preventDefault();

        const csrfToken = document.cookie
            .split(';')
            .find(row => row.startsWith('XSRF-TOKEN='))
            .split('=')[1];

        $.ajaxPrefilter(function (options, originalOptions, jqXHR){
            jqXHR.setRequestHeader('X-XSRF-TOKEN', csrfToken);
        });

        $.ajax({
            url: 'companies',
            type: 'post',
            contentType: 'application/json',
            data: serializeFormToJson(this),
            success: function (data) {
                $("#company-table").find("tr:gt(0)").remove();
                getData()
            },
            error: function (jqXhr, textStatus, errorThrown) {
                alert(jqXhr.status + " " + errorThrown)
            }
        });
    });
}

window.onload = function () {
    getData();
    submitForm();
}
