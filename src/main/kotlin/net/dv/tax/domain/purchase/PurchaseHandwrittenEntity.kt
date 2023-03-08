package net.dv.tax.domain.purchase

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@Table(name = "purchase_handwritten")
@Comment("수기대상매입관리")
@Suppress("JpaAttributeTypeInspection")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class PurchaseHandwrittenEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var hospitalId: String? = null,

    var dataFileId: Long? = null,

    @Comment("발급일자")
    val issueDate: String?,

    @Comment("품목")
    val itemName: String?,

    @Column(name = "SUPPLY_PRICE")
    @Comment("공급가액")
    var supplyPrice: Long? = 0,

    @Comment("세액")
    val taxAmount: Long? = 0,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    val writer: String? = null,

    @Column(name = "IS_DELETE")
    val delete: Boolean = false,

    )