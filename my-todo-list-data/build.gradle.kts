plugins {
  id("java-library")
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
}
