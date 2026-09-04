package tools.logfmt.logback

/**
 * Creates a key-value pair that can be used in a log message.
 *
 * The key of the key-value pair will be included in the output of the
 * formatted log message, and it will also be included in the structured data.
 *
 * @param key The key of the key-value pair.
 * @param value The value of the key-value pair. If null, it will be represented as "null" in the log message.
 * @return A [StructuredArgument] representing the key-value pair.
 */
fun kv(
    key: String,
    value: Any?,
) = StructuredArgument(key, value, StructuredArgument.Options(includeKey = true))

/**
 * Creates a key-value pair that can be used in a log message.
 *
 * The key of the key-value pair will not be included in the output of the
 * formatted log message, but it will still be included in the structured data.
 *
 * @param key The key of the key-value pair.
 * @param value The value of the key-value pair. If null, it will be represented as "null" in the log message.
 * @return A [StructuredArgument] representing the key-value pair without the key in the output.
 */
fun v(
    key: String,
    value: Any?,
) = StructuredArgument(key, value, StructuredArgument.Options(includeKey = false))
