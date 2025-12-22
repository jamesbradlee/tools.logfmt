plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
    alias(libs.plugins.ktlint)
    `maven-publish`
    signing
}

allprojects {
    group = "tools.logfmt"
    version = System.getenv("VERSION") ?: "UNVERSIONED"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    kotlin.jvmToolchain(21)

    if (System.getenv("PUBLISHING") == "true") {
        apply(plugin = "maven-publish")
        apply(plugin = "signing")

        afterEvaluate {
            val sourcesJar by tasks.register<Jar>("sourcesJar") {
                dependsOn(tasks.getByName("classes"))
                archiveClassifier.set("sources")
                from(sourceSets["main"].allSource)
            }

            val dokkaHtmlJar by tasks.registering(Jar::class) {
                from(tasks.dokkaGeneratePublicationHtml.flatMap { it.outputDirectory })
                archiveClassifier.set("javadoc")
            }

            signing {
                sign(publishing.publications)
                useInMemoryPgpKeys(
                    System.getenv("SIGNING_KEY_ID"),
                    System.getenv("SIGNING_KEY"),
                    System.getenv("SIGNING_PASSWORD"),
                )
            }

            publishing {
                repositories {
                    maven {
                        name = "central"
                        url = uri("https://central.sonatype.com/api/v1/publisher")
                        credentials {
                            username = System.getenv("CENTRAL_TOKEN_USERNAME")
                            password = System.getenv("CENTRAL_TOKEN_PASSWORD")
                        }
                    }
                }

                publications.create<MavenPublication>("central") {
                    from(components["kotlin"])
                    artifact(sourcesJar)
                    artifact(dokkaHtmlJar)

                    pom {
                        name.set(project.name)
                        description.set(requireNotNull(project.description))
                        url.set("https://logfmt.tools")

                        licenses {
                            license {
                                name.set("MIT License")
                                url.set("https://opensource.org/licenses/MIT")
                            }
                        }

                        scm {
                            url.set("https://github.com/jamesbradlee/tools.logfmt")
                            connection.set("scm:git:https://github.com/jamesbradlee/tools.logfmt.git")
                        }

                        developers {
                            developer {
                                id.set("jamesbradlee")
                                name.set("James Bradlee")
                            }
                        }
                    }
                }
            }
        }
    }
}
