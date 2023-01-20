package net.dv.tax.controller.purchase

import mu.KotlinLogging
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

        purchaseCreditCardService.rowDataMapper(excelComponent.readExcel(contents))

        return contents.originalFilename ?: "없어 그런거 야발!!"
    }



}