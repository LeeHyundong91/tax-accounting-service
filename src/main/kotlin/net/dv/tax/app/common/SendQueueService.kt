package net.dv.tax.app.common

import mu.KotlinLogging
import net.dv.tax.APP.AMQP.TAX_EXCHANGE_NAME
import net.dv.tax.APP.AMQP.TAX_ROUTE_NAME
import net.dv.tax.app.dto.QueueDto
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class SendQueueService(private val rabbitTemplate: RabbitTemplate) {

    private val log = KotlinLogging.logger {}

    fun sandMessage(data: QueueDto) {
        rabbitTemplate.convertAndSend(TAX_EXCHANGE_NAME, TAX_ROUTE_NAME, data)
    }
}