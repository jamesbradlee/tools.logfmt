package tools.logfmt

data class LogFmtMarshalOptions(
    /**
     * If true, booleans will be marshaled as "key key2 key3" instead of
     * "key=true key2=true key3=true" when they are true. When false, the
     * keys will be omitted entirely. When this setting is set to false,
     * the keys and values will be "key=false key2=false key3=false" when
     * the boolean is false, and "key=true key2=false key3=true" when the
     * boolean is true.
     */
    val booleanAsFlag: Boolean = false,
    /**
     * If true, empty strings and null values will be marshaled as
     * `key="" key2="" key3=""` when the values are empty strings or
     * null. When false, the values will be encoded with a trailing
     * equals sign, like `key= key2= key3=null`.
     *
     * For keys, this value will forcibly be set to true.
     */
    val quoteEmptyStringsAndNull: Boolean = false,
)
