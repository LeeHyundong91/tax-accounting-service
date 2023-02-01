package net.dv.tax.controller.purchase

import mu.KotlinLogging
import net.dv.tax.domain.purchase.PurchaseCreditCardEntity
import net.dv.tax.service.common.AccountingDataService
import net.dv.tax.service.purchase.PurchaseCreditCardService
import net.dv.tax.utils.AwsS3Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*


@RestController
@RequestMapping("/v1/purchase/credit-card")
class PurchaseCreditCardController(
    private val purchaseCreditCardService: PurchaseCreditCardService,
    private val accountingDataService: AccountingDataService,
    private val awsS3Service: AwsS3Service
) {

    private val log = KotlinLogging.logger {}


    @PostMapping("/upload")
    fun upload(contents: MultipartFile): String {

        /**
         * TODO 임시 값 추후 AUth 정보에서 추출 해서 박아넣어야함
         * 혹은 PATH 에 추가로 받아야함
         */
        var hospitalId: Int = 0

        var purchaseCreditCardEntity = PurchaseCreditCardEntity()
        purchaseCreditCardEntity.hospitalId = 0
        purchaseCreditCardEntity.writer = "작성자"


        accountingDataService.saveOriginData(0, "tester", "credit-card", contents)


        return contents.originalFilename ?: "없어 그런거 야발!!"
    }

    @GetMapping("/read")
    fun readFile() {

        var hospitalId: Int = 0

        var purchaseCreditCardEntity = PurchaseCreditCardEntity()
        purchaseCreditCardEntity.hospitalId = 0
        purchaseCreditCardEntity.writer = "작성자"


//        purchaseCreditCardService.cellToEntity(purchaseCreditCardEntity)


    }
    @GetMapping("/test")
    fun testFile() {

        var hospitalId: Int = 0

        var fileName = "origin/2023/01/27/credit-card_18:32_6d41c4e3e09b47e4baccf26a7066a392.xls"

        var purchaseCreditCardEntity = PurchaseCreditCardEntity()
        purchaseCreditCardEntity.hospitalId = 0
        purchaseCreditCardEntity.writer = "작성자"


        purchaseCreditCardService.cellToEntity(purchaseCreditCardEntity, fileName)


    }


}