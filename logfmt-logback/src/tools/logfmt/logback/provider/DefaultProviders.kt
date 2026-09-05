package tools.logfmt.logback.provider

import ch.qos.logback.classic.spi.ILoggingEvent
import tools.logfmt.logback.LogfmtProvider

@Suppress("MemberVisibilityCanBePrivate") // Public API
class DefaultProviders : LogfmtProvider.Base() {
    override fun keyValues(event: ILoggingEvent) = emptyList<Pair<String, Any?>>()

    private val timestamp = TimestampProvider()
    private val level = LevelProvider()
    private val logger = LoggerProvider()
    private val message = MessageProvider()
    private val structuredArguments = StructuredArgumentsProvider()
    private val mdc = MdcProvider()

    private val providers =
        listOf(timestamp, level, logger, message, structuredArguments, mdc)

    override fun start(context: LogfmtProvider.Context) {
        super.start(context)
        providers.forEach(context.providers::startAndAddProvider)
    }

    override fun stop() {
        if (!isStarted()) return super.stop()
        val context = context
        super.stop()
        providers.forEach(context.providers::stopAndRemoveProvider)
    }
}
