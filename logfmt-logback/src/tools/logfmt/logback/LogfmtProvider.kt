package tools.logfmt.logback

import ch.qos.logback.classic.spi.ILoggingEvent

interface LogfmtProvider {
    fun keyValues(event: ILoggingEvent): List<Pair<String, Any?>>

    fun isStarted(): Boolean

    fun start(context: Context)

    fun stop()

    data class Context(
        val providers: LogfmtProviders,
    )

    abstract class Base : LogfmtProvider {
        private var theStarted = false
        private var theContext: Context? = null
        protected val context: Context get() = theContext ?: throw IllegalStateException("Not started")

        override fun isStarted() = theStarted

        override fun start(context: Context) {
            if (theStarted) throw IllegalStateException("Already started")
            theStarted = true
            this.theContext = context
        }

        override fun stop() {
            if (!theStarted) throw IllegalStateException("Not started")
            theStarted = false
            theContext = null
        }
    }
}
