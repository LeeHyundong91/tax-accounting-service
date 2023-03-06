package net.dv.tax

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
class TaxAccountingApplication{
    companion object {
        const val VERSION: String = "v1"
    }
}

fun main(args: Array<String>) {
    runApplication<TaxAccountingApplication>(*args)
}
