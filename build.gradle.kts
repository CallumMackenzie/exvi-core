val ktorVersion = "1.6.7"
val kryptoVersion = "2.2.0"

repositories {
    mavenCentral()
    google()
}

plugins {
    kotlin("multiplatform") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    id("maven-publish")
}

group = "com.camackenzie"
version = "1.0-SNAPSHOT"

kotlin {
    ios {
        binaries {
            framework()
        }
    }
//    android {
//        publishLibraryVariants("release", "debug")
//    }
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(BOTH) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS \"$hostOs\" is not supported in Kotlin/Native.")
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.soywiz.korlibs.krypto:krypto:$kryptoVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
                implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")
                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt"){
                    version {
                        strictly("1.6.0-native-mt")
                    }
                }
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val desktopMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-curl:$ktorVersion")
            }
        }
        val nativeMain by getting {
            dependsOn(desktopMain)
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-java:$ktorVersion")
                implementation("com.soywiz.korlibs.krypto:krypto-jvm:$kryptoVersion")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:$ktorVersion")
                implementation("com.soywiz.korlibs.krypto:krypto-js:$kryptoVersion")
            }
        }
//        val androidMain by creating {
//            dependsOn(commonMain)
//            dependencies {
//                implementation("io.ktor:ktor-client-android:$ktor_version")
//                implementation("com.soywiz.korlibs.krypto:krypto-android:$kryptoVersion")
//            }
//        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
            }
        }
    }
}