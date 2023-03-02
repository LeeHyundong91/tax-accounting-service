package net.dv.tax.dto.purchase

data class ExcelRequiredDto (

    val writer: String,

    val hospitalId: String,

    val filePath: String,

    val year: String,

    val fileId: Long

)