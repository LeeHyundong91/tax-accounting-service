package net.dv.tax.domain.view

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "V_HOSPITAL_MEMBER")
class VHospitalMember(

    @Id
    @Column(name = "COMPANY_ID")
    var id: String? = null,

    @Column(name = "ACCOUNT_ID")
    var accountId: String? = null,

    @Column(name = "EMAIL")
    var email: String? = null,

    @Column(name = "NAME")
    var name: String? = null,

    @Column(name = "ROLE_CODE")
    var role: String? = null,

    @Column(name = "IS_OWNER", columnDefinition = "BIT")
    var isOwner: Boolean? = null,

    @Column(name = "STAKE")
    var stake: String? = null,

    @Column(name = "STATUS")
    var status: String? = null

)
