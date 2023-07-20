package net.dv.tax.app.dashboard

import net.dv.tax.app.dto.app.DashboardMainDto
import org.springframework.stereotype.Service

@Service
class DashboardMainService {


    fun dashboard(hospitalId: String, yearMonth: String): DashboardMainDto{

        return DashboardMainDto()
    }

}