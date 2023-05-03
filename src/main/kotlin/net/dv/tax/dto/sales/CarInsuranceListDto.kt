package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment


data class CarInsuranceListDto(

    var carInsuranceList: List<CarInsuranceDto>,

    var listTotal: CarInsuranceDto? = null,

    )

data class CarInsuranceDto(

    @Comment("진료년월 - diagYyyymm")
    var treatmentYearMonth: String? = null,

    @Comment("청구건수 - totDmdCnt")
    var claimItemsCount: Long? = null,

    @Comment("진료비총액 (심사결정) - edecTdamt")
    var decisionTotalAmount: Long? = null,

    @Comment("환자납부총액 (심사결정) - edecPtntPymnTotAmt")
    var decisionPatientPayment: Long? = null,

    @Comment("청구총액 (심사결정) - edecAmt")
    var decisionAmount: Long? = null,
)