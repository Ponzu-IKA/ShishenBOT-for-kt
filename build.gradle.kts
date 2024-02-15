plugins {
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.serialization") version "1.7.21"
    id("io.ktor.plugin") version "2.3.8"
    application
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "ponzu_ika"
version = "1.0-alpha"

repositories {
    mavenCentral()
    maven("https://m2.dv8tion.net/releases")
    maven("https://jitpack.io")


}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("io.ktor:ktor-client-core:2.1.3")
    implementation("io.ktor:ktor-client-cio:2.1.3")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.21")


    implementation("net.dv8tion:JDA:[5.0.0-beta.20]")
    implementation("com.github.mnemotechnician:markov-chain:cbc5a5a")
    implementation("com.github.takscape:cmecab-java:2.1.0")
}

application {
    mainClass.set("${group}.${rootProject}.MainKt")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

tasks.compileKotlin {
    kotlinOptions.apply {
        jvmTarget = "17"
    }
}