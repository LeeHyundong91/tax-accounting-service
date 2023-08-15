package net.dv.tax.app

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort


abstract class AbstractSearchQueryDto {
    var page: Int = 1
    var pageSize: Int = 30
    var sort: List<String> = mutableListOf()

    val pageable: Pageable get() {
        val sort = sort.map {
            val (column, direction) = it.split(",")
            println("--> $column, $direction")
            when(direction) {
                "asc" -> Sort.Order.asc(column)
                "desc" -> Sort.Order.desc(column)
                else -> null
            }
        }.filterNotNull().let {
            Sort.by(it)
        }

        return PageRequest.of(page - 1, pageSize, sort)
    }
}