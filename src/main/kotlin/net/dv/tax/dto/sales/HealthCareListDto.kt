package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment

data class HealthCareListDto(

    var healthcareList: List<HealthCareDto>,

    var listTotal: HealthCareDto,

    )

data class HealthCareDto(

    @Comment("지급일자 - payDt")
    var paidDate: String? = null,

    @Comment("청구금액 - hcDmdAmt")
    var claimAmount: Long? = 0,

    @Comment("조정금액 - hcAdjsAmt")
    var adjustedAmount: Long? = 0,

    @Comment("검진금액 - hcAmt")
    var screeningAmount: Long? = 0,

    @Comment("삭감금액 - hcToatRdAmt")
    var deductedAmount: Long? = 0,

    @Comment("지급금액 - hcPayTgtAmt")
    var paidAmount: Long? = 0,

    @Comment("가산인원 - hcHdayAddNop")
    var addedPersons: Long? = 0,

    @Comment("가산금액 - hcHdayAddAmt")
    var addedAmount: Long? = 0,

    @Comment("추가지급액 - adpyAmt")
    var additionalPayment: Long? = 0,
    
    @Comment("세액 - whtxTxam")
    var taxAmount: Long? = 0,

    @Comment("환수액 - rdamAmt")
    var refundAmount: Long? = 0,

    @Comment("공제액 - gongjeAmt")
    var deductionAmount: Long? = 0,

    @Comment("채권압류액 - grnsTgtSpcd")
    var receivableAmount: Long? = 0,

    @Comment("송금액 - rpymAmt")
    var remittanceAmount: Long? = 0,
)

