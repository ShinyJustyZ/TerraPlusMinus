import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    `java-library`
    alias(libs.plugins.lombok)
    alias(libs.plugins.plugin.yml)
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.shadow)
}

repositories {
    mavenCentral()

    maven("https://jitpack.io")

    maven("https://maven.buildtheearth.net/releases")

    maven("https://maven.daporkchop.net/")

    exclusiveContent {
        forRepository {
            maven {
                url = uri("https://repo.papermc.io/repository/maven-public/")
            }
        }
        filter {
            includeGroup("io.papermc")
            includeGroup("io.papermc.paper")
            includeGroup("net.md-5")
            includeGroup("com.mojang")
        }
    }

    maven("https://repo.lushplugins.org/releases")
}

dependencies {
    // Pinned to v2.2.3 (QET1 nodata fix + daporkchop dependency fix)
    implementation("com.github.ShinyJustyZ.terraminusminus:terraminusminus-bukkit:v2.2.3")
    implementation(libs.daporkchop.lib.common)
    implementation(libs.bstats)
    implementation(libs.pluginupdater.common) {
        exclude(group = "com.google.guava", module = "guava")
    }
    implementation(libs.jspecify)
    implementation(libs.pluginupdater.paper)

    compileOnly(libs.paper.api)
    compileOnly(libs.jackson.databind)
    compileOnly(libs.jetbrains.annotations)
}

group = "de.btegermany"
version = "1.7.2-SNAPSHOT"
description = "A plugin which implements the terra-- api in a paper plugin"
java.sourceCompatibility = JavaVersion.VERSION_24

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

paper {
    website = "https://github.com/ShinyJustyZ/TerraPlusMinus"

    main = "de.btegermany.terraplusminus.Terraplusminus"

    apiVersion = "1.21"

    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    authors = listOf("meysster", "Nudlsupp", "Nachwahl", "Zoriot", "ShinyJustyZ")

    prefix = "T+-"

}

tasks {
    generatePaperPluginDescription {
        useDefaultCentralProxy()
    }
}

tasks.jar {
    archiveClassifier = "UNSHADED"
    enabled = false // Disable the default jar task since we are using shadowJar
}

tasks.shadowJar {
    archiveClassifier = ""
    relocate(
        "org.bstats",
        "de.btegermany.terraplusminus.libs.bstats"
    )
}

tasks.assemble {
    dependsOn(tasks.shadowJar) // Ensure that the shadowJar task runs before the build task
}
