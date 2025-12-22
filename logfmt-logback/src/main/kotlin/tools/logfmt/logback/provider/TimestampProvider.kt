package tools.logfmt.logback.provider

import ch.qos.logback.classic.spi.ILoggingEvent
import tools.logfmt.logback.LogfmtProvider
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Suppress("MemberVisibilityCanBePrivate") // Public API
class TimestampProvider(
    var field: String = "time",
) : LogfmtProvider.Base() {
    companion object {
        private val utc = ZoneId.of("UTC")
        private val systemDefault = ZoneId.systemDefault()
    }

    override fun keyValues(event: ILoggingEvent) = listOf(field to doFormat(event))

    private var theZone: ZoneId = systemDefault
    private var theFormatter: DateTimeFormatter? = null
    private var theFormat: String? = null

    /**
     * Whether to localize the timestamp, meaning that the
     * timestamp will be adjusted to the local timezone.
     * Default is `true`. This only applies when there is
     * a custom format specified.
     */
    var local: Boolean?
        get() = theZone.id != utc.id
        set(value) {
            theZone = if (value == true) ZoneId.systemDefault() else utc
        }

    var format: String?
        get() = theFormat
        set(value) {
            theFormatter =
                value?.let {
                    when (it) {
                        "ISO_LOCAL_DATE" -> DateTimeFormatter.ISO_LOCAL_DATE
                        "ISO_OFFSET_DATE" -> DateTimeFormatter.ISO_OFFSET_DATE
                        "ISO_DATE" -> DateTimeFormatter.ISO_DATE
                        "ISO_LOCAL_TIME" -> DateTimeFormatter.ISO_LOCAL_TIME
                        "ISO_OFFSET_TIME" -> DateTimeFormatter.ISO_OFFSET_TIME
                        "ISO_TIME" -> DateTimeFormatter.ISO_TIME
                        "ISO_LOCAL_DATE_TIME" -> DateTimeFormatter.ISO_LOCAL_DATE_TIME
                        "ISO_OFFSET_DATE_TIME" -> DateTimeFormatter.ISO_OFFSET_DATE_TIME
                        "ISO_ZONED_DATE_TIME" -> DateTimeFormatter.ISO_ZONED_DATE_TIME
                        "ISO_DATE_TIME" -> DateTimeFormatter.ISO_DATE_TIME
                        "ISO_ORDINAL_DATE" -> DateTimeFormatter.ISO_ORDINAL_DATE
                        "ISO_WEEK_DATE" -> DateTimeFormatter.ISO_WEEK_DATE
                        "ISO_INSTANT" -> DateTimeFormatter.ISO_INSTANT
                        else -> DateTimeFormatter.ofPattern(it)
                    }
                }
            theFormat = value
        }

    private fun doFormat(event: ILoggingEvent): String =
        try {
            theFormatter?.format(event.instant.atZone(theZone)) ?: event.instant.toString()
        } catch (ex: Throwable) {
            println("error when formatting timestamp $ex")
            event.instant.toString()
        }
}
