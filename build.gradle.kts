import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
  mavenCentral()
  maven("https://jitpack.io")
  maven("https://repo.spring.io/milestone")
  maven("https://repo.spring.io/snapshot")
}
plugins {
  id("org.springframework.boot") version "3.0.1"
  id("io.spring.dependency-management") version "1.1.0"
  kotlin("jvm") version "1.8.0"
  kotlin("plugin.spring") version "1.8.0"
}
dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jsoup:jsoup:1.15.3")
  implementation("com.github.demidko:aot:2022.11.28")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("com.google.truth:truth:1.1.3")
  testImplementation("io.mockk:mockk:1.13.2")
}
tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "19"
  kotlinOptions.freeCompilerArgs += listOf("-opt-in=kotlin.time.ExperimentalTime", "-Xjsr305=strict")
}
tasks.test {
  minHeapSize = "1024m"
  maxHeapSize = "2048m"
  useJUnitPlatform()
  jvmArgs("--enable-preview")
}
tasks.bootJar {
  archiveVersion.set("boot")
}
