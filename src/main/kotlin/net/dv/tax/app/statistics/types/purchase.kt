package net.dv.tax.app.statistics.types

interface PurchaseStatistics {
    val summary: PurchaseAmount
    val byCategories: List<PurchaseAggregation>
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
    val ratio: Double
}