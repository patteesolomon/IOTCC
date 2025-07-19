plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "org.example.project"
version = "1.0.0"
application {
    mainClass.set("org.example.project.ApplicationKt")
    
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
var kotlin_version = "2.1.0"
var KTOR_VER = "3.1.0"
dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlin_version}")
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.identity.jvm)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.serverCoreJVM)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serverNettyJVM)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}