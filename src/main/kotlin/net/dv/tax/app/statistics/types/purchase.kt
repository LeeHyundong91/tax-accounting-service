package net.dv.tax.app.statistics.types

interface PurchaseStatistics {
    val summary: SalesAmount
    val byPayments: SalesPayment
    val byCategories: SalesCategory
}

interface PurchaseAmount {
    val currentAmount: Long
    val beforeAmount: Long
    val compareAmount: Long
    val compareRatio: Double
}

interface PurchaseAggregation {
    val hospitalId: String
    val period: String
    val debitAccount: String
    val category: String
    val amount: Long
}