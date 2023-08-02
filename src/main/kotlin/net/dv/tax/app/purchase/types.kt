package net.dv.tax.app.purchase


interface JournalEntry {
    var note: String
    var checkExpense: String
    var accountItem: String
    var status: String
    var requester: String
    var committer: String
}