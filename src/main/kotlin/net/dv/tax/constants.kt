package net.dv.tax

object APP {
    object AMQP {
        const val TAX_EXCHANGE_NAME = "tax.excel.exchange"
        const val TAX_QUEUE_NAME    = "tax.excel.queue"
        const val TAX_ROUTE_NAME    = "tax.excel.route"
    }
}