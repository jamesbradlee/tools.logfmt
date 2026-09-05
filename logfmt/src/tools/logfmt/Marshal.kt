package tools.logfmt

fun Testing() {}

/**
 * Marshal a map of fields into a logfmt formatted string.
 *
 * @param fields The map of fields to marshal.
 * @param options Options for marshaling.
 * @return A logfmt formatted string.
 */
fun marshal(
    fields: Map<String, Any?>,
    options: LogFmtMarshalOptions = LogFmtMarshalOptions(),
): String {
    var line = ""
    var separator = ""

    for ((key, value) in fields) {
        if (value is Boolean && options.booleanAsFlag) {
            if (value) {
                line += separator + quoteValue(key, true)
                if (separator == "") {
                    separator = " "
                }
            }
            continue
        }

        line += separator + quoteValue(key, true) + "=" + quoteValue(value, options.quoteEmptyStringsAndNull)
        if (separator == "") {
            separator = " "
        }
    }

    return line
}

private fun quoteValue(
    value: Any?,
    quoteEmptyStringsAndNull: Boolean,
): String {
    value ?: return if (quoteEmptyStringsAndNull) "\"\"" else "null"

    val theValue = value.toString()

    if (theValue == "") {
        return if (quoteEmptyStringsAndNull) "\"\"" else ""
    }

    var out = ""
    var needsQuoting = false

    for (char in theValue) {
        out +=
            when (char) {
                '"' -> {
                    "\\\""
                }

                '\\' -> {
                    "\\\\"
                }

                '\r' -> {
                    "\\r"
                }

                '\n' -> {
                    "\\n"
                }

                '\t' -> {
                    "\\t"
                }

                '\b' -> {
                    "\\b"
                }

                '\u000C' -> {
                    "\\f"
                }

                else -> {
                    if (
                        char <= '\u001F' ||
                        (char in '\u007F'..'\u009F') ||
                        (char in '\u2000'..'\u20FF')
                    ) {
                        "\\u" +
                            char.code
                                .toString(16)
                                .padStart(4, '0')
                                .uppercase()
                    } else {
                        char
                    }
                }
            }

        if (char.isWhitespace() || char == '=' || char == '\'') {
            needsQuoting = true
        }
    }

    return if (needsQuoting) "\"$out\"" else out
}
