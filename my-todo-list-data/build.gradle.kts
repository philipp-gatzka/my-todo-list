plugins {
  id("java-library")
  id("jacoco")
  alias(libs.plugins.lombok)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

dependencies {
  api(platform(libs.spring.boot.dependencies))
  api(libs.spring.boot.jpa)
  api(libs.spring.boot.validation)

  implementation("org.hibernate.orm:hibernate-envers")

  implementation(libs.mapstruct)

  annotationProcessor(libs.mapstruct.processor)

  testImplementation(libs.spring.boot.test)
  testImplementation("org.junit.jupiter:junit-jupiter-api")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
  useJUnitPlatform()
}

tasks.jacocoTestReport {
  reports {
    xml.required.set(true)
    html.required.set(false)
  }
}
