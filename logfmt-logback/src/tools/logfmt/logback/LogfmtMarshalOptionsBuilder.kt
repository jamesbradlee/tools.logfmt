package tools.logfmt.logback

import tools.logfmt.LogFmtMarshalOptions

@Suppress("unused") // API class for logback.xml
class LogfmtMarshalOptionsBuilder(
    private var theOptions: LogFmtMarshalOptions = LogFmtMarshalOptions(),
) {
    val options: LogFmtMarshalOptions get() = theOptions

    fun getBooleanAsFlag(): Boolean = theOptions.booleanAsFlag

    fun setBooleanAsFlag(booleanAsFlag: Boolean) {
        theOptions = theOptions.copy(booleanAsFlag = booleanAsFlag)
    }

    fun getQuoteEmptyStringsAndNull(): Boolean = theOptions.quoteEmptyStringsAndNull

    fun setQuoteEmptyStringsAndNull(quoteEmptyStringsAndNull: Boolean) {
        theOptions = theOptions.copy(quoteEmptyStringsAndNull = quoteEmptyStringsAndNull)
    }
}
