package net.dv.tax.service.app

import net.dv.tax.dto.app.DashboardMainDto
import org.springframework.stereotype.Service

@Service
class DashboardMainService {


    fun dashboard(hospitalId: String, yearMonth: String): DashboardMainDto{

        return DashboardMainDto()
    }

}