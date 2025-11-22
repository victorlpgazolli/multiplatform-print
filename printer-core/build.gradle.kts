
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.net.URI

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.mavenPublish)
    alias(libs.plugins.kotlinKsp)
}

group = "dev.victorlpgazolli.multiplatform-print-core"
version = "0.0.1"

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

    jvm("desktop"){}

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            xcf.add(this)
            isStatic = true
        }
    }

//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        moduleName = "dev.victorlpgazolli.printer"
//        browser {
//            val rootDirPath = project.rootDir.path
//            val projectDirPath = project.projectDir.path
//            commonWebpackConfig {
//                outputFileName = "kmpprinter.js"
//                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
//                    static = (static ?: mutableListOf()).apply {
//                        // Serve sources to debug inside browser
//                        add(rootDirPath)
//                        add(projectDirPath)
//                    }
//                }
//            }
//        }
//        binaries.executable()
//    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.print)
        }
        commonMain.dependencies {
            implementation(libs.okio)
            implementation(libs.kotlin.io)
            implementation(libs.kotlin.io.okio)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

val groupId = "dev.victorlpgazolli.multiplatform-print"
val artifactId = "printer-core"
val versionNumber = "0.0.1"

android {
    namespace = "dev.victorlpgazolli.multiplatform_print_core"
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
        name.set("Printer Core KMP Library")
        description.set("Printer Core MultiPlatform Library")
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
