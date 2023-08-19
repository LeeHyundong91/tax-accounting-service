package net.dv.tax.infra.endpoint.purchase

import net.dv.access.Jwt
import net.dv.tax.Application
import net.dv.tax.app.AbstractSearchQueryDto
import net.dv.tax.app.Period
import net.dv.tax.app.enums.purchase.PurchaseType
import net.dv.tax.app.purchase.*
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime


@RestController
@RequestMapping("/${Application.VERSION}/purchase/journal-entry")
class JournalEntryEndpoint(
    private val command: JournalEntryCommand,
) {

    @GetMapping("/types")
    fun types(): ResponseEntity<List<Any>> = ResponseEntity.ok(
        PurchaseType.values().map { mapOf("code" to it.code, "label" to it.label) })

    @GetMapping("/{hospitalId}")
    fun list(@Jwt("sub") accountId: String?,
             @PathVariable hospitalId: String,
             query: QueryRequest): ResponseEntity<Page<PurchaseBookOverview>> {
        val today = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDate()
        val res = command.expenseByHospital(hospitalId) {
            val reg = """^(\d*)M$""".toRegex()

            category = JournalEntryCommand.Category.valueOf(query.category)
            period = query.term?.uppercase()?.takeIf { reg.matches(it)}
                ?.let {
                    val months = reg.find(it)!!.groupValues[1].toLong()
                    Period(today.minusMonths(months), today)
                }
                ?: Period(query.begin, query.end)
            types = query.type

            page = query.page
            pageSize = query.pageSize
            sort = query.sort
        }
        return ResponseEntity.ok(res)
    }

    @GetMapping("/{type}/{id}")
    fun view(@PathVariable type: String,
             @PathVariable id: Long,
             @RequestParam("check", required = false) check: String?): ResponseEntity<JournalEntry> {
        val res = command.get(PurchaseBookDto(id, PurchaseType[type]))
        return ResponseEntity.ok(res)
    }

    @GetMapping("/{type}/{id}/history")
    fun history(@PathVariable type: String,
                @PathVariable id: Long): ResponseEntity<PurchaseBookOverview> {
        val res = command.history(PurchaseBookDto(id, PurchaseType[type]))
        return ResponseEntity.ok(res)
    }

    @PostMapping("/{type}/{id}")
    fun requestExpense(@PathVariable type: String,
                       @PathVariable id: Long,
                       @RequestBody data: JournalEntryReqDto,
                       @Jwt("sub") requester: String?): ResponseEntity<Any> {
        val res = command.request(PurchaseBookDto(id, PurchaseType[type]), data)
        return ResponseEntity.ok(res)
    }

    @PutMapping("/{type}/{id}")
    fun checkAndRequest(@PathVariable type: String,
                        @PathVariable id: Long,
                        @RequestBody data: JournalEntryReqDto): ResponseEntity<Any> {
        val res = command.confirm(PurchaseBookDto(id, PurchaseType[type]), data)
        return ResponseEntity.ok(res)
    }

    @GetMapping("/{hospitalId}/{type}/state")
    fun processingState(@PathVariable hospitalId: String,
                        @PathVariable type: String,
                        query: QueryRequest): ResponseEntity<Page<out JournalEntryStatus>> {
        val res = command.processingState(PurchaseType[type], hospitalId, query.pageable)

        return ResponseEntity.ok(res)
    }

    data class QueryRequest(
        val category: String = "ALL",
        var type: List<PurchaseType>? = null,
        var begin: LocalDate? = null,
        var end: LocalDate? = null,
        var term: String? = null,
    ): AbstractSearchQueryDto()
}
