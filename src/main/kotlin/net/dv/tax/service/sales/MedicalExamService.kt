package net.dv.tax.service.sales

import net.dv.tax.repository.sales.MedicalExamRepository
import net.dv.tax.service.feign.DataReceiveFeignService

class MedicalExamService(
    private val medicalExamRepository: MedicalExamRepository,
    private val dataReceiveFeignService: DataReceiveFeignService,
) {

    fun saveData() {


    }

}