package net.dv.tax.domain.purchase

import jakarta.persistence.*
import net.dv.tax.domain.AbstractCodeConverter
import net.dv.tax.domain.Code
import org.hibernate.annotations.Comment
import java.time.LocalDateTime


@Converter(autoApply = true)
class JournalEntryStatusConverter: AbstractCodeConverter<JournalEntryEntity.Status>(
    { cd -> JournalEntryEntity.Status.values().first { it.code == cd}}
)

@Entity
@Table(name = "JOURNAL_ENTRY")
@Comment("매입 항목에 대한 계정과목 분개 요청 / 처리 테이블")
class JournalEntryEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long = 0,

    @Column(name = "PURCHASE_ID")
    @Comment("매입 항목 레퍼런스")
    var purchaseId: Long = 0,

    @Column(name = "PURCHASE_TYPE")
    @Comment("매입 유형")
    var purchaseType: String? = null,

    @Column(name = "REQ_NOTE")
    @Comment("경비처리 요청 노트")
    var requestNote: String? = null,

    @Column(name = "REQ_EXPENSE")
    @Comment("경비여부")
    var checkExpense: Boolean? = null,

    @Column(name = "COMMIT_NOTE")
    @Comment("경비처리 확인 노트")
    var commitNote: String? = null,

    @Column(name = "ACCOUNTING_ITEM")
    @Comment("계정과목")
    var accountingItem: String? = null,

    @Column(name = "STATUS")
    @Comment("처리 상태")
    var status: Status? = null,

    @Column(name = "COMMITTER")
    @Comment("처리 담당자")
    var committer: String? = null,

    @Column(name = "REQUESTED_AT")
    @Comment("요청일")
    var requestedAt: LocalDateTime? = null,

    @Column(name = "COMMITTED_AT")
    @Comment("처리일")
    var committedAt: LocalDateTime? = null,
) {
    enum class Status(override val code: String, override val label: String): Code {
        REQUESTED("RQ", "요청완료"),
        CONFIRMED("CF", "확인됨"),
        REJECTED("RJ", "거절됨")
    }
}

@Entity
@Table(name = "JOURNAL_ENTRY_HISTORY")
@Comment("매입 항목에 대한 계정과목 분개 요청 / 처리 이력 테이블")
class JournalEntryHistoryEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long = 0,

    @Column(name = "PURCHASE_ID")
    @Comment("매입 항목 레퍼런스")
    var purchaseId: Long = 0,

    @Column(name = "PURCHASE_TYPE")
    @Comment("매입 유형")
    var purchaseType: String? = null,

    @Column(name = "NOTE")
    @Comment("비고 / 메모")
    val note: String? = null,

    @Column(name = "EXPENSE")
    @Comment("경비여부")
    val checkExpense: Boolean? = null,

    @Column(name = "ACCOUNTING_ITEM")
    @Comment("계정과목")
    val accountingItem: String? = null,

    @Column(name = "WRITER")
    @Comment("작성자")
    val writer: String? = null,

    @Column(name = "WRITTEN_AT")
    @Comment("작성일")
    val writtenAt: LocalDateTime
)
