package net.dv.tax.domain.common

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment


@Entity
@Table(name = "ACCOUNTING_ITEM")
@Comment("세무 계정과목")
class AccountingItemEntity(
    @Id @Column(name = "CODE")
    @Comment("계정과목 코드")
    val code: String = "",

    @Column(name = "NAME")
    @Comment("계정과목 이름")
    var name: String = ""
)