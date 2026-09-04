package tools.logfmt.logback.provider

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import tools.logfmt.logback.LogfmtProvider

@Suppress("MemberVisibilityCanBePrivate") // Public API
class LevelProvider(
    var field: String = "level",
    var mapping: Mapping = Mapping.Default,
) : LogfmtProvider.Base() {
    override fun keyValues(event: ILoggingEvent) = listOf(field to doFormat(event))

    private fun doFormat(event: ILoggingEvent): String =
        when (event.level) {
            Level.TRACE -> mapping.trace
            Level.DEBUG -> mapping.debug
            Level.INFO -> mapping.info
            Level.WARN -> mapping.warn
            Level.ERROR -> mapping.error
            else -> event.level.levelStr.lowercase()
        }

    open class Mapping(
        var trace: String = Level.TRACE.levelStr.lowercase(),
        var debug: String = Level.DEBUG.levelStr.lowercase(),
        var info: String = Level.INFO.levelStr.lowercase(),
        var warn: String = Level.WARN.levelStr.lowercase(),
        var error: String = Level.ERROR.levelStr.lowercase(),
    ) {
        companion object Default : Mapping()
    }
}
