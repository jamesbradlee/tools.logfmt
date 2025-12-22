dependencies {
    testImplementation(libs.kotlin.test)
}

description = "Logfmt marshaling library"

tasks.test {
    useJUnitPlatform()
}
