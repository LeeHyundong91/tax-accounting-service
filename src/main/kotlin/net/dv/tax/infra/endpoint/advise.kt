package net.dv.tax.infra.endpoint

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@Suppress("FunctionName")
@RestControllerAdvice
class EndpointExceptionHandler {
    private val log = KotlinLogging.logger {}

    @ExceptionHandler(Throwable::class)
    fun handle_500_response(t: Throwable): ResponseEntity<ErrorMessage> = t.response(HttpStatus.INTERNAL_SERVER_ERROR)

    private fun Throwable.response(status: HttpStatus): ResponseEntity<ErrorMessage> =
        ResponseEntity.status(status).body(
            ErrorMessage(this.message, this.stackTrace.map { it.toString() })
        ).also {
            log.error(this) { "error occurred!! : ${this.message}" }
        }
}

class ErrorMessage(
    val message: String?,
    val trace: List<String>,
)