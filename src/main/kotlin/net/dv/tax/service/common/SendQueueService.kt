package net.dv.tax.service.common

import mu.KotlinLogging
import net.dv.tax.dto.QueueDto
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SendQueueService(private val rabbitTemplate: RabbitTemplate) {

    @Value("\${dv.rabbit.exchange}")
    val exchange: String = ""

    @Value("\${dv.rabbit.route-key}")
    val routingKey: String = ""


    private val log = KotlinLogging.logger {}

    fun sandMessage(data: QueueDto) {
        rabbitTemplate.convertAndSend(exchange, routingKey, data)
    }

}