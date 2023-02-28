package net.dv.tax.dto.employee

import java.time.LocalDateTime

data class EmployeeAttachFileDto (

    val id: Long,

    var fileName: String? = null,

    var localFileName: String? = null,

    var path: String? = null,

    var fileSize: Long? = null,

    var fileExt: String? = null,

    var useYn: String? = null,

    var createdAt: LocalDateTime? = LocalDateTime.now(),

    val employeeId: String

)

