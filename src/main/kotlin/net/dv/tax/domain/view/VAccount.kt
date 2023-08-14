package net.dv.tax.domain.view

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "V_ACCOUNT")
data class VAccount(
    @Id
    @Column(name = "NO")
    var no: Int? = null,

    @Column(name = "ACCOUNT_ID")
    var accountId: String? = null,

    @Column(name = "EMAIL")
    var email: String? = null,

    @Column(name = "NAME")
    var name: String? = null,

    @Column(name = "PHONE")
    var phone: String? = null,

    @Column(name = "STATUS")
    var status: String? = null,

    @Column(name = "GROUPS")
    var groups: Int? = null,

    @Column(name = "PASSWORD")
    var password: String? = null,

    @Column(name = "PROFILE_URI")
    var profileUri: String? = null,

    @Column(name = "WRITER_ID")
    var writerId: String? = null,

    @Column(name = "WRITER_NAME")
    var writerName: String? = null,

    @Column(name = "REGISTERED_AT")
    var registeredAt: LocalDateTime? = null,

    @Column(name = "UPDATED_AT")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "DORMANT_AT")
    var dormantAt: LocalDateTime? = null,
)