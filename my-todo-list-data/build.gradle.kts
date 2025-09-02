plugins {
  id("java-library")
  alias(libs.plugins.lombok)
}

dependencies {
  api(platform(libs.spring.boot.dependencies))
  api(libs.spring.boot.jpa)
  api(libs.spring.boot.validation)

  implementation("org.hibernate.orm:hibernate-envers")

  implementation(libs.mapstruct)

  annotationProcessor(libs.mapstruct.processor)
}
