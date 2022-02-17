val ktorVersion = "1.6.7"
val kryptoVersion = "2.2.0"
val coroutineVersion = "1.6.0"
val jUnitVersion = "4.7"

plugins {
    kotlin("multiplatform") // version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    id("com.android.library")
    id("maven-publish")
}

group = "com.camackenzie"
version = "1.0-SNAPSHOT"

kotlin {
    android()
    ios {
        binaries {
            framework()
        }
    }
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
                api("com.soywiz.korlibs.krypto:krypto:$kryptoVersion")
                api("io.ktor:ktor-client-core:$ktorVersion")
                api("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val nativeMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-curl:$ktorVersion")
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt") {
//                    version {
//                        strictly("1.6.0-native-mt")
//                    }
//                }
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-java:$ktorVersion")
//                implementation("com.soywiz.korlibs.krypto:krypto-jvm:$kryptoVersion") {
//                    exclude(group="com.soywiz.korlibs.krypto", module = "krypto-android")
//                }
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:$ktorVersion")
//                implementation("com.soywiz.korlibs.krypto:krypto-js:$kryptoVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
//                implementation("com.soywiz.korlibs.krypto:krypto-android:$kryptoVersion") {
//                    exclude(group="com.soywiz.korlibs.krypto", module = "krypto-jvm")
//                }
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:$jUnitVersion")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt") {
//                    version {
//                        strictly("1.6.0-native-mt")
//                    }
//                }
            }
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 31
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}