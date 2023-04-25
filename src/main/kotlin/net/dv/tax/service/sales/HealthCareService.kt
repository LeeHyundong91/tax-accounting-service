package net.dv.tax.service.sales

import net.dv.tax.repository.sales.HealthCareRepository
import org.springframework.stereotype.Service

@Service
class HealthCareService(private val healthCareRepository: HealthCareRepository) {
}