package net.dv.tax

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class TaxAccountingApplication

fun main(args: Array<String>) {
    runApplication<TaxAccountingApplication>(*args)
}
