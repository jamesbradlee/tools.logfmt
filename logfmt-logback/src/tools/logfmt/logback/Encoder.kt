package tools.logfmt.logback

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.encoder.EncoderBase
import tools.logfmt.LogFmtMarshalOptions
import tools.logfmt.marshal

@Suppress("unused") // Public API for logback.xml
class Encoder : EncoderBase<ILoggingEvent>() {
    private var marshalingOptions = LogfmtMarshalOptionsBuilder()
    private var providers = LogfmtProviders()

    override fun start() {
        super.start()
        providers.start()
    }

    override fun stop() {
        providers.stop()
        super.stop()
    }

    override fun encode(event: ILoggingEvent): ByteArray {
        if (!isStarted) {
            throw IllegalStateException("Encoder is not started")
        }
        return (
            marshal(
                linkedMapOf(*providers.activeProviders.flatMap { it.keyValues(event) }.toTypedArray()),
                marshalingOptions.options,
            ) + "\n"
        ).toByteArray(charset = Charsets.UTF_8)
    }

    override fun headerBytes(): ByteArray = EMPTY_BYTES

    override fun footerBytes(): ByteArray = EMPTY_BYTES

    fun getOptions(): LogFmtMarshalOptions = marshalingOptions.options

    fun setOptions(options: LogfmtMarshalOptionsBuilder) {
        marshalingOptions = options
    }

    fun getProviders(): LogfmtProviders = providers

    fun setProviders(providers: LogfmtProviders) {
        this.providers = providers
    }
}

private val EMPTY_BYTES = ByteArray(0)
