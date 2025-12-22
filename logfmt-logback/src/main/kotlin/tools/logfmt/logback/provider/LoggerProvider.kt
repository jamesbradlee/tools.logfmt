package tools.logfmt.logback.provider

import ch.qos.logback.classic.spi.ILoggingEvent
import tools.logfmt.logback.LogfmtProvider

@Suppress("MemberVisibilityCanBePrivate") // Public API
class LoggerProvider(
    var field: String = "logger",
) : LogfmtProvider.Base() {
    override fun keyValues(event: ILoggingEvent) = listOf(field to event.loggerName)
}
