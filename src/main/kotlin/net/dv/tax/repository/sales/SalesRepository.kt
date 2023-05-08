package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.*
import net.dv.tax.repository.sales.support.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 요양급여
 */
interface MedicalBenefitsRepository : JpaRepository<MedicalBenefitsEntity, Long>, MedicalBenefitsSupport {
    fun findAllByHospitalIdAndTreatmentYearMonth(
        hospitalId: String,
        yearMonth: String,
        page: Pageable,
    ): Page<MedicalBenefitsEntity>
}

/**
 * 의료급여
 */
interface MedicalCareRepository : JpaRepository<MedicalCareEntity, Long>, MedicalCareSupport {
    fun findAllByHospitalIdAndTreatmentYearMonth(
        hospitalId: String,
        treatmentYearMonth: String,
        page: Pageable,
    ): Page<MedicalCareEntity>
}

/**
 * 자동차 보험
 */
interface CarInsuranceRepository : JpaRepository<CarInsuranceEntity?, Int>, CarInsuranceSupport {
    fun findAllByHospitalIdAndTreatmentYearMonth(
        hospitalId: String,
        treatmentYearMonth: String,
        page: Pageable,
    ): Page<CarInsuranceEntity>
}

/**
 * 예방접종
 */
interface SalesVaccineRepository : JpaRepository<SalesVaccineEntity, Int> {
    fun findAllByHospitalIdAndPaymentYearMonthStartingWithOrderByPaymentYearMonth(
        hospitalId: String,
        year: String,
    ): List<SalesVaccineEntity>
}

/**
 * 건강검진
 */
interface HealthCareRepository : JpaRepository<HealthCareEntity, Long>, HealthCareSupport {

    fun findAllByHospitalIdAndPaidDateStartingWith(
        hospitalId: String,
        paidDate: String,
        page: Pageable,
    ): Page<HealthCareEntity>
}

/**
 * 고용산재
 */
interface EmployeeIndustryRepository : JpaRepository<EmployeeIndustryEntity?, Int>,
    EmployeeIndustrySupport {

    fun findAllByHospitalIdAndTreatmentYearMonth(
        hospitalId: String,
        treatmentYearMonth: String,
        page: Pageable,
    ): Page<EmployeeIndustryEntity>
}

/**
 * 병원관리
 */
interface HospitalChartRepository : JpaRepository<HospitalChartEntity?, Int> {
    fun findAllByHospitalIdAndTreatmentYearMonthStartingWithOrderByTreatmentYearMonth(
        hospitalId: String,
        treatmentYearMonth: String,
    ): List<HospitalChartEntity>
}

/**
 * 기타급여
 */
interface SalesOtherBenefitsRepository : JpaRepository<SalesOtherBenefitsEntity, Long> {
    fun findAllByHospitalIdAndDataPeriodStartingWithOrderByDataPeriod(
        hospitalId: String,
        dataPeriod: String,
    ): List<SalesOtherBenefitsEntity>
}

/**
 * 신용카드
 */
interface SalesCreditCardRepository : JpaRepository<SalesCreditCardEntity?, Int>, SalesCreditCardSupport

/**
 * 판매결제 대행
 */
interface SalesAgentRepository : JpaRepository<SalesAgentEntity, Long>, SalesAgentSupport

/**
 * 현금영수증
 */
interface SalesCashReceiptRepository : JpaRepository<SalesCashReceiptEntity, Int>,
    CashReceiptSupport {
    fun findAllByHospitalIdAndSalesDateStartingWith(
        hospitalId: String,
        salesDate: String,
        page: Pageable,
    ): Page<SalesCashReceiptEntity>
}

/**
 * 전자계산서, 전자세금계산서
 */
interface SalesElecInvoiceRepository : JpaRepository<SalesElecInvoiceEntity, Long>, SalesElecInvoiceSupport {
    fun findAllByHospitalIdAndTaxIsFalseAndWritingDateStartingWithOrderByWritingDateDesc(
        hospitalId: String,
        writingDate: String,
        page: Pageable,
    ): Page<SalesElecInvoiceEntity>

    fun findAllByHospitalIdAndTaxIsTrueAndWritingDateStartingWithOrderByWritingDateDesc(
        hospitalId: String,
        writingDate: String,
        page: Pageable,
    ): Page<SalesElecInvoiceEntity>
}

/**
 * 수기세금계산서
 */
interface SalesHandInvoiceRepository : JpaRepository<SalesHandInvoiceEntity, Int> {
    fun findAllByHospitalIdAndIssueDateStartingWithAndIsDeleteIsFalseOrderByIssueDateDesc(
        hospitalId: String,
        issueDate: String,
        page: Pageable,
    ): Page<SalesHandInvoiceEntity>
}

interface IncomeStatementRepository : JpaRepository<IncomeStatementEntity?, Int> {
    fun findAllByHospitalIdAndDataPeriodStartingWith(hospitalId: String, dataPeriod: String)
}




