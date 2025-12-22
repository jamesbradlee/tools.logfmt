package tools.logfmt.logback.provider

import ch.qos.logback.classic.spi.ILoggingEvent
import tools.logfmt.logback.LogfmtProvider

@Suppress("MemberVisibilityCanBePrivate") // Public API
class MdcProvider(
    var prefix: String = "",
) : LogfmtProvider.Base() {
    override fun keyValues(event: ILoggingEvent) = event.mdcPropertyMap.map { (key, value) -> "$prefix$key" to value }
}
