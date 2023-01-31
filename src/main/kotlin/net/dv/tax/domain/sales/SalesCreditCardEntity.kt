package net.dv.tax.domain.sales

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*


@Comment("신용카드매출관리")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "sales_credit_card")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesCreditCardEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: Int,

    @Comment("승인일시")
    val approvalDate: Date,

    @Comment("승인번호")
    val approvalNo: String?,

    @Comment("자료구분")
    val dataType: String?,

    @Comment("면세카드")
    val dutyFreeCard: Long? = 0,

    @Comment("과세카드")
    val taxCard: Long? = 0,

    @Comment("봉사료")
    val serviceCharge: Long? = 0,

    @Comment("합계")
    val totalAmount: Long? = 0,

)
