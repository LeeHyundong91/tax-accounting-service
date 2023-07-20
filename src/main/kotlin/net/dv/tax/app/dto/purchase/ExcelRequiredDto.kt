package net.dv.tax.app.dto.purchase

data class ExcelRequiredDto (

    val writer: String,

    val hospitalId: String,

    val year: String,

    val fileDataId: Long,

    var isTax: Boolean = false

)