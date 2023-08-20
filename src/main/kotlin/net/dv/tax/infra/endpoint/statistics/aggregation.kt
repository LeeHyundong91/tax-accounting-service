package net.dv.tax.infra.endpoint.statistics

import net.dv.tax.Application
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/${Application.VERSION}/aggregation")
class StatisticsAggregationEndpoint {

}