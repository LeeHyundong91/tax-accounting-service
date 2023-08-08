package net.dv.tax.infra.endpoint.consulting

import net.dv.tax.Application
import net.dv.tax.app.consulting.ConsultingReportOperationCommand
import net.dv.tax.app.consulting.ConsultingReportQueryCommand
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/${Application.VERSION}/consulting/report")
class ConsultingController(
    val operationCommand: ConsultingReportOperationCommand,
    val queryCommand: ConsultingReportQueryCommand
) {

    //세무담당자 컨설팅리포트 등록하기

    //세무담당자 목록 조회하기

    //세무담당자 컨설팅리포트 수정하기

}