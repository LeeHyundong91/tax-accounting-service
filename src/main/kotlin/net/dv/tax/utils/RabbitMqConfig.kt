package net.dv.tax.utils

import mu.KotlinLogging
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMqConfig {

    private val log = KotlinLogging.logger {}

    @Value("\${dv.rabbit.queue}")
    var queue: String = ""

    @Value("\${dv.rabbit.exchange}")
    val exchange: String = ""

    @Value("\${dv.rabbit.route-key}")
    val routingKey: String = ""


    @Bean
    fun simpleRabbitListenerContainerFactory(connectionFactory: ConnectionFactory?): SimpleRabbitListenerContainerFactory? {
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        factory.setMessageConverter(jsonMessageConverter())
        return factory
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory?): RabbitTemplate? {
        val rabbitTemplate = RabbitTemplate(connectionFactory!!)
        rabbitTemplate.messageConverter = jsonMessageConverter()!!
        return rabbitTemplate
    }


    @Bean
    fun createDirectExchange(): DirectExchange {
        return DirectExchange(exchange, true, true, null)
    }

    @Bean
    fun createQueue(): Queue {
        return Queue(queue)
    }

    @Bean
    fun createBinding(): Binding {
        return BindingBuilder.bind(createQueue()).to(createDirectExchange()).with(routingKey)
    }

    @Bean
    fun jsonMessageConverter(): MessageConverter? {
        return Jackson2JsonMessageConverter()
    }


}