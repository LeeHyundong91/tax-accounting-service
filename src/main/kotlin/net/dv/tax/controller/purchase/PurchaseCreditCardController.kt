package net.dv.tax.controller.purchase

import mu.KotlinLogging
import net.dv.tax.domain.purchase.PurchaseCreditCardEntity
import net.dv.tax.service.purchase.PurchaseCreditCardService
import net.dv.tax.utils.ExcelComponent
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*


@RestController
@RequestMapping("/purchase/credit-card")
class PurchaseCreditCardController(
    private val excelComponent: ExcelComponent,
    private val purchaseCreditCardService: PurchaseCreditCardService
) {

    private val log = KotlinLogging.logger {}


    @PostMapping("/upload")
    fun upload(contents: MultipartFile): String {

        /**
         * TODO 임시 값 추후 AUth 정보에서 추출 해서 박아넣어야함
         * 혹은 PATH 에 추가로 받아야함
         */
        var hospitalId : Int = 0

        var purchaseCreditCardEntity = PurchaseCreditCardEntity()
        purchaseCreditCardEntity.hospitalId = 0
        purchaseCreditCardEntity.writer = "작성자"

        purchaseCreditCardService.cellToEntity(excelComponent.readExcel(contents), purchaseCreditCardEntity)

        return contents.originalFilename ?: "없어 그런거 야발!!"
    }



}