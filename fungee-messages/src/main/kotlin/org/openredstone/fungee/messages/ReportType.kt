package org.openredstone.fungee.messages

import kotlinx.serialization.Serializable

enum class ReportType {
    INFO, WARNING, ERROR
}

@Serializable
data class Report(val type: ReportType, val message: String)
