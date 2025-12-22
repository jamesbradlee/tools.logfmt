# Logfmt

A simple logfmt marshaller for Kotlin.

## Usage in Logback

**File: _gradle/libs.versions.toml_**

```toml
[versions]
logfmt = "0.1.0"

[dependencies]
logfmt-logback = { module = "tools.logfmt:logfmt-logback", version.ref = "logfmt" }
```

**File: _src/main/resources/logback.xml_**

```xml
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="tools.logfmt.logback.Encoder">
            <default />
            
            <!-- Customizations -->
            <!-- All customizations have default values -->
            
            <timestamp>
                <field>time</field>
                <format>
                    <!--
                        ISO_LOCAL_DATE
                        ISO_OFFSET_DATE
                        ISO_DATE
                        ISO_LOCAL_TIME
                        ISO_OFFSET_TIME
                        ISO_TIME
                        ISO_LOCAL_DATE_TIME
                        ISO_OFFSET_DATE_TIME
                        ISO_ZONED_DATE_TIME
                        ISO_DATE_TIME
                        ISO_ORDINAL_DATE
                        ISO_WEEK_DATE
                        ISO_INSTANT
                        ... or a custom pattern, e.g. "yyyy-MM-dd HH:mm:ss.SSSZ"
                    -->
                </format>
            </timestamp>
            
            <logger>
                <field>logger</field>
            </logger>
            
            <level>
                <field>level</field>
                <mapping>
                    <trace>TRACE</trace>
                    <debug>DEBUG</debug>
                    <info>INFO</info>
                    <warn>WARN</warn>
                    <error>ERROR</error>
                </mapping>
            </level>
            
            <message>
                <field>msg</field>
            </message>
            
            <mdc>
                <prefix>mdc.</prefix>
            </mdc>
            
            <arguments>
                <prefix>arg.</prefix>
            </arguments>
        </encoder>
    </appender>
    
    <root level="INFO">
        <appender-ref ref="CONSOLE" />
    </root>
</configuration>
```

You can also use structured arguments:

```kotlin
import tools.logfmt.logback.kv
import tools.logfmt.logback.v

logger.info("Database accessed by %s", kv("username", "alice"))
// Database accessed by username=alice

logger.info("Database accessed by %s", kv("application", "myapp"))
// Database accessed by application=myapp

logger.info("The weather is %s", v("weather", "sunny"))
// The weather is sunny
```

Using the structured arguments will require that the arguments section is enabled in the encoder configuration, either
by using the `<default />` tag or by explicitly adding the `<arguments>` section.
