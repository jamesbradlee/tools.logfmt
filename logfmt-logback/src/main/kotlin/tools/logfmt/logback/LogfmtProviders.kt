package tools.logfmt.logback

import tools.logfmt.logback.provider.DefaultProviders
import tools.logfmt.logback.provider.LevelProvider
import tools.logfmt.logback.provider.LoggerProvider
import tools.logfmt.logback.provider.MdcProvider
import tools.logfmt.logback.provider.MessageProvider
import tools.logfmt.logback.provider.StructuredArgumentsProvider
import tools.logfmt.logback.provider.TimestampProvider

@Suppress("unused") // API for logback.xml
class LogfmtProviders {
    private var theProviders = mutableListOf<LogfmtProvider>()
    private var theStartedProviders = linkedSetOf<LogfmtProvider>()

    val activeProviders: List<LogfmtProvider> get() = theStartedProviders.toList()

    private val context = LogfmtProvider.Context(this)

    fun startAndAddProvider(provider: LogfmtProvider) {
        if (theStartedProviders.contains(provider)) return
        provider.start(context)
        theStartedProviders.add(provider)
    }

    fun stopAndRemoveProvider(provider: LogfmtProvider) {
        if (!theStartedProviders.contains(provider)) return
        theStartedProviders.remove(provider)
        provider.stop()
    }

    fun start() {
        theProviders.toList().forEach { startAndAddProvider(it) }
    }

    fun stop() {
        theProviders.toList().forEach { stopAndRemoveProvider(it) }
        if (theStartedProviders.isNotEmpty()) {
            throw IllegalStateException("Some providers were not stopped")
        }
    }

    fun addProvider(provider: LogfmtProvider) {
        theProviders.add(provider)
    }

    fun addAllProviders(providers: Collection<LogfmtProvider>) {
        theProviders.addAll(providers)
    }

    fun removeProvider(provider: LogfmtProvider) {
        theProviders.remove(provider)
    }

    fun clearProviders() = theProviders.clear()

    // Provider factory methods:

    fun addDefault(provider: DefaultProviders) = addProvider(provider)

    fun removeDefault(provider: DefaultProviders) = removeProvider(provider)

    fun addTimestamp(provider: TimestampProvider) = addProvider(provider)

    fun removeTimestamp(provider: TimestampProvider) = removeProvider(provider)

    fun addLogger(provider: LoggerProvider) = addProvider(provider)

    fun removeLogger(provider: LoggerProvider) = removeProvider(provider)

    fun addLevel(provider: LevelProvider) = addProvider(provider)

    fun removeLevel(provider: LevelProvider) = removeProvider(provider)

    fun addMessage(provider: MessageProvider) = addProvider(provider)

    fun removeMessage(provider: MessageProvider) = removeProvider(provider)

    fun addArguments(provider: StructuredArgumentsProvider) = addProvider(provider)

    fun removeArguments(provider: StructuredArgumentsProvider) = removeProvider(provider)

    fun addMdc(provider: MdcProvider) = addProvider(provider)

    fun removeMdc(provider: MdcProvider) = removeProvider(provider)
}
