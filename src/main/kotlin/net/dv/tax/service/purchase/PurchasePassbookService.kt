package net.dv.tax.service.purchase

import mu.KotlinLogging
import net.dv.tax.domain.purchase.PurchasePassbookEntity
import net.dv.tax.dto.purchase.ExcelRequiredDto
import net.dv.tax.repository.purchase.PurchasePassbookRepository
import org.dhatim.fastexcel.reader.Row
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PurchasePassbookService(private val passbookRepository: PurchasePassbookRepository) {
    private val log = KotlinLogging.logger {}

    fun getPassbookList(hospitalId: String, transactionDate: String, page: Pageable): List<PurchasePassbookEntity> {

        return passbookRepository.findAllByHospitalIdAndTransactionDateStartingWith(hospitalId, transactionDate, page)!!

    }

    fun savePassbookList(dataList: List<PurchasePassbookEntity>){
        passbookRepository.saveAll(dataList)
    }

    fun excelToEntitySave(requiredDto: ExcelRequiredDto, rows: MutableList<Row>) {

        val dataList = mutableListOf<PurchasePassbookEntity>()

        /*첫번째 행 삭제*/
        rows.removeFirst()

        /*하위 행 삭제 */
        rows.removeLast()

        rows.forEach {
            val transactionDate = requiredDto.year + "-" + it.getCell(0).rawValue
            val data =
                PurchasePassbookEntity(
                    hospitalId = requiredDto.hospitalId,
                    dataFileId = requiredDto.fileDataId,
                    transactionDate = transactionDate,
                    summary = it.getCell(1)?.rawValue.toString(),
                    depositAmount = it.getCell(2)?.rawValue?.toLong(),
                    withdrawnAmount = it.getCell(3)?.rawValue?.toLong(),
                    balance = it.getCell(4)?.rawValue?.toLong()
                )
            dataList.add(data)
        }
        log.info { dataList }
        savePassbookList(dataList)
    }

}