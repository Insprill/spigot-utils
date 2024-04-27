plugins {
    id("java")
    id("signing")
    id("maven-publish")
    id("io.freefair.lombok") version "8.6"
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

group = "net.insprill"
version = property("version")!!

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") // Spigot-API, MockBukkit
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.1.0")
    compileOnly("org.spigotmc:spigot-api:1.20.5-R0.1-SNAPSHOT")

    testImplementation("com.github.seeseemelk:MockBukkit-v1.20:3.86.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(8)
    }
    compileTestJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    test {
        useJUnitPlatform()
    }
}

java {
    withSourcesJar()
    withJavadocJar()
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set(project.name)
                description.set("A small library of commonly used functions in Spigot plugins")
                url.set("https://github.com/Insprill/spigot-utils")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("insprill")
                        name.set("Pierce Thompson")
                        url.set("https://github.com/Insprill")
                    }
                }
                scm {
                    url.set("https://github.com/Insprill/spigot-utils")
                    connection.set("scm:git:git://github.com/Insprill/spigot-utils.git")
                    developerConnection.set("scm:git:git@github.com:Insprill/spigot-utils.git")
                }
                issueManagement {
                    system.set("GitHub Issues")
                    url.set("https://github.com/Insprill/spigot-utils/issues")
                }
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}
