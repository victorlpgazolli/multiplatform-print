
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.net.URI

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.mavenPublish)
}

val groupId = "dev.victorlpgazolli.multiplatform-print"
val artifactId = "printer-compose"
val versionNumber = "1.1.0"

group = "$groupId-compose"
version = versionNumber

kotlin {

    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = URI("https://maven.pkg.github.com/victorlpgazolli/multiplatform-print")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("PUBLISH_TOKEN")
                }
            }
        }
    }

    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm("desktop"){}
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "printer_compose"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "printer_compose.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
            }
        }
    }
}

android {
    namespace = "dev.victorlpgazolli.multiplatform_print_compose"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}



mavenPublishing {
    coordinates(
        groupId = groupId,
        artifactId = artifactId,
        version = versionNumber
    )

    pom {
        name.set("Printer Compose KMP Library")
        description.set("Printer Compose MultiPlatform Library")
        inceptionYear.set("2025")
        url.set("https://github.com/victorlpgazolli/multiplatform-print")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("victorlpgazolli")
            }
        }

        scm {
            url.set("https://github.com/victorlpgazolli/multiplatform-print")
        }
    }
}
