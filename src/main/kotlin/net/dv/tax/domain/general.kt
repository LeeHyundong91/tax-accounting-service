package net.dv.tax.domain

import jakarta.persistence.AttributeConverter


interface Code {
    val code: String
    val label: String
}

abstract class AbstractCodeConverter<T>(val convert: (String) -> T): AttributeConverter<T, String> where T: Enum<T>, T: Code {
    override fun convertToDatabaseColumn(attribute: T?): String? {
        return attribute?.code
    }

    override fun convertToEntityAttribute(code: String?): T? {
        return code?.takeIf { it.isNotEmpty() }?.let { convert(code) }
    }
}