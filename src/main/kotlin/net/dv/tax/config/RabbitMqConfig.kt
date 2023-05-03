package net.dv.tax.config

import mu.KotlinLogging
import net.dv.tax.APP.AMQP.TAX_EXCHANGE_NAME
import net.dv.tax.APP.AMQP.TAX_QUEUE_NAME
import net.dv.tax.APP.AMQP.TAX_ROUTE_NAME
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMqConfig {

    private val log = KotlinLogging.logger {}

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
        return DirectExchange(TAX_EXCHANGE_NAME, true, true, null)
    }

    @Bean
    fun createQueue(): Queue {
        return Queue(TAX_QUEUE_NAME)
    }

    @Bean
    fun createBinding(): Binding {
        return BindingBuilder.bind(createQueue()).to(createDirectExchange()).with(TAX_ROUTE_NAME)
    }

    @Bean
    fun jsonMessageConverter(): MessageConverter? {
        return Jackson2JsonMessageConverter()
    }


}