package net.dv.tax.app.statistics.types


interface Criteria {
    val hospitalId: String
    val term: Term?
    val date: String?

    enum class Term {
        ANNUAL, MONTHLY
    }
}

interface StatValue {
    val amount: Long
    val ratio: Double
}

interface SalesStatistics {
    val summary: SalesAmount
    val byPayments: SalesPayment
    val byCategories: SalesCategory
}

interface SalesAmount {
    val currentAmount: Long
    val beforeAmount: Long
    val compareAmount: Long
    val compareRatio: Double
}

interface SalesPayment {
    val creditCard: StatValue
    val cashReceipt: StatValue
    val netCash: StatValue
    val salesAgent: StatValue

}

interface SalesCategory {
    val medicalCareBenefits: StatValue
    val medicalBenefits: StatValue
    val carInsurance: StatValue
    val healthCheck: StatValue
    val others: StatValue
    val nonCovered: StatValue
}

interface SalesAggregation {
    val hospitalId: String
    val period: String
    val creditCard: Long?
    val cashReceipt: Long?
    val salesAgent: Long?
    val netCash: Long?
    val medicalCareBenefits: Long?
    val medicalBenefits: Long?
    val industry: Long?
    val vaccine: Long?
    val carInsurance: Long?
    val healthCare: Long?
    val others: Long?
}