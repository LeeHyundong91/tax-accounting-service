package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.*

interface CarInsuranceSupport {

    fun dataList(hospitalId: String, yearMonth: String) : List<CarInsuranceDto>

    fun dataListTotal(hospitalId: String, yearMonth: String) : CarInsuranceDto?

}

interface CashReceiptSupport {

    fun dataList(hospitalId: String, yearMonth: String): List<SalesCashReceiptDto>

    fun dataListTotal(hospitalId: String, yearMonth: String): SalesCashReceiptDto?

}

interface EmployeeIndustrySupport {
    fun dataList(hospitalId: String, yearMonth: String): List<EmployeeIndustryDto>

    fun dataTotalList(hospitalId: String, yearMonth: String): EmployeeIndustryDto?
}

interface HealthCareSupport {

    fun dataList(hospitalId: String, yearMonth: String) : List<HealthCareDto>

    fun dataListTotal(hospitalId: String, yearMonth: String) : HealthCareDto?

}

interface MedicalBenefitsSupport {

    fun dataList(hospitalId: String, treatmentYearMonth: String): List<MedicalBenefitsDto>

    fun dataListTotal(hospitalId: String, treatmentYearMonth: String) : MedicalBenefitsDto?

}

interface MedicalCareSupport {

    fun dataList(hospitalId: String, treatmentYearMonth: String): List<MedicalCareDto>

    fun dataListTotal(hospitalId: String, treatmentYearMonth: String) : MedicalCareDto?

}

interface SalesAgentSupport {

    fun dataList(hospitalId: String, yearMonth: String) : List<SalesAgentDto>

}

interface SalesElecInvoiceSupport {

    fun dataList(hospitalId: String, writingDate: String): List<SalesElecInvoiceDto>

    fun dataListTotal(hospitalId: String, writingDate: String): SalesElecInvoiceDto?

    fun taxDataList(hospitalId: String, writingDate: String): List<SalesElecTaxInvoiceDto>

    fun taxDataListTotal(hospitalId: String, writingDate: String): SalesElecTaxInvoiceDto?


}

interface SalesCreditCardSupport {
    fun dataList(hospitalId: String, year: String): List<SalesCreditCardDto>

    fun dataListTotal(hospitalId: String, year: String): SalesCreditCardDto?

}