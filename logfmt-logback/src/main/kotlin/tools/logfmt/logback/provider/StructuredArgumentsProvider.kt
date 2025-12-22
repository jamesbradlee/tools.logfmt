package tools.logfmt.logback.provider

import ch.qos.logback.classic.spi.ILoggingEvent
import org.slf4j.event.KeyValuePair
import tools.logfmt.logback.LogfmtProvider

@Suppress("MemberVisibilityCanBePrivate") // Public API
class StructuredArgumentsProvider(
    var prefix: String = "",
) : LogfmtProvider.Base() {
    override fun keyValues(event: ILoggingEvent) =
        (event.argumentArray?.mapNotNull { it as? KeyValuePair } ?: emptyList()).map { "$prefix${it.key}" to it.value }
}
