package net.dv.tax.app.sales.support

import net.dv.tax.app.dto.sales.*

interface CarInsuranceSupport {

    fun dataList(hospitalId: String, yearMonth: String) : List<CarInsuranceDto>

    fun dataListTotal(hospitalId: String, yearMonth: String) : CarInsuranceDto?

    fun monthlySumAmount(hospitalId: String, yearMonth: String): Long?


}

interface CashReceiptSupport {

    fun dataList(hospitalId: String, yearMonth: String): List<SalesCashReceiptDto>

    fun dataListTotal(hospitalId: String, yearMonth: String): SalesCashReceiptDto?

    fun monthlySumAmount(hospitalId: String, yearMonth: String): Long?

}

interface EmployeeIndustrySupport {
    fun dataList(hospitalId: String, yearMonth: String): List<EmployeeIndustryDto>

    fun dataTotalList(hospitalId: String, yearMonth: String): EmployeeIndustryDto?

    fun monthlySumAmount(hospitalId: String, yearMonth: String): Long?

}

interface HealthCareSupport {

    fun dataList(hospitalId: String, yearMonth: String) : List<HealthCareDto>

    fun dataListTotal(hospitalId: String, yearMonth: String) : HealthCareDto?

    fun monthlySumAmount(hospitalId: String, yearMonth: String): Long?

}

interface MedicalBenefitsSupport {

    fun dataList(hospitalId: String, yearMonth: String): List<MedicalBenefitsDto>

    fun dataListTotal(hospitalId: String, yearMonth: String) : MedicalBenefitsDto?

    fun monthlyCorpSumAmount(hospitalId: String, yearMonth: String): Long?

    fun monthlyOwnChargeSumAmount(hospitalId: String, yearMonth: String): Long?


}

interface MedicalCareSupport {

    fun dataList(hospitalId: String, yearMonth: String): List<MedicalCareDto>

    fun dataListTotal(hospitalId: String, yearMonth: String) : MedicalCareDto?

    fun monthlyAgencySumAmount(hospitalId: String, yearMonth: String): Long?

    fun monthlyOwnChargeSumAmount(hospitalId: String, yearMonth: String): Long?

}

interface SalesAgentSupport {

    fun dataList(hospitalId: String, yearMonth: String) : List<SalesAgentDto>

    fun monthlySumAmount(hospitalId: String, yearMonth: String): Long?

}

interface SalesElecInvoiceSupport {

    fun dataList(hospitalId: String, yearMonth: String): List<SalesElecInvoiceDto>

    fun dataListTotal(hospitalId: String, yearMonth: String): SalesElecInvoiceDto?

    fun taxDataList(hospitalId: String, yearMonth: String): List<SalesElecTaxInvoiceDto>

    fun taxDataListTotal(hospitalId: String, yearMonth: String): SalesElecTaxInvoiceDto?

}

interface SalesCreditCardSupport {
    fun dataList(hospitalId: String, yearMonth: String): List<SalesCreditCardDto>

    fun dataListTotal(hospitalId: String, yearMonth: String): SalesCreditCardDto?

    fun monthlySumAmount(hospitalId: String, yearMonth: String): Long?

}

interface SalesOtherBenefitsSupport {

    fun dataList(hospitalId: String, yearMonth: String) : List<SalesOtherBenefitsGroupDto>
}

interface SalesVaccineSupport {
    fun monthlySumAmount(hospitalId: String, yearMonth: String): Long?
}