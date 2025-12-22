package tools.logfmt

import kotlin.test.Test
import kotlin.test.assertEquals

class MarshalTests {
    @Test
    fun `marshals map with one entry`() {
        val map = linkedMapOf("key" to "value")
        val marshaled = marshal(map)
        assertEquals("key=value", marshaled)
    }

    @Test
    fun `marshals map with two entries`() {
        val map = linkedMapOf("key1" to "value1", "key2" to "value2")
        val marshaled = marshal(map)
        assertEquals("key1=value1 key2=value2", marshaled)
    }

    @Test
    fun `marshals map with boolean as flag`() {
        val map = linkedMapOf("key1" to true, "key2" to false)
        val marshaled = marshal(map, LogFmtMarshalOptions(booleanAsFlag = true))
        assertEquals("key1", marshaled)
    }

    @Test
    fun `marshals map with boolean not as flag`() {
        val map = linkedMapOf("key1" to true, "key2" to false)
        val marshaled = marshal(map, LogFmtMarshalOptions(booleanAsFlag = false))
        assertEquals("key1=true key2=false", marshaled)
    }

    @Test
    fun `marshals empty strings with no quoting`() {
        val map = linkedMapOf("key1" to "", "key2" to null, "key3" to "value")
        val marshaled = marshal(map, LogFmtMarshalOptions(quoteEmptyStringsAndNull = false))
        assertEquals("key1= key2=null key3=value", marshaled)
    }

    @Test
    fun `marshals empty strings with quoting`() {
        val map = linkedMapOf("key1" to "", "key2" to null, "key3" to "value")
        val marshaled = marshal(map, LogFmtMarshalOptions(quoteEmptyStringsAndNull = true))
        assertEquals("key1=\"\" key2=\"\" key3=value", marshaled)
    }

    @Test
    fun `marshals empty strings with quoting and boolean as flag`() {
        val map = linkedMapOf("key1" to "", "key2" to null, "key3" to "value", "key4" to true)
        val marshaled = marshal(map, LogFmtMarshalOptions(quoteEmptyStringsAndNull = true, booleanAsFlag = true))
        assertEquals("key1=\"\" key2=\"\" key3=value key4", marshaled)
    }

    @Test
    fun `marshals with quoting when needed`() {
        val map = linkedMapOf("key" to "= '")
        val marshaled = marshal(map, LogFmtMarshalOptions(quoteEmptyStringsAndNull = false, booleanAsFlag = true))
        assertEquals("key=\"= '\"", marshaled)
    }

    @Test
    fun `marshals special characters`() {
        val map = linkedMapOf("key" to " \" \\ \r \n \t \b \u000C \u001E \u0080 \u2001 ")
        val marshaled = marshal(map, LogFmtMarshalOptions(quoteEmptyStringsAndNull = false, booleanAsFlag = true))
        assertEquals("key=\" \\\" \\\\ \\r \\n \\t \\b \\f \\u001E \\u0080 \\u2001 \"", marshaled)
    }
}
