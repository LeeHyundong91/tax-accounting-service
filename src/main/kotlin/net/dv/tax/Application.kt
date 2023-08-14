package net.dv.tax

import net.dv.access.EnableAccessControl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
@EnableScheduling
//@EnableJpaRepositories(repositoryImplementationPostfix = "d")
@EnableAccessControl
class Application {
    companion object {
        const val VERSION: String = "v1"
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
