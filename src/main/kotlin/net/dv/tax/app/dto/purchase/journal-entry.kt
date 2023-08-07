package net.dv.tax.app.dto.purchase

import java.time.LocalDateTime


interface JournalEntryDto {
    var status: String?
    var requestedAt: LocalDateTime?
    var committedAt: LocalDateTime?
}