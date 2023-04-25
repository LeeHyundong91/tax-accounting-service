package net.dv.tax.service.sales

import net.dv.tax.repository.sales.MedicalCareRepository
import org.springframework.stereotype.Service

@Service
class MedicalCareService(private val medicalCareRepository: MedicalCareRepository) {
}