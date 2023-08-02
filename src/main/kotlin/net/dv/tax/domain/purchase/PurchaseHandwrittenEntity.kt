package net.dv.tax.domain.purchase

import jakarta.persistence.*
import net.dv.tax.domain.AbstractCodeConverter
import net.dv.tax.domain.Code
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@Table(name = "PURCHASE_HANDWRITTEN")
@Comment("수기세금계산서 / 간이영수증 - 매입")
@Suppress("JpaAttributeTypeInspection")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class PurchaseHandwrittenEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "BOOK_TYPE")
    @Comment("매출표 유형")
    var type: Type? = null,

    var hospitalId: String? = null,

    var dataFileId: Long? = null,

    @Comment("발급일자")
    val issueDate: String?,

    @Column(name = "SUPPLIER")
    @Comment("공급자")
    val supplier: String?,

    @Comment("품목")
    val itemName: String?,

    @Column(name = "SUPPLY_PRICE")
    @Comment("공급가액")
    var supplyPrice: Long? = 0,

    @Column(name = "DEBIT_ACCOUNT")
    @Comment("차변계정")
    var debitAccount: String? = null,

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
) {
    enum class Type(override val code: String,
                    override val label: String): Code {
        BASIC_RECEIPT("BR", "간이영수증"),
        TAX_INVOICE("TI", "수기세금계산서"),
    }

    @Converter(autoApply = true)
    class TypeConvertor: AbstractCodeConverter<Type>({cd -> Type.values().first { cd == it.code }})
}