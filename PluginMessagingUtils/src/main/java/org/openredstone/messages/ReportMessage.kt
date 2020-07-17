package org.openredstone.messages

import net.md_5.bungee.api.ChatColor
import org.openredstone.types.Report

class ReportMessage {
    var arguments: Array<String>
    var report: Report

    constructor(report: Report, arguments: Array<String>) {
        this.arguments = arguments
        this.report = report
    }

    constructor(serializedMessage: String) {
        val raw: Array<String> = serializedMessage.split(":").toTypedArray()
        if (raw.isEmpty()) {
            throw Exception("Not enough arguments provided in serialized message.")
        }
        require(isValidReport(raw[0])) { "Invalid action: " + raw[0] }
        report = Report.valueOf(raw[0])
        arguments = raw.copyOfRange(2, raw.size)
    }

    fun getSerializedMessage(): String =
        report.name + ":" + arguments.joinToString(":")

    val color: ChatColor
        get()
        = when (report) {
            Report.INFO -> ChatColor.WHITE
            Report.WARNING -> ChatColor.YELLOW
            Report.ERROR -> ChatColor.RED
        }

    companion object {
        private fun isValidReport(report: String?): Boolean {
            return try {
                Report.valueOf(report!!)
                true
            } catch (e: IllegalArgumentException) {
                false
            }
        }
    }
}
