plugins {
  id("java")
  id("jacoco")
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.lombok)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

dependencies {
  implementation(project(":my-todo-list-data"))
  implementation(platform(libs.spring.boot.dependencies))
  implementation(libs.spring.boot.web)
  implementation(libs.spring.boot.security)

  implementation(libs.jwt.api)
  implementation(libs.jwt.impl)
  implementation(libs.jwt.jackson)

  runtimeOnly("org.postgresql:postgresql")

  testImplementation(libs.spring.boot.test)
  testRuntimeOnly("com.h2database:h2")

  implementation(libs.spring.boot.devtools)
}

tasks {
  test {
    useJUnitPlatform()
  }
  jacocoTestReport {
    reports {
      xml.required.set(true)
      html.required.set(false)
    }
  }
}
