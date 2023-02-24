package net.dv.tax.controller

import mu.KotlinLogging
import net.dv.tax.dto.MenuCategoryCode
import net.dv.tax.service.common.AccountingDataService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/purchase/upload")
class ExcelUploadController(
    private val accountingDataService: AccountingDataService,
) {
    private val log = KotlinLogging.logger {}


    @PostMapping("/{categoryName}")
    fun uploadExcel(contents: MultipartFile, @PathVariable categoryName: String): String {

        /**
         * TODO 임시 값 추후 AUth 정보에서 추출 해서 박아넣어야함
         * 혹은 PATH 에 추가로 받아야함
         */
        val hospitalId: String = "cid01"
        val writer: String = "tester"


        log.error { MenuCategoryCode.getName(categoryName) }

        accountingDataService.saveOriginData(
            hospitalId,
            writer,
            MenuCategoryCode.valueOf(MenuCategoryCode.getName(categoryName)),
            contents
        )

        return contents.originalFilename ?: "없어 그런거 야발!!"
    }


}