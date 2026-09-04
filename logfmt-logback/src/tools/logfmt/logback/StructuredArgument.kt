package tools.logfmt.logback

import org.slf4j.event.KeyValuePair

/**
 * A structured argument is a key-value pair that can be used in a log message,
 * but will also add a key-value pair to the structured data of the log
 * message. The options for a structured argument can be configured to not show
 * the key in the output of the formatted log message, but it will still be
 * included in the structured data of the log message.
 */
class StructuredArgument(
    key: String,
    value: Any?,
    val options: Options = DefaultOptions,
) : KeyValuePair(key, value) {
    data class Options(
        /**
         * Whether to include the key in the output for the message.
         */
        val includeKey: Boolean = true,
    )

    private companion object {
        private val DefaultOptions = Options()
    }

    override fun toString(): String {
        val key = if (options.includeKey) "$key=" else ""
        return "$key$value"
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false
        if (!super.equals(o)) return false

        o as StructuredArgument

        return options == o.options
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + options.hashCode()
        return result
    }
}
