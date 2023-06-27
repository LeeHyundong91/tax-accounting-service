package net.dv.tax.dto.consulting

interface DirectorAndStake {
    val director: String
    val stake: Int
}

data class DirectorAndStakeDto(
    val director: String? = null,
    val stake: Int? = 0,
)