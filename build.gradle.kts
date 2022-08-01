repositories {
  mavenCentral()
  maven("https://jitpack.io")
  maven("https://repo.spring.io/milestone")
  maven("https://repo.spring.io/snapshot")
}
plugins {
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  id("org.springframework.boot") version "3.0.0-SNAPSHOT"
  kotlin("jvm") version "1.7.10"
  kotlin("plugin.spring") version "1.7.10"
  kotlin("plugin.serialization") version "1.7.10"
}
dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jsoup:jsoup:1.15.2")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("com.google.truth:truth:1.1.3")
  testImplementation("io.mockk:mockk:1.12.4")
}
tasks.compileKotlin {
  kotlinOptions.jvmTarget = "18"
  kotlinOptions.freeCompilerArgs += listOf("-opt-in=kotlin.time.ExperimentalTime", "-Xjsr305=strict")
}
tasks.compileTestKotlin {
  kotlinOptions.jvmTarget = "18"
  kotlinOptions.freeCompilerArgs += listOf("-opt-in=kotlin.time.ExperimentalTime", "-Xjsr305=strict")
}
tasks.test {
  useJUnitPlatform()
}
tasks.bootJar {
  archiveVersion.set("boot")
}
