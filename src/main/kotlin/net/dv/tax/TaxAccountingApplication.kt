package net.dv.tax

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaxAccountingApplication

fun main(args: Array<String>) {
    runApplication<TaxAccountingApplication>(*args)
}
