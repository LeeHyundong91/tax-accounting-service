package net.dv.tax.controller.purchase

import mu.KotlinLogging
import net.dv.tax.domain.common.AccountingDataEntity
import net.dv.tax.dto.MenuCategoryCode
import net.dv.tax.service.common.AccountingDataService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/purchase")
class ExcelUploadController(
    private val accountingDataService: AccountingDataService,
) {
    private val log = KotlinLogging.logger {}


    @PostMapping("/upload/{categoryName}/{hospitalId}")
    fun uploadExcel(
        contents: List<MultipartFile>,
        @PathVariable categoryName: String,
        @PathVariable hospitalId: String,
    ): ResponseEntity<HttpStatus> {

        /**
         * TODO 임시 값 추후 AUth 정보에서 추출 해서 박아넣어야함
         * 혹은 PATH 에 추가로 받아야함
         */
        val writer = "tester"
        log.error { categoryName }

        return accountingDataService.saveOriginData(
            hospitalId,
            writer,
            MenuCategoryCode.valueOf(MenuCategoryCode.convert(categoryName)),
            contents
        )
    }


    @GetMapping("/excel/list/{categoryName}/{hospitalId}")
    fun uploadExcelList(
        @PathVariable categoryName: String,
        @PathVariable hospitalId: String,
    ): List<AccountingDataEntity> {
        return accountingDataService.getOriginDataList(
            hospitalId,
            MenuCategoryCode.valueOf(MenuCategoryCode.convert(categoryName))
        )
    }

    @PatchMapping("/excel/update/{id}")
    fun updateExcelFile(@PathVariable id: Long): ResponseEntity<HttpStatus>{
        /*TODO Jwt use get data*/
        val writer = "tester"
        return accountingDataService.updateOriginData(id, writer)
    }

    @DeleteMapping("/excel/update/{id}")
    fun deleteExcelFile(@PathVariable id: Long): ResponseEntity<HttpStatus>{
        /*TODO Jwt use get data*/
        val writer = "tester"
        return accountingDataService.deleteOriginData(id, writer)
    }


}