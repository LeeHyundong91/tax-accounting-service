package net.dv.tax.app.dto

data class ResponseFileUploadDto(

    var message: String? = null,

    var result: Any? = null,

    )

data class ResponseEmployeeFileUploadDto(
    var fileName: String,
    var filePath: String
)