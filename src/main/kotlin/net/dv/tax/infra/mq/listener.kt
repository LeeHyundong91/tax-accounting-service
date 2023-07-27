package net.dv.tax.infra.mq

import mu.KotlinLogging
import net.dv.tax.app.dto.QueueDto
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component


@Component
class TaxAccountingMessageListener {

    private val log = KotlinLogging.logger {}

//    TODO("메시지 큐 리스너 위치 전환이 필요. command 인터페이스 정의도 필요함.")
//    @RabbitListener(queues = ["tax.excel.queue"])
//    fun getMessage(@Payload result: QueueDto) {
//        log.error { "upload before !!" }
//        purchaseCreditCardRepository.saveAll(result.creditCard!!)
//        log.error { "upload after !!" }
//    }
}