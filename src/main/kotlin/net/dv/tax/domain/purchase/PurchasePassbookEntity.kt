package net.dv.tax.domain.purchase

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Table(name = "purchase_passbook")
@Comment("통장매입관리")
@Suppress("JpaAttributeTypeInspection")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
@Entity
data class PurchasePassbookEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("병원아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String? = null,

    @Comment("업로드 파일 ID")
    @Column(name = "DATA_FILE_ID")
    var dataFileId: Long? = null,

    @Comment("거래일")
    @Column(name = "TRANSACTION_DATE")
    val transactionDate: String? = null,

    @Comment("적요")
    @Column(name = "SUMMARY")
    val summary: String? = null,

    @Comment("입금액")
    @Column(name = "DEPOSIT_AMOUNT")
    val depositAmount: Long? = null,

    @Comment("출금액")
    @Column(name = "WITHDRAWN_AMOUNT")
    val withdrawnAmount: Long? = null,

    @Comment("잔액")
    @Column(name = "BALANCE")
    val balance: Long? = null,

    @Comment("등록일(업로드일시")
    @CreatedDate
    @Column(name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),


    )