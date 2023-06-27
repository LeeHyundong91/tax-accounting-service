package net.dv.tax.dto.consulting

import net.dv.tax.domain.consulting.TaxCreditEntity
import net.dv.tax.domain.consulting.TaxCreditPersonalEntity

data class TaxCreditDto(

    val taxCredit: TaxCreditEntity? = null,
    val directorList: List<TaxCreditPersonalEntity> = mutableListOf(),

    )
