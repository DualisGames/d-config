import org.gradle.api.JavaVersion.VERSION_17
import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    `java-library`
    id("org.spongepowered.gradle.plugin") version "2.0.1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    `maven-publish`
}

group = "games.dualis"
version = "1.0-SNAPSHOT"

repositories {
   mavenCentral()
}

dependencies {
    implementation("org.codejargon", "fluentjdbc", "1.3.4")
}

//<editor-fold desc="API">
val API = true
sourceSets {
    val main by sourceSets

    if(!API) return@sourceSets
    create("api") {
        compileClasspath += main.compileClasspath
        runtimeClasspath += main.runtimeClasspath
    }
}
//</editor-fold>

//<editor-fold desc="Java configuration">
configure<JavaPluginExtension>{
    sourceCompatibility = VERSION_17
    targetCompatibility = VERSION_17

    withJavadocJar()
    withSourcesJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
//</editor-fold>

//<editor-fold desc="Sponge">
sponge {
    apiVersion("9.0.0-SNAPSHOT")
    license("CHANGEME")
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version("1.0")
    }
    plugin("d-config") {
        displayName("D-Config")
        entrypoint("games.dualis.dconfig.DConfigPlugin")
        description("A plugin that centralizes some configuration for our libraries.")
        links {
            homepage("https://dualis.games")
            source("https://github.com/DualisGames/d-config")
        }
        contributor("Shyrogan") {
            description("Lead Developer")
        }
        dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}
//</editor-fold>

//<editor-fold desc="Publishing">
tasks {
    if(!API) return@tasks

    create("apiJar", Jar::class) {
        group = "build"
        archiveClassifier.set("api")
        from(sourceSets["api"].output)
        from("LICENSE")
    }
}

// Define the default artifacts' tasks
val defaultArtifactTasks = arrayOf(
    tasks["sourcesJar"],
    tasks["javadocJar"]
).also { arr ->
    if(API) arr.plus(tasks["apiJar"])
}

publishing.publications {
    // Sets up the Maven integration.
    create("mavenJava", MavenPublication::class.java) {
        from(components["java"])
        defaultArtifactTasks.forEach(::artifact)
    }
}
//</editor-fold>
