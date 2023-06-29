package net.dv.tax.dto.consulting

import net.dv.tax.domain.consulting.TaxCreditEntity
import net.dv.tax.domain.consulting.TaxCreditPersonalEntity

data class TaxCreditDto(

    val taxCredit: TaxCreditEntity? = null,
    val directorList: List<TaxCreditPersonalEntity> = mutableListOf(),

    )

interface PersonalSum{
    val lastYearAmount: Long?

    val currentAccruals: Long?

    val vanishingAmount: Long?

    val currentDeduction: Long?

    val amountCarriedForward: Long?
}

interface DirectorAndStake {
    val directorId: String
    val director: String
    val stake: Int
}

data class DirectorAndStakeDto(
    val director: String? = null,
    val stake: Int? = 0,
)